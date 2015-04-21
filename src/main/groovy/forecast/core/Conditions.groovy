package forecast.core

import groovy.transform.Canonical
import groovy.transform.ToString
import org.joda.time.DateTime
import com.fasterxml.jackson.annotation.*

@ToString
@Canonical
final class Conditions {
	
	String date
	int highTempInFahrenheit
	int lowTempInFahrenheit
	String conditions
	int rainTotalInInches
	int snowTotalInInches
	int averageWindInMilesPerHour
	int maxWindInMilesPerHour
	int averageHumidity
	int maxHumidity

}
