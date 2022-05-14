package com.magocoop.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.magocoop.entities.UncleSam;
import com.magocoop.main.Game;

public class UI {
	
	private int lifeAmount;
	public boolean showSamLife = false;
	
	public void render(Graphics g) {
		//LIFE
		lifeAmount = (int) Game.player.maxLife;
		g.setColor(Color.black);
		g.fillRect(7, 7, lifeAmount+2, 11);
		g.setColor(Color.red);
		g.fillRect(8, 8, lifeAmount, 9);
		g.setColor(Color.GREEN);
		g.fillRect(8, 8, (int)((Game.player.life/Game.player.maxLife)*lifeAmount), 9);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, (int)(lifeAmount/2)-8, 16);
		
		//SAM LIFE BAR
		if(showSamLife == true) {
			int positionX = 0;
			int positionY = 0;
			g.setColor(Color.black);
			g.fillRect(positionX, positionY, 200+2, 12);
			g.setColor(Color.red);
			g.fillRect(positionX+1, positionY+1, 200, 10);
			g.setColor(Color.GREEN);
			g.fillRect(positionX+1, positionY+1, (int)((Game.sam.life/200)*200), 10);
//			g.setColor(Color.white);
//			g.setFont(new Font("arial", Font.BOLD, 9));
//			g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, (int)(lifeAmount/2)-8, 16);
		}

	}
	
}
