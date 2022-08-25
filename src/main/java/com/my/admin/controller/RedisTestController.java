package com.my.admin.controller;

import com.my.admin.thread.SecKillTestThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Map;
import java.util.UUID;

/**
 * @author Gzy
 * @version 1.0
 */
@Controller
@RequestMapping("/redisController/*")
public class RedisTestController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SecKillTestThread secKillTestThread;

    private Jedis jedis = new Jedis("127.0.0.1",6379);

//    @Autowired
//    private JedisCluster jedisCluster;


    @ResponseBody
    @GetMapping("/testRedis1")
    public String testRedis1(){

        String lockName = "lock";
        jedis.auth("12345");
        Long setnx = jedis.setnx(lockName, UUID.randomUUID().toString());
        if(0 == setnx){
            //已经有锁无法设置的情况
        }else{
            //成功设置锁 开始操作
            //设置锁过期时间 防止宕机死锁
            jedis.expire(lockName,10);
            //业务操作
            /////
            //释放锁
            jedis.del(lockName);
        }


        //设置值
        redisTemplate.opsForValue().set("rtp1","测试1");
        //获取值
        Object rtp1 = redisTemplate.opsForValue().get("rtp1");
        return String.valueOf(rtp1);
    }


    @ResponseBody
    @RequestMapping("seckill")
    public Map<String,Object> seckill(@RequestParam(value = "uuid",required = true)String uuid,
                                      Map<String,Object> model){
        uuid = String.valueOf(SecKillTestThread.randomUuid());//随机uuid 测试用
//        model = secKillTestThread.secKillOptimistic(uuid);
        model = secKillTestThread.secKillNoLock(uuid);
        return model;
    }


    @ResponseBody
    @RequestMapping("testRedisCluster")
    public void testRedisCluster(){
//        System.out.println("开始操作集群");
//        Long lock1 = jedisCluster.setnx("lock1", "11");
//        jedisCluster.expire("lock1",60);
//        System.out.println("操作集群成功");
    }


}
