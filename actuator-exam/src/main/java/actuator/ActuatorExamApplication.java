package actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by H on 2018. 7. 24.
 */

@EnableDiscoveryClient
@SpringBootApplication
public class ActuatorExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorExamApplication.class, args);
    }

}
