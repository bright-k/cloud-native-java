package actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

/**
 * Created by H on 2018. 9. 18.
 */

@RestController
public class EmotionalIndicator extends AbstractHealthIndicator {
    @Autowired
    private ApplicationEventPublisher publisher;

    private EmotionalEvent event;

    private Date occurredOn;

    @EventListener
    public void onEmotional(EmotionalEvent emotionalEvent) {
        this.event = emotionalEvent;
        occurredOn = new Date();
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        System.out.println("EmotionalIndicator");
        Optional.ofNullable(event)
                .ifPresent(evt -> {
                    Class<? extends EmotionalEvent> eventClass = event.getClass();
                    Health.Builder healthBuilder = eventClass.isAssignableFrom(HappyEvent.class) ? builder.up() : builder.down();
                    healthBuilder.withDetail("emotion", eventClass.isAssignableFrom(HappyEvent.class) ? "HAPPY" : "SAD").withDetail("class", eventClass).withDetail("occurredOn", occurredOn.toInstant().toString());
                });
    }

    public interface EmotionalEvent {

    }

    public class HappyEvent implements EmotionalEvent {

    }

    public class SadEvent implements EmotionalEvent {

    }

    @PutMapping("emotional/happy")
    public void happy() {
        publisher.publishEvent(new HappyEvent());
    }

    @PutMapping("emotional/sad")
    public void sad() {
        publisher.publishEvent(new SadEvent());
    }
}
