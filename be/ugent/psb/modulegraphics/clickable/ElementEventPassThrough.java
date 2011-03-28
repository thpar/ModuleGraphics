package be.ugent.psb.modulegraphics.clickable;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import be.ugent.psb.modulegraphics.elements.Element;

/**
 * Passes on {@link MouseEvent}s from a {@link Component} 
 * on to all listeners of a top{@link Element}
 * 
 * @author thpar
 *
 */
public class ElementEventPassThrough implements MouseListener {

	private Element topElement;
	
	
	public ElementEventPassThrough(Element element) {
		this.topElement = element;
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		for (MouseListener ml : topElement.getMouseListeners()){
			e.setSource(topElement);
			ml.mouseClicked(e);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (MouseListener ml : topElement.getMouseListeners()){
			e.setSource(topElement);
			ml.mouseEntered(e);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (MouseListener ml : topElement.getMouseListeners()){
			e.setSource(topElement);
			ml.mouseExited(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MouseListener ml : topElement.getMouseListeners()){
			e.setSource(topElement);
			ml.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MouseListener ml : topElement.getMouseListeners()){
			e.setSource(topElement);
			ml.mouseReleased(e);
		}
	}
	
}
