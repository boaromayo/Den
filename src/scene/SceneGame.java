package scene;

import den.*;

import entity.*;
//import entity.Subweapon;

import java.awt.*;

import java.util.*;

import map.*;

public class SceneGame extends Scene {
	/**=============================
	 * VARIABLES.
	/*==============================**/
	/** THE MAP. **/
	private TileMap map;
	
	/** THE PLAYER. **/
	private Den den;
	
	/** SET OF ITEMS. **/
	private ArrayList<Item> items;
	
	/** SET OF ENEMIES. **/
	private ArrayList<Enemy> enemies;
	
	/** SET OF SUBWEAPON PROJECTILES. **/
	//private ArrayList<Projectile> subweapons;
	
	/** SET OF ENEMY PROJECTILES. **/
	//private ArrayList<EnemyProjectile> enemyprojs;
	
	/** SET OF EVENTS. **/
	private ArrayList<DenEvent> events;
	
	/** THE DISPLAY. **/
	private HUD hud;
	
	/** EVENT TICKS. **/
	private int eventTicks = 0;
	
	/**============================
	// CONSTRUCTOR.
	//=============================**/
	public SceneGame() {
		super();
		DenSceneManager.clear();
	}
	
	@Override
	public void init() {
		// Initialize game components.
		map = new TileMap("../Den/maps/sample.txt");
		den = new Den(map, 60, 140, 200, 30, 15);
		hud = new HUD(den);
		
		items = new ArrayList<Item>();
		
		enemies = new ArrayList<Enemy>();
		
		events = new ArrayList<DenEvent>();
		
		// Animate all pieces.
		den.setAnimated(true);
		
		for (Item item : items) {
			item.setAnimated(true);
		}
		
		for (Enemy enemy : enemies) {
			enemy.setAnimated(true);
		}
		
		for (DenEvent event : events) {
			event.setAnimated(true);
		}
	}

	/** Draw game components (map, Den, HUD). **/
	@Override
	public void draw(Graphics g) {
		// Draw map.
		map.draw(g);
		
		// Draw player.
		den.draw(g);
		
		// Draw each item on map.
		for (Item item : items) {
			item.draw(g);
		}
		
		// Draw each enemy on map.
		for (Enemy enemy : enemies) {
			enemy.draw(g);
		}
		
		// Draw each event on map.
		for (DenEvent event : events) {
			event.draw(g);
		}
		
		// Draw each projectile: subweapon and enemy.
		/*for (Projectile swpn : subweapons) {
			swpn.draw(g);
		}*/
		
		/*for (EnemyProjectile eproj : enemyprojs) {
			eproj.draw(g);
		}*/
		
		// Draw display.
		hud.draw(g);
	}
	
	@Override
	public void update() {
		// Update input.
		updateInput();
		
		// Update map.
		//map.update();
		
		// Update player.
		den.update();
		
		// Update heads-up display.
		hud.update();
		
		// When the player is dead, enact dead event.
		/*if (den.isDead()) {
		 	blockInput = true;
			eventDead();
		}*/
		
		// For each item, update.
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			item.update();
			
			if (item.removable()) {
				// Take out item.
				items.remove(i);
				i--;
			}
		}
		
		// For each enemy, update enemy.
		for (int e = 0; e < enemies.size(); e++) {
			Enemy enemy = enemies.get(e);
			enemy.update();
			
			// Take out enemy if dead.
			if (enemy.removable()) {
				enemies.remove(e);
				e--;
			}
		}
		
		// For each event, update event.
		for (int e = 0; e < events.size(); e++) {
			DenEvent event = events.get(e);
			event.update();
		}
		
		// For each subweapon thrown, update.
		/*for (int s = 0; s < subweapons.size(); s++) {
			Projectile sproj = events.get(s);
			sproj.update();
			
			if (sproj.remove()) {
				subweapons.remove(s);
				s--;
			}
		}*/
		
		// For each enemy projectile thrown, update.
		/*for (int ep = 0; ep < enemyprojs.size(); ep++) {
			EnemyProjectile enemyproj = enemyprojs.get(ep);
			enemyproj.update();
			
			if (enemyproj.remove()) {
				enemyprojs.remove(ep);
				ep--;
			}
		}*/
		
