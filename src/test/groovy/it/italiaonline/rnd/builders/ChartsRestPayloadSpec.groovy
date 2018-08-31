package it.italiaonline.rnd.builders

import spock.lang.Specification
import spock.lang.Shared
import spock.lang.Unroll

import java.awt.Color
import static groovy.json.JsonOutput.toJson

import it.italiaonline.rnd.charts.Size
import it.italiaonline.rnd.charts.Labels
import it.italiaonline.rnd.charts.SupportedCharts
import it.italiaonline.rnd.charts.DataSource
import it.italiaonline.rnd.time.TimeGranularity
import it.italiaonline.rnd.time.TimeSeries

/**
 * Build a json representation of the json post payload
 * needed to query the charts rest service
 */
class ChartsRestPayloadSpec extends Specification {

	@Shared
	String  defaultTitle        = ''
	@Shared
	SupportedCharts defaultType = SupportedCharts.LINECHART
	@Shared
	Size    defaultChartSize    = new Size(800,600)
	@Shared
	Labels  defaultAxisLabels   = new Labels('','')
	@Shared
	Boolean defaultLegend       = false
	// colors
	@Shared Color heritageGreen  = new Color(142, 216, 0  )
	@Shared Color creativeYellow = new Color(255, 206, 6  )
	@Shared Color digitalBlue    = new Color( 32, 195, 243)
	@Shared Color darkBlue       = new Color( 43,   0, 82 )
	@Shared Color passionRed     = new Color(255,   0, 92 )
	@Shared Color coolGray       = new Color(140, 140, 140)
	@Shared Color black          = new Color(  0,   0,   0)
	// time series
	@Shared
	List xValues = new TimeSeries (
		Date.parse('y-MM','2016-04'),
		Date.parse('y-MM','2017-04'),
		'y-MM',
		TimeGranularity.MONTH
	).series()
	@Shared
	List yValues = [10, 1, 5, 3, 9, 4, 1, 4, 7, 10, 1, 5]
	@Shared
	DataSource ds1 = new DataSource (
										'ds1',
										xValues,
										yValues,
										passionRed
									)
	@Shared
	List defaultDataSources = [ds1]
	@Shared
	Map defaultPayload = [
				title: defaultTitle,
				type: defaultType.name().toLowerCase(),
				legend: defaultLegend,
				dataSources: defaultDataSources*.map()
			] + defaultChartSize.map() + defaultAxisLabels.map()

	def "Should not raise any exceptions if all the parameters are valid ones"() {
		when:
			def chartPayload = new ChartsRestPayload (
													defaultTitle,
													defaultType,
													defaultChartSize,
													defaultAxisLabels,
													defaultLegend,
													defaultDataSources
												 )
		then:
			noExceptionThrown()
	}

	@Unroll
	def "Should raise an exception if one of the parameters are null"() {
		when:
			def chartPayload = new ChartsRestPayload (
													title,
													type,
													chartSize,
													axisLabels,
													legend,
													dataSources
												 )
		then:
			thrown(NullPointerException)

		where:
			title        | type        | chartSize        | axisLabels        | legend        | dataSources
			null         | defaultType | defaultChartSize | defaultAxisLabels | defaultLegend | defaultDataSources
			defaultTitle | null        | defaultChartSize | defaultAxisLabels | defaultLegend | defaultDataSources
			defaultTitle | defaultType | null             | defaultAxisLabels | defaultLegend | defaultDataSources
			defaultTitle | defaultType | defaultChartSize | null              | defaultLegend | defaultDataSources
			defaultTitle | defaultType | defaultChartSize | defaultAxisLabels | null          | defaultDataSources
			defaultTitle | defaultType | defaultChartSize | defaultAxisLabels | defaultLegend | null
	}

	def "Should return the correct json representation using default values"() {
		when:
			def chartPayload = new ChartsRestPayload (
													defaultTitle,
													defaultType,
													defaultChartSize,
													defaultAxisLabels,
													defaultLegend,
													defaultDataSources
												 )
		then:
			toJson(defaultPayload) == chartPayload.json()
	}
}
