package edu.nwpu.ad.advice;

import edu.nwpu.ad.annotation.IgnoreResponseAdvice;
import edu.nwpu.ad.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应处理的代码
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    // 两种情况不会使用CommonResponse，
    // 1.类上注解了IgnoreResponseAdvice
    // 2.方法上注解了IgnoreResponseAdvice
    // IgnoreResponseAdvice注解是自己定义的，见edu.nwpu.ad.annotation.IgnoreResponseAdvice
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        // 通过methodParameter的声明方法拿到类的声明，
        // 如果类的声明被注解标识，我们就不想他被CommonResponse影响
        if (methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }

        if (methodParameter.getMethod().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }

        return true;
    }

    // 将CommonResponse进行拦截
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o, //o代表返回对象
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        // 默认情况code=0，message=""，代表没有问题的响应
        CommonResponse<Object> response = new CommonResponse<>(0, "");

        if (o == null) {//如果对象为null，直接返回，代表对象里面data为空
            return response;
        } else if (o instanceof CommonResponse) {//如果o直接是一个CommonResponse对象，就不需要多加处理
            response = (CommonResponse<Object>) o;
        } else {//o是一个普通的返回对象，需要包装成CommonResponse
            response.setData(o);
        }

        return response;
    }
}
