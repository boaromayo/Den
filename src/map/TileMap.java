package map;

import java.io.*;

import java.awt.*;
import java.awt.image.*;

import java.util.ArrayList;

import den.DenConstants;
import data.ImageLoader;

public class TileMap {
	// COORDINATES
	private double x, y;
	private double xmin, ymin;
	private double xmax, ymax;
	private double nx, ny;
	private double dx, dy;
	
	// MAP TITLE
	private String name;
	
	// MAP CHARACTER INDEX, TILES IN MAP, TILE INDEX
	//private char [][] map;
	private int [][] map;
	private Tile [][] tiles;
	private BufferedImage[][] tileset;
	
	// TILE AND MAP DIMS
	private int tileSize;
	private int mapWidth, mapHeight;
	private int mapCols, mapRows; // Full map width and height in tiles.
	private int rowLimit, colLimit; // Limit of cols and rows that can be drawn. 
	
	// MAP VERSION
	private boolean topDown; // Shows a top-down view of game.
	private boolean scrollable; // When map moves w/ player, or when it moves dynamically
	
	// MAP MOVING?
	private boolean moving;
	
	/**=================================================
	 * CONSTRUCTOR(filename) - For loading text-based map files.
	 *=================================================*/
	public TileMap(String fileName) {
		tileSize = DenConstants.TILESIZE;
		
		mapCols = mapRows = 0;
		mapWidth = mapCols * tileSize;
		mapHeight = mapRows * tileSize;
		
		rowLimit = DenConstants.HEIGHT / tileSize + 1; // Limit of rows and cols drawn on-screen.
		colLimit = DenConstants.WIDTH / tileSize + 1;
		
		dx = dy = 4;
		
		topDown = true;
		scrollable = false;
		
		name = "Sample";
		
		upload(fileName); // Upload the tiles
	}
	
	/**=================================================
	 * CONSTRUCTOR(mapwidth,mapheight,filename)
	 *=================================================*/
	public TileMap(String fileName, int mw, int mh) {
		tileSize = DenConstants.TILESIZE;
		
		mapCols = mw;
		mapRows = mh;
		mapWidth = mapCols * tileSize;
		mapHeight = mapRows * tileSize;
		
		rowLimit = DenConstants.HEIGHT / tileSize + 1; // Limit of rows and cols drawn on-screen.
		colLimit = DenConstants.WIDTH / tileSize + 1;
		
		dx = dy = 4;
		
		topDown = true;
		scrollable = false;
		
		name = " ";
		
		upload(fileName); // Upload tiles
	}
	
	/**=================================================
	 * CONSTRUCTOR(mapwidth,mapheight,filename,istop)
	 *==================================================*/
	public TileMap(String fileName, boolean isTop, int mw, int mh) {
		tileSize = DenConstants.TILESIZE;
		
		mapCols = mw;
		mapRows = mh;
		mapWidth = mapCols * tileSize;
		mapHeight = mapRows * tileSize;
		
		rowLimit = DenConstants.HEIGHT / tileSize + 1; // Limit of rows and cols drawn on-screen.
		colLimit = DenConstants.WIDTH / tileSize + 1;
		
		dx = dy = 4;
		
		topDown = isTop;
		scrollable = false;
		
		name = " ";
		
		upload(fileName); // Upload tiles
	}
	
	/**=================================================
	 * CONSTRUCTOR(mapwidth,mapheight,filename,istop,name)
	 *==================================================*/
	public TileMap(String fileName, String name, boolean isTop, int mw, int mh) {
		tileSize = DenConstants.TILESIZE;
		
		mapCols = mw;
		mapRows = mh;
		mapWidth = mapCols * tileSize;
		mapHeight = mapRows * tileSize;
		
		rowLimit = DenConstants.HEIGHT / tileSize + 1;
		colLimit = DenConstants.WIDTH / tileSize + 1;
		
		dx = dy = 4;
		
		topDown = isTop;
		scrollable = false;
		
		this.name = name;
		
		upload(fileName); // Upload tiles
	}
	
	/**===========================================
	/* Upload the map data from the text file.
	/*===========================================*/
	private void upload(String fileName) {
		try {
			BufferedReader br = new BufferedReader
					(new FileReader(fileName));
			int numLines = 0; // Checks how many lines in text file.
			
			// String variables, read line and delimited line.
			String line = "";
			String linedelim = "";
			
			if (mapRows == 0) {
				mapRows = readRows(fileName, line, numLines);
			}
			
			// Line index array as a temp to keep characters in
			//char [][] lineIndex = new char[mapRows][];
			
			int [][] lineIndex = new int[mapRows][];
			
			// Open stream again to reset reader.
			br = new BufferedReader(new FileReader(fileName));
						
			while (true) {
				line = br.readLine();
				
				if (line == null) {
					br.close();
					break;
				} else if (line.startsWith("")){
					// Skip first blank space after character
					for (int i = 0; i < line.length(); i += 2) {
						linedelim += line.substring(i, i+1);
					}
					
					// Convert strings in delimited line to array of chars
					lineIndex[numLines] = linedelim.toCharArray();
					
					// Empty delimited line.
					linedelim = "";
					
					// Set map width to the number of chars per row
					// in text file if length of line index is greater
					// than previous width
					if (lineIndex[numLines].length > mapCols) {
						mapCols = lineIndex[numLines].length;
					}
					
					numLines++;
				}
			}
			
			// Set map and tiles to fixed height and width.
			// Assign tiles based on tile id.
			map = new int[mapRows][mapCols];
			tiles = new Tile[mapRows][mapCols];
			
			for (int row = 0; row < mapRows; row++) {
				for (int col = 0; col < mapCols; col++) {
					map[row][col] = lineIndex[row][col]; // Int array should have index.
					tiles[row][col] = loadTile(map[row][col]); // Tiles based on map char array.
				}
			}
			
		} catch (Exception e) {
			System.err.println("ERROR: Unable to read map.");
			e.printStackTrace();
			System.exit(1); // ERROR!
		}
		
	}
	
