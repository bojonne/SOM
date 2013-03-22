package jp.ac.yamanalab.som.basic.sample;

import java.awt.Color;

import jp.ac.yamanalab.som.basic.DataVector;

public class RGBVector implements DataVector {
	private int[] rgb;
	private final int DIMENSION = 3;
	
	public RGBVector() {
		rgb = new int[DIMENSION];
	}
	
	public RGBVector(int r, int g, int b) {
		rgb = new int[] {r, g, b};
	}
	
	@Override
	public void randomize() {
		Color c = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
		rgb[0] = c.getRed();
		rgb[1] = c.getGreen();
		rgb[2] = c.getBlue();
	}
	
	public static RGBVector getRandom() {
		return new RGBVector(
				(int)(256.0 * Math.random()),
				(int)(256.0 * Math.random()),
				(int)(256.0 * Math.random()));
	}

	public static RGBVector getHSV(float h, float s, float b) {
		Color c = Color.getHSBColor(h, s, b);
		return new RGBVector(
				c.getRed(),
				c.getGreen(),
				c.getBlue());
	}
	
	@Override
	public double get(int index) {
		return rgb[index];
	}

	@Override
	public int getDimension() {
		return DIMENSION;
	}

	@Override
	public void set(int index, double value) {
		if (value < 0 || value > 255)
			throw new IllegalArgumentException("value = " + value + ", a value of RGB must be from 0 to 255");
		rgb[index] = (int)value;
	}

	@Override
	public Color getColor() {
		return new Color(rgb[0], rgb[1], rgb[2]);
	}
}
