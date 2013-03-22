package jp.ac.yamanalab.som.basic.sample;

import java.util.Random;

import jp.ac.yamanalab.som.basic.EuclideanMeasure;
import jp.ac.yamanalab.som.basic.LearningFunction;
import jp.ac.yamanalab.som.basic.SimpleLearingFunction;
import jp.ac.yamanalab.som.basic.sqr.LatticeMap;
import jp.ac.yamanalab.som.basic.sqr.LatticeMapFrame;

public class LatticeRGBTestMain {
	
	private void runTest() throws InterruptedException, InstantiationException, IllegalAccessException {
		final int MAP_W = 16;
		final int MAP_H = MAP_W;
		final int PIXEL_SIZE = 600 / MAP_W;
		
		EuclideanMeasure<RGBVector> measure = new EuclideanMeasure<RGBVector>();
		LearningFunction lfunc = new SimpleLearingFunction(Math.max(MAP_W, MAP_H)/3);
		
		LatticeMap<RGBVector> latticeMap = new LatticeMap<RGBVector>(RGBVector.class, MAP_W, MAP_H, measure, lfunc);
		LatticeMapFrame frame = new LatticeMapFrame(latticeMap, PIXEL_SIZE, true);
		frame.drawLine(true);
		
		frame.update();
		
		RGBVector[] inputs = generateInput(1000);
		
		System.out.println("Start learning");
		
		int step;
		for (step=0;step<100000;++step) {
			RGBVector v = inputs[step%inputs.length];
			if (latticeMap.learn(step, v, 1.0d)) break;
			frame.update();
			Thread.sleep(200);
		}
		
		System.out.println("End learning");
		System.out.println("Learning step: " + step);
	}
	
	private RGBVector[] generateInput(int num) {
		Random random = new Random(0);
		RGBVector[] data = new RGBVector[num];
		for (int i=0;i<num;++i)
			data[i] = RGBVector.getHSV(random.nextFloat(), 1.0f, 1.0f);
		return data;
	}
	
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
		new LatticeRGBTestMain().runTest();
	}
}
