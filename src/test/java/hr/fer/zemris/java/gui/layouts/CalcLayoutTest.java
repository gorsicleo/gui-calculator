package hr.fer.zemris.java.gui.layouts;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CalcLayoutTest {

	@Test
	public void addAlreadyExistingConstraintTest() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), new RCPosition(1, 1));
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(), new RCPosition(1, 1)));
	}

	@Test
	public void illegalConstraintTest() {
		CalcLayout layout = new CalcLayout();
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(), new RCPosition(-1, 1)));
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(), new RCPosition(1, -1)));
		assertThrows(CalcLayoutException.class, () -> layout.addLayoutComponent(new JLabel(), new RCPosition(1, 8)));
		assertThrows(IllegalArgumentException.class, () -> layout.addLayoutComponent(new JLabel(), Integer.valueOf(1)));
		assertThrows(IllegalArgumentException.class, () -> layout.addLayoutComponent(new JLabel(), "1.2"));
	}
	
	
	@Test
	public void stringConstraintsTest() {
		CalcLayout layout = new CalcLayout();
		layout.addLayoutComponent(new JLabel(), "1,1");
		layout.addLayoutComponent(new JButton(), "2,1");
		layout.addLayoutComponent(new JButton(), "3,1");
	}
	
	@Test
	public void sizePreferencesTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);

	}
	
	
	@Test
	@Disabled
	public void sizePreferencesTest2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}
}
