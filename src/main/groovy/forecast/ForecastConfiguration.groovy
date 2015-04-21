package forecast

import java.util.List

import io.dropwizard.Configuration

import com.fasterxml.jackson.annotation.JsonProperty

import org.hibernate.validator.constraints.NotEmpty

import groovy.transform.ToString
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable

@EqualsAndHashCode
@Immutable
@ToString
public class ForecastConfiguration extends Configuration {
    @NotEmpty
	@JsonProperty
    String defaultState = "WA"

    @NotEmpty
	@JsonProperty
    String defaultCity = "Seattle"
	
    @NotEmpty
	@JsonProperty
	String defaultApiKeyLocation = '/forecast/serviceadapter/wunderground.properties'

}