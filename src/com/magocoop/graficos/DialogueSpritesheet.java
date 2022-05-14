package com.magocoop.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DialogueSpritesheet {
	
	private BufferedImage dialSpritesheet;
	
	public DialogueSpritesheet(String path){
		try {
			dialSpritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public BufferedImage getSprite(int x, int y, int width, int heigth) {
		return dialSpritesheet.getSubimage(x, y, width, heigth);
	
	}
}
