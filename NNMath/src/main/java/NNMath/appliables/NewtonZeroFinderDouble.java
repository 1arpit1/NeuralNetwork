package NNMath.appliables;

import NNMath.applyFunction.OneVariableFunction;
import NNMath.applyFunction.OneVariableIterativeProcess;
import NNMath.exception.UnperformableActionException;
import NNMath.util.Constants;

public class NewtonZeroFinderDouble extends OneVariableIterativeProcess<Double> {

	/**
	 * Derivative of the function for which the zero will be found.
	 */
	private OneVariableFunction<Double> df;

	/**
	 * Defines the initial value for the search.
	 */
	public NewtonZeroFinderDouble(OneVariableFunction<Double> func, double start) {
		this.setFunction(func);
		setInitialValue(start);
	}

	/**
	 * Constructor method.
	 * 
	 * @param func
	 *            the function for which the zero will be found.
	 * @param dFunc
	 *            derivative of func.
	 * @param start
	 *            the initial value for the search.
	 */
	public NewtonZeroFinderDouble(OneVariableFunction<Double> func, OneVariableFunction<Double> dFunc, double start) {
		this(func, start);
		setDerivative(dFunc);
	}

	/**
	 * THIS WILL SET THE DERIVATIVE FUNCTION ,IF THE DERIVATIVE IS NOT CORRECT
	 * IT WILL THROW AN EXCEPTION
	 */
	public void setDerivative(OneVariableFunction<Double> dFunc) {
		df = new DerivativeFunctionDouble(this.getFunction());
		if (!Constants.equal(df.value(result), dFunc.value(result), 0.01)) {
			throw new IllegalArgumentException("Supplied derivative function is inaccurate");
		}
		df = dFunc;
	}

	/**
	 * SET FUNCTION TO INPUT, DERIVATIVE TO NULL
	 */
	public void setFunction(OneVariableFunction<Double> func) {
		this.function = func;
		df = null;
	}

	/**
	 * Initializes internal parameters to start the iterative process. Assigns
	 * default derivative if necessary.
	 * 
	 * @throws UnperformableAction
	 */
	protected void initializeIterations() {
		// IF DERIVATIVE IS NULL,IT WILL ASSIGN A NEW DERIVATIVE FUNCTION
		if (df == null) {
			df = new DerivativeFunctionDouble(this.getFunction());
		}
		if (Double.isNaN(result)) {
			result = 0d;
		}
		int n = 0;
		// WHILE DERIVATIVE VALUE IS ZERO AT THE STATING POINT IT WILL SET
		// DERIVATIVE
		// VALUE TO RANDOM SO THAT IT IS NOT ZERO AT STARTING POINT
		while (Constants.equal(df.value(result), 0)) {
			if (++n > getMaximumIterations()) {
				throw new UnperformableActionException("unable to initialize the Iterations");
			}
			result += Math.random();
		}
	}

	/**
	 * Evaluate the result of the current interation.
	 * 
	 * @return the estimated precision of the result.
	 * @throws UnperformableAction
	 */
	protected Double evaluateIteration() throws UnperformableActionException {
		double tempResult = result;
		int n = 0;
		while ((df.value(tempResult)) == 0) {
			tempResult = result + (Math.random() / 100);
			if (++n > getMaximumIterations()) {
				throw new UnperformableActionException("unable to perform the Iteration,derivative is zero");
			}
		}
		double delta = this.getFunction().value(tempResult) / df.value(tempResult);
		result -= delta;
		this.precision = relativePrecision(Math.abs(delta));
		return this.precision;
	}

	public boolean hasConverged()
	{return precision < desiredPrecision;}
	
	@Override
	protected void finalizeIterations() {
		return;
	}

}
