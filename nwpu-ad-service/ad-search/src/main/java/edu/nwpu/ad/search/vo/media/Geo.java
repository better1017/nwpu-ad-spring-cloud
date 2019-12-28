package edu.nwpu.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    private Float latitude; //纬度
    private Float longitude; //经度

    private String city; //城市
    private String province; //省份
}
