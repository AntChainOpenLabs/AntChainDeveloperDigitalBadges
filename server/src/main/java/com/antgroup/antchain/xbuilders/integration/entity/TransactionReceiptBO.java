package com.antgroup.antchain.xbuilders.integration.entity;

import cn.com.antcloud.api.utils.Base64;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReceiptBO {
    /**
     * 交易回执状态码
     */
    private Long code;

    /**
     * 资产id
     */
    private String output;

    /**
     * 事件
     */
    private List<LogBO> logs;

    /**
     * 交易哈希
     */
    private String txHash;

    public boolean isSuccess() {
        return code != null && code.equals(0L);
    }

    public static TransactionReceiptBO fromRestResponseJson(JSONObject jsonObject) {
        TransactionReceiptBO bo = new TransactionReceiptBO();
        bo.setCode(jsonObject.getLong("result"));
        bo.setTxHash(jsonObject.getString("hash"));

        String output = jsonObject.getString("output");
        if (StringUtils.isNotEmpty(output)) {
            bo.setOutput(Hex.toHexString(Base64.decode(output)));
        }
        if (jsonObject.containsKey("logs")) {
            bo.setLogs(jsonObject.getJSONArray("logs").stream()
                    .map(log -> LogBO.fromJson((JSONObject) log))
                    .collect(Collectors.toList()));
        }

        return bo;
    }
}
