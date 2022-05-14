package com.magocoop.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.magocoop.main.Game;
import com.magocoop.main.Sound;
import com.magocoop.world.Camera;
import com.magocoop.world.Tile;
import com.magocoop.world.World;

public class Player extends Entity{
	
	public boolean up, down, left, rigth;
	
	private int down_dir = 0;
	private int dir= down_dir;
	private int left_dir = 2;
	private int up_dir = 3;
	private int right_dir = 1;
	private boolean moved;
	
	public double speed=1.2;
	
	private int frames = 0, maxFrames=20, index = 0, maxIndex=3;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[]	downPlayer;
	private BufferedImage[] upPlayer;
	
	private BufferedImage[] damagedPlayer;
	
	public double life = 100, maxLife=100;
	
	public int mx,my;
	
	public int ammo = 0, maxAmmo=48;
	
	public boolean isDamaged = false;
	private int damageFrames = 0;
	
	
	public boolean punch = false;
	public boolean isPunching = false;
	private int punchCurFrames = 0;
	private int punchMaxFrames = 20;
	
	public BufferedImage[] punchL;
	public BufferedImage[] punchR;
	public BufferedImage[] punchD;
	public BufferedImage[] punchU;
	
	private int punchAnFrames = 0;
	private int punchMaxAnFrames = 2;
	private int punchAnInd = 0;
	
	public boolean hasGun = false;
	public boolean hasKey = false;	
	public boolean shoot = false, mouseShoot = false;
	
	public boolean unlockmessage = false;
	public boolean e = false;
	
	public boolean buttonpressed = false;
	
	public static int[] jailX = new int[5];
	public static int[] jailY = new int[5];
	public static boolean[] unlockDoor = new boolean[5];
	
	
	
