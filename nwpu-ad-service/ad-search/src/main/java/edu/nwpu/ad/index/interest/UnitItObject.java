package edu.nwpu.ad.index.interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 兴趣限制维度
 * 兴趣索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitItObject {

    private Long unitId;
    private String itTag;

}
