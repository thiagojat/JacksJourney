package com.magocoop.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.magocoop.main.Game;
import com.magocoop.world.Camera;
import com.magocoop.world.WallTile2;
import com.magocoop.world.World;

public class Bullet extends Entity {
	
	private double dx;
	private double dy;
	public static double spd = 4;
	
	private int life = 0, maxLife = 25;
		
	public Bullet(int x, int y, int width, int heigth, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, heigth, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	 
	public void tick() {
		depth=0;
		if(isFreeBullet((int)(x-spd),(int)(y-spd))) {
			x += dx*spd;
			y += dy*spd;
		}else if(!isFreeBullet((int)(x-spd),(int)(y-spd)))
			Game.bullets.remove(this);
		life++;
		if (life == maxLife)
			Game.bullets.remove(this);
			return;
			
	}
	
	public static boolean isFreeBullet(int nextX, int nextY) {
		int x1 = nextX / World.TILE_SIZE;
		int y1 = nextY / World.TILE_SIZE;
		
		int x2 = (nextX + World.TILE_SIZE-16)/ World.TILE_SIZE;
		int y2 = nextY / World.TILE_SIZE;
		
		int x3 = nextX / World.TILE_SIZE;
		int y3 = (nextY + World.TILE_SIZE-16)/ World.TILE_SIZE;
		
		int x4 = (nextX + World.TILE_SIZE-16) / World.TILE_SIZE;
		int y4 = (nextY + World.TILE_SIZE-16) / World.TILE_SIZE;
		
		return !((World.tiles[x1 + (y1*World.WIDHT)] instanceof WallTile2) ||
				 (World.tiles[x2 + (y2*World.WIDHT)] instanceof WallTile2) ||
				 (World.tiles[x3 + (y3*World.WIDHT)] instanceof WallTile2) ||
				 (World.tiles[x4 + (y4*World.WIDHT)] instanceof WallTile2)) ;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(getX() - Camera.x, getY() - Camera.y, width, height);
	}
	

}
