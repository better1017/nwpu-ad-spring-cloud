package edu.nwpu.ad.index.adplan;

import edu.nwpu.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 索引实现类
 * 索引服务实现：实现对索引的增删改查
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    private static Map<Long, AdPlanObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    /**
     * get方法，通过key获取到AdPlanObject，查询
     */
    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    /**
     * 添加一个索引，添加
     * */
    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    /**
     * 更新一个索引，修改
     * */
    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("before update: {}", objectMap);
        AdPlanObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    /**
     * 删除一个索引，删除
     * */
    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
