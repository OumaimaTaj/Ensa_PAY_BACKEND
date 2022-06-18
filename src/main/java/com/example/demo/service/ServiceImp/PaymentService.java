package com.example.demo.service.ServiceImp;

import com.example.demo.model.Client;
import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

private final PaymentRepository paymentRepo;


 @Autowired
    public PaymentService(PaymentRepository paymentRepo) {
     this.paymentRepo = paymentRepo;

    }



    public  void add(Payment newPayment) {
         paymentRepo.save(newPayment);
    }
}
