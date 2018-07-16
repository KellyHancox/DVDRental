package project4;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

import project4.RentDVDDialog;
import project4.RentGameDialog;
import project4.RentLateDialog;
/************************************************************************
 * This class is the GUI witch which the user interacts with to rent a 
 * game or a dvd 
 *
 * @author Kelly Hancox and Isfar Baset
 * @version July 15, 2018
 ************************************************************************/
public class RentalStoreGUI extends JFrame implements ActionListener {
    
	/** This is a part of Serializable */
	private static final long serialVersionUID = 1L;

	/** Holds the menu bar */
	private JMenuBar menus;

	/** Menu bar items */
	private JMenu fileMenu;
	private JMenu actionMenu;

	/** Menu bar items for each of the menus */
	private JMenuItem openSerItem;
	private JMenuItem exitItem;
	private JMenuItem saveSerItem;
	private JMenuItem openTextItem;
	private JMenuItem saveTextItem;
	private JMenuItem rentDVD;
	private JMenuItem rentGame;
	private JMenuItem returnItem;
    private JMenuItem lateRental;

    /** Creates a panel */
    private JPanel panel;

    /** Holds the input date */
    private String inputDate;

    /** Holds the list engine */
	private RentalStore list;

	/** Holds the JList area */
	private JList JListArea;

	/** Scroll pane */
	private JScrollPane scrollList;

