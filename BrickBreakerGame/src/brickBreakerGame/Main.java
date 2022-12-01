package brickBreakerGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		/*
		 * Create a jFrame for game play. 
		 * Name JFrame to Brick Breaker Game
		 * Set the size of the JFrame aka 'Bounds' to a resonable size
		 * Set JFrame to visible (true), Resizeable(false), and Closed on exit
		 */
		//JFrame gamePlayWindow = new JFrame("Brick Breaker");
		JFrame gamePlayWindow = new JFrame();
		//Create the Gameplay Obj
		Gameplay gamePlay = new Gameplay();
		
		//Continue to define the JFrame
		gamePlayWindow.setBounds(10,10,900,800);
		gamePlayWindow.setTitle("Brick Breaker Game");
		gamePlayWindow.add(gamePlay);
		gamePlayWindow.setVisible(true);
		gamePlayWindow.setResizable(false);
		gamePlayWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add the Gameplay object into the JFrame
		
	}

}
