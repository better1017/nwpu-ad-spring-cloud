package edu.nwpu.ad.client;


import edu.nwpu.ad.client.vo.AdPlan;
import edu.nwpu.ad.client.vo.AdPlanGetRequest;
import edu.nwpu.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * SponsorClient接口
 * feign只需要一个接口就可以实现微服务之间的调用
 */
@FeignClient(value = "eureka-client-ad-sponsor",
        fallback = SponsorClientHystrix.class) //服务降级
public interface SponsorClient {
    // 已经指定了AdSponsor，还需要指定调用AdSponsor的哪一个接口
    @RequestMapping(value = "/ad-sponsor/get/adPlan",
            method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(
            @RequestBody AdPlanGetRequest request);
}
