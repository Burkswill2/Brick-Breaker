package brickBreakerGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MapGenerator {
	//Create a 2D array mapping the bricks
	public int map[][];
	
	//create brick dimensions width and height
	public int brickWidth, brickHeight;
	
	//Create a constructor to generate the map
	public MapGenerator(int row, int col) {
		//initiate the map 2d array with row and col
		map = new int [row][col];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				
				/*
				 * Add 1 at each position to represent a brick not collided with a ball
				 * brick present = 1
				 * brick gone = 0
				 */
				map[i][j] = 1;
			}
		}
		
		brickWidth = 540/col;
		brickHeight = 150/row;
	}
	
	/*
	 * Create a function to draw the bricks
	 */
	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
			if (map[i][j] > 0) {
				g.setColor(Color.blue);
				g.fillRect(j * brickWidth + 180, i * brickHeight + 50, brickWidth, brickHeight);
				
				//Create a boarder around each Brick
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.black);
				g.drawRect(j * brickWidth + 180, i * brickHeight + 50, brickWidth, brickHeight);
			}
			}
		}
	}
	//Create a function that will reset brick value after a collision
	public void setBrickValue(int brickVal, int row, int col) {
		
			map[row][col] = brickVal;
		
	}
}


















