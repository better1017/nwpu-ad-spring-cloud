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

    /**
     * 校验
     * 定义一些返回值为布尔类型的方法，用来实现判断positionType是否满足要求
     **/
    private static boolean isKaiPing(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.KAIPING) > 0;
    }

    private static boolean isTIEPIAN(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN) > 0;
    }

    private static boolean isTIEPIANMiddle(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE) > 0;
    }

    private static boolean isTIEPIANPAUSE(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE) > 0;
    }

    private static boolean isTIEPIANPOST(int positionType) {
        return (positionType & AdUnitConstants.POSITION_TYPE.TIEPIAN_POST) > 0;
    }

    /**
     * 定义一个总的方法，实现统一校验
     **/
    public static boolean isAdSlotTypeOK(int adSlotType, int positionType) {

        switch (adSlotType) {
            case AdUnitConstants.POSITION_TYPE.KAIPING:
                return isKaiPing(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN:
                return isTIEPIAN(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE:
                return isTIEPIANMiddle(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE:
                return isTIEPIANPAUSE(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_POST:
                return isTIEPIANPOST(positionType);
            default:
                return false;
        }
    }
}
