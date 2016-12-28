package NNMath.applyFunction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import NNMath.complex.Complex;
import NNMath.complex.ComplexUtil;
import NNMath.util.Constants;

public abstract class OneVariableIterativeProcess<T> {

	protected int noOfIterations;
	protected int maximumIterations = 1000;
	protected static final Complex complexDesiredPrecision = new Complex(0.000001, 0.001, true);
	protected static final double doubleDesiredPrecision = 1.0536712127723509E-8;
	protected T desiredPrecision;
	protected T precision;
	protected T result;
	protected OneVariableFunction<T> function;

	public int getNoOfIterations() {
		return noOfIterations;
	}

	public void setNoOfIterations(int noOfIterations) {
		this.noOfIterations = noOfIterations;
	}

	public int getMaximumIterations() {
		return maximumIterations;
	}

	public void setMaximumIterations(int maximumIterations) {
		this.maximumIterations = maximumIterations;
	}

	public T getPrecision() {
		return precision;
	}

	public void setPrecision(T precision) {
		this.precision = precision;
	}

	public void setDesiredPrecision(T desiredPrecision) {
		this.desiredPrecision = desiredPrecision;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setInitialValue() {
		Type _super = getClass().getGenericSuperclass();
		Type t = ((ParameterizedType) _super).getActualTypeArguments()[0];
		try {
			Class c = Class.forName(t.toString());
			if (c.equals(Double.class) || (c.isPrimitive() && c.equals(double.class))) {
				result = (T) new Double(0);
			} else if (c.equals(Complex.class)) {
				result = (T) Complex.ZERO;
			} else {
				result = null;
			}
		} catch (ClassNotFoundException e) {
			// setting result as null
			result = null;
		}
	}

	public void setInitialValue(T initialResult) {
		result = initialResult;
	}

	public T getResult() {
		return result;
	}

	public OneVariableFunction<T> getFunction() {
		return function;
	}

	public T evaluateOneMoreIteration() {
		return evaluateIteration();
	}

	public void evaluate() {
		noOfIterations = 0;
		initializeIterations();
		while (noOfIterations++ < maximumIterations) {
			precision = evaluateIteration();
			if (hasConverged()) {
				break;
			}
		}
		finalizeIterations();
	}
	public double relativePrecision(double epsilon) {
		return relativePrecision(epsilon, Math.abs((Double) this.result));
	}
	public double relativePrecision(double epsilon, double x) {
		return (x > Constants.defaultNumericalPrecision ? epsilon / x : epsilon);
	}
	public Complex relativePrecision(Complex epsilon) {
		return relativePrecision(epsilon, ((Complex) this.result).getAbs());
	}
	public Complex relativePrecision(Complex epsilon, double x) {
		return (x > Constants.defaultNumericalPrecision ? ComplexUtil.div(epsilon, new Complex(x)) : epsilon);
	}

	public abstract boolean hasConverged();

	protected abstract void initializeIterations();

	protected abstract void finalizeIterations();

	protected abstract T evaluateIteration();

	public abstract void setFunction(OneVariableFunction<T> _function);

}
