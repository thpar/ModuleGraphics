package be.ugent.psb.modulegraphics.elements;

public interface TreeNode {

	/**
	 * 
	 * @return left child node
	 */
	public TreeNode left();
	
	/**
	 * 
	 * @return right child node
	 */
	public TreeNode right();
	
	/**
	 * 
	 * @return true when this node doesn't have children.
	 */
	public boolean isLeaf();
	
	/**
	 * 
	 * @return the total width of this node and all its leaves (in units)
	 */
	public int getWidth();
	
}
