package be.ugent.psb.modulegraphics.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Gathers a list of {@link Element}s that should be painted in the same spot.
 * First element to be added is also the first to be painted, so it can be covered 
 * by newer elements.
 * 
 * 
 * @author thpar
 *
 */
public class ElementStack extends Element implements Iterable<Element>{

	List<Element> stack = new ArrayList<Element>();
	
	public void addElement(Element el){
		this.stack.add(el);
		this.addChildElement(el);
		el.setParentElement(this);
	}
	
	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		int maxWidth = 0;
		int maxHeight = 0;
		
		for (Element el :stack){
			el.paint(g, xOffset, yOffset);
		}
		
		return new Dimension(maxWidth, maxHeight);
	}

	/**
	 * The raw dimensions of this stack. This includes the set margins of the 
	 * sub elements.
	 */
	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		int maxWidth = 0;
		int maxHeight = 0;
		
		for (Element el : stack){
			Dimension dim = el.getDimension(g);
			maxWidth = Math.max(dim.width, maxWidth);
			maxHeight = Math.max(dim.height, maxHeight);
		}
		return new Dimension(maxWidth, maxHeight);
	}

	@Override
	public Iterator<Element> iterator() {
		return stack.iterator();
	}
	
	
}
