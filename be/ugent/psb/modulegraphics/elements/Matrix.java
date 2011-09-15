package be.ugent.psb.modulegraphics.elements;

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
abstract public class Matrix<T> extends Element {

	protected T[][] data;

	

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
	abstract protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset);

	
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
