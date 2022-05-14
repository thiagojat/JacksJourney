package com.magocoop.world;

public class Camera {
	
	public static int x;
	public static int y;
	
	public static int clamp(int curX, int minX, int maxX ) {
		if(curX < minX) {
			curX = minX;
		}		
		if(curX > maxX) {
			curX = maxX;
		}
		return curX;
	}

}
