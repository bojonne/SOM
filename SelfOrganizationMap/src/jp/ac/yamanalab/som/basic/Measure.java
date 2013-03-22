package jp.ac.yamanalab.som.basic;

public interface Measure<T extends DataVector> {
	public double getDistance(T lhs, T rhs);
}
