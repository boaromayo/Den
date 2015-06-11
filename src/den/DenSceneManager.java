package den;

import scene.*;

import java.awt.*;

public class DenSceneManager {
	/** CURRENT IN-GAME SCENE. **/
	private static Scene currentScene;
	
	/** STACK FOR IN-GAME SCENES/STATES. **/
	private static Scene[] scenes;
	
	/** CONSTANT SIZE FOR STACK. **/
	private static final int SIZE = 5;
	
	/** SCENE STACK POSITION. **/
	private static int position; // Position for stack.
	
	public static void init() {
		currentScene = new SceneTitle();
		scenes = new Scene[SIZE];
		position = -1;
		changeScene(currentScene);
	}
	
	public static void update() {
		if (currentScene != null) {
			currentScene.update();
		}
	}
	
	public static void draw(Graphics g) {
		if (currentScene != null) {
			currentScene.draw(g);
		}
	}
	
	public static void changeScene(Scene newScene) {
		currentScene = newScene;
	}
	
	public static void callScene(Scene newScene) {
		saveScene();
		changeScene(newScene);
	}
	
	private static void saveScene() {
		// Allocate new array with size of (current position + 1) plus fixed size
		// if array not big enough for another scene. Set as current scene array.
		if (position >= scenes.length - 1) {
			int newsize = (position + 1) + SIZE;
			Scene [] scenescpy = new Scene[newsize];
			System.arraycopy(scenes, 0, scenescpy, 0, scenes.length);
			scenes = scenescpy;
		}
		
		scenes[++position] = currentScene; // Save scene (memory) to remember it.
	}
	
	public static void removeScene() {
		scenes[position--] = null; // pop out stored scene
	}
	
	public static void clear() { 
		// Clear out all Scenes in-stack.
		while (scenes != null) {
			removeScene();
		}
	}
	
	public static Scene getCurrentScene() {
		if (currentScene == null) { return null; }
		return currentScene;
	}
	
	public static Scene getScene(int pos) {
		if (pos >= scenes.length || 
				pos < 0 || scenes == null) { return null; }
		
		return scenes[pos];
	}
	
	public static int getPosition() { return position; }
}
