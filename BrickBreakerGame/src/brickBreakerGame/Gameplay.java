package brickBreakerGame;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * KeyListener for detecting arrow keys for paddle movement
 * Action listener for moving the ball
 */


public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	/* 
	 * When the game begins it will want to begin on its own
	 * Use a private Boolean method that is set to false to ensure the game cannot begin on its own
	 * In this case Boolean play = false meaning play will not begin
	 * 
	 * The initial score should be set to 0
	 * I'm using a 5x4 map so total bricks should be initialized to 20
	 * 
	 * A timer class should be initialized and delay for ball speed
	 * 
	 * Starting position of the player platform should be initialized = 310)
	 * 
	 * Starting position of the ball should be initialized (x = 120,y = 310) and also direction (x = -1,y = -2)
	 *
	 */
	
	//Start Code here
	
	//play false to prevent active game play and score 0 to initiate beginning score and Bricks to a integer
	private boolean play = false;
	private int score = 0;
	private int bricks = 50; //3x7 layout
	
	//Create an object from MapGenerator
	private MapGenerator brickMap;
	
	
	//Timer class for ball speed 
	private Timer timer;
	private int speedDelay = 8; //Regions (x,y positions) where ball cant go
	
	
	//Starting positions
	private int userPos = 510;
	
	private int ballPosMaxX = 850;  
	private int ballPosMinX = 250; 
	private int ballPosRangeX = ballPosMaxX - ballPosMinX +1;
	
	private int ballPosX = (int)(Math.random() * ballPosRangeX) + ballPosMinX;
	private int ballPosY = 250;
	
	static int alternate(int a, int b) {
		int x = a ^ b;
		return x;
	}
	
	private int ballDirX = 2;
	
	private double ballDirY = 3;
	
	
		
	
	/*
	 * The constructor below is designed to add the above values to the class Gameplay within the JFrame
	 * Add the key listener locally to gamePlay constructor
	 * Set Focusable to true
	 * Set FocusTraversalKeysEnabled to false
	 * 
	 */
	public Gameplay() {
		brickMap = new MapGenerator(5,10);
	
		addKeyListener(this);
		//Object set;
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(speedDelay,this);
		timer.start();
	}
	
	/*
	 * I'll need graphics to run the game
	 * Create a public class that paints graphics with no return type
	 * Set rect background color and for -delay dimensions of frame size 
	 * Create the rect borders around all sides but the bottom
	 * Create a rect paddle the will follow position and dimensions of user
	 * Create a oval ball that will follow position and dimensions of ball
	 * Dispose object graphics
	 */
	
	public void paint(Graphics g) {
		
		//add a background
		g.setColor(Color.black);
		g.fillRect(0, 0, 892, 792);
		
		/*
		 * Here we will need to draw the map of the bricks
		 * reference the draw method from the map object 
		 */
		brickMap.draw((Graphics2D) g);
		
		//Add Score board
		g.setColor(Color.white);
		g.setFont(new Font ("serif", Font.BOLD, 25));
		g.drawString("Current Score: " + score, 650, 30);
		//Create Borders
		g.setColor(Color.blue);
		g.fillRect(1, 1, 892, 3);
		g.fillRect(891, 1, 3, 792);
		g.fillRect(1, 1, 3, 792);
		
		//Create user paddle
		g.setColor(Color.white);
		g.fillRect(userPos, 750, 150, 8);
		
		//Create Ball
		g.setColor(Color.white);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		
		//Break game if ball goes out of bounds
		if (ballPosY > 799 || bricks == 0) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			g.setColor(Color.white);
			g.setFont(new Font ("serif", Font.BOLD, 25));
			g.drawString("Game Over! Your Score: " + score, 290, 500);
			
			g.setColor(Color.white);
			g.setFont(new Font ("serif", Font.BOLD, 25));
			g.drawString("Press ENTER to play again", 290, 550);
			
			
		}
				
		
		g.dispose();
		
		
	}

	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		//If the game is in play
		if (play) {
			
			/*
			 * check for intersection between paddle and the ball
			 * Create a new rectangle object around the ball matching ball position and dimensions
			 */
			
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(userPos, 750, 150, 8))) {
				ballDirY = - ballDirY;
				
			}
			
			/*
			 * Check for intersection between mapped brick and the ball
			 * first we iterate through every brick
			 */
			
			A: for (int i = 0; i < brickMap.map.length; i++) {
				for (int j = 0; j < brickMap.map[0].length; j++) {
					if(brickMap.map[i][j] > 0) {
						
						/*
						 * Need position of brick and position of ball at x and y 
						 */
						
						int brickX = j* brickMap.brickWidth + 180;
						int brickY = i* brickMap.brickHeight + 50;
						int brickWidth = brickMap.brickWidth;
						int brickHeight = brickMap.brickHeight;
						
						//Now create the rectangle around the brick
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
					
						
						if (ballRect.intersects(brickRect)) {
							//call class and then the setBrickValue method from that class
							brickMap.setBrickValue(0,i,j);
							bricks = bricks - 1;
							score +=5;
							
							//Change direction of the ball
							if (ballPosX + 19 < brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								ballDirX = -ballDirX;
							} else
							{
								ballDirY = -ballDirY;
							}
							
							//Break A allows me to choose what loop to break. Assign loop to break
							
							break A;
						}
					}
				}
			}
			
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			
			//For the left border
			if (ballPosX < 0) {
				ballDirX = -ballDirX;
			}
			//For the Top border
			if (ballPosY < 0) {
				ballDirY = -ballDirY;
			}
			//For the right border
			if (ballPosX > 870) {
				ballDirX = -ballDirX;
			}
		}
		
		repaint();
	}

	//I do not need this two methods
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	
	public void keyPressed(KeyEvent e) {
		/*
		 * Checks if user presses the right arrow key
		 * If the key event is equal to right key (VK_RIGHT) and if user is greater than or equal to 600 user = 600
		 * otherwise call method to move right right 
		 * Repeat for left key but set max position to 10 and call a method to move right
		 */
		
		//Start Code here
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (userPos >= 800) {
				userPos = 800;
			} else {
				moveRight();
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (userPos <= 10) {
				userPos = 10;
			} else {
				moveLeft();
			}
			
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play || bricks == 0) {
				play = true;
				score = 0;
				bricks = 50; //3x7 layout
				   
				ballPosX = (int)(Math.random() * ballPosRangeX) + ballPosMinX;
				ballPosY = 230;
				
				//ballDirX = alternate(-1,1);
				//ballDirY = 2.5;
				
				ballDirX = 2;
				ballDirY = 3;
				
				userPos = 310;
				
				brickMap = new MapGenerator(5,10);
				
				repaint();
			}
		}
	}
/*
 * Make one move right method as a public with not return type
 * Make sure play is true and then user position increments or decreases by 20 bits
 */
	public void moveRight() {
		play = true;
		userPos += 35;
	}
	
	public void moveLeft () {
		play = true;
		userPos -= 35;
	}

}
