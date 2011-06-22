package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;

public class PassThroughColorizer implements Colorizer<Color> {

	Color defaultColor = Color.WHITE;
	
	@Override
	public Color getColor(Color color) {
		if (color==null) return defaultColor;
		else return color;
	}

}
