package forecast.serviceadapter

import forecast.WundergroundProperties
import forecast.core.Conditions
import forecast.core.Forecast
import groovy.transform.Immutable
import groovy.transform.TailRecursive
import groovyx.net.http.*

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class WundergroundClient {

	Forecast retrieveConditions(final String state, final String city, String apiKey) {
		println "WundergroundClient.retrieveConditions($state, $city, $apiKey)"
		String apiPath = buildPath(state, city, apiKey)
		
		final def json = retrieveFromProvider(apiPath)
		
		println "WundergroundClient.retrieveConditions() -> json is ${json.toString()}"

		def final node = json.forecast.simpleforecast.forecastday
		
		final List<Conditions> conditions = buildDailyConditions(node, node.size(), new ArrayList<Conditions>()).sort { it.date }
		
		new Forecast(state: state, city: city, conditions: conditions)
	}
	
	private String buildPath(final String state, final String city, final String apiKey) {
		def apipath = "/api/${apiKey}/forecast10day/q/${state}/${city}.json"
		return apipath
	}

	@TailRecursive
	private List<Conditions> buildDailyConditions(json, int period, List<Conditions> dailyConditions) {
		if (period == 0) {
			dailyConditions
		}
		else {
			final def node = json[period - 1]
			def condition = new Conditions(
				date: new DateTime(node.date.year, node.date.month, node.date.day, 0, 0, DateTimeZone.forID(node.date.tz_long)),
				highTempInFahrenheit: node.high.fahrenheit as Integer,
				lowTempInFahrenheit: node.low.fahrenheit as Integer,
				conditions: node.conditions,
				rainTotalInInches: node.qpf_allday.in as Integer,
				snowTotalInInches: node.snow_allday.in as Integer,
				averageWindInMilesPerHour: node.avewind.mph as Integer,
				maxWindInMilesPerHour: node.maxwind.mph as Integer,
				averageHumidity: node.avehumidity as Integer,
				maxHumidity: node.maxhumidity as Integer
			)
			buildDailyConditions(json, --period, dailyConditions << condition)
		}
	} 
	
	def retrieveFromProvider(String apiPath) {
		println "WundergroundClient.retrieveFromProvider($apiPath)"
		def client = new RESTClient('http://api.wunderground.com')
		def resp = client.get( path : apiPath)
		println "WundergroundClient.retrieveFromProvider.resp --> $resp"
		resp.data
	}

}
