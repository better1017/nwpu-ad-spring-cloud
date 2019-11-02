package edu.nwpu.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地域限制索引对象
 * 地域限制索引对象定义
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDistrictObject {
    private Long unitId; //与推广单元id相关联
    private String province;
    private String city;

    // <String, Set<Long>>
    // province-city
}
