package edu.nwpu.ad.search;

import edu.nwpu.ad.search.vo.SaerchResponse;
import edu.nwpu.ad.search.vo.SearchRequest;

/**
 * 用于广告的检索请求
 */
public interface ISearch {

    /**
     * 定义服务接口
     * 媒体方发起请求，传递进来一个request对象，
     * 检索服务根据request对象返回一个response对象
     */
    SaerchResponse fetchAds(SearchRequest request);
}
