package hp_in_note05;

public class Descent {
	public static void main(String[] arg) {
		// set the initial guess to 1
		double upperbound = 50;
		double x1 = 0.5;
		double x2 = 0.5;
		double deltax1 = -1 * fd1(x1) / f2d1(x1);   // deltax1
		double deltax2 = -1 * fd2(x2) / f2d2(x2);
		int iter = 0;    // Number of iteration
		System.out.println("while x0 = (" + x1 +", " + x2 + ")");
		while(Math.sqrt(deltax1 * deltax1 + deltax2 * deltax2) / Math.sqrt(x1 * x1 + x2 * x2) >= Math.pow(10, -7)) {
			deltax1 = -1 * fd1(x1) / f2d1(x1); 
			deltax2 = -1 * fd2(x2) / f2d2(x2); 
			double t = bisection(1, upperbound, x1, deltax1);
			if(Math.abs(fdt1(t, x1, deltax1)) <= Math.max(Math.abs(fdt1(1, x1, deltax1)), fdt1(upperbound, x1, deltax1))) {
				if(Math.abs(fdt1(1, x1, deltax1)) < Math.abs(fdt1(upperbound, x1, deltax1))) {
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
			x1 += deltax1;
			x2 += deltax2;
			iter++;
		}
		// set the initial guess to 10

	}
	
	// f(x)
	public static double f(double x1, double x2) {
		return (3 * x1 * x1 + x2 - 4) * (3 * x1 * x1 + x2 - 4) + (x1 * x1 - 3 * x2 + 2) * (x1 * x1 - 3 * x2 + 2);
		}
	// derivative of f(x1)
	public static double fd1(double x1) {
		return 40 * x1 * (x1 * x1 - 1);
		}
	// derivative of f(x2)
	public static double fd2(double x2) {
		return 20 * (x2 - 1);
		}
	
	//derivative of fd(x1)
	public static double f2d1(double x1) {
		return 120 * x1 * x1 - 40;
	}
	//derivative of fd(x2)
	public static double f2d2(double x2) {
		return 20;
	}

	// fd(t) for x1
	public static double fdt1(double t, double x, double deltax) {
		return fd1(x + t * deltax);
	}	
	
	// derivative of fd(t) for x1
	public static double ftd1(double t, double x, double deltax) {
		return deltax * fd1(x + t * deltax);
	}
	
	
	// use bisection to search for the best t for x1
	public static double bisection(double a, double b, double x, double deltax) {
		if(Math.abs(b - a) < Math.pow(10, -3)) {
			return (a + b) / 2;
		}
		double c = (a + b) / 2;
		if(ftd1(c, x, deltax) > 0) {
			return bisection(Math.min(a, b), c, x, deltax);
		}
		else if(ftd1(c, x, deltax) < 0) {
			return bisection(c, Math.max(a, b), x, deltax);
		}
		else {
			return c;
		}
	}
}
