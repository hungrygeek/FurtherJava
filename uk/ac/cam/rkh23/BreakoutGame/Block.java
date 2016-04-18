package uk.ac.cam.rkh23.BreakoutGame;

import java.awt.Color;
import java.awt.Graphics;

public class Block extends SolidObject implements CollisionHandler {
	
	private Wall[] mWalls = new Wall[4];
	
	
	public Block(float x, float y, float vx, float vy, int size) {
		super(x, y, vx, vy, size);
		mWalls[0] = new HorizontalWall(x,y,0,0,size);
		mWalls[1] = new VerticalWall(x+size,y,0,0,size);
		mWalls[2] = new HorizontalWall(x,y+size,0,0,size);
		mWalls[3] = new VerticalWall(x,y,0,0,size);
		for (int i=0; i<4; i++) {
			mWalls[i].registerForCollisions(this);
		}
	}

	@Override
	public void checkCollision(Ball b) {
		for (int i=0; i<4; i++) {
			mWalls[i].checkCollision(b);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)getX(), (int)getY(), getSize(), getSize());

		for (int i=0; i<4; i++) {
			mWalls[i].draw(g);
		}
		
	}

	@Override
	public void handleCollision(SolidObject so) {
		this.notifyHandlers();
	}

}
