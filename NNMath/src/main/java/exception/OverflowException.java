package exception;

public class OverflowException extends RuntimeException {

	private static final long serialVersionUID = -4840986342579314642L;

	public OverflowException(){
		super("Overflow Occured!!");
	}
	
	public OverflowException(Class<?> klass, String message)
	{
		super(klass.getName() + " :: " + message);
	}
	
}
