package edu.nwpu.ad.client;

import edu.nwpu.ad.client.vo.AdPlan;
import edu.nwpu.ad.client.vo.AdPlanGetRequest;
import edu.nwpu.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SponsorClientHystrix：断路器
 */
@Component //声明为一个组件
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(
            AdPlanGetRequest request) {
        return new CommonResponse<>(1,
                "eureka-client-ad-sponsor error");
    }
}
