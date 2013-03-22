package jp.ac.yamanalab.som.basic.hex;

import java.lang.reflect.Array;

import jp.ac.yamanalab.som.basic.Coordinate;
import jp.ac.yamanalab.som.basic.DataVector;
import jp.ac.yamanalab.som.basic.LearningFunction;
import jp.ac.yamanalab.som.basic.Measure;

public class HexagonalLatticeMap<T extends DataVector> {
	private final int WIDTH;
	private final int HEIGHT;
	private final T[][] map;
	private final Measure<T> measure;
	private final LearningFunction lfunc;
	
	public enum HexDir {
		Right,
		RightUp,
		LeftUp,
		Left,
		LeftDown,
		RightDown
	}
	private final int[] dx = new int[] {  1,  0, -1, -1,  0,  1};
	private final int[] dy = new int[] {  0,  1,  1,  0, -1, -1};
	
	@SuppressWarnings("unchecked")
	public HexagonalLatticeMap(Class<T> clazz, int width, int height, Measure<T> measure, LearningFunction lfunc) throws InstantiationException, IllegalAccessException {
		this.WIDTH = width;
		this.HEIGHT = height;
		map = (T[][]) Array.newInstance(clazz, HEIGHT, WIDTH + HEIGHT / 2);
		for (int h=0;h<HEIGHT;++h) {
			int offset = (WIDTH - h -1) / 2;
			for (int w=offset;w<WIDTH+offset;++w) {
				map[h][w] = clazz.newInstance();
				map[h][w].randomize();
			}
		}
		this.measure = measure;
		this.lfunc = lfunc;
	}
	
	public HexagonalLatticeMap(T[][] map, int width, int height, Measure<T> measure, LearningFunction lfunc) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.map = map;
		this.measure = measure;
		this.lfunc = lfunc;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public T[][] getMap() {
		return map;
	}
	
	/**
	 * 
	 * @param time
	 * @param inputData
	 * @param threshold
	 * @return 'true' means learning was finished.
	 */
	public boolean learn(int time, T inputData, double threshold) {
		Coordinate bmu = getBMU(inputData);
		final int DMAX = Math.max(WIDTH, HEIGHT);
		double totalDelta = 0.0d;
		for (int dist=0;dist<=DMAX;++dist) {
			double lc = lfunc.getLearningCoefficient(time, dist);
			
			if (dist==0) {
				totalDelta += updateUnit(map[bmu.y][bmu.x], inputData, lc);
				continue;
			}
			
			Coordinate cur = new Coordinate(bmu);
			for (int i=0;i<dist;++i)
				go(cur, HexDir.LeftDown);
			
			for (HexDir dir : HexDir.values()) {
				for (int i=0;i<dist;++i) {
					go(cur, dir);
					if (isValidCoordinate(cur))
						totalDelta += updateUnit(map[cur.y][cur.x], inputData, lc);
				}
			}
		}
		return totalDelta < threshold;
	}
	
	private void go(Coordinate c, HexDir dir) {
		c.x += dx[dir.ordinal()];
		c.y += dy[dir.ordinal()];
	}
	
	private boolean isValidCoordinate(Coordinate c) {
		int offset = (HEIGHT - c.y - 1) / 2;
		if (c.x < offset || c.x >= offset + WIDTH)
			return false;
		if (c.y < 0 || c.y >= HEIGHT)
			return false;
		return true;
	}
	
	private double updateUnit(T target, T inputData, double lc) {
		double totalDelta = 0.0d;
		for (int i=0;i<target.getDimension();++i) {
			double delta = lc * (inputData.get(i) - target.get(i));
			totalDelta += Math.abs(delta);
			target.set(i, target.get(i) +  delta);
		}
		return totalDelta;
	}
	
	/**
	 * Search Best Matching Unit(BMU)
	 * @return BMU
	 */
	public Coordinate getBMU(T inputData) {
		double minDist = Double.MAX_VALUE;
		Coordinate bmu = null;
		for (int h=0;h<HEIGHT;++h) {
			int offset = (HEIGHT-h-1)/2;
			for (int w=0+offset;w<WIDTH+offset;++w) {
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
