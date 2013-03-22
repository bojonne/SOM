package jp.ac.yamanalab.som.basic;

import java.awt.Color;

public interface DataVector {
	/** Initializes the instance by random data */
	abstract public void randomize();
	
	/** Returns a dimension of the vector */
	abstract public int getDimension();
	
	/** Returns the double value at the specified position */
	abstract public double get(int index);
	
	/** Sets the value at the specified position */
	abstract public void set(int index, double value);
	
	/** Returns the color of this vector */
	abstract public Color getColor();  // for GUI
}
