package jp.ac.yamanalab.som.basic.demo;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import jp.ac.yamanalab.som.basic.Coordinate;
import jp.ac.yamanalab.som.basic.EuclideanMeasure;
import jp.ac.yamanalab.som.basic.LearningFunction;
import jp.ac.yamanalab.som.basic.SimpleLearingFunction;
import jp.ac.yamanalab.som.basic.sample.RGBVector;

public class DemoMain {
	private void runTest() throws InterruptedException {
		final int MAP_W = 16;
		final int MAP_H = 16;
		final int PIXEL_SIZE = 600 / MAP_W;
		
		boolean wait = true;
		
		EuclideanMeasure<RGBVector> measure = new EuclideanMeasure<RGBVector>();
		LearningFunction lfunc = new SimpleLearingFunction(Math.max(MAP_W/3, MAP_H/3));
		
		Random rand = new Random(System.currentTimeMillis());
		
		RGBVector[][] map = new RGBVector[MAP_H][MAP_W + MAP_H / 2];
		for (int h=0;h<MAP_H;++h) {
			int offset = (MAP_H - h -1) / 2;
			for (int w=offset;w<MAP_W+offset;++w) {
				map[h][w] = RGBVector.getHSV(rand.nextFloat(), 1.0f, 1.0f);
			}
		}
		
		DemoHexagonalLatticeMap<RGBVector> latticeMap
			= new DemoHexagonalLatticeMap<RGBVector>(map, MAP_W, MAP_H, measure, lfunc);
		DemoHexagonalLatticeMapFrame frame = new DemoHexagonalLatticeMapFrame(latticeMap, PIXEL_SIZE);
		
		frame.update();
		
		RGBVector[] inputs = generateInput(1000);
//		RGBVector[] inputs = new RGBVector[] {
//				RGBVector.getHSV(0f, 1.0f, 1.0f),
//				RGBVector.getHSV(0.333f, 1.0f, 1.0f),
//				RGBVector.getHSV(0.666f, 1.0f, 1.0f)
//		};
//		RGBVector[] inputs = new RGBVector[360];
//		for (int i=0;i<inputs.length;++i)
//			inputs[i] = RGBVector.getHSV((1.0f / (float)i) * 360, 1.0f, 1.0f);
		
		System.out.println("Start learning");
		
		int step;
		for (step=0;step<100000;++step) {

			if (wait) waitAction();
			System.out.println("Draw an input data");
			frame.clearBMU();
			RGBVector v = inputs[step%inputs.length];
			frame.drawInputData(v);
			
			if (wait) waitAction();
			System.out.println("Highlight BMU");
			Coordinate bmu = latticeMap.getBMU(v);
			frame.highlightBMU(bmu, Color.orange);
			
			if (wait) waitAction();
			System.out.println("Learning");
			if (latticeMap.learn(step, v, bmu, 1.0d)) break;
			frame.update();
		}
		
		System.out.println("End learning");
		System.out.println("Learning step: " + step);
	}
	
	private void waitAction() throws InterruptedException {
		Thread.sleep(100);
		try {
			System.in.read();
			System.in.skip(100);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private RGBVector[] generateInput(int num) {
		Random rand = new Random(0);
		RGBVector[] data = new RGBVector[num];
		for (int i=0;i<num;++i)
			data[i] = RGBVector.getHSV(rand.nextFloat(), 1.0f, 1.0f);
		return data;
	}
	
	public static void main(String[] args) throws InterruptedException {
		new DemoMain().runTest();
	}
}
