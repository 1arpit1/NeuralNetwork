package NNMath.exception;

public class SizeMisMatchException extends RuntimeException {

	private static final long serialVersionUID = 2989090118692006440L;

	public SizeMisMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public SizeMisMatchException(String message) {
		super(message);
	}

	public SizeMisMatchException(Throwable cause) {
		super(cause);
	}

}
