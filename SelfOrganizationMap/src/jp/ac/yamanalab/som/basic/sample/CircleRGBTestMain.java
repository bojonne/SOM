package jp.ac.yamanalab.som.basic.sample;

import java.util.Random;

import jp.ac.yamanalab.som.basic.EuclideanMeasure;
import jp.ac.yamanalab.som.basic.LearningFunction;
import jp.ac.yamanalab.som.basic.SimpleLearingFunction;
import jp.ac.yamanalab.som.basic.circle.CircleMap;
import jp.ac.yamanalab.som.basic.circle.CircleMapFrame;

public class CircleRGBTestMain {
	private void runTest() throws InterruptedException, InstantiationException, IllegalAccessException {
		final int N = 360;
		final int RADIUS = 300;
		
		EuclideanMeasure<RGBVector> measure = new EuclideanMeasure<RGBVector>();
		LearningFunction lfunc = new SimpleLearingFunction(N/12);
		
		CircleMap<RGBVector> circleMap = new CircleMap<RGBVector>(RGBVector.class, N, measure, lfunc);
		CircleMapFrame frame = new CircleMapFrame(circleMap, RADIUS);
		
		frame.update();
		
		RGBVector[] inputs = generateInput(1000);
//		RGBVector[] inputs = new RGBVector[] {
//				RGBVector.getHSV(0f, 1.0f, 1.0f),
//				RGBVector.getHSV(0.333f, 1.0f, 1.0f),
//				RGBVector.getHSV(0.666f, 1.0f, 1.0f)
//		};
		
		System.out.println("Start learning");
		
		int step;
		for (step=0;step<100000;++step) {
			RGBVector v = inputs[step%inputs.length];
			Thread.sleep(100);
			if (circleMap.learn(step, v, 1.0d)) break;
			frame.update();
		
		}
		
		System.out.println("End learning");
		System.out.println("Learning Step: " + step);
	}
	
	private RGBVector[] generateInput(int num) {
		Random rand = new Random(0);
		RGBVector[] data = new RGBVector[num];
		for (int i=0;i<num;++i)
			data[i] = RGBVector.getHSV(rand.nextFloat(), 1.0f, 1.0f);
		return data;
	}
	
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
		new CircleRGBTestMain().runTest();
	}
}
