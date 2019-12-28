package edu.nwpu.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    // 广告位编码
    private String adSlotCode;

    // 流量类型
    private Integer positionType;

    // 广告位的宽和高
    private Integer width;
    private Integer height;

    // 广告的物料类型（可以支持多个物料类型）
    private List<Integer> type;

    // 最低出价
    private Integer minCpm;
}
