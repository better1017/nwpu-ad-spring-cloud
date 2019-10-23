package edu.nwpu.ad.dao;

import edu.nwpu.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AdPlan的查询接口
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    /**
     * <h2>根据id和userId查询AdPlan</h2>
     */
    AdPlan findAdPlanByIdAndUserId(Long id, Long userId);

    /**
     * 根据Id列表和UserId查询，一个UserId可能对应多个AdPlan
     */
    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    /**
     * 根据UserId和PlanName查询，能够唯一确定一个AdPlan
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据状态查询一个AdPlan列表
     */
    List<AdPlan> findAllByPlanStatus(Integer status);
}
