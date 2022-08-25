package com.my.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * @author Gzy
 * @version 1.0
 */
public class Test1 {

    @Test
    void testAssertions(){
        Assertions.assertAll("testAll1",
                ()->Assertions.assertTrue(false,"不为真"),
                ()->Assertions.assertEquals(1,2,"不相等"));
    }

    @DisplayName("异常断言")
    @Test
    void testAssertionsThrows(){
        Assertions.assertThrows(ArithmeticException.class,
                ()->{int a = 1/1;},"自定义-不抛出异常提示");
    }

    @DisplayName("超时断言")
    @Test
    void testAssertionsTimeout(){
        Assertions.assertTimeout(Duration.ofMillis(1000),
                ()->{Thread.sleep(1200);},"超时");
    }

    @DisplayName("快速失败")
    @Test
    void testAssertionsFail(){
        Assertions.fail("快速失败");
    }


}
