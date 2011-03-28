package be.ugent.psb.modulegraphics.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Simple testing element that draws a coloured rectangle of
 * given dimension in units.
 * 
 * @author thpar
 *
 */
public class Rectangle extends Element {

	private Dimension dim;
	private Color col;

	/**
	 * 
	 * @param dim in canvas units
	 * @param col color of the rectangle
	 */
	public Rectangle(Dimension dim, Color col){
		this.dim = dim;
		this.col = col;
	}
	
	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return new Dimension(dim.width*getUnit().width, dim.height*getUnit().height);
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		g.setStroke(new BasicStroke());
		g.setColor(col);
		g.fillRect(xOffset, yOffset, dim.width*getUnit().width, dim.height*getUnit().height);
		g.setColor(Color.BLACK);
		g.drawRect(xOffset, yOffset, dim.width*getUnit().width, dim.height*getUnit().height);
		return dim;
	}

}
