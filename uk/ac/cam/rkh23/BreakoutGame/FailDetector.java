package uk.ac.cam.rkh23.BreakoutGame;

public class FailDetector implements CollisionHandler {

	@Override
	public void handleCollision(SolidObject o) {
		System.out.println("Loser!");
		System.exit(0);
	}

}
