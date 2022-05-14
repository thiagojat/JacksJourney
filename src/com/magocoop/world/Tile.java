 package com.magocoop.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.magocoop.main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_IN_FLOOR = Game.spritesheet.getSprite(0, 80, 16, 16);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
	public static BufferedImage TILE_GRID = Game.spritesheet.getSprite(16, 32, 16, 16);
	public static BufferedImage TILE_GRID_VL = Game.spritesheet.getSprite(0, 64, 16, 16);
	public static BufferedImage TILE_GRID_VR = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage TILE_GRID_CL = Game.spritesheet.getSprite(16, 48, 16, 16);
	public static BufferedImage TILE_GRID_CR = Game.spritesheet.getSprite(16, 64, 16, 16);
	public static BufferedImage TILE_GLASS_DOOR = Game.spritesheet.getSprite(16, 80, 16, 16);
	public static BufferedImage TILE_JAIL_DOOR = Game.spritesheet.getSprite(16, 96, 16, 16);
	public static BufferedImage TILE_JAIL_DOOR1 = Game.spritesheet.getSprite(16, 112, 16, 16);
	public static BufferedImage TILE_TABLE = Game.spritesheet.getSprite(0, 96, 16, 16);
	public static BufferedImage TILE_TABLE_COM = Game.spritesheet.getSprite(0, 112, 16, 16);
	public static BufferedImage TILE_JAIL_LO_DOOR1 = Game.spritesheet.getSprite(32, 96, 16, 16);
	public static BufferedImage TILE_JAIL_LO_DOOR2 = Game.spritesheet.getSprite(48, 96, 16, 16);
	public static BufferedImage TILE_JAIL_UO_DOOR1 = Game.spritesheet.getSprite(0, 144, 16, 16);
	public static BufferedImage TILE_JAIL_UO_DOOR2 = Game.spritesheet.getSprite(0, 128, 16, 16);
	public static BufferedImage TILE_BUTTON = Game.spritesheet.getSprite(32, 112, 16, 16);
	
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x=x;
		this.y=y;
		this.sprite=sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}