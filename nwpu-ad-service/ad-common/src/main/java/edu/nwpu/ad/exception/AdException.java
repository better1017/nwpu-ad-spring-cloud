package edu.nwpu.ad.exception;

/**
 * 自定义统一异常处理类
 * 也可以自定义其他异常处理类
 */
public class AdException extends Exception {

    public AdException(String message) {
        super(message);
    }
}
