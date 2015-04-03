package be.ugent.psb.modulegraphics.elements;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A part of the figure representing a specific element of the module (a list of gene names, 
 * the module expression matrix itself, a list of GO annotations, etc...).
 * Elements can have extra attributes like margins and alignments.
 * They can be drawn on a Graphics object or can be grouped and layouted by 
 * a Canvas element.
 * 
 * @author thpar
 *
 */
public abstract class Element{
	//margins
	private int topMargin = 0;
	private int rightMargin = 0;
	private int bottomMargin = 0;
	private int leftMargin = 0;
	
	private Alignment align = Alignment.TOP_LEFT;
	private Dimension unit;
	private Element parentElement;
	private Container container;
	
	private List<MouseListener> mouseListeners = new ArrayList<MouseListener>();
	private List<Element> childElements = new ArrayList<Element>();
	
	private static boolean debugMode = false; 
	
	private static final Dimension DEFAULT_UNIT = new Dimension(10,20);

	/**
	 * Once the {@link Element} has been drawn, the top left coordinates are stored here. 
	 */
	private Point paintedAtTopLeft = new Point();
	private int paintedWidth = -1;
	private int paintedHeight = -1;
	
	
	protected boolean highlighted;
	
	public enum Alignment{
		TOP_LEFT, TOP_CENTER, TOP_RIGHT,
		CENTER_LEFT, CENTER, CENTER_RIGHT,
		BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
	}

	
	/**
	 * Paint this Element on a Graphics object, at given location.
	 * @param g
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	public Dimension paint(Graphics2D g, int xOffset, int yOffset) {
		this.paintedAtTopLeft = new Point(xOffset+leftMargin, yOffset+topMargin);
		Dimension dim = paintElement((Graphics2D) g,xOffset+leftMargin, yOffset+topMargin);
		Dimension realDim = new Dimension(dim.width+leftMargin+rightMargin, dim.height+topMargin+bottomMargin);
		
		//debug
		if (debugMode){
			Color tmpColor = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(xOffset, yOffset, realDim.width, realDim.height);
			g.setColor(tmpColor);
		}
		return realDim;
	}

	/**
	 * Paint this Element on a Graphics object in the topleft corner.
	 * @param g
	 * @return
	 */
	public Dimension paint(Graphics2D g){
		return paint(g, 0, 0);
	}
	
	/**
	 * Draws the Element with xOffset and yOffset as coordinates of the topleft corner.
	 * 
	 * 
	 * @param g
	 * @param xOffset
	 * @param yOffset
	 * @return the 'raw' dimensions of the figure (margins not included)
	 */
	protected abstract Dimension paintElement(Graphics2D g, int xOffset, int yOffset);
	
	/**
	 * 
	 * @return the dimensions of the object, including margins.
	 */
	public Dimension getDimension(Graphics2D g){
		Dimension dim = this.getRawDimension((Graphics2D) g);
		paintedWidth = dim.width;
		paintedHeight = dim.height;
		Dimension realDim = new Dimension(dim.width+leftMargin+rightMargin, dim.height+topMargin+bottomMargin);
		return realDim;
	}
	
	/**
	 * 
	 * @param g Graphics environment. Some objects need this to calculate their actual size (eg. fonts).
	 * @return the dimensions of this object, margins not included
	 */
	protected abstract Dimension getRawDimension(Graphics2D g);
	
	public void setMargin(int margin){
		this.topMargin = margin;
		this.bottomMargin = margin;
		this.rightMargin = margin;
		this.leftMargin = margin;
	}
	public void setMargin(int topBottom, int rightLeft){
		this.topMargin = topBottom;
		this.bottomMargin = topBottom;
		this.rightMargin = rightLeft;
		this.leftMargin = rightLeft;
	}
	public void setMargin(int top, int right, int bottom, int left){
		this.topMargin = top;
		this.bottomMargin = bottom;
		this.rightMargin = right;
		this.leftMargin = left;
	}

	public int getTopMargin() {
		return topMargin;
	}

