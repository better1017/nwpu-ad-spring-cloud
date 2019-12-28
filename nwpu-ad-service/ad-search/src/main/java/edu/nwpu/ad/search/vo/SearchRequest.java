package edu.nwpu.ad.search.vo;

import edu.nwpu.ad.search.vo.feature.DistrictFeature;
import edu.nwpu.ad.search.vo.feature.FeatureRelation;
import edu.nwpu.ad.search.vo.feature.ItFeature;
import edu.nwpu.ad.search.vo.feature.KeywordFeature;
import edu.nwpu.ad.search.vo.media.AdSlot;
import edu.nwpu.ad.search.vo.media.App;
import edu.nwpu.ad.search.vo.media.Device;
import edu.nwpu.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 包含三个部分：1.媒体方的请求标识
 *              2.一次请求所携带的基本信息
 *              3.请求匹配信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    // 媒体方的请求标识
    private String mediaId;
    // 请求基本信息
    private RequestInfo requestInfo;
    // 匹配信息
    private FeatureInfo featureInfo;

    // 一次请求所携带的基本信息
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {

        private String requestId;

        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }

    // 请求匹配信息
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo {

        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;

        // feature(特征)之间的关系，默认设置为：AND
        private FeatureRelation relation = FeatureRelation.AND;
    }
}
