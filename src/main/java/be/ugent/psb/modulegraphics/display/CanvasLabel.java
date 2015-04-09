package be.ugent.psb.modulegraphics.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import be.ugent.psb.modulegraphics.clickable.ElementEventPassThrough;
import be.ugent.psb.modulegraphics.elements.Canvas;

public class CanvasLabel extends JLabel{

	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private Color background = Color.WHITE;
	private ImageIcon splash;
	
	private double zoomLevel = 1;
	
	public CanvasLabel(){
	}
	public CanvasLabel(Canvas canvas){
		setCanvas(canvas);
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * Set the Canvas on this label. Mouselisteners will be passed on from the JLabel to the Canvas
	 * and the preferred size will be switched to the dimensions of the Canvas.
	 * If canvas is null, the size is switched to that of the splash (if set) 
	 */
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
		if (canvas != null){
			canvas.setContainer(this);
			for (MouseListener ml : getMouseListeners()){
				if (ml instanceof ElementEventPassThrough){
					this.removeMouseListener(ml);
				}
			}
			this.addMouseListener(new ElementEventPassThrough(canvas));
		}
		if (this.getGraphics() !=null){
			this.setSize();
			this.repaint();			
		}
	}

	public void setBackground(Color background) {
		this.background = background;
	}


	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		this.setSize();
		if (canvas!=null){
			g2.setColor(background);
			g2.scale(zoomLevel, zoomLevel);
			g2.fillRect(0, 0, canvas.getDimension(g2).width, canvas.getDimension(g2).height);
			canvas.paint(g2, 0, 0);
			g2.scale(1/zoomLevel, 1/zoomLevel);
		} else {
			if (splash!=null){
				splash.paintIcon(this, g2, 0, 0);
			}
		}
	}

	public ImageIcon getSplash() {
		return splash;
	}

	public void setSplash(ImageIcon splash) {
		this.splash = splash;
	}
	
	protected void setSize(){
		if (canvas!=null){
			Graphics2D g2 = this.getGraphics();
			double width  = canvas.getDimension(g2).getWidth()*this.zoomLevel;
			double height = canvas.getDimension(g2).getHeight()*this.zoomLevel;
			this.setPreferredSize(new Dimension((int)Math.round(width), (int)Math.round(height)));
		} else if (splash!=null){
			this.setPreferredSize(new Dimension(splash.getIconWidth(), splash.getIconHeight()));					
		} else {
			this.setPreferredSize(new Dimension(250,200));
		}
		this.revalidate();
	}
	
	
	public Graphics2D getGraphics() {
		return (Graphics2D)super.getGraphics();
	}
	
	public double getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(double zoomLevel) {
		this.zoomLevel = zoomLevel;
		this.setSize();
	}
	
	
	
}
