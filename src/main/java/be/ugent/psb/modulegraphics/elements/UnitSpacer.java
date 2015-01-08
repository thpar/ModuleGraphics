package be.ugent.psb.modulegraphics.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;

public class UnitSpacer extends Spacer {
	
	private int unitX = 0;
	private int unitY = 0;

	public UnitSpacer(int unitX, int unitY){
		this.unitX = unitX;
		this.unitY = unitY;
	}
	
	
	private Dimension getUnitDim(Graphics2D g) {
		Dimension unit = this.getUnit();
		return new Dimension(unitX * unit.width, unitY * unit.height);
	}
	
	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (accent ){
			g.fillRect(xOffset, yOffset, 5, 5);
		}
		return getUnitDim(g);
	}


	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return getUnitDim(g);
	}
	
}
