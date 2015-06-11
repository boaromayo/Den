package scene;

import den.*;

import java.awt.*;

public abstract class Scene {
	/** INPUT BLOCKER FOR EVENT TRANSITIONS. **/
	protected boolean inputBlocked = false;
	
	/**=========================
	// CONSTRUCTOR. 
	//==========================**/
	public Scene() {
		init();
	}
	
	public abstract void init();
	
	public abstract void draw(Graphics g);
	
	public void update() { updateInput(); }
	
	public abstract void updateInput();
	
	public void removeScene() { DenSceneManager.removeScene(); }
}
