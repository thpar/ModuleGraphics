package be.ugent.psb.modulegraphics.display;

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
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sf.epsgraphics.ColorMode;
import net.sf.epsgraphics.EpsGraphics;
import be.ugent.psb.modulegraphics.elements.Canvas;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Draws a {@link Canvas} on a Graphics object for a specific output format.
 *  
 * @author Thomas Van Parys
 *
 */
public class CanvasFigure {

	

	private Canvas canvas;
	private String outputFileName;
	
	public CanvasFigure(Canvas canvas, String outputFileName){
		this.canvas = canvas;
		this.outputFileName = outputFileName;
	}
	
	public enum OutputFormat{
		EPS, PDF, PNG;
		
		@Override
		public String toString(){
			return this.name().toLowerCase();
		}
	}

	/**
	 * Export the {@link Canvas} to the desired output format.
	 * 
	 * @param format EPS, PDF or PNG
	 * @throws IOException
	 */
	public void writeToFigure(OutputFormat format) throws IOException{
		switch(format){
		case EPS: writeToEPS();
		break;
		case PDF: writeToPDF();
		break;	
		case PNG: writeToPNG();
		break;	
		}
	}
	

	private Dimension getDimension(){
		BufferedImage tmp_img = new BufferedImage(200,200, BufferedImage.TYPE_INT_BGR);
		Graphics2D tmp_g;
		tmp_g = tmp_img.createGraphics();
		tmp_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension dim = canvas.getDimension(tmp_g);
		return dim;
	}
	
	
	private void writeToPNG() throws IOException{
		File outputFile = new File(outputFileName);
		Dimension dim = this.getDimension();
						
		BufferedImage bi = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_BGR);
		Graphics2D g2 = bi.createGraphics();
		
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, dim.width, dim.height);
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.canvas.paint(g2);
		
		ImageIO.write(bi, "png", outputFile);
			
	}
	
	public void writeToEPS() throws IOException{
		
		File outputFile = new File(outputFileName);
		Dimension dim = getDimension();

		//output to eps
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
	
	public void writeToPDF() throws FileNotFoundException{
		Dimension dim = getDimension();
		Document document = new Document(new Rectangle(dim.width, dim.height));
		PdfWriter writer = null;
		

		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(outputFileName));

			document.open();

			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(dim.width, dim.height);
			Graphics2D g2 = tp.createGraphics(dim.width, dim.height, new DefaultFontMapper());

			// Create your graphics here - draw on the g2 Graphics object
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


			g2.setPaint(Color.WHITE);
			g2.fillRect(0, 0, dim.width, dim.height);

			canvas.paint(g2);
			
			g2.dispose();
			cb.addTemplate(tp, 0, 0); 
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally{
			document.close();
			writer.close();
		}
	}


}
