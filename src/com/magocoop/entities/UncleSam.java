package com.magocoop.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.magocoop.main.Game;
import com.magocoop.main.Sound;
import com.magocoop.world.Camera;

public class UncleSam extends Entity {
	
	public int hatBreak = 0;
	public int hatMaxBreak;
	public boolean throwHat = false;
	
	private boolean isTalking = false;
	private int talkFrames = 0;
	private int talkmaxFrames;
	private boolean openMouth = false;
	private boolean showBal = true;
	
	public double life = 200;
	
	private boolean isDamaged = false; 
	
	public BufferedImage openedMouth;
	public BufferedImage damaged;
	
	private int damageFrames = 8;
	private int currentFrames;
	
	private boolean showSamLife=false;
	
	public static boolean withoutHat = false;
	private BufferedImage withoutHatIm;
	
	//private int cutSceneFrames = 0;
	//private int curCu

	public UncleSam(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		hatMaxBreak = Entity.rand.nextInt(300-180)+180;
		talkmaxFrames = Entity.rand.nextInt(15-10)+10;
		
		openedMouth = Game.spritesheet.getSprite(48, 128, 16, 32);
		damaged = Game.spritesheet.getSprite(32, 128, 16, 32);
		withoutHatIm = Game.spritesheet.getSprite(64, 128, 16, 32);
	
		
	}
	
	public void tick() {
		depth = 3;
		if(life <= 50) {
			for (int i = 0; i< Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Player) {
					Game.coms.add(e);
				}if(e instanceof Prisoner) {
					Game.coms.add(e);
				}
			
				if(getDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY())<200 && Game.player.nearSam()) {
					int ii = Entity.rand.nextInt(Game.coms.size());
					Entity ee = Game.coms.get(ii);
					hatBreak++;
					//System.out.println("ta pertod");
					if(hatBreak == hatMaxBreak) {
						System.out.println("taca chapeu");
						hatBreak = 0;
						//logica do chapeu
						double dx = 0;
						double dy = 0;
						int px = 0;
						int py = 0;
						double angle = Math.atan2(ee.getY() - (this.getY()) + py, ee.getX() - (this.getX())+px);
						dx = Math.cos(angle);
						dy = Math.sin(angle);
					
						uncleSamHat hat = new uncleSamHat(this.getX()+10-px,this.getY()-py, 14, 14, Entity.HAT_EN, dx, dy);
						Game.hats.add(hat);
						this.withoutHat = true;
						Sound.hatFX.play();
						//System.out.println("1x: " + Game.player.getX() + " 1y: " + Game.player.getY());
					}
				}
			}
		}else {
			for (int i = 0; i< Game.entities.size(); i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Player) {
					Game.coms.add(e);
				}
			}
			if(getDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY())<200 && Game.player.nearSam()) {
				int i = Entity.rand.nextInt(Game.coms.size());
				Entity e = Game.coms.get(i);
				hatBreak++;
				//System.out.println("ta pertod");
				if(hatBreak == hatMaxBreak) {
					System.out.println("taca chapeu");
					hatBreak = 0;
					//logica do chapeu
					double dx = 0;
					double dy = 0;
					int px = 0;
					int py = 0;
					double angle = Math.atan2(e.getY() - (this.getY()) + py, e.getX() - (this.getX())+px);
					dx = Math.cos(angle);
					dy = Math.sin(angle);
				
					uncleSamHat hat = new uncleSamHat(this.getX()+10-px,this.getY()-py, 14, 14, Entity.HAT_EN, dx, dy);
					Game.hats.add(hat);
					this.withoutHat = true;
					Sound.hatFX.play();
					//System.out.println("1x: " + Game.player.getX() + " 1y: " + Game.player.getY());
				}
			}
		}
		
		if(Game.SAMcutSceneMove) {
			showBal = false;
			if(y < 23*16) {
				y++;
				if(y>208) {
					Game.isplaying = true;
					showSamLife = true;
				}	
			}else if(x >7*16) {
				x--;
			}
		}
		
		if(Game.dialSam[0] = true) {
			this.isTalking = true;
		}else {
			isTalking = false;
		}
		
		
		if(isTalking) {
			if(openMouth) {
				this.talkFrames++;
				if(talkFrames == this.talkmaxFrames) {
					//System.out.println("fecha");
					talkFrames = 0;
					openMouth = false;
				}
			}else if(!openMouth) {
				this.talkFrames++;
				if(talkFrames == this.talkmaxFrames) {
					//System.out.println("abre");
					talkFrames = 0;
					openMouth = true;
				}
			}	
		}
		
		bulletCollision();
		if(isDamaged) {
		
			this.currentFrames++;
			if(this.currentFrames == this.damageFrames) {
				this.currentFrames = 0;
				this.isDamaged = false;
			}
		}
		if(life <= 0) {
			Game.gameStatus = "WIN";
		}
	}
	public void bulletCollision(){
		for(int i = 0; i<Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
				if(Entity.isColliding(this, e)){
					//System.out.println("" + life);
					life-=2;
					Game.bullets.remove(e);
					isDamaged = true;
					return;
					
				}
		}
	}
	
	public void render(Graphics g) {
		//sprite = Game.spritesheet.getSprite(16, 128, 16, 32);
		super.render(g);
		if(this.withoutHat) {
			sprite = withoutHatIm;
			//System.out.println("careca");
		}else
			sprite = Entity.UNCLESAM_EN;
		if(Game.dialSam[0] == true && !Game.isplaying && showBal) {
			//System.out.println("dial");
			int xoff = -60;
			int yoff = 0;
			g.fillRect(this.getX()+xoff, this.getY()+yoff, 10, 10);
			g.drawImage(Game.dialspritesheet.getSprite(217, 64, 64, 17), this.getX()+xoff-Camera.x-4, this.getY()+yoff-Camera.y-10, null);
			g.setFont(Game.dial_font); g.setColor(Color.black);
			g.drawString("KILL HIM!!", this.getX()+xoff-Camera.x, this.getY()+yoff-Camera.y);
		}	
		if(openMouth == true && !Game.isplaying && showBal) {
			g.drawImage(openedMouth, (int)this.x-Camera.x,(int) this.y-Camera.y, null);
		}else {
			//System.out.println("b");
		}
		if(isDamaged) {
			g.drawImage(damaged, this.getX()-Camera.x, this.getY()-Camera.y, null);
			
		}
		if(showSamLife == true && Game.player.nearSam() == true) {
			int positionX = 20;
			int positionY = Game.HEIGTH - 15;
			g.setColor(Color.black);
			g.fillRect(positionX, positionY, 200+2, 12);
			g.setColor(Color.red);
			g.fillRect(positionX+1, positionY+1, 200, 10);
			g.setColor(Color.GREEN);
			g.fillRect(positionX+1, positionY+1, (int)((life/200)*200), 10);
//			g.setColor(Color.white);
//			g.setFont(new Font("arial", Font.BOLD, 9));
//			g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, (int)(lifeAmount/2)-8, 16);
		}
	}

}
