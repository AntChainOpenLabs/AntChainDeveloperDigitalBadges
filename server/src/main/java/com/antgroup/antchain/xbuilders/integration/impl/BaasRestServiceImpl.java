package com.antgroup.antchain.xbuilders.integration.impl;

import com.alibaba.fastjson.JSON;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.model.CallRestBizParam;
import com.antfinancial.mychain.baas.tool.restclient.model.Method;
import com.antfinancial.mychain.baas.tool.restclient.response.BaseResp;
import com.antfinancial.mychain.baas.tool.restclient.utils.RestClientUtils;
import com.antgroup.antchain.xbuilders.dal.entity.ChainConfig;
import com.antgroup.antchain.xbuilders.dal.mapper.ChainConfigMapper;
import com.antgroup.antchain.xbuilders.integration.BaasRestService;
import com.antgroup.antchain.xbuilders.integration.entity.BaaSRestCallContractBO;
import com.antgroup.antchain.xbuilders.integration.entity.BaasRestQueryReceiptRequest;
import com.antgroup.antchain.xbuilders.integration.entity.TransactionReceiptBO;
import com.antgroup.antchain.xbuilders.integration.enums.ContractTypeEnum;
import com.antgroup.antchain.xbuilders.model.exception.BusinessException;
import com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class BaasRestServiceImpl implements BaasRestService {

    @Autowired
    private ChainConfigMapper chainConfigMapper;

    @Value("${baas.rest.url}")
    private String restUrl;

    @Value("${baas.rest.cipher}")
    private String cipher;

    @Value("${baas.rest.retry}")
    private Long retry;

    @Value("${baas.rest.retry-interval}")
    private Long retryInterval;

    /**
     * chainId -> 链client缓存
     */
    private final Map<String, RestClient> restClientCache = new ConcurrentHashMap<>();

    @Override
    @Nonnull
    public RestClient getRestClient(String bizid) {
        // 获取缓存中的rest client
        RestClient restClient = restClientCache.get(bizid);
        if (restClient == null) {
            // 缓存中没找到，去数据库里查一下有没有，有的话创建client并更新缓存
            restClient = initClient(bizid);
            if (restClient == null) {
                // 如果还是为空，报错
                throw new BusinessException(ErrorCodeEnum.SYS_FAIL, String.format("chain %s not configured", bizid));
            }
        }
        return restClient;
    }

    @Override
    public TransactionReceiptBO callContract(BaaSRestCallContractBO callContractBO) {
        // 获取缓存中的rest client
        RestClient restClient = getRestClient(callContractBO.getChainId());
        RestClientProperties properties = restClient.getRestClientProperties();

        // 构建调用合约参数
        CallRestBizParam callRestBizParam = CallRestBizParam.builder()
                .account(properties.getAccount())
                .mykmsKeyId(properties.getKmsId())
                .orderId(UUID.randomUUID().toString())
                .method(getRestMethod(callContractBO))
                // 暂时写死10万
                .gas(500000L)
                .contractName(callContractBO.getContractName())
                .methodSignature(getMethodSignature(callContractBO))
                .inputParamListStr(JSON.toJSONString(callContractBO.getParams()))
                // hack to pass baas rest param check
                .outTypes("[]")
                .build();
        log.info("baasrest,callContract,request,{}", callRestBizParam.toString());

        BaseResp baseResp = null;
        for (long i = retry; i > 0; --i) {
            try {
                // 异步调用合约
                baseResp = restClient.chainCallForBiz(callRestBizParam);
                log.info("baasrest,callContract,response,{}", baseResp.toString());
                if (baseResp.isSuccess()) {
                    break;
                } else {
                    log.error("call to send tx to baas rest, request: {}, code: {}, error: {}", callRestBizParam, baseResp.getCode(), baseResp.getData());
                }
            } catch (Exception e) {
                // 调用合约未知异常
                log.error("failed to send request to baas rest, request: {}", callRestBizParam, e);
            }

            try {
                Thread.sleep(retryInterval * 1000);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodeEnum.SYS_FAIL);
            }
        }

        if (baseResp == null) {
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, "unknown baas rest error");
        } else if (!baseResp.isSuccess()) {
            // 调用合约rest服务或者链平台异常
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, String.format("call to send tx to baas rest, request: %s, code: %s, error: %s", callRestBizParam, baseResp.getCode(), baseResp.getData()));
        }

        String txHash = baseResp.getData();
        try {
            // 查询合约回执
            baseResp = restClient.multipleQueryReceipt(callContractBO.getChainId(), txHash);
            log.info("baasrest,callContract,receipt,{}", baseResp.toString());
        } catch (Exception e) {
            // 查询回执失败
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, String.format("failed to get receipt of tx %s", txHash), e);
        }

        if (!baseResp.isSuccess()) {
            // 调用合约rest服务或者链平台异常
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, String.format("failed to get receipt of tx %s, code: %s, error: %s", txHash, baseResp.getCode(), baseResp.getData()));
        }

        // 解析并返回调用结果
        return TransactionReceiptBO.fromRestResponseJson(JSON.parseObject(baseResp.getData()));
    }


    @Override
    public String sendTransaction(BaaSRestCallContractBO callContractBO) {
        // 获取缓存中的rest client
        RestClient restClient = getRestClient(callContractBO.getChainId());
        RestClientProperties properties = restClient.getRestClientProperties();

        // 构建调用合约参数
        CallRestBizParam callRestBizParam = CallRestBizParam.builder()
                .account(properties.getAccount())
                .mykmsKeyId(properties.getKmsId())
                .orderId(UUID.randomUUID().toString())
                .method(getRestMethod(callContractBO))
                // 暂时写死10万
                .gas(500000L)
                .contractName(callContractBO.getContractName())
                .methodSignature(getMethodSignature(callContractBO))
                .inputParamListStr(JSON.toJSONString(callContractBO.getParams()))
                // hack to pass baas rest param check
                .outTypes("[]")
                .build();
        log.info("baasrest,callContract,request,{}", callRestBizParam.toString());

        BaseResp baseResp = null;
        for (long i = retry; i > 0; --i) {
            try {
                // 异步调用合约
                baseResp = restClient.chainCallForBiz(callRestBizParam);
                log.info("baasrest,callContract,response,{}", baseResp.toString());
                if (baseResp.isSuccess()) {
                    break;
                } else {
                    log.error("call to send tx to baas rest, request: {}, code: {}, error: {}", callRestBizParam, baseResp.getCode(), baseResp.getData());
                }
            } catch (Exception e) {
                // 调用合约未知异常
                log.error("failed to send request to baas rest, request: {}", callRestBizParam, e);
            }

            try {
                Thread.sleep(retryInterval * 1000);
            } catch (Exception e) {
                throw new BusinessException(ErrorCodeEnum.SYS_FAIL);
            }
        }

        if (baseResp == null) {
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, "unknown baas rest error");
        } else if (!baseResp.isSuccess()) {
            // 调用合约rest服务或者链平台异常
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, String.format("call to send tx to baas rest, request: %s, code: %s, error: %s", callRestBizParam, baseResp.getCode(), baseResp.getData()));
        }

        return baseResp.getData();
    }

    public TransactionReceiptBO queryReceipt(BaasRestQueryReceiptRequest queryReceiptRequest) {

        // 获取缓存中的rest client
        RestClient restClient = getRestClient(queryReceiptRequest.getChainId());
        RestClientProperties properties = restClient.getRestClientProperties();

        BaseResp baseResp;
        String txHash = queryReceiptRequest.getTxHash();
        try {
            // 查询合约回执
            baseResp = restClient.multipleQueryReceipt(queryReceiptRequest.getChainId(), txHash);
            log.info("baasrest,callContract,receipt,{}", baseResp.toString());
        } catch (Exception e) {
            // 查询回执失败
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, String.format("failed to get receipt of tx %s", txHash), e);
        }

        if (!baseResp.isSuccess()) {
            // 调用合约rest服务或者链平台异常
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, String.format("failed to get receipt of tx %s, code: %s, error: %s", txHash, baseResp.getCode(), baseResp.getData()));
        }

        // 解析并返回调用结果
        return TransactionReceiptBO.fromRestResponseJson(JSON.parseObject(baseResp.getData()));
    }

    private RestClient initClient(String chainId) {
        ChainConfig chainConfig = chainConfigMapper.getChainConfigByChainId(chainId);
        if (chainConfig == null) {
            return null;
        }

        return initClient(chainConfig);
    }

    private RestClient initClient(ChainConfig chainConfig) {
        if (StringUtils.isEmpty(chainConfig.getAccessSecret())) {
            log.error("invalid chain config {}", chainConfig.getChainName());
            throw new BusinessException(ErrorCodeEnum.SYS_FAIL);
        } else if (chainConfig.getAccessSecret().split("\n").length < 2) {
            // 处理下数据库里的escape情况
            String[] split = chainConfig.getAccessSecret().split("\\\\n");
            if (split.length < 2) {
                log.error("invalid chain config {}", chainConfig.getChainName());
                throw new BusinessException(ErrorCodeEnum.SYS_FAIL);
            }

            chainConfig.setAccessSecret(Strings.join(Arrays.asList(split), '\n'));
        }

        log.info("initializing chain {}...", chainConfig.getChainName());
        RestClientProperties restClientProperties = new RestClientProperties();
        restClientProperties.setBizid(chainConfig.getChainId());
        restClientProperties.setCipherSuit(cipher);
        restClientProperties.setRestUrl(restUrl);
        restClientProperties.setAccessId(chainConfig.getAccessKey());
        restClientProperties.setAccessSecret(chainConfig.getAccessSecret());
        restClientProperties.setAccount(chainConfig.getAdminAccountName());
        restClientProperties.setTenantid(chainConfig.getTenant());
        restClientProperties.setKmsId(chainConfig.getAdminAccountKmsId());
        restClientProperties.setAccessSecretType("Flat");

        RestTemplate restTemplate = RestClientUtils.createRestTemplate(restClientProperties);
        try {
            RestClient client = new RestClient(restTemplate, restClientProperties);
            client.init();
            client.retryTemplate = RestClientUtils.createRetryTemplate(restClientProperties);

            // 缓存rest客户端
            restClientCache.put(chainConfig.getChainId(), client);
            log.info("initialized chain {}", chainConfig.getChainName());
            return client;
        } catch (Exception e) {
            log.error(String.format("failed to init chain %s", chainConfig.getChainName()));
            return null;
        }
    }

    private static String getMethodSignature(BaaSRestCallContractBO callContractBO) {
        if (ContractTypeEnum.WASM.equals(callContractBO.getContractType())) {
            return callContractBO.getMethod();
        }

        return callContractBO.getMethodSignature();
    }

    private static Method getRestMethod(BaaSRestCallContractBO callContractBO) {
        if (ContractTypeEnum.WASM.equals(callContractBO.getContractType())) {
            return Method.CALLWASMCONTRACTASYNC;
        }

        return Method.CALLCONTRACTBIZASYNC;
    }

}
