package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class BarChartDemo extends JFrame {

	private String descriptionY;
	private String descriptionX;
	private List<XYValue> points = List.of(new XYValue(1, 8), new XYValue(2, 20), new XYValue(3, 22),
			new XYValue(4, 10), new XYValue(5, 4), new XYValue(6, 2));
	private BarChart barChart = new BarChart(points, "opisX", "opisY", 0, 30, 2);

	public BarChartDemo() {
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
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo().setVisible(true);
		});
	}
}
