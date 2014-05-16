package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;


/**
 * Element that draws a single String under any given angle.
 * The label is rendered in given font, within a box of height 1 unit and a width to fit the entire string (before rotation).
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
	
	private Color backgroundColor;
	
	
	
	/**
	 * Create a horizontal label with default font.
	 * 
	 * @param text
	 */
	public Label(String text){
		this.labelString = text;
		setFont(new Font("SansSerif", Font.PLAIN, 12));
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
		int unitHeight = this.getUnit().height;
		
		double a = getRotationAngle();
		int rotatedWidth = (int)Math.ceil(Math.abs(unitHeight*Math.sin(a)) + Math.abs(adv*Math.cos(a)));
		int rotatedHeight = (int)Math.ceil(Math.abs(adv*Math.sin(a)) + Math.abs(unitHeight*Math.cos(a)));
		
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
		int unitHeight = this.getUnit().height;
		
		int bottomPadding = 2;
		
		double rotation = getRotationAngle();
		
		double yTranslate = yOffset + Math.abs(adv*Math.sin(rotation));
		
		g.translate(xOffset, yTranslate);
		g.rotate(rotation);
		
		//put colored marker on the text (not the whole element)
		if (this.backgroundColor!=null){
			Color previousColor = g.getColor();
			g.setColor(this.backgroundColor);
			g.fillRect(0, unitHeight-bottomPadding-textHeight, adv,	textHeight);
			g.setColor(previousColor);
		}
		
		g.setFont(font);
		if (highlighted){
			g.setColor(highlightedColor);
		} else{
			g.setColor(color);
		}


		g.drawString(labelString, 0, unitHeight-bottomPadding-dsc);
		
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
