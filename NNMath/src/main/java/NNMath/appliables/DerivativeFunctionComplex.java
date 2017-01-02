package NNMath.appliables;

import NNMath.apply.OneVariableFunction;
import NNMath.complex.Complex;
import NNMath.complex.ComplexUtil;

public class DerivativeFunctionComplex implements OneVariableFunction<Complex>{

	private OneVariableFunction<Complex> function;
	private double relativePrecisionMAG =0.0001;
	private double relativePrecisionARG =0.009;
	private Complex relativePrecision=null; 
	
	public DerivativeFunctionComplex(OneVariableFunction<Complex> function, double relativePrecisionMAG, double relativePrecisionARG) {
		this.function = function;
		this.relativePrecisionMAG = relativePrecisionMAG;
		this.relativePrecisionARG = relativePrecisionARG;
		relativePrecision=new Complex(relativePrecisionMAG, relativePrecisionARG, true); 
	}
	public DerivativeFunctionComplex(OneVariableFunction<Complex> function, double relativePrecisionMAG) {
		this.function = function;
		this.relativePrecisionMAG = relativePrecisionMAG;
		relativePrecision=new Complex(relativePrecisionMAG, relativePrecisionARG, true);
	}
	public DerivativeFunctionComplex(OneVariableFunction<Complex> function, Complex relativePrecision) {
		this.function = function;
		this.relativePrecisionMAG = relativePrecision.getAbs();
		this.relativePrecisionARG=relativePrecision.getArg();
		this.relativePrecision=relativePrecision;
	}
	public DerivativeFunctionComplex(OneVariableFunction<Complex> function)
	{
		this.function=function;
		relativePrecision=new Complex(relativePrecisionMAG, relativePrecisionARG, true);
	}
	public OneVariableFunction<Complex> getFunction() {
		return function;
	}
	/**
	 * THIS RETURN THE DERIVATIVE VALUE OF THE FUNCTION 
	 * <br>
	 * USING FORMULA
	 * <pre>
	 * dF(Z)       lim    f(z1)  -  f(z2)    
	 * ----     =  e->0   -----------------
	 *  dZ                     z1-z2
	 *  
	 *  where 
	 *  
	 *  Z1    =  Z * (1+e)
	 *  Z2    =  Z * (1-e)
	 *  Z1-Z2 =  2 * Z * e   
	 *  </pre>
	 * @param x=derivative at value
	 * @return 
	 */
	public Complex value(Complex x) {
		Complex x1=x.equals(new Complex(0,0))?relativePrecision:ComplexUtil.mul(x, ComplexUtil.add(new Complex(1), relativePrecision));
		Complex x2=ComplexUtil.sub(x.multiply(new Complex(2)), x1);
		Complex a=ComplexUtil.sub(this.function.value(x1),this.function.value(x2));
		Complex b=ComplexUtil.sub(x1, x2);
		return ComplexUtil.div(a, b);
	}

}
