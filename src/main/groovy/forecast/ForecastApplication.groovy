package forecast

import forecast.resources.ForecastResource
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import forecast.health.TemplateHealthCheck
import org.joda.time.DateTime

public class ForecastApplication extends Application<ForecastConfiguration> {
    public static void main(String[] args) throws Exception {
        new ForecastApplication().run(args)
    }

    @Override
    public String getName() {
        return "forecast"
    }

    @Override
    public void initialize(Bootstrap<ForecastConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(ForecastConfiguration configuration, Environment environment) {
		println "ForecastApplication.run($configuration, environment)"
		
	    final ForecastResource resource = new ForecastResource(
	        configuration.defaultState,
	        configuration.defaultCity,
			configuration.defaultApiKeyLocation
	    )
		
	    final TemplateHealthCheck healthCheck =
	        new TemplateHealthCheck(configuration.getDefaultCity())
			
			
	    environment.healthChecks().register("template", healthCheck)
	    environment.jersey().register(resource)
    }

}