package com.centrala.naucna_centrala.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.centrala.naucna_centrala.Security.AES256bit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centrala.naucna_centrala.model.Korisnik;

@Service
@Transactional
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    //aktivacija korisnickog naloga
    @Async
    public void sendMailAktivacijaKorisnickogNaloga(Korisnik korisnik){ //throws MailException, InterruptedException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException  {
        String decryptedString = AES256bit.decrypt(korisnik.getKorisnickoIme(),AES256bit.secretKey);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("nijemidosadno@gmail.com");
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("[NAUCNA CENTRALA] Aktivacija korisnickog naloga");
        mail.setText("Da biste aktivirali Vaš korisnički nalog, molimo posetite sledeći link:\n https://localhost:8080/api/korisnik/aktivirajKorisnickiNalog/" + decryptedString);
        javaMailSender.send(mail);
    }

}