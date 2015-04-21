package forecast.serviceadapter

import spock.lang.Specification
import spock.lang.Ignore
import forecast.WundergroundProperties
import forecast.core.Forecast
import forecast.serviceadapter.WundergroundClientDouble
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import groovyx.net.http.*

class WundergroundClientSpec extends Specification {
	
	WundergroundClient client = new WundergroundClientDouble()

	def "returns 10 day forecast"() {
		given:
		WundergroundProperties props = WundergroundProperties.from('/forecast/serviceadapter/test-wunderground.properties')
		
		when:
		Forecast forecast = client.retrieveConditions("IL", "Chicago", props.apiKey)
		
		then:
		forecast.conditions.size() == 10
		forecast.conditions[0].date == (new DateTime(2012, 7, 3, 0, 0, DateTimeZone.forID("America/Chicago"))) as String
		forecast.conditions[0].highTempInFahrenheit == 80
		forecast.conditions[0].lowTempInFahrenheit == 55
		forecast.conditions[0].conditions == "Clear"
		forecast.conditions[0].rainTotalInInches == 0
		forecast.conditions[0].snowTotalInInches == 0
		forecast.conditions[0].averageWindInMilesPerHour == 14
		forecast.conditions[0].maxWindInMilesPerHour == 17
		forecast.conditions[0].averageHumidity == 72
		forecast.conditions[0].maxHumidity == 82
	}

	@Ignore("used to test drive api call using RESTClient")
	def "make a GET request"() {
		given:
		def client = new RESTClient('http://api.wunderground.com')
		
		when:
		def resp = client.get( path : '/api/10e025b23fe1df3b/forecast10day/q/CA/San_Francisco.json') 
		
		then:
		resp.status == 200
		println "uri is ${client.getUri().toString()}"
		println "resp.contentType is ${resp.contentType}"
		println "resp.data is ${resp.data}"
		println "year is ${resp.data.forecast.simpleforecast.forecastday[0].date.year}"
		
	}
}
