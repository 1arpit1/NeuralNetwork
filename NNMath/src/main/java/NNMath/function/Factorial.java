package NNMath.function;

import NNMath.exception.NegativeNumberException;
import NNMath.exception.OverflowException;

public class Factorial {

	private static Long[] First21WholeNumberFactorials = { 1l, 1l, 2l, 6l, 24l,
			120l, 720l, 5040l, 40320l, 362880l, 3628800l, 39916800l,
			479001600l, 6227020800l, 87178291200l, 1307674368000l,
			20922789888000l, 355687428096000l, 6402373705728000l,
			121645100408832000l, 2432902008176640000l };

	public static Long evalFactorial(int n) {
		if (n <= -1) {
			throw new NegativeNumberException();
		}
		if (n < 21) {
			return First21WholeNumberFactorials[n];
		} else {
			Long r = evalFactorial(n - 1);
			if (Long.MAX_VALUE / r <= n) {
				throw new OverflowException(Long.class,
						"overflow occured for n = " + n);
			}
			return n * r;
		}
	}

}
