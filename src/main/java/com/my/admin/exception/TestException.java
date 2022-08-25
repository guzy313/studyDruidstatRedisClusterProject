package com.my.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义异常
 * @author Gzy
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "测试错误1")
public class TestException extends RuntimeException{
    public TestException() {
    }
    public TestException(String message) {
        super(message);
    }
}
