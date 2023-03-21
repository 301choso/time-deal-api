package kr.rebe.deal.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 분산 락 처리를 위한 Redisson config
 * */
@Configuration
@RequiredArgsConstructor
public class RedissonConfig {

    @Value("${spring.redis.host:myredis}")
    private String HOST;

    @Value("${spring.redis.port:6379}")
    private String PORT;

    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redisson = null;
        Config config = new Config();
        final Codec codec = new StringCodec(); // redis-cli에서 보기 위해
        config.setCodec(codec);
        config.useSingleServer().
                setAddress("redis://" + HOST + ":" + PORT).
                setConnectionPoolSize(100);
        redisson = Redisson.create(config);
        return redisson;
    }
}
