package complex;

import java.util.ArrayList;

import exception.UnperformableActionException;

public class Complex implements Cloneable {
	
	public static final Complex ZERO = new Complex();
	public static final Complex ONE = new Complex(1.0d, 0.0d);
	public static final Complex I = new Complex(0.0d, 1.0d);
	private double real = 0, imag = 0, abs = 0, arg = 0;

	public Complex() {
	}

	public Complex(double real) {
		this.real = real;
		solveAbsAndArg();
	}

	public Complex(double real, double imag) {

		this.real = real;
		this.imag = imag;
		solveAbsAndArg();

	}

	public Complex(double abs, double arg, boolean isRadian) {
		this.abs = abs;
		if (isRadian)
			this.arg = (arg);
		else
			this.arg = ComplexUtil.getDegree2Rad(arg);
		solveRealAndImag();
	}

	private void solveAbsAndArg() {
		abs = Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));
		arg = Math.atan2(imag, real);
	}

	private void solveRealAndImag() {
		real = abs * Math.cos(arg);
		imag = abs * Math.sin(arg);
	}

	public double getAbs() {
		return abs;
	}

	public void setAbs(double abs) {
		this.abs = abs;
		solveRealAndImag();

	}

	public double getArg() {
		return Math.atan2(this.imag, this.real);
	}

	public void setArg(double arg) {
		this.arg = Math.toRadians(Math.toDegrees(arg));
		solveRealAndImag();
	}

	public double getImag() {
		return imag;
	}

	public void setImag(double imag) {
		this.imag = imag;
		solveAbsAndArg();
	}

	public double getReal() {
		return real;
	}

	public void setReal(double real) {
		this.real = real;
		solveAbsAndArg();
	}

	public Complex conj() {
		return new Complex(this.getReal(), -this.getImag());
	}

	public Complex sinh() {
		return new Complex(Math.sinh(this.getReal()) * Math.cos(this.getImag()),
				Math.cosh(this.getReal()) * Math.sin(this.getImag()));
	}

	public Complex cosh() {
		return new Complex(Math.cosh(this.getReal()) * Math.cos(this.getImag()),
				Math.sinh(this.getReal()) * Math.sin(this.getImag()));
	}

	public Complex tanh() {
		return ComplexUtil.div(this.sinh(), this.cosh());
	}

	public Complex coth() {
		return ComplexUtil.div(this.cosh(), this.sinh());
	}

	public Complex sin() {
		return new Complex(Math.sin(this.getReal()) * Math.cosh(this.getImag()),
				Math.cos(this.getReal()) * Math.sinh(this.getImag()));
	}

	public Complex cos() {
		return new Complex(Math.cos(this.getReal()) * Math.cosh(this.getImag()),
				Math.sin(this.getReal()) * Math.sinh(this.getImag()));
	}

	public Complex tan() {
		double real, imag;
		double nn = 1d + Math.pow(Math.tan(this.getReal()) * Math.tanh(this.getImag()), 2);
		real = (Math.tan(this.getReal()) * (1 - Math.pow(Math.tanh(this.getImag()), 2))) / nn;
		imag = (Math.tanh(this.getImag()) * (1 + Math.pow(Math.tan(this.getReal()), 2))) / nn;
		return new Complex(real, imag);
	}

	public Complex cot() {
		double nn = Math.pow(Math.sin(this.real) * Math.cosh(this.imag), 2)
				+ Math.pow(Math.cos(this.real) * Math.sinh(this.imag), 2);
		return new Complex((Math.cos(this.real) * Math.sin(this.real)) / nn,
				(Math.cosh(this.imag) * Math.sinh(this.imag)) / nn);
	}

	public Complex pow(double power) {
		double abs0, arg0;
		Complex swap = null;
		abs0 = Math.pow(this.getAbs(), power);
		arg0 = this.getArg() * power;
		swap = new Complex(abs0, arg0, true);
		return swap;
	}

	public Complex exp() {
		return new Complex(Math.cos(this.getImag()) * (Math.sinh(this.getReal()) + Math.cosh(this.getReal())),
				Math.sin(this.getImag()) * (Math.cosh(this.getReal()) + Math.sinh(this.getReal())));
	}

	public Complex ln() {
		return new Complex(Math.log(this.getAbs()), this.getArg());
	}

	public Complex log() {
		return new Complex(Math.log10(this.getAbs()), Math.log10(Math.exp(this.getArg())));
	}

	public ArrayList<Complex> root(int number) {
		if (number <= 0) {
			throw new UnperformableActionException("can't take negative root");
		}
		ArrayList<Complex> c = new ArrayList<Complex>(number);
		double[] sqrtargarray = new double[number];
		double abs0 = Math.pow(this.getAbs(), -1 * number);

		double arg0 = ComplexUtil.getRad2Degree(this.arg) / number;
		for (int i = 0; i < number; i++) {
			sqrtargarray[i] = ComplexUtil.getDegree2Rad(arg0 + (2 * i * 180) / number);
			c.add(i, new Complex(abs0, sqrtargarray[i], true));
		}

		return c;
	}

	public Complex add(Complex comp) {
		return new Complex(this.real + comp.getReal(), this.imag + comp.getImag());
	}

	public Complex subtract(Complex comp) {
		return new Complex(this.real - comp.getReal(), this.imag - comp.getImag());
	}

	public Complex multiply(Complex comp) {
		return new Complex(this.abs * comp.getAbs(), this.arg + comp.getArg(), true);
	}

	public Complex divide(Complex comp) {
		return new Complex(this.abs / comp.getAbs(), this.arg - comp.getArg(), true);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(" ( " + this.real);
		if (this.imag > 0) {
			s.append(" + ");
			s.append(this.imag + "i )");
		} else if (this.imag < 0) {
			s.append(" - ");
			s.append(Math.abs(this.imag) + "i )");
		} else {
			s.append(" )");
		}
		return s.toString();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		int temp;
		temp = (int) imag;
		result = prime * result + temp;
		temp = (int) real;
		result = prime * result + temp;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		return ComplexUtil.compare(this, other);

	}

	@Override
	public Complex clone() throws CloneNotSupportedException {

		return new Complex(this.real, this.imag);
	}

}
