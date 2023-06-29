/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.antgroup.antchain.xbuilders.integration.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("aliyun.oss.file")
@ConditionalOnProperty(name = "mock", havingValue = "false", matchIfMissing = true)
public class OssConfig {

    @Setter
    private String endpoint;

    @Setter
    private Boolean needSsl;

    @Setter
    private String keyId;

    @Setter
    private String keySecret;

    /**
     * oss客户端初始化
     *
     * @return
     */
    @Bean
    public OSSClient ossClient() {
        ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();
        if (needSsl) {
            configuration.setProtocol(Protocol.HTTPS);
        } else {
            configuration.setProtocol(Protocol.HTTP);
        }
        return (OSSClient) new OSSClientBuilder().build(endpoint, keyId, keySecret, configuration);
    }

}