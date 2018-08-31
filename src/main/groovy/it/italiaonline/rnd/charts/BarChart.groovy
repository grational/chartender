package it.italiaonline.rnd.charts

import org.knowm.xchart.CategoryChart
import org.knowm.xchart.CategoryChartBuilder
import org.knowm.xchart.BitmapEncoder
import org.knowm.xchart.BitmapEncoder.BitmapFormat
import java.awt.Color
import java.awt.Font
import org.knowm.xchart.CategorySeries
import org.knowm.xchart.style.Styler.LegendPosition
import it.italiaonline.rnd.colors.Colors

class BarChart {
	String           title
	Size             size
	Boolean          legend
	Labels           labels
	List<DataSource> datasources

	BarChart (
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
		CategoryChart chart = new CategoryChartBuilder().with {
			width(this.size.width)
			height(this.size.height)
			if (this.title) title(this.title)
			if (this.labels.xLabel) xAxisTitle(this.labels.xLabel)
			if (this.labels.yLabel) yAxisTitle(this.labels.yLabel)
			build()
		}

		// avoid to annotate columns with the actual value
		chart.styler.hasAnnotations = false
		// remove vertical lines of the background grid
		chart.styler.plotGridVerticalLinesVisible = false
		// set external frame color
		chart.styler.chartBackgroundColor = Colors.WHITE.color
		// change title font
		chart.styler.chartTitleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20)
		// y decimal pattern
		//chart.styler.YAxisDecimalPattern = '0.#E0'
		// change format of the axis value (in case of date)
		chart.styler.datePattern = 'MM'
		// change locale for month
		chart.styler.locale = Locale.ITALIAN
		// remove black border around the plot
		chart.styler.plotBorderVisible = false
		// Remove legend if there is only 1 datasource
		chart.styler.legendVisible = this.legend
		// set legend position
		chart.styler.legendPosition = LegendPosition.InsideNW

		this.datasources.each { ds ->
			CategorySeries cs = chart.addSeries(
				ds.name,
				ds.xValues,
				ds.yValues
			)
			cs.fillColor = ds.color
		}

		BitmapEncoder.getBitmapBytes (
			chart,
			BitmapFormat.PNG
		)
	}
}
