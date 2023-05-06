package com.arg.smart.common.core.exception;

/**
 * @author cgli
 * @date: 2021/8/12 11:45
 */
public class UtilException extends RuntimeException {
    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
