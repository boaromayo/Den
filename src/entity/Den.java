package entity;

import den.*;

import java.awt.*;
import java.awt.image.*;

import java.util.*;

import map.*;

public class Den extends Unit {
	/** DEN JUMPING DISTANCE. **/
	private int dist;
	
	/** DEN STATUS. **/
	private int mp, mmp;
	private int orb;
	private int coin;
	
	/** DEN EQUIPMENT **/
	private boolean attacking;
	private boolean defending;
	private ArrayList<BasicItem> items;
	//private Weapon weapon;
	//private Subweapon swpn;
	
	/** DEN ITEM LIMIT **/
	private int numItems = 0;
	public static final int MAX_ITEMS = 20;
	
	/** DEN COLLISION BOXES **/
	private Rectangle atkbox;
	
	//======================================
	// CONSTRUCTOR(map, newX, newY).
	//======================================
	public Den(TileMap m, int newX, int newY) {
		super(m);
		
		atk = 1;
		
		x = newX;
		y = newY;
		width = DenConstants.DENWIDTH;
		height = DenConstants.DENHEIGHT;
		boxw = width;
		boxh = height;
		
		atkbox = new Rectangle();
		atkbox.width = boxw + 16;
		atkbox.height = boxh - (boxh / 2);
		
		hp = mhp = 100;
		mp = mmp = 20;
		
		orb = 10;
		coin = 0;
		//animation.setFrames(frames);
		//visible = true;
		
		items = new ArrayList<BasicItem>();
		numItems = 0;
	}
	
	//======================================
	// CONSTRUCTOR(map, newX, newY, maxHP, maxMP, newOrb)
	//======================================
	public Den(TileMap m, int newX, int newY, 
			int newHP, int newMP, int newOrb) {
		super(m);
		
		atk = 1;
		
		x = newX;
		y = newY;
		width = DenConstants.DENWIDTH;
		height = DenConstants.DENHEIGHT;
		boxw = width;
		boxh = height;
		
		atkbox = new Rectangle();
		atkbox.width = boxw + 16;
		atkbox.height = boxh - (boxh / 2);
		
		hp = mhp = newHP;
		mp = mmp = newMP;
		
		orb = newOrb;
		coin = 0;
		//animation = new Animation(frames);
		//visible = true;
		
		items = new ArrayList<BasicItem>();
		numItems = 0;
	}
	
	//=====================================
	// MAIN UPDATE METHOD.
	//=====================================
	public void update() {
		animate();
		move();
		checkMapCollision();
		
		if (hp < 0) { hp = 0; }
		if (hp == 0) { /*kill();*/ System.exit(0); }
		
		//if (swpn.isUsed()) { /* Create a subweapon projectile.*/ }
	}
	
	public void animate() {
		if (!isAnimated() || frames == null) { return; }
		
		animation.update();
		
		if (isHit()) {
			// Play sound.
			flash(); // Be transparent/flash for a while.
		}
		
		if (isFlashing()) {
			int flashCounter = 0, flashDelay = 400;
			flashCounter++;
			
			if (flashCounter > flashDelay) {
				flashing = false;
			}
		} // Keep player flashing and invincible to enemies.
		
		if (isDead()) {} // Play dead animation.
	}
	
	public void move() {
		if (left) { 
			setdx(-4);
		} 
		if (right) { 
			setdx(4); 
		} 
		if (up) {
			if (map.isTopDown()) {
				setdy(-4);
			}
		} 
		if (down) {
			if (map.isTopDown()) {
				setdy(4);
			}
		}
		if (!map.isTopDown()) {
			if (!jumping) {
				setdx(dist);
				setdy(dist);
				setJumping(true);
			}
			if (falling) {
				dy += fallSpeed;
				if (dy > maxFallSpeed) { dy = maxFallSpeed; }
				if (dy < 0 && falling) { dy += fallSpeed; }
			}
		}
		if (isAttacking()) {
			// Play attacking sprite.
			atkbox.x = (int) x + 10;// Get location of attack bounds.
			atkbox.y = (int) y - (boxh / 2);
		}
		if (isHit() && jumping) {
			if (facingRight) { setdx(-1); }
			else { setdx(1); }
				
			setdy(-3);
			//falling = true;
			setJumping(false);
		} else if (isHit() && !jumping) {
			setdx(0);
		}
		
		x += dx;
		y += dy;
		
		dx = 0;
		dy = 0;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval((int)x, (int)y, width, height);
		//g.drawImage(getImage(), (int)x, (int)y, null);
		
	}
	
