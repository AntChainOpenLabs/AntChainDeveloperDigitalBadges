package com.antgroup.antchain.xbuilders.integration.config;

import lombok.Setter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("spring.redis")
@ConditionalOnProperty(name = "redis.enable", havingValue = "true", matchIfMissing = true)
public class RedissonConfig {
    @Setter
    private String host;

    @Setter
    private Integer port;

    @Setter
    private Integer database;

    @Setter
    private String password;

    @Setter
    private Integer timeout;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress(String.format("redis://%s:%d", host, port == null ? 6379 : port))
                .setDatabase(database)
                .setPassword(password)
                .setTimeout(timeout)
                .setConnectionPoolSize(64)
                .setConnectionMinimumIdleSize(16);
        return Redisson.create(config);
    }
}

