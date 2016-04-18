package uk.ac.cam.rkh23.BreakoutGame;

import java.awt.Color;
import java.awt.Graphics;

public class VerticalWall extends Wall {

	public VerticalWall(float x, float y, float vx, float vy, int size) {
		super(x, y, vx, vy, size);
	}

	@Override
	public void checkCollision(Ball b) {
		if ( (b.getY() >= getY()) &&
				(b.getY() <=getY()+getSize())) {
			float last = b.getLastX()-getX();
			float now = b.getX()-getX();
			// If there was a collision change the ball
			// motion and alert everyone!
			if (last!=0.0 && (last*now<=0.0)) {
				b.setX(getX());
				b.setVx(-b.getVx());
				this.notifyHandlers();
			}
		}				
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.drawLine((int)getX(), (int)getY(), 
				(int)(getX()), (int)(getY()+getSize()));
	}

}
