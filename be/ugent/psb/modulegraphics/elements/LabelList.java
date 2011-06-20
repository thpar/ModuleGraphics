package be.ugent.psb.modulegraphics.elements;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A LabelList takes a List of Strings and draws them next to or under eachother
 * depending on the given direction.
 * 
 * @author thpar
 *
 */
public class LabelList extends Element{

	private List<Label> labels = new ArrayList<Label>();
	
	
	public enum Direction{
		LEFT_TO_RIGHT, TOP_TO_BOTTOM;
	}
	
	public enum Angle{
		STRAIGHT, SKEWED;
	}
	
	private Direction dir = Direction.LEFT_TO_RIGHT;
	private Angle angle = Angle.STRAIGHT;
//	private Font font;
	
	private boolean pushBounds = true;
	
	public LabelList(List<String> labelStrings){
		for (String labelString : labelStrings){
			Label newLabel = new Label(labelString);
			labels.add(newLabel);
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
//			label.setFont(font);
			
			Dimension dim = label.getDimension(g);
			maxWidth = Math.max(maxWidth, dim.width);
			maxHeight = Math.max(maxHeight, dim.height);	
		}
		int totalWidth = 0;
		int totalHeight = 0;
		
		switch(dir){
		case LEFT_TO_RIGHT:
			totalWidth = labels.size()* this.getUnit().width;
			if (pushBounds){
//				totalWidth = totalWidth - this.getUnit().width + maxWidth;
			}
			totalHeight = maxHeight;
			break;
		case TOP_TO_BOTTOM:
			totalHeight = labels.size() * this.getUnit().height;
			if (pushBounds){
//				totalHeight = totalHeight - this.getUnit().height +maxHeight;
			}
			totalWidth = maxWidth;
			break;
		}
		return new Dimension(totalWidth, totalHeight);
		
	}

	@Override
	public Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		int x = 0;
		int y = 0;
		
		int maxWidth = 0;
		int maxHeight = 0;
		
		for (Label label : labels){
			
//			label.setFont(font);
			this.setLabelAngle(label);
			label.paint(g, x + xOffset, y + yOffset);
			switch(dir){
			case LEFT_TO_RIGHT:
				x+=this.getUnit().width;
				break;
			case TOP_TO_BOTTOM:
				y+=this.getUnit().height;
				break;
			}
			
			Dimension dim = label.getDimension(g);
			maxWidth = Math.max(maxWidth, dim.width);
			maxHeight = Math.max(maxWidth, dim.height);	
		}
		
		int totalWidth = 0;
		int totalHeight = 0;
		
		switch(dir){
		case LEFT_TO_RIGHT:
//			totalWidth = labels.size()* (this.getUnit().width-1) + maxWidth;
			totalWidth = labels.size()* (this.getUnit().width);
			totalHeight = maxHeight;
			break;
		case TOP_TO_BOTTOM:
//			totalHeight = labels.size()* (this.getUnit().height-1) + maxHeight;
			totalHeight = labels.size()* (this.getUnit().height);
			totalWidth = maxWidth;
			break;
		}
		
		return new Dimension(totalWidth, totalHeight);
	}
	
	
	private void setLabelAngle(Label label){
		switch(angle){
		case SKEWED:
			label.setAngle(Math.PI/4);
			break;
		case STRAIGHT:
			switch(dir){
			case LEFT_TO_RIGHT:
				label.setAngle(Math.PI/2);				
				break;
			case TOP_TO_BOTTOM:
				label.setAngle(0);
				break;
			}
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
	
}
