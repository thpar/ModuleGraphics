package be.ugent.psb.modulegraphics.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;



/**
 * The tree branches that are put above the matrix with top regulators.
 * 
 * @author thpar
 *
 */
public class TreeStructure extends Element {

	private TreeNode rootNode;
	private int xPointer;
	private int xOffset;
	private int yOffset;

	public TreeStructure(TreeNode rootNode){
		this.rootNode = rootNode;
	}
	
	
	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		int height = getUnit().height * (maxDepthCount(rootNode) +1);
		int width  = getUnit().width  * rootNode.getWidth();
		return new Dimension(width, height);
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		this.xPointer = 0;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		int level = 0;
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		Dimension rawDim = getRawDimension(g);
		drawBranches(rootNode, level, rawDim.height, g);
		return rawDim;
	}
	
	/**
	 * Recursively called method to draw all branches in a tree structure.
	 * It passes on the current tree node and the level in the tree (root being level 0, 
	 * its children level 1 etc...). The maxHeight is the height of the complete tree that was
	 * calculated on beforehand.
	 * 
	 * Each node returns the x coordinate where it drew its vertical tree branch. This way each internal 
	 * node just connects the dots given by its children. 
	 * The xPointer is incremented by the width of a leave, every time a leave is encountered, so when xPointer 
	 * is saved after drawing the left child, but before drawing the right child, the internal node knows where
	 * to draw its vertical branch.
	 * 
	 * @param node
	 * @param level
	 * @param maxHeight
	 * @param g
	 * @return
	 */
	private int drawBranches(TreeNode node, int level, int maxHeight, Graphics2D g){
		if (!node.isLeaf()){
			int from = drawBranches(node.left(), level+1, maxHeight, g);
			int middle = xPointer;
			int to   = drawBranches(node.right(), level+1, maxHeight, g);
			
			int yCrossing = (level+1)*getUnit().height;
			
			g.drawLine(middle + xOffset, yCrossing - getUnit().height + yOffset, 
					   middle + xOffset, yCrossing + yOffset);
			g.drawLine(from + xOffset,  yCrossing + yOffset, 
					   to   + xOffset,  yCrossing + yOffset);
			return middle;
		} else {
			int leaveWidth = getUnit().width*node.getWidth();
			int depth = level*getUnit().height;
			int drawX = xPointer+leaveWidth/2;
			g.drawLine(drawX + xOffset, depth + yOffset,
					   drawX + xOffset,	maxHeight + yOffset);
			xPointer+=leaveWidth;
			return drawX;
		}
	}
	
	/**
	 * Recursively called method to determine the longest path from 
	 * root to a leave.
	 * 
	 * @param node
	 * @return
	 */
	private int maxDepthCount(TreeNode node){
		if (!node.isLeaf()){
			int leftCount = maxDepthCount(node.left());
			int rightCount = maxDepthCount(node.right());
			return Math.max(leftCount,rightCount) +1;
		} else {
			return 0;
		}
	}

}
