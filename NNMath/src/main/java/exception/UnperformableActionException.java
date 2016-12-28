package exception;

public class UnperformableActionException extends RuntimeException {

	private static final long serialVersionUID = 8133960051083604256L;

	public UnperformableActionException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnperformableActionException(String message) {
		super(message);
	}

	public UnperformableActionException(Throwable cause) {
		super(cause);
	}
}
