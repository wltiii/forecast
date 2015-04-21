package forecast.resources

import forecast.WundergroundProperties
import forecast.core.Forecast
import forecast.core.Conditions
import forecast.serviceadapter.WundergroundClient
import groovy.transform.TailRecursive
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import com.google.common.base.Optional
import com.codahale.metrics.annotation.Timed

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.PathParam
import javax.ws.rs.core.MediaType

import java.util.concurrent.atomic.AtomicLong
import org.joda.time.DateTime

@Path("/forecast")
@Produces(MediaType.APPLICATION_JSON)
@EqualsAndHashCode
@ToString
public final class ForecastResource {
	final String defaultState
	final String defaultCity
	final String defaultApiKeyLocation
	
	private final WundergroundClient wundergroundClient

    public ForecastResource(final String defaultState, final String defaultCity, final String defaultApiKeyLocation) {
		this(defaultState, defaultCity, defaultApiKeyLocation, new WundergroundClient())
    }

    public ForecastResource(final String defaultState, final String defaultCity, final String defaultApiKeyLocation, final WundergroundClient wundergroundClient) {
    	this.defaultState = defaultState
    	this.defaultCity = defaultCity
		this.defaultApiKeyLocation = defaultApiKeyLocation
		this.wundergroundClient = wundergroundClient
    }

    @GET
    @Timed
	@Path("states/{states}/cities/{cities}")
	public Forecast retrieve(@PathParam("states") String state, @PathParam("cities") String city) {
		final String stateName = state ?: defaultState
		final String cityName = city ?: defaultCity
		
		WundergroundProperties props = WundergroundProperties.from(defaultApiKeyLocation)

		wundergroundClient.retrieveConditions(stateName, cityName, props.apiKey)
    }
}
