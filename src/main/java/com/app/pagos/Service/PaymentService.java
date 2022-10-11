package com.app.pagos.Service;

import java.security.Key;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.app.pagos.Model.Payment;
import com.app.pagos.Persistence.PaymentFile;
import com.app.pagos.Segurity.PaymentEncryption;

import javax.swing.*;

public class PaymentService {
	
	private ArrayList<Payment> paymentsList;
	private PaymentFile paymentFile;
	private PaymentEncryption encryption;

	private int cont;

	
	private final static String FILEPATH = "src/main/java/com/app/pagos/Persistence/paymentsData.txt";

	public PaymentService() {
		paymentsList = new ArrayList<>();
		paymentFile = new PaymentFile();
		encryption = new PaymentEncryption();
		
		loadList();
	}
	
	public ArrayList<String> findAll() {
		//paymentsList.clear();
		//loadListEncrypted();
		//loadList();
		return paymentFile.ContenidoArchivo(FILEPATH);
	}
	
	public String findById(int id) {
		for (int i = 0; i < paymentsList.size(); i++) {
			if (paymentsList.get(i).getId_payment()==id) {
				try{
					return paymentsList.get(i).toString();
				}catch(Exception e){

				}

			}
		}
		return null;
	}
	
	public ArrayList<String> findByUser(int id){

		ArrayList<String> list = new ArrayList<>();
		for (Payment payment :
				paymentsList) {
			if (payment.getId_apto() == id){
				try{
					list.add(encryption.encryptByPublicKey(payment.toString()));
				}catch (Exception e){

				}
			}
		}
		return list;
	}

	public Payment save(Payment payment)  {
		cont=paymentsList.size()+1;
		payment.setId_payment(cont);
		payment.setDate_payment(String.valueOf(LocalDate.now()));
		payment.setValue_payment((int)valueCalculate(payment.getAptm_Rooms(), payment.getAptm_coparcenary()));
		paymentsList.add(payment);

		//loadFile();
		return payment;
	}

	public void deleteById(int id)  {
		for (int i = 0; i < paymentsList.size(); i++) {
			if (paymentsList.get(i).getId_payment()==id) {
				paymentsList.remove(i);
				loadFile();
			}
		}
	}

	public double tValue(int apto){
		double sum=0;

		for (Payment aux :
				paymentsList) {
			if (aux.getId_apto() == apto){
				sum+= aux.getValue_payment();
			}
		}
		return sum;
	}
	
	public void loadFile()  {
		String payments = "";
		for (int i = 0; i < paymentsList.size(); i++) {
			//payments += paymentsList.get(i).toString()+"\n";
			try{
				payments += encryption.encryptByPublicKey(paymentsList.get(i).toString())+"\n";
			}catch (Exception e){

			}
		}
		paymentFile.SobreescribirInformacion(FILEPATH, payments);
	}
	
	public void loadList() {
		for (int i = 0; i < paymentFile.ContenidoArchivo(FILEPATH).size(); i++) {

			String line="";

			try{
				//String line = paymentFile.ContenidoArchivo(FILEPATH).get(i);
				line = encryption.decryptByPrivateKey(paymentFile.ContenidoArchivo(FILEPATH).get(i));
			}catch(Exception e){

			}

			String [] paymentObject = line.split(";");
			Payment payment = new Payment(Integer.parseInt(paymentObject[0]), paymentObject[1],Double.parseDouble(paymentObject[2]),Integer.parseInt(paymentObject[3]),Integer.parseInt(paymentObject[4]),Double.parseDouble(paymentObject[5]));
			paymentsList.add(payment);
		}
	}
	public ArrayList<String> loadListEncrypted() {
		return paymentFile.ContenidoArchivo(FILEPATH);
	}

	private double valueCalculate(int rooms, double coparcenary) {
		double value = rooms*50000;
		value+= (value*coparcenary);
		return (int) value;
	}
}
