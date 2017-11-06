package data;

import java.awt.*;
import java.awt.image.*;

import java.io.*;

import javax.imageio.*;

import main.DenPanel;

public class ImageLoader {
	/**=============================================
	// PRIVATE CONSTRUCTOR. - Prevents default instantiation.
	//==============================================**/
	private ImageLoader() {}
	
	/**=============================================
	// SINGLETON OBJECT. - Ensure only one object is made.
	//==============================================**/
	private static ImageLoader singleton = null;
	
	/**=============================================
	// get() - Ensures access to methods.
	//==============================================**/
	public static ImageLoader get() {
		if (singleton == null) {
			synchronized (ImageLoader.class) {
				if (singleton == null) {
					singleton = new ImageLoader();
				}
			}
		}
		
		return null;
	}
	
	/**=============================================
	/* loadImage(fileName) - Load an image.
	/**=============================================**/
	public Image loadImage(String fileName) {
		try {
			System.out.println("Image loading...");
			return Toolkit.getDefaultToolkit().getImage(fileName);
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load image " + fileName);
			System.err.println("REASON: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		return null;
		
	}
	
	/**=============================================
	/* loadBufferedImage(fileName) - Load BufferedImage.
	/**=============================================**/
	public BufferedImage loadBufferedImage(String fileName) {
		try {
			// Try and load buffered image.
			if (DenPanel._debug) System.out.println("Buffered image loading...");
			return ImageIO.read(getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load buffered image " + fileName);
			System.err.println("REASON: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	/**=============================================
	/* loadAndCrop(fileName) - Load a BufferedImage and get part of it.
	/**=============================================**/
	public BufferedImage loadAndCrop(String fileName, int x, int y, 
			int w, int h) {
		BufferedImage b = null;
		
		try {
			// Try and load buffered image.
			b = loadBufferedImage(fileName);
			
			// Crop image.
			if (DenPanel._debug) System.out.println("Cropping...");
			b = b.getSubimage(x, y, w, h);
			if (DenPanel._debug) System.out.println("Image cropped successfully.");
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load/prepare image " + fileName);
			System.err.println("REASON: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
			b = null;
		}
		
		return b;
	}
	
	/**=============================================
	/* loadSheet(fileName) - Load a BufferedImage and place each part on a 2-D "sheet".
	/**=============================================**/
	public BufferedImage[][] loadSheet(String fileName, int w, int h) {
		BufferedImage[][] b = null;
		
		try {
			// Load in spritesheet.
			BufferedImage sheet = loadBufferedImage(fileName);
			
			// Crop image.
			System.out.print("Cropping...");
			
			// Set in dimensions of sheet.
			int width = sheet.getWidth() / w;
			int height = sheet.getHeight() / h;
			b = new BufferedImage[height][width];
			
			// Load images onto array.
			for (int j = 0; j < h; j++) {
				for (int i = 0; i < w; i++) {
					b[i][j] = loadAndCrop(fileName,i*w,j*h,w,h);
				}
			}
			if (DenPanel._debug) System.out.println("Image successfully cropped.");
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load/prepare image " + fileName);
			System.err.println("REASON: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
			b = null;
		}
		
		return b;
	}
}
