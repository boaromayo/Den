package entity;

import java.awt.*;
import java.awt.image.*;

import map.*;

public abstract class Unit extends Entity {
	/**==============================
	 * UNIT VARIABLES.
	/**==============================**/
	/** HEALTH. **/
	protected int hp, mhp;
	
	/** ATTACK/DEFENSE **/
	protected int atk, def;

	/** CONDITIONS **/
	protected boolean hit, dead;
	
	/** SPRITES **/
	protected BufferedImage[] frames;
	
	/** VISIBLILITY **/
	protected boolean visible;
	
	/**=============================
	 * CONSTRUCTOR(map).
	/*==============================**/
	public Unit(TileMap m) {
		super(m);
	}
	
	/**==============================
	 * UNIT METHODS.
	/**==============================**/
	@Override
	public abstract void update();
	
	@Override
	public abstract void draw(Graphics g);
	
	public void hit() {
		if (hit) { return; }
		hit = true;
	}
	
	public void kill() { dead = true; }
	
	//public String getName() { return name; }
	
	/** Add current health by a set value. **/
	public void addHP(int val) { 
		if (getHP() + val > getMaxHP()) { setHP(getMaxHP()); }
		else setHP(getHP()+val);
	}
	
	/** Take current health by a set value. **/
	public void takeHP(int val) { 
		if (getHP() - val < 0) { setHP(0); }
		else setHP(getHP()-val); 
	}
	
	/** Set the current health. **/
	public void setHP(int h) { 
		if (hp >= 0 || h >= 0 || hp-h >= 0) hp = h; 
	}
	
	/** Set the maximum health of the Unit. **/
	public void setMaxHP(int maxh) { if (hp > mhp) hp = mhp; mhp = maxh; }
	
	public void setAtkPower(int a) { atk = a; }
	
	public void setDefPower(int d) { def = d; }
	
	public void setVisible(boolean v) { visible = v; }
	
	/** Check if the target is hit. **/
	public boolean isHit() { return hit; }
	
	/** Check if the target is dead. **/
	public boolean isDead() { return dead; }
	
	public int getHP() { return hp; }
	
	public int getMaxHP() { return mhp; }
	
	public double getHPRate() { return (double) hp / mhp; }
	
	public int getAtkPower() { return atk; }
	
	public int getDefPower() { return def; }
	
	public boolean isVisible() { return visible; }
}
