package proxy.filter.outbound;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletResponseWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by H on 2018. 7. 25.
 */

@Service
@RefreshScope
public class AddResponseHeaderFilter extends ZuulFilter {

    @Value("${zuul.AddResponseHeaderFilter.enabled:true}")
    private Boolean filterEnabled;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return filterEnabled && RequestContext.getCurrentContext().getRequest().getMethod().equalsIgnoreCase("POST");
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        response.addHeader("X-Random-UUID", UUID.randomUUID().toString());

        return null;
    }
}
