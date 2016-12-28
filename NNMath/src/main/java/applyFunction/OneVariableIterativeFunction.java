package applyFunction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import complex.Complex;

public abstract class OneVariableIterativeFunction<T> implements OneVariableFunction<T> {

	protected int noOfIterations;
	protected int maximumIterations = 1000;
	protected final Complex complexDesiredPrecision = new Complex(0.000001, 0.001, true);
	protected final double doubleDesiredPrecision = 1.0536712127723509E-8;
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
	public void setFunction(OneVariableFunction<T> _function) {
		this.function = _function;
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

	public abstract boolean hasConverged();
	protected abstract void initializeIterations();
	protected abstract void finalizeIterations();
	protected abstract T evaluateIteration();

}