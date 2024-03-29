package be.ugent.psb.modulegraphics.elements;

/*
 * #%L
 * ModuleGraphics
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

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
 * @author Thomas Van Parys
 *
 */
public class Canvas extends Element implements Iterable<Element>{
	
	private List<List<Element>> grid = new ArrayList<List<Element>>();
	private List<Element> currentRow;
	
	private Element lastAddedElement;
	
	private int currentX = 0;
	private int currentY = 0;
	
	
	
	public Canvas(){
		currentRow = new ArrayList<Element>();
		grid.add(currentRow);
		addMouseListener(new ElementEventChildForwarder(this));
	}
	
	/**
	 * A list of maximum heights for rows
	 */
	private List<Integer> rowMax = new ArrayList<Integer>();
	
	/**
	 * A list of maximum widths for columns
	 */
	private List<Integer> colMax = new ArrayList<Integer>();
	
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
	
	/**
	 * Adds an element to this Canvas.
	 * 
	 * @param el
	 */
	public void add(Element el){
		if (currentX<currentRow.size()){
			if (currentRow.get(currentX) instanceof NullElement){
				currentRow.set(currentX, el);
			}
		} else {
			currentRow.add(el);
		}
		lastAddedElement = el;
		el.setParentElement(this);
		currentX++;
	} 
	
	private void position(int x, int y){
		//go to row
		currentY=0;
		currentRow = grid.get(currentY);
		while (currentY<y){
			newRow();
		}
		currentX=0;
		while (currentX<x){
			add(new NullElement());
		}
	}
	
	
	public void newRow(){		
		currentX = 0;
		currentY++;
		if (currentY>=grid.size()){
			currentRow = new ArrayList<Element>();			
			grid.add(currentRow);
		} else {
			currentRow = grid.get(currentY);
		}
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
		List<Integer> calcRowMax = new ArrayList<Integer>();
		List<Integer> calcColMax = new ArrayList<Integer>();
		
		for (List<Element> row : grid){
			int colCounter = 0;
			int rowHeight = 0;
			for (Element el : row){
				if (colCounter >= calcColMax.size()){
					calcColMax.add(el.getDimension(g).width);
				} else{
					calcColMax.set(colCounter, Math.max(calcColMax.get(colCounter), el.getDimension(g).width));
				}
				rowHeight = Math.max(rowHeight, el.getDimension(g).height);
				colCounter++;
			}
			calcRowMax.add(rowHeight);
		}
		
		rowMax = calcRowMax;
		colMax = calcColMax;
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
	/**
	 * The spacing of a Canvas defines how far apart its child elements will be drawn.
	 * @param horizontalSpacing
	 */
	public int getHorizontalSpacing() {
		return horizontalSpacing;
	}
	
	/**
	 * The spacing of a Canvas defines how far apart its child elements will be drawn.
	 * @param horizontalSpacing
	 */
	public void setHorizontalSpacing(int horizontalSpacing) {
		this.horizontalSpacing = horizontalSpacing;
	}
	/**
	 * The spacing of a Canvas defines how far apart its child elements will be drawn.
	 * @return
	 */
	public int getVerticalSpacing() {
		return verticalSpacing;
	}
	/**
	 * The spacing of a Canvas defines how far apart its child elements will be drawn.
	 * @param horizontalSpacing
	 */
	public void setVerticalSpacing(int verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}
	
	/**
	 * Iterating over the Canvas object gives you the list of child 
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
	
	public Iterator<List<Element>> rowIterator(){
		return grid.iterator();
	}
	
	
	private class GridIterator implements Iterator<Element>{

		Iterator<List<Element>> rowIt;
		Iterator<Element> colIt;
		
		public GridIterator(){
			rowIt = rowIterator();
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
