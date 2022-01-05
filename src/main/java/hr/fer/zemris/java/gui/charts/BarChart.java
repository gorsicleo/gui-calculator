package hr.fer.zemris.java.gui.charts;

import java.util.List;

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
	
	
	public BarChart(List<XYValue> points, String descriptionX, String descriptionY, int minY, int maxY, int stepY) {

		checkInputValidity(points, minY, maxY);
		this.points = points;
		this.descriptionX = descriptionX;
		this.descriptionY = descriptionY;
		this.minY = minY;
		this.maxY = maxY;
		this.stepY = stepY;
	}


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


	public static String getInvalidPoint() {
		return INVALID_POINT;
	}


	public static String getMaxSmallerThanMin() {
		return MAX_SMALLER_THAN_MIN;
	}


	public static String getNegativeMinY() {
		return NEGATIVE_MIN_Y;
	}


	public List<XYValue> getPoints() {
		return points;
	}


	public String getDescriptionX() {
		return descriptionX;
	}


	public String getDescriptionY() {
		return descriptionY;
	}


	public int getMinY() {
		return minY;
	}


	public int getMaxY() {
		return maxY;
	}


	public int getStepY() {
		return stepY;
	}
	
	
	
	
	
	
	
}
