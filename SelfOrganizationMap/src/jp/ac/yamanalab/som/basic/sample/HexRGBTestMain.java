package jp.ac.yamanalab.som.basic.sample;

import java.io.IOException;
import java.util.Random;

import jp.ac.yamanalab.som.basic.EuclideanMeasure;
import jp.ac.yamanalab.som.basic.LearningFunction;
import jp.ac.yamanalab.som.basic.SimpleLearingFunction;
import jp.ac.yamanalab.som.basic.hex.HexagonalLatticeMap;
import jp.ac.yamanalab.som.basic.hex.HexagonalLatticeMapFrame;

public class HexRGBTestMain {
	private void runTest() throws InterruptedException, InstantiationException, IllegalAccessException {
		final int MAP_W = 16;
		final int MAP_H = 16;
		final int PIXEL_SIZE = 600 / MAP_W;
		
		boolean wait = false;
		
		EuclideanMeasure<RGBVector> measure = new EuclideanMeasure<RGBVector>();
		LearningFunction lfunc = new SimpleLearingFunction(Math.max(MAP_W/3, MAP_H/3));
		
		HexagonalLatticeMap<RGBVector> hexMap
			= new HexagonalLatticeMap<RGBVector>(RGBVector.class, MAP_W, MAP_H, measure, lfunc);
		HexagonalLatticeMapFrame frame = new HexagonalLatticeMapFrame(hexMap, PIXEL_SIZE);
		
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
			
			Thread.sleep(100);
			if (wait) {
				try {
					System.in.read();
					System.in.skip(100);
					System.out.println("read");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			RGBVector v = inputs[step%inputs.length];
			if (hexMap.learn(step, v, 1.0d)) break;
			frame.update();
		}
		
		System.out.println("End learning");
		System.out.println("Learning step: " + step);
	}
	
	private RGBVector[] generateInput(int num) {
		Random rand = new Random(0);
		RGBVector[] data = new RGBVector[num];
		for (int i=0;i<num;++i)
			data[i] = RGBVector.getHSV(rand.nextFloat(), 1.0f, 1.0f);
		return data;
	}
	
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
		new HexRGBTestMain().runTest();
	}
}
