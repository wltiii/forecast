package forecast

import forecast.resources.ForecastResource;
import forecast.serviceadapter.WundergroundClientDouble
import forecast.core.Forecast
import forecast.core.Conditions
import specification.ResourceSpec
import spock.lang.Specification
import spock.lang.Shared

class ForecastApiSpec extends ResourceSpec {
	@Shared Forecast suggestedActivities
	
	@Override
	void setUpResources() {
		final String apiKeyLocation = '/forecast/serviceadapter/test-wunderground.properties'
		final ForecastResource forecastResource = new ForecastResource('IL', 'Chicago', apiKeyLocation, new WundergroundClientDouble())
		jersey.addResource(forecastResource)
		
	}
	
	def 'GET request returns suggestion for a city'() {
		when:
		def result = jersey.client().resource('/forecast/states/IL/cities/Chicago').get(Forecast) 
		
		then:
		// TODO validate more than just the city name
		result.city == 'Chicago'
	}

}
