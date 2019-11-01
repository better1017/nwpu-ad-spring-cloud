package edu.nwpu.ad.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关键字索引对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeywordObject {

    private Long unitId;
    private String keyword;
}
