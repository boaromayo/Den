package entity;

import java.awt.*;

import map.*;

public abstract class DenEvent extends Entity {
	/** EVENT VISIBILITY **/
	protected boolean visible;
	
	/**=========================
	// CONSTRUCTOR(tilemap).
	//==========================**/
	public DenEvent(TileMap m) {
		super(m);
	}

	@Override
	public abstract void update();

	@Override
	public abstract void draw(Graphics g);
	
	public void setVisible(boolean v) { visible = v; }
	
	public boolean isVisible() { return visible; }
}
