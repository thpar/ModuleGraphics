package be.ugent.psb.modulegraphics.testing;

import javax.swing.JFrame;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Canvas;

public class LabelTest {

	public static void main(String[] args) {
		LabelTest test = new LabelTest();
		test.run();
	}
	
	
	public void run(){
		Canvas c = new Canvas();
		
		
		
		JFrame frame = new JFrame("Test Label");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CanvasLabel label = new CanvasLabel(c);
		frame.add(label);
		
		frame.pack();
		frame.setVisible(true);
		
		
		
	}

}