	public void setTopMargin(int top) {
		this.topMargin = top;
	}

	public int getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(int right) {
		this.rightMargin = right;
	}

	public int getBottomMargin() {
		return bottomMargin;
	}

	public void setBottomMargin(int bottom) {
		this.bottomMargin = bottom;
	}

	public int getLeftMargin() {
		return leftMargin;
	}

	public void setLeft(int left) {
		this.leftMargin = left;
	}

	/**
	 * The way this element will align itself within the {@link Canvas}.
	 * @return
	 */
	public Alignment getAlignment() {
		return align;
	}

	/**
	 * Define how this element will be aligned. This defines the alignment of 
	 * the Element (or Canvas) itself within its parent Canvas, NOT the way child Elements will be aligned.
	 * @param align
	 */
	public void setAlignment(Alignment align) {
		this.align = align;
	}


	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}

	/**
	 * Set the unit used in this Element. Most Elements use the unit to define the dimensions
	 * of a label, a square in a matrix, etc...
	 * 
	 * @param unit
	 */
	public void setUnit(Dimension unit) {
		this.unit = unit;
	}

	/**
	 * Returns the unit used in this Element. The Dimension of a unit is used as dimensions of the 
	 * labels, matrix squares, etc... in this Element. If no unit is set, an Element will
	 * automatically ask its parent Element. 
	 * @return
	 */
	public Dimension getUnit() {
		if (unit==null) {
			if (parentElement != null){
				return parentElement.getUnit();
			} else {
				return DEFAULT_UNIT;
			}
		}
		else {
			return this.unit;
		}
	}
	

	public Element getParentElement() {
		return parentElement;
	}

	public void setParentElement(Element parent) {
		this.parentElement = parent;
	}

	public Iterable<Element> getChildElements(){
		return childElements;
	}
	/**
	 * Registers an other {@link Element} as a child Element.
	 * MouseEvents are automatically passed
	 * on to registered child Elements.
	 * This will also set the parent element to this.
	 *  
	 * @param child
	 */
	public void addChildElement(Element child){
		child.setParentElement(this);
		this.childElements.add(child);
	}
	
	public Point absoluteToRelative(Point absolute){
		int x = absolute.x - paintedAtTopLeft.x;
		int y = absolute.y - paintedAtTopLeft.y;
		return new Point(x,y);
	}
	
	/*
	 * MouseListener
	 */
	
	public void addMouseListener(MouseListener ml){
		this.mouseListeners.add(ml);
	}

	public void removeMouseListener(MouseListener ml){
		this.mouseListeners.remove(ml);
	}
	
	public List<MouseListener> getMouseListeners() {
		return mouseListeners;
	}

	public boolean isHit(int x, int y){
		if (x >= paintedAtTopLeft.x && y >= paintedAtTopLeft.y 
				&& paintedAtTopLeft.x+paintedWidth >= x 
				&& paintedAtTopLeft.y+paintedHeight >= y) return true;
		else return false;
	}
	
	
	
	/**
	 * Inefficient method to retrieve the child Element that has been hit.
	 * It's up to the implementing Elements to override this with a more efficient method.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Element getHitChild(int x, int y){
		for (Element child : getChildElements()){
			if (child.isHit(x, y)) return child;
		}
		return null;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
		this.refresh();
	}

	public boolean isHighlighted() {
		return highlighted;
	}
	
	public void toggleHighlighted(){
		setHighlighted(!highlighted);
	}
	
	/**
	 * Signals the container to repaint
	 */
	protected void refresh(){
		
		if (this.parentElement != null){
			this.parentElement.refresh();
		} else {
			if (this.container!=null){
				this.container.repaint();
			}
		}
	}
	
	public void setContainer(Container cont){
		this.container = cont;
	}
	public Container getContainer(){
		return this.container;
	}

	public static boolean isDebugMode() {
		return debugMode;
	}

	public static void setDebugMode(boolean debugMode) {
		Element.debugMode = debugMode;
	}
	
	
	
	
}
