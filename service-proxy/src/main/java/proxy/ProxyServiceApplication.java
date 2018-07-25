package proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by H on 2018. 7. 24.
 */

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ProxyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyServiceApplication.class, args);
    }

}
