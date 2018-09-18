package actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by H on 2018. 9. 18.
 */

@RestController
@RequestMapping("${management.endpoints.web.base-path:/actuator}")
public class Custom1HealthIndicator implements HealthIndicator {

    private final AtomicReference<Health> health = new AtomicReference<>(Health.up().build());

    @Override
    public Health health() {
        System.out.println("CustomHealthIndicator");
        return health.get();
    }

    @PutMapping("${management.endpoints.web.path-mapping.health:health}/up")
    public Health up() {
        Health up = Health.up().build();
        this.health.set(up);
        return up;
    }

    @PutMapping("${management.endpoints.web.path-mapping.health:health}/down")
    public Health down() {
        Health down = Health.down().build();
        this.health.set(down);
        return down;
    }
}
