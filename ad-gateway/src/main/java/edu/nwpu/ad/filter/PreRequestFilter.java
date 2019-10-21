package edu.nwpu.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 打印访问日志
 * 把filter当作JavaBean注册到容器中
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    @Override
    public String filterType() {//定义filter的类型
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {//定义filter执行的顺序，数字越小顺序越高
        return 0;
    }

    @Override
    public boolean shouldFilter() {//表示是否执行这个过滤器，true为执行，false为不执行
        return true;
    }

    @Override
    public Object run() throws ZuulException {//filter需要执行的具体操作
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime", System.currentTimeMillis());
        return null;
    }
}
