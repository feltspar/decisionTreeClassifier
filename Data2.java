
/**
 * 
 * @author Shubham Saxena
 *
 */
public class Data2 {

	double a, b, c,d ;
	/**
	 * for training data with the class value given
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	Data2(double a, double b, double c, int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	/**
	 * for test data.
	 * @param a
	 * @param b
	 * @param c
	 */
	Data2(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	/**
	 * for printing 
	 */
	public String toString(){
		return "a:" +a+ " b:"+b+" c:"+c + " d:"+d;
	}
}
