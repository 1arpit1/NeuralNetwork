package NNMath.polynomial;

import NNMath.appliables.NewtonZeroFinderComplex;
import NNMath.apply.OneVariableFunction;
import NNMath.complex.Complex;
import NNMath.complex.ComplexUtil;
import NNMath.exception.UnperformableActionException;
import NNMath.util.Constants;

public class ComplexPolynomial implements  OneVariableFunction<Complex> {
	private Complex[] coefficients;

	/**
	 * Mathematical PolynomialD: of double type coefficients
	 * 
	 * @param coefficients
	 *            = c[0] + c[1] * x + c[2] * xˆ2 + .... C IS THE COEFFICIENT
	 *            ARRAY
	 */
	public ComplexPolynomial(Complex[] coefficients) {
		this.coefficients = coefficients;
	}

	/**
	 * @param r
	 * @return {@link PolynomialD} THIS WILL RETURN A NEW PolynomialD WITH THE
	 *         INPUT ADDED TO COEFFICIENT OF CONSTANT
	 */
	public ComplexPolynomial add(Complex c) {
		int n = this.coefficients.length;
		Complex coeff[] = new Complex[n];
		coeff[0] = ComplexUtil.add(c, this.coefficients[0]);
		for (int i = 1; i < n; i++)
			coeff[i] = this.coefficients[i];
		return new ComplexPolynomial(coeff);
	}

	/**
	 * @param func
	 * @return THIS WILL RETURN A NEW PolynomialD AFTER ADDING THE INPUT
	 *         PolynomialD
	 */
	public ComplexPolynomial add(ComplexPolynomial func) {
		int n = Math.max(func.degree(), degree()) + 1;
		Complex[] coef = new Complex[n];
		for (int i = 0; i < n; i++)
			coef[i] = ComplexUtil.add(coefficient(i), func.coefficient(i));
		return new ComplexPolynomial(coef);
	}

	/**
	 * 
	 * @param r
	 * @return THIS WILL RETURN A NEW PolynomialD WITH THE INPUT SUBTRACTED TO
	 *         COEFFICIENT OF CONSTANT
	 */
	public ComplexPolynomial subtract(Complex r) {
		return add(new Complex(-1 * r.getReal(), -1 * r.getImag()));
	}

	/**
	 * @param p
	 * @return THIS WILL RETURN A NEW PolynomialD AFTER SUBTRACTING THE INPUT
	 *         PolynomialD
	 */
	public ComplexPolynomial subtract(ComplexPolynomial p) {
		int n = Math.max(p.degree(), degree()) + 1;
		Complex[] coef = new Complex[n];
		for (int i = 0; i < n; i++)
			coef[i] = ComplexUtil.sub(coefficient(i), p.coefficient(i));
		return new ComplexPolynomial(coef);
	}

	/**
	 * @return THIS WILL RETURN THE DEGREE OF POLYNOMIAL
	 */
	public int degree() {
		return this.coefficients.length - 1;
	}

	private Complex coefficient(int n) {
		return (Complex) (n < this.coefficients.length ? this.coefficients[n] : new Complex());
	}

	public ComplexPolynomial derivative() {
		int n = degree();
		if (n == 0) {
			Complex coef[] = { new Complex(0) };
			return new ComplexPolynomial(coef);
		}
		Complex[] coef = new Complex[n];
		for (int i = 1; i <= n; i++)
			coef[i - 1] = ComplexUtil.mul(this.coefficients[i], new Complex(i));
		return new ComplexPolynomial(coef);
	}

	public Complex value(Complex value) {
		int n = coefficients.length;
		Complex answer = coefficients[--n];
		while (n > 0)
			answer = ComplexUtil.add(ComplexUtil.mul(answer, value), coefficients[--n]);
		return answer;
	}

	/**
	 * @param r
	 * @return THIS WILL RETURN A POLYNOMIAL WITH ITS COEFFICIENT MULTIPLIED BY
	 *         INPUT
	 */
	public ComplexPolynomial multiply(Complex r) {
		int n = this.coefficients.length;
		Complex[] coef = new Complex[n];
		for (int i = 0; i < n; i++)
			coef[i] = ComplexUtil.mul(this.coefficients[i], r);
		return new ComplexPolynomial(coef);
	}

