package uk.ac.cam.rkh23.BreakoutGame;

import java.util.ArrayList;

public abstract class SolidObject extends Sprite {
	
	private ArrayList<CollisionHandler> mHandlers = new ArrayList<CollisionHandler>();

	public SolidObject(float x, float y, float vx, float vy, int size) {
		super(x, y, vx, vy, size);
	}

	public abstract void checkCollision(Ball b);
	
	// Observer pattern here
	public void registerForCollisions(CollisionHandler ch) {
		mHandlers.add(ch);
	}
	
	public void notifyHandlers() {
		for (CollisionHandler c : mHandlers) {
			c.handleCollision(this);
		}
	}
	
}
