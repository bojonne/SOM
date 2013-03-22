package jp.ac.yamanalab.som.basic;

public class SimpleLearingFunction implements LearningFunction {
	private final int defaultRadius;
	
	public SimpleLearingFunction(int defaultRadius) {
		this.defaultRadius = defaultRadius;
	}
	
	@Override
	public double getLearningCoefficient(int time, double dist) {
		return alpha(time) * Math.exp(- (dist * dist) / (2 * Math.pow(sigma(time),2)));
	}

	private double alpha(int time) {
		return 90d / (time + 100d);
//		return 0.9 * (1 - time / 10000.0);
	}
	
	private double sigma(int time) {
		return defaultRadius * Math.exp(- (double)time/100);
		//return 5 / Math.pow((double)time, 0.2);
	}
}
