package be.ugent.psb.modulegraphics.testing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.display.FigureCanvas;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Rectangle;

public class CanvasTest implements MouseListener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CanvasTest test = new CanvasTest();
		test.clickTest();
	}


	
	
	
	private void clickTest() {
	
		Canvas c = new Canvas();
		
		Rectangle rec1 = new Rectangle(new Dimension(5,5), Color.BLACK);
		Rectangle rec2 = new Rectangle(new Dimension(5,5), Color.YELLOW);
		c.add(rec1);
		c.add(rec2);
		
		rec1.addMouseListener(this);
		rec2.addMouseListener(this);
		
		JFrame frame = new JFrame("Test Window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CanvasLabel label = new CanvasLabel(c);
		frame.add(label);
		
		frame.pack();
		frame.setVisible(true);
		
		FigureCanvas fc = new FigureCanvas(c, "test");
		fc.writeToEPS();
		
	}




	
	@Override
	public void mouseClicked(MouseEvent e) {
		Rectangle r = (Rectangle)e.getSource();
		Color color = r.getColor();
		r.toggleHighlighted();
		System.out.println(color);
	}




	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
