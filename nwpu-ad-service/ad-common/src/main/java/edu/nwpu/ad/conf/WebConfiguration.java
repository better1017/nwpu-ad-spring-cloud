package edu.nwpu.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 统一配置的开发
 * 消息转换器
 */
// 标注Configuration，在广告系统的其他模块引用ad-common之后，
// 所有请求的处理和消息头都会经过消息转换器去做一层过滤处理（这里消息转换器只有一个）
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    // SpringBoot主动提供的是多个消息转换器
    // 需要先进行清空，再添加需要的
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>>
                                                       converters) {
        //清空提供的转换器
        converters.clear();
        //添加需要的转换器，
        // MappingJackson2HttpMessageConverter实现将Java对象转换为JSON对象
        converters.add(new MappingJackson2HttpMessageConverter());

    }
}
