package com.app.pagos.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import com.app.pagos.Service.ConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.pagos.Model.Payment;
import com.app.pagos.Service.PaymentService;


@CrossOrigin("https://daa1-186-168-237-73.ngrok.io/")
@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	PaymentService paymentService= new PaymentService();
	ConsignmentService service = new ConsignmentService();

	int cont =0;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ArrayList<Payment> payment) {

		for (Payment payment1 :
				payment) {
			paymentService.save(payment1);
		}
		paymentService.loadFile();
		
		return ResponseEntity.status(HttpStatus.CREATED).body("El arraylist se recibio exitosamente");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		String payment = paymentService.findById(id);
		if (payment== null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(payment);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		//autoPayment();
		ArrayList<String> payments = paymentService.findAll();
		//ArrayList<String> payments = paymentService.loadListEncrypted();
		return ResponseEntity.ok(payments);
		//return ResponseEntity.ok(String.valueOf(paymentService.key()));
	}
	
	@GetMapping("/apto{id}")
	public ResponseEntity<?> findByOwner(@PathVariable int id){
		ArrayList<String> payments = paymentService.findByUser(id);
		return ResponseEntity.ok(payments);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id) {
		String line = paymentService.findById(id);
		if (line==null) {
			return ResponseEntity.notFound().build();
		}
		String [] paymentObject = line.split(";");
		Payment payment = new Payment(Integer.parseInt(paymentObject[0]), paymentObject[1],Double.parseDouble(paymentObject[2]),Integer.parseInt(paymentObject[3]),Integer.parseInt(paymentObject[4]),Double.parseDouble(paymentObject[5]));
		//payment.setState("Pagado");
		
		paymentService.deleteById(id);
		paymentService.save(payment);

		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) throws Exception {
		if (paymentService.findById(id)==null) {
			return ResponseEntity.notFound().build();
		}
		paymentService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/apto={id}")
	public ResponseEntity paymentValueT(@PathVariable int id){
		return  ResponseEntity.status(HttpStatus.OK).body((int)paymentService.tValue(id));
	}

	@GetMapping("/state/{id}")
	public ResponseEntity stateClient(@PathVariable int id){
		if(paymentService.tValue(id)> service.vTotal(id)){
			return ResponseEntity.status(HttpStatus.OK).body("En deuda");
		}else{
			return ResponseEntity.status(HttpStatus.OK).body("A paz y salvo");
		}
	}
	
//	private Payment autoPayment() {
//		Date randomDate = new Date(ThreadLocalRandom.current().nextLong(, cont));
//		Payment payment = new Payment(cont++, null, Math.random()*99999.25, cont);
//		
//		return payment;
//	}


	public PaymentService getPaymentService() {
		return paymentService;
	}
}
