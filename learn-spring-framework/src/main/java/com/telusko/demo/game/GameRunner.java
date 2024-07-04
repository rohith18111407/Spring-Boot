package com.telusko.demo.game;

public class GameRunner {
//	MarioGame game;
	private GamingConsole game;
	
//	public GameRunner(MarioGame game) {
//		// TODO Auto-generated constructor stub
//		this.game=game;
//	}
	
	public GameRunner(GamingConsole game) {
		this.game=game;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Running game: "+game);
		game.up();
		game.down();
		game.right();
		game.left();
		
	}

}
