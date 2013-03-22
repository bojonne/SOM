package jp.ac.yamanalab.som.basic;

public interface LearningFunction {
	public double getLearningCoefficient(int time, double dist);
}
