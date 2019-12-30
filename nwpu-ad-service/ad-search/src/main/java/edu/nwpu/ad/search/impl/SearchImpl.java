package edu.nwpu.ad.search.impl;

import edu.nwpu.ad.index.DataTable;
import edu.nwpu.ad.index.adunit.AdUnitIndex;
import edu.nwpu.ad.search.ISearch;
import edu.nwpu.ad.search.vo.SaerchResponse;
import edu.nwpu.ad.search.vo.SearchRequest;
import edu.nwpu.ad.search.vo.feature.DistrictFeature;
import edu.nwpu.ad.search.vo.feature.FeatureRelation;
import edu.nwpu.ad.search.vo.feature.ItFeature;
import edu.nwpu.ad.search.vo.feature.KeywordFeature;
import edu.nwpu.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    @Override
    public SaerchResponse fetchAds(SearchRequest request) {

        // 把请求的广告位信息取出来
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        // 三个 Feature
        KeywordFeature keywordFeature =
                request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature =
                request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature =
                request.getFeatureInfo().getItFeature();

        FeatureRelation relation = request.getFeatureInfo().getRelation();

        // 构造响应对象
        SaerchResponse response = new SaerchResponse();
        Map<String, List<SaerchResponse.Creative>> adSlot2Ads =
                response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;

            // 根据流量类型获取初始的 Adunit
            Set<Long> adUnitIdSet = DataTable.of(
                    AdUnitIndex.class
            ).match(adSlot.getPositionType());
        }

        return null;
    }
}
