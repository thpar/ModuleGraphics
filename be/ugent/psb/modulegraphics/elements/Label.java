package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;


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



	public Font getDerivedFont() {
		AffineTransform tx = new AffineTransform();
		tx.rotate(angle);
		return font.deriveFont(tx);
	}

	public Font getFont(){
		return font;
	}


	public double getAngle() {
		return angle;
	}



	public void setAngle(double angle) {
		this.angle = angle;
	}



	public void setFont(Font font) {
		this.font = font;
	}



	@Override
	public Dimension getRawDimension(Graphics2D g) {
		FontRenderContext frc = g.getFontRenderContext();

		TextLayout layout = new TextLayout(labelString, getDerivedFont(), frc);
		Rectangle2D dim = layout.getBounds();
				
		int width  = (int)Math.round(Math.floor(dim.getWidth())+1);
		int height = (int)Math.round(Math.floor(dim.getHeight())+1);
		return new Dimension(width, height);
	}

	@Override
	public Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		Dimension bounds = getDimension(g);
		FontRenderContext frc = g.getFontRenderContext();
		FontMetrics metrics = g.getFontMetrics(getDerivedFont());
		int ascend = metrics.getAscent();
		int descend = metrics.getDescent();
		
		TextLayout layout = new TextLayout(labelString, getDerivedFont(), frc);

		g.setFont(getDerivedFont());
		if (highlighted){
			g.setColor(highlightedColor);
		} else{
			g.setColor(color);
		}
		layout.draw(g, xOffset, yOffset + ascend - descend);
		
		
		return bounds;
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

	

}
