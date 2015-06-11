package entity;

import java.awt.*;

import javax.swing.*;

import map.*;

public abstract class Enemy extends Unit {
	/** REMOVAL FLAG **/
	protected boolean remove;
	
	/**===========================
	// CONSTRUCTOR(map).
	//============================**/
	public Enemy(TileMap m) {
		super(m);
	}
	
	@Override
	public abstract void update();
	
	@Override
	public abstract void draw(Graphics g);
	
	public boolean removable() { return remove; }
	
	public TileMap getMap() { return map; }
}
