package be.ugent.psb.modulegraphics.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import net.sf.epsgraphics.ColorMode;
import net.sf.epsgraphics.EpsGraphics;
import be.ugent.psb.modulegraphics.elements.Canvas;

public class CanvasFigure {

	

	private Canvas canvas;
	private String outputFileName;
	
	public CanvasFigure(Canvas canvas, String outputFileName){
		this.canvas = canvas;
		this.outputFileName = outputFileName;
	}
	
	public void writeToEPS(){
		
		File outputFile = new File(outputFileName + ".eps");

		BufferedImage tmp_img = new BufferedImage(200,200, BufferedImage.TYPE_INT_BGR);
		Graphics2D tmp_g;
		tmp_g = tmp_img.createGraphics();
		tmp_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension dim = canvas.getDimension(tmp_g);

		//output to eps
		try{
			EpsGraphics g2 = new EpsGraphics(outputFileName, 
					new FileOutputStream(outputFile),
					0,
					0,
					dim.width,
					dim.height,
					ColorMode.COLOR_CMYK);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


			g2.setPaint(Color.WHITE);
			g2.fillRect(0, 0, dim.width, dim.height);

			canvas.paint(g2);

			g2.flush();
			g2.close();
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println(e);
			System.exit(1);
		}
	}


}
