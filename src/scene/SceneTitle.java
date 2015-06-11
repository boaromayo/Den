package scene;

import data.*;
import den.*;

import java.awt.*;
import java.awt.image.*;

public class SceneTitle extends Scene {
	// CURRENT CHOICE
	private int currentIndex;
	
	// FILE CHECKER
	private boolean fileexist;
	
	// CHOICES
	private final int START = 0;
	private final int LOAD = 1;
	private final int QUIT = 2;
	
	// IMAGES
	private Image titlebg, title;
	
	// CHOICE ICON
	private BufferedImage heart;
	
	private String[] choices;
	
	/**============================
	// CONSTRUCTOR.
	//=============================**/
	public SceneTitle() {
		super();
		DenSceneManager.clear();
	}
	
	@Override
	public void init() {
		currentIndex = 0;
		
		fileexist = false;
		
		// Initialize menu choices.
		choices[START] = "START GAME";
		choices[LOAD] = "CONTINUE";
		choices[QUIT] = "QUIT";
	}

	/** Draw components of the title. **/
	@Override
	public void draw(Graphics g) {
		// Draw background.
		drawBackground(g, titlebg);
		
		// Draw title.
		drawTitle(g, title);
		
		// Draw menu.
		drawMenu(g);
		
		// Draw cursor.
		drawCursor(g);
		
		// Draw copyright.
		drawOther(g);
	}
	
	private void drawBackground(Graphics g, Image bg) {
		g.drawImage(bg, 0, 0, null);
	}
	
	private void drawTitle(Graphics g, Image title) {
		g.drawImage(title, 150, 100, null);
	}
	
	private void drawMenu(Graphics g) {
		//g.drawString(choices[0], 150);
	}
	
	private void drawCursor(Graphics g) {
		if (currentIndex == START) {
			//g.drawImage(heart, null);
		}
		if (currentIndex == LOAD) {
			
		}
		if (currentIndex == QUIT) {
			
		}
	}
	
	private void drawOther(Graphics g) {
		String cr = "2014 (c) OM Independent";
		g.drawString(cr, DenPanel.WIDTH-cr.length(), DenPanel.HEIGHT-25);
	}
	
	@Override
	public void updateInput() {
		int up = DenInput.UP;
		int down = DenInput.DOWN;
		int left = DenInput.LEFT;
		int right = DenInput.RIGHT;
		int enter = DenInput.ENTER;
		int esc = DenInput.ESCAPE;
		
		if (DenInput.keyDown(up) || DenInput.keyDown(left)) {
			if (currentIndex > 0) { currentIndex--; }
		}
		if (DenInput.keyDown(down) || DenInput.keyDown(right)) {
			if (currentIndex < choices.length) { currentIndex++; }
		}
		if (DenInput.keyDown(enter)) {
			// Current index decides the entered choice
			enterChoice();
		}
		if (DenInput.keyDown(esc)) {
			// Play sound and shut down game
			System.exit(0);
		}
	}
	
	private void enterChoice() {
		if (currentIndex == START) {
			// Play sound and start game
			DenSceneManager.changeScene(new SceneGame());
		} else if (currentIndex == LOAD) {
			if (fileexist) { /* Go to files */ }
			else { /* Play buzzer and do nothing. */ }
		} else if (currentIndex == QUIT) {
			// Play sound and quit game
			System.exit(0);
		}
	}
	
	public boolean fileExists() { return fileexist; }

}
