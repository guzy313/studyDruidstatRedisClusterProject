package com.my.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Gzy
 * @version 1.0
 */
//@Configuration
public class RedisClusterConfig  {
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.cluster.password}")
    private String password;
    @Value("${spring.redis.connectionTimeout}")
    private int connectionTimeout;
    @Value("${spring.redis.maxAttempts}")
    private int maxAttempts;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;
    @Value("${spring.redis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.cluster.command-timeout}")
    private int commandTimeout;

    /* Jedis - 集群、连接池模式 */
    @Bean
    public JedisCluster jedisCluster(){

        /* 切割节点信息 */
        String[] nodes = clusterNodes.split(",");
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        for (String node : nodes) {
            int index = node.indexOf(":");
            hostAndPorts.add(new HostAndPort(node.substring(0,index),Integer.parseInt(node.substring(index + 1))));
        }
        /* Jedis连接池配置 */
        JedisPoolConfig jedisPoolConfig = getJedisPoolConfig();

        return new JedisCluster(hostAndPorts,connectionTimeout,
                commandTimeout,maxAttempts,password,jedisPoolConfig);

    }
    /**
     * 连接池配置
     * @return JedisPoolConfig
     **/
    private JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle); // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxTotal(maxActive); // 最大连接数, 默认8个
        jedisPoolConfig.setMinIdle(minIdle); // 最小空闲连接数, 默认0
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis); // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
        jedisPoolConfig.setTestOnBorrow(true); // 对拿到的connection进行validateObject校验
        return jedisPoolConfig;
    }

}
