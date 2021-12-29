package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

	private static final String ILLEGAL_ARGUMENT_MESSAGE = "Argument: %s cannot be recognized as format (row, column)";

	private int row;
	
	private int column;
	

	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	

	public int getRow() {
		return row;
	}


	public int getColumn() {
		return column;
	}

	
	public static RCPosition parse(String text) {
		String[] data = text.split(",");
		
		try {
			return new RCPosition(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(String.format(ILLEGAL_ARGUMENT_MESSAGE, text));
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;

		return (column == other.column) && (row == other.row);
	}
	
	
	
	
}
