package entity;

import java.awt.*;
import java.awt.image.*;

import map.*;

public abstract class Item extends Entity {
	/** SPRITES **/
	protected BufferedImage[] frames;
	
	/** REMOVAL FLAG **/
	protected boolean remove;
	
	/** PLAYER USED FLAG **/
	protected boolean used = false;
	
	/**=========================
	// CONSTRUCTOR(tilemap). 
	//==========================**/
	public Item(TileMap m) {
		super(m);
	}
	
	@Override
	public abstract void update();
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(animation.getImage(), (int)x, (int)y, null);
	}
	
	public boolean removable() { return remove; }
	
	public boolean isUsed() { return used; }
}
