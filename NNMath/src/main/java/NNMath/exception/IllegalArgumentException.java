package NNMath.exception;

public class IllegalArgumentException extends RuntimeException {

	private static final long serialVersionUID = 8857417705877482553L;

	public IllegalArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalArgumentException(String message) {
		super(message);
	}

	public IllegalArgumentException(Throwable cause) {
		super(cause);
	}

}
