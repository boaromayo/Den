package den;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import map.TileMap;

import entity.*;

public class DenPanel extends JPanel implements Runnable {
	/** THE MAP. **/
	private TileMap map;
	
	/** THE PLAYER. **/
	private Den den;
	
	/** THE DISPLAY. **/
	private HUD hud;
	
	/** THE MAIN THREAD. **/
	private Thread t;
	
	/** OPTIMIZATION MANAGEMENT **/
	private final int FPS = 24;
	private double avgFPS;
	
	private static final long serialVersionUID = 1L;
	
	/** GAME LOOP VARIABLE. **/
	private boolean gameRunning;
	
	public static boolean debug;
	
	public DenPanel() {
		super();
		init();
	}
	
	public void init() {
		setMinimumSize(DenConstants.DIM);
		setPreferredSize(DenConstants.DIM);
		
		initGame();
		
		setFocusable(true);
		requestFocus();
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent kp) {
				int key = kp.getKeyCode();
				DenInput.setKey(key, true);
				den.updateInput();
				
				if (DenInput.keyDown(DenInput.ESCAPE)) {
					if (DenPanel.debug) { System.exit(0); }
				}
			}
			
			@Override
			public void keyReleased(KeyEvent kr) {
				int key = kr.getKeyCode();
				DenInput.setKey(key, false);
				den.updateInput();
			}
			@Override
			public void keyTyped(KeyEvent kt) {}
			
		});
		
		setVisible(true);
		
		if (debug) { printDebug(); }
	}
	
	private void initGame() {
		gameRunning = true; // Start game loop.
		debug = true;
		
		//DenSceneManager.init();
		map = new TileMap("../Den/maps/sample.txt");
		den = new Den(map, 60, 240);
		hud = new HUD(den);
	}
	
	public void addNotify() {
		super.addNotify();
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}
	
	public void run() {
		// Initialize time-counting variables.
		long startTime;
		long diffTime;

		long targetTime = 1000 / FPS;
		
		long waitTime;
		long playTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = FPS;
		
		try {
			while(gameRunning) {
				startTime = System.nanoTime();
				
				update();
				repaint();
				
				diffTime = (System.nanoTime() - startTime) / 1000000;
				waitTime = targetTime - diffTime;
				
				if (waitTime < 0) { waitTime = targetTime; }
				
				Thread.sleep(waitTime);
				
				playTime += System.nanoTime() - startTime;
				
				frameCount++;
				
				if (frameCount == maxFrameCount) {
					avgFPS = 1000.0 / ((playTime / frameCount) / 1000000);
					frameCount = 0;
					playTime = 0;
				}
			}
		} catch (Exception mainerr) {
			System.err.println("ERROR: Unable to run Epic of Den.");
			mainerr.getStackTrace();
			System.exit(1); // ERROR! Exit.
		}
	}
	
	/**====================
	 * MAIN UPDATE METHOD.
	/**====================*/
	public void update() {
		// Update state.
		//DenSceneManager.update();
		
		// Update input.
		DenInput.update();
		
		// Update map.
		map.update();
		
		// Update player.
		den.update();
		
		// Update heads-up display.
		hud.update();
	}
	
	private void printDebug() {
		System.out.println(map.getName());
		
		for (int row = 0; row < map.rows(); row++) {
			for (int col = 0; col < map.cols(); col++) {
				System.out.print(map.getIndex(row,col) + " ");
			}
			System.out.println();
		}
	}
	
	/**================================
	/* PAINT METHOD (for rendering & double buffering).
	/*=================================**/
	@Override
	public void paint(Graphics g) {
		Image dbi = this.createImage(DenConstants.WIDTH, DenConstants.HEIGHT);
		Graphics2D dbg = (Graphics2D) dbi.getGraphics();

		draw(dbg); // Draw
		g.drawImage(dbi, 0, 0, DenConstants.WIDTH, DenConstants.HEIGHT, null);
		
		// Sync up and dispose.
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	/**================================
	/* MAIN DRAWING METHOD.
	/*=================================**/
	/** Draw components (map, Den, and heads-up display). **/
	public void draw(Graphics2D g) {
		// Convert Graphics object to Graphics2D.
		g = convertTo2D(g);
		
		/** Draw current state. **/
		//DenSceneManager.draw(g);
		
		// Draw map
		map.draw(g);
		
		// Draw player.
		den.draw(g);
		
		// Draw HUD.
		hud.draw(g);
		
		// If debug, draw average FPS.
		if (debug) { 
			drawFPS(g);
		}
		
	}
	
	private void drawFPS(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("Avg FPS: " + avgFPS, 20, 12); 
	}
	
	private Graphics2D convertTo2D(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		return g2d;
	}
}
