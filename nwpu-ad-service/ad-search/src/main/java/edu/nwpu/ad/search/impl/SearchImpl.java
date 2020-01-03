package edu.nwpu.ad.search.impl;

import com.alibaba.fastjson.JSON;
import edu.nwpu.ad.index.CommonStatus;
import edu.nwpu.ad.index.DataTable;
import edu.nwpu.ad.index.adunit.AdUnitIndex;
import edu.nwpu.ad.index.adunit.AdUnitObject;
import edu.nwpu.ad.index.creative.CreativeIndex;
import edu.nwpu.ad.index.creative.CreativeObject;
import edu.nwpu.ad.index.creativeunit.CreativeUnitIndex;
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

            // 获取adUnitObject对象
            List<AdUnitObject> unitObjects =
                    DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);

            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);

            List<Long> adIds = DataTable.of(CreativeUnitIndex.class)
                    .selectAds(unitObjects);
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class)
                    .fetch(adIds);

            // 通过 AdSlot 实现对 CreativeObject 的过滤
            filterCreeativeByAdSlot(
                    creatives,
                    adSlot.getWidth(),
                    adSlot.getHeight(),
                    adSlot.getType()
            );

            adSlot2Ads.put(
                    // 返回的是随机的创意信息，返回多个用List
                    adSlot.getAdSlotCode(), buildCreativeResponse(creatives)
            );
        }

        log.info("fetchAds: {}-{}",
                JSON.toJSONString(request),
                JSON.toJSONString(response));

        return response;
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

    /**
     * 根据推广单元以及它关联的推广计划的状态，
     * 实现对传入的unitObjects进行过滤
     */
    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects,
                                           CommonStatus status) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }

        CollectionUtils.filter(
                unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    private void filterCreeativeByAdSlot(List<CreativeObject> creatives,
                                         Integer width,
                                         Integer height,
                                         List<Integer> type) {

        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        CollectionUtils.filter(
                creatives,
                creative ->
                        creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                                && creative.getWidth().equals(width)
                                && creative.getHeight().equals(height)
                                && type.contains(creative.getType())
        );
    }

    private List<SaerchResponse.Creative> buildCreativeResponse(
            List<CreativeObject> creatives
    ) {

        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }

        CreativeObject randomObject = creatives.get(
                Math.abs(new Random().nextInt()) % creatives.size()
        );

        return Collections.singletonList(
                SaerchResponse.convert(randomObject)
        );
    }
}
