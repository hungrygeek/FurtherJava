package uk.ac.cam.rkh23.BreakoutGame;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Game extends JFrame implements WindowListener, KeyListener, CollisionHandler {


	private GamePanel mGamePanel = new GamePanel(); // The window
	private Ball mBall = new Ball(400,200,1,-1, 10); // The ball
	private Wall[] mBounds = new Wall[4]; // The game area
	private Bouncer mReflector = new Bouncer(250,450,0,0,200); // The bouncer thing
	private ArrayList<Block> mBlocks = new ArrayList<Block>(); // All the blocks
	private LinkedList<SolidObject> mToDelete = new LinkedList<SolidObject>(); 
	
	
	// COnstructor sets up the playing area
	public Game() {
		setSize(600,500);
		this.addKeyListener(this);
		this.addWindowListener(this);
		this.getContentPane().add(mGamePanel, BorderLayout.CENTER);
				
		mBounds[0] = new HorizontalWall(0,465,0,0,600); // Floor
		mBounds[1] = new HorizontalWall(0,10,0,0,600); // Ceiling
		mBounds[2] = new VerticalWall(10,0,0,0,500); // Left side
		mBounds[3] = new VerticalWall(585,0,0f,0,500); // Right side
	
		mGamePanel.addSprite(mBall);
		mGamePanel.addSprite(new Ball(100,100,1,-1, 10));
		for (Wall w : mBounds) mGamePanel.addSprite(w);
		mGamePanel.addSprite(mReflector);
	
		mBounds[1].registerForCollisions(new SuccessDetector());
		mBounds[0].registerForCollisions(new FailDetector());
		
		for (int j=0; j<5; j++) {
			for (int i=0; i<18; i++) {
				Block b = new Block(i*30+20,j*30+20,0,0,30);
				b.registerForCollisions(this);
				mBlocks.add(b);
				mGamePanel.addSprite(b);
			}
		}
		
		setVisible(true);
	}
	
	// Move forward one time unit
	public void advance() {
		// Delete anything we need to delete from the last time step
		while (mToDelete.size()>0) {
			SolidObject so = mToDelete.pop();
			mGamePanel.removeSprite(so);
			mBlocks.remove(so);
		}
		// Move the ball along
		mBall.update();
		
		// Check for any collisions with the ball
		for(Wall w : mBounds) w.checkCollision(mBall);
		mReflector.checkCollision(mBall);
		for(Block so : mBlocks) {
			so.checkCollision(mBall);
		}
		
		// Repaint the image in the panel
		mGamePanel.repaint();
	}

	// WindowListener stuff
	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowClosing(WindowEvent arg0) {System.exit(0);}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
	
	
	// KeyListener stuff
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode()==39) mReflector.moveRight();
		else if (arg0.getKeyCode()==37) mReflector.moveLeft();
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	
	@Override
	public void handleCollision(SolidObject o) {
		mToDelete.add(o);
	}
	
	
	
	public static void main(String[] args) {
		Game g = new Game();
		while (true) {
			g.advance();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
