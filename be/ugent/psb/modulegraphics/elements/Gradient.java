package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Element that draws a continuous gradient over a given array of
 * checkpoints, based on a {@link Colorizer<Double>}
 * 
 * At least a start and end point should be given.
 * 
 * @author Thomas Van Parys
 *
 */
public class Gradient extends Element{

	private Colorizer<Double> c;

	private List<CheckPoint> checkPoints = new ArrayList<CheckPoint>();
	private Font font = new Font("SansSerif", Font.PLAIN, 12);
	private int width = 10;
	private int height = 1;
	
	private boolean dataChanged = false;

	private boolean paintLabels = true;
	
	/**
	 * Value increment per pixel
	 */
	private double valueStep;
	
	private class CheckPoint{
		public String label;
		public double value;
		public int pixelLocation;

		public CheckPoint(String label, double value){
			this.label = label;
			this.value = value;
		}
	}
	
	public Gradient(Colorizer<Double> c){
		this.c = c;
	}
	
	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}

	public void setWidth(int width) {
		this.width = width;
		this.dataChanged = true;
	}

	public void setHeight(int height) {
		this.height = height;
		this.dataChanged = true;
	}

	public void addCheckPoint(String label, double value){
		this.checkPoints.add(new CheckPoint(label, value));
		this.dataChanged = true;
	}
	
	/**
	 * Maps the checkpoints on their pixel location.
	 * 
	 * @return the increment in value for each pixel
	 */
	private void translate(){
		int pixelWidth = this.width * this.getUnit().width;
		CheckPoint firstPoint = this.checkPoints.get(0);
		CheckPoint lastPoint = this.checkPoints.get(this.checkPoints.size()-1);
		
		firstPoint.pixelLocation = 0;
		lastPoint.pixelLocation = pixelWidth;
		
		double valueRange = lastPoint.value - firstPoint.value;
				
		for (int i = 1; i<checkPoints.size()-1; i++){
			int lastLoc = checkPoints.get(i-1).pixelLocation;
			double lastValue = checkPoints.get(i-1).value;
			
			double thisValue = checkPoints.get(i).value;
			
			double pixels = Math.abs(Math.abs(thisValue) - Math.abs(lastValue))/valueRange * pixelWidth;
			checkPoints.get(i).pixelLocation =  lastLoc + (int)Math.round(pixels);
		}
		
		this.valueStep = valueRange/pixelWidth;
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (dataChanged && checkPoints.size()>=2){
			dataChanged = false;
			translate();
		}
		
		if (checkPoints.size()>=2){
			int pixelWidth = this.width * this.getUnit().width;
			double value = checkPoints.get(0).value;
			for (int i=0; i<=pixelWidth; i++){
				g.setColor(c.getColor(value));
				g.drawLine(xOffset+i, yOffset, xOffset+i, yOffset+this.getUnit().height*height);
				value+=valueStep;
			}
			
			FontRenderContext frc = g.getFontRenderContext();
			
			
			g.setPaint(Color.BLACK);
			if (paintLabels){
				for (CheckPoint checkPoint : checkPoints){
					String label = checkPoint.label;
					TextLayout layout = new TextLayout(label, this.font, frc);
					Rectangle2D layoutBounds = layout.getBounds();
					double fontWidth = layoutBounds.getWidth();
					double fontHeight = layoutBounds.getHeight();
					g.drawString(label, checkPoint.pixelLocation - (int)fontWidth/2, this.getUnit().height*height + (int)fontHeight+5);
				}
			}
		}
		
		g.setPaint(Color.BLACK);
		g.drawRect(xOffset, yOffset, width * this.getUnit().width, height * this.getUnit().height);
		return this.getRawDimension(g);
	}

	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		if (dataChanged && checkPoints.size()>=2){
			dataChanged = false;
			translate();
		}
		return new Dimension(width * this.getUnit().width, height * this.getUnit().height+1);
	}

	public boolean isPaintLabels() {
		return paintLabels;
	}

	public void setPaintLabels(boolean paintLabels) {
		this.paintLabels = paintLabels;
	}
	
	
	
}
