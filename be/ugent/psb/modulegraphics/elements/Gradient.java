package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Element that draws a continuous gradient based on a {@link Colorizer<Double>}
 * 
 * @author Thomas Van Parys
 *
 */
public class Gradient extends Element{

	private Colorizer<Double> c;

	private int width = 10;
	private int height = 1;
	
	private double min;
	private double max;
	
	
	public Gradient(double min, double max, Colorizer<Double> c){
		this.c = c;
		this.min = min;
		this.max = max;
	}
	
	/**
	 * 
	 * @return width (in units)
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @return height (in units)
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set width (in units)
	 * @param width width (in units)
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * Set height (in units)
	 * @param height height (in units)
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		
		int pixelWidth = this.width * this.getUnit().width;
		double value = min;
		double range = max - min;
		double valueStep = range/pixelWidth;
		
		for (int i=0; i<=pixelWidth; i++){
			g.setColor(c.getColor(value));
			g.drawLine(xOffset+i, yOffset, xOffset+i, yOffset+this.getUnit().height*height);
			value+=valueStep;
		}

		g.setPaint(Color.BLACK);
		g.drawRect(xOffset, yOffset, width * this.getUnit().width, height * this.getUnit().height);
		return this.getRawDimension(g);
	}

	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return new Dimension(width * this.getUnit().width, height * this.getUnit().height);
	}

	
}
