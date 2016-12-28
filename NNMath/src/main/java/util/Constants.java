package util;

public class Constants {
	public static final double PI=3.141592653589793;
	public static final double E=2.718281828459045;	
	public static final double RADIAN=57.295779513082323;
	public static final double GOLDENRATIO= 1.618033988749895;
	public static final double ROOT2=1.414213562373095;
	public static final double ROOT3=1.7320508075688773;
	public static final double ROOT5=2.236067977499789;
	public static final double logE2=0.6931471805599453;
	public static final double logE3=1.0986122886681098;
	public static final double logE5=1.6094379124341003;
	public static final double logE7=1.9459101490553132;
	public static final double logE10=2.302585092994046;
	public static final double EPSILON=1e-12;
	static public final double smallNumber = 2.2227587494850775E-162;
	static public final double largestNumber = 1.7976931348623157E308;
	static public final double defaultNumericalPrecision = 1.0536712127723509E-8;
//	static public final Complex defaultComplexPrecision=new Complex(0.00001,0.001,true);
	static public final double machinePrecision = 1.1102230246251565E-16;
	static public final double negativeMachinePrecision = 5.551115123125783E-17;
	static public final double smallestNumber = 4.9E-324;
	static public final double largestExponentialArgument = 709.782712893384;
	
	
	public static boolean equal( double a, double b)
	{
		return equal( a, b, defaultNumericalPrecision);
	}
	public static boolean equal( double a, double b, double precision)
	{
		double norm = Math.max( Math.abs(a),Math.abs(b));
		return (norm < precision) || (Math.abs( a - b) < precision * norm);
	}
	
	/**
	 * This method returns the specified value rounded to
	 * the nearest integer multiple of the specified scale.
	 *
	 * @param value number to be rounded
	 * @param scale defining the rounding scale
	 * @return rounded value
	 */
	public static double roundTo(double value, double scale)
	{
		return Math.round( value / scale) * scale;
	}

}