package den;

/**===============================================
// HUD
//------------------------------------------------
// This class shows the heads-up display 
// for the player.
//================================================**/
import java.awt.*;
import java.awt.image.*;

import entity.*;

public class HUD {
	
	/** THE PLAYER. **/
	private Den den;
	
	/** IMAGES FOR DISPLAY. **/
	private BufferedImage image;
	
	/** COLOR AND FONT. **/
	private Color color;
	private Font fnt;
	
	/** RATE OF CHANGE TIME COUNTER **/
	private long rateStart, rateDiff;
	private int rateDelay;

	public HUD(Den d) {
		// Set player.
		den = d;
		
		color = Color.BLACK;
		fnt = new Font("Arial", Font.BOLD, 20);
		
		rateDelay = 200;
	}
	
	public void update() {
		rateStart = System.nanoTime();
	}
	
	public void draw(Graphics g) {
		// Draw heads-up display.
		g.setColor(color);
		g.setFont(fnt);
		
		/** Draw HUD components. **/
		
		// Draw status.
		drawStatus(g);
		
		// Draw money.
		drawMoney(g);
		
		// Draw orb value.
		drawOrb(g);
		
		// Draw equipment.
		drawEquip(g);
	}
	
	private void drawStatus(Graphics g) {
		// Change font
		g.setFont(new Font("Arial", Font.BOLD, 12));
		
		// Draw HP and MP
		drawHP(g);
		drawMP(g);
	}
	
	private void drawHP(Graphics g) {
		// Change color
		g.setColor(color);
		
		// Draw text
		g.drawString("HP", 60, 30);
		
		// Calculate current health
		int maxHPCtr = 20; // Max number of counters in gauge.
		int hpWidth = 5; // Width of one square.
		int hpHeight = 12; // Height of one square
		
		double hpPercent = den.getHPRate();
		
		// Get number of filled squares based on current health.
		int currentHP = (int) (hpPercent * maxHPCtr);
		
		// Draw health gauge
		for (int i = 0; i < maxHPCtr / 2; i++) {
			g.drawRect(80 + (i*8), 20, hpWidth, hpHeight);
		}
		for (int i = (maxHPCtr / 2); i < maxHPCtr; i++) {
			g.drawRect(80 + (i*8), 20, hpWidth, hpHeight);
		}
		
		// Fill gauge == to current health
		g.setColor(new Color(230,0,30,255));
		
		for (int j = 0; j < currentHP; j++) {
			g.fillRect(80 + (j*8), 20, hpWidth, hpHeight);
		}
		
		// Draw current and max health
		g.setColor(color);
		
	}
	
	private void drawMP(Graphics g) {
		// Change color
		g.setColor(color);
		
		// Draw text
		g.drawString("MP", 60, 50);
		
		// Calculate current mana
		int maxMPCtr = 20; // Max number of circles in gauge.
		int mpWidth = 6; // Width of one circle.
		int mpHeight = 12; // Height of one square.
		
		double mpPercent;
		
		// Mana points based on number of squares visible.
		if (den.getMaxMP() > 0) {
			mpPercent = den.getMPRate();
		} else {
			mpPercent = 0;
		}
		
		// Get number of filled squares based off current mana
		int currentMP = (int) (mpPercent * maxMPCtr);
		
		// Draw bar skeleton
		g.setColor(Color.WHITE);
		
		// Draw mana squares
		for (int i = 0; i < maxMPCtr / 4; i++) {
			g.drawOval(80 + (i*8), 40, mpWidth, mpHeight);
		}
		for (int i = maxMPCtr / 4; i < maxMPCtr / 2; i++) {
			g.drawOval(80 + (i*8), 40, mpWidth, mpHeight);
		}
		for (int i = maxMPCtr / 2; i < (3*maxMPCtr) / 4; i++) {
			g.drawOval(80 + (i*8), 40, mpWidth, mpHeight);
		}
		for (int i = (3*maxMPCtr) / 4; i < maxMPCtr; i++) {
			g.drawOval(80 + (i*8), 40, mpWidth, mpHeight);
		}
		
		// Fill squares == to current MP
		g.setColor(new Color(58,0,230,255));
		
		for (int j = 0; j < currentMP; j++) {
			g.fillOval(80 + (j*8), 40, mpWidth, mpHeight);
		}
	}
	
	private void drawMoney(Graphics g) {
		// Change stats
		g.setColor(color);
		
	}
	
	private void drawOrb(Graphics g) {
		// Change stats
		//g.setColor(color);
		g.setFont(fnt);
		
		// Draw the orb value square
		g.drawRect(20, 20, 30, 25);
		//g2d.drawImage("", dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
		g.drawString(String.format("%02d", den.getOrb()), 25, 40);
	}
	
	private void drawEquip(Graphics g) {
		// Convert to 2D
		Graphics2D g2 = (Graphics2D) g;
		
		// Change settings
		g2.setColor(color);
		
		// Make a set distance
		int startDist = 100;
		int equipDist = 80;
		
		// Bold drawing lines
		g2.setStroke(new BasicStroke(3));
		
		// Draw two pieces of equipment: sword, and usable item
		g2.drawRect(DenConstants.WIDTH - startDist - equipDist, 20, 50, 40);
		
		g2.drawRect(DenConstants.WIDTH - startDist, 20, 50, 40);
		
		// Draw current equipment
		//g2.drawImage(image, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
		
		// Set back to default
		g2.setStroke(new BasicStroke(1));
	}
	
	//public void setRateDelay(int delay) { rateDelay = delay; }
}
