package edu.nwpu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeUnitTable {

    //这两个id唯一的标识了一个CreativeUnit对象
    private Long adId;
    private Long unitId;
}
