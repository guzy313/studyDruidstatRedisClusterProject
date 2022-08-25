package com.my.admin.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gzy
 * @version 1.0
 */
@Component
public class MyComHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Map<String,Object> map = new HashMap<>();
        if(true){//此处为判断条件,测试写死true
            map.put("测试",123);
            builder.status(Status.UP);
        }else{
            map.put("err","错误111");
            builder.status(Status.OUT_OF_SERVICE);
            //builder.status(Status.DOWN);
        }
        builder.withDetail("code",100)
                .withDetails(map);
    }
}
