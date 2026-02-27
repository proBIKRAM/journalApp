package nec.bikram.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    public static class Current{
        @JsonProperty("feelslike")
        private int feelsLike;
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int temperature;
    }
}
