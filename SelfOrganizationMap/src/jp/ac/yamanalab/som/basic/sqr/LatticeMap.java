package jp.ac.yamanalab.som.basic.sqr;

import java.lang.reflect.Array;

import jp.ac.yamanalab.som.basic.Coordinate;
import jp.ac.yamanalab.som.basic.DataVector;
import jp.ac.yamanalab.som.basic.LearningFunction;
import jp.ac.yamanalab.som.basic.Measure;

public class LatticeMap<T extends DataVector> {
	private final int WIDTH;
	private final int HEIGHT;
	private final T[][] map;
	private final Measure<T> measure;
	private final LearningFunction lfunc;

	/**
	 * Constructs a new square lattice type SOM whose size is width * height.
	 * For the argument "Class<T> clazz", the class must has default constructor
	 * to create new instance of T. 
	 * @param clazz
	 * @param width
	 * @param height
	 * @param measure A function to measure distance between T
	 * @param lfunc A learning function.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public LatticeMap(Class<T> clazz, int width, int height,
			Measure<T> measure, LearningFunction lfunc)
			throws InstantiationException, IllegalAccessException {
		
		this.WIDTH = width;
		this.HEIGHT = height;
		map = (T[][]) Array.newInstance(clazz, height, width);
		for (int h=0;h<height;++h) {
			for (int w=0;w<width;++w) {
				map[h][w] = clazz.newInstance();
				map[h][w].randomize();
			}
		}
		this.measure = measure;
		this.lfunc = lfunc;
	}
	
	public LatticeMap(T[][] map, Measure<T> measure, LearningFunction lfunc) {
		this.WIDTH = map[0].length;
		this.HEIGHT = map.length;
		this.map = map;
		this.measure = measure;
		this.lfunc = lfunc;
	}
	
	/**
	 * Returns the array of SOM
	 * @return the array of SOM
	 */
	public T[][] getMap() {
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
		Coordinate bmu = getBMU(inputData);
		final int DMAX = Math.max(WIDTH, HEIGHT+1);
		double totalDelta = 0.0d;
		for (int dist=0;dist<=DMAX;++dist) {
			double lc = lfunc.getLearningCoefficient(time, dist);
			int sx = bmu.x - dist, ex = bmu.x + dist;
			int sy = bmu.y - dist, ey = bmu.y + dist;
			for (int h=Math.max(sy, 0);h<=Math.min(ey, HEIGHT-1);++h) {
				if (h==sy || h == ey) {
					for (int w=Math.max(sx, 0);w<=Math.min(ex, WIDTH-1);++w)
						totalDelta += updateUnit(map[h][w], inputData, lc);
				} else {
					if (sx >= 0)
						totalDelta += updateUnit(map[h][sx], inputData, lc);
					if (ex < WIDTH)
						totalDelta += updateUnit(map[h][ex], inputData, lc);
				}
			}
		}
		return totalDelta < threshold;
	}
	
	/**
	 * Updates the reference vector of target unit
	 * based on the learning coefficient
	 * @param target
	 * @param inputData
	 * @param lc Learning Coefficient
	 */
	private double updateUnit(T target, T inputData, double lc) {
		double totalDelta = 0.0d;
		for (int i=0;i<target.getDimension();++i) {
			double delta =lc * (inputData.get(i) - target.get(i));
			totalDelta += Math.abs(delta);
			target.set(i,  target.get(i) + delta);
		}
		return totalDelta;
	}
	
	/**
	 * Search Best Matching Unit(BMU)
	 * @return BMU
	 */
	private Coordinate getBMU(T inputData) {
		double minDist = Double.MAX_VALUE;
		Coordinate bmu = null;
		for (int h=0;h<HEIGHT;++h) {
			for (int w=0;w<WIDTH;++w) {
				double dist = measure.getDistance(inputData, map[h][w]);
				if (dist < minDist) {
					bmu = new Coordinate(w, h);
					minDist = dist;
				}
			}
		}
		return bmu;
	}
}
