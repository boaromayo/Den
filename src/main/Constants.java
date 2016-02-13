package main;

import java.awt.*;

import data.ImageLoader;

public class Constants {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int SCALE = 1;
	
	public static final Dimension DIM = new Dimension(WIDTH, HEIGHT);
	
	public static final int ARRAYNUM = 840;
	public static final int TILESIZE = 24;//32;
	
	public static final int DENWIDTH = 24;
	public static final int DENHEIGHT = 24;
	
	public static final int ANIMDELAY = 15;
	public static final int DELAY = 200;
	
	public static final String mappath = "../Den/maps/";
	public static final String path = "../Den/res/";
	private static String tilepath = path + "tiles/";
	private static String spritepath = path + "sprites/";
	private static String imagepath = path + "images/";
	
	public static final String G = tilepath + "AboveGround.png";
	public static final String GRD = tilepath + "Ground.png";
	//public static final String SK = tilepath + "Sky.png";
	
	public static final BufferedImage BLANK = ImageLoader.loadBufferedImage(tilepath + "Blank.png"); 
	public static final BufferedImage GRASS = ImageLoader.loadBufferedImage(G);
	public static final BufferedImage GROUND = ImageLoader.loadBufferedImage(GRD);
								
	//public static final Image TITLEBG = ImageLoader.loadBufferedImage(imagepath + "");
	//public static final Image TITLELABEL = ImageLoader.loadBufferedImage(imagepath + "");
	
	public static final String DEN = spritepath + "Densheet.png";
	
	public static final String GF1_1 = mappath + "";
	
	public static final String TITLE = "The Epic of Den v0.1_0";
}
