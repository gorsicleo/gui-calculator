package hr.fer.zemris.java.gui.layouts;

/**Class that models simple exception for invalid layout.
 * @author gorsicleo
 */
@SuppressWarnings("serial")
public class CalcLayoutException extends RuntimeException{

	public CalcLayoutException(String message) {
		super(message);
	}

}
