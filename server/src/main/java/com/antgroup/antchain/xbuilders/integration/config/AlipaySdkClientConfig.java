package com.antgroup.antchain.xbuilders.integration.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("alipay.openapi")
public class AlipaySdkClientConfig {

    @Setter
    private String privateKey;

    @Setter
    private String publicKey;

    @Setter
    private String endpoint;

    @Setter
    private String appId;

    @Setter
    private String signType;

    @Setter
    private String charset;

    @Setter
    private String format;

    /**
     * 支付宝开放平台客户端
     *
     * @return
     */
    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(endpoint, appId, privateKey, format,
                charset, publicKey, signType);
    }

}
