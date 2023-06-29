package com.antgroup.antchain.xbuilders.integration.config;

import com.antgroup.antchain.openapi.appex.Client;
import com.antgroup.antchain.openapi.appex.models.Config;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("antchain.openapi")
public class AntChainOpenAPIConfig {
    @Setter
    private String endPoint;

    @Setter
    private String accessKey;

    @Setter
    private String accessSecret;

    @Setter
    private String protocol;

    @Bean
    public Client openApiClient() throws Exception {
        Config config = new Config();
        config.setAccessKeyId(accessKey);
        config.setAccessKeySecret(accessSecret);
        config.setEndpoint(endPoint);
        if (StringUtils.isNotEmpty(protocol)) {
            config.setProtocol(protocol);
        }
        return new Client(config);
    }
}
