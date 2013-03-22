package jp.ac.yamanalab.som.basic;

public class EuclideanMeasure<T extends DataVector> implements Measure<T> {

	@Override
	public double getDistance(T lhs, T rhs) {
		double max =0;
		for (int i=0;i<rhs.getDimension();++i) {
			if (Math.abs(lhs.get(i)) > max) max = Math.abs(lhs.get(i));
			if (Math.abs(rhs.get(i)) > max) max = Math.abs(rhs.get(i));
		}
		
		double sum = 0;
		for (int i=0;i<lhs.getDimension();++i) {
			sum += Math.pow(lhs.get(i)/max - rhs.get(i)/max, 2);
		}
		return Math.sqrt(sum) * max;
	}

}
