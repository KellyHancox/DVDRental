package project4;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import project4.Game;


public class RentGameDialog extends JDialog implements ActionListener {

	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField dueBackTxt;
	private JTextField gameTypeTxt;

	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;

	private JFrame frame;

	private PlayerType player;
	private boolean validPlayerType;

	private boolean parseDueBack;
	private boolean parseRentedOn;

	public static boolean disposed;

	private Date boughtDateDateForm;
	private Date dueBackDateDateForm;

	private GregorianCalendar gregorianCalendar;

	private Calendar c;

	private Game game;

	public RentGameDialog(JFrame parent, Game g) {
		// call parent and create a 'modal' dialog
		super(parent, true);

		setTitle("Rent a DVD:");
		closeStatus = false;
		setSize(400, 200);

		game = g;
		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// instantiate and display text fields

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6, 2));

		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("T Haas", 30);
		textPanel.add(renterTxt);

		textPanel.add(new JLabel("Title of Game:"));
		titleTxt = new JTextField("Call of Duty", 30);
		textPanel.add(titleTxt);

		textPanel.add(new JLabel("Type of Game System: "));
		gameTypeTxt = new JTextField("Xbox360", 30);
		textPanel.add(gameTypeTxt);


		//creating a gregorian calendar
		c = new GregorianCalendar();

		//making the calendar appear in a simple format
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		//date is getting the time of the calendar
		Date date = c.getTime();

		//this is what prints on the side
		textPanel.add(new JLabel("Rented on: "));

		//this is the box where they can input date
		rentedOnTxt = new JTextField(dateFormat.format(date), 30);

		//this is what adds the box to the panel
		textPanel.add(rentedOnTxt);

		//adding one day to the calendar for due back
		c.add(c.DAY_OF_YEAR, 1);

		//getting the time after the addition
		date = c.getTime();

		//this is printed on the panel
		textPanel.add(new JLabel("Due back: "));

		//this is what shows up in the due back box
		dueBackTxt = new JTextField(dateFormat.format(date), 30);

		//this puts the due back box on the panel
		textPanel.add(dueBackTxt);


		getContentPane().add(textPanel, BorderLayout.CENTER);

		// Instantiate and display two buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		setSize(300, 300);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();

		if(button == cancelButton) {
			dispose();
			disposed = true;
		}

		// if OK clicked the fill the object
		else if(button == okButton) {

			if(renterTxt.getText() == null || titleTxt.getText() == null || rentedOnTxt.getText() == null || dueBackTxt.getText() == null) {
				JOptionPane.showMessageDialog(null, "You missed a box. Please input all values");
			}

			else {
				// save the information in the object
				closeStatus = true;

				game.setNameOfRenter(renterTxt.getText());
				game.setTitle(titleTxt.getText());

				setPlayerType(gameTypeTxt.getText());


				if(validPlayerType == false) {
					JOptionPane.showMessageDialog(frame, "Please input a valid console from "
							+ "the options:\nXbox360, XBox1, PS4, WiiU, and NintendoSwitch");
				}


				checkRentedOnDate(rentedOnTxt.getText());
				checkDueBackDate(dueBackTxt.getText());


				if(parseRentedOn == false || parseDueBack == false) {
					JOptionPane.showMessageDialog(frame, "Please input valid dates");
					parseRentedOn = false;
					parseDueBack = false;
				}

				if(parseRentedOn == true && parseDueBack == true && validPlayerType == true) {
					if(boughtDateDateForm.before(dueBackDateDateForm)) {
						// make the dialog disappear
						dispose();
						disposed = false;
					}
					
					else {	
						JOptionPane.showMessageDialog(frame, "The return date "
								+ "should be after bought date");
					}
				}
			}
		}
	}


	private void checkRentedOnDate(String date) {
		String[] dates = date.split("/");
		int monthAttempt = 0;
		int dayAttempt = 0;
		int yearAttempt = 0;

		try {
			monthAttempt = Integer.parseInt(dates[0]);
			dayAttempt = Integer.parseInt(dates[1]);
			yearAttempt = Integer.parseInt(dates[2]);

			//this is checking to make sure that the rented on date uses positive numbers
			if(monthAttempt > 0 && dayAttempt > 0 && yearAttempt > 0) {
				parseRentedOn();
			}
		}
		catch(Exception e1) {

		}
	}

	private void checkDueBackDate(String date) {
		String[] dates = date.split("/");
		int monthAttempt = 0;
		int dayAttempt = 0;
		int yearAttempt = 0;

		try {
			monthAttempt = Integer.parseInt(dates[0]);
			dayAttempt = Integer.parseInt(dates[1]);
			yearAttempt = Integer.parseInt(dates[2]);

			//this is checking to make sure that the due back date uses positive numbers
			if(monthAttempt > 0 && dayAttempt > 0 && yearAttempt > 0) {
				parseDueBack();
			}

		}
		catch(Exception e1) {
		}

	}

	private void parseRentedOn() {
		//this takes the string form of the due back input
		String boughtDate = rentedOnTxt.getText();

		//must surround with a try-catch block
		try {

			//this takes the string form and parses it into a date form
			boughtDateDateForm = new SimpleDateFormat("MM/dd/yyyy").parse(boughtDate);

			//this takes the date form and turns it into a gregorian calendar form
			gregorianCalendar = (GregorianCalendar)GregorianCalendar.getInstance();
			gregorianCalendar.setTime(boughtDateDateForm);


			// this puts the gregorian calendar form and puts it into the unit
			game.setBought(gregorianCalendar);
			parseRentedOn = true;

		} 

		//this happens if the user inputs something that isn't a date into the system 
		catch (Exception e1) {
			parseRentedOn = false;
		}
	}

	private void parseDueBack() {
		//this takes the string form of the due back input
		String dueBack = dueBackTxt.getText();

		//must surround with a try-catch block
		try {

			//this takes the string form and parses it into a date form
			dueBackDateDateForm = new SimpleDateFormat("MM/dd/yyyy").parse(dueBack);


			//this takes the date form and turns it into a gregorian calendar form
			gregorianCalendar = (GregorianCalendar)GregorianCalendar.getInstance();
			gregorianCalendar.setTime(dueBackDateDateForm);

			// this puts the gregorian calendar form and puts it into the unit
			game.setDueBack(gregorianCalendar);
			parseDueBack = true;
		}

		//this happens if the user inputs something that isn't a date into the system 
		catch (Exception e1) {
			parseDueBack = false;
		}
	}


	public void setPlayerType(String gameTypeTxt) {
		String temp = PlayerType.PS4.toString();
		String temp2 = PlayerType.NintendoSwitch.toString();
		String temp3 = PlayerType.XBox1.toString();
		String temp4 = PlayerType.Xbox360.toString();
		String temp5 = PlayerType.WiiU.toString();


		if (gameTypeTxt.equals(temp)) {
			PlayerType p = PlayerType.valueOf(temp);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;
		}
		else if (gameTypeTxt.equals(temp2)) {
			PlayerType p = PlayerType.valueOf(temp2);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;
		}
		else if (gameTypeTxt.equals(temp3)) {
			PlayerType p = PlayerType.valueOf(temp3);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;
		}
		else if (gameTypeTxt.equals(temp4)) {
			PlayerType p = PlayerType.valueOf(temp4);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;
		}
		else if (gameTypeTxt.equals(temp5)) {
			PlayerType p = PlayerType.valueOf(temp5);
			player = p;
			game.setPlayerType(player);
			validPlayerType = true;
		}
		else {
			validPlayerType = false;
		}
	}


	public boolean closeOK() {
		return closeStatus;
	}

}
