package redis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class RedisClient {
    private Jedis jedis;
    private JedisPool jedisPool;

    public RedisClient() {
        initPool();
        jedis = jedisPool.getResource();
    }

    // 初始化
    private void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(512);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "localhost", 6379);
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void returnResource(Jedis jedis) {
        if(jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

}
