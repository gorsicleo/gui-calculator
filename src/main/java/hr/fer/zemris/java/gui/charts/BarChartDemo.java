package hr.fer.zemris.java.gui.charts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Class that demonstrates functionality of {@link BarChartComponent}. Class
 * loads .txt file and constructs new {@link BarChart} and calls
 * {@link BarChartComponent} to draw histogram.
 * 
 * @author gorsicleo
 *
 */
@SuppressWarnings("serial")
public class BarChartDemo extends JFrame {

	private static final String WINDOW_TITLE = "Colorful Grapher";
	/** {@link BarChart} containing histogram data */
	private BarChart barChart;

	/**
	 * Creates new {@link BarChartDemo} and sets frame size to 500x500 size, then
	 * calls methods to draw histogram
	 */
	public BarChartDemo(BarChart barChart) {
		this.barChart = barChart;
		initGUI();
	}

	/**Sets size, title and close operation for new Frame.
	 * Constructs new {@link BarChartComponent} that will be added to frame.
	 */
	private void initGUI() {
		setSize(500, 500);
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		add(new BarChartComponent(barChart));
		setVisible(true);

	}

	public static void main(String[] args) {
		BarChart barChart;
		try {
			Scanner sc = new Scanner(new File(args[0]));
			String descriptionX = sc.nextLine();
			String descriptionY = sc.nextLine();
			List<XYValue> points = new LinkedList<XYValue>();

			String[] unparsedPoints = sc.nextLine().split(" ");
			for (String point : unparsedPoints) {
				int x = Integer.parseInt(point.split(",")[0]);
				int y = Integer.parseInt(point.split(",")[1]);
				points.add(new XYValue(x, y));
			}

			int minY = Integer.parseInt(sc.nextLine());
			int maxY = Integer.parseInt(sc.nextLine());
			int step = Integer.parseInt(sc.nextLine());

			barChart = new BarChart(points, descriptionX, descriptionY, minY, maxY, step);
			sc.close();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("can't open file!");
		}

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(barChart).setVisible(true);
		});
	}
}
