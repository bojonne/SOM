package jp.ac.yamanalab.som.basic.circle;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jp.ac.yamanalab.som.basic.DataVector;

public class CircleMapFrame {
	private JFrame frame;
	private CircleMapPanel mapPanel;
	
	public CircleMapFrame(CircleMap<?> map, int radius) {
		
		this.frame = new JFrame("SOM - CiecleMap");
		
		this.mapPanel = new CircleMapPanel(map, radius);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mapPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update() {
		frame.repaint();
	}
	
	private class CircleMapPanel extends JPanel {
		private static final long serialVersionUID = -805781652698922658L;
		
		private final CircleMap<?> map;
		private final int N;
		private final int RADIUS;
		private final double MIN_RADIAN;
		
		public CircleMapPanel(CircleMap<?> map, int radius) {
			this.map = map;
			this.N = map.getMap().length;
			this.RADIUS = radius;
			this.MIN_RADIAN = 360.0 / N;
			
			setPreferredSize(new Dimension(RADIUS*2, RADIUS*2));
		}

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			super.paint(g);
			DataVector[] m = map.getMap();
			for (int rad=0;rad<N;++rad) {
				g2.setColor(m[rad].getColor());
				Arc2D.Double arc = new Arc2D.Double(0,0,RADIUS*2, RADIUS*2, rad * MIN_RADIAN, MIN_RADIAN, Arc2D.PIE);
				g2.fill(arc);
			}
			g.setColor(this.getBackground());
			g.fillOval(RADIUS/2, RADIUS/2, RADIUS, RADIUS);
		}
				
	}
}
