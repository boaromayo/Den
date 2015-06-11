package scene;

import den.*;

import entity.*;

import java.awt.*;

import java.util.*;

public class SceneMenu extends Scene {
	
	/** THE PLAYER. **/
	private Den den;
	
	/** DEN EQUIPMENT **/
	private ArrayList<BasicItem> items;
	
	/** MENU CHOICES, INDEX, AND MODE. **/
	private int currentIndex;
	private int currentMode;
	private String[] choices;
	
	/** MENU WINDOWS. **/
	//private WindowStatus ws;
	//private WindowItem wi;
	//private WindowEquip we;
	
	/**============================
	// CONSTRUCTOR.
	//=============================**/
	public SceneMenu(Den d) {
		super();
		initDen(d);
	}
	
	@Override
	public void init() {
		currentIndex = 0;
		currentMode = 1; // Start at status mode.
	}
	
	private void initDen(Den d) {
		den = d;
		items = den.getItems();
	}
	
	@Override
	public void draw(Graphics g) {
		// Draw menu based on current mode.
		drawMode(g, currentMode);
	}
	
	private void drawMode(Graphics g, int mode) {
		if (mode == 0) {
			drawItemMode(g);
		} 
		if (mode == 1) {
			drawStatusMode(g);
		}
		if (mode == 2) {
			drawEquipMode(g);
		}
	}
	
	private void drawItemMode(Graphics g) {
		
	}
	
	private void drawStatusMode(Graphics g) {
		
	}
	
	private void drawEquipMode(Graphics g) {
		
	}
	
	
	
	@Override
	public void update() {
		updateInput();
	}
	
	@Override
	public void updateInput() {
		int up = DenInput.UP;
		int down = DenInput.DOWN;
		int left = DenInput.LEFT;
		int right = DenInput.RIGHT;
		
		if (DenInput.keyDown(up) || 
				DenInput.keyDown(DenInput.W)) {
			
		}
		if (DenInput.keyDown(down) || 
				DenInput.keyDown(DenInput.S)) {

		}
		if (DenInput.keyDown(left) ||
				DenInput.keyDown(DenInput.A)) {
			if (currentMode == 0) {
				if (currentIndex > 0) { currentIndex--; }
			}
			if (currentMode == 1) {
				currentMode = 0;
			}
			if (currentMode == 2) {
				
			}
		}
		if (DenInput.keyDown(right) ||
				DenInput.keyDown(DenInput.D)) {
			if (currentMode == 0) {
				if (currentIndex < choices.length) { currentIndex++; }
				else { currentMode++; currentIndex = 0; }
			}
			if (currentMode == 1) {
				currentMode = 2;
			}
			if (currentMode == 2) {
				if (currentIndex < choices.length) { currentIndex++; }
				else { currentMode = currentIndex = 0; }
			}
		}
	}
	
	public void setMode(int mode) { currentMode = mode; }
	
	public void setIndex(int i) { currentIndex = i; }

}
