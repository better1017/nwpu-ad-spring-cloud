package edu.nwpu.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * search应用的启动类
 */
@EnableFeignClients //使用feign去调用
@EnableEurekaClient
@EnableHystrix //断路器（熔断器）
@EnableCircuitBreaker //断路器（熔断器）
@EnableDiscoveryClient //开启微服务的发现能力
@EnableHystrixDashboard //Hystrix监控
@SpringBootApplication
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

    /**
     * 通过ribbon调用广告投放系统的接口
     */
    //定义rest客户端
    @Bean
    @LoadBalanced
    //能够开启负载均衡的能力，如果有多个投放系统，则轮询，实现负载均衡
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
