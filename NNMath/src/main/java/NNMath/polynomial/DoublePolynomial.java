package NNMath.polynomial;

import NNMath.appliables.NewtonZeroFinderDouble;
import NNMath.applyFunction.OneVariableFunction;
import NNMath.exception.UnperformableActionException;
import NNMath.util.Constants;

public class DoublePolynomial implements OneVariableFunction<Double>{
	private double[] coefficients;
	/**
	 * Mathematical DoublePolynomial: of double type coefficients
	 * @param coefficients = c[0] + c[1] * x + c[2] * xˆ2 + ....
	 * C IS THE COEFFICIENT ARRAY
	 */
	public DoublePolynomial(double[] coefficients) {
		this.coefficients = coefficients;
	}
	/**
	 * @param r
	 * @return {@link DoublePolynomial}
	 * THIS WILL RETURN A NEW DoublePolynomial WITH THE 
	 * INPUT ADDED TO COEFFICIENT OF CONSTANT
	 */
	public DoublePolynomial add( double r)
	{
		int n = this.coefficients.length;
		double coef[] = new double[n];
		coef[0] = this.coefficients[0] + r;
		for ( int i = 1; i < n; i++)
			coef[i] = this.coefficients[i];
		return new DoublePolynomial(coef);
	}
	/** 
	 * @param func
	 * @return
	 * THIS WILL RETURN A NEW DoublePolynomial AFTER ADDING THE INPUT 
	 * DoublePolynomial 
	 */
	public DoublePolynomial add(DoublePolynomial func)
	{
		int n = Math.max( func.degree(), degree()) + 1;
		double[] coef = new double[n];
		for ( int i = 0; i < n; i++ )
			coef[i] = coefficient(i) + func.coefficient(i);
		return new DoublePolynomial(coef);
	}
	/**
	 * 
	 * @param r
	 * @return
	 * THIS WILL RETURN A NEW DoublePolynomial WITH THE 
	 * INPUT SUBTRACTED TO COEFFICIENT OF CONSTANT
	 */
	public DoublePolynomial subtract( double r)
	{
		return add( -r);
	}
	/**
	 * @param p
	 * @return
	 * THIS WILL RETURN A NEW DoublePolynomial AFTER SUBTRACTING
	 * THE INPUT DoublePolynomial 
	 */
	public DoublePolynomial subtract( DoublePolynomial p)
	{
		int n = Math.max( p.degree(), degree()) + 1;
		double[] coef = new double[n];
		for ( int i = 0; i < n; i++ )
			coef[i] = coefficient(i) - p.coefficient(i);
		return new DoublePolynomial( coef);
	}
	/**
	 * @return
	 * THIS WILL RETURN THE DEGREE OF POLYNOMIAL
	 */
	public int degree() {
		return this.coefficients.length - 1;
	}
	private double coefficient(int n) {
		return n < this.coefficients.length ? this.coefficients[n] : 0;	
	}
	/**
	 * @return
	 * THIS WILL RETURN THE DERIVATIVE OF POLYNOMIAL
	 */
	public DoublePolynomial derivative()
	{
		int n = degree();
		if ( n == 0 )
		{
			double coef[] = {0};
			return new DoublePolynomial(coef);
		}
		double coef[] = new double[n];
		for ( int i = 1; i <= n; i++)
			coef[i-1] = this.coefficients[i]*i;
		return new DoublePolynomial( coef);
	}
	/**
	 * @param 
	 * @return 
	 * IT RETURNS THE VALUE OF POLYNOMIAL
	 * <br>
	 * USING THIS EXPRESSION
	 * <pre>
	 * (value) = a0 + x *(a1 + x *(a2 + x *(a3 + ...)))
	 * </pre>
	 * 
	 */
	public Double value(Double value) {
		int n = coefficients.length;
		double answer = coefficients[--n];
		while ( n > 0 )
			answer = answer * value + coefficients[--n];
		return answer;
	}
	/**
	 * @param r
	 * @return
	 * THIS WILL RETURN A POLYNOMIAL WITH ITS COEFFICIENT 
	 * MULTIPLIED BY INPUT
	 */
	public DoublePolynomial multiply( double r)
	{
		int n = this.coefficients.length;
		double coef[] = new double[n];
		for ( int i = 0; i < n; i++)
			coef[i] = this.coefficients[i] * r;
		return new DoublePolynomial( coef);
	}
	/**
	 * @param r
	 * @return
	 * THIS WILL RETURN THE POLYNOMIAL AFTER DEVIDING
	 * ITS COEFFICIENT BY INPUT
	 */
	public DoublePolynomial divide( double r)
	{
		return this.multiply( 1 / r);
	}
	/**
	 * @param p
	 * @return
	 * THIS WILL RETURN THE POLYNOMIAL AFTER MULTIPLICATION WITH INPUT POLYNOMIAL
	 */
	public DoublePolynomial multiply( DoublePolynomial p)
	{
		int n = p.degree() + this.degree();
		double[] coef = new double[n + 1];
		for ( int i = 0; i <= n; i++)
		{
			coef[i] = 0;
			for ( int k = 0; k <= i; k++)
			{
				coef[i] =coef[i]+ p.coefficient(k) * this.coefficient(i-k);
			}
		}
		return new DoublePolynomial( coef);
	}
	/**
	 * Returns the integral of the receiver having the value 0 for X = 0.
	 * @return PolynomialFunction integral of the receiver.
	 */
	public DoublePolynomial integral( )
	{
		return integral(0);
	}
	/**
	 * Returns the integral of the receiver having the specified value for X = 0.
	 * @param value double    value of the integral at x=0
	 * @return Polynomial integral of the receiver.
	 */
	public DoublePolynomial integral( double value)
	{
		int n = coefficients.length + 1;
		double coef[] = new double[n];
		coef[0] = value;
		for ( int i = 1; i < n; i++)
			coef[i] = coefficients[i-1]/i;
		return new DoublePolynomial( coef);
	}
	/**
	 * TOSTRING METHOD
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		boolean firstNonZeroCoefficientPrinted = false;
		for ( int n = coefficients.length-1; n >=0; n--)
		{
			if ( coefficients[n] != 0 )
			{
				if ( firstNonZeroCoefficientPrinted)
				{sb.append( coefficients[n] > 0 ? " + " : " ");}
				else
				{firstNonZeroCoefficientPrinted = true;}
				if (  coefficients[n] != 1 && n!=0)
					sb.append( Double.toString( coefficients[n])+"*");
				if (n==0 )
				{
					sb.append( Double.toString( coefficients[n]));
				}
				if ( n > 0 )
					sb.append( "X^"+n);
			}
		}
		return sb.toString();
	}
	/**
	 * @param x
	 * @return
	 * THIS WILL RETURN TWO VALUE
	 * 1-->  VALUE AT INPUT
	 * 2-->  VALUE OF DERIVATIVE AT INPUT
	 */
	public double[] valueAndDerivative( double x)
	{
		double[] answer = new double[2];
		answer[0] = value(x);
		answer[1] = derivative().value(x);
		return answer;
	}
	/**
	 * @param r double
	 * @return 
	 * THIS WILL RETURN THE POLYNOMIAL AFTER DEVIDING WITH THE INPUT
	 * POLYNOMIAL
	 */
	public DoublePolynomial divide( DoublePolynomial p)
	{
		return divideWithRemainder(p)[0];
	}
	/**
	 * @param p
	 * @return
	 * THIS WILL RETURN TWO POLYNOMIAL 
	 * <pre>
	 * 1-->  QUOTIENT
	 * 2-->  REMAINDER
	 * </pre>
	 */
	public DoublePolynomial[] divideWithRemainder( DoublePolynomial p)
	{
		DoublePolynomial[] answer = new DoublePolynomial[2];
		int m = degree();
		int n = p.degree();
		if ( m < n )
		{
			double[] q = {0};
			answer[0] = new DoublePolynomial( q);
			answer[1] = p;
			return answer;
		}
		double[] quotient = new double[ m - n + 1];
		double[] coef = new double[ m + 1];
		for ( int k = 0; k <= m; k++ )
		{  coef[k] = coefficients[k];}
		double norm = 1 / p.coefficient(n);
		for ( int k = m - n; k >= 0; k--)
		{
			quotient[k] = coef[ n + k] * norm;
			coef[n+k]=0;
			for ( int j = n + k - 1; j >= k; j--)
				coef[j] -= quotient[k] * p.coefficient(j-k);
		}
		double[] remainder = new double[n];
		for ( int k = 0; k < n; k++)
			remainder[k] = coef[k];
		answer[0] = new DoublePolynomial( quotient);
		answer[1] = new DoublePolynomial( remainder);
		return answer;
	}
	/**
	 * @param p
	 * @return
	 * RETURN THE REMAINDER POLYNOMIAL AFTER DEVIDING
	 * THE RECIEVER WITH INPUT
	 */
	public DoublePolynomial remainder( DoublePolynomial p)
	{
		return divideWithRemainder(p)[1];
	}
	/**
	 * @param r double   
	 * @return 
	 * THE QUOTIENT POLYNOMIAL AFTER DEVIDING WITH X-R  
	 */
	public DoublePolynomial deflate( double r)
	{
		int n = degree();
		double remainder = coefficients[n];
		double[] coef = new double[n];
		for ( int k = n - 1; k >= 0; k--)
		{
			coef[k] = remainder;
			remainder = remainder * r + coefficients[k];
		}
		return new DoublePolynomial( coef);
	}
	//THIS WILL FIND THE ROOTS WITH DEFAULT PRECISION
	public double[] roots() 
	{
		return roots( Constants.defaultNumericalPrecision);
	}
	/**
	 * @param desiredPrecision double
	 * @return
	 * THIS WILL RETURN THE ROOTS CALCULATED USING NEWTON'S METHOD
	 * @throws UnperformableAction 
	 */
	public double[] roots( double desiredPrecision) 
	{
		//DERIVATIVE OF THE FUNCTION
		DoublePolynomial dp = derivative();
		//STARTING WITH ZERO
		double start = 0;
		int n=0;
		//INITIALIZES SO THAT DERIVATIVE IS NOT ZERO AT START
		while ( Math.abs(dp.value( start))<desiredPrecision)
		{start = Math.random();
		if(++n>100)
		{
			throw new UnperformableActionException("unable to initialize the derivative function!!!");
		}
		}
		DoublePolynomial p = this;
		NewtonZeroFinderDouble rootFinder =null;
			rootFinder= new NewtonZeroFinderDouble( this, dp, start);
			rootFinder.setDesiredPrecision( desiredPrecision);
		
		double[] roots=new double[this.degree()];
		//INITIALIZING ROOTS TO NAN i.e. WILL BE NAN
		//IF  COMPLEX ROOTS ARE FOUND
		for(int i=0;i<this.degree();i++)
		{
			roots[i]=Double.NaN;
		}
		int i=0;
        while ( true)
		{   //EVALUATE ROOT USING METHOD OF ITERATIVE PROCESS CLASS
			rootFinder.evaluate();
			//IF NOT FOUND THEN BREAK..TRUE FOR COMPLEX ROOTS
			if (!rootFinder.hasConverged())
			{break;}
			//GET ROOT
			double r = rootFinder.getResult();
			roots[i++]=r;
			//POLYNOMIAL DEGREE IS REDUCED TO FIND REMAINING ROOTS
			p = p.deflate(r);
			//IF ALL ROOTS ARE FOUND
			if (p.degree()== 0)
			{break;}
			//FIND ANOTHER ROOTS
			rootFinder.setFunction(p);
			 rootFinder.setDerivative( p.derivative());
			
		}
		return roots;
	}


}
