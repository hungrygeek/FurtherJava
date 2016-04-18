package uk.ac.cam.rkh23.BreakoutGame;

public class SuccessDetector implements CollisionHandler {

	@Override
	public void handleCollision(SolidObject o) {
		System.out.println("Made it!");
		System.exit(0);
	}

}
