package com.my.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
class AdminApplicationTests {

    private Jedis jedis = new Jedis("127.0.0.1",6379);

    @Resource
    private JedisCluster jedisCluster;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {
    }


    /**
     * 重置秒杀数据
     */
    @Test
    void clearSecKillDemo(){
        jedis.auth("12345");
        jedis.select(3);
        jedis.set("goodsAmount","100");
        jedis.del("seckillUserIds");
        jedis.del("lockToSeckill");
        System.out.println("重置秒杀数据成功");
    }

    @Test
    void testClusterOperationByJedis(){

        jedisCluster.set("lock1","123");
        System.out.println("成功");


    }

    @Test
    void testStringRedisTemplate(){

        redisTemplate.opsForValue().set("key1","123");
        System.out.println("操作成功");

    }

    @Test
    void testAssertions(){
        Assertions.assertAll("testAll1",() ->Assertions.assertEquals(1,1,"不相等"),
                ()->Assertions.assertTrue(false,"不为真"));
    }

}
