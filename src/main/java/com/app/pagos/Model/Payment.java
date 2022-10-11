package com.app.pagos.Model;


public class Payment {

	private int id_payment;
	private String date_payment;
	private double value_payment;
	private int id_apto;
	private int aptm_Rooms;
	private double aptm_coparcenary;
	//private String state;

	//id_payment +";" + date_payment+";" + date_PaidOut +";" + value_payment +";" + id_apto +";" + aptm_Rooms +";" + aptm_coparcenary +";" + state
	public Payment(int id, String date_G,  double value, int aptm, int rooms, double coparcenary) {
		this.id_payment=id;
		this.date_payment=date_G;
		this.value_payment=value;
		this.id_apto = aptm;
		this.aptm_Rooms = rooms;
		this.aptm_coparcenary = coparcenary;
		//this.state = state;
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

	public double getValue_payment() {
		return value_payment;
	}



	public void setValue_payment(double value_payment) {
		this.value_payment = value_payment;
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



	public double getAptm_coparcenary() {
		return aptm_coparcenary;
	}


	public void setAptm_coparcenary(double aptm_coparcenary) {
		this.aptm_coparcenary = aptm_coparcenary;
	}


	@Override
	public String toString() {
		return id_payment +";" + date_payment+";" + value_payment +";" + id_apto +
				";" + aptm_Rooms +";" + aptm_coparcenary;
	}
}
