package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {
	
	private static final int LEFT_MARGIN = 30;
	private static final int RIGHT_MARGIN = 30;
	private static final int BOTTOM_MARGIN = 30;
	private static final int ARROW_HEAD_OFFSET = 20;

	private BarChart barChart;


	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		draw(g2D);
	}

	public void draw(Graphics2D graphic) {
		List<XYValue> points = barChart.getPoints();
		
		drawArrowLine(graphic, LEFT_MARGIN, getBounds().height-2*BOTTOM_MARGIN , LEFT_MARGIN, ARROW_HEAD_OFFSET, 10, 10);
		
		drawArrowLine(graphic, LEFT_MARGIN, getBounds().height-2*BOTTOM_MARGIN ,getBounds().width-RIGHT_MARGIN+ARROW_HEAD_OFFSET, getBounds().height-2*BOTTOM_MARGIN, 10, 10);
		
		int step = getBounds().height/((barChart.getMaxY()-barChart.getMinY())/barChart.getStepY());
		for (int i=barChart.getMinY(); i<barChart.getMaxY(); i = i + barChart.getStepY()) {
			if (getBounds().height-(i/barChart.getStepY())*step-2*BOTTOM_MARGIN-30 < 0) continue;
			if (i > 9) {
				graphic.drawString(String.valueOf(i), LEFT_MARGIN-15, getBounds().height-(i/barChart.getStepY())*step-2*BOTTOM_MARGIN);
			} else {
				graphic.drawString(String.valueOf(i), LEFT_MARGIN-10, getBounds().height-(i/barChart.getStepY())*step-2*BOTTOM_MARGIN);
			}
			
		}
		
		drawHistogram(graphic, points);
	    
		
		
		
	}
	
	
	/**
	 * Draw an arrow line between two points.
	 * @param g the graphics component.
	 * @param x1 x-position of first point.
	 * @param y1 y-position of first point.
	 * @param x2 x-position of second point.
	 * @param y2 y-position of second point.
	 * @param d  the width of the arrow.
	 * @param h  the height of the arrow.
	 */
	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
	    int dx = x2 - x1, dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = D - d, xn = xm, ym = h, yn = -h, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};

	    g.drawLine(x1, y1, x2, y2);
	    g.fillPolygon(xpoints, ypoints, 3);
	}

	private void drawHistogram(Graphics2D graphic, List<XYValue> points) {
		int width = getBounds().width-LEFT_MARGIN;
		int height = getBounds().height-BOTTOM_MARGIN;
		points.forEach((point) -> {
			int barWidth = (width-RIGHT_MARGIN)/points.size();
			int barHeight = Math.round(height * point.getY()/barChart.getMaxY());
			
			Rectangle bar = new Rectangle(LEFT_MARGIN+(point.getX()-1)*barWidth, height-barHeight-BOTTOM_MARGIN, barWidth, barHeight);
			graphic.setColor(Color.decode("#3faafc"));
			graphic.fill(bar);
			graphic.setColor(Color.BLACK);
			graphic.draw(bar);
		});
	}

}
