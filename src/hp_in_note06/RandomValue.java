package hp_in_note06;
/*
 * As it is too hard to plot on java, I use standard deviation and average of the values for verification
 * As the randomness, the values floats a little bit around the ideal values.
 */
public class RandomValue{
	static int k = 10000;
	public static void main(String[] arg) {
		double[] v = new double[k];
		double add = 0;
		for(int i = 0; i < k; i++) {
			double u = Math.random();
			v[i] = -1 * Math.log(1 - u) / 0.2;
			add += v[i];
		}
		double avg = add / k;
		System.out.println("When number is " + k);
		System.out.println("The average is " + avg);    // Output the average of v, which should be 5
		add = 0;
		for(int i = 0; i < k; i++) {
			add += (v[i] - avg) * (v[i] - avg);
		}
		double standarD = Math.sqrt(add / k);
		System.out.println("Standard deviation is " + standarD);  // Output the standard deviation of v, which should be 5
	}
	public static double F(double v) {
		return 1 - Math.exp(-0.2 * v);
	}
	public static double p(double v) {
		return 0.2 * Math.exp(-0.2 * v);
	}
	
}
