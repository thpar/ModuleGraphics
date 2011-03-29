package be.ugent.psb.modulegraphics.elements;

public interface ITreeNode {

	/**
	 * 
	 * @return left child node
	 */
	public ITreeNode left();
	
	/**
	 * 
	 * @return right child node
	 */
	public ITreeNode right();
	
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
