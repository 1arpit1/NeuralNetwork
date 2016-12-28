package exception;

public class NegativeNumberException extends RuntimeException {

	private static final long serialVersionUID = -856474362540063797L;

	public NegativeNumberException()
	{
		super("negative number not allowed!!!");
	}

	public NegativeNumberException(String s)
	{
		super(s);
	}
}
