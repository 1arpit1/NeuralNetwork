package NNMath.matrix;

public class GramSchmidtOrthonormalizationDouble {
	public static DoubleMatrix solve(DoubleMatrix a)
	{
		return new DoubleMatrix(solve(a.toDoubleArray()));	
	}
	public static double[][] solve(double[][] a)
	{
		double[][] b=DoubleMatrixUtil.transpose(new DoubleMatrix(a)).toDoubleArray();	
		double[][] c=new double[a.length][a[0].length];
		double d[]=new double[b[0].length];
		c[0]=devide(b[0],mod(b[0]));
		int j;
		for(int i=1;i<b.length;i++)
		{
			j=i;	
			while(j>0)
			{   j--;
			d=add(d,multiply(c[j],dot(c[j],b[i])));
			}
			c[i]=devide(subtract(b[i],d),mod(subtract(b[i],d)));
			d=setZero(d);
		}
		return DoubleMatrixUtil.transpose(new DoubleMatrix(c)).toDoubleArray();
	}
	
	private static double[] setZero(double [] b)
	{
		for(int i=0;i<b.length;i++)
		{ b[i]=0;	}
		return b;
	}
	private static double[] add(double[] b,double [] c)
	{
		double []temp=new double[b.length];
		for (int i=0;i<b.length;i++)
		{  temp[i]=b[i]+c[i];  }
		return temp;
	}
	private static double[] subtract(double[] b,double [] c)
	{
		double []temp=new double[b.length];
		for (int i=0;i<b.length;i++)
		{
			temp[i]=b[i]-c[i];
		}
		return temp;
	}
	private static double[] devide(double[] b,double c)
	{
		for(int i=0;i<b.length;i++)
		{
			b[i]=b[i]/c;
		}
		return b;
	}
	private static double dot(double[] b,double[] c)
	{
		double dot=0;
		for(int i=0;i<b.length;i++)
		{
			dot=dot+b[i]*c[i];
		}
		return dot;
	}
	//element wise multiplication
	@SuppressWarnings("unused")
	private static double[] multiply(double[] b,double []c)
	{
		double []temp=new double[b.length];
		for(int i=0;i<b.length;i++)
		{
			temp[i]=b[i]*c[i];
		}
		return temp;
	}
	private static double[] multiply(double[] b,double c)
	{
		double []temp=new double[b.length];
		for(int i=0;i<b.length;i++)
		{
			temp[i]=b[i]*c;
		}
		return temp;
	}
	private static double mod(double[] b)
	{
		double mod=0;
		for(int i=0;i<b.length;i++)
		{
			mod=mod+b[i]*b[i];	
		}
		mod=Math.sqrt(mod);
		return mod;
	}

}
