package NNMath.matrix;

import NNMath.exception.MultipleValueException;
import NNMath.exception.SizeMisMatchException;

public class SolveLinearEquationsDouble {
	
	
	public static DoubleMatrix solve(DoubleMatrix a_mat,DoubleMatrix b_mat)
	{
		if(a_mat.getRows()!=b_mat.getRows())
		{
			throw new SizeMisMatchException("MATRICES DON'T HAVE SAME DIMENTION");
		}
		double d[][] =new double[1][];
		d[0]=solve(a_mat.toDoubleArray(), b_mat.toPackedArray());
		return new DoubleMatrix(d);
	}
	
	
	public static double[] solve(double[][] a,double[] b) 
	{
		if(a.length!=b.length)
		{
			throw new SizeMisMatchException("MATRICES DON'T HAVE SAME DIMENTION");
		}
		for (int k = 0; k <a.length; k++)
		{ 
			double d=a[k][k];
			//if this element is 0,then we will swap it with largest pivot
			if(d==0.0)
			{
				if ((k + 1) < a[0].length)
				{   int max = 0;
				max = findLargestPivot(a, k); 
				if (max == -1) 
				{ 
					throw new MultipleValueException("UNNECESSARY VARIABLE!!! MULTIPLE SOLUTION MAY EXIST!!!"); 
				} else if (max != 0)
				{   double temp1;
				temp1=b[k];
				b[k]=b[max];
				b[max]=temp1;
				a = swapColRow(a,k, max);  	
				}
				}
				else
				{
					throw new SizeMisMatchException("UNNECESSARY VARIABLE!!! MULTIPLE SOLUTION MAY EXIST!!!");
				}
			}
			d=a[k][k];
			b[k]=b[k]/d;
			for (int j=k;j<a[k].length;j++)
			{
				a[k][j]=a[k][j]/d;
			}
			int l;
			l=k+1;
			while(l<a.length)
			{
				d=a[l][k];
				b[l]=b[l]-d*b[k];
				for (int j=k;j<a[k].length;j++)
				{
					a[l][j]=a[l][j]-d*a[k][j];
				}
				l=l+1;
			}
		}
		int index=b.length;
		double sol[]=new double[index];
		sol[index-1]=b[index-1];
		int j;
		for (int i=a.length-2;i>=0;i--)
		{
			j=i+1;
			while(j<a.length)
			{
				b[i]=b[i]-a[i][j]*sol[j];	
				j=j+1;        	
			}
			sol[i]=b[i]; 
		}
		return sol;
	}

	//this will find the abs largest value from a[col][col] to a[a.length-1][col]
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
	
	//this will swap col and row values
	private static double[][] swapColRow(double[][] a, int col, int row) {
		double temp;
		for (int i = col; i < a[col].length; i++) {
			temp = a[col][i];
			a[col][i] = a[row][i];
			a[row][i] = temp;
		}
		return a;
	}

}
