package learnspringframework;

import learnspringframework.game.GameRunner;
import learnspringframework.game.MarioGame;
import learnspringframework.game.PacmanGame;
import learnspringframework.game.SuperContraGame;

public class AppGamingBasicJava {
	public static void main(String args[])
	{
//		var game=new MarioGame();
//		var game=new SuperContraGame();
		var game=new PacmanGame();
		
		var gameRunner=new GameRunner(game);
		
		gameRunner.run();
	}
}
