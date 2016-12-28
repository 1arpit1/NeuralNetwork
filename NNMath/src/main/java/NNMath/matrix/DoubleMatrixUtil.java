package NNMath.matrix;

import NNMath.exception.SizeMisMatchException;

public class DoubleMatrixUtil {
	private DoubleMatrixUtil() {
	}

	public static DoubleMatrix inverse(final DoubleMatrix input) {
		return InverseMatrixDouble.inverse(input);
	}

	public static DoubleMatrix add(final DoubleMatrix a, final DoubleMatrix b) {
		if (a.getRows() != b.getRows()) {
			throw new SizeMisMatchException(
					"To add the matrices they must have the same number of rows and columns.  DoubleMatrix a has "
							+ a.getRows() + " rows and DoubleMatrix b has " + b.getRows() + " rows.");
		}
		if (a.getCols() != b.getCols()) {
			throw new SizeMisMatchException(
					"To add the matrices they must have the same number of rows and columns.  DoubleMatrix a has "
							+ a.getCols() + " cols and DoubleMatrix b has " + b.getCols() + " cols.");
		}

		final double result[][] = new double[a.getRows()][a.getCols()];

		for (int resultRow = 0; resultRow < a.getRows(); resultRow++) {
			for (int resultCol = 0; resultCol < a.getCols(); resultCol++) {
				result[resultRow][resultCol] = a.get(resultRow, resultCol) + b.get(resultRow, resultCol);
			}
		}
		return new DoubleMatrix(result);
	}

	public static DoubleMatrix subtract(final DoubleMatrix a, final DoubleMatrix b) {
		if (a.getRows() != b.getRows()) {
			throw new SizeMisMatchException(
					"To subtract the matrices they must have the same number of rows and columns.  DoubleMatrix a has "
							+ a.getRows() + " rows and DoubleMatrix b has " + b.getRows() + " rows.");
		}
		if (a.getCols() != b.getCols()) {
			throw new SizeMisMatchException(
					"To subtract the matrices they must have the same number of rows and columns.  DoubleMatrix a has "
							+ a.getCols() + " cols and DoubleMatrix b has " + b.getCols() + " cols.");
		}
		final double result[][] = new double[a.getRows()][a.getCols()];
		for (int resultRow = 0; resultRow < a.getRows(); resultRow++) {
			for (int resultCol = 0; resultCol < a.getCols(); resultCol++) {
				result[resultRow][resultCol] = a.get(resultRow, resultCol) - b.get(resultRow, resultCol);
			}
		}
		return new DoubleMatrix(result);
	}

	public static void copy(final DoubleMatrix source, final DoubleMatrix target) {
		if ((target.getCols() != source.getCols()) || (target.getRows() != source.getRows())) {
			throw new SizeMisMatchException("source and target matrices should be equal in sizes");
		}
		for (int row = 0; row < source.getRows(); row++) {
			for (int col = 0; col < source.getCols(); col++) {
				target.set(row, col, source.get(row, col));

			}
		}
	}

	public static DoubleMatrix transpose(final DoubleMatrix input) {
		final double inverseDoubleMatrix[][] = new double[input.getCols()][input.getRows()];
		for (int r = 0; r < input.getRows(); r++) {
			for (int c = 0; c < input.getCols(); c++) {
				try {
					inverseDoubleMatrix[c][r] = input.get(r, c);
				} catch (SizeMisMatchException e) {
				}
			}
		}
		return new DoubleMatrix(inverseDoubleMatrix);
	}

	public static DoubleMatrix scaleMultiply(final DoubleMatrix a, final double b) {
		final double result[][] = new double[a.getRows()][a.getCols()];
		for (int row = 0; row < a.getRows(); row++) {
			for (int col = 0; col < a.getCols(); col++) {
				try {
					result[row][col] = a.get(row, col) * b;
				} catch (SizeMisMatchException e) {
				}
			}
		}
		return new DoubleMatrix(result);
	}

	public static DoubleMatrix scaleDivide(final DoubleMatrix a, final double b) {
		final double result[][] = new double[a.getRows()][a.getCols()];
		for (int row = 0; row < a.getRows(); row++) {
			for (int col = 0; col < a.getCols(); col++) {
				try {
					result[row][col] = a.get(row, col) / b;
				} catch (SizeMisMatchException e) {
				}
			}
		}
		return new DoubleMatrix(result);
	}

	public static DoubleMatrix matrixMultiply(final DoubleMatrix a, final DoubleMatrix b) {
		if (a.getCols() != b.getRows()) {
			throw new SizeMisMatchException(
					"To use ordinary DoubleMatrix multiplication the number of columns on the first DoubleMatrix must match the number of rows on the second.");
		}
		final double result[][] = new double[a.getRows()][b.getCols()];
		for (int resultRow = 0; resultRow < a.getRows(); resultRow++) {
			for (int resultCol = 0; resultCol < b.getCols(); resultCol++) {
				double value = 0;
				for (int i = 0; i < a.getCols(); i++) {
					value += a.get(resultRow, i) * b.get(i, resultCol);
				}
				result[resultRow][resultCol] = value;
			}
		}
		return new DoubleMatrix(result);
	}

