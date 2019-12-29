package edu.nwpu.ad.search.vo;

import edu.nwpu.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SaerchResponse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaerchResponse {

    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    /**
     * 广告创意内部类
     * 创意数据信息，返回给媒体方的
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {

        private Long adId; //广告id
        private String adUrl; //广告的url
        private Integer width; //广告的宽度
        private Integer height; //广告的高度
        private Integer type; //广告的类型
        private Integer materialType; //广告的物料类型

        // 展示监测 url：广告返回给媒体方后，媒体方会对广告数据进行曝光（曝光就是将广告数据展示给用户）
        private List<String> showMonitorUrl =
                Arrays.asList("www.nwpu.edu.cn", "www.nwpu.edu.cn");
        // 点击监测 url
        private List<String> clickMonitorUrl =
                Arrays.asList("www.nwpu.edu.cn", "www.nwpu.edu.cn");
    }

    /**
     * 将索引对象转换成定义的Creative对象
     */
    public static Creative convert(CreativeObject object) {

        Creative creative = new Creative();

        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());

        return creative;
    }
}
