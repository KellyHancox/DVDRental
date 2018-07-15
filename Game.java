package project4;

import java.util.GregorianCalendar;

public class Game extends DVD {
	
	/** Represents the type of player */
	private PlayerType player; // Xbox360, PS4, Xbox720, PS3, GameCube

	/***************************************************************
	 * Constructor method initializes the player type to the 
	 * provided value
	 ***************************************************************/
	public Game() {
		
	}

	//put it in the dialog
	protected void setPlayerType(PlayerType gameSystem) {
		this.player = gameSystem;
	}

	public PlayerType getGamePlayerType() {
		return player;
	}

	public double getCost(GregorianCalendar date) {
		if(date.after(dueBack)) {
			cost = 10.0 + 5.0;
		}
		else {
			cost = 5.0;
		}
		
		return cost;
	}

}


