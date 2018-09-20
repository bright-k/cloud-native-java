package actuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by H on 2018. 9. 20.
 */

@Service
public class AsyncService {
    private final static Logger logger = LoggerFactory.getLogger(AsyncService.class);

    @Async
    public void asyncMethod() {
        logger.info("asyncMethod start");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }

        logger.info("asyncMethod end");
    }
}
