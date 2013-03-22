package jp.ac.yamanalab.som.basic.demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.ac.yamanalab.som.basic.Coordinate;
import jp.ac.yamanalab.som.basic.DataVector;

public class DemoHexagonalLatticeMapFrame {
	private DemoHexagonalLatticeMap<?> map;
	private JFrame frame;
	private InputDataPanel inputDataPanel;
	private HexagonalLatticeMapPanel mapPanel;
	private final int MAP_W;
	private final int MAP_H;
	private final int PIXEL_SIZE;
	private final int DELTA_HEIGHT;
	private final Polygon hexagonTemplate;
	
	public DemoHexagonalLatticeMapFrame(DemoHexagonalLatticeMap<?> map, int pixelSize) {
		this.map = map;
		this.MAP_W = map.getWidth();
		this.MAP_H = map.getHeight();
		this.PIXEL_SIZE = pixelSize;
		this.DELTA_HEIGHT = (int)((double)pixelSize / 2d * Math.tan(Math.PI / 6d));
		this.hexagonTemplate = getHexagonTamplate(pixelSize);
		
		int width = (MAP_W+1) * pixelSize;
		int height = (MAP_H+1) * (pixelSize - DELTA_HEIGHT);
		
		this.frame = new JFrame("SOM - HexagonalLatticeMap");
		
		this.inputDataPanel = new InputDataPanel();
		inputDataPanel.setPreferredSize(new Dimension(PIXEL_SIZE * 2, height));
		
		this.mapPanel = new HexagonalLatticeMapPanel();
		mapPanel.setPreferredSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.add(inputDataPanel);
		frame.add(mapPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void drawInputData(DataVector input) {
		inputDataPanel.addInput(input);
		update();
	}
	
	public void update() {
		frame.repaint();
	}
	
	public void highlightBMU(Coordinate bmu, Color highlightColor) {
		mapPanel.setBMU(bmu, highlightColor);
		update();
	}
	
	public void clearBMU() {
		mapPanel.setBMU(null, null);
		update();
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
	
	private class InputDataPanel extends JPanel {
		private static final long serialVersionUID = -6656809782204605727L;
		private List<DataVector> inputs = new ArrayList<DataVector>();
		private final int MAX_ITEMS = 5;
		
		@Override
		public void paint(Graphics g) {
			Polygon p = new Polygon(hexagonTemplate.xpoints, hexagonTemplate.ypoints, hexagonTemplate.npoints);
			if (!inputs.isEmpty()) {
				g.setColor(inputs.get(inputs.size()-1).getColor());
				g.fillPolygon(p);
				g.setColor(Color.black);
				g.drawPolygon(p);
				p.translate(0, PIXEL_SIZE);
			}
			for (int idx=inputs.size()-2;idx>=0;--idx) {
				p.translate(0, PIXEL_SIZE);
				g.setColor(inputs.get(idx).getColor());
				g.fillPolygon(p);
				g.setColor(Color.black);
				g.drawPolygon(p);
			}
		}
		
		public void addInput(DataVector input) {
			System.out.println("Add input: " + input.getColor());
			inputs.add(input);
			if (inputs.size()> MAX_ITEMS)
				inputs.remove(0);
		}
	}
	
	private class HexagonalLatticeMapPanel extends JPanel {
		private static final long serialVersionUID = 8829419411827781011L;
		private Coordinate bmu;
		private Color highlightColor;

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
			if (bmu != null) {
//				int offset = (MAP_H - bmu.y - 1) / 2;
				Polygon p = getHexagon(bmu.x, bmu.y);
				((Graphics2D)g).setStroke(new BasicStroke(4f));
				g.setColor(highlightColor);
				g.drawPolygon(p);
			}
		}
		
		public void setBMU(Coordinate bmu, Color highlightColor) {
			this.bmu = bmu;
			this.highlightColor = highlightColor;
		}
	}
}
