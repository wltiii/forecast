package forecast.core

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.ToString
import groovy.transform.Canonical

@ToString
@Canonical
final class Forecast {
    @JsonProperty
    String state

    @JsonProperty
    String city

    @JsonProperty
    List<Conditions> conditions

}
