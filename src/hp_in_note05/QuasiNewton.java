package hp_in_note05;

public class QuasiNewton {
	public static void main(String[] arg) {
		// set the initial guess to 1
		double upperbound = 5;
		double x = 1;
		double deltax = -1 * f(x) / deltaf(x);   // deltax
		int iter = 0;    // Number of iteration
		System.out.println("while x0 = 1");
		while(Math.abs(deltax) / Math.abs(x) >= Math.pow(10, -9)) {
			deltax = -1 * f(x) / deltaf(x); 
			double t = bisection(1, upperbound, x, deltax);
			if(Math.abs(ft(t, x, deltax)) <= Math.max(Math.abs(ft(1, x, deltax)), ft(100, x, deltax))) {
				if(Math.abs(ft(1, x, deltax)) < Math.abs(ft(upperbound, x, deltax))) {
					t = 1;
				}
				else {
					t = upperbound;
				}
			}
			deltax *= t;
			System.out.println("x" + iter + " = " + x);
			System.out.println("scalar t = " + t);
			System.out.println("deltax" + iter + " = " + deltax);
			System.out.println("f(x" + iter + ") = " + f(x));
			x += deltax;
			iter++;
		}
	}
	
	// f(x)
	public static double f(double x) {
		return Math.exp(100 * x) - 1;
	}
	// Jacobian of f(x)
	public static double deltaf(double x) {
		return (f(1.0001 * x) - f(x))/ (0.0001 * x) ;
	}
	// derivative of f(x)
	public static double fd(double x) {
		return 100 * Math.exp(100 * x);
	}
	// f(t)
	public static double ft(double t, double x, double deltax) {
		return f(x + t * deltax);
	}
	// derivative of f(t)
	public static double ftd(double t, double x, double deltax) {
		return deltax * fd(x + t * deltax);
	}
	// use bisection to search for the best t
	public static double bisection(double a, double b, double x, double deltax) {
		if(Math.abs(b - a) < Math.pow(10, -3)) {
			return (a + b) / 2;
		}
		double c = (a + b) / 2;
		if(ftd(c, x, deltax) > 0) {
			return bisection(Math.min(a, b), c, x, deltax);
		}
		else if(ftd(c, x, deltax) < 0) {
			return bisection(c, Math.max(a, b), x, deltax);
		}
		else {
			return c;
		}
	}
}