	/*****************************************************************
	 * Constructor instantiates the GUI components and displays the 
	 * GUI
	 ****************************************************************/
	public RentalStoreGUI() {

		// adding menu bar and menu items
		menus = new JMenuBar();
		fileMenu = new JMenu("File");
		actionMenu = new JMenu("Action");
		openSerItem = new JMenuItem("Open Serial");
		exitItem = new JMenuItem("Exit");
		saveSerItem = new JMenuItem("Save Serial");
		openTextItem = new JMenuItem("Open Text");
		saveTextItem = new JMenuItem("Save Text");
		rentDVD = new JMenuItem("Rent DVD");
		rentGame = new JMenuItem("Rent Game");
		returnItem = new JMenuItem("Return");
		lateRental = new JMenuItem("Late Rental Check");
		panel = new JPanel();

		// adding items to bar
		fileMenu.add(openSerItem);
		fileMenu.add(saveSerItem);
		fileMenu.add(exitItem);
		actionMenu.add(rentDVD);
		actionMenu.add(rentGame);
		actionMenu.add(returnItem);
		actionMenu.add(lateRental);

		menus.add(fileMenu);
		menus.add(actionMenu);

		// adding actionListener
		openSerItem.addActionListener(this);
		saveSerItem.addActionListener(this);
		exitItem.addActionListener(this);
		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		returnItem.addActionListener(this);
		lateRental.addActionListener(this);

		setJMenuBar(menus);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// adding list to the GUI
		list = new RentalStore();
		JListArea = new JList(list);
		add(JListArea);
		JListArea.setVisible(true);

		setVisible(true);
		setSize(800, 800);
	}
	/*****************************************************************
	 * This method causes the selected menu items to function as 
	 * appropriate 
	 * 
	 * @param ActionEvent
	 * @return none
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		Object clicked = e.getSource();
        
		// If load is clicked 
		if (openSerItem == clicked || openTextItem == clicked) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().
						getAbsolutePath();
				if (openSerItem == clicked)
					list.loadFromSerializable(filename);
			}
		}

		// If save is clicked 
		if (saveSerItem == clicked || saveTextItem == clicked) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().
						getAbsolutePath();
				if (saveSerItem == e.getSource())
					list.saveAsSerializable(filename);
			}
		}

		// MenuBar options
		// If exit is clicked 
		if (e.getSource() == exitItem) {
			System.exit(1);
		}

		// If rent dvd is clicked 
		if (e.getSource() == rentDVD) {
			DVD dvd = new DVD(); 
			RentDVDDialog dialog = new RentDVDDialog(this, dvd);

			if (RentDVDDialog.disposed == false) {
				list.add(dvd);
			}
		}

		// If rent game is clicked 
		if (e.getSource() == rentGame) {
			Game game = new Game();
			RentGameDialog dialog = new RentGameDialog(this, game);

			if (RentGameDialog.disposed == false) {
				list.add(game);
			}
		}

		// If check late rentals is clicked 
		if (e.getSource() == lateRental) {
			RentLateDialog lateRent = new RentLateDialog(this);
		}

		// If return items is clicked 
		if (returnItem == e.getSource()) {

			boolean validReturnDate = false;
			int index = JListArea.getSelectedIndex();

			GregorianCalendar date = new GregorianCalendar();
			String inputDate = 
					JOptionPane.
					showInputDialog("Enter return date: ");
			// Makes it into a date form 
			SimpleDateFormat df = 
					new SimpleDateFormat("MM/dd/yyyy");
            
			// CHeck if the input date is negative 
			if (checkNegativeReturnDate(inputDate) 
					&& parseReturnDate()) {

				try {

					Date newDate = df.parse(inputDate);
					date.setTime(newDate);

					validReturnDate = true;

				} catch (Exception ex) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,
							"You did not input a valid return" 
					+ " date.\nPlease try again.");
				}
                
				// This executes if the return date is valid
				if (validReturnDate) {
					DVD unit = list.get(index);
                    
					// If return date is before rented date 
					if (date.before(unit.getBoughtDate())) {
						JOptionPane.showMessageDialog(null, 
					"You cannot return an item before it is bought.");
					}

					else {
						JOptionPane.showMessageDialog(null, "Thanks, " 
					+ unit.getNameOfRenter() + " for returning "
								+ unit.getTitle() 
								+ ", you owe: " 
								+ unit.getCost(date) + " dollars");
                       
						// deletes item from list after returned 
						list.delete(unit);
					}
				}

			}
			// this executes if something is wrong
			else if (inputDate != null) {
				JOptionPane.showMessageDialog(null, 
						"Please input a valid date");
			}
		}
	}
	/*****************************************************************
	 * This method checks if the return date is negative 
	 * 
	 * @param return date 
	 * @return true if it is positive and false if negative
	 *****************************************************************/
	private boolean checkNegativeReturnDate(String date) {

		try {
			String[] dates = date.split("/");
			int monthAttempt = 0;
			int dayAttempt = 0;
			int yearAttempt = 0;
			monthAttempt = Integer.parseInt(dates[0]);
			dayAttempt = Integer.parseInt(dates[1]);
			yearAttempt = Integer.parseInt(dates[2]);

			// this is checking to make sure that the 
			// rented on date uses positive numbers
			if (monthAttempt > 0 && dayAttempt > 0 &&
					yearAttempt > 0) {
				return true;
			} else {
				return false;
			}
		}
		// this happens if the user 
	    //inputs something that isn't a date into the system
		catch (Exception ex) {

			return false;
		}
	}
	/*****************************************************************
	 * This method checks if the return date is parsed into a 
	 * GregorianCalendar
	 * 
	 * @param none
	 * @return true if parsed, false if not  
	 *****************************************************************/
	private boolean parseReturnDate() {
		// this takes the string form of the due back input
		String returnedOnDate = inputDate;

		// must surround with a try-catch block
		try {

			// this takes the string form and parses 
			// it into a date form
			Date returnedOn = 
					new SimpleDateFormat("MM/dd/yyyy").
					parse(returnedOnDate);

			// this takes the date form and turns it 
			// into a gregorian calendar form
			GregorianCalendar gregorianCalendar = 
					(GregorianCalendar) GregorianCalendar.
					getInstance();
			gregorianCalendar.setTime(returnedOn);

			return true;

		}

		// this happens if the user 
		//inputs something that isn't a date into the system
		catch (Exception e1) {
			return false;
		}
	}
	/*****************************************************************
	 * This is the main method that runs the GUI  
	 *****************************************************************/
	public static void main(String[] args) {
		new RentalStoreGUI();
	}

}

