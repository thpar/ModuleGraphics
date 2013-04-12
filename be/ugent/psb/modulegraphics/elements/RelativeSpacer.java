package be.ugent.psb.modulegraphics.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * {@link Element} to keep an empty spot on the {@link Canvas}. It will follow the height and/or
 * width of another {@link Element}.
 * 
 * @author thpar
 *
 */
public class RelativeSpacer extends Spacer {

	private Element widthElement;
	private Element heightElement;
	
	public RelativeSpacer(){
		
	}
	
	
	/**
	 * 
	 * @param widthElement element to be tracking in width
	 * @param heightElement element to be tracking in height
	 */
	public RelativeSpacer(Element widthElement, Element heightElement){
		this.widthElement = widthElement;
		this.heightElement = heightElement;
	}
	
	
	public Element getWidthElement() {
		return widthElement;
	}

	public void setWidthElement(Element widthElement) {
		this.widthElement = widthElement;
	}

	public Element getHeightElement() {
		return heightElement;
	}

	public void setHeightElement(Element heightElement) {
		this.heightElement = heightElement;
	}

	private Dimension getRelativeDim(Graphics2D g) {
		int width = 0;
		if (this.widthElement==null && dim !=null){
			width = this.dim.width;
		} else {
			width = this.widthElement.getDimension(g).width;
		}
		int height = 0;
		if (this.heightElement==null && dim !=null){
			height = this.dim.height;
		} else {
			height = this.heightElement.getDimension(g).height;
		}
		return new Dimension(width, height);
	}
	
	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		if (accent ){
			g.fillRect(xOffset, yOffset, 5, 5);
		}
		return getRelativeDim(g);
	}


	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return getRelativeDim(g);
	}
	
}
