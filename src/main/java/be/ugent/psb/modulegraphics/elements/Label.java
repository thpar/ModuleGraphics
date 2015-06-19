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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;


/**
 * Element that draws a single String under any given angle.
 * 
 * @author thpar
 *
 */
public class Label extends Element {

	private String labelString;
	private Font font;
	private double angle = 0;
	
	private Color color = Color.BLACK;
	private Color highlightedColor = Color.RED;
	private Font normalFont;
	
	private int boxHeight;
	
	private Color backgroundColor;
	
	
	
	/**
	 * Create a horizontal label with default font.
	 * 
	 * @param text
	 */
	public Label(String text){
		this.labelString = text;
		setFont(new Font("SansSerif", Font.PLAIN, 12));
		setBoxHeight(this.getUnit().height);
	}
	
	/**
	 * Set the box height of the label. By default, this is 1 unit and this height is maintained 
	 * in possible rotations. 
	 * 
	 * For vertical labels, it might be desirable to set the box height to the unit width.
	 * 
	 * @param height
	 */
	public void setBoxHeight(int height) {
		this.boxHeight = height;
	}

	public Label(String text, Font font){
		this.labelString = text;
		setFont(font);
	}
	
	
	public String getLabelString() {
		return labelString;
	}


	public void setLabelString(String labelString) {
		this.labelString = labelString;
	}


	private double getRotationAngle() {
		if (angle< -Math.PI/2) return angle+Math.PI;
		else if (angle> Math.PI/2) return angle-Math.PI;
		else return angle;
	}

	public Font getFont(){
		return font;
	}


	public double getAngle() {
		return angle;
	}

	
	

	/**
	 * Angle in radials. Given angle is rescaled to fall between -PI and +PI
	 * 
	 * @param angle
	 */
	public void setAngle(double angle) {
		angle = angle % (Math.PI*2);
		if (Math.abs(angle)>Math.PI){
			angle = angle>0? angle-Math.PI*2:angle+Math.PI*2;
		}
		this.angle = angle;
	}


	/**
	 * Set the font of this label. Default font size is 12px. When changing font size, take
	 * into account that the label height is fixed on 1 unit.
	 * @param font
	 */
	public void setFont(Font font) {
		this.font = font;
	}



	@Override
	public Dimension getRawDimension(Graphics2D g) {
		if (labelString==null || labelString.isEmpty()) return new Dimension(0,0);
		

		FontMetrics metrics = g.getFontMetrics(font);
		
		int adv = metrics.stringWidth(this.labelString);
		
		double a = getRotationAngle();
		int rotatedWidth = (int)Math.ceil(Math.abs(boxHeight*Math.sin(a)) + Math.abs(adv*Math.cos(a)));
		int rotatedHeight = (int)Math.ceil(Math.abs(adv*Math.sin(a)) + Math.abs(boxHeight*Math.cos(a)));
		
		Dimension rotatedDim = new Dimension(rotatedWidth, rotatedHeight);
		return rotatedDim;
	}

	@Override
	public Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (labelString==null || labelString.isEmpty()) return new Dimension(0,0);
		
		FontMetrics metrics = g.getFontMetrics(font);

		
		int adv = metrics.stringWidth(this.labelString);
		int dsc = metrics.getMaxDescent();
		int textHeight = metrics.getHeight();
		
		double rotation = getRotationAngle();
		
		double yTranslate = yOffset + Math.abs(adv*Math.sin(rotation));
		
		g.translate(xOffset, yTranslate);
		g.rotate(rotation);
		
		//put colored marker on the text (not the whole element)
		if (this.backgroundColor!=null){
			Color previousColor = g.getColor();
			g.setColor(this.backgroundColor);
			g.fillRect(0, boxHeight-textHeight, adv,	textHeight);
			g.setColor(previousColor);
		}
		
		g.setFont(font);
		if (highlighted){
			g.setColor(highlightedColor);
		} else{
			g.setColor(color);
		}


		g.drawString(labelString, 0, boxHeight-dsc);
		
		g.rotate(-rotation);
		g.translate(-xOffset, -yTranslate);
				
		return getRawDimension(g);
	}

	public String getString(){
		return labelString;
	}
	
	@Override
	public void setHighlighted(boolean highlighted){
		super.setHighlighted(highlighted);
		
		if (highlighted){
			normalFont = this.font;
			setFont(this.font.deriveFont(Font.ITALIC));
		} else {
			setFont(normalFont);
		}
		refresh();
	}

	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getHighlightedColor() {
		return highlightedColor;
	}

	public void setHighlightedColor(Color highlightedColor) {
		this.highlightedColor = highlightedColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	
	

}
