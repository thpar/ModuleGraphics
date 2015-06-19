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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Takes a List of edges and draws them next to or under eachother
 * depending on the given direction.
 * 
 * @author thpar
 *
 */
public class ConnectArrows extends Element{

	protected String DEFAULT_SET = "default";
	
	/**
	 * Width of the bows in Units
	 */
	public int bowWidth = 5; 
	
	public enum Direction{
		HORIZONTAL, VERTICAL;
	}
	
	protected Color[] defaultColors = {Color.GRAY, Color.ORANGE, Color.BLACK, Color.RED};
	
	private Direction dir = Direction.VERTICAL;
	
	//TODO visualize directed arrows (add arrow head)
	private boolean directed = false;
	
	/**
	 * Maps an arrow set id to a set of arrows
	 */
	private Map<String, Set<Edge>> edges = new HashMap<String, Set<Edge>>();
	/**
	 * Maps the arrow set id to its color
	 */
	private Map<String, Color> colorMap = new HashMap<String, Color>();
	
	/**
	 * On some occasions, we want to make every edge a bit longer (to bridge a gap)
	 * Gap expressed in pixels...
	 */
	private int gap = 0;
	
	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}
	/**
	 * When drawing the arrows vertical, the arrow who reaches the lowest (so actually the highest
	 * edge number)
	 */
	private int lowest = 0;

	private int colorUsed = 0;
	
	private boolean fixedNumber = false;
	private int fixedNumberCount = 0;
	
	public class Edge {
		public int from;
		public int to;
		public Color color;
		
		public Edge(int from, int to){
			this.from = from;
			this.to = to;
		}
		public Edge(int from, int to, Color c){
			this.from = from;
			this.to = to;
			this.color = c;
		}
	}
	

	public void addEdge(int from, int to){
		addEdge(DEFAULT_SET, from, to, null);
	}
	
	public void addEdge(int from, int to, Color color){
		addEdge(DEFAULT_SET, from, to, color);
	}
	
	
	public void addEdge(String id, int from, int to, Color color){

		Set<Edge> edgeSet = edges.get(id);
		if (edgeSet == null){
			edgeSet = new HashSet<Edge>();
			this.edges.put(id, edgeSet);
		}
		
		Edge edge = new Edge(from, to, color);
		lowest = Math.max(edge.from, lowest);
		lowest = Math.max(edge.to, lowest);
		edgeSet.add(edge);
	}
	
	
	public Direction getDirection() {
		return dir;
	}


	public void setDirection(Direction dir) {
		this.dir = dir;
	}


	public boolean isDirected() {
		return directed;
	}


	public void setDirected(boolean directed) {
		this.directed = directed;
	}


	@Override
	public Dimension getRawDimension(Graphics2D g) {
		int width = 0;
		int height = 0;
		
		switch(dir){
		case HORIZONTAL:
			if (fixedNumber){
				width = this.getUnit().width * fixedNumberCount;
			} else {
				width = (lowest+1) * this.getUnit().width;
			}
			width+=gap;
			height = bowWidth * this.getUnit().height; 
			break;
		case VERTICAL:
			width = bowWidth * this.getUnit().width; 
			if (fixedNumber){
				height = this.getUnit().height * fixedNumberCount;
			} else {
				height = (lowest+1) * this.getUnit().height;; 				
			}
			height+=gap;
			break;
		}
		return new Dimension(width, height);
		
	}

	@Override
	public Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		colorUsed = 0;
		int startAngle = 0;
		int arcAngle = 180;
		int arrowStep=0;
		int bowStep=0;
		double bowUnits = bowWidth;
		double extraOffsetUnits = 0;
		
		g.setStroke(new BasicStroke());
		
		switch(dir){
		case HORIZONTAL:
			startAngle = 0;
			arrowStep = this.getUnit().width;
			bowStep = this.getUnit().height;
			break;
		case VERTICAL:
			startAngle = 90;
			arrowStep = this.getUnit().height;
			bowStep = this.getUnit().width;
			break;			
		}
		for (Entry<String, Set<Edge>> entrySet : this.edges.entrySet()){
			String id = entrySet.getKey();
			//set a color to use on this edge set
			g.setColor(setOrDefaultColor(id));
			Set<Edge> edgeSet = entrySet.getValue();
			for (Edge edge : edgeSet){
				//override color if the edge has its own color defined
				if (edge.color != null){
					g.setColor(edge.color);
				}
				if (edge.from==edge.to){
					drawLoop(g, edge, xOffset, yOffset, bowStep, bowUnits, 
							arrowStep, extraOffsetUnits);
				} else {
					drawEdge(g, edge, xOffset, yOffset, bowStep, arrowStep, 
							bowUnits, extraOffsetUnits, startAngle, arcAngle);
				}
			}
			
			if (bowUnits>=1){
				bowUnits-=0.5;
				extraOffsetUnits+=0.5;
			}
			

		}

		return getRawDimension(g);
	}
	
	private void drawLoop(Graphics2D g, Edge edge, int xOffset, int yOffset,
								int bowStep, double bowUnits, int arrowStep, double extraOffsetUnits){
		
		int width = 0;
		int height = 0;
		int startX = 0;
		int startY = 0;
		DropShape drop = null;
		switch(dir){
		case HORIZONTAL:
			width = arrowStep;
			height = (int)Math.round(bowUnits * bowStep);
			startX = xOffset + edge.from*arrowStep;
			startY = yOffset + (int)Math.round(extraOffsetUnits*bowStep);
			drop = new DropShape(startX, startY, width, height, DropShape.Direction.VERTICAL);
			break;
		case VERTICAL: 
			width = (int)Math.round(bowUnits * bowStep);
			height = arrowStep;
			startX = xOffset + (int)Math.round(extraOffsetUnits*bowStep);
			startY = yOffset + edge.from*arrowStep; 
			drop = new DropShape(startX, startY, width, height, DropShape.Direction.HORIZONTAL);
			break;
		}

		g.draw(drop);
	}
	
	private void drawEdge(Graphics2D g, Edge edge, int xOffset, int yOffset, 
			int bowStep, int arrowStep, double bowUnits, double extraOffsetUnits, 
			int startAngle, int arcAngle){
		int from = Math.min(edge.from, edge.to);			

		int start = from * arrowStep + arrowStep/2;			
		int arcArrowLength = Math.abs(edge.from - edge.to) * arrowStep;
		
		/**
		 * width of the rectangle surrounding the complete circle 
		 * from which the arc is taken.
		 */
		int width=0;
		/**
		 * height of the rectangle surrounding the complete circle 
		 * from which the arc is taken.
		 */
		int height=0;
		
		int startX=0;
		int startY=0;
		
		switch(dir){
		case HORIZONTAL:
			width = arcArrowLength + gap;
			height = (int)Math.round(bowUnits * bowStep * 2);
			startX = xOffset+start;
			startY = yOffset+((int)Math.round(extraOffsetUnits*bowStep));
			break;
		case VERTICAL:
			width = (int)Math.round(bowUnits * bowStep * 2);
			height = arcArrowLength + gap;
			startX = xOffset+((int)Math.round(extraOffsetUnits*bowStep));
			startY = yOffset+start;
			break;
		}

		g.drawArc(startX,startY,width,height,startAngle,arcAngle);
	}

	/**
	 * Either gets the assigned color from the color map, or
	 * picks a default color. Combining default colors and the color map
	 * leads to unexpected results.
	 * 
	 * @param id
	 * @return
	 */
	private Color setOrDefaultColor(String id) {
		Color c = colorMap.get(id);
		if (c!=null) return c;
		else {
			return defaultColors[colorUsed++ % defaultColors.length];
		}
	}

	

	public void setColor(String id, Color c){
		this.colorMap.put(id, c);
	}
	public Color getColor(String id){
		return this.colorMap.get(id);
	}
	public Map<String, Color> getColorMap(){
		return colorMap;
	}
	public void setColorMap(Map<String, Color>colorMap){
		this.colorMap = colorMap;
	}

	public int getBowWidth() {
		return bowWidth;
	}

	/**
	 * Set the width of the largest bow (in units)
	 * 
	 * @param bowWidth
	 */
	public void setBowWidth(int bowWidth) {
		this.bowWidth = bowWidth;
	}
	
	/**
	 * Remove all edges and colors
	 */
	public void reset(){
		edges = new HashMap<String, Set<Edge>>();
		colorMap = new HashMap<String, Color>();
	}
	
	public void setFixedNumber(int number){
		this.fixedNumber = true;
		this.fixedNumberCount = number;
	}
	public void unsetFixedNumber(){
		this.fixedNumber = false;
	}
	
}
