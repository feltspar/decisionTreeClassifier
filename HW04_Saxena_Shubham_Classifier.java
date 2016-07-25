import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * 
 * @author Shubham Saxena
 *
 */

class HW04_Saxena_Shubham_Classifier {
	static String first = "d.a", second = "d.b", third = "d.c";

	public static void main(String args[]) {
		try {
			//reading data
			File f = new File(args[0]);
			ArrayList<Data2> tup = new ArrayList<Data2>();
			Scanner sc = new Scanner(f);
			sc.nextLine();
			while (sc.hasNextLine()) {
				String row = sc.nextLine();
				String[] arr = row.split(",");
				double[] arr2 = new double[3];

				arr2[0] = Double.parseDouble(arr[0]);
				arr2[1] = Double.parseDouble(arr[1]);
				arr2[2] = Double.parseDouble(arr[2]);

				Data2 d = new Data2(arr2[0], arr2[1], arr2[2]);
				tup.add(d);
				System.out.println(d);
			}
			// System.out.println(tup);
			HW04_Saxena_Shubham_Classifier hw4 = new HW04_Saxena_Shubham_Classifier();
			hw4.classify(tup);
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found.");
		}
	}

	/**
	 * Template classifier that was given in the HW04 Question.
	 * @param test
	 */
	void classify(ArrayList<Data2> test) {
		
		int classV = 9999;
		PrintWriter p;
		try {
			p = new PrintWriter("HW04_Saxena_Shubham_MyClassifications.txt");
			for (Data2 d : test) {
				if (d.a >= 4.2) {
					if (d.b >= 3.3) {
						classV = 0;
					} else {
						classV = 1;
					}
				} else {
					if (d.c >= 2.0) {
						classV = 0;
					} else {
						classV = 1;
					}
				}
				d.d = classV;
				System.out.println("Row:" + d + " Class Value = " + classV);
				p.write("Row:" + d + " Class Value = " + classV + "\n");
				p.flush();
			}
			p.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
