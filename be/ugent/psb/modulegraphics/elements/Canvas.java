package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;

/**
 * The Canvas groups and layouts Elements. As a Canvas is an Element in itself,
 * nested Canvasses can be used.
 * The Elements in a Canvas are layouted as a simple grid. They are added in a
 * consecutive way. A new row is started when the newRow() method is explicitly called.
 * Each column and each row are stretched to fit their largest element (margins included).
 * 
 * @author thpar
 *
 */
public class Canvas extends Element implements Iterable<Element>{
	

	private List<List<Element>> grid = new ArrayList<List<Element>>();
	private List<Element> currentRow;
	
	private Element lastAddedElement;
	
	
	public Canvas(){
		currentRow = new ArrayList<Element>();
		grid.add(currentRow);
		addMouseListener(new ElementEventChildForwarder(this));
	}
	
	/**
	 * A list of maximum heights for rows
	 */
	private List<Integer> rowMax;
	
	/**
	 * A list of maximum widths for columns
	 */
	private List<Integer> colMax;
	
	private int horizontalSpacing = 0;
	private int verticalSpacing = 0;
	

	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		calcMaxDimensions(g);		
		int gridWidth = 0;
		for (int width : colMax){
			gridWidth+=width;
		}
		
		int gridHeight = 0;
		for (int height : rowMax){
			gridHeight+=height;
		}
		
		int horSpace = (colMax.size()-1)*this.horizontalSpacing;
		int verSpace = (rowMax.size()-1)*this.verticalSpacing;
		
		return new Dimension(gridWidth+horSpace, gridHeight+verSpace);
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		calcMaxDimensions(g);
		
		
		int y = 0;
		int rowCounter = 0;
		
		for (List<Element> row : grid){			
			int x = 0;
			int colCounter = 0;
			for (Element el : row){
				XYPair xyAlign = alignElement(g, el, colCounter, rowCounter);
				
				el.paint(g, xOffset+x + xyAlign.x, yOffset+y + xyAlign.y);
				
				boolean debug = false;
				//debug
				if (debug){
					Color colorBack = g.getColor();
					g.setColor(Color.RED);
					Dimension elDims = el.getDimension(g);
					g.drawRect(xOffset+x, yOffset+y, elDims.width, elDims.height);
					g.setColor(colorBack);
				}
				
				x+=colMax.get(colCounter++) + horizontalSpacing;
			}
			y+=rowMax.get(rowCounter++) + verticalSpacing;
		}
		
		int gridWidth = 0;
		for (int width: colMax){
			gridWidth+=width + horizontalSpacing;
		}
		return new Dimension(gridWidth - horizontalSpacing, y-verticalSpacing);
	}
	

	public void add(Element el){
		currentRow.add(el);
		lastAddedElement = el;
		el.setParentElement(this);
	}
	public void newRow(){		
		currentRow = new ArrayList<Element>();
		grid.add(currentRow);
	}

	/**
	 * Calculate the xy offset to align the given element.
	 * 
	 * @param g
	 * @param el
	 * @param colCounter
	 * @param rowCounter
	 * @return
	 */
	private XYPair alignElement(Graphics2D g, Element el, 
							int colCounter, 
							int rowCounter) {	
		int xAlign = 0;
		int yAlign = 0;
		Dimension elDim = el.getDimension(g);
		switch(el.getAlignment()){
		case TOP_LEFT:
			xAlign = 0;
			yAlign = 0;
			break;
		case TOP_RIGHT:
			xAlign = colMax.get(colCounter) - elDim.width;
			yAlign = 0;
			break;
		case TOP_CENTER:
			xAlign = (colMax.get(colCounter) - elDim.width)/2;
			yAlign = 0;
			break;
		case CENTER_LEFT:
			xAlign = 0;
			yAlign = (rowMax.get(rowCounter) - elDim.height)/2;
			break;
		case CENTER_RIGHT:
			xAlign = colMax.get(colCounter) - elDim.width;
			yAlign = (rowMax.get(rowCounter) - elDim.height)/2;
			break;
		case CENTER:
			xAlign = (colMax.get(colCounter) - elDim.width)/2;
			yAlign = (rowMax.get(rowCounter) - elDim.height)/2;
			break;
		case BOTTOM_LEFT:
			xAlign = 0;
			yAlign = rowMax.get(rowCounter) - elDim.height;
			break;
		case BOTTOM_RIGHT:
			xAlign = colMax.get(colCounter) - elDim.width;
			yAlign = rowMax.get(rowCounter) - elDim.height;
			break;
		case BOTTOM_CENTER:
			xAlign = (colMax.get(colCounter) - elDim.width)/2;
			yAlign = rowMax.get(rowCounter) - elDim.height;
			break;
		}
		
		return new XYPair(xAlign,yAlign);
	}
	/**
	 * Check for each column and each row its maximum height or width.
	 */
	private void calcMaxDimensions(Graphics2D g){
		rowMax = new ArrayList<Integer>();
		colMax = new ArrayList<Integer>();
		
		for (List<Element> row : grid){
			int colCounter = 0;
			int rowHeight = 0;
			for (Element el : row){
				if (colCounter >= colMax.size()){
					colMax.add(el.getDimension(g).width);
				} else{
					colMax.set(colCounter, Math.max(colMax.get(colCounter), el.getDimension(g).width));
				}
				rowHeight = Math.max(rowHeight, el.getDimension(g).height);
				colCounter++;
			}
			rowMax.add(rowHeight);
		}
	}

	/**
	 * Returns the last Element that was added to the Canvas,
	 * so margins can easily be added afterwards.
	 * 
	 * returns null for an empty canvas
	 */
	public Element getLastAddedElement(){
		return lastAddedElement;
	}
	
	public int getHorizontalSpacing() {
		return horizontalSpacing;
	}
	
	public void setHorizontalSpacing(int horizontalSpacing) {
		this.horizontalSpacing = horizontalSpacing;
	}
	
	public int getVerticalSpacing() {
		return verticalSpacing;
	}
	
	public void setVerticalSpacing(int verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}
	
	/**
	 * Iterating over the Canvas object gives you the lost of child 
	 * elements
	 */
	@Override
	public Iterable<Element> getChildElements(){
		return this;
	}
	
	private class XYPair{
		public int x;
		public int y;
		
		public XYPair(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	
	@Override
	public Iterator<Element> iterator() {
		return new GridIterator();
	}
	
	private class GridIterator implements Iterator<Element>{

		Iterator<List<Element>> rowIt;
		Iterator<Element> colIt;
		
		public GridIterator(){
			rowIt = grid.iterator();
		}
		
		@Override
		public boolean hasNext() {
			if ((colIt==null || !colIt.hasNext()) && rowIt.hasNext()){
				colIt = rowIt.next().iterator();
			}
			if (colIt==null) return false;
			return colIt.hasNext();
		}

		@Override
		public Element next() {
			if ((colIt==null || !colIt.hasNext()) && rowIt.hasNext()){
				colIt = rowIt.next().iterator();
			}
			if (colIt!=null && colIt.hasNext()) return colIt.next();
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}

	
}
