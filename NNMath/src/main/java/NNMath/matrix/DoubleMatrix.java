package NNMath.matrix;

import NNMath.exception.SizeMisMatchException;
import NNMath.util.Constants;

public class DoubleMatrix implements Cloneable {
	private Double[][] elements = null;
	private int rows;
	private int cols;

	public DoubleMatrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		elements = new Double[rows][cols];

	}

	public DoubleMatrix(Double[][] elements) {
		this.rows = elements.length;
		this.cols = elements[0].length;
		this.elements = elements;

	}

	public DoubleMatrix(final boolean sourceMatrix[][]) {
		this.elements = new Double[sourceMatrix.length][sourceMatrix[0].length];
		rows = sourceMatrix.length;
		cols = sourceMatrix[0].length;
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getCols(); c++) {
				if (sourceMatrix[r][c]) {
					this.set(r, c, 1);
				} else {
					this.set(r, c, -1);

				}
			}
		}
	}

	public DoubleMatrix(double[][] elements) {
		this.rows = elements.length;
		this.cols = elements[0].length;
		this.elements = new Double[this.rows][this.cols];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.elements[i][j] = new Double(elements[i][j]);
			}
		}
	}

	public Double[][] getElements() {
		return elements;
	}

	public int getRows() {
		return this.rows;
	}

	public int getCols() {
		return this.cols;
	}

	public int size() {
		return this.rows * this.cols;
	}

	public static DoubleMatrix random(int M, int N, final double min,
			final double max) {
		DoubleMatrix A = new DoubleMatrix(M, N);
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				A.elements[i][j] = Math.random() * (max - min) + min;
			}
		}
		return A;
	}

	public double sumElements() {
		double result = 0;
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getCols(); c++) {
				result += this.elements[r][c];
			}
		}
		return result;
	}

	public boolean isZero() {
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				if (this.elements[row][col] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	private void validate(final int row, final int col) {
		if ((row >= getRows()) || (row < 0)) {
			throw new SizeMisMatchException("The row:" + row
					+ " is out of range:" + getRows());
		}
		if ((col >= getCols()) || (col < 0)) {
			throw new SizeMisMatchException("The col:" + col
					+ " is out of range:" + getCols());
		}
	}

	public void set(final int row, final int col, final double value) {
		validate(row, col);
		if (Double.isInfinite(value) || Double.isNaN(value)) {
			throw new IllegalArgumentException(
					"Trying to assign invalid number to matrix: " + value);
		}
		this.elements[row][col] = value;
	}

	public void setZero() {
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getCols(); c++) {
				try {
					this.set(r, c, 0);
				} catch (SizeMisMatchException | IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String toString() {
		String s = "MATRIX IS \n";
		for (int i = 0; i < this.elements.length; i++) {
			for (int j = 0; j < this.elements[0].length; j++) {
				s = s + " " + this.elements[i][j];
			}
			s = s + " \n";
		}
		return s;
	}

	public DoubleMatrix getRow(final int row) {
		if (row > getRows()) {
			throw new SizeMisMatchException("Can't get row #" + row
					+ " because it does not exist.");
		}
		final double newMatrix[][] = new double[1][getCols()];
		for (int col = 0; col < getCols(); col++) {
			newMatrix[0][col] = this.elements[row][col];
		}
		return new DoubleMatrix(newMatrix);
	}

	public DoubleMatrix getCol(final int col) {
		if (col > getCols()) {
			throw new SizeMisMatchException("Can't get column #" + col
					+ " because it does not exist.");
		}
		final double newMatrix[][] = new double[getRows()][1];
		for (int row = 0; row < getRows(); row++) {
			newMatrix[row][0] = this.elements[row][col];
		}
		return new DoubleMatrix(newMatrix);
	}

	public static DoubleMatrix createColumnMatrix(final double input[]) {
		final double d[][] = new double[input.length][1];
		for (int row = 0; row < d.length; row++) {
			d[row][0] = input[row];
		}
		return new DoubleMatrix(d);
	}

	public static DoubleMatrix createRowMatrix(final double input[]) {
		final double d[][] = new double[1][input.length];
		System.arraycopy(input, 0, d[0], 0, input.length);
		return new DoubleMatrix(d);
	}

	@Override
	public int hashCode() {
		return (this.size() * 100000) + this.rows;
	}

	private boolean equal(double d1, double d2) {
		return Math.abs(d1 - d2) <= Constants.EPSILON;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.elements == null) {
			return false;
		}
		DoubleMatrix obj2 = (DoubleMatrix) obj;
		if (this.rows != obj2.getRows() && this.cols != obj2.getCols()) {
			return false;
		}
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				try {
					if (!equal(this.get(i, j), obj2.get(i, j))) {
						return false;
					}
				} catch (SizeMisMatchException e) {
				}
			}
		}
		return true;
	}

	public boolean isVector() {
		return ((getRows() == 1) || (getCols() == 1));
	}

	public double get(final int row, final int col) {
		validate(row, col);
		return this.elements[row][col];
	}

	public boolean isLowerTriangular() {
		for (int i = 0; i < getRows(); i++) {
			for (int j = i + 1; j < getCols(); j++) {
				if (elements[i][j] != 0.0)
					return false;
			}
		}
		return true;
	}

	public boolean isUpperTriangular() {
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < i; j++) {
				if (elements[i][j] != 0.0)
					return false;
			}
		}
		return true;
	}

	public boolean isSymmetric() {
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < i; j++) {
				if (this.elements[i][j] != this.elements[j][i])
					return false;
			}
		}
		return true;
	}

	public double[][] toDoubleArray() {
		double[][] temp = new double[getRows()][getCols()];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				temp[i][j] = this.elements[i][j];
			}
		}
		return temp;
	}

	public static DoubleMatrix identity(final int size) {
		if (size < 1) {
			throw new SizeMisMatchException(
					"Identity DoubleMatrix must be at least of size 1.");
		}
		final DoubleMatrix result = new DoubleMatrix(size, size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) {
					try {
						result.set(i, j, 1);
					} catch (IllegalArgumentException e) {
					}
				} else {
					try {
						result.set(i, j, 0);
					} catch (IllegalArgumentException e) {
					}
				}
			}
		}
		return result;
	}

	public DoubleMatrix getSubMatrix(int rowStartIndex, int rowEndIndex,
			int colStartIndex, int colEndIndex) {
		if (rowEndIndex > (this.getRows() - 1) || rowEndIndex < 1
				|| rowStartIndex > (this.getRows() - 2) || rowStartIndex < 0
				|| rowStartIndex >= rowEndIndex) {
			throw new SizeMisMatchException(
					"the row starting index is = "
							+ rowStartIndex
							+ " and end index is = "
							+ rowEndIndex
							+ " ,which is either not in range(0,"
							+ this.getRows()
							+ " ,or end index is equal to or smaller than strating index");
		}
		if (colEndIndex > (this.getCols() - 1) || colEndIndex < 1
				|| colStartIndex > (this.getCols() - 2) || colStartIndex < 0
				|| colStartIndex >= colEndIndex) {
			throw new SizeMisMatchException(
					"the col starting index is = "
							+ colStartIndex
							+ " and end index is = "
							+ colEndIndex
							+ " ,which is either not in range(0,"
							+ this.getCols()
							+ " ,or end index is equal to or smaller than strating index");
		}
		double[][] d = new double[rowEndIndex - rowStartIndex + 1][colEndIndex
				- colStartIndex + 1];
		for (int i = rowStartIndex; i <= rowEndIndex; i++) {
			for (int j = colStartIndex; j <= colEndIndex; j++) {
				d[i - rowStartIndex][j - colStartIndex] = this.elements[i][j];
			}
		}
		return new DoubleMatrix(d);
	}

	public double[] toPackedArray() {
		final double[] result = new double[getRows() * getCols()];
		int index = 0;
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getCols(); c++) {
				result[index++] = new Double(elements[r][c]);
			}
		}
		return result;
	}

	public void setFromPackedArray(final Double[] array) {
		if (array.length != this.size()) {
			throw new SizeMisMatchException(
					"array size and row,columns does match up!!!");
		}
		int index = 0;
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getCols(); c++) {
				this.elements[r][c] = array[index++];
			}
		}
	}

	public double mod(int index) {
		if (index > this.getRows() || index < 0) {
			throw new SizeMisMatchException("INDEX OUT OF BOUND");
		}
		double mod = 0;
		for (int i = 0; i < elements[0].length; i++) {
			mod = mod + (elements[index][i] * elements[index][i]);
		}
		mod = Math.sqrt(mod);
		return mod;
	}

	public void add(final int row, final int col, final double value) {
		validate(row, col);
		final double newValue = get(row, col) + value;
		set(row, col, newValue);
	}

	@Override
	public DoubleMatrix clone() {
		DoubleMatrix d = new DoubleMatrix(getRows(), getCols());
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				d.set(i, j, this.elements[i][j]);
			}
		}
		return d;
	}

	public void setElements(double[][] elements) {
		if ((this.rows != elements.length) || (this.cols != elements[0].length)) {
			throw new SizeMisMatchException(
					"dimentions do not match given rows and cols value");
		} else {
			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.cols; j++) {
					this.elements[i][j] = elements[i][j];
				}
			}
		}
	}

	public double getDeterminant() {
		return DoubleMatrixUtil.Determinant(this);

	}

}