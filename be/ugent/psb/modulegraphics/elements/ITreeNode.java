package be.ugent.psb.modulegraphics.elements;

import java.util.List;

public interface ITreeNode<T> {

	/**
	 * 
	 * @return left child node
	 */
	public ITreeNode<T> left();
	
	/**
	 * 
	 * @return right child node
	 */
	public ITreeNode<T> right();
	
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
	
	/**
	 * 
	 * @return the actual content of this node
	 */
	public List<T> getColumns();

	
}
