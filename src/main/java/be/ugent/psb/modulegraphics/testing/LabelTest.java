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

import java.awt.Dimension;

import javax.swing.JFrame;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element.Alignment;
import be.ugent.psb.modulegraphics.elements.Label;

public class LabelTest {

	public static void main(String[] args) {
		LabelTest test = new LabelTest();
		test.run();
	}
	
	
	public void run(){
		Canvas c = new Canvas();
		
		Label k= new Label("Bonobo");
		Label l = new Label("Aracyc");
		
		
		k.setAlignment(Alignment.BOTTOM_LEFT);
		l.setAlignment(Alignment.BOTTOM_LEFT);
		
		c.add(k);
		c.add(l);
		c.newRow();
		
		Label m = new Label("Bonobo");
		Label n = new Label("Aracyc");
		
		m.setAngle(-Math.PI/4);
		n.setAngle(-Math.PI/4);
		
		m.setAlignment(Alignment.BOTTOM_LEFT);
		n.setAlignment(Alignment.BOTTOM_LEFT);
		
		c.add(m);
		c.add(n);
		c.newRow();
		
		
		Label o = new Label("Bonobo");
		Label p = new Label("Aracyc");
		
		o.setAngle(-Math.PI/2);
		p.setAngle(-Math.PI/2);
		
		o.setAlignment(Alignment.BOTTOM_RIGHT);
		p.setAlignment(Alignment.BOTTOM_RIGHT);
		
		c.add(o);
		c.add(p);
		c.newRow();
		
		
		
		JFrame frame = new JFrame("Test Label");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CanvasLabel label = new CanvasLabel(c);
		frame.add(label);
		
		frame.pack();
		frame.setVisible(true);
		frame.setSize(new Dimension(200,200));
		
	}

}
