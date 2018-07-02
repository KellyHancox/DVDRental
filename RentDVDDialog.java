package project4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class RentDVDDialog extends JDialog implements ActionListener {

	private JTextField titleTxt;
	private JTextField renterTxt;
	public JTextField rentedOnTxt;
	public JTextField DueBackTxt;

	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;
	
	//private Date date;
	private Calendar c;
	
	private GregorianCalendar gregorianCalendar;

	private DVD unit;

	public RentDVDDialog(JFrame parent, DVD d) {
		// call parent and create a 'modal' dialog
		super(parent, true);

		setTitle("Rent a DVD:");
		closeStatus = false;
		setSize(400, 200);

		unit = d;
		
		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// instantiate and display text fields

		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(6, 2));

		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("John Doe", 30);
		textPanel.add(renterTxt);

		textPanel.add(new JLabel("Title of DVD:"));
		titleTxt = new JTextField("Avengers", 30);
		textPanel.add(titleTxt);

		
		
		
		
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
		DueBackTxt = new JTextField(dateFormat.format(date), 30);
		
		//this puts the due back box on the panel
		textPanel.add(DueBackTxt);
		
		
		//this adds the entire boxes to the whole pane 
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

	public void actionPerformed(ActionEvent e) {

		JButton button = (JButton) e.getSource();

		// if OK clicked the fill the object
		if (button == okButton) {
			// save the information in the object
			closeStatus = true;

			unit.setNameOfRenter(renterTxt.getText());
			unit.setTitle(titleTxt.getText());
			
			
			//DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			//Date date = Calendar.getInstance().getTime();
			
			//this takes the string form of the due back input
			String dueBack = DueBackTxt.getText();
			
			//this defines the date form of the due back input
			Date dueBackDateDateForm;
			
			//must surround with a try-catch block
			try {
				
				//this takes the string form and parses it into a date form
				dueBackDateDateForm = new SimpleDateFormat("MM/dd/yyyy").parse(dueBack);
				//System.out.println(dueBackDateDateForm);
				
				
				//this takes the date form and turns it into a gregorian calendar form
				gregorianCalendar =(GregorianCalendar) GregorianCalendar.getInstance();
	            gregorianCalendar.setTime(dueBackDateDateForm);
	           
	            
	            
	            // this puts the gregorian calendar form and puts it into the unit
	            unit.setDueBack(gregorianCalendar);
			} 
			//this happens if the user inputs something that isn't a date into the system 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				//have a jDialog box make them input again
				closeStatus = false;
			}
			
			
			
			//this takes the string form of the due back input
			String boughtDate = rentedOnTxt.getText();
			
			//this defines the date form of the due back input
			Date boughtDateDateForm;
			
			//must surround with a try-catch block
			try {
				
				//this takes the string form and parses it into a date form
				boughtDateDateForm = new SimpleDateFormat("MM/dd/yyyy").parse(boughtDate);
				
				
				//this takes the date form and turns it into a gregorian calendar form
				gregorianCalendar =(GregorianCalendar) GregorianCalendar.getInstance();
	            gregorianCalendar.setTime(boughtDateDateForm);
	           
	            
	            // this puts the gregorian calendar form and puts it into the unit
	            unit.setBought(gregorianCalendar);
			} 
			//this happens if the user inputs something that isn't a date into the system 
			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				//have a jDialog box make them input again
				closeStatus = false;
			}	
			
			
			
		}
		// make the dialog disappear
		dispose();
	}

	public boolean closeOK() {
		return closeStatus;
	}
}
