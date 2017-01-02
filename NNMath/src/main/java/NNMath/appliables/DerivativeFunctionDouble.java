package NNMath.appliables;

import NNMath.apply.OneVariableFunction;

public class DerivativeFunctionDouble implements OneVariableFunction<Double> {

	private double relativePrecision = 0.000001;
	private OneVariableFunction<Double> function;

	public OneVariableFunction<Double> getFunction() {
		return function;
	}
	
	public DerivativeFunctionDouble(OneVariableFunction<Double> function, double relativePrecision) {
		this.function = function;
		if(Math.abs(relativePrecision)>this.relativePrecision)
		{
			this.relativePrecision = relativePrecision;	
		}
	}

	public DerivativeFunctionDouble(OneVariableFunction<Double> function) {
		this.function = function;
	}

	/**
	 * THIS RETURN THE DERIVATIVE VALUE OF THE FUNCTION <br>
	 * USING FORMULA
	 * 
	 * <pre>
	 * dF(x)       lim    f(x1)  -  f(x2)    
	 * ----     =  e->0   -----------------
	 *  dx                     x1-x2
	 *  
	 *  where 
	 *  
	 *  x1    =  x * (1+e)
	 *  x2    =  x * (1-e)
	 *  x1-x2 =  2 * x * e
	 * </pre>
	 * 
	 * @param x=derivative
	 *            at value
	 * @return
	 */
	public Double value(Double input) {
		double x1 = input == 0 ? relativePrecision : input * (1 + relativePrecision);
		double x2 = 2 * input - x1;
		return ((this.function.value(x1) - this.function.value(x2)) / (x1 - x2));
}

}
