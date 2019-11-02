package edu.nwpu.ad.index.creativeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创意与推广单元关联索引对象定义与服务实现
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {
    private Long adId;
    private Long unitId;

}
