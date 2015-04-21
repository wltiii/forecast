package forecast

import spock.lang.Specification
import forecast.health.TemplateHealthCheck
import forecast.resources.ForecastResource
import io.dropwizard.jersey.setup.JerseyEnvironment
import io.dropwizard.setup.Environment
import com.codahale.metrics.health.HealthCheckRegistry

class ForecastApplicationSpec extends Specification {
	
	def "validate run registers the resource"() {
		
		given: "environment has mock jersey and health check implementations"
		def mockJerseyEnvironment = Mock(JerseyEnvironment)
		def mockHealthCheckRegistry = Mock(HealthCheckRegistry)
		def mockEnvironment = Mock(Environment)
		mockEnvironment.healthChecks() >> mockHealthCheckRegistry
		mockEnvironment.jersey() >> mockJerseyEnvironment
		
		and: "a configuration for portland WA"
		ForecastConfiguration configuration = new ForecastConfiguration(defaultState: "WA", defaultCity: "Portland", defaultApiKeyLocation: '/forecast/serviceadapter/test-wunderground.properties')
		
		and: "a resource expected to be registered"
		ForecastResource expectedResource = new ForecastResource("WA", "Portland", '/forecast/serviceadapter/test-wunderground.properties')
		
		and: "a health check expected to be registered"
		TemplateHealthCheck expectedHealthCheck = new TemplateHealthCheck("Portland")
		
		when: "run is called"
		new ForecastApplication().run(configuration, mockEnvironment)
		
		then: "expect resource and health check to be registered"
		1 * mockJerseyEnvironment.register(expectedResource)
		1 * mockHealthCheckRegistry.register("template", expectedHealthCheck)
	}

}