	public Player(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		
		for(int i=0; i<4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 16, 16, 16); 
		}
		for(int i=0; i<4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 0, 16, 16);
		}
		for(int i=0; i<4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 32, 16, 16); 
		}
		for(int i=0; i<4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(32+(i*16), 48, 16, 16); 
		}
		
		damagedPlayer = new BufferedImage[3];
		//left_dir damage
		damagedPlayer[0] = Game.spritesheet.getSprite(0, 16, World.TILE_SIZE, World.TILE_SIZE);
		//
		//up/down_dir damage
		damagedPlayer[1] = Game.spritesheet.getSprite(16, 16, World.TILE_SIZE, World.TILE_SIZE);
		//
		//right_dir damage
		damagedPlayer[2] = Game.spritesheet.getSprite(0, 32, World.TILE_SIZE, World.TILE_SIZE);
		
		punchL = new BufferedImage[4];
		punchR = new BufferedImage[4];
		punchD = new BufferedImage[4];
		punchU = new BufferedImage[4];
		for(int i=0; i<punchR.length; i++) {
			punchR[i] = Game.spritesheet.getSprite(96+(i*16), 64, 16,16);
		}
		for(int i=0; i<punchL.length; i++) {
			punchL[i] = Game.spritesheet.getSprite(96+(i*16), 80, 16,16);
		}
		for(int i=0; i<punchD.length; i++) {
			punchD[i] = Game.spritesheet.getSprite(96+(i*16), 96, 16,16);
		}
		for(int i=0; i<punchU.length; i++) {
			punchU[i] = Game.spritesheet.getSprite(96+(i*16), 112, 16,16);
		}
	}
	
	
	
	public void tick() {
		//life = 100;
		if(Game.CUR_LEVEL > 2)
			hasGun = true;
		depth=2;
		moved = false;
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			moved = true;
			dir = up_dir;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}	
		if(left && World.isFree((int)(x-speed), this.getY())){
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		else if(rigth && World.isFree((int)(x+speed), this.getY())){
			moved = true;
			dir = right_dir;
			x+=speed;
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
		}else
			index = 0;
		
		if(life<=0) {
			//game over
			life=0;
			Game.gameStatus = "GAME_OVER";
			return;

		}
	
		if(shoot) {
			shoot = false;
			if(hasGun && ammo>0) {
				Sound.shootFX.play();
				//System.out.println("Atirou");
				ammo--;
				int dx = 0;
				int dy = 0;
				int px = 0;
				int py = 0;
				
					if(dir == right_dir) {
							px = -9; 
							py = -7;
							dx = 1;
					}else if(dir == left_dir){
							px = -2;
							py = -7;
							dx = -1;
					}else if(dir == up_dir) {
							px = -9;
							py = -4;
							dy = -1;
					}else if(dir == down_dir) {
							px = -5;
							py = -10;
							dy = 1;
					}
				
				Bullet bullet = new Bullet(this.getX()-px,this.getY()-py, 3, 3, null, dx, dy);
				Game.bullets.add(bullet);
				//shoot = false;
			}
		}
		
		if(mouseShoot) {
			mouseShoot = false;
			if(hasGun && ammo>0) {
				//System.out.println("POW");
				Sound.shootFX.play();
				ammo--;
				
				double dx = 0;
				double dy = 0;
				int px = 0;
				int py = 0;
				double angle = Math.atan2(my - (this.getY() - Camera.y ) + py, mx - (this.getX() - Camera.x)+px);
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
				//shoot = false;
			}
			
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDHT*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGTH/2), 0, World.HEIGTH*16 - Game.HEIGTH);		
		
		isColiddingEntities();
		
		if(isDamaged) {
			//System.out.println("2x: " + getX() + " 2y: " + getY());
			this.damageFrames++;
			if(this.damageFrames>8) {
				damageFrames=0;
				isDamaged=false;
			}

		}
		
		jailX[0] = 35*16; jailY[0] =6*16;
		if(Game.CUR_LEVEL!=3) {
			unlockDoor[0]=false;
			
		}	
		for(int d = 0; d<= jailY.length-1; d++) {
			if(this.getDistance(getX(), getY(), jailX[0], jailY[0])<20 && Npc.showedDialogue && hasKey&&!unlockDoor[0]) {
				unlockmessage = true;
				if(getDistance(getX(), getY(), jailX[0], jailY[0])>20)
					e=false;
				if(hasKey) {
					if(e) {
						
					e=false;
					unlockDoor[0] = true;
					if(unlockDoor[0] = true) {
						//System.out.println("libara");
						System.out.println("x: "+ jailX[0]);
						System.out.println("y: "+ jailY[0]);
						}
					}
				}
			}else {
				unlockmessage = false;
			}
		}
			nextLevel();
			if(ammo==0 || !hasGun) {
				if(punch) {
					
					punch = false;
					if(isPunching == false) {
						isPunching = true;
					}
				}
				if(isPunching) {
					//animaçao
					punchAnFrames++;
					if(punchAnFrames >punchMaxAnFrames) {
						punchAnInd++;
						punchAnFrames = 0;
					}
					if(punchAnInd > (punchL.length-1)) {
						punchAnInd = 0;
						punchAnFrames = 0;
						isPunching =false;
					}
					//logica
					punchCurFrames++;
					if(punchCurFrames > punchMaxFrames) {
						punchCurFrames = 0;
						isPunching = false;
					}
					
					//collision
					for(int i = 0; i < Game.entities.size(); i++) {
						Entity e = Game.entities.get(i);
						if(e instanceof Enemy) {
							if(Entity.isColliding(this, e)) {
								if(Entity.rand.nextInt(100)<30) {
									((Enemy)e).life--;
									((Enemy)e).isDamaged=true;
								}
									break;
							}
						}
						if(e instanceof Enemy2) {
							if(Entity.isColliding(this, e)) {
								if(Entity.rand.nextInt(100)<30) {
									((Enemy2)e).life--;
									((Enemy2)e).isDamaged=true;
								}
									break;
							}	
						}
					}
				}
			}else {
				punchAnInd = 0;
				punchAnFrames = 0;
				isPunching =false;
			}
			
			if(buttonpressed) {
				Prisoner.free = true;
			}
	}
	
	public boolean nearSam(){
		if(this.x > 96 && this.y <464 && this.y>272) {
			return true;
		}
		else
			return false;
	}
	
	public boolean buttonAvaliable() {
		if(this.x < 624 && this.x > 572 && this.y >576 && this.y<608 && Game.CUR_LEVEL == 4)
			return true;
		else return false;
	}
	
	public boolean nextLevel(){
		int xDoor = 0;
		int yDoor = 0;
		int wDoor = 0;
		int hDoor = 0;
		if(Game.CUR_LEVEL==1) {
			xDoor = 400-Camera.x;
			yDoor = 460-Camera.y;
			wDoor = 64;
			hDoor = 10;
		}else if(Game.CUR_LEVEL == 2) {
			xDoor = 16-Camera.x;
			yDoor = 0-Camera.y;
			wDoor = 10;
			hDoor = 64;
		}else if(Game.CUR_LEVEL == 3) {
			xDoor = 16-Camera.x;
			yDoor = 623-Camera.y;
			wDoor = 96;
			hDoor = 10;
		}
		
		
		Rectangle player = new Rectangle((int)this.getX() - Camera.x, (int)this.getY() - Camera.y, World.TILE_SIZE, World.TILE_SIZE);
		Rectangle nextlevelDoor = new Rectangle(xDoor, yDoor, wDoor, hDoor);			
		return player.intersects(nextlevelDoor);
		
	}
	
	
	
	public void isColiddingEntities() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof LifePack) {
				if(Entity.isColliding(this, e)) {
					if(life>=100)
						return;
					if(life<=100) {
						Sound.pickupPackFX.play();
						life += 10;
					if(life > 100)
							life=100;
					Game.entities.remove(i);
					
					return;
					}	
				}		
			}
			if(e instanceof MedKit) {
				if(Entity.isColliding(this, e)) {
					if(life>=100)
						return;
					if(life<=100) {
						Sound.pickupPackFX.play();
						life += 30;
					if(life > 100)
							life=100;
					Game.entities.remove(i);
					
					return;
					}	
				}		
			}
			if(e instanceof Ammunition) {
				if(Entity.isColliding(this, e)) {
					if(ammo >= maxAmmo || ammo+8 >= maxAmmo) {
						ammo = maxAmmo;
						return;
					}
					Sound.pickupAmmoFX.play();
					ammo+=8;	
					Game.entities.remove(i);
					//System.out.println(ammo);
					return;
				}
			}
			if(e instanceof Ammo_Box) {
				if(Entity.isColliding(this, e)) {
					if(ammo >= maxAmmo) {
						ammo = maxAmmo;
						return;
					}
					Sound.pickupAmmoFX.play();
					ammo = maxAmmo;	
					Game.entities.remove(i);
					//System.out.println(ammo);
					return;
				}
			}
			if(e instanceof DroppedAmmo) {
				if(Entity.isColliding(this, e)) {
					if(ammo >= maxAmmo || ammo+3 >= maxAmmo) {
						ammo = maxAmmo;
						return;
					}
					Sound.pickupAmmoFX.play();
					ammo +=3;	
					Game.entities.remove(i);
					//System.out.println(ammo);
					return;
				}
			}
			
			if(e instanceof Weapon) {
				if(Entity.isColliding(this, e)) {
					if(hasGun) 
						return;
				hasGun = true;
				Sound.pickupWeaponFX.play();
				Game.entities.remove(i);
				//aaSystem.out.println("Vc tem uma arma");
					return;
				}
			}

			if(e instanceof Key) {
				if(Entity.isColliding(this, e)) {
					hasKey = true;
				Sound.pickupPackFX.play();
				Game.entities.remove(i);
					return;
				}
			}
		}
		
	}	
		
	
	public void render(Graphics g) {
		/*int xDoor1 = 16-Camera.x;
		int yDoor1 = 384-Camera.y+224+15;
		g.fillRect(xDoor1, yDoor1, 96, 10);*/
		//g.fillRect(World.jailX[0]-Camera.x, World.jailY[0]-Camera.y, 16, 16);
		if(buttonAvaliable() && buttonpressed == false) {
			//System.out.println("aa");
			g.setFont(Game.dial_font);
			g.setColor(Color.black);
			g.drawString("Press E to free your allies", 60, 155);
		}
		
		if(unlockDoor[0]) {
			g.drawImage(Tile.TILE_JAIL_UO_DOOR1, jailX[0]-Camera.x, jailY[0]-Camera.y, null);
			g.drawImage(Tile.TILE_JAIL_UO_DOOR2, jailX[0]-Camera.x, jailY[0]-16-Camera.y, null);
		}
		if(unlockmessage) {
			g.setFont(Game.dial_font);
			g.setColor(Color.BLACK);
			g.drawString("Press E to open the cell", 60, 155);
		}
		if(dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(isDamaged)
				g.drawImage(damagedPlayer[1], this.getX()-Camera.x, this.getY()-Camera.y, null);
			if(hasGun && ammo>0)
				g.drawImage(GUN_DOWN, this.getX() - Camera.x+1, this.getY() - Camera.y+2, null);
			if(isPunching) {
				g.drawImage(punchD[punchAnInd], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			
		}else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(isDamaged)
				g.drawImage(damagedPlayer[1], this.getX()-Camera.x, this.getY()-Camera.y, null);
			if(hasGun && ammo>0)
				g.drawImage(GUN_UP, this.getX() - Camera.x, this.getY() - Camera.y, null);
			if(isPunching) {
				g.drawImage(punchU[punchAnInd], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
		
		if(dir == right_dir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(isDamaged)
				g.drawImage(damagedPlayer[2], this.getX()-Camera.x, this.getY()-Camera.y, null);
			if(hasGun && ammo>0)
				g.drawImage(GUN_RIGHT, this.getX() - Camera.x, this.getY() - Camera.y+2, null);
			if(isPunching) {
				g.drawImage(punchR[punchAnInd], this.getX() - Camera.x+16-3, this.getY() - Camera.y, null);
			}
		}else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y,null);
			if(isDamaged)
				g.drawImage(damagedPlayer[0], this.getX()-Camera.x, this.getY()-Camera.y, null);
			if(hasGun && ammo>0)
				g.drawImage(GUN_LEFT, this.getX() - Camera.x, this.getY() - Camera.y+2, null);
			if(isPunching) {
				g.drawImage(punchL[punchAnInd], this.getX() - Camera.x-16+6, this.getY() - Camera.y, null);
			}
		}
		
	}

	
}
