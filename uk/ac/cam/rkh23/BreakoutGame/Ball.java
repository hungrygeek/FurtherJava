package uk.ac.cam.rkh23.BreakoutGame;

import java.awt.Color;
import java.awt.Graphics;

public class Ball extends Sprite {

	public Ball(float x, float y, float vx, float vy, int size) {
		super(x, y, vx, vy, size);
	}
	
	
	// Add storage of last position when updating
	private float mLastX;
	private float mLastY;
	@Override
	public void update() {
		mLastX=getX();
		mLastY=getY();
		super.update();
	}
	public float getLastX() {return mLastX;}
	public float getLastY() {return mLastY;}
	
	// Set how to draw a ball
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval((int)getX(), (int)getY(), getSize(), getSize());
	}

}
