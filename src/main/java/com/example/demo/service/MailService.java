package com.example.demo.service;

import com.example.demo.model.User;

import javax.mail.MessagingException;

public interface MailService {
    void sendPasswordMail (User receiver,String password);
}
