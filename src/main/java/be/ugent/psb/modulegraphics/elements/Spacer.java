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

/**
 * Empty element to keep space in the grid of a Canvas. A Spacer can have 
 * dimensions to keep at least a certain amount of space.
 * 
 * @author thpar
 *
 */
public class Spacer extends Element {

	
	protected Dimension dim;
	/**
	 * Set a little mark in the corner of the spacer, for debugging purposes.
	 */
	protected boolean accent = false;

	public Spacer(){
		dim = new Dimension();
	}
	
	public Spacer(Dimension dim){
		this.dim = dim;
	}
	

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (accent ){
			g.fillRect(xOffset, yOffset, 5, 5);
		}
		return dim;
	}

	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return dim;
	}

	public void setDimension(Dimension dim) {
		this.dim = dim;
	}
	
	
}
