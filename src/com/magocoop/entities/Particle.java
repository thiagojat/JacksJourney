package com.magocoop.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.magocoop.main.Game;
import com.magocoop.world.Camera;

public class Particle extends Entity{
	private int curLife=0;
	private int lifeTime = 15;
	
	public double spd=1.5;
	private double dx;
	private double dy;

	public Particle(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
	}
	public void tick() {
		curLife++;
		if(lifeTime==curLife)
			Game.entities.remove(this);
		x+=dx*spd;
		y+=dy*spd;
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(getX() - Camera.x, getY() - Camera.y, width, height);
	}
}
