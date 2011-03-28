package be.ugent.psb.modulegraphics.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Draws a grid of values. The values are coloured according to the given Colorizer.
 * 
 * @author thpar
 *
 * @param <T> the type of value the matrix contains.
 */
public class Matrix<T> extends Element {

	private T[][] data;
	private Colorizer<T> c;
	
	
	public Matrix(T[][] data, Colorizer<T> c) {
		this.data = data;
		this.c = c;
	}

	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		if (data.length != 0 && data[0].length!=0){
			Dimension unit = this.getUnit();
			return new Dimension(unit.width * data[0].length, unit.height * data.length);
		} else {
			return new Dimension();
		}
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
	
	/**
	 * Returns the location in the data matrix that has been hit.
	 * 
	 * @param x absolute click coordinates X-axis
	 * @param y absolute click coordinates Y-axis
	 * @return a {@link Point} with {@link Point}.x the hit column and {@link Point}.y the hit row.  
	 */
	public Point getHitSquare(int x, int y){
		Point abs = absoluteToRelative(new Point(x, y));
		int col = abs.x/getUnit().width;
		int row = abs.y/getUnit().height;
		return new Point(col,row);
	}
	
	/**
	 * Returns 
	 * 
	 * @param x absolute click coordinates X-axis
	 * @param y absolute click coordinates Y-axis
	 * @return data clicked upon
	 */
	public T getHitData(int x, int y){
		Point hitPoint = getHitSquare(x, y);
		int col = hitPoint.x;
		int row = hitPoint.y;
		return data[row][col];
	}
	
	
}
