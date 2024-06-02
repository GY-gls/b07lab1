import java.io.File;

public class Driver {
	
	public static void main(String [] args) {
		
		Polynomial p0 = new Polynomial();
		printCoeffArray(p0.nonZeroCoeff);
		printExpArray(p0.exponents);
		
		// testing method add --------------------------------------------------------------------------------
		// testing two polynomials (unsorted arrays) whose coefficients do not sum to 0
		double[] c1 = {1, 2, 1, -7};
		int[] e1 = {1, 2, 0, 8};
		Polynomial p1 = new Polynomial(c1, e1);
		double[] c2 = {6, 3, 5, 4, 9};
		int[] e2 = {9, 0, 3, 1, 4};
		Polynomial p2 = new Polynomial(c2, e2);
		
		Polynomial sum1 = p1.add(p2);
		printCoeffArray(sum1.nonZeroCoeff);
		printExpArray(sum1.exponents);
		
		// testing two polynomials (unsorted arrays) in which some of the coefficients do sum to 0
		double[] c3 = {1, 2, -6, -4, 3};
		int[] e3 = {0, 5, 1, 10, 21};
		Polynomial p3 = new Polynomial(c3, e3);
		double[] c4 = {-9, 7, -1, -2};
		int[] e4 = {10, 12, 0, 5};
		Polynomial p4 = new Polynomial(c4, e4);

		Polynomial sum2 = p3.add(p4);
		printCoeffArray(sum2.nonZeroCoeff);
		printExpArray(sum2.exponents);

		// testing two polynomials with sorted arrays
		double[] c5 = {3};
		int[] e5 = {2};
		Polynomial p5 = new Polynomial(c5, e5);
		double[] c6 = {8, 5, 4, 9};
		int[] e6 = {1, 3, 4, 1999};
		Polynomial p6 = new Polynomial(c6, e6);

		Polynomial sum3 = p5.add(p6);
		printCoeffArray(sum3.nonZeroCoeff);
		printExpArray(sum3.exponents);

		// testing two polynomials that sum to zero
		double[] c7 = {1, 2, 3};
		int[] e7 = {0, 4, 8};
		Polynomial p7 = new Polynomial(c7, e7);
		double[] c8 = {-3, -1, -2};
		int[] e8 = {8, 0, 4};
		Polynomial p8 = new Polynomial(c8, e8);
		Polynomial sum4 = p7.add(p8);
		printCoeffArray(sum4.nonZeroCoeff);
		printExpArray(sum4.exponents);
		
		Polynomial abc = p7.add(sum4);
		printCoeffArray(abc.nonZeroCoeff);
		System.out.println(sum4.hasRoot(183745));
		Polynomial zero = sum4.multiply(p8);
		System.out.println(zero.exponents == null);

		// testing method evaluate --------------------------------------------------------------------------------
		System.out.println("sum2(0.1) = " + sum2.evaluate(0.1)); // arrays sorted
		System.out.println("p1(3.5) = " + p1.evaluate(3.5)); // arrays unsorted
		
		
		// testing method hasRoot --------------------------------------------------------------------------------
		if(sum1.hasRoot(1))
			System.out.println("1 is a root of sum1");
		else
			System.out.println("1 is not a root of sum1");
		double[] c = {1, -2, 1};
		int[] e = {2, 1, 0};
		Polynomial p = new Polynomial(c, e);
		if(p.hasRoot(1))
			System.out.println("1 is a root of p");
		else
			System.out.println("1 is not a root of p");
		
		// testing method multiply --------------------------------------------------------------------------------
		Polynomial product1 = p1.multiply(p3);
		printCoeffArray(product1.nonZeroCoeff);
		printExpArray(product1.exponents);
		
		Polynomial product2 = p4.multiply(p5);
		printCoeffArray(product2.nonZeroCoeff);
		printExpArray(product2.exponents);
		
		// testing method saveToFile --------------------------------------------------------------------------------
		p3.saveToFile("text.txt");
		sum4.saveToFile("text.txt");
		
		// testing polynomial constructor that accepts a file --------------------------------------------------------
		Polynomial poly = new Polynomial(new File("text.txt"));
		printCoeffArray(poly.nonZeroCoeff);
		printExpArray(poly.exponents);
	}
	
	// helper functions
	public static void printCoeffArray(double[] coeff) {
		if(coeff == null) return;
		for(int i = 0; i < coeff.length; i++) {
			System.out.print(coeff[i] + " ");
		}
		System.out.println();
	}
	public static void printExpArray(int[] exp) {
		if(exp == null) return;
		for(int i = 0; i < exp.length; i++) {
			System.out.print(exp[i] + " ");
		}
		System.out.println();
	}
}