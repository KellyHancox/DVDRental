package project4;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DVD implements Serializable {

	private static final long serialVersionUID = 1L;
	protected GregorianCalendar bought;
	protected GregorianCalendar dueBack;
	protected String title;
	protected String nameOfRenter; 
	protected double cost;

	public DVD() {
		//this is used to create a type dvd in DVDDialog
	}

	//changed from calendar to return a string
	public String getBoughtString() {
		//creates the format we want to return the date in
		SimpleDateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		//turns the date into a string
		String formattedDate = myDateFormat.format(bought.getTime());

		return formattedDate;
	}

	public GregorianCalendar getBoughtDate() {
		return bought;
	}

	public void setBought(GregorianCalendar bought) {
		this.bought = bought;
	}


	//changed this from calendar to string
	public String getDueBack() {
		//creates the format we want to return the date in
		SimpleDateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		//turns the date into a string
		String formattedDate = myDateFormat.format(dueBack.getTime());
		
		return formattedDate;
	}

	public GregorianCalendar getDueBackGregorianCalendar() {
		return dueBack;
	}
	
	public Date getDueBackDate() {	  
		 Date date = dueBack.getTime();
		 //System.out.println(date);
		return date;
	}

	public void setDueBack(GregorianCalendar dueBack) {
		this.dueBack = dueBack;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNameOfRenter() {
		return nameOfRenter;
	}

	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}

	public double getCost(GregorianCalendar date) {
		if(date.after(dueBack)) {
			cost = 1.2 + 2.0;
		}
		else {
			cost = 1.2;
		}
		return cost;
	}
}
