package jp.ac.yamanalab.som.basic.circle;

import java.lang.reflect.Array;

import jp.ac.yamanalab.som.basic.DataVector;
import jp.ac.yamanalab.som.basic.LearningFunction;
import jp.ac.yamanalab.som.basic.Measure;

public class CircleMap<T extends DataVector> {
	private T[] map;
	private final int N;
	private final Measure<T> measure;
	private final LearningFunction lfunc;
	
	@SuppressWarnings("unchecked")
	public CircleMap(Class<T> clazz, int num, Measure<T> measure, LearningFunction lfunc) throws InstantiationException, IllegalAccessException {
		this.N = num;
		map = (T[]) Array.newInstance(clazz, num);
		for (int i=0;i<N;++i) {
			map[i] = clazz.newInstance();
			map[i].randomize();
		}
		this.measure = measure;
		this.lfunc = lfunc;
	}
	
	public CircleMap(T[] map, Measure<T> measure, LearningFunction lfunc) {
		this.map = map;
		this.N = map.length;
		this.measure = measure;
		this.lfunc = lfunc;
	}
	
	public DataVector[] getMap() {
		return map;
	}
	
	/**
	 * 
	 * @param time
	 * @param inputData
	 * @param threshold
	 * @return 'true' means learning was finished
	 */
	public boolean learn(int time, T inputData, double threshold) {
		int bmu = getBMU(inputData);
		final int DMAX = N;
		double totalDelta = 0.0d;
		for (int dist=0;dist<=DMAX;++dist) {
			double lc = lfunc.getLearningCoefficient(time, dist);
			totalDelta += updateUnit(map[(bmu + dist) % N], inputData, lc);
			totalDelta += updateUnit(map[(N + bmu - dist) % N], inputData, lc);
		}
		return totalDelta < threshold;
	}
	
	/**
	 * 
	 * @param target
	 * @param inputData
	 * @param lc Learning Coefficient
	 */
	private double updateUnit(T target, T inputData, double lc) {
		double totalDelta = 0.0d;
		for (int i=0;i<target.getDimension();++i) {
			double delta = lc * (inputData.get(i) - target.get(i));
			totalDelta += Math.abs(delta);
			target.set(i, target.get(i) + delta);
		}
		return totalDelta;
	}
	
	/**
	 * Search Best Matching Unit(BMU)
	 * @return BMU
	 */
	private int getBMU(T inputData) {
		double minDist = Double.MAX_VALUE;
		int bmu = 0;
		for (int i=0;i<N;++i) {
			double dist = measure.getDistance(inputData, map[i]);
			if (dist < minDist) {
				bmu = i;
				minDist = dist;
			}
		}
		return bmu;
	}
}
