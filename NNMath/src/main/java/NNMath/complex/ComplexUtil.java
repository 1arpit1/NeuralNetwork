package NNMath.complex;

import java.util.ArrayList;

public  class ComplexUtil {

	public static Complex add(Complex comp1, Complex comp2){
		return new Complex(comp1.getReal()+comp2.getReal(), comp1.getImag()+comp2.getImag());
	}
	public static Complex sub(Complex comp1, Complex comp2){
		return new Complex(comp1.getReal()-comp2.getReal(), comp1.getImag()-comp2.getImag());
	}
	public static Complex mul(Complex comp1, Complex comp2){
		return new Complex(comp1.getAbs() * comp2.getAbs(), comp1.getArg() + comp2.getArg(), true);
	}
	public static Complex RandomComplex(double factor){
		return new Complex(Math.random()/factor,Math.random()/factor );
	}
	public static Complex div(Complex comp1, Complex comp2)
	{
		return new Complex(comp1.getAbs() / comp2.getAbs(), comp1.getArg() - comp2.getArg(), true);
	}
	public static double getDegree2Rad(double degree) {
		return Math.toRadians(degree);
	}
	public static double getRad2Degree(double rad){
		return Math.toDegrees(rad);
	}
	public static Complex exp(Complex comp)
	{
		return new Complex(Math.cos(comp.getImag()) * (Math.sinh(comp.getReal()) + Math.cosh(comp.getReal())),
				Math.sin(comp.getImag()) * (Math.cosh(comp.getReal()) + Math.sinh(comp.getReal())));
	}
	public static Complex conj(Complex comp)
	{
		return new Complex(comp.getReal(), -comp.getImag());
	}
	public static Complex pow(Complex comp, double power)
	{		
		power=(double)power;
		double abs0, arg0;					
		Complex swap = null;
		abs0 = Math.pow(comp.getAbs(),power);	
		arg0 = comp.getArg() * power;			
		swap = new Complex(abs0, arg0, true);
		return swap;
	}
	// ln(re^(iP))=ln(r)+iP
	public static Complex ln(Complex comp)
	{
		return new Complex(Math.log(comp.getAbs()), comp.getArg());
	}
	public static Complex log(Complex comp)
	{
		return new Complex(Math.log10(comp.getAbs()), Math.log10(Math.exp(comp.getArg())));
	}

	public static Complex sin(Complex comp)
	{
		return new Complex(Math.sin(comp.getReal()) * Math.cosh(comp.getImag()),
				Math.cos(comp.getReal()) * Math.sinh(comp.getImag()));
	}

	public static Complex cos(Complex comp)
	{
		return new Complex(Math.cos(comp.getReal()) * Math.cosh(comp.getImag()),
				Math.sin(comp.getReal()) * Math.sinh(comp.getImag()));
	}
	
	public static Complex tan(Complex comp)
	{
		double real, imag;
		double nn = 1d + Math.pow(Math.tan(comp.getReal()) * Math.tanh(comp.getImag()), 2);	
		real = (Math.tan(comp.getReal())*(1-Math.pow(Math.tanh(comp.getImag()), 2))) / nn;
		imag = (Math.tanh(comp.getImag())*(1+Math.pow(Math.tan(comp.getReal()), 2))) / nn;
		return new Complex(real, imag);
	}
	public static Complex cot(Complex comp)
	{
		double nn = Math.pow(Math.sin(comp.getReal())*Math.cosh(comp.getImag()), 2) + Math.pow(Math.cos(comp.getReal())*Math.sinh(comp.getImag()), 2); 
		return new Complex((Math.cos(comp.getReal()) * Math.sin(comp.getReal())) / nn, (Math.cosh(comp.getImag()) * Math.sinh(comp.getImag())) / nn);
	}
	public static Complex sinh(Complex comp)
	{
		return new Complex(Math.sinh(comp.getReal()) * Math.cos(comp.getImag()),
				Math.cosh(comp.getReal()) * Math.sin(comp.getImag()));
	}
	// cosh(a+bi)=cosh(a)cos(b)+isinh(a)sin(b)
	public static Complex cosh(Complex comp)
	{
		return new Complex(Math.cosh(comp.getReal()) * Math.cos(comp.getImag()),
				Math.sinh(comp.getReal()) * Math.sin(comp.getImag()));
	}
	public static Complex tanh(Complex comp)
	{
		return div(comp.sinh(), comp.cosh());
	}
	public static Complex coth(Complex comp)
	{
		return div(comp.cosh(), comp.sinh());
	}

	@SuppressWarnings("unused")
	public static Complex cosinvx(Complex comp){
	
		Complex b=mul(Complex.I,pow(sub(new Complex(1,0),mul(comp, comp)),1/2d));
		Complex c=mul(Complex.I,ln(add(comp,b)));
		Complex d=mul(Complex.I,ln(sub(comp,b)));
		return new Complex(-1*c.getReal(),-1*c.getImag());
	}
	
	@SuppressWarnings("unused")
	public static Complex sininvx(Complex comp){
		
		Complex b=pow(sub(new Complex(1,0),mul(comp, comp)),1/2d);
		Complex c=mul(Complex.I,ln(add(mul(comp,Complex.I),b)));
		Complex d=mul(Complex.I,ln(sub(mul(comp,Complex.I),b)));
		return new Complex(-1*c.getReal(),-1*c.getImag());
	}
	
	public static Complex taninvx(Complex comp){
		Complex a=ln(div(add(Complex.I,comp),sub(Complex.I,comp)));
		return mul(new Complex(0,1/2d),a);
	}
	
	public static ArrayList<Complex> root(Complex comp, int number)
	{
		ArrayList<Complex> c=new ArrayList<Complex>(number);
		double []sqrtargarray = new double[number];
		double abs0=Math.pow(comp.getAbs(), -1*number);
		double arg0=getRad2Degree(comp.getArg())/number;
		for(int i=0;i<number;i++)
		{
			sqrtargarray[i]=getDegree2Rad(arg0+(2*i*180)/number);
			c.add(i, new Complex(abs0, sqrtargarray[i], true)); 
		}
		return c;
	}
	public static boolean compare(Complex comp1,Complex comp2){
		return compareWithPrecision(comp1, comp2, 0.0001, 0.0085);
	}
	public static boolean compareWithPrecision(Complex comp1,Complex comp2,double elip){
		return compareWithPrecision(comp1, comp2, elip,0.0085); 
	}
	public static boolean compareWithPrecision(Complex comp1,Complex comp2,Complex elip){
		return compareWithPrecision(comp1, comp2, elip.getAbs(),elip.getArg()); 
	}
	public static boolean compareWithPrecision(Complex comp1,Complex comp2,double elip,double theta){
		double a=Math.sqrt(Math.pow(comp1.getReal()-comp2.getReal(), 2)+Math.pow(comp1.getImag()-comp2.getImag(), 2));
		double b=Math.abs((comp1.getArg()-comp2.getArg()));
	
		return (a<=elip && b<=theta);
	}

}