	/**
	 * @param r
	 * @return THIS WILL RETURN THE POLYNOMIAL AFTER DEVIDING ITS COEFFICIENT BY
	 *         INPUT
	 */
	public ComplexPolynomial divide(Complex r) {
		return this.multiply(ComplexUtil.div(new Complex(1), r));
	}

	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int n = p.degree() + this.degree();
		Complex[] coef = new Complex[n + 1];
		for (int i = 0; i <= n; i++) {
			coef[i] = new Complex();
			for (int k = 0; k <= i; k++) {
				coef[i] = ComplexUtil.add(coef[i], ComplexUtil.mul(p.coefficient(k), this.coefficient(i - k)));
			}
		}
		return new ComplexPolynomial(coef);
	}

	public ComplexPolynomial integral() {
		return integral(new Complex());
	}

	public ComplexPolynomial integral(Complex value) {
		int n = coefficients.length + 1;
		Complex[] coef = new Complex[n];
		coef[0] = value;
		for (int i = 1; i < n; i++)
			coef[i] = ComplexUtil.div(coefficients[i - 1], new Complex(i));
		return new ComplexPolynomial(coef);
	}

	/**
	 * TOSTRING METHOD
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		boolean firstNonZeroCoefficientPrinted = false;
		for (int n = coefficients.length - 1; n >= 0; n--) {
			if (coefficients[n] != new Complex(0)) {
				if (firstNonZeroCoefficientPrinted) {
					sb.append(" + ");
				} else {
					firstNonZeroCoefficientPrinted = true;
				}
				if (n != 0)
					sb.append(coefficients[n].toString() + "*");
				if (n == 0) {
					sb.append(coefficients[n].toString());
				}
				if (n > 0)
					sb.append("X^" + n);
			}
		}
		return sb.toString();
	}

	public Complex[] valueAndDerivative(Complex x) {
		Complex[] answer = new Complex[2];
		answer[0] = value(x);
		answer[1] = derivative().value(x);
		return answer;
	}

	/**
	 * @param r
	 *            double
	 * @return THIS WILL RETURN THE POLYNOMIAL AFTER DEVIDING WITH THE INPUT
	 *         POLYNOMIAL
	 */
	public ComplexPolynomial divide(ComplexPolynomial p) {
		return divideWithRemainder(p)[0];
	}

	/**
	 * @param p
	 * @return THIS WILL RETURN TWO POLYNOMIAL
	 * 
	 *         <pre>
	 * 1-->  QUOTIENT
	 * 2-->  REMAINDER
	 *         </pre>
	 */
	public ComplexPolynomial[] divideWithRemainder(ComplexPolynomial p) {
		ComplexPolynomial[] answer = new ComplexPolynomial[2];
		int m = degree();
		int n = p.degree();
		if (m < n) {
			Complex[] q = { new Complex() };
			answer[0] = new ComplexPolynomial(q);
			answer[1] = p;
			return answer;
		}
		Complex[] quotient = new Complex[m - n + 1];
		Complex[] coef = new Complex[m + 1];
		for (int k = 0; k <= m; k++) {
			coef[k] = coefficients[k];
		}
		Complex norm = ComplexUtil.div(new Complex(1), p.coefficient(n));
		for (int k = m - n; k >= 0; k--) {
			quotient[k] = ComplexUtil.mul(coef[n + k], norm);
			coef[n + k] = new Complex();
			for (int j = n + k - 1; j >= k; j--)
				coef[j] = ComplexUtil.sub(coef[j], ComplexUtil.mul(quotient[k], p.coefficient(j - k)));
		}
		Complex[] remainder = new Complex[n];
		for (int k = 0; k < n; k++)
			remainder[k] = coef[k];
		answer[0] = new ComplexPolynomial(quotient);
		answer[1] = new ComplexPolynomial(remainder);
		return answer;
	}

	/**
	 * @param p
	 * @return RETURN THE REMAINDER POLYNOMIAL AFTER DEVIDING THE RECIEVER WITH
	 *         INPUT
	 */
	public ComplexPolynomial remainder(ComplexPolynomial p) {
		return divideWithRemainder(p)[1];
	}

	public ComplexPolynomial deflate(Complex r) {
		int n = degree();
		Complex remainder = coefficients[n];
		Complex[] coef = new Complex[n];
		for (int k = n - 1; k >= 0; k--) {
			coef[k] = remainder;
			remainder = ComplexUtil.add(ComplexUtil.mul(remainder, r), coefficients[k]);
		}
		return new ComplexPolynomial(coef);
	}

	public Complex[] roots() {
		return roots(Constants.defaultComplexPrecision);
	}

	public Complex[] roots(Complex desiredPrecision) {
		// DERIVATIVE OF THE FUNCTION
		ComplexPolynomial dp = derivative();
		// STARTING WITH ZERO
		Complex start = new Complex();
		int n = 0;

		// INITIALIZES SO THAT DERIVATIVE IS NOT ZERO AT START
		while (ComplexUtil.compare(dp.value(start), desiredPrecision)) {
			start = ComplexUtil.RandomComplex(100);
			if (++n > 100) {
				throw new UnperformableActionException("unable to initialize the derivative function!!!");
			}
		}
		ComplexPolynomial p = this;
		NewtonZeroFinderComplex rootFinder = null;
		rootFinder = new NewtonZeroFinderComplex(this, dp, start);

		rootFinder.setDesiredPrecision(desiredPrecision);

		Complex[] roots = new Complex[this.degree()];
		// INITIALIZING ROOTS TO NAN i.e. WILL BE NAN
		// IF COMPLEX ROOTS ARE FOUND
		for (int i = 0; i < this.degree(); i++) {
			roots[i] = new Complex();
		}
		int i = 0;
		while (true) { // EVALUATE ROOT USING METHOD OF ITERATIVE PROCESS CLASS
			rootFinder.evaluate();
			// IF NOT FOUND THEN BREAK..TRUE FOR COMPLEX ROOTS
			// GET ROOT
			Complex r = rootFinder.getResult();
			roots[i++] = r;
			System.out.println(r);
			// POLYNOMIAL DEGREE IS REDUCED TO FIND REMAINING ROOTS
			p = p.deflate(r);
			// IF ALL ROOTS ARE FOUND
			if (p.degree() == 0) {
				break;
			}
			// FIND ANOTHER ROOTS
			rootFinder.setFunction(p);
			rootFinder.setDerivative(p.derivative());

		}
		return roots;
	}

}
