package hp_in_note06;

public class PowerLaw {
	public static void main(String[] arg) {
		// set the initial guess to (0,0)
	    double[] x = {1.0, 4.5, 9.0, 20, 74, 181};
	    double[] y = {3.0, 49.4, 245, 1808, 22000, 73000};
		double a = 0.5;
		double m = 0.5;
		// deltaa
		double deltaa = (hessianf(a, m, x, y)[1][1] * f1(a, m, x, y) - hessianf(a, m, x, y)[0][1] * f2(a, m, x, y)) / (hessianf(a, m, x, y)[0][1] * hessianf(a, m, x, y)[1][0] - hessianf(a, m, x, y)[1][1] * hessianf(a, m, x, y)[0][0]);
		// deltab
		double deltam = (hessianf(a, m, x, y)[0][0] * f2(a, m, x, y) - hessianf(a, m, x, y)[1][0] * f1(a, m, x, y)) / (hessianf(a, m, x, y)[0][1] * hessianf(a, m, x, y)[1][0] - hessianf(a, m, x, y)[1][1] * hessianf(a, m, x, y)[0][0]);
		int iter = 0;    // Nubber of iteration
		System.out.println("while a0 = " + a + "m0 = " + m);
		while(Math.sqrt(deltaa * deltaa + deltam * deltam) / Math.sqrt(a * a + m * m) >= Math.pow(10, -7)) {
			deltaa = (hessianf(a, m, x, y)[1][1] * f1(a, m, x, y) - hessianf(a, m, x, y)[0][1] * f2(a, m, x, y)) / (hessianf(a, m, x, y)[0][1] * hessianf(a, m, x, y)[1][0] - hessianf(a, m, x, y)[1][1] * hessianf(a, m, x, y)[0][0]);
			deltam = (hessianf(a, m, x, y)[0][0] * f2(a, m, x, y) - hessianf(a, m, x, y)[1][0] * f1(a, m, x, y)) / (hessianf(a, m, x, y)[0][1] * hessianf(a, m, x, y)[1][0] - hessianf(a, m, x, y)[1][1] * hessianf(a, m, x, y)[0][0]);
			System.out.println("a" + iter + " = " +  a);
			System.out.println("m" + iter + " = " +  m);
			System.out.println("||delta||" + iter + " = " + Math.sqrt(deltaa * deltaa + deltam * deltam));
			a += deltaa;
			m += deltam;
			iter++;
		}
	}
	


	// f1
	public static double f1(double a, double m, double[] x, double[] y) {
		int n = x.length;
		double res = 0;
		for(int i = 0; i < n; i++) {
			res += a * Math.pow(x[i], 2 * m) - y[i] * Math.pow(x[i], m);
		}
		return res;
	}
	
	//f2
	public static double f2(double a, double m, double[] x, double[] y) {
		int n = x.length;
		double res = 0;
		for(int i = 0; i < n; i++) {
			res += a * a * Math.pow(x[i], 2 * m) * Math.log(x[i]) - a * y[i] * Math.pow(x[i], m) * Math.log(x[i]);
		}
		return res;
	}
	
	//Hessian metrix
	public static double[][] hessianf(double a, double m, double[] x, double[] y) {
		int n = x.length;
		double[][] res = new double[2][2];
        res[0][0] = 0;
        res[0][1] = 0;
        res[1][0] = 0;
        res[1][1] = 0;
        for(int i = 0; i < n; i++) {
    		    res[0][0] += Math.pow(x[i], 2 * m);
    			res[0][1] += 2 * a * Math.pow(x[i], 2 * m) - y[i] * Math.pow(x[i], m);
    			res[1][0] += 2 * a * Math.pow(x[i], 2 * m) * Math.log(x[i]) - y[i] * Math.pow(x[i], m) * Math.log(x[i]);
    			res[1][1] += 2 * a * a * Math.pow(x[i], 2 * m) * Math.log(x[i]) - a * y[i] * Math.pow(x[i], m) * Math.log(x[i]);
        }
		return res;
	}
}
