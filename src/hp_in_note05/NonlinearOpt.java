package hp_in_note05;

public class NonlinearOpt {
	public static void main(String[] arg) {
		// set the initial guess to (0,0)
		double upperbound = 15;
		double x1 = 0.5;
		double x2 = 0.5;
		// deltax1
		double deltax1 = (hessianf(x1, x2)[1][1] * fd(x1, x2)[0] - hessianf(x1, x2)[0][1] * fd(x1, x2)[1]) / (hessianf(x1, x2)[0][1] * hessianf(x1, x2)[1][0] - hessianf(x1, x2)[1][1] * hessianf(x1, x2)[0][0]);
		// deltax2
		double deltax2 = (hessianf(x1, x2)[0][0] * fd(x1, x2)[1] - hessianf(x1, x2)[1][0] * fd(x1, x2)[0]) / (hessianf(x1, x2)[0][1] * hessianf(x1, x2)[1][0] - hessianf(x1, x2)[1][1] * hessianf(x1, x2)[0][0]);
		int iter = 0;    // Number of iteration
		System.out.println("while x0 = (0,0)");
		while(Math.sqrt(deltax1 * deltax1 + deltax2 * deltax2) / Math.sqrt(x1 * x1 + x2 * x2) >= Math.pow(10, -7)) {
			deltax1 = (hessianf(x1, x2)[1][1] * fd(x1, x2)[0] - hessianf(x1, x2)[0][1] * fd(x1, x2)[1]) / (hessianf(x1, x2)[0][1] * hessianf(x1, x2)[1][0] - hessianf(x1, x2)[1][1] * hessianf(x1, x2)[0][0]);
			deltax2 = (hessianf(x1, x2)[0][0] * fd(x1, x2)[1] - hessianf(x1, x2)[1][0] * fd(x1, x2)[0]) / (hessianf(x1, x2)[0][1] * hessianf(x1, x2)[1][0] - hessianf(x1, x2)[1][1] * hessianf(x1, x2)[0][0]);
			double t = bisection(1, upperbound, x1, x2, deltax1, deltax2);
			if(Math.abs(fdt(t, x1, x2, deltax1, deltax2)[0]) <= Math.max(fdt(1, x1, x2, deltax1, deltax2)[0], fdt(upperbound, x1, x2, deltax1, deltax2)[0])) {
				if(Math.abs(fdt(1, x1, x2, deltax1, deltax2)[0]) < Math.abs(fdt(upperbound, x1, x2, deltax1, deltax2)[0])) {
					t = 1;
				}
				else {
					t = upperbound;
				}
			}
			deltax1 *= t;
			deltax2 *= t;
			System.out.println("||x||" + iter + " = " +  Math.sqrt(x1 * x1 + x2 * x2));
			System.out.println("scalar t = " + t);
			System.out.println("||deltax||" + iter + " = " + Math.sqrt(deltax1 * deltax1 + deltax2 * deltax2));
			System.out.println("f(x" + iter + ") = " + f(x1, x2) );
			System.out.println("fdx = " + fd(x1, x2)[0] + ", " + fd(x1, x2)[1]);
			x1 += deltax1;
			x2 += deltax2;
			iter++;
		}
	}
	
	// f(x)
	public static double f(double x1, double x2) {
		return (3 * x1 * x1 + x2 - 4) * (3 * x1 * x1 + x2 - 4) + (x1 * x1 - 3 * x2 + 2) * (x1 * x1 - 3 * x2 + 2);
	}
	// Hessian matrix of f(x)
	/*public static double[][] hessianf(double x1, double x2) {
		double[][] res = new double[2][2];
		res[0][0] = (f(1.0002 * x1, x2) - 2 * f(1.0001 * x1, x2) + f(x1, x2)) / (0.0001 * 0.0001 * x1 * x1);
		res[0][1] = (f(1.0001 * x1, 1.0001 * x2) - 2 * f(1.0001 * x1, x2) + f(x1, x2)) / (0.0001 * 0.0001 * x1 * x2);
		res[1][0] = (f(1.0001 * x1, 1.0001 * x2) - 2 * f(x1, 1.0001 * x2) + f(x1, x2)) / (0.0001 * 0.0001 * x1 * x2);
		res[1][1] = (f(x1, 1.0002 * x2) - 2 * f(x1, 1.0001 * x2) + f(x1, x2)) / (0.0001 * 0.0001 * x2 * x2);
		return res;
	}*/
	// Hessian matrix of f(x)
	public static double[][] hessianf(double x1, double x2) {
		double[][] res = new double[2][2];
		res[0][0] = (fd(1.0001 * x1, x2)[0] - fd(x1, x2)[0]) / (0.0001 * x1);
		res[0][1] = (fd(x1, 1.0001 * x2)[0] - fd(x1, x2)[0]) / (0.0001 * x2);
		res[1][0] =  (fd(1.0001 * x1, x2)[1] - fd(x1, x2)[1]) / (0.0001 * x1);;
		res[1][1] = (fd(x1, 1.0001 * x2)[1] - fd(x1, x2)[1]) / (0.0001 * x2);
		return res;
	}
	// derivative of f(x)
	public static double[] fd(double x1, double x2) {
		double[] res = new double[2];
		res[0] = 40 * x1 * (x1 * x1 - 1);
		res[1] = 20 * (x2 - 1);
		return res;
	}
	
    //fd(t)
	public static double[] fdt(double t, double x1, double x2, double deltax1, double deltax2) {
		return fd(x1 + t * deltax1, x2 + t * deltax2);
	}
	
	// derivative of fd(t)
	public static double[] derfdt(double t, double x1, double x2, double deltax1, double deltax2) {
		double[] res = new double[2];
		res[0] = deltax1 * (120 * (x1 - deltax1 * t) * (x1 - deltax1 * t) - 40);
		res[1] = 20 * deltax2;
		return res;
	}
	
	// use bisection to search for the best t, as the x2 part of the derivative of fd(t) is a constant to t,
	// we only consider x1 part.
	public static double bisection(double a, double b, double x1, double x2, double deltax1, double deltax2) {
		if(Math.abs(b - a) < Math.pow(10, -3)) {
			return (a + b) / 2;
		}
		double c = (a + b) / 2;
		if(derfdt(c, x1, x2, deltax1, deltax2)[0] > 0) {
			return bisection(Math.min(a, b), c, x1, x2, deltax1, deltax2);
		}
		else if(derfdt(c, x1, x2, deltax1, deltax2)[0] < 0) {
			return bisection(c, Math.max(a, b), x1, x2, deltax1, deltax2);
		}
		else {
			return c;
		}
	}
}
