package com.example.demo.controller;
import com.example.demo.model.Payment;
import com.example.demo.service.ServiceImp.PaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/payment")
public class PaymentController {



  private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/add")
    public   void addPayment(@RequestBody Payment newPayment){
        service.add(newPayment);
    }
}
