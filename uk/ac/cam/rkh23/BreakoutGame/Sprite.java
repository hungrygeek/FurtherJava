package uk.ac.cam.rkh23.BreakoutGame;

import java.awt.Graphics;

public abstract class Sprite {
	private float mX;
	private float mY;
	private float mVx;
	private float mVy;
	private int mSize;
	
	public Sprite(float x, float y, float vx, float vy, int size) {
		mX=x;
		mY=y;
		mVx=vx;
		mVy=vy;
		mSize=size;
	}
	
	// Force users of the class to implement a draw() method
	public abstract void draw(Graphics g);
	
	// Update the position by moving one time unit on
	public void update() {
		mX+=mVx;
		mY+=mVy;
	}
	
	// Getters and setters
	public int getSize() {return mSize;}
	public float getX() {return mX;}
	public void setX(float mX) {this.mX = mX;}
	public float getY() {return mY;}
	public void setY(float mY) {this.mY = mY;}
	public float getVx() {return mVx;}
	public void setVx(float mVx) {this.mVx = mVx;}
	public float getVy() {return mVy;}
	public void setVy(float mVy) {this.mVy = mVy;}

	
	
}
