 import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Polynomial{
	
	//field
	double[] nonZeroCoeff;
	int[] exponents;
	
	//constructors
	public Polynomial() {
		nonZeroCoeff = null;
		exponents = null;
	}
	
	public Polynomial(double[] coeff, int[] exp) {
		
		// Note: cannot assume input is sorted
			
		nonZeroCoeff = new double[coeff.length];
		exponents = new int[exp.length];
		
		for(int i = 0; i < coeff.length; i++) {
			nonZeroCoeff[i] = coeff[i];
			exponents[i] = exp[i];
		}
	}
	
	public Polynomial(File f) {
		
		Scanner input;	
		try {
			input = new Scanner(f);
			String[] splitted = input.nextLine().split("(?=[+-])"); // (?=[+-]) allows for splitting at the +- signs while preserving them
			nonZeroCoeff = new double[splitted.length];
			exponents = new int[splitted.length];

			for(int i = 0; i < splitted.length; i++) {
				
				String part = splitted[i];
				
				if(part.contains("x")) {
					String[] splitMore = part.split("x");
					nonZeroCoeff[i] = Double.parseDouble(splitMore[0]);
					exponents[i] = Integer.parseInt(splitMore[1]);					
				}
				else { // the constant term
					nonZeroCoeff[i] = Double.parseDouble(part);
					exponents[i] = 0;		
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}	
	}
	

	//methods
	public Polynomial add(Polynomial p) {
		
		int totalLen = this.exponents.length + p.exponents.length;
		double[] coeff = new double[totalLen];
		int[] exp = new int[totalLen];
		
		
		for(int i = 0; i < this.exponents.length; i++) {
			coeff[i] = this.nonZeroCoeff[i];
			exp[i] = this.exponents[i];
		}
		
		int index = this.exponents.length;
		
		for(int i = 0; i < p.exponents.length; i++) {
			
			boolean added = false;
			for(int j = 0; j < this.exponents.length; j++) {
				
				if(this.exponents[j] == p.exponents[i]) {
					coeff[j] += p.nonZeroCoeff[i];
					added = true;
					break;
				}
			}
			if(!added) {
				coeff[index] = p.nonZeroCoeff[i];
				exp[index] = p.exponents[i];
				index++;
			}		
		}
		
		
		//determine where zero coefficients occur
		int countZero = 0;
		for(int i = 0; i < index; i++) {
			if(coeff[i] == 0.0) {
				countZero++;
			}
		}
		// create new (possibly) smaller array without zero coefficients & create exponents array
		double[] newCoeff = new double[index - countZero];
		int[] newExp = new int[index - countZero];
		
		if(newCoeff.length == 0) {
			return new Polynomial();
		}
			
		int newIndex = 0;
		
		for(int i = 0; i < index; i++) {
			if(coeff[i] != 0.0) {
				newCoeff[newIndex] = coeff[i];
				newExp[newIndex] = exp[i];
				newIndex++;
			}
		}
		
		// return resulting polynomial whose coefficients and exponent arrays are sorted
		return new Polynomial(newCoeff, newExp);
	}
	
	public double evaluate(double x) {	
		
		double result = 0.0;	
		for(int i = 0; i < nonZeroCoeff.length; i++) {
			result += nonZeroCoeff[i] * Math.pow(x, exponents[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double root) {	
		
		if(evaluate(root) == 0) return true;
		return false;
	}
	
	public Polynomial multiply(Polynomial p) {
		
		double[] coeff = new double[this.exponents.length];
		int[] exp = new int[this.exponents.length]; 
		
		// multiply first term of p with all terms of calling polynomial, store this in new polynomial
		for(int i = 0; i < this.exponents.length; i++) {
			coeff[i] = this.nonZeroCoeff[i] * p.nonZeroCoeff[0];
			exp[i] = this.exponents[i] + p.exponents[0];
		}
		Polynomial poly = new Polynomial(coeff, exp);
		
		// multiply each of the rest of the terms of p with all terms of calling polynomial
		for(int i = 1; i < p.exponents.length; i++) {
			for(int j = 0; j < this.exponents.length; j++) {
				coeff[j] = this.nonZeroCoeff[j] * p.nonZeroCoeff[i];
				exp[j] = this.exponents[j] + p.exponents[i];
			}
			poly = poly.add(new Polynomial(coeff, exp));
		}	
		return poly;
	}
	
	public void saveToFile(String fileName) {
		
		String polyStr = "";
		
		for(int i = 0; i < exponents.length; i++) {
			
			String coeff = String.valueOf(nonZeroCoeff[i]);			
			if(nonZeroCoeff[i] > 0.0) {
				coeff = "+" + coeff;
			}
			if(exponents[i] != 0) {
				polyStr += coeff + "x" + String.valueOf(exponents[i]);
			}
			else {
				polyStr += coeff;
			}
		}
		
		if(polyStr.charAt(0) == '+') {
			polyStr = polyStr.substring(1, polyStr.length());
		}
		
		try {
			PrintStream output = new PrintStream(fileName);
			output.append(polyStr);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
	}
	
}