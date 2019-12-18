package com.hupubao.workbook.exception;
/**
 *
 * @author Moses
 * @date 2017-11-09 16:06:03
 * @description 连接超时异常
 */
public class TimeoutException extends RuntimeException {

    public TimeoutException() {
    }

    public TimeoutException(String message) {
        super(message);
    }
}
