package it.italiaonline.rnd.charts

import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.BitmapEncoder.BitmapFormat
import java.awt.Color
import java.awt.Font
import org.knowm.xchart.XYSeries
import org.knowm.xchart.style.markers.SeriesMarkers
import org.knowm.xchart.style.Styler.LegendPosition
import it.italiaonline.rnd.colors.Colors

class LineChart {
	String           title
	Size             size
	Boolean          legend
	Labels           labels
	List<DataSource> datasources

	LineChart (
		String           ttl,
		Size             sz,
		Boolean          lgd,
		Labels           lbs,
		List<DataSource> lds
	) {
		this.title       = Objects.requireNonNull(ttl)
		this.size        = Objects.requireNonNull(sz)
		this.legend      = Objects.requireNonNull(lgd)
		this.labels      = Objects.requireNonNull(lbs)
		this.datasources = Objects.requireNonNull(lds)
		if (this.datasources.size() == 0 ) {
			throw new IllegalArgumentException("At least one datasource is required")
		}
	}

	byte[] png() {

		// Create Chart
		XYChart chart = new XYChartBuilder().with {
			width(this.size.width)
			height(this.size.height)
			if (this.title) title(this.title)
			if (this.labels.xLabel) xAxisTitle(this.labels.xLabel)
			if (this.labels.yLabel) yAxisTitle(this.labels.yLabel)
			build()
		}

		// remove vertical lines of the background grid
		chart.styler.plotGridVerticalLinesVisible = false
		// set external frame color
		chart.styler.chartBackgroundColor = Colors.WHITE.color
		// change title font
		chart.styler.chartTitleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20)
		// Remove legend if there is only 1 datasource
		chart.styler.legendVisible = this.legend
		// change format of the axis value (in case of date)
		chart.styler.datePattern = 'MM'
		// y decimal pattern
		//chart.styler.YAxisDecimalPattern = '0.#E0'
		// change locale for month
		chart.styler.locale = Locale.ITALIAN
		// remove black border around the plot
		chart.styler.plotBorderVisible = false
		// legend position 
		chart.styler.legendPosition = LegendPosition.InsideNW

		def markers = [ SeriesMarkers.DIAMOND, SeriesMarkers.TRIANGLE_UP, SeriesMarkers.CIRCLE ]
		this.datasources.eachWithIndex { ds, idx ->
			XYSeries cs = chart.addSeries(
				ds.name,
				ds.xValues,
				ds.yValues
			)
			cs.lineColor         = ds.color
			cs.lineWidth         = 2.5f
			cs.markerColor       = ds.color
			cs.marker            = markers[idx % markers.size()]
		}
		BitmapEncoder.getBitmapBytes (
			chart,
			BitmapFormat.PNG
		)
	}
}
