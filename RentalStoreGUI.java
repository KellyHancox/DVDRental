package project4;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import project4.RentDVDDialog;
import project4.RentGameDialog;
import project4.RentLateDialog;

public class RentalStoreGUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds menu bar
	 */
	private JMenuBar menus;

	/**
	 * menus in the menu bar
	 */
	private JMenu fileMenu;
	private JMenu actionMenu;

	/**
	 * menu items in each of the menus
	 */
	private JMenuItem openSerItem;
	private JMenuItem exitItem;
	private JMenuItem saveSerItem;
	private JMenuItem openTextItem;
	private JMenuItem saveTextItem;
	private JMenuItem rentDVD;
	private JMenuItem rentGame;
	private JMenuItem returnItem;

	private JMenuItem lateRental;

	private JPanel panel;

	private String inputDate;

	/**
	 * Holds the list engine
	 */
	private RentalStore list;

	/**
	 * Holds JListArea
	 */
	private JList JListArea; //this is your jlist

	/** Scroll pane */
	private JScrollPane scrollList;

	public RentalStoreGUI() {

		//adding menu bar and menu items
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
		//scrollList = new JScrollPane(5, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		//adding items to bar
		fileMenu.add(openSerItem);
		fileMenu.add(saveSerItem);
		fileMenu.add(exitItem);
		actionMenu.add(rentDVD);
		actionMenu.add(rentGame);
		actionMenu.add(returnItem);
		actionMenu.add(lateRental);

		menus.add(fileMenu);
		menus.add(actionMenu);

		//adding actionListener
		openSerItem.addActionListener(this);
		saveSerItem.addActionListener(this);
		exitItem.addActionListener(this);
		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		returnItem.addActionListener(this);
		lateRental.addActionListener(this);

		setJMenuBar(menus);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//adding list to the GUI1024
		list = new RentalStore();
		JListArea = new JList(list);
		add(JListArea);
		JListArea.setVisible(true);

		//panel.add(scrollList);

		//panel.add(menus);

		setVisible(true);
		setSize(800, 800);
		//		setSize(new Dimension (550,400));
		//		setMinimumSize(new Dimension(550,400));
		//		setMaximumSize(new Dimension(550,400));

	}

	public void actionPerformed(ActionEvent e) {

		Object clicked = e.getSource();

		if (openSerItem == clicked || openTextItem == clicked) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				if (openSerItem == clicked)
					list.loadFromSerializable(filename);
			}
		}

		if (saveSerItem == clicked || saveTextItem == clicked) {
			JFileChooser chooser = new JFileChooser();
			int status = chooser.showSaveDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				if (saveSerItem == e.getSource())
					list.saveAsSerializable(filename);
			}
		}

		//MenuBar options
		if (e.getSource() == exitItem) {
			System.exit(1);
		}

		if (e.getSource() == rentDVD) {
			DVD dvd = new DVD(); //is this calling on the DVD class?
			RentDVDDialog dialog = new RentDVDDialog(this, dvd);

			if(RentDVDDialog.disposed == false) {
				list.add(dvd);
			}
		}

		if (e.getSource() == rentGame) {
			Game game = new Game();
			RentGameDialog dialog = new RentGameDialog(this, game);

			if(RentGameDialog.disposed == false) {
				list.add(game);
			}
		}
		
		if(e.getSource() == lateRental) {
			RentLateDialog lateRent = new RentLateDialog(this);
		}


		if (returnItem == e.getSource()) {

			boolean validReturnDate = false;
			int index = JListArea.getSelectedIndex();

			GregorianCalendar date = new GregorianCalendar();
			String inputDate = JOptionPane.showInputDialog("Enter return date: ");
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

			if(checkNegativeReturnDate(inputDate) && parseReturnDate()) {

				try {

					Date newDate = df.parse(inputDate);
					date.setTime(newDate);

					validReturnDate = true;

				}
				catch (Exception ex){
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "You did not input a valid return"
							+ " date.\nPlease try again.");
				}

				if(validReturnDate) {
					DVD unit = list.get(index);

					if(date.before(unit.getBoughtDate())) {
						JOptionPane.showMessageDialog(null, "You cannot return an item before it is bought.");
					}

					else {
						JOptionPane.showMessageDialog(null, "Thanks, " + unit.getNameOfRenter() +
								" for returning " + unit.getTitle() + ", you owe: " + unit.getCost(date) +
								" dollars");

						list.delete(unit);
					}
				}

			}
			// this executes if something is wrong
			else if(inputDate != null) {
				JOptionPane.showMessageDialog(null, "Please input a valid date");
			}
		}
	}


	private boolean checkNegativeReturnDate(String date) {

		try {
			String[] dates = date.split("/");
			int monthAttempt = 0;
			int dayAttempt = 0;
			int yearAttempt = 0;
			monthAttempt = Integer.parseInt(dates[0]);
			dayAttempt = Integer.parseInt(dates[1]);
			yearAttempt = Integer.parseInt(dates[2]);

			//this is checking to make sure that the rented on date uses positive numbers
			if(monthAttempt > 0 && dayAttempt > 0 && yearAttempt > 0) {
				return true;
			}
			else {
				return false;
			}
		}

		catch(Exception ex) {

			return false;
		}
	}


	private boolean parseReturnDate() {
		//this takes the string form of the due back input
		String returnedOnDate = inputDate;

		//must surround with a try-catch block
		try {

			//this takes the string form and parses it into a date form
			Date returnedOn = new SimpleDateFormat("MM/dd/yyyy").parse(returnedOnDate);

			//this takes the date form and turns it into a gregorian calendar form
			GregorianCalendar gregorianCalendar = (GregorianCalendar)GregorianCalendar.getInstance();
			gregorianCalendar.setTime(returnedOn);


			return true;

		} 

		//this happens if the user inputs something that isn't a date into the system 
		catch (Exception e1) {
			return false;
		}
	}



	public static void main(String[] args) {
		new RentalStoreGUI();
	}

}
