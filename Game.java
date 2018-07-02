package project4;

import java.util.GregorianCalendar;

public class Game extends DVD {

	/** Represents the type of player */
	private PlayerType player; // Xbox360, PS4, Xbox720, PS3, GameCube

	// add constructor

	/***************************************************************
	 * Constructor method initializes the player type to the provided value
	 ***************************************************************/

	public Game() {

	}

//	public Game(GregorianCalendar bought, GregorianCalendar dueBack, 
//			String title, String name, String playerType) {
//		//super();
//		super(bought, dueBack, title, name);
//		setPlayerType(playerType);
//	}

	
	//put it in the dialog
	protected void setPlayerType(String playerType) {
		String temp = PlayerType.PS4.toString();
		String temp2 = PlayerType.NintendoSwitch.toString();
		String temp3 = PlayerType.XBox1.toString();
		String temp4 = PlayerType.Xbox360.toString();
		String temp5 = PlayerType.WiiU.toString();

		if (playerType.equals(temp)) {
			PlayerType p = PlayerType.valueOf(temp);
			player = p;
		}

		if (playerType.equals(temp2)) {
			PlayerType p = PlayerType.valueOf(temp2);
			player = p;
		}
		if (playerType.equals(temp3)) {
			PlayerType p = PlayerType.valueOf(temp3);
			player = p;
		}
		if (playerType.equals(temp4)) {
			PlayerType p = PlayerType.valueOf(temp4);
			player = p;
		}
		if (playerType.equals(temp5)) {
			PlayerType p = PlayerType.valueOf(temp5);
			player = p;
		} else {

		}
	}

	public PlayerType getGamePlayerType() {
		return player;
	}

//	public GregorianCalendar getGameBought() {
//		return bought;
//	}
//
//	public void setGameBought(GregorianCalendar bought) {
//		this.bought = bought;
//	}
//
//	public GregorianCalendar getGameDueBack() {
//		return dueBack;
//	}
//
//	public void seGametDueBack(GregorianCalendar dueBack) {
//		this.dueBack = dueBack;
//	}
//
//	public String getGameTitle() {
//		return title;
//	}
//
//	public void setGameTitle(String title) {
//		this.title = title;
//	}
//
//	public String getGameNameOfRenter() {
//		return nameOfRenter;
//	}
//
//	public void setGameNameOfRenter(String nameOfRenter) {
//		this.nameOfRenter = nameOfRenter;
//	}
//
//	public double getGameCost(GregorianCalendar date) {
//		return 2.0;
//	}

	// add getter, setter methods
}


