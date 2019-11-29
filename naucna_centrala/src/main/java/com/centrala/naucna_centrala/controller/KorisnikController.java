package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.KorisnikDTO;
import com.centrala.naucna_centrala.model.Korisnik;
import com.centrala.naucna_centrala.service.EmailService;
import com.centrala.naucna_centrala.service.Korisnik_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.centrala.naucna_centrala.Security.AES256bit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@RestController
@RequestMapping(value = "api/korisnik")
public class KorisnikController {

    @Autowired
    private Korisnik_service korisnik_service;

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = "/registracijaKorisnika")
    public ResponseEntity<Void> registracijaKorisnika(@RequestBody KorisnikDTO korisnik){// throws InvalidAlgorithmParameterException, InterruptedException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        int broj = proveraCestihLozinki(korisnik.getLozinka());
        System.out.println("Broj: " + broj);

        //BCrypt passworda korisnika - problem nije moguce obrnuto enkodovanje
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(broj != -1) {
            Korisnik k = new Korisnik();
            k.setIme(korisnik.getIme());
            k.setPrezime(korisnik.getPrezime());
            k.setGrad(korisnik.getGrad());
            k.setDrzava(korisnik.getDrzava());
            k.setTitula(korisnik.getTitula());
            k.setEmail(korisnik.getEmail());
            k.setKorisnickoIme(AES256bit.encrypt(korisnik.getKorisnicko_ime(),AES256bit.secretKey));
            k.setLozinka(passwordEncoder.encode(korisnik.getLozinka()));
            k.setTipKorisnika(korisnik.getTipKorisnika());
            k.setAktiviran_nalog(false);
            k.setId_casopisa(new HashSet<>());
            k.setRecenzenti(new HashSet<>());
            emailService.sendMailAktivacijaKorisnickogNaloga(k);
            korisnik_service.save(k);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logovanje")
    public ResponseEntity<?> logovanjeKorisnika(@RequestBody KorisnikDTO k)
    {
        //System.out.println("Korisnicko ime: " + k.getKorisnicko_ime());
        Korisnik kor = korisnik_service.findByKorisnicko_ime(AES256bit.encrypt(k.getKorisnicko_ime(),AES256bit.secretKey));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(kor != null)
        {
        //obratiti paznju da se nalog mora aktivirati
            if(AES256bit.decrypt(kor.getKorisnickoIme(),AES256bit.secretKey).equals(k.getKorisnicko_ime()) && passwordEncoder.matches(k.getLozinka(), kor.getLozinka()) && kor.isAktiviran_nalog()) {
                kor.setKorisnickoIme(AES256bit.decrypt(kor.getKorisnickoIme(),AES256bit.secretKey));
                return new ResponseEntity<>(new KorisnikDTO(kor),HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    //uzimanje svih korisnika i slanje na front
    @RequestMapping(method = RequestMethod.GET, value = "/getKorisnici")
    public ResponseEntity<List<KorisnikDTO>> getKorisnici()
    {
        List<Korisnik> korisnici = korisnik_service.findAll();
        List<KorisnikDTO> korisniciDTO =  new ArrayList<>();

        for(Korisnik k : korisnici)
        {
            korisniciDTO.add(new KorisnikDTO(k));
        }
        if(!korisniciDTO.isEmpty())
            return new ResponseEntity<>(korisniciDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/izmeniKorisnika", method = RequestMethod.PUT)
    public ResponseEntity<?> izmenaKorisnika(@RequestBody KorisnikDTO korisnik)
    {
        Korisnik k = korisnik_service.findByKorisnicko_ime(korisnik.getKorisnicko_ime());

        System.out.println("Username: " + k.getKorisnickoIme());

        k.setTipKorisnika(korisnik.getTipKorisnika());

        korisnik_service.save(k);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/brisanjeKorisnika/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> obrisiKorisnika(@PathVariable String username)
    {
        Korisnik k = korisnik_service.findByKorisnicko_ime(username);

        System.out.println("Username: " + k.getKorisnickoIme());
        if(k != null) {
            korisnik_service.remove(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(method = RequestMethod.PUT, value="/izmeniPodatkeOKorisniku")
    public ResponseEntity<?> izmeniPodatkeOKorisniku(@RequestBody KorisnikDTO kDTO)
    {
        Korisnik k = korisnik_service.findByKorisnicko_ime(kDTO.getKorisnicko_ime());

        if(k != null)
        {
            k.setIme(kDTO.getIme());
            k.setPrezime(kDTO.getPrezime());
            k.setGrad(kDTO.getGrad());
            k.setDrzava(kDTO.getDrzava());
            k.setTitula(kDTO.getTitula());
            k.setEmail(kDTO.getEmail());

            korisnik_service.save(k);

            return new ResponseEntity<>(new KorisnikDTO(k),HttpStatus.ACCEPTED);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/promenaLozinke")
    public ResponseEntity<?> promeniLozinku(@RequestBody KorisnikDTO k)
    {
        Korisnik korisnik = korisnik_service.findByKorisnicko_ime(k.getKorisnicko_ime());

        if(korisnik != null)
        {
            System.out.println("Lozinka");
            if(korisnik.getLozinka().equals(k.getLozinka()))
            {
                //ovde sam setovao novi password kroz EMAIL sam ga provukao
                korisnik.setLozinka(k.getEmail());
                korisnik_service.save(korisnik);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Integer proveraCestihLozinki(String lozinka)
    {
        String file ="cestoKorisceneLozinke.txt";
        List<String> sablon = new ArrayList<String>();

        try{
            InputStream ips = new FileInputStream(file);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String linija;

            while ((linija = br.readLine()) != null){
                sablon.add(linija);
            }

            br.close();
        }
        catch (Exception e){
            System.out.println("\n\n\t\tGreska prilikom pokusaja citanja iz fajla sa cesto koriscenim lozinkama.\n");
        }
        //plaintext vs plaintext a syso ispisati na frontu
        for(int i = 0; i < sablon.size(); i++) {
            if(sablon.get(i).toLowerCase().equals(lozinka.toLowerCase())) {
                System.out.println("\n\n\t\t\tLozinka se nalazi na listi najcesce koriscenih. Morate odabrati novu lozinku.");
                return -1;
            }
        }
        return 0;
    }

    @RequestMapping(value = "/aktivirajKorisnickiNalog/{korisnicko_ime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Korisnik> aktivirajKorisnickiNalog(@PathVariable String korisnicko_ime) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        String encryptedString = AES256bit.encrypt(korisnicko_ime,AES256bit.secretKey);
        Korisnik korisnik = korisnik_service.findByKorisnicko_ime(encryptedString);

        if(korisnik != null)
        {
            korisnik.setAktiviran_nalog(true);
            korisnik_service.save(korisnik);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
