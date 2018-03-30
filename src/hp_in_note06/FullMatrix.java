package hp_in_note06;

public class FullMatrix {
	public static int rank;
	public static double[][] matrixB;
	
    public FullMatrix(int ran) {
    	    rank = ran;
    	    double[][] B = new double[ran][ran];
    	    for(int i = 0; i < ran; i++)
    	    	    for(int j = 0; j < ran; j++)
    	    	    	    B[i][j] = 0;
    	    matrixB = B;
    }
    
    public FullMatrix(double[][] mat) {
    	    double[][] helper = new double[mat.length][mat.length];
    	    rank = mat.length;
    	    for(int i = 0; i < rank; i++) {
    	    	    for(int j = 0; j < rank; j++)
    	    	    	    helper[i][j] = mat[i][j];
    	    }
    	    matrixB = helper;
    }
    
	public void add(int i, int j, double a) {
		matrixB[i][j] = a;
	}
	
	public void permute(int i, int j) {
		if(i > rank || j > rank) {
			System.out.println("Cannot permute");
			return;
		}
		for(int m = 0; m < rank; m++) {
			double n = matrixB[i - 1][m];
			matrixB[i - 1][m] = matrixB[j - 1][m];
			matrixB[j - 1][m] = n;
		}
	}
	
	public void scaling(int i, int j, double a) {
		if(i > rank || j > rank) {
			System.out.println("Cannot permute");
			return;
		}
		for(int m = 0; m < rank; m++) {
			matrixB[j - 1][m] = matrixB[j - 1][m] + a * matrixB[i - 1][m];
		}
	}
	
	public double[] productAx(double[] x) {
		if(x.length != rank) {
			System.out.println("Cannot product");
		}
		double[] res = new double[rank];
		for(int i = 0; i < rank; i++) {
			double val = 0;
			for(int j = 0; j < rank; j++) {
				val += matrixB[i][j] * x[j];
			}
			res[i] = val;
		}
		return res;
	}
	
	public void output() {
		System.out.println("The matrix is: " );
		for(int i = 0; i < rank; i++) {
			for(int j = 0; j < rank; j++) {
				System.out.print(matrixB[i][j] + " ");
				if(j == rank - 1)
					System.out.println("");
			}
		}
	}
}
