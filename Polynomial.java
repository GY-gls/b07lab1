public class Polynomial{
	
	//field
	double[] coefficients;
	
	//constructors
	public Polynomial() {
		coefficients = new double[0];
	}
	
	public Polynomial(double[] input) {
		
		coefficients = new double[input.length];
		
		for(int i = 0; i < input.length; i++) {
			coefficients[i] = input[i];
		}
	}
	
	//methods
	public Polynomial add(Polynomial p) {
		
		// determining which polynomial has higher degree and set up corresponding coefficient array
		double[] coeff;
		
		if(p.coefficients.length >= this.coefficients.length) {
			coeff = new double[p.coefficients.length];
		}
		else {
			coeff = new double[this.coefficients.length];
		}
		
		// fill in array
		for(int i = 0; i < p.coefficients.length; i++) {
			coeff[i] += p.coefficients[i];
		}
		for(int j = 0; j < this.coefficients.length; j++) {
			coeff[j] += this.coefficients[j];
		}
		
		// return resulting polynomial
		return new Polynomial(coeff);
	}
	
	public double evaluate(double x) {
		
		double result = 0.0;
		
		for(int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(x, i);
		}
		
		return result;
	}
	
	public boolean hasRoot(double root) {
		
		if(evaluate(root) == 0) return true;
		
		return false;
	}
}