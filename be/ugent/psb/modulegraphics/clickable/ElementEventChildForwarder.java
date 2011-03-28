package be.ugent.psb.modulegraphics.clickable;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import be.ugent.psb.modulegraphics.elements.Element;


/**
 * Forwards {@link MouseEvent}s to child of an {@link Element} that has been hit.
 * @author thpar
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
				e.setSource(child);
				ml.mouseClicked(e);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
				e.setSource(child);
				ml.mouseEntered(e);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
				e.setSource(child);
				ml.mouseExited(e);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
				e.setSource(child);
				ml.mousePressed(e);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Element child = parent.getHitChild(e.getX(), e.getY());
		if (child!=null){
			for (MouseListener ml : child.getMouseListeners()){
				e.setSource(child);
				ml.mouseReleased(e);
			}
		}
	}

}
