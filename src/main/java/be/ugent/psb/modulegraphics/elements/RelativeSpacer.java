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
 * {@link Element} to keep an empty spot on the {@link Canvas}. It will follow the height and/or
 * width of another {@link Element}.
 * 
 * @author Thomas Van Parys
 *
 */
public class RelativeSpacer extends Spacer {

	private Element widthElement;
	private Element heightElement;
	
	public RelativeSpacer(){
		
	}
	
	
	/**
	 * 
	 * @param widthElement element to be tracking in width
	 * @param heightElement element to be tracking in height
	 */
	public RelativeSpacer(Element widthElement, Element heightElement){
		this.widthElement = widthElement;
		this.heightElement = heightElement;
	}
	
	
	public Element getWidthElement() {
		return widthElement;
	}

	public void setWidthElement(Element widthElement) {
		this.widthElement = widthElement;
	}

	public Element getHeightElement() {
		return heightElement;
	}

	public void setHeightElement(Element heightElement) {
		this.heightElement = heightElement;
	}

	private Dimension getRelativeDim(Graphics2D g) {
		int width = 0;
		if (this.widthElement==null && dim !=null){
			width = this.dim.width;
		} else {
			width = this.widthElement.getDimension(g).width;
		}
		int height = 0;
		if (this.heightElement==null && dim !=null){
			height = this.dim.height;
		} else {
			height = this.heightElement.getDimension(g).height;
		}
		return new Dimension(width, height);
	}
	
	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (accent ){
			g.fillRect(xOffset, yOffset, 5, 5);
		}
		return getRelativeDim(g);
	}


	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return getRelativeDim(g);
	}
	
}
