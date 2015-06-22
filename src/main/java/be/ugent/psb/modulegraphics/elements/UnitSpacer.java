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
 * A {@link Spacer} that takes its dimensions in Units.
 * 
 * @author Thomas Van Parys
 *
 */
public class UnitSpacer extends Spacer {
	
	private int unitX = 0;
	private int unitY = 0;

	/**
	 * 
	 * @param unitX with in units
	 * @param unitY height in units
	 */
	public UnitSpacer(int unitX, int unitY){
		this.unitX = unitX;
		this.unitY = unitY;
	}
	
	
	private Dimension getUnitDim(Graphics2D g) {
		Dimension unit = this.getUnit();
		return new Dimension(unitX * unit.width, unitY * unit.height);
	}
	
	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (accent ){
			g.fillRect(xOffset, yOffset, 5, 5);
		}
		return getUnitDim(g);
	}


	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return getUnitDim(g);
	}
	
}
