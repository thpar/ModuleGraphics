package be.ugent.psb.modulegraphics.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;


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
	
	int maxWidth = 0;
	int maxHeight = 0;
	
	public void add(Element el){
		this.stack.add(el);
		this.addChildElement(el);
		el.setParentElement(this);
	}
	
	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		Dimension rawDim = this.getRawDimension(g);
		
		for (Element el :stack){
			XYPair xyAlign = this.alignElement(g, el);
			el.paint(g, xOffset + xyAlign.x, yOffset + xyAlign.y);
		}
		
		return rawDim;
	}
	
	private class XYPair{
		public int x;
		public int y;
		
		public XYPair(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	/**
	 * Calculate the xy offset to align the given element.
	 * 
	 * @param g
	 * @param el
	 * @return
	 */
	private XYPair alignElement(Graphics2D g, Element el) {	
		int xAlign = 0;
		int yAlign = 0;
		Dimension elDim = el.getDimension(g);
		switch(el.getAlignment()){
		case TOP_LEFT:
			xAlign = 0;
			yAlign = 0;
			break;
		case TOP_RIGHT:
			xAlign = maxWidth - elDim.width;
			yAlign = 0;
			break;
		case TOP_CENTER:
			xAlign = (maxWidth - elDim.width)/2;
			yAlign = 0;
			break;
		case CENTER_LEFT:
			xAlign = 0;
			yAlign = (maxHeight - elDim.height)/2;
			break;
		case CENTER_RIGHT:
			xAlign = maxWidth - elDim.width;
			yAlign = (maxHeight - elDim.height)/2;
			break;
		case CENTER:
			xAlign = (maxWidth - elDim.width)/2;
			yAlign = (maxHeight - elDim.height)/2;
			break;
		case BOTTOM_LEFT:
			xAlign = 0;
			yAlign = maxHeight - elDim.height;
			break;
		case BOTTOM_RIGHT:
			xAlign = maxWidth - elDim.width;
			yAlign = maxHeight - elDim.height;
			break;
		case BOTTOM_CENTER:
			xAlign = (maxWidth - elDim.width)/2;
			yAlign = maxHeight - elDim.height;
			break;
		}
		
		return new XYPair(xAlign,yAlign);
	}
	
	/**
	 * The raw dimensions of this stack. This includes the set margins of the 
	 * sub elements.
	 */
	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		maxWidth = 0;
		maxHeight = 0;
		
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
	
	public int size(){
		return stack.size();
	}

	@Override
	public void forEach(Consumer<? super Element> action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spliterator<Element> spliterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
