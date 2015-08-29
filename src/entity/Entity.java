package entity;

import java.awt.*;
import java.awt.image.*;

import data.ImageLoader;
import den.DenConstants;

import map.*;

public abstract class Entity {
	/**==================================
	 * CONSTANTS.
	/**==================================*/
	/** DIRECTIONAL CONSTANTS.**/
	public static final int UP = 1, LEFT = 2, RIGHT = 3, DOWN = 4;
	
	/** TILEMAP. **/
	protected TileMap map;
	
	/** POSITION AND VELOCITY **/
	protected double x, y; 
	protected double dx, dy;
	protected int tilex, tiley;
	
	/** SIZE **/
	protected int width, height;
	
	/** COLLISION BOX SIZE **/
	protected int boxw, boxh;
	
	/** MOVEMENT **/
	protected boolean up, down, left, right;
	
	/** ACTIONS **/
	protected boolean moving, jumping, falling, flashing;
	
	/** DIRECTION **/
	protected int direction;
	
	/** SPEED **/
	protected boolean walkSpeed, runSpeed; // Aka min & max move speed
	protected double fallSpeed, maxFallSpeed;
	protected double jumpSpeed, maxJumpSpeed;
	
	// DIRECTION FACING
	protected boolean facingRight;
	
	// ANIMATION OBJECT AND TOGGLE.
	protected Animation animation;
	protected boolean animated;
	
	/**==========================
	// CONSTRUCTOR(tileMap).
	//==========================**/
	public Entity(TileMap m) {
		map = m;
		
		jumpSpeed = 2;
		maxJumpSpeed = 5;
		
		fallSpeed = 2;
		maxFallSpeed = 10;
		
		jumping = false;
		falling = true;
		
		animation = new Animation();
		animated = false;
		
		direction = RIGHT;
	}
	
	public void checkMapCollision() {
		tilex = (int)(x / map.tileSize());
		tiley = (int)(y / map.tileSize());
		
		checkTiles(tilex, tiley);
	}
	
	public void checkTiles(int tx, int ty) {
		// Surrounding tiles will be collision check tiles
		boolean tc = (ty - 1) < 0 ? false : map.isSolid(ty - 1,tx);
		boolean lc = (tx - 1) < 0 ? false : map.isSolid(ty,tx - 1);
		boolean rc = (tx + 1) > map.tileSize() ? false : map.isSolid(ty,tx + 1);
		boolean bc = (ty + 1) > map.tileSize() ? false : map.isSolid(ty + 1,tx);
		
		if (tc) { setJumping(false); }
		if (lc) { setLeft(false); }
		if (rc) { setRight(false); }
		if (bc) { setFalling(false); }
	}
	
	/**===============================
	/* SET METHODS.
	/*=================================**/
	public void setLeft(boolean l) { left = l; }
	
	public void setRight(boolean r) { right = r; }
	
	public void setUp(boolean u) { up = u; }
	
	public void setDown(boolean d) { down = d; }
	
	public void setJumping(boolean j) {
		if (jumping && !falling) { return; }
		jumping = j; 
	}
	
	public void setFalling(boolean f) {  
		if (!jumping && falling) { return; }
		falling = f; 
	}
	
	public void flash() { 
		if (flashing) return; 
		flashing = true; 
	}
	
	public void setAnimation(int anim) { /* Let subclasses deal w/ this. */ }
	
	public void setAnimated(boolean anim) { animated = anim; }
	
	public void setdx(double dx) { this.dx = dx; }
	
	public void setdy(double dy) { this.dy = dy; }
	
	/**============================
	/* setLocation(x,y)
	/*=============================**/
	public void setLocation(double x, double y) {
		this.x = x; 
		this.y = y;
	}
	
	public void setLocation(int x, int y) {
		this.x = (double)x; 
		this.y = (double)y;
	}
	
	/**============================
	 * setTileLocation(x,y)
	/*=============================**/
	public void setTileLocation(int x, int y) {
		int lx = x * map.tileSize();
		int ly = y * map.tileSize();
		setLocation(lx,ly);
	}
	
	/**============================
	/* setVelocity(dx,dy)
	/*=============================**/
	public void setVelocity(double dx, double dy) { 
		this.dx = dx; 
		this.dy = dy; 
	}
	
	/**========================
	/* GET METHODS.
	/*======================**/
	public double getx() { return x; }
	
	public double gety() { return y; }
	
	public double getdx() { return dx; }
	
	public double getdy() { return dy; }
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
	
	public boolean isJumping() { return jumping; }
	
	public boolean isFalling() { return falling; }
	
	public boolean isFlashing() { return flashing; }
	
	public Rectangle getBounds() { return new Rectangle((int)x, (int)y, boxw, boxh); }
	
	public boolean intersects(Entity entity) {
		Rectangle r1 = getBounds();
		Rectangle r2 = entity.getBounds();
		return r1.intersects(r2);
	}
	
	public boolean intersects(Rectangle r) {
		Rectangle r1 = getBounds();
		return r1.intersects(r);
	}
	
	public boolean contains(Entity entity) {
		Rectangle r1 = getBounds();
		Rectangle r2 = entity.getBounds();
		return r1.contains(r2);
	}
	
	public boolean isAnimated() { return animated; }
	
	/**=============================
	/* ENTITY ABSTRACT METHODS.
	/**==============================**/
	public abstract void update();
	
	public abstract void draw(Graphics g);
}
