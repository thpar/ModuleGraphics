package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;

/**
 * A Colorizer takes a values and determines which color to associate with it.
 * 
 * @author thpar
 *
 * @param <T> the type of value on which to base the color.
 */
public interface Colorizer<T> {

	
	public Color getColor(T element);
}