	public void updateInput() {
		boolean left = DenInput.keyDown(DenInput.LEFT);
		boolean right = DenInput.keyDown(DenInput.RIGHT);
		
		setLeft(left);
		
		setRight(right);
		
		// Jump if not jumping right now
		/*if (!isJumping()) { 
			setDistance((int)maxJumpSpeed);
			setJumping(DenInput.keyDown(DenInput.SPACE)); 
		}*/
		
		if (DenInput.keyDown(DenInput.SPACE)) {
			takeHP(10);
		}
		if (DenInput.keyDown(DenInput.B)) {
			takeMP(5);
		}
		if (DenInput.keyDown(DenInput.X)) {
			takeOrb();
		}
		if (DenInput.keyDown(DenInput.ENTER)) {
			addOrb(5);
		}

	}
	
	public void setDistance(int d) { dist = d; }

	//public void setSubweapon(Subweapon sw) { swpn = sw; }
	
	/** Add mana by a set value. **/
	public void addMP(int val) {
		if (getMP() + val > getMaxMP()) { setMP(getMaxMP()); }
		else setMP(getMP()+val); 
	}
	
	/** Take away mana by a set value. **/
	public void takeMP(int val) { 
		if (getMP() - val < 0) { setMP(0); }
		else setMP(getMP()-val); 
	}
	
	/** Add one orb. **/
	public void addOrb() { if (orb < 99) orb++; }
	
	/** Add orbs by a set value. **/
	public void addOrb(int o) { 
		if (getOrb() + o > 99) { setOrb(getOrb() + (99-getOrb())); }
		else setOrb(getOrb()+o); 
	}
	
	/** Take one orb. **/
	public void takeOrb() { if (orb > 0) orb--; }
	
	/** Take orbs by a set value. **/
	public void takeOrb(int o) { 
		if (getOrb() - o < 0) { setOrb(0); }
		else setOrb(getOrb()-o); 
	}
	
	public void addItem(BasicItem item) {
		items.add(item);
		numItems++;
	}
	
	public void addMoney() { coin++; }
	
	public void addMoney(int c) { coin += c; }
	
	public void takeMoney() { if (coin > 0) coin--; }
	
	public void takeMoney(int c) { coin -= c; }
	
	public void setMP(int m) { if (mp >= 0 || m >= 0 || mp-m >= 0) mp = m; }
	
	public void setMaxMP(int maxm) { mmp = maxm; }
	
	public void setOrb(int o) { orb = o; }
	
	public void setMoney(int c) { coin = c; }
	
	public void attack() { 
		if (attacking) { return; }
		
		if (DenInput.keyDown(DenInput.C) && !attacking) { /* swpn.use(); Use subweapon. */ }
		else attacking = true;
	}
	
	public void defend() { if (defending) { return; } defending = true; }
	
	public void setMap(TileMap m, boolean top, int x, int y) { 
		map = m; 
		map.setTopDown(top);
		setTileLocation(x,y);
	}
	
	public Rectangle getAttackBounds() { return new Rectangle(atkbox.x, atkbox.y, 
			atkbox.width, atkbox.height); }
	
	public ArrayList<BasicItem> getItems() { return items; }
	
	//public BasicItem getItem(int index) { return items.get(index); }
	
	/*public boolean hasItem(BasicItem item) {
		for (int i = 0; i < numItems; i++) { return (items.get(i) == item); }
	}*/
	
	//public Subweapon getSubweapon() { return swpn; }
	
	//public int getSubwpnAtkPower() { return swpn.getAtkPower(); }
	
	public int getMP() { return mp; }
	
	public int getMaxMP() { return mmp; }
	
	public double getMPRate() { return (double) mp / mmp; }
	
	public int getNumItems() { return numItems; }
	
	public int getOrb() { return orb; }
	
	public int getMoney() { return coin; }
	
	public boolean isAttacking() { return attacking; }
	
	public boolean isDefending() { return defending; }
	
	public TileMap getMap() { return map; }
}
