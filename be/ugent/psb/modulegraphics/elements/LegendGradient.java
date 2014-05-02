package be.ugent.psb.modulegraphics.elements;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LegendGradient extends Gradient {

	List<CheckPoint> checkPoints = new ArrayList<CheckPoint>();
	private CheckPoint minLabel;
	private CheckPoint maxLabel;
	
	private Font font = new Font("SansSerif", Font.PLAIN, 12);
	private Font titleFont = new Font("SansSerif", Font.BOLD, 14);
	private int minMargin = 0;
	private int maxMargin = 0;
	
	private String title = null;
	private int titleMarginBottom = 5;
	
	public LegendGradient(double min, double max, Colorizer<Double> c) {
		super(min, max, c);
		minLabel = addLabel(min);
		maxLabel = addLabel(max);
	}
	
	
	
	private class CheckPoint{
		double value;
		String label;
		int pixelLocation = 0;
		
		CheckPoint(String label, double value){
			this.value = value;
			this.label = label;
		}

		@Override
		public String toString() {
			return label+": "+value+" ("+pixelLocation+")"; 
		}
		
	}
	
	
	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		
		if (title!=null){
			yOffset+=this.getUnit().height;
			g.setFont(titleFont);
			g.drawString(title, xOffset, yOffset);
			yOffset+=titleMarginBottom;
		}
		
		super.paintElement(g, xOffset + minMargin, yOffset);
		
		g.setFont(font);
		calcFontMargins(g);
		
		for (CheckPoint cp : checkPoints){
			g.drawString(cp.label, xOffset+cp.pixelLocation, yOffset+(this.getUnit().height*(this.getHeight()+1)));
		}
		
		return this.getRawDimension(g);
	}

	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		int unitWidth = this.getWidth();
		int unitHeight = this.getHeight() +1;
		
		int extraHeight = 0;
		if (title!=null){
			unitHeight++;
			extraHeight = titleMarginBottom;
		}
		
		calcFontMargins(g);
		
		
		Dimension rawDim = new Dimension(unitWidth * this.getUnit().width + minMargin + maxMargin,
				unitHeight * this.getUnit().height + extraHeight);
		
		return rawDim;
	}
	
	private void calcFontMargins(Graphics2D g){
		FontRenderContext frc = g.getFontRenderContext();
		TextLayout minLayout = new TextLayout(minLabel.label, font, frc);
		TextLayout maxLayout = new TextLayout(maxLabel.label, font, frc);
		Rectangle2D minDim = minLayout.getBounds();
		Rectangle2D maxDim = maxLayout.getBounds();
		
		this.minMargin = (int)minDim.getWidth()/2;
		this.maxMargin = (int)maxDim.getWidth()/2;
	}
	
	/**
	 * Add a label to the gradient (other than min or max)
	 * 
	 * @param label
	 * @param value
	 * @return the added {@link CheckPoint}
	 */
	public CheckPoint addLabel(String label, double value){
		CheckPoint checkPoint = new CheckPoint(label, value);
		setPixelLocation(checkPoint);
		checkPoints.add(checkPoint);
		return checkPoint;
	}
	
	/**
	 * Add a value as label to the gradient (other than min or max)
	 * 
	 * @param label
	 * @param value
	 * @return the added {@link CheckPoint}
	 */
	public CheckPoint addLabel(double value){
		DecimalFormat df2 = new DecimalFormat("###.##");
		return addLabel(String.valueOf(df2.format(value)), value);
	}
	
	private void recalcPixelLocations(){
		if (checkPoints!=null){
			for (CheckPoint cp : checkPoints){
				setPixelLocation(cp);
			}			
		}
	}
	
	private void setPixelLocation(CheckPoint cp){
		cp.pixelLocation = (int)((cp.value+Math.abs(min))/this.range * this.pixelWidth);
	}

	@Override
	protected void calc(){
		super.calc();
		recalcPixelLocations();
	}
	
	/**
	 * Set a label for the minimum value. Default is simply the value.
	 * @param label label for the minimum value
	 */
	public void setMinLabel(String label){
		minLabel.label = label;
	}

	/**
	 * Set a label for the maximum value. Default is simply the value.
	 * @param label label for the maximum value
	 */
	public void setMaxLabel(String label){
		maxLabel.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
