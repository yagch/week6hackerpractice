package hp_in_note06;

public class MonteCarlo {
	public static void main(String[] arg) {
		for(int i = 1; i <= 6; i++) {
			System.out.println("When N = 10^" + i +", the pi value calculated is" + pi(Math.pow(10, i)) );
		}
	}
	public static double pi(double n) {
		int count = 0;
		for(int i = 0; i < n; i++) {
			double x = Math.random();
			double y = Math.random();
			if(x * x + y * y < 1) {
				count++;
			}
		}
		return 4 * count / n;
	}
}
