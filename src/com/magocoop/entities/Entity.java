package com.magocoop.entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.magocoop.main.Game;
import com.magocoop.world.Camera;
import com.magocoop.world.Node;
import com.magocoop.world.Vector2i;

public class Entity {
	
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(7*16, 16, 16, 16);
	public static BufferedImage NPC = Game.spritesheet.getSprite(32, 64, 16, 16);
	public static BufferedImage DAMAGED_ENEMY = Game.spritesheet.getSprite(7*16, 32, 16, 16);
	public static BufferedImage ENEMY_EN2 = Game.spritesheet.getSprite(112, 48, 16, 16);	
	public static BufferedImage DAMAGED_ENEMY2 = Game.spritesheet.getSprite(8*16, 32, 16, 16);
	public static BufferedImage UNCLESAM_EN = Game.spritesheet.getSprite(16, 128, 16, 32);
	public static BufferedImage AMMUNITION_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage AMMUNITIONBOX_EN = Game.spritesheet.getSprite(96, 32, 16, 16);
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage MEDKIT_EN = Game.spritesheet.getSprite(64, 96, 16, 16);
	public static BufferedImage WEAPON = Game.spritesheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(8*16, 0, 16, 16);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(9*16, 0, 16, 16);
	public static BufferedImage GUN_UP = Game.spritesheet.getSprite(9*16, 16, 16, 16);
	public static BufferedImage GUN_DOWN = Game.spritesheet.getSprite(9*16, 2*16, 16, 16);
	public static BufferedImage KEY_EN = Game.spritesheet.getSprite(96, 48, 16, 16);
	public static BufferedImage GUNLOCKER_EN = Game.spritesheet.getSprite(48, 112, 16, 16);
	public static BufferedImage HAT_EN = Game.spritesheet.getSprite(144, 48, 16, 16);
	
	public double x;
	public double y;
	protected int width;
	protected int height;
	
	public int depth;
	
	protected List<Node> path;
	
	protected BufferedImage sprite;
	
	protected int maskx;
	protected int masky;
	protected int maskw;
	protected int maskh;
	
	protected int down_dir = 0;
	protected int dir= down_dir;
	protected int left_dir = 2;
	protected int up_dir = 3;
	protected int right_dir = 1;
	
	public static Random rand = new Random();
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.maskw = width;
		this.maskh = height;
	}
	
	public static Comparator<Entity> entitySorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth> n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	public int getAmmo() {
			return Game.player.ammo;
			
	}
	
	public int getLife() {
		if(Game.player.life != 100)
			return (int) Game.player.life;
		else
			return 100;
	}
	
	public void setMask(int maskx, int masky, int maskw, int maskh) {
		this.maskx = maskx;
		this.masky = masky;
		this.maskw = maskw;
		this.maskh = maskh;

		
	}
	
	public void setX(int newX) {
		this.x=newX;
	}
	
	public void setY(int newY) {
		this.y=newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeigth() {
		return this.height;
	}
	
	public void tick() {
		
	}
	
	public boolean isColliding (int nextX, int nextY) {
		Rectangle currentEnemy = new Rectangle(nextX+maskx, nextY+masky, maskw, maskh);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e == this)
				continue;
			if(e instanceof Prisoner) {
			Rectangle enemyTarget = new Rectangle(e.getX()+maskx, e.getY()+masky, maskw, maskh);
				if(currentEnemy.intersects(enemyTarget)) {
					return true;
				}
			}
		}
		
		return false;
	}
	public double getDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size()>0) {
				Vector2i target = path.get(path.size()-1).tile;
				if(x<target.x*16) {
					x++;
					dir = right_dir;
				}else if(x>target.x*16) {
					x--;
					dir = left_dir;
				}if(y>target.y*16) {
					y--;
					dir = up_dir;
				}else if(y<target.y*16) {
					y++;
					dir = down_dir;
				}
				if(x == target.x *16 && y == target.y && !isColliding(this.getX() + 1, this.getY()))
					path.remove(path.size()-1);
			}
			
		}
	}
	
	public static boolean isColliding(Entity e1, Entity e2){
		Rectangle e1mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskw, e1.maskh);
		Rectangle e2mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskw, e2.maskh);
		
		return e1mask.intersects(e2mask);
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		/* ###DEBUG###
		 g.setColor(Color.red);
		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, mwidth, mheight);
		*/
		
		
	}
}