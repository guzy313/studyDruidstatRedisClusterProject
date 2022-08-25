package com.my.admin.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class SecKillTestThread extends Thread {


    @Autowired
    private RedisTemplate redisTemplate;

    private static Jedis jedis = new Jedis("127.0.0.1",6379);

    @Override
    public void run() {
        //登录redis
        jedis.auth("12345");
        //操作4号数据库
        jedis.select(3);
        boolean flag = true;
        while (flag){

//            secKillPessimistic();//悲观锁实现
            //随机生成用户ID 1 - 20;
            int userId = (int)(Math.random()*20 +1);

            flag = (boolean)secKillOptimistic(String.valueOf(userId)).get("message");
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //不做任何加锁操作——测试用
    public Map<String,Object> secKillNoLock(String uuid){
        Map<String,Object> model = new HashMap<>();
        //登录redis
        jedis.auth("12345");
        //操作4号数据库
        jedis.select(3);
        //模拟用户ID
//        String uuid = UUID.randomUUID().toString();
        //redis存储剩余商品数量的字段
        String goodsAmountField = "goodsAmount";
        String userIdsField = "seckillUserIds";


        Boolean sismember = jedis.sismember(userIdsField,uuid);



        int surplus = Integer.parseInt(jedis.get(goodsAmountField));
        if(surplus < 1){
            System.out.println("库存小于0,用户"+uuid+"秒杀失败");
            return getResultMsg("库存小于0,用户"+uuid+"秒杀失败",
                    "500",false);
        }
        if(sismember){
            System.out.println("用户"+uuid+"已经秒杀过了");
            return getResultMsg("用户"+uuid+"已经秒杀过了",
                    "500",false);
        }


            //商品剩余数量
            int suiplusGoodsAmount = Integer.parseInt(String.valueOf(jedis.get(goodsAmountField)));
            //符合条件 剩余商品自减1
            if(suiplusGoodsAmount >= 1){
                jedis.decr(goodsAmountField);
                //设置秒杀成功用户ID
                jedis.sadd(userIdsField,uuid);
                System.out.println("线程"+Thread.currentThread().getName()+"秒杀成功");
            }
            jedis.close();
            return getResultMsg("秒杀成功",
                    "200",true);
    }



    //悲观锁
    public Map<String,Object> secKillPessimistic(String uuid){
        Map<String,Object> model = new HashMap<>();
        //登录redis
        jedis.auth("12345");
        //操作4号数据库
        jedis.select(3);
        //模拟用户ID
//        String uuid = UUID.randomUUID().toString();
        //redis存储剩余商品数量的字段
        String goodsAmountField = "goodsAmount";
        String userIdsField = "seckillUserIds";


        Boolean sismember = jedis.sismember(userIdsField,uuid);



        int surplus = Integer.parseInt(jedis.get(goodsAmountField));
        if(surplus < 1){
            System.out.println("库存小于0,用户"+uuid+"秒杀失败");
            return getResultMsg("库存小于0,用户"+uuid+"秒杀失败",
                    "500",false);
        }
        if(sismember){
            System.out.println("用户"+uuid+"已经秒杀过了");
            return getResultMsg("用户"+uuid+"已经秒杀过了",
                    "500",false);
        }

        String lockName = "lockToSeckill";
        Long setnx = jedis.setnx(lockName,"lockToSeckill");
        if(0 == setnx){//设置锁不成功 即已经有锁
            System.out.println("用户"+uuid+"秒杀失败");
            jedis.close();
            return getResultMsg("网络繁忙,用户"+uuid+"秒杀失败",
                    "500",false);
        }else{
            jedis.expire(lockName,10);
            //商品剩余数量
            int suiplusGoodsAmount = Integer.parseInt(String.valueOf(jedis.get(goodsAmountField)));
            //符合条件 剩余商品自减1
            if(suiplusGoodsAmount >= 1){
                jedis.decr(goodsAmountField);
                //设置秒杀成功用户ID
                jedis.sadd(userIdsField,uuid);
                System.out.println("线程"+Thread.currentThread().getName()+"秒杀成功");
            }
            jedis.del(lockName);
            jedis.close();
            return getResultMsg("秒杀成功",
                    "200",true);
        }
    }

    //乐观锁实现秒杀
    public Map<String,Object> secKillOptimistic(String uuid){
        Map<String,Object> model = new HashMap<>();
        //登录redis
        jedis.auth("12345");
        //操作4号数据库
        jedis.select(3);
        //模拟用户ID
//        String uuid = UUID.randomUUID().toString();
        //redis存储剩余商品数量的字段
        String goodsAmountField = "goodsAmount";
        String userIdsField = "seckillUserIds";

        Boolean sismember = jedis.sismember(userIdsField,uuid);
        if(sismember){
            System.out.println("用户"+uuid+"已经秒杀过了");
            return getResultMsg("用户"+uuid+"已经秒杀过了",
                    "500",false);
        }
        String watch = jedis.watch(goodsAmountField);
        int surplus = Integer.parseInt(jedis.get(goodsAmountField));
        if(surplus > 0){
            Transaction multi = jedis.multi();
            //符合条件 自减1
            multi.decr(goodsAmountField);
            multi.sadd(userIdsField,uuid);
            multi.exec();
            String s = jedis.get(goodsAmountField);
            System.out.println("当前剩余:"+s);
            return getResultMsg("秒杀成功",
                    "200",true);
        }else{
            System.out.println("库存小于0,用户"+uuid+"秒杀失败");
            return getResultMsg("库存小于0,用户"+uuid+"秒杀失败",
                    "500",false);
        }
    }


    //封装返回信息
    public static Map<String,Object> getResultMsg(String message,String code,boolean success){
        Map<String,Object> model = new HashMap<>();
        model.put("message",message);
        model.put("code",code);
        model.put("success",success);
        return model;
    }

    //随机UUID，测试用
    public static int randomUuid(){
        return (int)(Math.random()*300 + 1);
    }

}
