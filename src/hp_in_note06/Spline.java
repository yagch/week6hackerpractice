package hp_in_note06;

public class Spline {
	static double[] x = {1, 2, 3, 4, 5, 6};
	static double[] y = {0, 1, 0, -1, 0, 1};
	public static void main(String[] arg) {
		for(int i = 0; i < 5; i++) {
			System.out.println("a" + (i + 1) + " = " + para()[i]);
		}
		for(int i = 5; i < 10; i++) {
			System.out.println("b" + (i - 4) + " = " + para()[i]);
		}
		for(int i = 10; i < 16; i++) {
			System.out.println("k" + (i - 9) + " = " + para()[i]);
		}
	}
	
	// Get parameter k and a and b
	public static double[] para() {
		int d = x.length - 1;
		double[] k = new double[d + 1];
		double[][] a = new double[d + 1][d + 1];
		double[] re = new double[d + 1];
		a[0][0] = 2 * (x[1] - x[0]);
		a[0][1] = x[1] - x[0];
		a[d][d - 1] = 2 * (x[d] - x[d - 1]);
		a[d][d] = x[d] - x[d - 1];
		for(int i = 1; i < d; i++) {
			a[i][i - 1] = 1 / (x[i] - x[i - 1]);
			a[i][i] = 2 * (1 / (x[i] - x[i - 1]) + 1 / (x[i + 1] - x[i]));
			a[i][i + 1] = 1 / (x[i + 1] - x[i]);
		}
		re[0] = 3 * (y[1] - y[0]);
		re[d] = 3 * (y[d] - y[d - 1]);
		for(int i = 1; i < d; i++) {
			re[i] = 3 * ((y[i] - y[i - 1]) / ((x[i] - x[i - 1]) * (x[i] - x[i - 1])) + (y[i + 1] - y[i]) / ((x[i + 1] - x[i]) * (x[i + 1] - x[i])));
		}
		
		// parameters used to get inverse of matrix a
		double[][] aInv = new double[d + 1][d + 1];  //inverse of a
		double[] theta = new double[d + 2];
		double[] phi = new double[d + 2];
		theta[0] = 1;
		theta[1] = a[0][0];
		phi[d + 1] = 1;
		phi[d] = a[d][d];
		for(int i = 2; i < d + 2; i++) {
			theta[i] = a[i - 1][i - 1] * theta[i - 1] - a[i - 2][i - 1] * a[i - 1][i - 2] * theta[i - 2];
		}
		for(int i = d - 1; i >= 0; i--) {
			phi[i] = a[i][i] * phi[i + 1] - a[i][i + 1] * a[i + 1][i] * phi[i + 2];
		}
		
		// get Inverse of the matrix a
		for(int i = 0; i <= d; i++) {
			for(int j = 0; j <=d; j++) {
				if(i < j) {
					aInv[i][j] = Math.pow(-1, i + j) * theta[i] * phi[j + 1] / theta[d + 1];
					for(int m = i; m < j; m++) {
						aInv[i][j] *= a[m][m + 1];
					}
				}
				else if(i == j) {
					aInv[i][j] = theta[i] * phi[j + 1] / theta[d + 1];
				}
				else {
					aInv[i][j] = Math.pow(-1, i + j) * theta[i] * phi[j + 1] / theta[d + 1];
					for(int m = i; m < j; m++) {
						aInv[i][j] *= a[m + 1][m] ;
					}
				}
			}
		}
		
		// Use Full Matrix to get k
		FullMatrix Inv = new FullMatrix(aInv);   
		k = Inv.productAx(re);
		double[] ai = new double[d];
		double[] bi = new double[d];
		for(int i = 0; i < d; i++) {
			ai[i] = k[i] * (x[i + 1] - x[i]) - (y[i + 1] - y[i]);
			bi[i] = -1 * k[i] * (x[i + 1] - x[i]) + y[i + 1] - y[i];
		}
		double[] res = new double[d * 3 + 1];
		for(int i = 0; i < d; i++) {
			res[i] = ai[i];
		}
		for(int i = d; i < 2 * d; i++) {
			res[i] = bi[i - d];
		}
		for(int i = 2 * d; i <= 3 * d; i++) {
			res[i] = k[i - d * 2];
		}
		/*It looks weird to put a and b and kinto an array, but it is the best way I have come up with
		 * to compute parameter a and b and kas global parameters, which can be used by the functions.
		 */
        return res;

	}
	public double q1(double xx) {
		double t = (xx - x[0]) / (x[0] - x[1]);
		return (1 - t) * y[0] + t * y[1] + t * (1 - t) * (para()[0] * (1 - t) + para()[5] * t);
	}
	public double q2(double xx) {
		double t = (xx - x[1]) / (x[1] - x[2]);
		return (1 - t) * y[1] + t * y[2] + t * (1 - t) * (para()[1] * (1 - t) + para()[6] * t);
	}
	public double q3(double xx) {
		double t = (xx - x[2]) / (x[2] - x[3]);
		return (1 - t) * y[2] + t * y[3] + t * (1 - t) * (para()[2] * (1 - t) + para()[7] * t);
	}
	public double q4(double xx) {
		double t = (xx - x[3]) / (x[3] - x[4]);
		return (1 - t) * y[3] + t * y[4] + t * (1 - t) * (para()[3] * (1 - t) + para()[8] * t);
	}
	public double q5(double xx) {
		double t = (xx - x[4]) / (x[4] - x[5]);
		return (1 - t) * y[4] + t * y[5] + t * (1 - t) * (para()[4] * (1 - t) + para()[9] * t);
	}
}
