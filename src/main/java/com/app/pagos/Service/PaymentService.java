package com.app.pagos.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.app.pagos.Model.Payment;
import com.app.pagos.Persistence.PaymentFile;
import com.app.pagos.Segurity.PaymentEncryption;

public class PaymentService {
	
	private ArrayList<Payment> paymentsList;
	private PaymentFile paymentFile;
	private PaymentEncryption encryption;
	
	private final static String FILEPATH = "src/main/java/com/app/pagos/Persistence/paymentsData.txt" ;

	public PaymentService() {
		paymentsList = new ArrayList<>();
		paymentFile = new PaymentFile();
		encryption = new PaymentEncryption();
		
		loadList();
	}
	
	public ArrayList<Payment> findAll() {
		return paymentsList;
	}
	
	public Payment findById(int id) {
		for (int i = 0; i < paymentsList.size(); i++) {
			if (paymentsList.get(i).getId_payment()==id) {
				return paymentsList.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Payment> findByUser(int id){
		ArrayList<Payment> list = new ArrayList<>();
		for (int i = 0; i < paymentsList.size(); i++) {
			if (paymentsList.get(i).getId_owner()==id) {
				list.add(paymentsList.get(i));
			}
		}
		return list;
	}

	public Payment save(Payment payment)  {
		payment.setDate_payment(String.valueOf(LocalDate.now()));
		payment.setValue_payment(valueCalculate(payment.getAptm_Rooms(), payment.getAptm_Area()));
		paymentsList.add(payment);
		loadFile();
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
	
	public void loadFile()  {
		String payments = "";
		for (int i = 0; i < paymentsList.size(); i++) {
			payments += encryption.encript(paymentsList.get(i).toString())+"\n";
		}
		paymentFile.SobreescribirInformacion(FILEPATH, payments);
	}
	
	public void loadList() {
		for (int i = 0; i < paymentFile.ContenidoArchivo(FILEPATH).size(); i++) {
			String line = encryption.decrypt(paymentFile.ContenidoArchivo(FILEPATH).get(i));
			String [] paymentObject = line.split(";");
			
			Payment payment = new Payment(Integer.parseInt(paymentObject[0]), Double.parseDouble(paymentObject[1]),Integer.parseInt(paymentObject[2]),Integer.parseInt(paymentObject[3]),Integer.parseInt(paymentObject[4]),Integer.parseInt(paymentObject[5]),paymentObject[6],paymentObject[7],paymentObject[8]);
			paymentsList.add(payment);
		}
	}
	public ArrayList<String> loadListEncrypted() {
		return paymentFile.ContenidoArchivo(FILEPATH);
	}
	
	private double valueCalculate(int rooms, int area) {
		double value = rooms*50000;
		
		switch (area) {
		case 100:
			value+=100000;
			break;
			
		case 150:
			value+=150000;
			break;
			
		case 200:
			value+=200000;
			break;

		default:
			break;
		}
		
		return value;
	}
}
