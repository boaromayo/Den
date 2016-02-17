package entity;

import java.awt.*;
import java.awt.image.*;

import java.util.*;

import den.DenConstants;

public class AnimationComp {
	/** ANIMATION VARIABLES **/
	private BufferedImage [] frames;
	private int currFrame, maxFrames; // Current frame count and max number of frames.
	private int countToFrame;
	private int delay;
	private boolean jumping;
	private boolean falling;
	
	/**=========================
	// CONSTRUCTOR. 
	//==========================**/
	public AnimationComp() {
		countToFrame = 0;
		currFrame = 0;
		
		delay = DenConstants.ANIMDELAY;
	}
	
	/**=========================
	// CONSTRUCTOR(frames). 
	//==========================**/
	public AnimationComp(BufferedImage [] frames) {
		this.frames = frames;
		
		countToFrame = 0;
		currFrame = 0;
		maxFrames = frames.length;
		
		delay = DenConstants.ANIMDELAY;
		
	}
	
	public void update() {
		if (delay <= 0) { return; }
		
		countToFrame++; // Increment count-to-frame.
		
		if (countToFrame > delay) {
			currFrame++; // Increment frame.
			countToFrame = 0; // Reset count-to-frame.
		}
		
		if (currFrame == maxFrames) {
			currFrame = 0; // Reset current frame if max is reached.
		}
		
	}
	
	/**======================
	// SET METHODS.
	//=======================**/
	public void setFrames(BufferedImage [] frm) { 
		frames = frm;
		
		maxFrames = frm.length;
	}
	
	public void setCurrentFrame(int frame) { currFrame = frame; }
	
	public void setDelay(int d) { delay = d; }
	
	public void setJumping(boolean jump) { jumping = jump; }
	
	public void setFalling(boolean fall) { falling = fall; } 
	/**=====================
	// GET METHODS.
	//=======================**/
	public BufferedImage[] getFrames() { return frames; }
	
	public BufferedImage getImage() { return frames[currFrame]; }
	
	public int getFrameCount() { return countToFrame; }
	
	public int getCurrentFrame() { return currFrame; }
	
	public int getMaxFrames() { return maxFrames; }
	
	public boolean isJumping() { return jumping; }
	
	public boolean isFalling() { return falling; }
}
