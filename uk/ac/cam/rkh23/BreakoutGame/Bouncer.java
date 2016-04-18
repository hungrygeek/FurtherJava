package uk.ac.cam.rkh23.BreakoutGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bouncer extends HorizontalWall {

	public Bouncer(float x, float y, float vx, float vy, int size) {
		super(x, y, vx, vy, size);
	}
	
	public void moveRight() {
		setX(getX()+15);
	}
	public void moveLeft() {
		setX(getX()-15);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g.drawLine((int)getX(), (int)getY(), 
				(int)(getX()+getSize()), (int)(getY()));
		g2.setStroke(new BasicStroke(1));
	}
}
