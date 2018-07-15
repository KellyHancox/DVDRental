package project4;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class RentalStore extends AbstractListModel {

	public static ArrayList<DVD> listDVDs;

	private boolean filter;

	private Calendar c;

	public RentalStore() {
		super();
		filter = false;
		listDVDs = new ArrayList<DVD>();
	}

	public void add (DVD a) {
		listDVDs.add(a);
		fireIntervalAdded(this, 0, listDVDs.size());
	}

	public void delete (DVD a) {
		for(int i = 0; i < listDVDs.size(); i++) {
			if(a ==  listDVDs.get(i)) {
				listDVDs.remove(i);
				fireIntervalRemoved(this, 0, i);
			}
		}
	}

	public DVD get (int i) {
		return listDVDs.get(i);
	}

	public Object getElementAt(int arg0) {	

		DVD unit = listDVDs.get(arg0);

		String line = "Name: " + " " + listDVDs.get(arg0).getNameOfRenter();
		line += "    Title: " + " " + listDVDs.get(arg0).getTitle();	
		line += "    Rented On: " + " " + listDVDs.get(arg0).getBoughtString();
		line += "    Due Back On: " + " " + listDVDs.get(arg0).getDueBack();

		if (unit instanceof Game) {
			line += "    Console: " + ((Game)unit).getGamePlayerType();
		}

		return line;
	}


	public void Undo() {
		DVD lastOne = listDVDs.get(getSize()-1);
		listDVDs.remove(lastOne);
		///add a fireinterval removed
	}

	public int getSize() {
		return listDVDs.size();
	}

	public void saveAsSerializable(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(listDVDs);
			os.close();
		}
		catch (IOException ex) {
			JOptionPane.showMessageDialog(null,"Error in saving db");
		}
	}


	public void loadFromSerializable(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			listDVDs = (ArrayList<DVD>) is.readObject();
			fireIntervalAdded(this, 0, listDVDs.size() - 1);
			is.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"Error in loading db");
		}
	}
}