	/**=======================================
	/* readRows(filename, line, numlines) - Reads the file to find total no. of rows.
	/*========================================**/
	private int readRows(String filename, String line, int numLines) {
		int rows = 0;
		try {
			BufferedReader br = new BufferedReader
					(new FileReader(filename));
			
			// Keep reading file if there are lines.
			while (line != null) {
				line = br.readLine();
				numLines++;
			}
		
			br.close(); // Close reader.
		
			// set height based on (number of lines read - 1)
			// and reset counter.
			rows = numLines - 1;
			numLines = 0;
		} catch (Exception e) {
			System.err.println("ERROR: Unable to read map.");
			e.printStackTrace();
			System.exit(1);
		}
		return rows;
	}
	
	private Tile loadTile(String fileName, int index) {
		try {
			return new Tile(fileName, index);
			/*if (index == 'A') {
				return new Tile(DenConstants.GRASS, Tile.YES);
			} else if (index == 'G') {
				return new Tile(DenConstants.GROUND, Tile.YES);
			} else if (index == 'S') {
				return new Tile(DenConstants.SKY, Tile.NO);
			} else {
				return new Tile(DenConstants.BLANK, Tile.NO);
			}*/
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load tiles onto map.");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	private Tile loadTileset(String fileName) {
		try {
			/*Tile tile = null;
			tileset = ImageLoader.loadSheet(fileName, 
					DenConstants.TILESIZE, DenConstants.TILESIZE);
			
			
			return tile;*/
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load tileset onto game.");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public void update() {
		// Move map when player is out of viewing bounds.
		if (isMoving()) {
			setBounds();
			
			if (x != nx || y != ny) {
				setMoving(true);
			}
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = convert(g);
		
		// Draw tiles.
		/*int colpos = (int)x / tileSize;
		int rowpos = (int)y / tileSize;*/
		if (rowLimit >= mapRows) { rowLimit = mapRows; }
		if (colLimit >= mapCols) { colLimit = mapCols; }
		
		for (int row = 0; row < rowLimit; row++) {
			for (int col = 0; col < colLimit; col++) {
				g2d.drawImage(tiles[row][col].getImage(), 
						(int)x + tileSize * col, 
						(int)y + tileSize * row, 
						null);
			}
		}
		
	}
	
	private Graphics2D convert(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		return g2d;
	}
	
	public void setLocation(double x, double y) { this.x = x; this.y = y; }
	
	public void setx(double x) { this.x = x; }
	
	public void sety(double y) { this.y = y; }
	
	public void setBounds() {
		if (x > nx) {
			x -= dx;
			if (x < nx) {
				x = nx;
			}
		}
		if (x < nx) {
			x += dx;
			if (x > nx) {
				x = nx;
			}
		}
		if (y > ny) {
			y -= dy;
			if (y < ny) {
				y = ny;
			}
		}
		if (y < ny) {
			y += dy;
			if (y > ny) {
				y = ny;
			}
		}
	}
	
	public void setName(String n) { name = n; }
	
	public void setTopDown(boolean t) { topDown = t; }
	
	public void setScrollable(boolean s) { scrollable = s; }
	
	public void setMoving(boolean m) { moving = m; }
	
	public int getx() { return (int)x; }
	
	public int gety() { return (int)y; }
	
	public int tileSize() { return tileSize; }
	
	public int cols() { return mapCols; }
	
	public int rows() { return mapRows; }
	
	public int mapWidth() { return mapWidth; }
	
	public int mapHeight() { return mapHeight; }
	
	public String getName() { return name; }
	
	//public char getIndex(int row, int col) { return map[row][col]; }
	
	public int getIndex(int row, int col) { return map[row][col]; }
	
	public boolean isSolid(int row, int col) { return tiles[row][col].isSolid(); }
	
	public boolean isTransparent(int row, int col) { return tiles[row][col].isTransparent(); }
	
	public boolean isCtrTile(int row, int col) { return tiles[row][col].isCtrTile(); }
	
	public boolean isDangerous(int row, int col) { return tiles[row][col].isDangerous(); }
	
	public boolean isTopDown() { return topDown; }
	
	public boolean isScrollable() { return scrollable; }
	
	public boolean isMoving() { return moving; }
}
