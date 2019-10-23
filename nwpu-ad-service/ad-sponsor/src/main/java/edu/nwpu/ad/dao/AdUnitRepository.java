package edu.nwpu.ad.dao;

import edu.nwpu.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AdUnit查询接口
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    AdUnit findByPlanIdAndUnitName(Long planId, Long unitName);

    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
