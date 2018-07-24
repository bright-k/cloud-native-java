package greetings;

import com.netflix.config.ConfigurationManager;
import feign.Feign;
import feign.Target;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.ribbon.LoadBalancingTarget;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by H on 2018. 7. 23.
 */

@RestController
public class FeignController {

    private GreetingClient greetingClient;

    private GreetingClient2 greetingClient2;

    @Inject
    FeignController(GreetingClient greetingClient) {
        this.greetingClient = greetingClient;

        ConfigurationManager.getConfigInstance().setProperty("greeting-service" + ".ribbon.listOfServers", "http://localhost:8082,http://localhost:8081");
        LoadBalancingTarget<GreetingClient2> target = LoadBalancingTarget.create(GreetingClient2.class, "http://greeting-service");
        System.out.println(target.lb().getLoadBalancerStats());
        greetingClient2 = Feign.builder()
                                .encoder(new GsonEncoder())
                                .decoder(new GsonDecoder())
                                .target(target);
    }

    @GetMapping("/api/{name}")
    public Map<String, Object> api(@PathVariable String name) {
        return greetingClient.greet(name);
    }

    @PostMapping(value = "/api2")
    public Map<String, Object> api2(@RequestBody Map<String, Object> map) {
        return greetingClient.greetPost(map);
    }

    @GetMapping("/api3/{name}")
    public Map<String, Object> api3(@PathVariable String name) {
        return greetingClient2.greet(name);
    }

    @PostMapping(value = "/api4")
    public Map<String, Object> api4(@RequestBody Map<String, Object> map) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return greetingClient2.greetPost(headers, map);
    }
}
