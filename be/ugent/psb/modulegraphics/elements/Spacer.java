package be.ugent.psb.modulegraphics.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * Empty element to keep space in the grid of a Canvas. A Spacer can have 
 * dimensions to keep at least a certain amount of space.
 * 
 * @author thpar
 *
 */
public class Spacer extends Element {

	
	private Dimension dim;
	/**
	 * Set a little mark in the corner of the spacer, for debugging purposes.
	 */
	private boolean accent = false;

	public Spacer(){
		dim = new Dimension();
	}
	
	public Spacer(Dimension dim){
		this.dim = dim;
	}
	

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (accent ){
			g.fillRect(xOffset, yOffset, 5, 5);
		}
		return dim;
	}

	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return dim;
	}

	public void setDimension(Dimension dim) {
		this.dim = dim;
	}
	
	
}
