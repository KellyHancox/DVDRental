package project4;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.*;

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

//	public DVD(GregorianCalendar bought, 
//			GregorianCalendar dueBack, 
//			String title, String name) {
//			//double daysRented) {
//		super();
//		this.bought = bought;
//		this.dueBack = dueBack;
//		this.title = title;
//		this.nameOfRenter = name;
//		this.cost = 5.0; // + 3*daysRented;
//	}

	
	//changed from calendar to return a string
	public String getBought() {
		//creates the format we want to return the date in
		SimpleDateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		//turns the date into a string
		String formattedDate = myDateFormat.format(bought.getTime());

		return formattedDate;
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
		cost = 2.0;
		//this.cost = 2.0 + 3*daysRented(returned - rented on);
		return cost;
	}
}
