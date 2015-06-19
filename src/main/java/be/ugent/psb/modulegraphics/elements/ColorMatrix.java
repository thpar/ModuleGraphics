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
