package it.italiaonline.rnd.charts

import org.knowm.xchart.PieChart
import org.knowm.xchart.PieChartBuilder
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle
import org.knowm.xchart.style.PieStyler.AnnotationType
import org.knowm.xchart.style.Styler.LegendPosition
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.BitmapEncoder.BitmapFormat
import org.knowm.xchart.PieSeries
import java.awt.Color
import java.awt.Font
import it.italiaonline.rnd.colors.Colors

class DonutChart {
	String           title
	Size             size
	Boolean          legend
	List<DataSource> datasources

	DonutChart (
		String           ttl,
		Size             sz,
		Boolean          lgd,
		List<DataSource> lds
	) {
		this.title       = Objects.requireNonNull(ttl)
		this.size        = Objects.requireNonNull(sz)
		this.legend      = Objects.requireNonNull(lgd)
		this.datasources = Objects.requireNonNull(lds)
		if (this.datasources.size() == 0 ) {
			throw new IllegalArgumentException("At least one datasource is required")
		}
	}

	byte[] png() {

		// Create Chart
		PieChart chart = new PieChartBuilder().with {
			width(this.size.width)
			height(this.size.height)
			if (this.title) title(this.title)
			build()
		}

		// Remove legend if there is only 1 datasource
		chart.styler.legendVisible = this.legend
		// always draw annotations
		chart.styler.drawAllAnnotations = true
		// set split of the donut
		println "live powerlistings -> ${this.datasources}"
		chart.styler.startAngleInDegrees = (this.datasources.first().yValues.first() >= 50)
		                                 ? 135.0d
		                                 : 90.0d
		// place annotation inside the chart
    chart.styler.annotationType = AnnotationType.Percentage
    // donut diameter
    chart.styler.plotContentSize = 0.8f
    // annotations distance from the chart
    chart.styler.annotationDistance = 1.22f
		// remove black border around the plot
		chart.styler.plotBorderVisible = false
    // donut instead of pie
    chart.styler.defaultSeriesRenderStyle = PieSeriesRenderStyle.Donut
		// set external frame color
		chart.styler.chartBackgroundColor = Colors.WHITE.color
		// change title font
		chart.styler.chartTitleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20)
		// set legend position
		chart.styler.legendPosition = LegendPosition.InsideNW

		this.datasources.each { ds ->
			PieSeries ps = chart.addSeries(
				ds.xValues.first(),
				ds.yValues.first()
			)
			ps.fillColor = ds.color
		}

		BitmapEncoder.getBitmapBytes (
			chart,
			BitmapFormat.PNG
		)
	}
}
