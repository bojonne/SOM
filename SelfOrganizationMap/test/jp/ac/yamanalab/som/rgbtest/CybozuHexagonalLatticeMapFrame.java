package jp.ac.yamanalab.som.rgbtest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.ac.yamanalab.som.basic.Coordinate;
import jp.ac.yamanalab.som.basic.EuclideanMeasure;
import jp.ac.yamanalab.som.basic.hex.HexagonalLatticeMap;
import jp.ac.yamanalab.som.rgbtest.CybozuAnalysis.CybozuUser;

public class CybozuHexagonalLatticeMapFrame {
	private HexagonalLatticeMap<CybozuAnalysis.CybozuUser> map;
	private JFrame frame;
	private CybozuHexagonalLatticeMapPanel mapPanel;
	private final int MAP_W;
	private final int MAP_H;
	private final int PIXEL_SIZE;
	private final int DELTA_HEIGHT;
	private final Polygon hexagonTemplate;
	
	public CybozuHexagonalLatticeMapFrame(HexagonalLatticeMap<CybozuAnalysis.CybozuUser> map, int pixelSize) {
		this.map = map;
		this.MAP_W = map.getWidth();
		this.MAP_H = map.getHeight();
		this.PIXEL_SIZE = pixelSize;
		this.DELTA_HEIGHT = (int)((double)pixelSize / 2d * Math.tan(Math.PI / 6d));
		this.hexagonTemplate = getHexagonTamplate(pixelSize);
		
		int width = (MAP_W+1) * pixelSize;
		int height = (MAP_H+1) * (pixelSize - DELTA_HEIGHT);
		
		this.frame = new JFrame("SOM - HexagonalLatticeMap");
		
		this.mapPanel = new CybozuHexagonalLatticeMapPanel();
		mapPanel.setPreferredSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mapPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update() {
		frame.repaint();
	}
	
	public void setUsers(Map<String, Coordinate> users) {
		mapPanel.setUsers(users);
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
	
	private void go(Coordinate c, HexDir dir) {
		c.x += dx[dir.ordinal()];
		c.y += dy[dir.ordinal()];
	}
	
	private double getAvgNorm(Coordinate p, CybozuUser[][] m) {
		EuclideanMeasure<CybozuUser> measure = new EuclideanMeasure<CybozuUser>();
		double total = 0.0;
		int count = 0;
		for (HexDir dir : HexDir.values()) {
			Coordinate cmp = new Coordinate(p);
			go(cmp, dir);
			int offset = (MAP_H - cmp.y - 1) / 2;
			if (cmp.x < offset || cmp.x >= MAP_W+offset)
				continue;
			if (cmp.y < 0 || cmp.y >= MAP_H)
				continue;
			++count;
			total += measure.getDistance(m[p.y][p.x], m[cmp.y][cmp.x]);
		}
		return total / count;
	}
	
	private class CybozuHexagonalLatticeMapPanel extends JPanel {
		private static final long serialVersionUID = 8829419411827781011L;
		private Map<String, Coordinate> users;

		@Override
		public void paint(Graphics g) {
			CybozuUser[][] m = map.getMap();
			for (int h=0;h<MAP_H;++h) {
				int offset = (MAP_H - h - 1) / 2;
				for (int w=offset;w<MAP_W+offset;++w) {
					Polygon p = getHexagon(w, h);
					
					float NormMAX = 35.0f;
					double avgNorm = Math.min(getAvgNorm(new Coordinate(w,h), m), NormMAX*0.8);
					System.out.println(avgNorm);
					Color c = Color.getHSBColor(1.0f * (float)avgNorm / NormMAX, 1.0f, 1.0f);
					//Color c = Color.getHSBColor(30.0f / 360.0f, 1.0f, 1.0f);
					g.setColor(c);
					//g.setColor(m[h][w].getColor());
					g.fillPolygon(p);
					g.setColor(Color.black);
					g.drawPolygon(p);
				}
			}
			
			// draw username
			g.setColor(Color.black);
			for (Entry<String, Coordinate> user : users.entrySet()) {
				int offset = (MAP_H - user.getValue().y - 1)/2;
				int x = PIXEL_SIZE * (user.getValue().x - offset) - PIXEL_SIZE / 2 * (user.getValue().y%2);
				int y = (PIXEL_SIZE - DELTA_HEIGHT) * user.getValue().y;
				((Graphics2D)g).drawString(user.getKey(), x+PIXEL_SIZE/2, y+PIXEL_SIZE/2);
			}
		}
		
		public void setUsers(Map<String, Coordinate> users) {
			this.users = users;
		}
	}
}
