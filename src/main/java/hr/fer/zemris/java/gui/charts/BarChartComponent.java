package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	private static final int LEFT_MARGIN = 40;
	private static final int RIGHT_MARGIN = 30;
	private static final int BOTTOM_MARGIN = 30;
	private static final int ARROW_HEAD_OFFSET = 18;

	private BarChart barChart;
	private int count;
	private Random rand = new Random();
	private Color histogramColor;

	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		histogramColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		draw(g2D);
	}

	public void draw(Graphics2D graphic) {
		List<XYValue> points = barChart.getPoints();

		int yStart = getBounds().height - 2 * BOTTOM_MARGIN;
		int xEnd = getBounds().width - RIGHT_MARGIN + ARROW_HEAD_OFFSET;
		int step = getBounds().height / ((barChart.getMaxY() - barChart.getMinY()) / barChart.getStepY());
		
		

		drawCoordinates2(graphic, yStart, xEnd, step);

		drawHistogram(graphic, points);
		
		drawDescriptionX(graphic);
		drawDescriptionY(graphic);

	}

	private void drawDescriptionY(Graphics2D graphic) {
		String text = barChart.getDescriptionY();
		
		AffineTransform at = new AffineTransform();
		at.rotate(-1*Math.PI / 2);
		 
		graphic.setTransform(at);
		graphic.setFont(new Font("Arial", Font.BOLD, 16));
		graphic.drawString(text, -1*(getBounds().height+2*(BOTTOM_MARGIN+ARROW_HEAD_OFFSET))/2, 20);
		
		at.rotate(Math.PI / 2);
		graphic.setTransform(at);
	}

	private void drawDescriptionX(Graphics2D graphic) {
		String text = barChart.getDescriptionX();
		
		graphic.drawString(text, getBounds().width/2, getBounds().height-BOTTOM_MARGIN);
		
	}

	private void drawCoordinates2(Graphics2D graphic, int yStart, int xEnd, int step) {
		drawCoordinateArrows(graphic, yStart, xEnd);
		drawHorizontalNumbersAndLines(graphic, xEnd, step);
	}

	private void drawHorizontalNumbersAndLines(Graphics2D graphic, int xEnd, int step) {
		for (int i = barChart.getMinY(); i < barChart.getMaxY(); i = i + barChart.getStepY()) {

			int yForStep = getBounds().height - (i / barChart.getStepY()) * step - 2 * BOTTOM_MARGIN;
			if (yForStep - 30 < 0)
				continue;

			graphic.drawLine(LEFT_MARGIN, yForStep, xEnd, yForStep);

			if (i > 9) {
				graphic.drawString(String.valueOf(i), LEFT_MARGIN - 15, yForStep);
			} else {
				graphic.drawString(String.valueOf(i), LEFT_MARGIN - 10, yForStep);
			}

		}
	}

	private void drawCoordinateArrows(Graphics2D graphic, int yStart, int xEnd) {
		drawArrowLine(graphic, LEFT_MARGIN, yStart, LEFT_MARGIN, ARROW_HEAD_OFFSET, 10, 10);
		drawArrowLine(graphic, LEFT_MARGIN, yStart, xEnd, yStart, 10, 10);
	}

	/**
	 * Draw an arrow line between two points.
	 * 
	 * @param g  the graphics component.
	 * @param x1 x-position of first point.
	 * @param y1 y-position of first point.
	 * @param x2 x-position of second point.
	 * @param y2 y-position of second point.
	 * @param d  the width of the arrow.
	 * @param h  the height of the arrow.
	 */
	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - d, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.drawLine(x1, y1, x2, y2);
		g.fillPolygon(xpoints, ypoints, 3);
	}

	private void drawHistogram(Graphics2D graphic, List<XYValue> points) {
		int width = getBounds().width - LEFT_MARGIN;
		int height = getBounds().height - BOTTOM_MARGIN;
		count = 0;

		points.forEach((point) -> {
			int barWidth = (width - RIGHT_MARGIN) / points.size();
			int barHeight = Math.round((height + ARROW_HEAD_OFFSET) * point.getY() / barChart.getMaxY());

			Rectangle bar = new Rectangle(LEFT_MARGIN + (point.getX() - 1) * barWidth,
					height - barHeight - BOTTOM_MARGIN, barWidth, barHeight);
			graphic.setColor(histogramColor);
			graphic.fill(bar);
			graphic.setColor(Color.BLACK);
			drawCoordinates1(graphic, point, barWidth);
			graphic.draw(bar);
			count++;
		});
	}

	private void drawCoordinates1(Graphics2D graphic, XYValue point, int barWidth) {
		int xStart = LEFT_MARGIN + (point.getX() - 1) * barWidth;
		graphic.drawLine(barWidth + xStart, getBounds().height - 2 * BOTTOM_MARGIN, barWidth + xStart,
				ARROW_HEAD_OFFSET);
		graphic.drawString(String.valueOf(count), xStart + (barWidth / 2), getBounds().height - BOTTOM_MARGIN*2+ARROW_HEAD_OFFSET);
	}

}
