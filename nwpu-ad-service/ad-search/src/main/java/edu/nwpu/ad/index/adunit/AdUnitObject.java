package edu.nwpu.ad.index.adunit;

import edu.nwpu.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdUnitObject: 推广单元索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Integer unitStatus;
    private Integer positionType; //核心过滤条件
    private Long planId; //与推广计划进行关联

    /**
     * 定义推广单元对推广计划索引对象的一个引用
     */
    private AdPlanObject adPlanObject;

    // 更新一个推广单元索引对象
    void update(AdUnitObject newObject) {
        if (null != newObject.getUnitId()) {
            this.unitId = newObject.getUnitId();
        }
        if (null != newObject.getUnitStatus()) {
            this.unitStatus = newObject.getUnitStatus();
        }
        if (null != newObject.getPositionType()) {
            this.positionType = newObject.getPositionType();
        }
        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }
    }
}
