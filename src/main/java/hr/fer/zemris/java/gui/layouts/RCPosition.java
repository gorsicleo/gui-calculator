package hr.fer.zemris.java.gui.layouts;

/**Model for component position in {@link CalcLayout}.
 * @author gorsicleo
 *
 */
public class RCPosition {

	private static final String ILLEGAL_ARGUMENT_MESSAGE = "Argument: %s cannot be recognized as format (row, column)";

	private int row;
	
	private int column;
	

	/**Creates new {@link RCPosition} at given <code>row</code> and <code>column</code>
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	

	/**Returns row of this position*/
	public int getRow() {
		return row;
	}

	/**Returns column of this position*/
	public int getColumn() {
		return column;
	}

	/**Creates {@link RCPosition} with row and column data parsed from input string in format of x,y
	 * @throws IllegalArgumentException if string cannot be parsed to {@link RCPosition}
	 * */
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
