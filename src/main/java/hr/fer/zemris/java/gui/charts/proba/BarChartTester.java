package hr.fer.zemris.java.gui.charts.proba;

import javax.swing.JFrame;

public class BarChartTester
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();

      final int FRAME_WIDTH = 300;
      final int FRAME_HEIGHT = 400;

      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      frame.setTitle("BarChart");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      BarChartComponent component = new BarChartComponent();
      frame.add(component);

      frame.setVisible(true);
   }
}
