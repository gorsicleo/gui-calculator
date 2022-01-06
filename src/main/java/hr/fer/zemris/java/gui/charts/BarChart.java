package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class that models simple histogram, X axis is defined by number of data
 * points provided with {@link XYValue} list, class also contains two short
 * strings for description of X and Y axis. Y axis is defined with smallest Y
 * value, biggest Y value and Y value step. <b>Values are read only, once
 * created {@link BarChart} object is immutable.</b>
 * 
 * @author gorsicleo
 *
 */
public class BarChart {

	private static final String INVALID_POINT = "There is a point in list that has smaller Y value than minimum Y";
	private static final String MAX_SMALLER_THAN_MIN = "Please give maximum Y that is bigger than given minimum Y";
	private static final String NEGATIVE_MIN_Y = "Please give positive minimum Y";
	
	private List<XYValue> points;
	private String descriptionX;
	private String descriptionY;
	
	private int minY;
	private int maxY;
	private int stepY;

	/**Creates new {@link BarChart}
	 * @param points list of {@link XYValue} objects representing data.
	 * @param descriptionX short description for X axis.
	 * @param descriptionY short description for Y axis.
	 * @param minY smallest Y value
	 * @param maxY biggest Y value
	 * @param stepY step between two Y marks on Y axis (increment)
	 */
	public BarChart(List<XYValue> points, String descriptionX, String descriptionY, int minY, int maxY, int stepY) {

		checkInputValidity(points, minY, maxY);
		
		this.points = points;
		this.descriptionX = descriptionX;
		this.descriptionY = descriptionY;
		this.minY = minY;
		this.maxY = maxY;
		this.stepY = stepY;
	}

	/**Method checks if given data satisfies requirements. 
	 * @param points must be between minY and maxY
	 * @param minY must be positive number.
	 * @param maxY must be greater than minY
	 * 
	 * @throws IllegalArgumentException if given data violates requirements.
	 */
	private void checkInputValidity(List<XYValue> points, int minY, int maxY) {
		if (minY < 0) {
			throw new IllegalArgumentException(NEGATIVE_MIN_Y);
		}

		if (maxY < minY) {
			throw new IllegalArgumentException(MAX_SMALLER_THAN_MIN);
		}

		points.forEach((point) -> {
			if (point.getY() < minY) {
				throw new IllegalArgumentException(INVALID_POINT);
			}
		});
	}

	/**Returns graph points.
	 * @return List of XYValue
	 */
	public List<XYValue> getPoints() {
		return points;
	}

	/**Returns short description for X axis. */
	public String getDescriptionX() {
		return descriptionX;
	}

	/**Returns short description for Y axis. */
	public String getDescriptionY() {
		return descriptionY;
	}

	/**Returns smallest Y value */
	public int getMinY() {
		return minY;
	}

	/**Returns biggest Y value */
	public int getMaxY() {
		return maxY;
	}

	/**Returns step between two Y marks on Y axis (increment) */
	public int getStepY() {
		return stepY;
	}

}
