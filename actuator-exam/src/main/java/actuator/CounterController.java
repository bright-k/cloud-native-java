package actuator;

import com.google.common.util.concurrent.AtomicDouble;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by H on 2018. 9. 16.
 */

@RestController
public class CounterController {

    private final MeterRegistry meterRegistry;
    private final Counter counter;
    private final AtomicDouble gauge;

    @Autowired
    public CounterController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        counter = meterRegistry.counter("countercontroller.get.count");
        gauge = Metrics.gauge("countercontroller.get.gauge", new ArrayList<>(5), new AtomicDouble(0));
    }

    @GetMapping("get")
    public void get() {
        counter.increment();
        gauge.set(counter.count());
    }
}
