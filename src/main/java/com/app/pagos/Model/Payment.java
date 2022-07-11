package com.app.pagos.Model;

public class Payment {

	private int id_payment;
	private String date_payment;
	private String date_PaidOut;
	private double value_payment;
	private int id_owner;
	private int id_apto;
	private int aptm_Rooms;
	private int aptm_Area;
	private String state;
	
	public Payment(int id, double value, int owner, int aptm, int area, int rooms, String date_G, String date_P,String state) {
		this.id_payment=id;
		this.date_payment=date_G;
		this.date_PaidOut=date_P;
		this.value_payment=value;
		this.id_owner = owner;
		this.id_apto = aptm;
		this.aptm_Rooms = rooms;
		this.aptm_Area = area;
		this.state = state;
	}
	
	
	public int getId_payment() {
		return id_payment;
	}



	public void setId_payment(int id_payment) {
		this.id_payment = id_payment;
	}



	public String getDate_payment() {
		return date_payment;
	}



	public void setDate_payment(String date_payment) {
		this.date_payment = date_payment;
	}



	public String getDate_PaidOut() {
		return date_PaidOut;
	}



	public void setDate_PaidOut(String date_PaidOut) {
		this.date_PaidOut = date_PaidOut;
	}



	public double getValue_payment() {
		return value_payment;
	}



	public void setValue_payment(double value_payment) {
		this.value_payment = value_payment;
	}



	public int getId_owner() {
		return id_owner;
	}



	public void setId_owner(int id_owner) {
		this.id_owner = id_owner;
	}



	public int getId_apto() {
		return id_apto;
	}



	public void setId_apto(int id_apto) {
		this.id_apto = id_apto;
	}



	public int getAptm_Rooms() {
		return aptm_Rooms;
	}



	public void setAptm_Rooms(int aptm_Rooms) {
		this.aptm_Rooms = aptm_Rooms;
	}



	public int getAptm_Area() {
		return aptm_Area;
	}



	public void setAptm_Area(int aptm_Area) {
		this.aptm_Area = aptm_Area;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	@Override
	public String toString() {
		return id_payment + ";" +value_payment+ ";" +id_owner+ ";" +id_apto+ ";" +aptm_Area+ ";" +aptm_Rooms+ ";" + date_payment 
				+ ";" +date_PaidOut+ ";"+ state;
	}
	
}
