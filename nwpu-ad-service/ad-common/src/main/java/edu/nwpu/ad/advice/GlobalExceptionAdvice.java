package edu.nwpu.ad.advice;

import edu.nwpu.ad.exception.AdException;
import edu.nwpu.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServlet;

/**
 * 统一的异常处理
 * 异常处理Advice类
 * 异常处理也是一种响应，需要对异常进行响应处理
 * 使用的也是CommonResponse，异常也要统一的作响应
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    // 发生异常时会传入两个参数
    // 1.发生异常的请求 2.抛出的异常
    // 这样就可以知道是哪个请求抛出了异常
    // 注解是对exception进行handler，handler的目标是AdException类
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerADException(HttpServlet req,
                                                     AdException ex) {
        CommonResponse<String> response =
                new CommonResponse<>(-1,"business error");
        response.setData(ex.getMessage());
        return response;
    }
}
