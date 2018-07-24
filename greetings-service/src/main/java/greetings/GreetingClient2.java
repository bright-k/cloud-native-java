package greetings;

import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

/**
 * Created by H on 2018. 7. 24.
 */
public interface GreetingClient2 {
    @RequestLine("GET /greet/{name}")
    @Headers({
            "Content-Type: application/json"
    })
    Map<String, Object> greet(@Param("name") String name);

    @RequestLine("POST /greet")
    Map<String, Object> greetPost(@HeaderMap Map<String, Object> headers, Map<String, Object> map);
}
