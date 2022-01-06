package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

/**
 * Class that draws histogram along with coordinate system and short description
 * for X and Y axis. Class must be provided with {@link BarChart} in order to
 * draw histogram.
 * 
 * @author gorsicleo
 *
 */
@SuppressWarnings("serial")
public class BarChartComponent extends JComponent {

	private static final int LEFT_MARGIN = 40;
	private static final int RIGHT_MARGIN = 30;
	private static final int BOTTOM_MARGIN = 60;
	private static final int TOP_MARGIN = 18;
	private static final int ARROW_MARGIN = 10;

	private BarChart barChart;

	/** Number of bars drawn */
	private int count;

	/** Used for creating random color for histogram fill */
	private Random rand = new Random();
	private Color histogramColor;

	/**
	 * Creates new {@link BarChartComponent} object and draws histogram with given
	 * barChart
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		histogramColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		draw(g2D);
	}

	/**
	 * Calls methods to draw histogram along with coordinate system.
	 * 
	 * @param graphic
	 */
	private void draw(Graphics2D graphic) {
		List<XYValue> points = barChart.getPoints();

		int yStart = getBounds().height - BOTTOM_MARGIN;
		int xEnd = getBounds().width - RIGHT_MARGIN + TOP_MARGIN;
		int step = (getBounds().height - BOTTOM_MARGIN - TOP_MARGIN - ARROW_MARGIN)
				/ (barChart.getMaxY() / barChart.getStepY());

		drawCoordinates2(graphic, yStart, xEnd, step);

		drawHistogram(graphic, points);

		drawDescriptionX(graphic);
		drawDescriptionY(graphic);
	}

	/**
	 * Draws rotated string of barChart description for Y axis.
	 * 
	 * @param graphic
	 */
	private void drawDescriptionY(Graphics2D graphic) {
		String text = barChart.getDescriptionY();

		AffineTransform at = new AffineTransform();
		at.rotate(-1 * Math.PI / 2);

		graphic.setTransform(at);
		graphic.setFont(new Font("Arial", Font.BOLD, 16));
		graphic.drawString(text, -1 * (getBounds().height - (BOTTOM_MARGIN + TOP_MARGIN)), 20);

		at.rotate(Math.PI / 2);
		graphic.setTransform(at);
	}

	/**
	 * Draws string of barChart description for X axis on bottom of component.
	 * 
	 * @param graphic
	 */
	private void drawDescriptionX(Graphics2D graphic) {
		String text = barChart.getDescriptionX();
		int textLength = text.length();

		graphic.drawString(text, getBounds().width / 2 - textLength * 2, getBounds().height - BOTTOM_MARGIN / 2);

	}

	/**
	 * Draws coordinate arrows for X and Y axis. Also draws horizontal lines for
	 * coordinate grid together with Y axis numbers.
	 * 
	 * @param graphic
	 * @param yStart
	 * @param xEnd
	 * @param step
	 */
	private void drawCoordinates2(Graphics2D graphic, int yStart, int xEnd, int step) {
		drawCoordinateArrows(graphic, yStart, xEnd);
		drawHorizontalNumbersAndLines(graphic, xEnd, step);
	}

	/**
	 * Draws horizontal lines for coordinate grid together with Y axis numbers.
	 * 
	 * @param graphic
	 * @param xEnd
	 * @param step
	 */
	private void drawHorizontalNumbersAndLines(Graphics2D graphic, int xEnd, int step) {

		int height = getBounds().height - BOTTOM_MARGIN;
		int count = 0;
		for (int i = barChart.getMinY(); i <= barChart.getMaxY(); i = i + barChart.getStepY()) {

			int yForStep = height - (count * step);

			graphic.drawLine(LEFT_MARGIN, yForStep, xEnd, yForStep);

			if (i > 9) {
				graphic.drawString(String.valueOf(i), LEFT_MARGIN - 15, yForStep);
			} else {
				graphic.drawString(String.valueOf(i), LEFT_MARGIN - 10, yForStep);
			}
			
			count++;
		}
	}

	/**
	 * Draws two arrow lines one representing X axis, second representing Y axis.
	 * 
	 * @param graphic
	 * @param yStart
	 * @param xEnd
	 */
	private void drawCoordinateArrows(Graphics2D graphic, int yStart, int xEnd) {
		drawArrowLine(graphic, LEFT_MARGIN, yStart, LEFT_MARGIN, TOP_MARGIN, 10, 10);
		drawArrowLine(graphic, LEFT_MARGIN, yStart, xEnd, yStart, 10, 10);
	}

	/**
	 * Draws arrow line, where start of arrow is represented with (x1,y1) and arrow
	 * tip is at (x2,y2)
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

	/**
	 * Method draws rectangles (bars) for every point of data extracted from
	 * barChart.
	 * 
	 * @param graphic
	 * @param points
	 */
	private void drawHistogram(Graphics2D graphic, List<XYValue> points) {
		count = 0;
		int width = getBounds().width - LEFT_MARGIN - RIGHT_MARGIN;
		int height = getBounds().height - BOTTOM_MARGIN - TOP_MARGIN - ARROW_MARGIN;

		points.forEach((point) -> {
			int barWidth = width / points.size();
			int barHeight = Math.round(height * point.getY() / (barChart.getMaxY()));

			Rectangle bar = new Rectangle(LEFT_MARGIN + (point.getX() - 1) * barWidth,
					height - barHeight + TOP_MARGIN + ARROW_MARGIN, barWidth, barHeight);
			graphic.setColor(histogramColor);
			graphic.fill(bar);
			graphic.setColor(Color.BLACK);
			drawCoordinates1(graphic, point, barWidth);
			graphic.draw(bar);
			count++;
		});
	}

	/**
	 * Method draws vertical lines for coordinate grid and corresponding numbers
	 * below X axis representing bar count.
	 * 
	 * @param graphic
	 * @param point
	 * @param barWidth
	 */
	private void drawCoordinates1(Graphics2D graphic, XYValue point, int barWidth) {
		int xStart = LEFT_MARGIN + (point.getX() - 1) * barWidth;
		graphic.drawLine(barWidth + xStart, getBounds().height - BOTTOM_MARGIN - TOP_MARGIN, barWidth + xStart,
				TOP_MARGIN);
		graphic.drawString(String.valueOf(count + 1), xStart + (barWidth / 2),
				getBounds().height - BOTTOM_MARGIN - TOP_MARGIN / 4);
	}

}
