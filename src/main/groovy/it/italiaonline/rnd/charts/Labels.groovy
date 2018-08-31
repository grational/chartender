package it.italiaonline.rnd.charts

class Labels {
	private final String xLabel
	private final String yLabel

	Labels ( 
		String x,
		String y
	) {
		this.xLabel = Objects.requireNonNull(x)
		this.yLabel = Objects.requireNonNull(y)
	}

	Map map() {
		[
			xLabel: this.xLabel,
			yLabel: this.yLabel
		]
	}
}
