package com.magocoop.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.magocoop.entities.Ammo_Box;
import com.magocoop.entities.Ammunition;
import com.magocoop.entities.Enemy;
import com.magocoop.entities.Enemy2;
import com.magocoop.entities.Entity;
import com.magocoop.entities.Gun_Locker;
import com.magocoop.entities.Key;
import com.magocoop.entities.LifePack;
import com.magocoop.entities.MedKit;
import com.magocoop.entities.Npc;
import com.magocoop.entities.Particle;
import com.magocoop.entities.Player;
import com.magocoop.entities.Prisoner;
import com.magocoop.entities.UncleSam;
import com.magocoop.entities.Weapon;
import com.magocoop.entities.uncleSamHat;
import com.magocoop.graficos.Spritesheet;
import com.magocoop.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDHT, HEIGTH;
	public static final int TILE_SIZE = 16;
		
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getHeight()*map.getWidth()];
			WIDHT= map.getWidth();
			HEIGTH= map.getHeight();
			tiles = new Tile[map.getHeight()*map.getWidth()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
				int pixelAtual = pixels[xx + (yy * map.getWidth())];
				if(Game.CUR_LEVEL == 1)
					tiles[xx + (yy * WIDHT)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
				else
					tiles[xx + (yy * WIDHT)] = new FloorTile(xx*16, yy*16, Tile.TILE_IN_FLOOR);
				if(pixelAtual == 0xFF000000){	
					//Floor
					tiles[xx + (yy * WIDHT)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
				}else if(pixelAtual == 0xFF363535){
					//In_Floor
					tiles[xx + (yy * WIDHT)] = new FloorTile(xx*16,yy*16,Tile.TILE_IN_FLOOR);
				}else if(pixelAtual == 0xFFc5c5c5){
					//Wall
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16,yy*16,Tile.TILE_WALL);
				}else if(pixelAtual == 0xFF0b6669) {
					//Grid
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16, yy*16, Tile.TILE_GRID);
				}else if(pixelAtual == 0xFF314a4b) {
					//Grid_ver_lef
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16, yy*16, Tile.TILE_GRID_VL);
				}else if(pixelAtual == 0xFF5a6a6b) {
					//Grid_ver_rig
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16, yy*16, Tile.TILE_GRID_VR);
				}else if(pixelAtual == 0xFF80dbde) {
					//Grid_cor_lef
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16, yy*16, Tile.TILE_GRID_CL);
				}else if(pixelAtual == 0xFF8fabac) {
					//Grid_cor_rigt
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16, yy*16, Tile.TILE_GRID_CR);
				}else if(pixelAtual == 0xFF645310) {
					//GlassDoor
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16, yy*16, Tile.TILE_GLASS_DOOR);
				}else if(pixelAtual == 0xFF3e4542) {
					//JailDoor
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16, yy*16, Tile.TILE_JAIL_DOOR);
				}else if(pixelAtual == 0xFF6c7a74) {
					//JailDoorreta
					tiles[xx + (yy * WIDHT)] = new WallTile2(xx*16, yy*16, Tile.TILE_JAIL_DOOR1);
				}else if(pixelAtual == 0xFF97c9b3) {
					//porrta
					tiles[xx + (yy * WIDHT)] = new Cell_Door(xx*16, yy*16, Tile.TILE_JAIL_DOOR1);
				}else if(pixelAtual == 0xFF899590) {
					//JailDoor left opened 1
					tiles[xx + (yy * WIDHT)] = new FloorTile(xx*16, yy*16, Tile.TILE_JAIL_LO_DOOR1);
				}else if(pixelAtual == 0xFF656d6a) {
					//JailDoor left opened 2
					tiles[xx + (yy * WIDHT)] = new GridTile(xx*16, yy*16, Tile.TILE_JAIL_LO_DOOR2);
				}else if(pixelAtual == 0xFF4b3002) {
					//Mesa
					tiles[xx + (yy * WIDHT)] = new GridTile(xx*16, yy*16, Tile.TILE_TABLE);
				}else if(pixelAtual == 0xFF4b3001) {
					//Mesa com pc
					tiles[xx + (yy * WIDHT)] = new GridTile(xx*16, yy*16, Tile.TILE_TABLE_COM);
				}else if(pixelAtual == 0xFFff7272) {
					//botao
					tiles[xx + (yy * WIDHT)] = new GridTile(xx*16, yy*16, Tile.TILE_BUTTON);
				}else if(pixelAtual == 0xFF0024ff) {
					//Player
					Game.player.setX(xx*16);
					Game.player.setY(yy*16);
					if(Game.CUR_LEVEL == 1)
						tiles[xx + (yy * WIDHT)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					else
						tiles[xx + (yy * WIDHT)] = new FloorTile(xx*16, yy*16, Tile.TILE_IN_FLOOR);
				}else if(pixelAtual == 0xFF8400ff) {
					//Sam
					UncleSam sam = new UncleSam(xx*16, yy*16, 14,32,Entity.UNCLESAM_EN);
					Game.entities.add(sam);
					//System.out.println("imperialismo");
				}else if(pixelAtual== 0xFFff000b){
					//Enemy
					Enemy en = new Enemy(xx*16, yy*16, 16,16,Entity.ENEMY_EN);
					Game.entities.add(en);
					Game.enemies.add(en);
				}else if(pixelAtual== 0xFF5f2022){
					//Enemy2
					Enemy2 en2 = new Enemy2(xx*16, yy*16, 16,16,Entity.ENEMY_EN2);
					Game.entities.add(en2);
					Game.enemies2.add(en2);
				}else if(pixelAtual== 0xFF5561a9){
					//Prisoners
					Prisoner pris = new Prisoner(xx*16, yy*16, 16,16,Entity.NPC);
					Game.entities.add(pris);
					Game.prisoners.add(pris);
					Game.coms.add(pris);
				}else if(pixelAtual== 0xFF4549ce){
					//Npc
					Npc npc = new Npc(xx*16, yy*16, 16,16,Entity.NPC);
					Game.entities.add(npc);
					Game.npcs.add(npc);
				}else if(pixelAtual== 0xFFffde00) {
					//Ammunition
					Ammunition ammo = new Ammunition(xx*16, yy*16, 16,16,Entity.AMMUNITION_EN);
					ammo.setMask(5, 5, 5, 11);
					Game.entities.add(ammo);
				}else if(pixelAtual== 0xFFc1a90b) {
					//Key
					Key key = new Key(xx*16, yy*16, 16,16,Entity.KEY_EN);
					key.setMask(5, 5, 5, 11);
					Game.entities.add(key);
				}else if(pixelAtual== 0xFF054e30) {
					//AmmunitionBOX
					Ammo_Box ammoBox = new Ammo_Box(xx*16, yy*16, 16,16,Entity.AMMUNITIONBOX_EN);
					ammoBox.setMask(5, 5, 5, 11);
					Game.entities.add(ammoBox);
				}else if(pixelAtual == 0xFF00ff1e) {
					//Life-Pack
					LifePack lp = new LifePack(xx*16, yy*16, 16,16,Entity.LIFEPACK_EN);
					lp.setMask(3, 5, 11, 11);
					Game.entities.add(lp);
				}else if(pixelAtual == 0xFF47f0ce) {
					//MedKit
					MedKit mk = new MedKit(xx*16, yy*16, 16,16,Entity.MEDKIT_EN);
					mk.setMask(1, 1, 15, 15);
					Game.entities.add(mk);
				}else if(pixelAtual == 0xFF747474) {
					//Weapon
					Game.entities.add(new Weapon(xx*16, yy*16, 16,16,Entity.WEAPON));
				}else if(pixelAtual == 0xFF1b2f10) {
					//LOCKER STORAGE
					Game.entities.add(new Gun_Locker(xx*16, yy*16, 16,16,Entity.GUNLOCKER_EN));
				}
			}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createParticles(int amount, int x, int y) {
		for(int i = 0; i<amount; i++)
			Game.entities.add(new Particle(x, y, 1, 1, null));
	}
	
	
	public static boolean isFree(int nextX, int nextY) {
		int x1 = nextX / TILE_SIZE;
		int y1 = nextY / TILE_SIZE;
		
		int x2 = (nextX + TILE_SIZE-1)/ TILE_SIZE;
		int y2 = nextY / TILE_SIZE;
		
		int x3 = nextX / TILE_SIZE;
		int y3 = (nextY + TILE_SIZE-1)/ TILE_SIZE;
		
		int x4 = (nextX + TILE_SIZE-1) / TILE_SIZE;
		int y4 = (nextY + TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDHT)] instanceof WallTile2 ||  
				tiles[x1 + (y1*World.WIDHT)] instanceof GridTile||  
				tiles[x1 + (y1*World.WIDHT)] instanceof Cell_Door) ||
				(tiles[x2 + (y2*World.WIDHT)] instanceof WallTile2 || 
				tiles[x2 + (y2*World.WIDHT)] instanceof GridTile || 
				tiles[x2 + (y2*World.WIDHT)] instanceof Cell_Door) ||
				(tiles[x3 + (y3*World.WIDHT)] instanceof WallTile2 || 
				tiles[x3 + (y3*World.WIDHT)] instanceof GridTile || 
				tiles[x3 + (y3*World.WIDHT)] instanceof Cell_Door) ||
				(tiles[x4 + (y4*World.WIDHT)] instanceof WallTile2 || 
				tiles[x4 + (y4*World.WIDHT)] instanceof GridTile || 
				tiles[x4 + (y4*World.WIDHT)] instanceof Cell_Door)) ;
	}
	
	public static void restartGame(String level) {
		Game.entities= new ArrayList<Entity>();
		Game.hats = new ArrayList<uncleSamHat>();
		Game.prisoner.free = false;
		Game.enemies = new ArrayList<Enemy>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0,0,16,16, Game.spritesheet.getSprite(32,16,16,16));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
		Npc.restart();
		Game.player.unlockDoor[0] = false;
		Game.cutScene[0] = false;
		Game.isplaying = false;
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGTH >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || xx >= WIDHT || yy < 0 || yy >= HEIGTH) {
					continue;
				}

				Tile tile = tiles[xx + (yy*WIDHT)];
				tile.render(g);
			}
		}
	}

}
