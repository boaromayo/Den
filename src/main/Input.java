package den;

import java.awt.event.*;

public class Input {

	/**===========================
	/* INPUT KEY CONSTANTS.
	/**===========================*/
	public static final int W = KeyEvent.VK_W;
	public static final int A = KeyEvent.VK_A;
	public static final int D = KeyEvent.VK_D;
	public static final int S = KeyEvent.VK_S;
	public static final int B = KeyEvent.VK_B;
	public static final int X = KeyEvent.VK_X;
	public static final int C = KeyEvent.VK_C;
	public static final int UP = KeyEvent.VK_UP;
	public static final int LEFT = KeyEvent.VK_LEFT;
	public static final int RIGHT = KeyEvent.VK_RIGHT;
	public static final int DOWN = KeyEvent.VK_DOWN;
	public static final int SPACE = KeyEvent.VK_SPACE;
	public static final int ENTER = KeyEvent.VK_ENTER;
	public static final int SHIFT = KeyEvent.VK_SHIFT;
	public static final int ESC = KeyEvent.VK_ESCAPE;
	
	/**==========================
	/* INPUT KEY METHODS.
	/*===========================**/
	public static final int[] keys = { W, A, D, S, B, X, C,
										UP, LEFT, RIGHT, DOWN, 
										SPACE, ENTER, SHIFT, ESC };
	private static boolean [] keyBefore = new boolean[keys.length]; // keeps previous key pressed
	private static boolean [] keyNow = new boolean[keys.length]; // checks current key pressed
	
	private static int checkKey(int key) {
		int newKey = 0; // 0 is invalid
		for (int i = 0; i < keys.length; i++) {
			if (key == keys[i]) { newKey = i; break; }
		}
		
		return newKey;
	}
	
	public static void setKey(int key, boolean set) {
		/*for (int i = 0; i < keys.length; i++) {
			if (key == keys[i]) { keyNow[i] = set; }
		}*/
		switch(key) {
		case W:
			keyNow[0] = set;
			break;
		case A:
			keyNow[1] = set;
			break;
		case D:
			keyNow[2] = set;
			break;
		case S:
			keyNow[3] = set;
			break;
		case B:
			keyNow[4] = set;
			break;
		case X:
			keyNow[5] = set;
			break;
		case C:
			keyNow[6] = set;
			break;
		case UP:
			keyNow[7] = set;
			break;
		case LEFT:
			keyNow[8] = set;
			break;
		case RIGHT:
			keyNow[9] = set;
			break;
		case DOWN:
			keyNow[10] = set;
			break;
		case SPACE:
			keyNow[11] = set;
			break;
		case ENTER:
			keyNow[12] = set;
			break;
		case SHIFT:
			keyNow[13] = set;
			break;
		case ESCAPE:
			keyNow[14] = set;
			break;
		default:
			break;
		}
	}
	
	public static void update() {
		for (int i = 0; i < keys.length; i++) {
			keyBefore[i] = keyNow[i];
		}
	}
	
	public static boolean keyPressed(int i) {
		i = checkKey(i);
		return keyNow[i] && !keyBefore[i];
	}
	
	public static boolean keyDown(int i) {
		i = checkKey(i);
		return keyNow[i];
	}
	
	public static boolean keyRepeated(int i) {
		i = checkKey(i);
		return keyNow[i] && keyBefore[i];
	}
	
	public static boolean keyUp(int i) {
		i = checkKey(i);
		return !keyNow[i];
	}
	
}
