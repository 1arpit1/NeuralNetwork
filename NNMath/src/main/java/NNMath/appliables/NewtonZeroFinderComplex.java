package NNMath.appliables;

import NNMath.apply.OneVariableFunction;
import NNMath.apply.OneVariableIterativeProcess;
import NNMath.complex.Complex;
import NNMath.complex.ComplexUtil;
import NNMath.exception.UnperformableActionException;
import NNMath.exception.IllegalArgumentException;

public class NewtonZeroFinderComplex extends OneVariableIterativeProcess<Complex> {

	private OneVariableFunction<Complex> df;

	/**
	 * Defines the initial value for the search.
	 */
	public NewtonZeroFinderComplex(OneVariableFunction<Complex> func, Complex start) {
		this.setFunction(func);
		setInitialValue(start);
	}

	public NewtonZeroFinderComplex(OneVariableFunction<Complex> func, OneVariableFunction<Complex> dFunc,
			Complex start) {
		this(func, start);
		setDerivative(dFunc);
	}

	public void setDerivative(OneVariableFunction<Complex> dFunc) {
		df = new DerivativeFunctionComplex(this.getFunction());
		if (!ComplexUtil.compare(df.value(result), dFunc.value(result))) {
			throw new IllegalArgumentException("Supplied derivative function is inaccurate");
		}
		df = dFunc;
	}

	public void setFunction(OneVariableFunction<Complex> func) {
		this.function = func;
		df = null;
	}

	protected void initializeIterations() {
		// IF DERIVATIVE IS NULL,IT WILL ASSIGN A NEW DERIVATIVE FUNCTION
		if (df == null) {
			df = new DerivativeFunctionComplex(this.getFunction());
		}
		int n = 0;
		// WHILE DERIVATIVE VALUE IS ZERO AT THE STATING POINT IT WILL SET
		// DERIVATIVE
		// VALUE TO RANDOM SO THAT IT IS NOT ZERO AT STARTING POINT
		while (ComplexUtil.compare(df.value(result), new Complex())) {
			if (++n > getMaximumIterations()) {
				throw new UnperformableActionException("unable to initialize the Iterations");
			}
			result = ComplexUtil.add(result, ComplexUtil.RandomComplex(1000));
		}
	}

	protected Complex evaluateIteration() {
		Complex tempResult = result;
		int n = 0;
		while (ComplexUtil.compare(df.value(tempResult), new Complex())) {
			tempResult = ComplexUtil.add(tempResult, ComplexUtil.RandomComplex(100));
			if (++n > getMaximumIterations()) {
				throw new UnperformableActionException("unable to perform the Iteration,derivative is zero");
			}
		}
		Complex delta = ComplexUtil.div(this.getFunction().value(tempResult), df.value(tempResult));

		result = ComplexUtil.sub(result, delta);
		this.precision = relativePrecision(new Complex(delta.getAbs(), Math.abs(delta.getArg()), true));
		return precision;
	}

	public boolean hasConverged() {
		return ComplexUtil.compare(precision, desiredPrecision);
	}

	@Override
	protected void finalizeIterations() {
		return;
	}
}