		// Update collisions.
		updateCollision();
		updateEnemyCollision();
		updateProjectile();
	}
	
	@Override
	public void updateInput() {
		// CHECK IF INPUT BLOCKED.
		if (inputBlocked) { return; }
		
		// DIRECTION
		boolean up = DenInput.keyDown(DenInput.UP) || 
			DenInput.keyDown(DenInput.W);
		boolean down = DenInput.keyDown(DenInput.DOWN) || 
			DenInput.keyDown(DenInput.S);
		boolean left = DenInput.keyDown(DenInput.LEFT) || 
			DenInput.keyDown(DenInput.A);
		boolean right = DenInput.keyDown(DenInput.RIGHT) || 
			DenInput.keyDown(DenInput.D);
		
		// ACTIONS
		boolean jump = DenInput.keyDown(DenInput.A);
		boolean action = DenInput.keyDown(DenInput.S);
		
		// OTHER
		boolean menu = DenInput.keyDown(DenInput.B);
		int esc = DenInput.ESCAPE;
		int DEBUG = DenInput.X;
		
		// SET COMMANDS
		den.setUp(up);
		
		den.setDown(down);
		
		den.setLeft(left);
		
		den.setRight(right);
		
		den.setJumping(jump);
		
		if (action) { /*den.attack();*/ }
		
		if (menu) { 
			inputBlocked = true;
			eventMenu(); // Transition to menu.
		}
		
		if (DenInput.keyDown(esc)) { 
			if (DenPanel.debug) {
				System.exit(0);
			} else {
				eventMenu();
			}
		}
	}
	
	public void updateCollision() {
		// For each item, set effect when player touches it.
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			
			if (den.intersects(item)) {
				// Set effect for item touched and take out item.
				items.remove(i);
				i--;
			}
		}
		
		// For each event, trigger event when player collides.
		for (int e = 0; e < events.size(); e++) {
			DenEvent event = events.get(e);
			
			if (event.contains(den)) {
				if (event instanceof Warp) {
					
				}
			}
			
			/*if (event.intersects(den) && DenInput.keyDown(DenInput.UP)) {
				if (event instanceof NPC) {
				
				}
			}*/
			
		}
	}
	
	private void updateEnemyCollision() {
		// For each enemy, set collisions when player attacks or touches it.
		for (int e = 0; e < enemies.size(); e++) {
			Enemy enemy = enemies.get(e);
			
			// When player touches/hits enemy.
			if (den.intersects(enemy) && !den.isFlashing()) {
				// Calculate damage.
				int dmg = enemy.getAtkPower() - den.getDefPower();
				if (dmg <= 0) { dmg = 0; }
				den.hit();
				den.takeHP(dmg);
			}
			
			// When enemy is hit by player.
			if (enemy.intersects(den.getAttackBounds())) {
				int dmg = den.getAtkPower() - enemy.getDefPower();
				if (dmg <= 0) { dmg = 0; }
				enemy.hit();
				enemy.takeHP(dmg);
			}
		}
	}
	
	private void updateProjectile() {
		// For each enemy projectile on-screen, set collision effects.
		/*for (int ep = 0; ep < enemyprojs.size(); ep++) {
			//EnemyProjectile enemyproj = enemyprojs.get(ep);
		
			// When player is hit by enemy projectiles.
			/*if (den.intersects(enemyproj) && !den.isFlashing()) {
				// Calculate damage.
				int dmg = enemy.getAtkPower() - den.getDefPower();
				if (dmg <= 0) { dmg = 0; }
				den.hit();
				den.takeHP(dmg);
				
				// Remove enemy projectiles.
				enemyprojs.remove(ep);
				ep--;
			}
			
			// When player hits enemy projectiles.
			if (enemyproj.intersects(den.getAttackBounds())) {
				// Remove enemy projectiles.
				enemyprojs.remove(ep);
				ep--;
			}
		}*/
	}
	
	/**==================================================
	 * EVENT METHODS.
	/**==================================================*/
	public void eventMenu() {
		eventTicks++;
		
		if (eventTicks == 1) {
			// Play sound, visual effects
			
		}
		if (eventTicks == 30) { // Transition to menu
			resetTicks();
			DenSceneManager.callScene(new SceneMenu(den));
		}
	}
	
	public void eventTransfer(String fileName, boolean isTop, int x, int y) {
		eventTicks++;
		
		if (eventTicks == 1) {
			// Make the transition
			// Play sound
			// Fade screen
			// Set transition to new map
		}
		if (eventTicks == 100) {
			map = new TileMap(fileName);
			den.setMap(map, isTop, x, y);
		}
	}
	
	public void eventDead() {
		eventTicks++;
		
		if (eventTicks == 1) {
			
		}
	}
	
	public void resetTicks() { eventTicks = 0; }
}
