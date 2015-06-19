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
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A LabelList takes a List of Strings and draws them next to or under eachother
 * depending on the given direction.
 * 
 * @author thpar
 *
 */
public class LabelList extends Element{

	private List<Label> labels = new ArrayList<Label>();
	private Map<String, Label> nameToLabel = new HashMap<String, Label>();
	
	/**
	 * List labels next to or below eachother
	 * @author thpar
	 *
	 */
	public enum Direction{
		LEFT_TO_RIGHT, TOP_TO_BOTTOM;
	}
	
	/**
	 * Draw labels straight or under an angle
	 * @author thpar
	 *
	 */
	public enum Angle{
		STRAIGHT, SKEWED;
	}
	
	public enum Alignment{
		LEFT, RIGHT, TOP, BOTTOM, CENTER;
	}
	
	/**
	 * Direction to turn your head while reading
	 * @author thpar
	 *
	 */
	public enum ReadingAngle{
		LEFT, RIGHT;
	}
	
	private Direction dir = Direction.LEFT_TO_RIGHT;
	private Angle angle = Angle.STRAIGHT;
	private ReadingAngle rAngle = ReadingAngle.RIGHT;
	private Alignment labelAlignment = Alignment.LEFT;		
	private boolean pushBounds = true;
	
	public LabelList(List<String> labelStrings){
		for (String labelString : labelStrings){
			Label newLabel = new Label(labelString);
			labels.add(newLabel);
			nameToLabel.put(labelString, newLabel);
			newLabel.setParentElement(this);
		}
		setFont(new Font("SansSerif", Font.PLAIN, 12));
	}
	
	public void setFont(Font font) {
//		this.font = font;
		for (Label l : labels){
			l.setFont(font);
		}
	}
	
	public Direction getDirection() {
		return dir;
	}


	public void setDirection(Direction dir) {
		this.dir = dir;
	}


	public void setAngle(Angle angle) {
		this.angle = angle;
	}

	
	public Angle getAngle() {
		return angle;
	}
	
		

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	
	
	public ReadingAngle getrAngle() {
		return rAngle;
	}

	public void setrAngle(ReadingAngle rAngle) {
		this.rAngle = rAngle;
	}

	public boolean isPushBounds() {
		return pushBounds;
	}

	public void setPushBounds(boolean pushBounds) {
		this.pushBounds = pushBounds;
	}

	@Override
	public Dimension getRawDimension(Graphics2D g) {
		
		int maxWidth = 0;
		int maxHeight = 0;
		
		for (Label label : labels){
			this.setLabelAngle(label);
		
			Dimension dim = label.getDimension(g);
			maxWidth = Math.max(maxWidth, dim.width);
			maxHeight = Math.max(maxHeight, dim.height);	
		}
		int totalWidth = 0;
		int totalHeight = 0;
		
		switch(dir){
		case LEFT_TO_RIGHT:
			totalWidth = labels.size()* this.getUnit().width;
//			if (pushBounds){
//				totalWidth = totalWidth - this.getUnit().width + maxWidth;
//			}
			totalHeight = maxHeight;
			break;
		case TOP_TO_BOTTOM:
			totalHeight = labels.size() * this.getUnit().height;
//			if (pushBounds){
//				totalHeight = totalHeight - this.getUnit().height +maxHeight;
//			}
			totalWidth = maxWidth;
			break;
		}
		return new Dimension(totalWidth, totalHeight);
		
	}

	@Override
	public Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		Dimension rawDim = this.getRawDimension(g);
		
		int x = 0;
		int y = 0;
		
		
		for (Label label : labels){
			
			this.setLabelAngle(label);
			Dimension labelDim = label.getRawDimension(g);
			int xAlignOffset = 0;
			int yAlignOffset = 0;
			
			switch(dir){
			case LEFT_TO_RIGHT:
				switch(this.labelAlignment){
				case RIGHT:
				case BOTTOM:
					yAlignOffset=rawDim.height-labelDim.height;
					break;
				case LEFT:
				case TOP:
				case CENTER:
				}
				break;
			case TOP_TO_BOTTOM:
				switch(this.labelAlignment){
				case RIGHT:
				case BOTTOM:
					xAlignOffset=rawDim.width-labelDim.width;
					break;
				case LEFT:
				case TOP:
				case CENTER:
				}
				break;
			}
			
			
			
			label.paint(g, x + xOffset + xAlignOffset, y + yOffset + yAlignOffset);
			switch(dir){
			case LEFT_TO_RIGHT:
				x+=this.getUnit().width;
				break;
			case TOP_TO_BOTTOM:
				y+=this.getUnit().height;
				break;
			}
			
	
		}
		

		return new Dimension(rawDim.width, rawDim.height);
	}
	
	
	private void setLabelAngle(Label label){
		switch(angle){
		case SKEWED:
			switch(rAngle){
			case LEFT:
				label.setAngle(-Math.PI/4);
				break;
			case RIGHT:
				label.setAngle(Math.PI/4);
				break;				
			}
			break;
		case STRAIGHT:
			switch(dir){
			case LEFT_TO_RIGHT:
				label.setBoxHeight(this.getUnit().width);
				switch(rAngle){
				case LEFT:
					label.setAngle(-Math.PI/2);				
					break;
				case RIGHT:
					label.setAngle(Math.PI/2);				
					break;
				}
				break;
			case TOP_TO_BOTTOM:
				label.setBoxHeight(this.getUnit().height);
				label.setAngle(0);
				break;
			}
			break;
		}
	}

	public Integer getHitLabelRow(int x, int y) {
		Point relCoord = absoluteToRelative(new Point(x,y));
		switch(dir){
		case LEFT_TO_RIGHT:
			return relCoord.x / getUnit().width;
		case TOP_TO_BOTTOM:
			return relCoord.y / getUnit().height;
		default: return -1;
		}
		
	}
	
	public String getHitLabelString(int x, int y){
		int hitRow = getHitLabelRow(x, y);
		return labels.get(hitRow).getString();
	}


	public void setHighlightedLabels(List<String> highlighted){
		for (Label l : labels){
			if (highlighted.contains(l.getString())) {
				l.setHighlighted(true);
			} else {
				l.setHighlighted(false);
			}
		}
	}
	public void setLabelHighlight(String label, boolean highlighted){
		for (Label l : labels){
			if (l.getString().equals(label)) l.setHighlighted(true);
		}
	}
	public void removeHighlights(){
		for (Label l : labels){
			l.setHighlighted(false);
		}
	}

	public void toggleLabelHighlight(String name) {
		for (Label l : labels){
			if (l.getString().equals(name)) l.toggleHighlighted();
		}
	}

	public Alignment getLabelAlignment() {
		return labelAlignment;
	}

	public void setLabelAlignment(Alignment labelAlignment) {
		this.labelAlignment = labelAlignment;
	}
	
	public void colorBackgrounds(List<String> names, Color color){
		for (String name : names){
			Label l = nameToLabel.get(name);
			if (l!=null){
				l.setBackgroundColor(color);
			}
		}
	}
	public void resetBackgrounds(){
		for (Label l: labels){
			l.setBackgroundColor(null);
		}
	}
	
}
