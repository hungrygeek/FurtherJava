package uk.ac.cam.rkh23.BreakoutGame;

import java.awt.Color;
import java.awt.Graphics;

public class HorizontalWall extends Wall {

	public HorizontalWall(float x, float y, float vx, float vy, int size) {
		super(x, y, vx, vy, size);
	}

	@Override
	public void checkCollision(Ball b) {
		if ( (b.getX() >= getX()) &&
				(b.getX() <=getX()+getSize())) {
			float last = b.getLastY()-getY();
			float now = b.getY()-getY();
			// If there was a collision change the ball
			// motion and alert everyone!
			if (last!=0.0 && (last*now<=0.0)) {
				b.setY(getY());
				b.setVy(-b.getVy());
				this.notifyHandlers();
			}
		}				
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.drawLine((int)getX(), (int)getY(), 
				(int)(getX()+getSize()), (int)(getY()));
	}

}
