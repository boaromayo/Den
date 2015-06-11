package entity;

import java.awt.*;

import map.*;

public class Warp extends DenEvent {

	public Warp(TileMap m) {
		super(m);
	}
	
	public Warp(TileMap m, int newX, int newY) {
		super(m);
		x = newX;
		y = newY;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(animation.getImage(), (int)x, (int)y, null);
	}

}
