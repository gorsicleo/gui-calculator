package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class BarChartDemo extends JFrame {

	private BarChart barChart;

	public BarChartDemo(BarChart barChart) {
		this.barChart = barChart;
		initGUI();
	}


	private void initGUI() {
		setSize(500, 500);
		setTitle("Grapher");
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