	public static DoubleMatrix deleteCol(final DoubleMatrix DoubleMatrix, final int deletedIndex) {
		if (deletedIndex >= DoubleMatrix.getCols() || deletedIndex < 0) {
			throw new SizeMisMatchException("Can't delete column " + deletedIndex + " from DoubleMatrix, it only has "
					+ DoubleMatrix.getCols() + " columns.");
		}
		final double newDoubleMatrix[][] = new double[DoubleMatrix.getRows()][DoubleMatrix.getCols() - 1];

		for (int row = 0; row < DoubleMatrix.getRows(); row++) {
			int targetCol = 0;

			for (int col = 0; col < DoubleMatrix.getCols(); col++) {
				if (col != deletedIndex) {
					newDoubleMatrix[row][targetCol] = DoubleMatrix.get(row, col);
					targetCol++;
				}
			}
		}
		return new DoubleMatrix(newDoubleMatrix);
	}

	public static DoubleMatrix deleteRow(final DoubleMatrix DoubleMatrix, final int deletedIndex) {
		if (deletedIndex >= DoubleMatrix.getRows() || deletedIndex < 0) {
			throw new SizeMisMatchException("Can't delete row " + deletedIndex + " from DoubleMatrix, it only has "
					+ DoubleMatrix.getRows() + " rows.");
		}
		final double newDoubleMatrix[][] = new double[DoubleMatrix.getRows() - 1][DoubleMatrix.getCols()];
		int targetRow = 0;
		for (int row = 0; row < DoubleMatrix.getRows(); row++) {
			if (row != deletedIndex) {
				for (int col = 0; col < DoubleMatrix.getCols(); col++) {
					newDoubleMatrix[targetRow][col] = DoubleMatrix.get(row, col);
				}
				targetRow++;
			}
		}
		return new DoubleMatrix(newDoubleMatrix);
	}

	public static DoubleMatrix deleteRowCol(final DoubleMatrix DoubleMatrix, final int deletedRowIndex,
			final int deletedColIndex) {
		if (deletedColIndex >= DoubleMatrix.getCols() || deletedColIndex < 0) {
			throw new SizeMisMatchException("Can't delete column " + deletedColIndex
					+ " from DoubleMatrix, it only has " + DoubleMatrix.getCols() + " columns.");
		}
		if (deletedRowIndex >= DoubleMatrix.getRows() || deletedRowIndex < 0) {
			throw new SizeMisMatchException("Can't delete column " + deletedRowIndex
					+ " from DoubleMatrix, it only has " + DoubleMatrix.getCols() + " columns.");
		}
		final double newDoubleMatrix[][] = new double[DoubleMatrix.getRows() - 1][DoubleMatrix.getCols() - 1];
		int targetRow = -1;
		for (int row = 0; row < DoubleMatrix.getRows(); row++) {
			int targetCol = 0;
			if (row != deletedRowIndex) {
				targetRow++;
				for (int col = 0; col < DoubleMatrix.getCols(); col++) {
					if (col != deletedColIndex) {
						newDoubleMatrix[targetRow][targetCol] = DoubleMatrix.get(row, col);
						targetCol++;
					}
				}
			}
		}
		return new DoubleMatrix(newDoubleMatrix);
	}

	public static DoubleMatrix randomTriangular(int N) {
		DoubleMatrix I = new DoubleMatrix(N, N);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= i; j++) {
				I.set(i, j, Math.random());
			}
		}
		return I;
	}

	public static double Determinant(final DoubleMatrix input) {
		double determinant = 0;
		double[][] d = input.toDoubleArray();
		int s;
		if (d.length != d[0].length) {
			throw new SizeMisMatchException("THE DoubleMatrix IS NOT SQUARE.SO DETERMINANT NOT POSSIBLE");
		} else {
			if (input.getRows() == 1) {
				return input.get(0, 0);
			} else {
				for (int i = 0; i < input.getCols(); i++) {
					s = (i % 2 == 0) ? 1 : -1;
					DoubleMatrix m = DoubleMatrixUtil.deleteRowCol(input, 0, i);
					determinant = determinant + s * input.get(0, i) * DoubleMatrixUtil.Determinant(m);
				}
				return determinant;
			}
		}
	}

	public static double dotProduct(final DoubleMatrix a, final DoubleMatrix b) {
		if (!a.isVector() || !b.isVector()) {
			throw new SizeMisMatchException("To take the dot product, both matrices must be vectors.");
		}

		final double aArray[] = a.toPackedArray();
		final double bArray[] = b.toPackedArray();

		if (aArray.length != bArray.length) {
			throw new SizeMisMatchException("To take the dot product, both matrices must be of the same length.");
		}

		double result = 0;
		final int length = aArray.length;

		for (int i = 0; i < length; i++) {
			result += aArray[i] * bArray[i];
		}

		return result;
	}

	public static double vectorLength(final DoubleMatrix input) {
		if (!input.isVector()) {
			throw new SizeMisMatchException("Can only take the vector length of a vector.");
		}
		final double v[] = input.toPackedArray();
		double rtn = 0.0;
		for (int i = 0; i < v.length; i++) {
			rtn += Math.pow(v[i], 2);
		}
		return Math.sqrt(rtn);
	}

	public static DoubleMatrix randomSymmetric(int N) {
		DoubleMatrix I = randomTriangular(N);
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				I.set(i, j, I.get(j, i));
			}
		}
		return I;
	}

}
