package forecast.resources

import forecast.WundergroundProperties
import forecast.core.Forecast
import forecast.core.Conditions
import spock.lang.Specification

import com.google.common.base.Optional

import forecast.resources.ForecastResource;
import forecast.serviceadapter.WundergroundClientDouble
import org.joda.time.DateTime

class ForecastResourceSpec extends Specification {
	
	ForecastResource forecastResource
	
	def setup() {
		final String apiKeyLocation = '/forecast/serviceadapter/test-wunderground.properties'
		
		forecastResource = new ForecastResource('IL', 'Chicago', apiKeyLocation, new WundergroundClientDouble())
	}
	
	def 'obtains forecast for default state and city'() {
		when:
		def forecast = forecastResource.retrieve(null, null)
		
		then:
		forecast.city == 'Chicago'
		forecast.conditions[0].date.toString() == "2012-07-03T00:00:00.000-05:00"
		forecast.conditions[0].conditions == "Clear"
		forecast.conditions[0].highTempInFahrenheit == 80
	}

	def 'obtains forecast for specific city'() {
		when:
		def forecast = forecastResource.retrieve('MN', 'Minneapolis')
		
		then:
		forecast.city == 'Minneapolis'
		forecast.conditions[0].date.toString() == "2012-07-03T00:00:00.000-05:00"
		forecast.conditions[0].conditions == "Clear"
		forecast.conditions[0].highTempInFahrenheit == 90
		forecast.conditions[1].date.toString() == "2012-07-04T00:00:00.000-05:00"
		forecast.conditions[1].conditions == "Clear"
		forecast.conditions[1].highTempInFahrenheit == 60
	}
	
	def 'object equality tests' () {
		given: 'an instance of forecast resource'
		ForecastResource firstResource = new ForecastResource("WA", "Portland", '/forecast/serviceadapter/test-wunderground.properties')
		
		and: 'another instance of the resource'
		ForecastResource secondResource = new ForecastResource("WA", "Portland", '/forecast/serviceadapter/test-wunderground.properties')
		
		expect: 'the two objects to be equal'
		firstResource == secondResource
	}

}
