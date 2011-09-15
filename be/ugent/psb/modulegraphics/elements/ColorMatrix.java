package be.ugent.psb.modulegraphics.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Draws a grid of values. The values are coloured according to the given Colorizer.
 * 
 * @author thpar
 *
 * @param <T> the type of value the matrix contains.
 */
public class ColorMatrix<T> extends Matrix<T> {

	private Colorizer<T> c;
	
	
	public ColorMatrix(T[][] data, Colorizer<T> c) {
		this.data = data;
		this.c = c;
	}


	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		int x = 0;
		int y = 0;
		Dimension unit = getUnit();
		g.setStroke(new BasicStroke());
		for (T[] row : data){
			for (T element : row){
				Color color = c.getColor(element);
				g.setColor(color);
				g.fillRect(x + xOffset, y + yOffset, unit.width, unit.height);
				g.setColor(Color.BLACK);
				g.drawRect(x + xOffset, y + yOffset, unit.width, unit.height);
				x+=unit.width;
			}
			y+=getUnit().height;
			x=0;
		}
		return getRawDimension(g);
	}
	
	
	
}
