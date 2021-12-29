package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CalcLayout implements LayoutManager2 {

	private static final String FULL_LAYOUT_MESSAGE = "Maximum number of components reached";
	private static final String NULL_ARGUMENT_ERROR = "Please provide not-null argumenets";
	private static final String NOT_IMPLEMENTED_MESSAGE = "Not implemented";
	private static final String ILLEGAL_CONSTRAINT_TYPE = "Not legal type of constraint, please use only RCPosition.";
	private static final String CONSTRAINT_ALREADY_EXISTS_MESSAGE = "Contraint already exists";
	private static final String RESERVED_POSITION_MESSAGE = "That position is reserved.";
	private static final String ILLEGAL_COLUMN_MESSAGE = "Illegal column constraint";
	private static final String ILLEGAL_ROW_MESSAGE = "Illegal row constraint";
	private static final int ROWS = 5;
	private static final int COLUMNS = 7;
	private static final int MAX_NUMBER_OF_COMPONENTS = 31;
	private static final RCPosition FIRST_COMPONENT = new RCPosition(1, 1);
	private int space;

	private Set<RCPosition> allConstraints;
	private Map<Component, RCPosition> components;

	/**
	 * Constructor, sets spacers between components to 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor, sets spacers between components to a given value.
	 * 
	 * @param space gap between two neighbouring components.
	 */
	public CalcLayout(int space) {
		allConstraints = new HashSet<>();
		this.space = space;
		components = new HashMap<>();
	}

	private void checkConstraintValidity(RCPosition constraint) {
		int row = constraint.getRow();
		int col = constraint.getColumn();

		if (row > 5 || row < 1) {
			throw new CalcLayoutException(ILLEGAL_ROW_MESSAGE);
		}

		if (col < 1 || col > 7) {
			throw new CalcLayoutException(ILLEGAL_COLUMN_MESSAGE);
		}

		if (row == 1 && col > 1 && col < 6) {
			throw new CalcLayoutException(RESERVED_POSITION_MESSAGE);
		}
	}

	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException(NOT_IMPLEMENTED_MESSAGE);

	}

	public void removeLayoutComponent(Component comp) {
		RCPosition constraint = components.get(comp);
		allConstraints.remove(constraint);
		components.remove(comp);
	}

	public Dimension preferredLayoutSize(Container parent) {
		int height = components.entrySet().stream()
				.mapToInt((entry) -> entry.getKey().getPreferredSize().height)
				.max().getAsInt();
		int width = components.entrySet().stream()
				.filter( entry -> !entry.getValue().equals(FIRST_COMPONENT))
				.mapToInt((entry) -> entry.getKey().getPreferredSize().width)
				.max().getAsInt();
		
		
		return createDimensionFromValues(parent, height, width);
	}
	
	
	

	public Dimension minimumLayoutSize(Container parent) {
		int height = components.entrySet().stream().mapToInt((entry) -> entry.getKey().getMinimumSize().height)
				.max().getAsInt();
		int width = components.entrySet().stream()
				.filter( entry -> !entry.getValue().equals(FIRST_COMPONENT))
				.mapToInt((entry) -> entry.getKey().getMinimumSize().width)
				.max().getAsInt();
		
		
		return createDimensionFromValues(parent, height, width);
	}

	
	private Dimension createDimensionFromValues(Container parent, int height, int width) {
		return new Dimension(
				parent.getInsets().left + parent.getInsets().right + COLUMNS * width + (COLUMNS - 1) * space,
				parent.getInsets().top + parent.getInsets().bottom + ROWS * height + (ROWS - 1) * space
		);
	}

	private Dimension getComponentDimension() {
		int height = components.entrySet().stream().mapToInt((entry) -> entry.getKey().getPreferredSize().height)
				.max().getAsInt();
		int width = components.entrySet().stream().mapToInt((entry) -> entry.getKey().getPreferredSize().width)
				.max().getAsInt();
		
		return new Dimension(width, height);
	}
	
	public void layoutContainer(Container parent) {
		
		Dimension d = getComponentDimension();
		double ratioX = (double) parent.getWidth() / preferredLayoutSize(parent).getWidth();
		double ratioY = (double) parent.getHeight() / preferredLayoutSize(parent).getHeight();

		d.setSize(ratioX * d.getWidth(),  ratioY * d.getHeight());

		
		components.entrySet()
		.stream()
		.forEach(entry-> {
			if (entry.getValue().equals(FIRST_COMPONENT)) {
				entry.getKey().setBounds(0, 0, 5 * d.width + 4 * space, d.height);
				return;
			}
			entry.getKey().setBounds(
					(entry.getValue().getColumn() - 1) * (d.width + space),
					(entry.getValue().getRow() - 1) * (d.height + space),
					d.width,
					d.height
			);
		});
		

	}

	public void addLayoutComponent(Component comp, Object constraints) {
		RCPosition constraint = null;

		if (comp == null || constraints == null) {
			throw new NullPointerException(NULL_ARGUMENT_ERROR);
		}

		if (constraints instanceof String) {
			constraint = RCPosition.parse((String) constraints);
		} else if (constraints instanceof RCPosition) {
			constraint = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException(ILLEGAL_CONSTRAINT_TYPE);
		}

		if (components.size() >= MAX_NUMBER_OF_COMPONENTS) {
			throw new IllegalArgumentException(FULL_LAYOUT_MESSAGE);
		}
		if (!allConstraints.add(constraint)) {
			throw new CalcLayoutException(CONSTRAINT_ALREADY_EXISTS_MESSAGE);
		}
		checkConstraintValidity(constraint);

		components.put(comp, constraint);
	}

	public Dimension maximumLayoutSize(Container target) {
		int height = components.entrySet().stream()
				.mapToInt((entry) -> entry.getKey().getMaximumSize().height)
				.max().getAsInt();
		int width = components.entrySet().stream()
				.filter( entry -> !entry.getValue().equals(FIRST_COMPONENT))
				.mapToInt((entry) -> entry.getKey().getMaximumSize().width)
				.max().getAsInt();
		
		
		return createDimensionFromValues(target, height, width);
	}

	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	public void invalidateLayout(Container target) {
	}

	

}
