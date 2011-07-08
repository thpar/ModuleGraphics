package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
		tx.rotate(this.getRotationAngle());
		return font.deriveFont(tx);
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



	public void setFont(Font font) {
		this.font = font;
	}



	@Override
	public Dimension getRawDimension(Graphics2D g) {
		if (labelString==null || labelString.isEmpty()) return new Dimension(0,0);
		
		FontRenderContext frc = g.getFontRenderContext();

		TextLayout layout = new TextLayout(labelString, getDerivedFont(), frc);
		Rectangle2D dim = layout.getBounds();
				
		int width  = (int)Math.round(Math.floor(dim.getWidth())+1);
		int height = (int)Math.round(Math.floor(dim.getHeight())+1);
		return new Dimension(width, height);
	}

	@Override
	public Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (labelString==null || labelString.isEmpty()) return new Dimension(0,0);
		
		Dimension bounds = getDimension(g);
		
		FontRenderContext frc = g.getFontRenderContext();
		Font df = getDerivedFont();
		
		TextLayout layout = new TextLayout(labelString, df, frc);
		Rectangle2D layoutBounds = layout.getBounds();
		
		
		g.setFont(df);
		if (highlighted){
			g.setColor(highlightedColor);
		} else{
			g.setColor(color);
		}

		
		layout.draw(g, (float)(xOffset - layoutBounds.getX()), (float)(yOffset - layoutBounds.getY()));

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
