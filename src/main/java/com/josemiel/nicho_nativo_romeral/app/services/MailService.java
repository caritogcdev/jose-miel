package com.josemiel.nicho_nativo_romeral.app.services;

import com.josemiel.nicho_nativo_romeral.domain.entities.Booking;
import com.josemiel.nicho_nativo_romeral.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor

public class MailService {
    private final JavaMailSender sender;

  public void sendWelcome(User u) {
    var m = new SimpleMailMessage();
    m.setTo(u.getEmail());
    m.setSubject("¡Bienvenido!");
    m.setText("Hola " + u.getFirstName() + ", gracias por registrarte.");
    sender.send(m);
  }

  public void sendBookingCreated(Booking b) {
    var m = new SimpleMailMessage();
    m.setTo(b.getUser().getEmail());
    m.setSubject("Reserva creada #" + b.getId());
    m.setText("Tu reserva está pendiente de pago.");
    sender.send(m);
  }

  public void sendBookingConfirmed(Booking b) {
    var m = new SimpleMailMessage();
    m.setTo(b.getUser().getEmail());
    m.setSubject("Reserva confirmada #" + b.getId());
    m.setText("¡Nos vemos el " + b.getTourSession().getStartAt() + "!");
    sender.send(m);
  }
}
