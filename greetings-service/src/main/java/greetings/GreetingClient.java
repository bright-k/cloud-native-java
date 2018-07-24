package greetings;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by H on 2018. 7. 23.
 */

@FeignClient(name = "greeting-service", url = "http://localhost:8081")
public interface GreetingClient {

    @RequestMapping(method = RequestMethod.GET, value = "/greet/{name}")
    Map<String, Object> greet(@PathVariable("name") String name);

    @RequestMapping(method = RequestMethod.POST, value = "/greet")
    Map<String, Object> greetPost(Map<String, Object> map);
}
