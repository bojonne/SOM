package jp.ac.yamanalab.som.basic.sqr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.ac.yamanalab.som.basic.DataVector;

public class LatticeMapFrame {
	private JFrame frame;
	private LatticeMapPanel mapPanel;
	
	public LatticeMapFrame(LatticeMap<?> map, int pixelSize, boolean drawLine) {
		
		this.frame = new JFrame("SOM - LatticeMap");
		
		this.mapPanel = new LatticeMapPanel(map, pixelSize, drawLine);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mapPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update() {
		frame.repaint();
	}
	
	public void drawLine(boolean drawLine) {
		mapPanel.drawLine(drawLine);
	}
	
	private class LatticeMapPanel extends JPanel {
		private static final long serialVersionUID = -4088377806437243402L;

		private final LatticeMap<?> map;
		private final int MAP_W;
		private final int MAP_H;
		private final int PIXEL_SIZE;
		private boolean drawLine;
		
		public LatticeMapPanel(LatticeMap<?> map, int pixelSize, boolean drawLine) {
			this.map = map;
			
			this.MAP_W = map.getMap()[0].length;
			this.MAP_H = map.getMap().length;
			this.PIXEL_SIZE = pixelSize;
			
			final int width = MAP_W * PIXEL_SIZE;
			final int height = MAP_H * PIXEL_SIZE;
			setPreferredSize(new Dimension(width, height));
		}
		
		@Override
		public void paint(Graphics g) {
			DataVector[][] m = map.getMap();
			for (int h=0;h<MAP_H;++h) {
				for (int w=0;w<MAP_W;++w) {
					g.setColor(m[h][w].getColor());
					g.fillRect(w*PIXEL_SIZE, h*PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
				}
			}
			
			if (drawLine) {
				int width = MAP_W*PIXEL_SIZE;
				int height = MAP_H*PIXEL_SIZE;
				g.setColor(Color.black);
				for (int w=0;w<=width;w+=PIXEL_SIZE) {
					g.drawLine(w, 0, w, height);
				}
				for (int h=0;h<=height;h+=PIXEL_SIZE) {
					g.drawLine(0, h, width, h);
				}
			}
		}
		
		public void drawLine(boolean drawLine) {
			this.drawLine = drawLine;
		}
	}
}
