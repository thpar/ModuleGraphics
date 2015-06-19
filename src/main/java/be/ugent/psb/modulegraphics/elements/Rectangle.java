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
		if (highlighted){
			g.setColor(Color.RED);
			g.drawRect(xOffset, yOffset, dim.width*getUnit().width, dim.height*getUnit().height);
		}
		return dim;
	}


	public Color getColor() {
		return col;
	}

}
