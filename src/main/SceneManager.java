package main;

import scene.*;

import java.awt.*;

public class SceneManager {
	/** CURRENT IN-GAME SCENE. **/
	private static Scene _currentScene;
	
	/** STACK FOR IN-GAME SCENES/STATES. **/
	private static Scene[] _scenes;
	
	/** CONSTANT SIZE FOR STACK. **/
	private static final int SIZE = 5;
	
	/** SCENE STACK POSITION. **/
	private static int _position; // Position for stack.
	
	public static void init() {
		_currentScene = new SceneTitle();
		_scenes = new Scene[SIZE];
		_position = -1;
		changeScene(_currentScene);
	}
	
	public static void update() {
		if (_currentScene != null) {
			_currentScene.update();
		}
	}
	
	public static void draw(Graphics g) {
		if (_currentScene != null) {
			_currentScene.draw(g);
		}
	}
	
	public static void changeScene(Scene newScene) {
		_currentScene = newScene;
	}
	
	public static void callScene(Scene newScene) {
		saveScene();
		changeScene(newScene);
	}
	
	private static void saveScene() {
		// Allocate new array with size of (current position + 1) plus fixed size
		// if array not big enough for another scene. Set as current scene array.
		if (_position >= _scenes.length - 1) {
			int newsize = (_position + 1) + SIZE;
			Scene [] scenescpy = new Scene[newsize];
			System.arraycopy(_scenes, 0, scenescpy, 0, _scenes.length);
			_scenes = scenescpy;
		}
		
		_scenes[++_position] = _currentScene; // Save scene (memory) to remember it.
	}
	
	public static void removeScene() {
		_scenes[_position--] = null; // pop out stored scene
	}
	
	public static void clear() { 
		// Clear out all Scenes in-stack.
		while (_scenes != null) {
			removeScene();
		}
	}
	
	public static Scene getCurrentScene() {
		if (_currentScene == null) { return null; }
		return _currentScene;
	}
	
	public static Scene getScene(int pos) {
		if (pos >= _scenes.length || 
				pos < 0 || _scenes == null) { return null; }
		
		return _scenes[pos];
	}
	
	public static int getPosition() { return _position; }
}
