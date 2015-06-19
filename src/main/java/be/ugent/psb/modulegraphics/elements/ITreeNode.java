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

	/**
	 * Return all leaves, starting from this node.
	 * @return
	 */
	public List<ITreeNode<T>> getLeaves();
}
