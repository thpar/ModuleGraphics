package be.ugent.psb.modulegraphics.elements;

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

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class DropShape implements Shape {

	GeneralPath path = new GeneralPath();
	
	enum Direction{
		HORIZONTAL, VERTICAL;
	}
	
	public DropShape(int x, int y, int width, int height){
		this(x, y, width,height, Direction.HORIZONTAL);
	}
	public DropShape(int x, int y, int width, int height, Direction dir){
		float x0 = 0;
		float y0 = 0;
		
		float x1 = 0;
		float y1 = 0;
		
		float x2 = 0;
		float y2 = 0;
		
		float x3 = 0;
		float y3 = 0;
		
		float x4 = 0;
		float y4 = 0;
		
		float x5 = 0;
		float y5 = 0;
		
		
		switch(dir){
		case HORIZONTAL:
			x0 = x + 1f   * width;
			y0 = y + 0.5f * height;

			x1 = x + 0.3f * width;
			y1 = y + 0f   * height;
			x2 = x + 0f   * width;
			y2 = y + 0f   * height;
			x3 = x + 0f   * width;
			y3 = y + 0.5f * height;

			x4 = x + 0f   * width;
			y4 = y + 1f   * height;

			x5 = x + 0.3f * width;
			y5 = y + 1f   * height;
			break;
		case VERTICAL:
			x0 = x + 0.5f   * width;
			y0 = y + 1f * height;

			x1 = x + 1f * width;
			y1 = y + 0.3f   * height;
			x2 = x + 1f   * width;
			y2 = y + 0f   * height;
			x3 = x + 0.5f   * width;
			y3 = y + 0f * height;

			x4 = x + 0f   * width;
			y4 = y + 0f   * height;

			x5 = x + 0f * width;
			y5 = y + 0.3f   * height;


			break;
		}

		path.moveTo(x0, y0);
		path.curveTo(x1, y1, x2, y2, x3, y3);
		path.curveTo(x4, y4, x5, y5, x0, y0);
		
	}
	
	@Override
	public Rectangle getBounds() {
		return path.getBounds();
	}

	@Override
	public Rectangle2D getBounds2D() {
		return path.getBounds2D();
	}

	@Override
	public boolean contains(double x, double y) {
		return path.contains(x,y);
	}

	@Override
	public boolean contains(Point2D p) {
		return path.contains(p);
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return path.intersects(x, y, w, h);
	}

	@Override
	public boolean intersects(Rectangle2D r) {
		return path.intersects(r);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return path.contains(x, y, w, h);
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return path.contains(r);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		return path.getPathIterator(at);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return path.getPathIterator(at, flatness);
	}
	
	
	

}
