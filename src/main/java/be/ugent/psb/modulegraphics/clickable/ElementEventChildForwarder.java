package be.ugent.psb.modulegraphics.clickable;

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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import be.ugent.psb.modulegraphics.elements.Element;


/**
 * Forwards {@link MouseEvent}s to child of an {@link Element} that has been hit.
 * @author Thomas Van Parys
 *
 */
public class ElementEventChildForwarder implements MouseListener {
	
	private Element parent;
	
	
	
	public ElementEventChildForwarder(Element parent) {
		this.parent = parent;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
//				e.setSource(child);
				ml.mouseClicked(e);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
//				e.setSource(child);
				ml.mouseEntered(e);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
//				e.setSource(child);
				ml.mouseExited(e);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
//				e.setSource(child);
				ml.mousePressed(e);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
//				e.setSource(child);
				ml.mouseReleased(e);
			}
		}
	}

}
