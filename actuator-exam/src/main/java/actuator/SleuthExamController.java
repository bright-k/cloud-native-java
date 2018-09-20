package actuator;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by H on 2018. 9. 20.
 */

@RestController
@RequestMapping("/sleuth")
public class SleuthExamController {
    private final static Logger logger = LoggerFactory.getLogger(SleuthExamController.class);

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private Tracer tracer;

    private Executor executor;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void init() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setMaxPoolSize(1);
        threadPoolTaskExecutor.setThreadNamePrefix("sleuth-threadpool-");
        threadPoolTaskExecutor.initialize();

        executor = new TraceableExecutorService(beanFactory, Executors.newSingleThreadExecutor());
//        executor = new LazyTraceExecutor(beanFactory, threadPoolTaskExecutor);

//        restTemplate = new RestTemplate();
    }

    @GetMapping("/test")
    public void test(@RequestHeader Map<String, String> headers) {
        logger.info("sleuth test - " + headers);
    }

    @GetMapping("/es")
    public void es() {
        logger.info("sleuth es start");

        executor.execute(() -> {
            logger.info("sleuth es - " + Thread.currentThread().getName());
        });

        logger.info("sleuth es end");
    }

    @GetMapping("/async")
    public void async() {
        logger.info("sleuth async start");

        asyncService.asyncMethod();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }

        logger.info("sleuth async end");
    }

    @GetMapping("/restTemplate")
    public void rest() {
        logger.info("request ");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/sleuth/test", String.class);
        logger.info("response");
    }
}
