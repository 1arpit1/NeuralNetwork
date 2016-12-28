package NNMath.matrix;

public class QRDecomposeDouble {
	DoubleMatrix m = null;
	private DoubleMatrix gramSchmidt = null;
	private DoubleMatrix uppertriangle = null;

	public QRDecomposeDouble(DoubleMatrix m) {
		this.m = m.clone();
		gramSchmidt = GramSchmidtOrthonormalizationDouble.solve(m);
		uppertriangle = DoubleMatrixUtil.matrixMultiply(DoubleMatrixUtil.transpose(gramSchmidt), m);

	}

	public DoubleMatrix getGramSchmidt() {
		return gramSchmidt;
	}

	public DoubleMatrix getUppertriangle() {
		return uppertriangle;
	}
}
