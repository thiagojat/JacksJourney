package com.magocoop.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.magocoop.main.Game;
import com.magocoop.world.AStar;
import com.magocoop.world.Camera;
import com.magocoop.world.Vector2i;



public class Npc extends Entity {
	
	private BufferedImage[] rightNpc;
	private BufferedImage[] upNpc;
	private int frames = 0, maxFrames=20, index = 0, maxIndex=3;
	private boolean moved = false;
	private int up_dir = 0;
	private int right_dir = 1;
	private int dir= up_dir;
	
	private String[] dialogue = new String[3];
	private static boolean showFMessage = false;
	public static boolean showDialogue = false;
	public static boolean showedDialogue = false;
	public static int dialInd = 1;
	public static boolean e = false;
	public static boolean f = false;
	
	
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightNpc = new BufferedImage[4] ;
		upNpc = new BufferedImage[4]; 
		for(int i=0; i<4; i++) 
			rightNpc[i] = Game.spritesheet.getSprite(32+(i*16), 80, 16, 16); 
		for(int i=0; i<4; i++) 
			upNpc[i] = Game.spritesheet.getSprite(32+(i*16), 64, 16, 16); 
		
		dialogue[0] = "Help!! Please help me!!";
		dialogue[1] = "OMG! Thank god you've arrived! Please, let me free!";
		dialogue[2] = "Yeah sure. I'm gonna find the keys.";
 		
	}
	
	public void tick() {	
		depth=2;
		if(getDistance(Game.player.getX(), Game.player.getY(), this.getX(), this.getY()) < 60) {
			if(!showedDialogue)
				showFMessage=true;
			if(e && showedDialogue == false) {
			e = false;
			showDialogue = true;
			showedDialogue = true;
	
		}
		}else
			showFMessage=false;
			if(f && showDialogue) {
				f=false;
				dialInd++;
			}	
		if(Player.unlockDoor[0]) {
			if(y>= 32) {
				y--;
				moved=true;
			}else if(x<= 640) {
				x++;
				moved=true;
				dir=right_dir;
				}
			}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}else {
			index=0;
		}
		
		
	}
	public static void restart() {
			showFMessage = false;
			showDialogue = false;
			showedDialogue = false;
			dialInd = 1;
			e = false;
			f = false;
		}
	public void render(Graphics g) {
		super.render(g);
		if(this.showFMessage) {
			Graphics2D g2 = (Graphics2D) g;
			g.setFont(Game.dial_font);
			g.setColor(Color.black);
			int xOff = 17;
			int yOff = 12+16;
			g.fillRect((35*16-Camera.x-50-2-1)+xOff, (7*16-Camera.y-10-1)+yOff, 7*16+1+2, 13+2);
			g.setColor(Color.white);
			g.fillRect((35*16-Camera.x-50-2)+xOff, (7*16-Camera.y-10)+yOff, 7*16+1, 13);
			g.setColor(Color.black);
			g.drawString(dialogue[0], (35*16-Camera.x-50)+xOff, (7*16-Camera.y)+yOff);
			g2.setColor(new Color(0,0,0,200));
			if(!showedDialogue)
			g.drawString("Press E to talk to the prisoner", 40, 155);
			if(showDialogue) {
				// Jake sprite Game.dialspritesheet.getSprite(18, 8, 65, 91)
			 	showedDialogue = true;
			 	g2.setColor(new Color(0,0,0,200));
			 	if(dialInd == 1) {
			 		//npc
			 		g.drawImage(Game.dialspritesheet.getSprite(18, 8, 65, 91), 175, 80, null);
			 		g2.setColor(new Color(0,0,0,200));
			 		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.WIDTH*Game.SCALE);
			 		g.drawImage(Game.dialspritesheet.getSprite(120, 8, 77, 91), 0, 80, null);
			 		g.drawImage(Game.dialspritesheet.getSprite(19, 114, 160, 43), 65, 114, null);
			 		g.drawString(dialogue[dialInd].substring(0, 21), 65+20, 114+12);
			 		g.drawString(dialogue[dialInd].substring(22, 46), 65+20, 114+12+10);
			 		g.drawString(dialogue[dialInd].substring(46, 51), 65+20, 114+12+10+10);
			 		g2.setColor(new Color(0,0,0,80));
			 		g.drawString("Press F to continue", 65+40, 114+12+30);
			 	}else if(dialInd == 2) {
			 		///jack
			 		g.drawImage(Game.dialspritesheet.getSprite(120, 8, 77, 91), 0, 80, null);
			 		g2.setColor(new Color(0,0,0,200));
			 		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.WIDTH*Game.SCALE);
			 		g.drawImage(Game.dialspritesheet.getSprite(217, 16, 160, 43), 25, 114, null);
			 		g.drawImage(Game.dialspritesheet.getSprite(18, 8, 65, 91), 175, 80, null);
			 		g.drawString(dialogue[dialInd].substring(0, 25), 65-33, 114+12);
			 		g.drawString(dialogue[dialInd].substring(26, 35), 65-33, 114+12+10);
			 		g2.setColor(new Color(0,0,0,80));
			 		g.drawString("Press F to continue", 65-20, 114+12+30);
			 	}
			 }	
		}	
			//ANIMAÇÃO ANDAR//
			 	if(dir == up_dir) {
					g.drawImage(upNpc[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			 	}
			 	if(dir == right_dir) {
					g.drawImage(rightNpc[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			 	}
		
		
	}
}	