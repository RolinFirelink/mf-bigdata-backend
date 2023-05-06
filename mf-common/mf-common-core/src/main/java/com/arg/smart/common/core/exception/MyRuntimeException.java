package com.arg.smart.common.core.exception;

/**
 * @author cgli
 * @date: 2020/2/10 17:17
 */
public class MyRuntimeException extends RuntimeException {
    public MyRuntimeException(String msg) {
        super(msg);
    }

    public MyRuntimeException(Throwable cause) {
        super(cause);
    }

    public MyRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
