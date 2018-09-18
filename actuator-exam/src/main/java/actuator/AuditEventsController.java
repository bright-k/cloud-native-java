package actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by H on 2018. 9. 18.
 */

@RestController
@RequestMapping("/audit")
public class AuditEventsController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/hi")
    public void hi() {
        publisher.publishEvent(new AuditApplicationEvent(new AuditEvent("user", "HI_EVENT", Collections.singletonMap("say", "hi"))));
    }
}
