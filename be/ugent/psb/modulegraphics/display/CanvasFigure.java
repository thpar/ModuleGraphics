package be.ugent.psb.modulegraphics.display;

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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

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
