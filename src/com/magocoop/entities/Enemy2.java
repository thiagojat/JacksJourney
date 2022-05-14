package com.magocoop.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.magocoop.main.Game;
import com.magocoop.main.Sound;
import com.magocoop.world.AStar;
import com.magocoop.world.Camera;
import com.magocoop.world.Vector2i;
import com.magocoop.world.World;

public class Enemy2 extends Entity {
	
		private int speed = 1;
		
		public int life = 25;
		
		private int frames = 0, maxFrames=20, index = 0, maxIndex=1;
		
		//private int movFrames = 50, movStage = 1, maxStages = 2;
		
		private BufferedImage[] sprites;
		
		
		public boolean isDamaged = false;
		private int currentFrames = 0, damageFrames = 8;
		
		public Enemy2(int x, int y, int width, int heigth, BufferedImage sprite) {
			super(x, y, width, heigth, null);
			
			sprites = new BufferedImage[2];
			sprites[0] = Game.spritesheet.getSprite(112, 48, World.TILE_SIZE, World.TILE_SIZE);
			sprites[1] = Game.spritesheet.getSprite(128, 48, World.TILE_SIZE, World.TILE_SIZE);
		}	
		public void tick() {
			depth = 1;
			if(this.getDistance(getX(), getY(), Game.player.getX(), Game.player.getY()) < 100) {
					if(!isCollidingWithPlayer()) {
						if(path == null || path.size() == 0) {
							Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
							Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
							path = AStar.findPath(Game.world, start, end);
						}
					}else {
						if(new Random().nextInt(100) < 5) {
							//Sound.hurtEffect.play();
							Game.player.life-=Game.rand.nextInt(7);
							Game.player.isDamaged = true;
						}
					}
				if(new Random().nextInt(100) < 50)
				followPath(path);
					
					if(x % 16 == 0 && y % 16 == 0) {
						if(new Random().nextInt(100) < 5) {
							Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
							Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
							path = AStar.findPath(Game.world, start, end);
						}
					}		
			}	
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index > maxIndex)
						index = 0;
				}
				
				bulletCollision();
				
				if(life <= 0) {
					destroyItSelf();
					return;
				}
				
				if(isDamaged) {
					this.currentFrames++;
					if(this.currentFrames == this.damageFrames) {
						this.currentFrames = 0;
						this.isDamaged = false;
					}
				}
			
		
		}
		
	public void bulletCollision(){
		for(int i = 0; i<Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
				if(Entity.isColliding(this, e)){
					life-=3;
					Game.bullets.remove(e);
					isDamaged = true;
					return;
					
				}
		}
	}

	public void destroyItSelf() {
		Sound.enemyDeathFX.play();
		Game.entities.remove(this);
		World.createParticles(100, this.getX(), this.getY());
	}
	public boolean isCollidingWithPlayer() {
		Rectangle currentEnemy = new Rectangle(this.getX(), this.getY()+masky, maskw, maskh);
		Rectangle player = new Rectangle((int)Game.player.getX(), (int)Game.player.getY(), World.TILE_SIZE, World.TILE_SIZE);
		return currentEnemy.intersects(player);
	}


		

	public void render(Graphics g) {
		if(!isDamaged)
		g.drawImage(sprites[index], this.getX()- Camera.x, this.getY()-Camera.y, null);
		else
			g.drawImage(Entity.DAMAGED_ENEMY2, this.getX()- Camera.x, this.getY()-Camera.y, null);
		g.setColor(Color.orange);
	}
			
}
