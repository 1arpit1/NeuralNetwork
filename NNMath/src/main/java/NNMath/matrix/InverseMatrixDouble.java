package NNMath.matrix;

import NNMath.exception.SizeMisMatchException;


public class InverseMatrixDouble {
	public static DoubleMatrix inverse(DoubleMatrix m) 
	{   DoubleMatrix m1=m.clone();
	if(m.getRows()!=m1.getCols())
	{
		throw new SizeMisMatchException("NOT A SQUARE MATRIX");
	}
	DoubleMatrix temp=new DoubleMatrix((inverse(m1.toDoubleArray())));
	return temp;
	}
	private static double[] devide(double[] b,double factor)
	{
		for(int i=0;i<b.length;i++)
		{
			b[i]=b[i]/factor;
		}
		return b;
	}
	public static double[][] inverse(double[][] a)
	{
		if(a.length!=a[0].length)
		{
			throw new SizeMisMatchException("NOT A SQUARE MATRIX");
		}
		double[][] b=DoubleMatrix.identity(a.length).toDoubleArray();
		for (int k = 0; k <a.length; k++)
		{ 
			double d=a[k][k];
			if(d==0.0)
			{
				if ((k + 1) < a[0].length)
				{   int max = 0;
				max = findLargestPivot(a, k); 
				if (max == -1) 
				{ 
					throw new SizeMisMatchException("UNNECESSARY VARIABLE!!!SINGULAR MATRIX!!!"); 
				} else if (max != 0)
				{   
					b=swap(b,k,max);	
					a = swap(a,k, max);  	
				}
				}
				else
				{
					throw new SizeMisMatchException("UNNECESSARY VARIABLE!!! SINGULAR MATRIX!!!!");
				}
			}
			d=a[k][k];
			a[k]=devide(a[k], d);
			b[k]=devide(b[k],d);
			int l;
			l=k+1;
			while(l<a.length)
			{
				d=a[l][k];
				for (int j=k;j<a[k].length;j++)
				{
					a[l][j]=a[l][j]-d*a[k][j];
					b[l][j]=b[l][j]-d*b[k][j];
				}
				l=l+1;
			}
			int y=k;
			while(y>0)
			{   y--;
			b[y]=subtract(b[y], b[k], a[y][k]);
			a[y]=subtract(a[y], a[k], a[y][k]);	  
			}
		}
		return b;
	}

	private static double[] subtract(double[] b1,double[]b2,double factor)
	{
		for(int i=0;i<b1.length;i++)
		{
			b1[i]=b1[i]-factor*b2[i];
		}
		return b1;
	}
	private static int findLargestPivot(double[][] a, int col) {
		int maxRow = - 1;
		double maxValue = 0;
		for(int row = col; row < a.length; row++) {
			if (Math.abs(a[row][col]) > maxValue) {
				maxRow = row;
				maxValue = Math.abs(a[row][col]);
			}
		}
		return maxRow;
	}

	private static double[][] swap(double[][] a, int col, int row) {
		double temp;
		for (int i = col; i < a[col].length; i++) {
			temp = a[col][i];
			a[col][i] = a[row][i];
			a[row][i] = temp;
		}
		return a;
	}

}
