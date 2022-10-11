package com.app.pagos.Controller;

import com.app.pagos.Service.ConsignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consignments")
@CrossOrigin
public class ConsignmentController {

    ConsignmentService service = new ConsignmentService();

    @PostMapping("/{id}/{payments}")
    public ResponseEntity<?> post(@PathVariable int id, @PathVariable int payments){
        service.create(id, payments);
        return ResponseEntity.status(HttpStatus.CREATED).body("Creado exitosamente");
    }

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.showAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(service.showOne(id));
    }

    @GetMapping("/aptm{id}")
    public ResponseEntity getByAptm(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(service.showByAptm(id));
    }

    @GetMapping("/fee/{id}")
    public ResponseEntity getTotalValue(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(service.vTotal(id));
    }
}
