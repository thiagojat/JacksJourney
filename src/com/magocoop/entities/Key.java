package com.magocoop.entities;

import java.awt.image.BufferedImage;

public class Key extends Entity {

	public Key(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		depth=0;
	}

}
