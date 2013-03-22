package jp.ac.yamanalab.som.basic.hex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.ac.yamanalab.som.basic.DataVector;

public class HexagonalLatticeMapFrame {
	private JFrame frame;
	private HexagonalLatticeMapPanel mapPanel;
	
	public HexagonalLatticeMapFrame(HexagonalLatticeMap<?> map, int pixelSize) {
		this.frame = new JFrame("SOM - HexagonalLatticeMap");
		
		this.mapPanel = new HexagonalLatticeMapPanel(map, pixelSize);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mapPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update() {
		frame.repaint();
	}
	
	private class HexagonalLatticeMapPanel extends JPanel {
		private static final long serialVersionUID = 8829419411827781011L;
		
		private HexagonalLatticeMap<?> map;
		private final int MAP_W;
		private final int MAP_H;
		private final int PIXEL_SIZE;
		private final int DELTA_HEIGHT;
		private final Polygon hexagonTemplate;
		
		public HexagonalLatticeMapPanel(HexagonalLatticeMap<?> map, int pixelSize) {
			this.map = map;
			this.MAP_W = map.getWidth();
			this.MAP_H = map.getHeight();
			this.PIXEL_SIZE = pixelSize;
			this.DELTA_HEIGHT = (int)((double)pixelSize / 2d * Math.tan(Math.PI / 6d));
			this.hexagonTemplate = getHexagonTamplate(pixelSize);
			
			int width = (MAP_W+1) * pixelSize;
			int height = (MAP_H+1) * (pixelSize - DELTA_HEIGHT);
			setPreferredSize(new Dimension(width, height));
		}

		@Override
		public void paint(Graphics g) {
			DataVector[][] m = map.getMap();
			for (int h=0;h<MAP_H;++h) {
				int offset = (MAP_H - h - 1) / 2;
				for (int w=offset;w<MAP_W+offset;++w) {
					Polygon p = getHexagon(w, h);
					g.setColor(m[h][w].getColor());
					g.fillPolygon(p);
					g.setColor(Color.black);
					g.drawPolygon(p);
				}
			}
		}
		
		private Polygon getHexagonTamplate(int pixelSize) {
			Polygon p = new Polygon();
			p.addPoint(pixelSize/2, 0);
			p.addPoint(pixelSize, DELTA_HEIGHT);
			p.addPoint(pixelSize, pixelSize - DELTA_HEIGHT);
			p.addPoint(pixelSize/2, pixelSize);
			p.addPoint(0, pixelSize - DELTA_HEIGHT);
			p.addPoint(0, DELTA_HEIGHT);
			p.translate(pixelSize/2, 0);
			return p;
		}
		
		private Polygon getHexagon(int w, int h) {
			Polygon p = new Polygon(hexagonTemplate.xpoints, hexagonTemplate.ypoints, hexagonTemplate.npoints);
			int offset = (MAP_H - h - 1) / 2;
			p.translate(PIXEL_SIZE * (w - offset) - PIXEL_SIZE / 2 * (h%2), (PIXEL_SIZE - DELTA_HEIGHT) * h);
			return p;
		}
	}
}
