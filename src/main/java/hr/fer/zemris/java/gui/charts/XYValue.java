package hr.fer.zemris.java.gui.charts;

/**
 * Simple model for a point in 2D space. Object is immutable once created.
 * 
 * @author gorsicleo
 */
public class XYValue {

	private final int x;

	private final int y;

	/**creates new 2D point.
	 * @param x
	 * @param y
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**Returns x coordinate of point.*/
	public int getX() {
		return x;
	}

	/**Returns y coordinate of point.*/
	public int getY() {
		return y;
	}

}
