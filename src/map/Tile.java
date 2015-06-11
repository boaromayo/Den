package map;

import java.awt.*;
import java.awt.image.*;

public class Tile {

	/**============================
	/* VARIABLES.
	/*=============================**/
	// TILE IMAGE.
	private Image img;
	
	// COLLISION
	private boolean solid;
	
	// TRANSPARENT
	private boolean transparent;
	
	// COLLISION TYPES.
	public static final boolean NO = false;
	public static final boolean YES = true;
	
	/**=============================
	/* CONSTRUCTOR(int).
	/*==============================**/
	public Tile(int i) {
		//init(i);
	}
	
	/**=============================
	/* CONSTRUCTOR(image,solid).
	/*==============================**/
	public Tile(Image i, boolean s) {
		img = i;
		solid = s;
	}
	
	/**=============================
	/* CONSTRUCTOR(bufferedImage,solid).
	/*==============================**/
	public Tile(BufferedImage i, boolean s) {
		img = i;
		solid = s;
	}
	
	public void setImage(Image i) {
		try {
			img = i;
		} catch (Exception e) {
			System.err.println("ERROR: Assigned image is invalid.");
			e.printStackTrace();
			img = null; // Return nothing.
			System.exit(1); // ERROR!
		}
	}
	
	public void setSolid(boolean s) { solid = s; }
	
	public void setTransparent(boolean t) { transparent = t; }
	
	public Image getImage() { return img; }
	
	public boolean isSolid() { return solid; }
	
	public boolean isTransparent() { return transparent; }
}
