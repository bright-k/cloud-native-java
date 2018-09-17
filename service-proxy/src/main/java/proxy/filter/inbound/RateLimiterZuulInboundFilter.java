package proxy.filter.inbound;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by H on 2018. 7. 25.
 */

@Service
@RefreshScope
public class RateLimiterZuulInboundFilter extends ZuulFilter {

    @Value("${zuul.RateLimiterFilter.enabled: true}")
    private Boolean filterEnabled;

    private final HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;

    private final RateLimiter rateLimiter;

    @Inject
    public RateLimiterZuulInboundFilter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        return filterEnabled && RequestContext.getCurrentContext().getRequest().getRequestURI().contains("greet");
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletResponse response = currentContext.getResponse();

            if(!rateLimiter.tryAcquire()) {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(tooManyRequests.value());
                response.getWriter().append(tooManyRequests.getReasonPhrase());

                currentContext.setSendZuulResponse(false);

                throw new ZuulException(tooManyRequests.getReasonPhrase(), tooManyRequests.value(), tooManyRequests.getReasonPhrase());
            }
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }
}
