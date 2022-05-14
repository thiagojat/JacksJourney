package com.magocoop.entities;

import java.awt.image.BufferedImage;

public class Ammunition extends Entity {

	public Ammunition(int x, int y, int width, int heigth, BufferedImage sprite) {
		super(x, y, width, heigth, sprite);
		
		depth=0;
	}

}
