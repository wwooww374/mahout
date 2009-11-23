/*
Copyright � 1999 CERN - European Organization for Nuclear Research.
Permission to use, copy, modify, distribute and sell this software and its documentation for any purpose 
is hereby granted without fee, provided that the above copyright notice appear in all copies and 
that both that copyright notice and this permission notice appear in supporting documentation. 
CERN makes no representations about the suitability of this software for any purpose. 
It is provided "as is" without expressed or implied warranty.
*/
package org.apache.mahout.jet.random;

import org.apache.mahout.jet.random.engine.RandomEngine;
/**
 * Benchmarks random number generation from various distributions as well as PDF and CDF lookups.
 *
 * @author wolfgang.hoschek@cern.ch
 * @version 1.0, 09/24/99
 */
/** 
 * @deprecated until unit tests are in place.  Until this time, this class/interface is unsupported.
 */
@Deprecated
public class Benchmark extends org.apache.mahout.colt.PersistentObject {
	protected RandomEngine randomGenerator;
/**
 * Makes this class non instantiable, but still let's others inherit from it.
 */
protected Benchmark() {
	throw new RuntimeException("Non instantiable");
}
/**
 * Prints the first <tt>size</tt> random numbers generated by the distribution.
 */
public static void demo1() {
// Gamma distribution

// define distribution parameters
double mean = 5;
double variance = 1.5;
double alpha = mean*mean / variance; 
double lambda = 1 / (variance / mean); 

// for tests and debugging use a random engine with CONSTANT seed --> deterministic and reproducible results
org.apache.mahout.jet.random.engine.RandomEngine engine = new org.apache.mahout.jet.random.engine.MersenneTwister();

// your favourite distribution goes here
org.apache.mahout.jet.random.AbstractDistribution dist = new org.apache.mahout.jet.random.Gamma(alpha,lambda,engine);

// collect random numbers and print statistics
int size = 100000;
org.apache.mahout.colt.list.DoubleArrayList numbers = new org.apache.mahout.colt.list.DoubleArrayList(size);
for (int i=0; i < size; i++) numbers.add(dist.nextDouble());

//hep.aida.bin.DynamicBin1D bin = new hep.aida.bin.DynamicBin1D();
//bin.addAllOf(numbers);
//System.out.println(bin);
}
/**
 * Benchmarks all subclasses
 * @param args[0] the number of random numbers to be generated per subclass.
 * @param args[1] <tt>true</tt> prints each generated number, <tt>false</tt> does not print generated numbers (use this setting for benchmarking).
 */
public static void main(String args[]) {
	int size = Integer.parseInt(args[0]);
	boolean print = new Boolean(args[1]).booleanValue();
	double mean = new Double(args[2]).doubleValue();
	String generatorName = args[3];
	random(size,print,mean,generatorName);
}
/**
 * Benchmarks all subclasses
 * @param size the number of random numbers to be generated per subclass.
 * @param print <tt>true</tt> prints each generated number, <tt>false</tt> does not print generated numbers (use this setting for benchmarking).
 * @param mean the mean for distributions that require a mean.
 */
public static void random(int size, boolean print, double mean, String generatorName) {
	System.out.println("Generating "+size+" random numbers per distribution...\n");

	//int large = 100000000;
	int largeVariance = 100;
	RandomEngine gen; // = new MersenneTwister();
	try {
		gen = (RandomEngine) Class.forName(generatorName).newInstance();
	} catch (Exception exc) {
		throw new InternalError(exc.getMessage());
	}

	/*
	randomInstance(size,print,new Zeta(10.0, 10.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Zeta(1.0, 1.0, (RandomEngine)gen.clone()));
	randomInstance(size,print,new Zeta(mean, mean, (RandomEngine)gen.clone()));
	randomInstance(size,print,new Zeta(mean, 1/mean, (RandomEngine)gen.clone()));
	//randomInstance(size,print,new Zeta(1/mean, mean, (RandomEngine)gen.clone()));
*/

	/*
	
	randomInstance(size,print,new Beta(10.0, 10.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Beta(1.0, 1.0, (RandomEngine)gen.clone()));
	randomInstance(size,print,new Beta(mean, mean, (RandomEngine)gen.clone()));
	randomInstance(size,print,new Beta(mean, 1/mean, (RandomEngine)gen.clone()));
	randomInstance(size,print,new Beta(1/mean, mean, (RandomEngine)gen.clone()));
	
	randomInstance(size,print,new Uniform((RandomEngine)gen.clone()));
	*/
	randomInstance(size,print,new Poisson(mean,(RandomEngine)gen.clone()));
	/*
	randomInstance(size,print,new PoissonSlow(mean,(RandomEngine)gen.clone()));
	
	randomInstance(size,print,new Poisson(3.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new PoissonSlow(3.0,(RandomEngine)gen.clone()));
	
	randomInstance(size,print,new Binomial(1,0.5,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Binomial(5,0.3,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Binomial((int)mean,0.999999999,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Binomial((int)mean,1.0/mean,(RandomEngine)gen.clone()));
	
	randomInstance(size,print,new Exponential(1.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Exponential(3.0,(RandomEngine)gen.clone()));
	
	randomInstance(size,print,new Normal(0.0,1.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Normal(3.0,1.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Normal(mean,largeVariance,(RandomEngine)gen.clone()));
	
	randomInstance(size,print,new BreitWigner(1.0, 0.2, Double.NEGATIVE_INFINITY, (RandomEngine)gen.clone()));
	randomInstance(size,print,new BreitWigner(1.0, 0.2, 1.0, (RandomEngine)gen.clone()));
	
	randomInstance(size,print,new BreitWignerMeanSquare(1.0, 0.2, Double.NEGATIVE_INFINITY, (RandomEngine)gen.clone()));	
	randomInstance(size,print,new BreitWignerMeanSquare(1.0, 0.2, 1.0, (RandomEngine)gen.clone()));
	
	randomInstance(size,print,new ChiSquare(1.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new ChiSquare(5.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new ChiSquare(mean,(RandomEngine)gen.clone()));
	
	randomInstance(size,print,new Gamma(0.2,1.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Gamma(1.0,1.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Gamma(3.0,0.5,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Gamma(mean,0.5,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Gamma(mean,1.0/mean,(RandomEngine)gen.clone()));
	randomInstance(size,print,new Gamma(mean,mean,(RandomEngine)gen.clone()));
	
	randomInstance(size,print,new StudentT(1.0,(RandomEngine)gen.clone()));
	randomInstance(size,print,new StudentT(2.5,(RandomEngine)gen.clone()));
	randomInstance(size,print,new StudentT(mean,(RandomEngine)gen.clone()));
	randomInstance(size,print,new StudentT(1.0/mean,(RandomEngine)gen.clone()));

	int probs = 10000;
	double[] pdf = new double[probs];
	for (int i=0; i<probs; i++) pdf[i]=i*i; // prepare f(x)=x^2 distrib.
	randomInstance(size,print,new Empirical(pdf,Empirical.NO_INTERPOLATION, (RandomEngine)gen.clone()));
	randomInstance(size,print,new Empirical(pdf,Empirical.LINEAR_INTERPOLATION, (RandomEngine)gen.clone()));
	*/
}
/**
 * generates <size> random numbers from <dist>
 */
public static void randomInstance(int size, boolean print, AbstractDistribution dist) {
	System.out.print("\n"+dist+" ...");
	org.apache.mahout.colt.Timer timer = new org.apache.mahout.colt.Timer().start();

	for (int i=size; --i >= 0;) {
		double rand = dist.nextDouble();
		if (print) {
			if ((size-i-1)%8 == 0) System.out.println();
			System.out.print((float)rand+", ");
		}
	}

	timer.stop();
	System.out.println("\n"+timer);
}
/**
 * Prints the first <tt>size</tt> random numbers generated by the distribution.
 */
public static void test(int size, AbstractDistribution distribution) {
	for (int j=0, i=size; --i>=0; j++) {
		System.out.print(" "+distribution.nextDouble());
		if (j%8==7) System.out.println();
	}
	System.out.println("\n\nGood bye.\n");
}
/**
 * Prints the first <tt>size</tt> random numbers generated by the distribution.
 *
public static void test2(int size, AbstractDistribution distribution) {
	hep.aida.bin.DynamicBin1D bin = new hep.aida.bin.DynamicBin1D();
	for (int j=0, i=size; --i>=0; j++) {
		bin.add(distribution.nextDouble());
	}
	System.out.println(bin);
	System.out.println("\n\nGood bye.\n");
}
**
 * Prints the first <tt>size</tt> random numbers generated by the distribution.
 *
public static void test2(int size, AbstractDistribution a, AbstractDistribution b) {
	hep.aida.bin.DynamicBin1D binA = new hep.aida.bin.DynamicBin1D();
	hep.aida.bin.DynamicBin1D binB = new hep.aida.bin.DynamicBin1D();
	for (int j=0, i=size; --i>=0; j++) {
		binA.add(a.nextDouble());
		binB.add(b.nextDouble());
	}
	//System.out.println(binA);
	//System.out.println(binB);
	//System.out.println(binA.compareWith(binB));

	System.out.println("\n\nBenchmarking frequencies...\n");
	IntArrayList freq = new IntArrayList();
	DoubleArrayList distinct = new DoubleArrayList();
	org.apache.mahout.colt.Timer timer = new org.apache.mahout.colt.Timer();
	timer.reset();
	timer.start();
	binA.frequencies(distinct,freq);
	timer.stop().display();
	//System.out.println(distinct);
	//System.out.println(freq);

	/*
	timer.reset();
	timer.start();
	binA.xfrequencies2(distinct,freq);
	timer.stop().display();
	//System.out.println(distinct);
	//System.out.println(freq);
	/
	
	/
	distinct.shuffle();
	timer.reset().start();
	distinct.sort();
	timer.stop().display();

	timer.reset().start();
	binA.frequencies(distinct,freq);
	timer.stop().display();
	//System.out.println(distinct);
	//System.out.println(freq);
	*


	
	System.out.println("\n\nGood bye.\n");
}
*/
}
