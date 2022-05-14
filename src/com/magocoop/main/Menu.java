package com.magocoop.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.magocoop.world.World;

public class Menu {
	
	public String[] options = {"new game","load","controls","exit"};
	
	public int curOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up,down,enter,esc;
	
	private static boolean isplaying =false;
	
	public static boolean pause = false;
	
	public static boolean controls = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;

	public void tick() {
		File file = new File("save.txt");
		if(file.exists())
			saveExists = true;
		else
			saveExists = false;
		if(up) {
			up = false;
			curOption--;
			if(curOption < 0)
				curOption = maxOption;
			Sound.selectFX.play();
		}
		if(down) {
			down = false;
			curOption++;
			if(curOption > maxOption)
				curOption = 0;
			Sound.selectFX.play();
		}
		if(enter){
			
			enter=false;
			if(options[curOption] == "new game" || options[curOption] == "continuar") {
				if(!isplaying) {
					isplaying = true;
					Sound.music.loop();
					//System.out.println("a");
				}	
				Game.gameStatus = "NORMAL";
				pause = false;
				file = new File("save.txt");
				file.delete();
			}else if(options[curOption] == "load" && pause == true) {
				Game.saveGame = true;
				
			}else if(options[curOption] == "load" && pause == false){
				file = new File("save.txt");
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
				}	
			}else if(options[curOption] == "controls") {
				controls = true;
				
			}else if(options[curOption] == "exit")
				System.exit(1);
		
		}if(esc) {
			esc = false;
			if(controls)
				controls = false;
		}	
	}
		
		public static void applySave(String str) {
			String[] spl = str.split("/");
			for(int i = 0; i<spl.length; i++) {
				String[] spl2 = spl[i].split(":");
				switch(spl2[0]) 
				{
					case "level":
						World.restartGame("level"+spl2[1]+".png");
						Game.gameStatus = "NORMAL";
						pause = false;
						break;
					case "life":
						Game.player.life = Integer.parseInt(spl2[1]);
						break;
					case "ammo":
						Game.player.ammo = Integer.parseInt(spl2[1]);
						break;
				}
			}
		}
	
		public static String loadGame(int encode) {
			String line = "";
			File file = new File("save.txt");
			if(file.exists()) {
				try {
					String singleLine = null;
					BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
					try {
						while((singleLine = reader.readLine()) != null) {
							String[] trans = singleLine.split(":");
							char[] val = trans[1].toCharArray();
							trans[1] = "";
							for(int i = 0; i<val.length; i++) {
								val[i] -= encode;
								trans[1] += val[i];
							}
							line += trans[0];
							line += ":";
							line += trans[1];
							line += "/";
						}
					}catch(IOException e) {}
				}catch(FileNotFoundException e) {}
			}
			return line;
		}
	
		public static void saveGame(String[] val1, int[] val2, int encode) {
			BufferedWriter write = null;
			try {
				write = new BufferedWriter(new FileWriter("save.txt"));
			}catch(IOException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < val1.length; i++) {
				String current = val1[i];
				current += ":";
				char[] value = Integer.toString(val2[i]).toCharArray();
				for(int n = 0; n < value.length; n++) {
					value[n] += encode;
					current += value[n];
				}
				try {
					write.write(current);
					if(i < val1.length - 1) 
						write.newLine();
				}catch(IOException e) {}
			}
			try {
				write.flush();
				write.close();
			}catch(IOException e) {}
		}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(pause == true) {
		g2.setColor(new Color(0,0,0,200));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.WIDTH*Game.SCALE);
		}else if(pause == false) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.WIDTH*Game.SCALE);
		}
		
		g.setColor(new Color(200,0,70));
		g.setFont(Game.title_font);
		g.drawString("Jack's Journey", (Game.WIDTH*Game.SCALE/2)-300, 120);
		
		//
		
		g.setColor(Color.WHITE);
		g.setFont(Game.opt_font);
		if(pause == false) {
			g.drawString("New game", (Game.WIDTH*Game.SCALE)/2-52-15, 140+80);
			g.drawString("Load Save", (Game.WIDTH*Game.SCALE)/2-55-15, 240+80-50);
		}else if(pause == true) {
			g.drawString("Resume", (Game.WIDTH*Game.SCALE)/2-40-15, 140+80);
			g.drawString("Save game", (Game.WIDTH*Game.SCALE)/2-60-15, 190+80);
			}
		if(!pause)
			g.drawString("Controls", (Game.WIDTH*Game.SCALE)/2-59, 190+80+50);
		else 
			g.drawString("Controls", (Game.WIDTH*Game.SCALE)/2-52-15, 190+80+50);
		g.drawString("Exit", (Game.WIDTH*Game.SCALE)/2-18-13, 240+80+50);
		g.setColor(Color.WHITE);
		if(options[curOption] == "new game" && pause == false) {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-72-15, 140+80);
		}else if(options[curOption] == "new game" && pause == true) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2-60-15, 140+80);
				}
		if(options[curOption] == "controls" && pause) {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-80-5, 190+80+50);
		}else if(options[curOption] == "controls" && !pause)
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-80, 190+80+50);
		if(options[curOption] == "load") 
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-80-10, 190+80);
		if(options[curOption] == "exit") 
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-38-15, 240+80+50);
		if(controls) {
			//controles
			g.setColor(Color.black);
			g.fillRect(0,0,Game.WIDTH*3, Game.HEIGTH*3);
			g2.setColor(new Color(200,0,0,200));
			g2.fillRect(120,140,148,25);
			g2.fillRect(315,140,63,25);
			g2.fillRect(385,180,17,25);
			g2.fillRect(265,245,117,25);
			g2.fillRect(93,350,43,25);
			g.setFont(Game.ctrl_font);
			g.setColor(Color.white);
			int yOff = 120;
			g.drawString("Use Arrow Keys or WASD to move your player;", 20+50, 40+yOff);
			g.drawString("If you don't have a gun, use C to punch your enemies;", 20, 80+yOff);
			g.drawString("If you have a gun, use your mouse to aim,", 20+70, 120+yOff);
			g.drawString("and left click to shoot;", 215, 140+5+yOff);
			g.drawString("Press ESC to return", 20, 250+yOff);
		

		}
		g.setFont(Game.opt_font);
		g2.drawString("Game by Thiago José", 380, 460);
		

	}

}
