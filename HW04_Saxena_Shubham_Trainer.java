
import java.util.*;
import java.io.*;

/**
 * 
 * @author Shubham Saxena
 *
 */

public class HW04_Saxena_Shubham_Trainer {
	double firstGini, secondGini, thirdGini, firstThres, secondThres, thirdThres;
	String first= "d.a"; String second = "d.b"; String third = "d.c";
	public static void main(String[] args) {
		File f = new File(args[0]);
		try {
			ArrayList<Data2> tup = new ArrayList<Data2>();
			Scanner sc = new Scanner(f);
			sc.nextLine();
			while (sc.hasNextLine()) {
				String row = sc.nextLine();
				String[] arr = row.split(",");
				double[] arr2 = new double[4];
				arr2[0] = Double.parseDouble(arr[0]);
				arr2[1] = Double.parseDouble(arr[1]);
				arr2[2] = Double.parseDouble(arr[2]);
				arr2[3] = Double.parseDouble(arr[3]);
				Data2 d = new Data2(arr2[0], arr2[1], arr2[2], (int) arr2[3]);
				tup.add(d);
				// System.out.println(d);
			}
			// System.out.println(tup);
			HW04_Saxena_Shubham_Trainer hw4 = new HW04_Saxena_Shubham_Trainer();
			hw4.whichClass(tup);
			hw4.writeCode();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found.");
		}
	}

	
	
	/**
	 * takes in dataset to build the model. The variables to be passed to the generated program are set here. 
	 * @param dList - dataset
	 */
	void whichClass(ArrayList<Data2> dList) {
		ArrayList<AttributeClassPair> one = new ArrayList<AttributeClassPair>();
		ArrayList<AttributeClassPair> two = new ArrayList<AttributeClassPair>();
		ArrayList<AttributeClassPair> three = new ArrayList<AttributeClassPair>();
		
		//splitting the attributes into lists with associated class values. 
		for (Data2 dObj : dList) {
			one.add(new AttributeClassPair(dObj.a, (int) dObj.d));
			two.add(new AttributeClassPair(dObj.b, (int) dObj.d));
			three.add(new AttributeClassPair(dObj.c, (int) dObj.d));
		}
		
		System.out.println(one);
		System.out.println(two);
		System.out.println(three);
		
		
		//return values from the gini Index function. 
		double[] oneGini = giniIndex(one);
		double[] twoGini = giniIndex(two);
		double[] threeGini = giniIndex(three);
		
		//this block till the end of the if else statements is to tell the generated program the values and order of the columns.
		firstThres = oneGini[0];
		secondThres = twoGini[0];
		thirdThres = threeGini[0];
		firstGini = oneGini[1];
		secondGini = twoGini[1];
		thirdGini = threeGini[1];
		
		if(secondGini <= firstGini && secondGini <= thirdGini ){
			first = "d.b";
			second = "d.a";
			third = "d.c";
			if(firstGini <= thirdGini){
				second = "d.c";
				third = "d.a";
			}
		} else if(thirdGini <= firstGini && thirdGini <= secondGini ){
			first = "d.c";
			second = "d.a";
			third = "d.b";
			if(secondGini <= firstGini){
				second = "d.b";
				third = "d.a";
			}
		} else{
			if(thirdGini <= secondGini){
				first = "d.a";
				second = "d.c";
				third = "d.b";
			}
		}
		
		System.out.println("col 1: weighted gini: " + oneGini[0] + " weighted gini: " + oneGini[1]);
		System.out.println("col 2: weighted gini: " + twoGini[0] + " weighted gini: " + twoGini[1]);
		System.out.println("col 3: weighted gini: " + threeGini[0] + " weighted gini: " + threeGini[1]);

	}

	/**
	 * calculates the gini index for one attribute column at a time
	 * @param attributeClassPair
	 * @return double array of the minimum wtginiIndex and the corresponding threshhold.
	 */
	double[] giniIndex(ArrayList<AttributeClassPair> attributeClassPair) {
		Collections.sort(attributeClassPair);
		double thresh = 0;
		// System.out.println(attributeClassPair);
		
		//for getting unique threshold values. 
		Set<Double> quant = new TreeSet<Double>();
		double giniWt = 1;
		double leastGiniWt = 1;
		// adding into set for quantized values.
		for (AttributeClassPair atcp : attributeClassPair) {
			quant.add(atcp.att);
		}

		// System.out.println(quant);
		
		//for every threshold 
		for (double i : quant) {
			int lessYes = 0, lessNo = 0;
			int moreYes = 0, moreNo = 0;
			int total = 0;
			
			for (AttributeClassPair atcp : attributeClassPair) {
				if (atcp.att <= i && atcp.classV == 1) {
					lessYes++;
				} else if (atcp.att <= i && atcp.classV == 0) {
					lessNo++;
				} else if (atcp.att > i && atcp.classV == 1) {
					moreYes++;
				} else if (atcp.att > i && atcp.classV == 0) {
					moreNo++;
				}
				total++;
			}
			 //System.out.println("ly ln my mn: " + lessYes + " " + lessNo + " "
			 //+ moreYes + " " + moreNo + total);
			// Weighted gini index
			try {
				double a = ((double) (lessYes + lessNo) / (double) total)
						* (1.0 - Math.pow(((double) lessYes / (double) (lessYes + lessNo)), 2)
								- Math.pow(((double) lessNo / (double) (lessYes + lessNo)), 2));
				double b = ((double) (moreYes + moreNo) / (double) total)
						* ((1.0 - Math.pow(((double) moreYes / (double) (moreYes + moreNo)), 2)
								- Math.pow(((double) moreNo / (double) (moreYes + moreNo)), 2)));
				giniWt = a + b;
				// System.out.println("gini" + giniWt);
				if (giniWt < leastGiniWt) {
					leastGiniWt = giniWt;
					 System.out.println("mara switch " + leastGiniWt + "thresh:" + thresh);
					thresh = i;
				}

			} catch (ArithmeticException e) {
				System.out.println("Divide by zero Handled");
				continue;
			}
		}
		double threshGini[] = { thresh, leastGiniWt };
		return threshGini;
	}

