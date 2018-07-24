package greetings;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by H on 2018. 7. 23.
 */

@RestController
public class GreetingController {


    @GetMapping("/greet/{name}")
    public Map<String, Object> greet(@PathVariable String name) {
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);

        return map;
    }

    @PostMapping(value = "/greet", consumes = "application/json")
    public Map<String, Object> greetPost(@RequestBody Map<String, Object> map) {

        return map;
    }
}
