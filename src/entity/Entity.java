package entity;

import java.awt.*;
import java.awt.image.*;

import data.ImageLoader;
import main.Constants;

import map.*;

public abstract class Entity {
	/**==================================
	 * CONSTANTS.
	/**==================================*/
	/** DIRECTIONAL ENUM CONSTANTS.**/
	public enum Direction { UP, LEFT, RIGHT, DOWN };
	
	/** TILEMAP. **/
	protected TileMap _map;
	
	/** DIRECTION **/
	protected Direction _direction;
	
	/** POSITION AND VELOCITY **/
	protected double _x, _y;
	protected double _vel;
	
	/** PHYSICS COMPONENT. **/
	protected PhysicsComp _physics;
	
	/** ANIMATION COMPONENT. **/
	protected AnimationComp _animation;
	
	/** SIZE **/
	protected int _width, _height;
	
	/** MOVEMENT **/
	protected boolean _up, _down, _left, _right;
	
	/** SPEED **/
	protected double walkSpeed, runSpeed; // Aka min & max move speed
	protected double fallSpeed, maxFallSpeed;
	protected double jumpSpeed, maxJumpSpeed;
	
	/**==========================
	// CONSTRUCTOR. (for entities which won't need a map)
	//===========================**/
	public Entity() {
		_map = null;
		
		_physics = new PhysicsComp();
		_animation = new AnimationComp();
	}
	
	/**==========================
	// CONSTRUCTOR(tileMap).
	//==========================**/
	public Entity(TileMap m) {
		_map = m;
		
		_physics = new PhysicsComp(_width, _height);
		_animation = new AnimationComp();
	}
	
	/*public void checkMapCollision() {
		tilex = (int)(_x / _map.tileSize());
		tiley = (int)(_y / _map.tileSize());
		
		checkTiles(tilex, tiley);
	}
	
	public void checkTiles(int tx, int ty) {
		// Surrounding tiles will be collision check tiles
		boolean tc = (ty - 1) < 0 ? false : _map.isSolid(ty - 1,tx);
		boolean lc = (tx - 1) < 0 ? false : _map.isSolid(ty,tx - 1);
		boolean rc = (tx + 1) > _map.tileSize() ? false : _map.isSolid(ty,tx + 1);
		boolean bc = (ty + 1) > _map.tileSize() ? false : _map.isSolid(ty + 1,tx);
		
		if (tc) { _animation.setJumping(false); }
		if (lc) { setLeft(false); }
		if (rc) { setRight(false); }
		if (bc) { _animation.setFalling(false); }
	}*/
	
	/**===============================
	/* SET METHODS.
	/*=================================**/
	public void setLeft(boolean l) { _left = l; _direction = Direction.LEFT; }
	
	public void setRight(boolean r) { _right = r; _direction = Direction.RIGHT; }
	
	public void setUp(boolean u) { _up = u; _direction = Direction.UP; }
	
	public void setDown(boolean d) { _down = d; _direction = Direction.DOWN; }
	
	/**============================
	/* setLocation(x,y)
	/*=============================**/
	public void setLocation(double x, double y) {
		_x = x; 
		_y = y;
	}
	
	public void setLocation(int x, int y) {
		_x = (double)x; 
		_y = (double)y;
	}
	
	/**============================
	 * setVelocity(v)
	/*=============================**/
	public void setVelocity(double v) { _vel = v; }
	
	public void setVelocity(int v) { _vel = (double)v; }
	
	/**============================
	 * setSize(w,h)
	/*=============================**/
	public void setSize(int w, int h) {
		_width = w;
		_height = h;
	}
	
	/**============================
	 * setTileLocation(x,y)
	/*=============================**/
	/*public void setTileLocation(int x, int y) {
		int lx = x * _map.tileSize();
		int ly = y * _map.tileSize();
		setLocation(lx,ly);
	}*/
	
	/**========================
	/* GET METHODS.
	/*======================**/
	public PhysicsComp physics() { return _physics; }
	
	public AnimationComp animation() { return _animation; }
	
	public double x() { return _x; }
	
	public double y() { return _y; }
	
	public double velocity() { return _vel; }
	
	public int width() { return _width; }
	
	public int height() { return _height; }

	//public boolean isAnimated() { return _animated; }
	
	/**=============================
	 * update()
	/**=============================**/
	public void update() {
		_physics.update();
		_animation.update();
	}
	
	/**=============================
	/* ENTITY ABSTRACT METHODS.
	/**==============================**/
	public abstract void draw(Graphics g);
}
