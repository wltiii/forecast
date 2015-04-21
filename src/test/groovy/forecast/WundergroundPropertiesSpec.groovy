package forecast

import spock.lang.Specification

class WundergroundPropertiesSpec extends Specification {
	
	def "loads property file"() {
		given:
		def props = WundergroundProperties.from('/forecast/serviceadapter/wunderground.properties')
		
		expect:
		props.apiKey == '<your api key>'
	}

	def "loads property test file"() {
		given: 'path to test properties file'
		def props = WundergroundProperties.from('/forecast/serviceadapter/test-wunderground.properties')
		
		expect:
		props.apiKey.startsWith '10e0'
	}

}
