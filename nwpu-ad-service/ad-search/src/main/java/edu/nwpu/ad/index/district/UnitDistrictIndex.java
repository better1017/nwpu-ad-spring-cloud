package edu.nwpu.ad.index.district;

import edu.nwpu.ad.index.IndexAware;
import edu.nwpu.ad.search.vo.feature.DistrictFeature;
import edu.nwpu.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * 地域限制索引对象
 * 地域限制索引服务实现
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    // 正向索引
    private static Map<String, Set<Long>> districtUnitMap;
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndex, before add: {}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getorCreate(
                    unitId, unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
        }
        log.info("UnitDistrictIndex, after add: {}", unitDistrictMap);
    }

    @Override
    public void update(String key, Set<Long> value) {

        log.error("district index can not support update.");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndex, before delete: {}", unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getorCreate(
                key, districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getorCreate(
                    unitId, unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districtUnitMap.remove(key);
        }
        log.info("UnitDistrictIndex, after delete: {}", unitDistrictMap);
    }

    /**
     * district的match方法
     **/
    public boolean match(Long adUnitId,
                         List<DistrictFeature.ProvinceAndCity> districts) {
        if (unitDistrictMap.containsKey(adUnitId) &&
                CollectionUtils.isNotEmpty(unitDistrictMap.get(adUnitId))) {
            Set<String> unitDistricts = unitDistrictMap.get(adUnitId);

            List<String> targetDistricts = districts.stream()
                    .map(
                            d -> CommonUtils.stringConcat(
                                    d.getProvince(), d.getCity()
                            )
                    ).collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts, unitDistricts);
        }

        return false;
    }
}
