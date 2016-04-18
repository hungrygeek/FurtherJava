package uk.ac.cam.rkh23.BreakoutGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	// List of all sprites
	private LinkedList<Sprite> mSprites = new LinkedList<Sprite>();
	
	public synchronized void addSprite(Sprite s) {
		mSprites.add(s);
	}
	
	
	@Override
	public synchronized void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Draw all the sprites in our list
		for (Sprite s : mSprites) {
			s.draw(g);
		}
	}
	
	public synchronized void removeSprite(Sprite so) {
		mSprites.remove(so);
	}

}
