package edu.nwpu.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 尝试在检索系统中通过代码的形式，
 * 微服务调用去获取广告投放系统AdPlan的一些信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanGetRequest {

    private Long userId;
    private List<Long> ids;

}
