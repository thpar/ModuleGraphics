package be.ugent.psb.modulegraphics.elements;

/*
 * #%L
 * ModuleGraphics
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Draws a grid of values. The values are coloured according to the given Colorizer.
 * 
 * @author Thomas Van Parys
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
