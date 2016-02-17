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
	/** DIRECTIONAL ENUM CONSTANTS.**/
	public enum Direction { UP = 0, LEFT = 1, RIGHT = 2, DOWN = 3; }
	
	/** TILEMAP. **/
	protected TileMap _map;
	
	/** POSITION AND VELOCITY **/
	protected PhysicsComp _physicsComp;
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
	protected boolean moving, flashing;
	
	/** SPEED **/
	protected boolean walkSpeed, runSpeed; // Aka min & max move speed
	protected double fallSpeed, maxFallSpeed;
	protected double jumpSpeed, maxJumpSpeed;
	
	// ANIMATION OBJECT AND TOGGLE.
	protected AnimationComp _animationComp;
	//protected boolean _animated;
	
	/**==========================
	// CONSTRUCTOR(tileMap).
	//==========================**/
	public Entity(TileMap m) {
		_map = m;
		
		_physicsComp = new PhysicsComp();
		_animationComp = new AnimationComp();
		
		jumpSpeed = 2;
		maxJumpSpeed = 5;
		
		fallSpeed = 2;
		maxFallSpeed = 10;
		
		jumping = false;
		falling = true;

		//_animated = false;
		
		direction = Direction.RIGHT;
	}
	
	public void checkMapCollision() {
		tilex = (int)(x / _map.tileSize());
		tiley = (int)(y / _map.tileSize());
		
		checkTiles(tilex, tiley);
	}
	
	public void checkTiles(int tx, int ty) {
		// Surrounding tiles will be collision check tiles
		boolean tc = (ty - 1) < 0 ? false : _map.isSolid(ty - 1,tx);
		boolean lc = (tx - 1) < 0 ? false : _map.isSolid(ty,tx - 1);
		boolean rc = (tx + 1) > _map.tileSize() ? false : _map.isSolid(ty,tx + 1);
		boolean bc = (ty + 1) > _map.tileSize() ? false : _map.isSolid(ty + 1,tx);
		
		if (tc) { _animationComp.setJumping(false); }
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
	
	public void flash() { 
		if (flashing) return; 
		flashing = true; 
	}
	
	//public void setAnimation(int anim) { /* Let subclasses deal w/ this. */ }
	
	//public void setAnimated(boolean anim) { _animated = anim; }
	
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
		int lx = x * _map.tileSize();
		int ly = y * _map.tileSize();
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
	public AnimationComp animationComp() { return _animationComp; }
	
	public double getx() { return x; }
	
	public double gety() { return y; }
	
	public double getdx() { return dx; }
	
	public double getdy() { return dy; }
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
	
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
	
	public boolean isAnimated() { return _animated; }
	
	/**=============================
	/* ENTITY ABSTRACT METHODS.
	/**==============================**/
	public abstract void update();
	
	public abstract void draw(Graphics g);
}
