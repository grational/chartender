package it.italiaonline.rnd.charts

import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.BitmapEncoder.BitmapFormat
import java.awt.Color
import java.awt.Font
import org.knowm.xchart.XYSeries
import org.knowm.xchart.style.markers.SeriesMarkers
import org.knowm.xchart.style.lines.SeriesLines
import it.italiaonline.rnd.colors.Colors

class EmptyChart {
	String           title
	Size             size
	List<DataSource> datasources

	EmptyChart (
		String           ttl,
		Size             sz,
		List<DataSource> lds
	) {
		this.title       = Objects.requireNonNull(ttl)
		this.size        = Objects.requireNonNull(sz)
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
			build()
		}

		// remove vertical lines of the background grid
		chart.styler.plotGridVerticalLinesVisible = false
		// hide x axis from the graph
		chart.styler.XAxisTicksVisible = false
		// set external frame bg color
		chart.styler.chartBackgroundColor = Colors.LIGHT_GRAY.color
		// set internal plot bg color
		chart.styler.plotBackgroundColor  = Colors.LIGHT_GRAY.color
		// change title font
		chart.styler.chartTitleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20)
		// Remove legend if there is only 1 datasource
		chart.styler.legendVisible = false
		// change format of the axis value (in case of date)
		chart.styler.datePattern = 'MM'
		// change locale for month
		chart.styler.locale = Locale.ITALIAN
		// remove black border around the plot
		chart.styler.plotBorderVisible = false
  
		this.datasources.eachWithIndex { ds, idx ->
			XYSeries cs = chart.addSeries(
				ds.name,
				ds.xValues,
				ds.yValues
			)
			cs.lineStyle = SeriesLines.NONE
			cs.marker    = SeriesMarkers.NONE
		}
		BitmapEncoder.getBitmapBytes (
			chart,
			BitmapFormat.PNG
		)
	}
}
