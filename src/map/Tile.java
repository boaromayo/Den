package map;

import java.awt.*;
import java.awt.image.*;

public class Tile {
	/**============================
	/* VARIABLES.
	/*=============================**/
	// TILE ID, TYPE.
	private int id;
	private String type;
	
	// TILE IMAGE.
	private BufferedImage img;
	
	// COLLISION
	private boolean solid;
	
	// TRANSPARENT
	private boolean transparent;
	
	// COUNTER
	private boolean counter;
	
	// DANGEROUS
	private boolean dangerous;
	/**=============================
	/* CONSTRUCTOR().
	/*==============================**/
	public Tile() {
		id = 0;
		type = "";
		img = null;
		solid = false;
		transparent = false;
		counter = false;
		dangerous = false;
	}
	
	/**=============================
	/* CONSTRUCTOR(id,type,bufferedImage).
	/*==============================**/
	public Tile(int id, String type, BufferedImage img) {
		this.id = id;
		this.type = type;
		this.img = img;
		solid = false;
		transparent = false;
		counter = false;
		dangerous = false;
	}
	
	/**=============================
	/* CONSTRUCTOR(id,type,bufferedImage,solid).
	/*==============================**/
	public Tile(int id, String type, BufferedImage img, boolean s) {
		this(id,type,img);
		solid = s;
		transparent = false;
		counter = false;
		dangerous = false;
	}
	
	/**=============================
	/* CONSTRUCTOR(id,type,bufferedImage,solid,transparent).
	/*==============================**/
	public Tile(int id, String type, BufferedImage img, boolean s, boolean t) {
		this(id,type,img,s);
		transparent = t;
		counter = false;
		dangerous = false;
	}
	
	public void setId(int i) { id = i; }
	
	public void setType(String t) {	type = t; }
	
	public void setImage(BufferedImage i) {
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
	
	public void setCounter(boolean ctr) { counter = ctr; }
	
	public void setDangerous(boolean d) { dangerous = d; }
	
	public int getId() { return id; }
	
	public String getType() { return type; }
	
	public BufferedImage getImage() { return img; }
	
	public boolean isSolid() { return solid; }
	
	public boolean isTransparent() { return transparent; }

	public boolean isCtrTile() { return counter; }
	
	public boolean isDangerous() { return dangerous; }
}