	/**
	 * generates the classifier .java file
	 */
	void writeCode() {
		PrintWriter writer;
		try {
			
			//this is probably not the best way to generate a new program. But life is hard
			writer = new PrintWriter("HW04_Saxena_Shubham_Classifier.java", "UTF-8");
			String prog = "import java.io.File; "+"\n"+
					"import java.io.FileNotFoundException; "+"\n"+
					"import java.util.ArrayList;"+"\n"+
					"import java.util.Scanner;"+"\n"+
					"import java.io.PrintWriter;"+"\n"+
					""+"\n"+
					"/**"+"\n"+
					" * "+"\n"+
					 "* @author Shubham Saxena"+"\n"+
					" *"+"\n"+
					" */"+"\n"+
					""+"\n"+
					"class HW04_Saxena_Shubham_Classifier {"+"\n"+
						"static String first = \""+first+"\", second = \""+second+"\", third = \""+third+"\";"+"\n"+
						""+"\n"+
						"public static void main(String args[]) {"+"\n"+
							"try {"+"\n"+
								"File f = new File(args[0]);"+"\n"+
								"ArrayList<Data2> tup = new ArrayList<Data2>();"+"\n"+
								"Scanner sc = new Scanner(f);"+"\n"+
								"sc.nextLine();"+"\n"+
								"while (sc.hasNextLine()) {"+"\n"+
									"String row = sc.nextLine();"+"\n"+
									"String[] arr = row.split(\",\");"+"\n"+
									"double[] arr2 = new double[3];"+"\n"+
									""+"\n"+
									"arr2[0] = Double.parseDouble(arr[0]);"+"\n"+
									"arr2[1] = Double.parseDouble(arr[1]);"+"\n"+
									"arr2[2] = Double.parseDouble(arr[2]);"+"\n"+
									""+"\n"+
									"Data2 d = new Data2(arr2[0], arr2[1], arr2[2]);"+"\n"+
									"tup.add(d);"+"\n"+
									"System.out.println(d);"+"\n"+
								"}"+"\n"+
								"// System.out.println(tup);"+"\n"+
								"HW04_Saxena_Shubham_Classifier hw4 = new HW04_Saxena_Shubham_Classifier();"+"\n"+
								"hw4.classify(tup);"+"\n"+
								"sc.close();"+"\n"+
							"} catch (FileNotFoundException e) {"+"\n"+
								"e.printStackTrace();"+"\n"+
								"System.out.println(\"File not found.\");"+"\n"+
							"}"+"\n"+
						"}"+"\n"+
						""+"\n"+
						"void classify(ArrayList<Data2> test) {"+"\n"+
							"int classV = 9999;"+"\n"+
							"PrintWriter p;"+"\n"+
							"try {"+"\n"+
							"p = new PrintWriter(\"HW04_Saxena_Shubham_MyClassifications.txt\");"+"\n"+
							"for (Data2 d : test) {"+"\n"+
								"if ("+ first+" >= "+firstThres+") { "+"\n"+
									"if ("+ second+">= "+secondThres+") { "+"\n"+
										"classV = 0;"+"\n"+
									"} else {"+"\n"+
										"classV = 1;"+"\n"+
									"}"+"\n"+
								"} else {"+"\n"+
									"if ("+ third+" >= "+thirdThres+") { "+"\n"+
										"classV = 0;"+"\n"+
									"} else {"+"\n"+
										"classV = 1;"+"\n"+
									"}"+"\n"+
								"}"+"\n"+
								"d.d = classV;"+"\n"+
								"System.out.println(\"Row:\" + d + \" Class Value = \" + classV);"+"\n"+
								
						"p.write(\"Row:\" + d + \" Class Value = \" + classV+\"\\n\");"+"\n"+
						"p.flush();"+"\n"+
						"}"+"\n"+
						"p.close();"+"\n"+
						"} catch (FileNotFoundException e) {"+"\n"+
						"e.printStackTrace();"+"\n"+
						"}"+"\n"+
					"}"+"\n"+
				"}"+"\n";
			writer.write(prog);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}

