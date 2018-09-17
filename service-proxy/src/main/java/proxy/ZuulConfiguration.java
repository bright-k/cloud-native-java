package proxy;

import com.google.common.util.concurrent.RateLimiter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by H on 2018. 7. 24.
 */


@Configuration
public class ZuulConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(RouteLocator routeLocator) {
        Logger log = LoggerFactory.getLogger(getClass());
        return args -> routeLocator.getRoutes().forEach(r -> log.info(String.format("-------> %s (%s) %s", r.getId(), r.getLocation(), r.getFullPath())));
    }

    @Bean
    RateLimiter rateLimiter() {
        return RateLimiter.create(1.0D / 5.0D);
    }

//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
//        return registry -> registry.config().commonTags("h", "test");
//    }
}
