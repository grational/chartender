package it.italiaonline.rnd.charts

class Size {
	private final Integer width
	private final Integer height

	Size (
		Integer w,
		Integer h
	) {
		this.width  = Objects.requireNonNull(w)
		this.height = Objects.requireNonNull(h)
	}

	Map map() {
		[
			width: this.width,
			height: this.height
		]
	}
}
