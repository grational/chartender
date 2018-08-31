package it.italiaonline.rnd.charts

import java.awt.Color
import java.awt.Font

class DataSource {
	String name
	List   xValues
	List   yValues
	Color  color

	DataSource ( 
		String nm,
		List   x,
		List   y,
		Color  clr
	) {
		this.name    = Objects.requireNonNull(nm)
		this.xValues = Objects.requireNonNull(x)
		if ( areDates(this.xValues) )
			this.xValues = dates(this.xValues)
		this.yValues = Objects.requireNonNull(y)
		this.color   = Objects.requireNonNull(clr)
	}

	private Boolean areDates(List<String> values) {
		def results = values.collect {
			it ==~ /20[01][0-9]-(?:0[1-9]|1[0-2])/
		}
		!(false in results)
	}

	private List<Date> dates(
		List<String> stringDates
	) {
		stringDates.collect { date ->
			Date.parse("y-MM", date)
		}
	}

	Map map() {
		def x = (this.xValues.first() in Date)
					? this.xValues*.format('y-MM')
					: this.xValues
		[
			name: this.name,
			color: [
				rgb: [
					this.color.red,
					this.color.green,
					this.color.blue
				]
			],
			x: x,
			y: this.yValues
		]
	}
}
