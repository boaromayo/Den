package den;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import map.TileMap;

import entity.*;

public class DenPanel extends JPanel implements Runnable {
	/** THE MAP. **/
	private TileMap _map;
	
	/** THE PLAYER. **/
	private Den _den;
	
	/** THE DISPLAY. **/
	private HUD _hud;
	
	/** THE MAIN THREAD. **/
	private Thread _t;
	
	/** OPTIMIZATION MANAGEMENT **/
	private final int _FPS = 60;
	private double _avgFPS;
	
	private static final long serialVersionUID = 1L;
	
	/** GAME LOOP VARIABLE. **/
	private boolean _gameRunning;
	
	public static boolean _debug;
	
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
		_gameRunning = true; // Start game loop.
		_debug = true;
		
		//DenSceneManager.init();
		_map = new TileMap("../Den/maps/sample.txt");
		_den = new Den(map, 60, 240);
		_hud = new HUD(den);
	}
	
	public void addNotify() {
		super.addNotify();
		if (_t == null) {
			_t = new Thread(this);
			_t.start();
		}
	}
	
	public void run() {
		// Initialize time-counting variables.
		long startTime;
		long diffTime;

		long targetTime = 1000 / _FPS;
		
		long waitTime;
		long elapsedTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = _FPS;
		
		try {
			while(_gameRunning) {
				startTime = System.nanoTime();
				
				update();
				repaint();
				
				diffTime = (System.nanoTime() - startTime) / 1000000;
				waitTime = targetTime - diffTime;
				
				if (waitTime < 0) { waitTime = targetTime; }
				
				Thread.sleep(waitTime);
				
				elapsedTime += System.nanoTime() - startTime;
				
				frameCount++;
				
				if (frameCount == maxFrameCount) {
					_avgFPS = 1000.0 / ((elapsedTime / frameCount) / 1000000);
					frameCount = 0;
					elapsedTime = 0;
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
		_map.update();
		
		// Update player.
		_den.update();
		
		// Update heads-up display.
		_hud.update();
	}
	
	private void printDebug() {
		System.out.println(_map.getName());
		
		for (int row = 0; row < _map.rows(); row++) {
			for (int col = 0; col < _map.cols(); col++) {
				System.out.print(_map.getIndex(row,col) + " ");
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
		_map.draw(g);
		
		// Draw player.
		_den.draw(g);
		
		// Draw HUD.
		_hud.draw(g);
		
		// If debug, draw average FPS.
		if (_debug) { 
			drawFPS(g);
		}
		
	}
	
	private void drawFPS(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.drawString("Avg FPS: " + _avgFPS, 20, 12); 
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
