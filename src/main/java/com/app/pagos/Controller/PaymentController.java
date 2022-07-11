package com.app.pagos.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.pagos.Model.Payment;
import com.app.pagos.Service.PaymentService;


@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	PaymentService paymentService= new PaymentService();
	int cont =0;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ArrayList<Payment> payment) {
		for (int i = 0; i < payment.size(); i++) {
			ResponseEntity.status(HttpStatus.CREATED).body(paymentService.save(payment.get(i)));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("El arraylist se recibio exitosamente");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		Payment payment = paymentService.findById(id);
		if (payment== null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(payment);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		//autoPayment();
		ArrayList<Payment> payments = paymentService.findAll();
		//ArrayList<String> payments = paymentService.loadListEncrypted();
		return ResponseEntity.ok(payments);
	}
	
	@GetMapping("/Owner{id}")
	public ResponseEntity<?> findByOwner(@PathVariable int id){
		//autoPayment();
		ArrayList<Payment> payments = paymentService.findByUser(id);
		//ArrayList<String> payments = paymentService.loadListEncrypted();
		return ResponseEntity.ok(payments);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id) throws Exception{
		Payment payment = paymentService.findById(id);
		if (payment==null) {
			return ResponseEntity.notFound().build();
		}
		payment.setDate_PaidOut(String.valueOf(LocalDate.now()));
		payment.setState("Pagado");
		
		paymentService.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.save(payment));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) throws Exception {
		if (paymentService.findById(id)==null) {
			return ResponseEntity.notFound().build();
		}
		paymentService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
//	private Payment autoPayment() {
//		Date randomDate = new Date(ThreadLocalRandom.current().nextLong(, cont));
//		Payment payment = new Payment(cont++, null, Math.random()*99999.25, cont);
//		
//		return payment;
//	}

}
