// internal
import it.italiaonline.rnd.charts.Size
import it.italiaonline.rnd.charts.Labels
import it.italiaonline.rnd.charts.DataSource
import it.italiaonline.rnd.charts.BarChart
import it.italiaonline.rnd.charts.LineChart
import it.italiaonline.rnd.charts.DonutChart
import it.italiaonline.rnd.charts.EmptyChart
import it.italiaonline.rnd.charts.SupportedCharts
// external
import java.awt.Color
import groovy.json.JsonSlurper
import static ratpack.groovy.Groovy.ratpack

ratpack {
	handlers {
		post ("charts") {
			request.body.then { postData ->
				def json  = new JsonSlurper().parseText(
					postData.text
				)
				def type   = json.type.toUpperCase() as SupportedCharts
				def title  = json.title ?: ''
				def legend = json.legend
				def labels = new Labels(json.xLabel ?: '', json.yLabel ?: '')
				def datasources = json.dataSources.collect { ds ->
					new DataSource(
						ds.name,
						ds.x,
						ds.y,
						new Color(
							ds.color.rgb[0],
							ds.color.rgb[1],
							ds.color.rgb[2]
						)
					)
				}
				def chart
				if ( type == SupportedCharts.BARCHART ) {
					chart = new BarChart (
						title,
						new Size(json.width,json.height),
						legend,
						labels,
						datasources
					)
				} else if ( type == SupportedCharts.LINECHART ) {
					chart = new LineChart (
						title,
						new Size(json.width,json.height),
						legend,
						labels,
						datasources
					)
				} else if ( type == SupportedCharts.DONUTCHART ) {
					chart = new DonutChart (
						title,
						new Size(json.width,json.height),
						legend,
						datasources
					)
				} else if ( type == SupportedCharts.EMPTYCHART ) {
					chart = new EmptyChart (
						title,
						new Size(json.width,json.height),
						datasources
					)
				}
				response.send 'image/png', chart.png()
			}
		}
	}
}
