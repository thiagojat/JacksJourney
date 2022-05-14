package com.magocoop.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.magocoop.main.Game;
import com.magocoop.world.Camera;
import com.magocoop.world.WallTile2;
import com.magocoop.world.World;

public class uncleSamHat extends Entity{
	
	private double dx;
	private double dy;
	public static double spd = 3;
	
	private int duration =0;
	private int maxDuration =50;
	
	public boolean returnHat = false;

	public uncleSamHat(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void tick() {
		depth=0;
		setMask(2,4,13,9);
		if(isFreeHat((int)(x-spd),(int)(y-spd))) {
			//vai ao objetivo
			//System.out.println("anda");
			x += dx*spd;
			y += dy*spd;
		}
		if(!returnHat) {
			if(!isFreeHat((int)(x-spd),(int)(y-spd)))
				//se bater, retorna
				returnHat = true;
			
			for(int i = 0; i<Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Player) {
					if(isColliding(this, e)) {
						Game.player.life-= 20;
						returnHat = true;
						Game.player.isDamaged = true;
					}
				}		
			}
			duration++;
			if(duration > maxDuration) {
				duration = 0;
				returnHat = true;
			}
		}else {
			int py = 368 - Camera.y;
			int px = 112 - Camera.x;
			double angle = Math.atan2(py - (this.getY()-Camera.y), px - (this.getX() - Camera.x));
			dx = Math.cos(angle);
			dy = Math.sin(angle);
			//System.out.println("x: " + px + " y: " + py );
			
			if(returnHat) {
				for(int i = 0; i<Game.entities.size(); i++) {
					Entity e = Game.entities.get(i);
					if(e instanceof UncleSam) {
						if(isColliding(this, e)) {
							Game.hats.remove(this);
							Game.sam.withoutHat = false;
							
						}
						
					}		
				}
				
			}
		}
		
	}
	
	public static boolean isFreeHat(int nextX, int nextY) {
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
	
	
//	public void render(Graphics g) {
//		sprite = Game.spritesheet.getSprite(144, 48, 16, 16);
//		g.fillRect(this.getX(), this.getY(), 10, 10);
//	}

}
//package com.magocoop.entities;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//
//import com.magocoop.main.Game;
//import com.magocoop.world.Camera;
//import com.magocoop.world.WallTile2;
//import com.magocoop.world.World;
//
//public class uncleSamHat extends Entity {
//	
//	private double dx;
//	private double dy;
//	public static double spd = 4;
//	
//	private int life = 0, maxLife = 25;
//		
//	public uncleSamHat(int x, int y, int width, int heigth, BufferedImage sprite, double dx, double dy) {
//		super(x, y, width, heigth, sprite);
//		this.dx = dx;
//		this.dy = dy;
//	}
//	 
//	public void tick() {
//		depth=0;
//		if(isFreeBullet((int)(x-spd),(int)(y-spd))) {
//			x += dx*spd;
//			y += dy*spd;
//		}else if(!isFreeBullet((int)(x-spd),(int)(y-spd)))
//			Game.hats.remove(this);
//		life++;
//		if (life == maxLife)
//			Game.hats.remove(this);
//			return;
//			
//	}
//	
//	public static boolean isFreeBullet(int nextX, int nextY) {
//		int x1 = nextX / World.TILE_SIZE;
//		int y1 = nextY / World.TILE_SIZE;
//		
//		int x2 = (nextX + World.TILE_SIZE-16)/ World.TILE_SIZE;
//		int y2 = nextY / World.TILE_SIZE;
//		
//		int x3 = nextX / World.TILE_SIZE;
//		int y3 = (nextY + World.TILE_SIZE-16)/ World.TILE_SIZE;
//		
//		int x4 = (nextX + World.TILE_SIZE-16) / World.TILE_SIZE;
//		int y4 = (nextY + World.TILE_SIZE-16) / World.TILE_SIZE;
//		
//		return !((World.tiles[x1 + (y1*World.WIDHT)] instanceof WallTile2) ||
//				 (World.tiles[x2 + (y2*World.WIDHT)] instanceof WallTile2) ||
//				 (World.tiles[x3 + (y3*World.WIDHT)] instanceof WallTile2) ||
//				 (World.tiles[x4 + (y4*World.WIDHT)] instanceof WallTile2)) ;
//	}
//	
//	public void render(Graphics g) {
//		g.setColor(Color.yellow);
//		g.fillOval(getX() - Camera.x, getY() - Camera.y, width, height);
//	}
//	
//
//}

