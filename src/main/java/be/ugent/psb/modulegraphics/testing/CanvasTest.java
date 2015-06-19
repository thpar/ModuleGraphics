package be.ugent.psb.modulegraphics.testing;

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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;

import be.ugent.psb.modulegraphics.display.CanvasFigure;
import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Canvas;
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
		
		CanvasFigure fc = new CanvasFigure(c, "test");
		try {
			fc.writeToEPS();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
