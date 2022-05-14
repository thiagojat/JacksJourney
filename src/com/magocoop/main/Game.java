package com.magocoop.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.magocoop.entities.Bullet;
import com.magocoop.entities.Enemy;
import com.magocoop.entities.Enemy2;
import com.magocoop.entities.Entity;
import com.magocoop.entities.Npc;
import com.magocoop.entities.Player;
import com.magocoop.entities.Prisoner;
import com.magocoop.entities.UncleSam;
import com.magocoop.entities.uncleSamHat;
import com.magocoop.graficos.DialogueSpritesheet;
import com.magocoop.graficos.Spritesheet;
import com.magocoop.graficos.UI;
import com.magocoop.world.Camera;
import com.magocoop.world.Tile;
import com.magocoop.world.World;



public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public final static int WIDTH = 240;
	public final static int HEIGTH = 160;
	public final static int SCALE = 3;
	
	public static int CUR_LEVEL = 1;
	private int MAX_LEVEL = 4;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Enemy2> enemies2;
	public static List<Prisoner> prisoners;
	public static List<Bullet> bullets;
	public static List<Npc> npcs;
	public static List<uncleSamHat> hats;
	public static List<Entity> coms;
		
	public static Spritesheet spritesheet;
	
	public static DialogueSpritesheet dialspritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public static Enemy enemy;
	
	public static Npc npc;
	
	public static Prisoner prisoner;
	
	public static Bullet bullet;
	
	public static UncleSam sam;
	
	public static uncleSamHat hat;
	
	public static UI ui;
	
	public static InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("title_font.ttf");
	public static InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("title_font.ttf");
	public static InputStream stream3 = ClassLoader.getSystemClassLoader().getResourceAsStream("title_font.ttf");
	public static InputStream stream4 = ClassLoader.getSystemClassLoader().getResourceAsStream("title_font.ttf");
	public static InputStream stream5 = ClassLoader.getSystemClassLoader().getResourceAsStream("title_font.ttf");
	public static Font title_font;
	public static Font opt_font;
	public static Font dial_font;
	public static Font ctrl_font;
	public static Font win_font;
	
	public Menu menu;
	
	public static String gameStatus = "MENU";
	
	public static boolean isplaying = false;
	public static boolean cutScene[] = new boolean[1];
	public static int cutSceneFramesInit = 0;
	public static int MAXcutSceneFramesInit = 10;
	public static boolean dialSam[] = new boolean[2];
	public static boolean cutSceneEnd = false;
	public int yyCam = 0;
	public int xxCam = 0;
	public int istalkingFrames = 0;
	public int MAXistalkingFrames = 120;
	public static boolean SAMcutSceneMove = false;
	
	public boolean showMessageWin = false;
	
	public boolean showMessageGameOver = false;
	private int framesGameOver;
	
	public static boolean restartGame; 
	
	public static boolean saveGame = false;
	
	public Game(){
				
		//Sound.basedMusic.loop();
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		//setPreferredSize(new Dimension (Toolkit.getDefaultToolkit().getScreenSize()));
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGTH*SCALE));
		initFrame();
		/*Inicializando objetos*/


		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGTH, BufferedImage.TYPE_INT_RGB);
		entities= new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		enemies2 = new ArrayList<Enemy2>();
		prisoners = new ArrayList<Prisoner>();
		bullets = new ArrayList<Bullet>();
		hats = new ArrayList<uncleSamHat>();
		npcs = new ArrayList<Npc>();
		coms = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet.png");
		dialspritesheet = new DialogueSpritesheet("/dialSpritesheet.png");
		sam = new UncleSam(0, 0, 16, 32, spritesheet.getSprite(16, 128, 16, 32));
		player = new Player(0,0,16,16, spritesheet.getSprite(32,16,16,16));
		entities.add(player);
		world = new World("/level1.png");
		try {
			title_font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(70f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			opt_font = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(25f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			dial_font = Font.createFont(Font.TRUETYPE_FONT, stream3).deriveFont(8f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ctrl_font = Font.createFont(Font.TRUETYPE_FONT, stream4).deriveFont(20f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			win_font = Font.createFont(Font.TRUETYPE_FONT, stream5).deriveFont(100f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		menu = new Menu();
		
		xxCam = 0;
		yyCam = 8*16;
	}
	
	public void initFrame(){
		frame = new JFrame("Jack's Journey");
		frame.add(this);
		//frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		//icon
		Image icon = null;
		try {
			icon = ImageIO.read(getClass().getResource("/icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image cursor = toolkit.getImage(getClass().getResource("/cursor.png"));
		Cursor c = toolkit.createCustomCursor(cursor, new Point(0,0), "img");
		frame.setCursor(c);
		frame.setIconImage(icon);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread =new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		if(gameStatus == "NORMAL") {
			if(CUR_LEVEL != 4 || isplaying) {
				restartGame = false;
				for(int i = 0; i<entities.size(); i++) {
					Entity e = entities.get(i);
					e.tick();
				}
				
				for(int i = 0; i<bullets.size(); i++) {
					Bullet b = bullets.get(i);
					b.tick();
				}
				for(int i = 0; i<hats.size(); i++) {
					uncleSamHat h = hats.get(i);
					h.tick();
				}
			}else{
				Game.cutScene[0] = true;
				for(int i = 0; i<entities.size(); i++) {
					Entity e = entities.get(i);
					if(e instanceof UncleSam) {
						e.tick();
					}
				}
				if(cutScene[0]) {
//					Camera.x = Camera.clamp(player.getX() - (Game.WIDTH/2), 0, World.WIDHT*16 - Game.WIDTH);
					Camera.y = Camera.clamp(player.getY() - (Game.HEIGTH/2), 0, World.HEIGTH*16 - Game.HEIGTH);	
					if(player.y < 8*16) {
						player.y++;
						}
				}
				//System.out.println(player.x);
				if(player.y >= 8*16) {
					
//					int xx = 0;
//					int yy = 0;
					if (xxCam != 38*16)
						xxCam+=2;
					if (yyCam != 8*16)
						yyCam++;
					//System.out.println(xxCam);
					Camera.x = Camera.clamp(xxCam - (Game.WIDTH/2), 0, World.WIDHT*16 - Game.WIDTH);
					Camera.y = Camera.clamp(yyCam - (Game.HEIGTH/2), 0, World.HEIGTH*16 - Game.HEIGTH);	
					if(xxCam == (38*16) && yyCam == (8*16)) {
						Game.dialSam[0] = true;
						if(Game.cutSceneEnd = true) {
							this.istalkingFrames++;
							if(istalkingFrames == this.MAXistalkingFrames) {
								Game.cutSceneEnd = false;
								istalkingFrames = 0;
								Game.cutScene[0] = false;
								Game.dialSam[0] = false;
								Game.SAMcutSceneMove = true;
								
							}
						}
					}
				}
					
				
			}
			if(player.nextLevel()) {
				//System.out.println("Proxima fase!!");
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
					}
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
				if(CUR_LEVEL ==2)
					player.hasGun = true;
			}
		}else if(gameStatus == "GAME_OVER") {
			framesGameOver++;
			if(framesGameOver == 28) {
				framesGameOver=0;
				if(showMessageGameOver) 
					showMessageGameOver = false;
				else showMessageGameOver = true;
			}
			if(restartGame) {
				restartGame = false;
				gameStatus = "NORMAL";
				CUR_LEVEL = 1;
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
			}
		}else if(gameStatus == "MENU") {
			if(saveGame) {
				saveGame = false;
				String[] opt1 = {"level","life","ammo"};
				int[] opt2 = {this.CUR_LEVEL, (int)player.life, player.ammo};
				Menu.saveGame(opt1, opt2, 10);
				System.out.println("Jogo salvo");
				
			}
			menu.tick();
		}else if(gameStatus == "WIN") {
			framesGameOver++;
			if(framesGameOver == 28) {
				framesGameOver=0;
				if(showMessageWin) 
					showMessageWin = false;
				else showMessageWin = true;
			}
			if(restartGame) {
				restartGame = false;
				gameStatus = "NORMAL";
				CUR_LEVEL = 1;
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
			}
		}
		
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
	Graphics g = image.getGraphics();

	g.setColor(new Color(0,0,0));
	g.fillRect(0, 0, WIDTH*4, HEIGTH*4);
	/*Render*/
	world.render(g);
	Collections.sort(entities, Entity.entitySorter);
	if(CUR_LEVEL == 4 && prisoner.free) {
		for(int i = 0; i<6; i++) {
		g.drawImage(Tile.TILE_IN_FLOOR, (11+(i*6))*16-Camera.x, 35*16-Camera.y, null);
		}
	}
	for(int i = 0; i<entities.size(); i++) {
		Entity e = entities.get(i);
		e.render(g);
	}
	for(int i = 0; i<bullets.size(); i++) {
		Bullet b = bullets.get(i);
		b.render(g);
	}
	for(int i = 0; i<hats.size(); i++) {
		uncleSamHat h = hats.get(i);
		h.render(g);
	}
	ui.render(g);
	g.drawImage(Game.spritesheet.getSprite(6*16, 16, 16, 16), 210, 2, null);
	g.setColor(Color.white);
	g.setFont(new Font("arial", Font.BOLD, 10));
	g.drawString("x" + Game.player.ammo, 222, 15);
	if(player.hasKey)
	g.drawImage(Game.spritesheet.getSprite(96, 48, 16, 16), 215, 20, null);
	//PORTA//g.fillRect(400-Camera.x,470-Camera.y, 64, 10);
	/**/
	g.dispose();
	g= bs.getDrawGraphics();
	g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGTH*SCALE, null);
	//g.drawImage(image, 0, 0, (Toolkit.getDefaultToolkit().getScreenSize()).width, (Toolkit.getDefaultToolkit().getScreenSize()).height, null);
	if(gameStatus == "GAME_OVER") {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,130));
		g2.fillRect(0, 0, WIDTH*SCALE, HEIGTH*SCALE);
		g2.setFont(new Font("arial", Font.BOLD, 60));
		g2.setColor(Color.WHITE);
		g2.drawString("Game Over!", 193, 220);
		g2.setFont(new Font("arial", Font.BOLD, 20));
		
		if(showMessageGameOver)
			g2.drawString(">Press ENTER to restart<", 227, 250);
					
	}else if(gameStatus == "MENU") {
		menu.render(g);
	}
	else if(gameStatus == "WIN") {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,130));
		g2.fillRect(0, 0, WIDTH*SCALE, HEIGTH*SCALE);
		g2.setFont(win_font);
		g2.setColor(Color.WHITE);
		if(this.showMessageWin) {
		g2.drawString("You won!!", 90, 290);
		g2.setFont(opt_font);
		g2.drawString("Congratulations,", 120, 200);
		g2.drawString(">Press enter to restart<", 120, 360);
		}
		g2.setFont(new Font("arial", Font.BOLD, 20));
		g2.drawString("Game by Thiago José", 480, 450);
	}
	bs.show();	
	
	}
	
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta>=1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis()-timer>= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
						
				
			}
			
		}
		
	}

	
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP ||
				e.getKeyCode()==KeyEvent.VK_W) {
			player.up=true;
			if(gameStatus == "MENU") {
				menu.up = true;
				//System.out.println("Up");
			}
		} else if(e.getKeyCode()==KeyEvent.VK_DOWN ||
				e.getKeyCode()==KeyEvent.VK_S) {
			player.down=true;
			if(gameStatus == "MENU") {
				menu.down = true;
				//System.out.println("Down");
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT ||
				e.getKeyCode()==KeyEvent.VK_A) {
			player.left=true;
		} else if(e.getKeyCode()==KeyEvent.VK_RIGHT ||
				e.getKeyCode()==KeyEvent.VK_D) {
			player.rigth=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			player.shoot = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			Game.restartGame = true;
			if(gameStatus == "MENU") {
				menu.enter = true;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_F) {
			Npc.f = true;
		}
		if(e.getKeyCode()== KeyEvent.VK_ESCAPE) {
			if(gameStatus == "NORMAL") {
				gameStatus = "MENU";	
				menu.pause = true;
			}else if(menu.controls == true)
				menu.esc = true;
		}	
		if(e.getKeyCode()== KeyEvent.VK_E) {
			Npc.e = true;
			if(player.unlockmessage && Npc.showedDialogue) {
				player.e = true;
			}
			if(player.buttonAvaliable()) {
				player.buttonpressed = true;
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_C) {
			player.punch = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP ||
				e.getKeyCode()==KeyEvent.VK_W) {
			player.up=false;
			
				
		} else if(e.getKeyCode()==KeyEvent.VK_DOWN ||
				e.getKeyCode()==KeyEvent.VK_S) {
			player.down=false;
			
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT ||
				e.getKeyCode()==KeyEvent.VK_A) {
			player.left=false;
		} else if(e.getKeyCode()==KeyEvent.VK_RIGHT ||
				e.getKeyCode()==KeyEvent.VK_D) {
			player.rigth=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			player.shoot = false;
		}
		if(e.getKeyCode()== KeyEvent.VK_E) {
			npc.e = false;
			if(player.unlockmessage) {
			
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_F) {
			Npc.f=false;
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		player.mouseShoot = true;
		player.mx = (e.getX()/3);
		player.my = (e.getY()/3);
		//System.out.println(e.getX()+ ", " + e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
