package com.magocoop.entities;

import java.awt.image.BufferedImage;

public class Ammo_Box extends Entity {

	public Ammo_Box(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		depth=0;
	}

}
