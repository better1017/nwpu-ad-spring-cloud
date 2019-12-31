package edu.nwpu.ad.search.impl;

import edu.nwpu.ad.index.DataTable;
import edu.nwpu.ad.index.adunit.AdUnitIndex;
import edu.nwpu.ad.index.district.UnitDistrictIndex;
import edu.nwpu.ad.index.interest.UnitItIndex;
import edu.nwpu.ad.index.keyword.UnitKeywordIndex;
import edu.nwpu.ad.search.ISearch;
import edu.nwpu.ad.search.vo.SaerchResponse;
import edu.nwpu.ad.search.vo.SearchRequest;
import edu.nwpu.ad.search.vo.feature.DistrictFeature;
import edu.nwpu.ad.search.vo.feature.FeatureRelation;
import edu.nwpu.ad.search.vo.feature.ItFeature;
import edu.nwpu.ad.search.vo.feature.KeywordFeature;
import edu.nwpu.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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

            if (relation == FeatureRelation.AND) {
                // 如果是AND该怎么过滤
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                //
                targetUnitIdSet = getORRelationUnitIds(
                        adUnitIdSet,
                        keywordFeature,
                        districtFeature,
                        itFeature
                );
            }
        }

        return null;
    }

    /**
     * OR类型的zmguolv
     */
    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,
                                           ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordUnitIdsSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIdsSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItTagFeature(itUnitIdSet, itFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdsSet, districtUnitIdSet),
                        itUnitIdSet
                )
        );
    }

    /**
     * 过滤方法
     * 关键词特征
     */
    private void filterKeywordFeature( // 关键词特征
                                       Collection<Long> adUnitIds, KeywordFeature keywordFeature) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitKeywordIndex.class)
                            .match(adUnitId,
                                    keywordFeature.getKeywords())
            );
        }
    }

    /**
     * 过滤方法
     * 地域特征
     */
    private void filterDistrictFeature( // 地域过滤
                                        Collection<Long> adUnitIds, DistrictFeature districtFeature
    ) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitDistrictIndex.class)
                            .match(adUnitId,
                                    districtFeature.getDistricts())
            );
        }
    }

    /**
     * 过滤方法
     * 兴趣特征
     */
    private void filterItTagFeature( // 兴趣过滤
                                     Collection<Long> adUnitIds, ItFeature itFeature
    ) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitItIndex.class)
                            .match(adUnitId,
                                    itFeature.getIts())
            );
        }
    }
}
