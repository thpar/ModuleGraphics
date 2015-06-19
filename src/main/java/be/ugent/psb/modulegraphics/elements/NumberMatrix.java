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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class NumberMatrix extends Matrix<Integer> {
	
	private Color foregroundColor = Color.BLACK;
	private Color backgroundColor = Color.WHITE;
	
	private Font font;
	
	public NumberMatrix(Integer[][] data) {
		this.data = data;
		setFont(new Font("SansSerif", Font.PLAIN, 12));
	}


	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		int x = 0;
		int y = 0;
		Dimension unit = getUnit();
		g.setStroke(new BasicStroke());
		g.setFont(this.font);
		for (Integer[] row : data){
			for (Integer element : row){				
				g.setColor(backgroundColor);
				g.fillRect(x + xOffset, y + yOffset, unit.width, unit.height);
				g.setColor(Color.BLACK);
				g.drawRect(x + xOffset, y + yOffset, unit.width, unit.height);
				if (element!=null){
					g.setColor(foregroundColor);
					FontRenderContext frc = g.getFontRenderContext();
					TextLayout layout = new TextLayout(element.toString(), this.font, frc);
					Rectangle2D dim = layout.getBounds();
					int xAlign = (int)Math.floor((unit.width - dim.getWidth())/2);
					int yAlign = (int)Math.floor((unit.height - dim.getHeight())/2+dim.getHeight());
					g.drawString(element.toString(), x+xOffset+xAlign, y+yOffset+yAlign);
				}
				x+=unit.width;
			}
			y+=getUnit().height;
			x=0;
		}
		return getRawDimension(g);
	}


	public Color getForegroundColor() {
		return foregroundColor;
	}


	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}


	public Color getBackgroundColor() {
		return backgroundColor;
	}


	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	
	private void setFont(Font font) {
		this.font = font;
		
	}
	public Font getFont() {
		return font;
	}

}
