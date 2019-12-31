package edu.nwpu.ad.index;

import lombok.Getter;

/**
 * 定义枚举类，
 * 标记推广单元和推广计划是有效状态还是无效状态
 */
@Getter
public enum CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
