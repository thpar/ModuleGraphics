package be.ugent.psb.modulegraphics.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class NumberMatrix extends Matrix<Integer> {
	
	private Color foregroundColor = Color.BLACK;
	private Color backgroundColor = Color.WHITE;
	
	public NumberMatrix(Integer[][] data) {
		this.data = data;
	}


	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		int x = 0;
		int y = 0;
		Dimension unit = getUnit();
		g.setStroke(new BasicStroke());
		for (Integer[] row : data){
			for (Integer element : row){				
				g.setColor(backgroundColor);
				g.fillRect(x + xOffset, y + yOffset, unit.width, unit.height);
				g.setColor(Color.BLACK);
				g.drawRect(x + xOffset, y + yOffset, unit.width, unit.height);
				g.setColor(foregroundColor);
				g.drawString(element.toString(), x+xOffset, y+yOffset);
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
	
	


}
