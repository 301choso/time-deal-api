package kr.rebe.deal.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.io.IOException;

/**
 * 분산 락 처리를 위한 Redisson config
 * */
@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
public class RedissonConfig {

    private final RedisProperties redisProperties;

    @Bean(name = "redissonClient")
    public RedissonClient redissonClient() throws IOException {
        RedissonClient redisson = null;
        Config config = new Config();
        final Codec codec = new StringCodec(); // redis-cli에서 보기 위해
        config.setCodec(codec);
        config.useSingleServer().
                setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort()).
                setConnectionPoolSize(100);
        redisson = Redisson.create();
        return redisson;
    }
}
