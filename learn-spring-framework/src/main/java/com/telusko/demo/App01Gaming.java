package com.telusko.demo;

import com.telusko.demo.game.GameRunner;
import com.telusko.demo.game.MarioGame;
import com.telusko.demo.game.PacManGame;
import com.telusko.demo.game.SuperContraGame;

public class App01Gaming {

	public static void main(String[] args)
	{
//		var game=new MarioGame();
//		var game=new SuperContraGame();
//		var gameRunner=new GameRunner(marioGame);
		var game=new PacManGame();
		
		var gameRunner=new GameRunner(game);
		gameRunner.run();
	}
}
