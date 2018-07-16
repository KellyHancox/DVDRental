package project4;

import javax.swing.*;
import java.io.*;
import java.util.*;

/********************************************************************
 * This class creates the rules for the rental store
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ********************************************************************/
public class RentalStore extends AbstractListModel {

	/** holds the DVDs that are checked out*/
	public static ArrayList<DVD> listDVDs;

	//private boolean filter;

	//private Calendar c;

	/*****************************************************************
	 * Constructor creates instantiates the arraylist of DVDs
	 * 
	 * @param none
	 * @return none
	 ****************************************************************/
	public RentalStore() {
		super();
		//filter = false;
		listDVDs = new ArrayList<DVD>();
	}

	/*****************************************************************
	 * Adds a DVD to the arraylist
	 * 
	 * @param DVD a DVD with DVD information
	 * @return none
	 ****************************************************************/
	public void add (DVD a) {
		
		//adds the dvd to the arraylist
		listDVDs.add(a);
		
		//continuously updates the arraylist
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	/*****************************************************************
	 * Deletes a DVD from the arraylist
	 * 
	 * @param a DVD with DVD information
	 * @return none
	 ****************************************************************/
	public void delete (DVD a) {
		//sorts through the list of DVDs and deletes the correct DVD
		for(int i = 0; i < listDVDs.size(); i++) {
			if(a ==  listDVDs.get(i)) {
				listDVDs.remove(i);
				fireIntervalRemoved(this, 0, i);
			}
		}
	}

	/*****************************************************************
	 * Deletes a DVD from the arraylist
	 * 
	 * @param i integer to get from the arraylist
	 * @return DVD at the selected index
	 ****************************************************************/
	public DVD get (int i) {
		return listDVDs.get(i);
	}

	/*****************************************************************
	 * This method gathers the information for a DVD into a string
	 * to print out
	 * 
	 * @param arg0 item that was selected
	 * @return line the string with the information 
	 ****************************************************************/
	public Object getElementAt(int arg0) {	

		//creates a local DVD unit 
		DVD unit = listDVDs.get(arg0);

		//uses the unit to get the information for a string
		String line = "Name: " + " " + listDVDs.get(arg0).getNameOfRenter();
		line += "    Title: " + " " + listDVDs.get(arg0).getTitle();	
		line += "    Rented On: " + " " + listDVDs.get(arg0).getBoughtString();
		line += "    Due Back On: " + " " + listDVDs.get(arg0).getDueBack();

		//gets the gamesystem of games
		if (unit instanceof Game) {
			line += "    Console: " + ((Game)unit).getGamePlayerType();
		}

		return line;
	}

	/*****************************************************************
	 * This method returns how many DVDs there are
	 * 
	 * @param none
	 * @return the size of the arraylist of DVDs
	 ****************************************************************/
	public int getSize() {
		return listDVDs.size();
	}

	/*****************************************************************
	 * This method saves the list of DVDs
	 * 
	 * @param filename name of file
	 * @return none
	 ****************************************************************/
	public void saveAsSerializable(String filename) {
		
		//attempts to save the arraylist
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(listDVDs);
			os.close();
		}
		
		//displays error message if the file does not work
		catch (IOException ex) {
			JOptionPane.showMessageDialog(null,"Error in saving db");
		}
	}

	/*****************************************************************
	 * This method loads the list of DVDs from a saved file
	 * 
	 * @param filename name of file
	 * @return none
	 ****************************************************************/
	public void loadFromSerializable(String filename) {
		
		//attempts to read the file and display it
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			listDVDs = (ArrayList<DVD>) is.readObject();
			fireIntervalAdded(this, 0, listDVDs.size() - 1);
			is.close();
		}
		
		//displays error message if loading does not work 
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error in loading db");
		}
	}
}
