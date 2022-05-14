package com.magocoop.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.magocoop.main.Game;
import com.magocoop.main.Sound;
import com.magocoop.world.AStar;
import com.magocoop.world.Camera;
import com.magocoop.world.Node;
import com.magocoop.world.Tile;
import com.magocoop.world.Vector2i;

public class Prisoner extends Entity {
	
	private int frames = 0, maxFrames=20, index = 0, maxIndex=3;
	private BufferedImage[] rightPrisoner;
	private BufferedImage[] leftPrisoner;
	private BufferedImage[]	downPrisoner;
	private BufferedImage[] upPrisoner;
	
	private double sx,sy;
	private int shootTime = 0;
	private int shotMaxTime = 30;
	private boolean hasGun = false;
	private int ammo = 5;
	
	public static boolean free = false;
	
	public Prisoner(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);	
		
		
		rightPrisoner = new BufferedImage[4];
		leftPrisoner = new BufferedImage[4];
		downPrisoner = new BufferedImage[4];
		upPrisoner = new BufferedImage[4];
		
		for(int i=0; i<4; i++) {
			rightPrisoner[i] = Game.spritesheet.getSprite(32+(i*16), 80, 16, 16); 
		}
		for(int i=0; i<4; i++) {
			upPrisoner[i] = Game.spritesheet.getSprite(32+(i*16), 64, 16, 16); 
		}
		for(int i=0; i<4; i++) {
			downPrisoner[i] = Game.spritesheet.getSprite(96+(i*16), 128, 16, 16); 
		}
		for(int i=0; i<4; i++) {
			leftPrisoner[i] = Game.spritesheet.getSprite(96+(i*16), 144, 16, 16); 
		}
		
	}
	public void tick() {
		if(free) {
			//System.out.print(hasGun);
			if(hasGun) {
				if(nearSam() == false) {
					gotoSam();
//					if(y > 11*16 && x < 6*16) {
//						dir = up_dir;
//					}else if(y < 11*16) {
//						dir = right_dir;
//					}else if(y < 11*16 && x > 31*16) {
//						dir = down_dir;
//					}else {
//						dir = left_dir;
//					}
					//System.out.println("vote mata");
				}else{
					if(!nextToSam()) {
						gotoSam();
					}else {
						//System.out.println("atira");
						shootTime++;
						if(shootTime == shotMaxTime) {
							shootTime = 0;
							shooting();
						}
					}
				}

			}else if(hasGun == false){
				//System.out.println("pega arma");
				pickupGun();
				
			}
		
			//animação
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
			
		collisionEn();
		}
	}
	
	public boolean nextToSam() {
		if(this.getDistance(this.getX(), getY(), 7*16, 26*16)<100) {
			return true;
		}else return false;
	}
	
	public void collisionEn() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Gun_Locker) {
				if(Entity.isColliding(this, e)) {
				hasGun = true;
				}
			}
		}
	}
	
	public void shooting() {
		depth = 3;
		Sound.shootFX.play();
		ammo--;
		int erp = Entity.rand.nextInt(8);
		int erm = Entity.rand.nextInt(8);
		sx = 7*16 + (erp - erm);
		sy = 23*16 +(erp - erm);
		System.out.println("x: " + sx + ", y: " + sy);
		double dx = 0;
		double dy = 0;
		int px = 0;
		int py = 0;
		double angle = Math.atan2(sy - (y ) + py, sx - (x)+px);
		if(dir == right_dir) {
				px = -9; 
				py = -7;
				dx = Math.cos(angle);
				dy = Math.sin(angle);
		}else if(dir == left_dir){
				px = -2;
				py = -7;
				dx = Math.cos(angle);
				dy = Math.sin(angle);
		}else if(dir == up_dir) {
				px = -9;
				py = -4;
				dx = Math.cos(angle);
				dy = Math.sin(angle);
		}else if(dir == down_dir) {
				px = -5;
				py = -10;
				dx = Math.cos(angle);
				dy = Math.sin(angle);
		}
		
		Bullet bullet = new Bullet(this.getX()-px,this.getY()-py, 3, 3, null, dx, dy);
		
		Game.bullets.add(bullet);
	}
	
	public boolean nearSam(){
		if(this.x > 96 && this.y <464 && this.y>272) {
			return true;
		}
		else
			return false;
	}
	
	public void gotoSam() {
		if(this.getDistance(getX(), getY(), Game.player.getX(), Game.player.getY()) < 200) {
			if(this.getDistance(getX(), getY(), Game.player.getX(), Game.player.getY()) < 200) {
				if(path == null || path.size() == 0) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(7)),((int)(23)));
					path = AStar.findPath(Game.world, start, end);
				}
				if(new Random().nextInt(100) < 90)
					followPath(path);
				
				if(x % 16 == 0 && y % 16 == 0) {
					if(new Random().nextInt(100) < 10) {
						Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
						Vector2i end = new Vector2i(((int)(7)),((int)(23)));
						path = AStar.findPath(Game.world, start, end);
					}
				}
			}
		}
	}
	public void pickupGun() {
		for(int i = 0; i<Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Gun_Locker) {
				if(!hasGun) {
					if(this.getDistance(getX(), getY(), Game.player.getX(), Game.player.getY()) < 200) {
						if(path == null || path.size() == 0) {
							Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
							Vector2i end = new Vector2i(((int)(e.x/16)),((int)(e.y/16)));
							path = AStar.findPath(Game.world, start, end);
						}
						if(new Random().nextInt(100) < 90)
							followPath(path);
					
						if(x % 16 == 0 && y % 16 == 0) {
							if(new Random().nextInt(100) < 10) {
								Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
								Vector2i end = new Vector2i(((int)(e.x/16)),((int)(e.y/16)));
								path = AStar.findPath(Game.world, start, end);
							}
						}	
					}
				}else break;
//			System.out.println("x = " + e.x);
//			System.out.println("y = " + e.y);
			}	
		}	
	}
	
	public void render(Graphics g) {
		
		//g.fillRect(7*16-Camera.x, 23*16-Camera.y, 16, 32);
		
		if(dir == down_dir) {
			g.drawImage(downPrisoner[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(hasGun && ammo>0)
				g.drawImage(GUN_DOWN, this.getX() - Camera.x+1, this.getY() - Camera.y+2, null);
			
			
		}else if(dir == up_dir) {
			g.drawImage(upPrisoner[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(hasGun && ammo>0)
				g.drawImage(GUN_UP, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		if(dir == right_dir) {
			g.drawImage(rightPrisoner[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(hasGun && ammo>0)
				g.drawImage(GUN_RIGHT, this.getX() - Camera.x, this.getY() - Camera.y+2, null);
			
		}else if(dir == left_dir) {
			g.drawImage(leftPrisoner[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(hasGun && ammo>0)
				g.drawImage(GUN_LEFT, this.getX() - Camera.x, this.getY() - Camera.y+2, null);
			}
		
		}
	}

