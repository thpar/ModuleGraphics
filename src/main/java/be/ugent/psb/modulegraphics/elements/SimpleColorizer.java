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

import java.awt.Color;


/**
 * A simple colorizer checks if a gene is toggled on or off and colors
 * it accordingly.
 * 
 * @author Thomas Van Parys
 *
 */
public class SimpleColorizer implements Colorizer<Boolean>{
	
	private Color color;
	
	private Color offColor = Color.WHITE;
	
	public SimpleColorizer(Color color){
		this.color = color;
	}
	@Override
	public Color getColor(Boolean toggle) {
		return toggle? color:offColor;
	}

}
