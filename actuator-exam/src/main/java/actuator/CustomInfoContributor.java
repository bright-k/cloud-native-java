package actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by H on 2018. 9. 18.
 */

@Service
public class CustomInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("class", this.getClass());

        Map<String, Object> map = new TreeMap<>();
        map.put("date", new Date().toInstant().toString());
        builder.withDetails(map);
    }
}
