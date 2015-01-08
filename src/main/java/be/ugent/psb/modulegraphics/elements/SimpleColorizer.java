package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;


/**
 * A simple colorizer checks if a gene is toggled on or off and colors
 * it accordingly.
 * 
 * @author thpar
 *
 */
public class SimpleColorizer implements Colorizer<Boolean>{
	
	private Color color;
	
	private Color offColor = Color.WHITE;
	
	public SimpleColorizer(Color color){
		this.color = color;
	}
	@Override
	public Color getColor(Boolean toggle) {
		return toggle? color:offColor;
	}

}
