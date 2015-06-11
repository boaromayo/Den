package entity;

import map.TileMap;
import java.awt.*;

public class BasicItem extends Item {
	/**=========================
	// CONSTRUCTOR. 
	//==========================**/
	public BasicItem(TileMap m) {
		super(m);
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics g) {
		
	}
	
	public void use() { 
		used = true;
		useEffect();
	}
	
	public void useEffect() {}
	
}
