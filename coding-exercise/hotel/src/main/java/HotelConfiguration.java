import com.agoda.model.Rate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import java.util.Map;

class HotelConfiguration extends Configuration {
    @JsonProperty
    private Rate globalRateLimit;

    @JsonProperty
    private Map<String, Rate> rateLimitMap;

    @JsonProperty
    private String hoteldb;

    @JsonProperty
    private Integer suspendTimeInMinutes;

    public HotelConfiguration() {
    }

    Map<String, Rate> getRateLimitMap() {
        return rateLimitMap;
    }

    Rate getGlobalRateLimit() {
        return globalRateLimit;
    }

    String getHoteldb() {
        return hoteldb;
    }

    Integer getSuspendTimeInMinutes() {
        return suspendTimeInMinutes;
    }

}
