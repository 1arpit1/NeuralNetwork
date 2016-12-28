package NNMath.exception;

public class MultipleValueException extends RuntimeException {

	private static final long serialVersionUID = 3991792184964708063L;

	public MultipleValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultipleValueException(String message) {
		super(message);
	}

	public MultipleValueException(Throwable cause) {
		super(cause);
	}

}
