package it.italiaonline.rnd.colors

import java.awt.Color

enum Colors {
	HERITAGE_GREEN  (new Color( 142, 216, 0   )),
	CREATIVE_YELLOW (new Color( 255, 206, 6   )),
	DIGITAL_BLUE    (new Color(  32, 195, 243 )),
	DARK_BLUE       (new Color(  43,   0, 82  )),
	PASSION_RED     (new Color( 255,   0, 92  )),
	COOL_GRAY       (new Color( 140, 140, 140 )),
	LIGHT_GRAY      (new Color( 229, 229, 229 )),
	WHITE           (new Color( 255, 255, 255 )),
	BLACK           (new Color(   0,   0,   0 ))

	private final Color color

	private Colors(Color clr) {
		this.color = clr
	}

	Color getColor() {
		this.color
	}
}
