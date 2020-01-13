package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.KorisnikDTO;
import com.centrala.naucna_centrala.DTO.Naucna_oblastDTO;
import com.centrala.naucna_centrala.Security.JwtAuthenticationRequest;
import com.centrala.naucna_centrala.Security.TokenUtils;
import com.centrala.naucna_centrala.model.Korisnik;
import com.centrala.naucna_centrala.model.Korpa;
import com.centrala.naucna_centrala.model.Naucna_oblast;
import com.centrala.naucna_centrala.model.TipKorisnika;
import com.centrala.naucna_centrala.service.EmailService;
import com.centrala.naucna_centrala.service.Korisnik_service;
import com.centrala.naucna_centrala.service.Korpa_service;
import com.centrala.naucna_centrala.service.Naucna_oblast_service;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Set;

import com.centrala.naucna_centrala.Security.AES256bit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/korisnik")
public class KorisnikController {

    @Autowired
    private Korisnik_service korisnik_service;

    @Autowired
    private Naucna_oblast_service nos;

    @Autowired
    private Korpa_service korpa_service;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenUtils tokenUtils;

    private static final Logger logger = LoggerFactory.getLogger(KorisnikController.class);

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = "/registracijaKorisnika")
    public ResponseEntity<Void> registracijaKorisnika(@RequestBody KorisnikDTO korisnik){// throws InvalidAlgorithmParameterException, InterruptedException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        int broj = proveraCestihLozinki(korisnik.getLozinka());

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
            if(korisnik.isRecenzent())
            {
                k.setRecenzent(korisnik.isRecenzent());
            }else
            {
                k.setRecenzent(korisnik.isRecenzent());
                Korpa korpa = new Korpa();
                korpa.setNaucni_rad(new HashSet<>());
                korpa.setNaucni_casopis(new HashSet<>());
                korpa_service.save(korpa);
                k.setKorpa(korpa);
            }
            System.out.println("Cekirao recenzent : " + k.isRecenzent());
            k.setId_casopisa(new HashSet<>());
            k.setRecenzenti(new HashSet<>());
            Set<Naucna_oblast> no = new HashSet<>();
            for(Naucna_oblastDTO n : korisnik.getNaucne_oblasti())
            {
                Naucna_oblast n1 = nos.getByNaziv(n.getNaziv());
                no.add(n1);
            }
            k.setNaucne_oblasti(no);
            emailService.sendMailAktivacijaKorisnickogNaloga(k);
            korisnik_service.save(k);
            logger.info("\n\t\tKorisnik " + korisnik.getKorisnicko_ime() + " se registrovao na sistem naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logovanje")
    public ResponseEntity<?> logovanjeKorisnika(@RequestBody JwtAuthenticationRequest auth)
    {
        String jwt = korisnik_service.login(auth.getKorisnickoIme(), auth.getLozinka(), TipKorisnika.ADMINISTRATOR);

        if (jwt == "") {
            logger.info("\n\t\tNeuspesno kreiranje JWT tokena za korisnicko ime"+ auth.getKorisnickoIme() +" na sistem naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }else {
            logger.info("\n\t\tKorisnik "+auth.getKorisnickoIme() +" je uspesno dobio JWT token.\n");
            return ResponseEntity.ok(jwt);
        }
    }

    @RequestMapping(value = "/ulogovan", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public ResponseEntity<KorisnikDTO> getKorisnik(HttpServletRequest request) {
        try {

            String token = tokenUtils.getToken(request);

            Korisnik k = korisnik_service.findByKorisnicko_ime(tokenUtils.getUsernameFromToken(token));

            k.setKorisnickoIme(AES256bit.decrypt(tokenUtils.getUsernameFromToken(token),AES256bit.secretKey));

            logger.info("\n\t\tKorisnik sa email adresom " + k.getEmail() + " se upravo ulogovao na sistem naucne centrale.\n");
            return new ResponseEntity<>(new KorisnikDTO(k),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("\n\t\tNeuspesno logovanje na sistem naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
    }


    //uzimanje svih korisnika i slanje na front
    @RequestMapping(method = RequestMethod.GET, value = "/getKorisnici")
    public ResponseEntity<List<KorisnikDTO>> getKorisnici()
    {
        List<Korisnik> korisnici = korisnik_service.findAll();
        List<KorisnikDTO> korisniciDTO =  new ArrayList<>();

        for(Korisnik k : korisnici)
        {
            k.setKorisnickoIme(AES256bit.decrypt(k.getKorisnickoIme(),AES256bit.secretKey));
            korisniciDTO.add(new KorisnikDTO(k));
        }
        if(!korisniciDTO.isEmpty())
            return new ResponseEntity<>(korisniciDTO, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/izmeniKorisnika", method = RequestMethod.PUT)
    public ResponseEntity<?> izmenaKorisnika(@RequestBody KorisnikDTO korisnik, HttpServletRequest request)
    {
        String token = tokenUtils.getToken(request);

        Korisnik k = korisnik_service.findByKorisnicko_ime(AES256bit.encrypt(tokenUtils.getUsernameFromToken(token),AES256bit.secretKey));

        k.setTipKorisnika(korisnik.getTipKorisnika());

        korisnik_service.save(k);
        logger.info("\n\t\tKorisniku " + korisnik.getKorisnicko_ime() + " je promenjena uloga na sistemu naucne centrale.\n");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/brisanjeKorisnika/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> obrisiKorisnika(HttpServletRequest request)
    {
        String token = tokenUtils.getToken(request);

        Korisnik k = korisnik_service.findByKorisnicko_ime(AES256bit.encrypt(tokenUtils.getUsernameFromToken(token),AES256bit.secretKey));

        //System.out.println("Username: " + k.getKorisnickoIme());
        if(k != null) {
            korisnik_service.remove(k.getKorisnickoIme());
            logger.info("\n\t\tKorisnik " + k.getKorisnickoIme() + " je obrisan sa sistema naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            logger.info("\n\t\tNeuspelo brisanje korisnika sa sistema naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(method = RequestMethod.PUT, value="/izmeniPodatkeOKorisniku")
    public ResponseEntity<?> izmeniPodatkeOKorisniku(@RequestBody KorisnikDTO kDTO)
    {
        Korisnik k = korisnik_service.findByKorisnicko_ime(AES256bit.encrypt(kDTO.getKorisnicko_ime(),AES256bit.secretKey));

        if(k != null)
        {
            k.setIme(kDTO.getIme());
            k.setPrezime(kDTO.getPrezime());
            k.setGrad(kDTO.getGrad());
            k.setDrzava(kDTO.getDrzava());
            k.setTitula(kDTO.getTitula());
            k.setEmail(kDTO.getEmail());

            korisnik_service.save(k);
            k.setKorisnickoIme(AES256bit.decrypt(k.getKorisnickoIme(),AES256bit.secretKey));
            logger.info("\n\t\tKorisnik " + k.getKorisnickoIme() + " je upravo izmenio podatke o profilu na sistem naucne centrale.\n");
            return new ResponseEntity<>(new KorisnikDTO(k),HttpStatus.ACCEPTED);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/promenaLozinke")
    public ResponseEntity<?> promeniLozinku(@RequestBody KorisnikDTO k)
    {
        Korisnik korisnik = korisnik_service.findByKorisnicko_ime(AES256bit.encrypt(k.getKorisnicko_ime(),AES256bit.secretKey));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(korisnik != null)
        {
            System.out.println("Lozinka");
            if(passwordEncoder.matches(k.getLozinka(), korisnik.getLozinka()))
            {
                //ovde sam setovao novi password kroz EMAIL sam ga provukao
                korisnik.setLozinka(passwordEncoder.encode(k.getEmail()));
                korisnik_service.save(korisnik);
                logger.info("\n\t\tKorisnik " + k.getKorisnicko_ime() + " je uspesno promenio lozinku na svom nalogu.\n");
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        logger.info("\n\t\tNeuspesna promena lozinke u sistemu naucne centrale.\n");
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
            logger.info("\n\t\tKorisnik " + korisnicko_ime + " je potvrdio aktivaciju svog naloga.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("\n\t\tKorisnik nije potvrdio aktivaciju svog naloga.\n");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="/iRecenzent/{odobrio}")
    public ResponseEntity<?> zahtevZaRecenzenta(@RequestBody KorisnikDTO korisnik, @PathVariable Integer odobrio)
    {
       Korisnik k = korisnik_service.findByKorisnicko_ime(AES256bit.encrypt(korisnik.getKorisnicko_ime(),AES256bit.secretKey));

            if(odobrio == 1)
            {
                k.setTipKorisnika(TipKorisnika.RECENZENT);
                korisnik_service.save(k);
            }else
            {
                k.setRecenzent(false);
                korisnik_service.save(k);
            }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="/registracijaUrednika")
    public ResponseEntity<?> registracijaUrednika(@RequestBody KorisnikDTO korisnik)
    {
        Korisnik kor = korisnik_service.findByKorisnicko_ime(AES256bit.encrypt(korisnik.getKorisnicko_ime(),AES256bit.secretKey));

        if(kor != null)
        {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }else
        {
            Korisnik k = new Korisnik();
            k.setIme(korisnik.getIme());
            k.setPrezime(korisnik.getPrezime());
            k.setGrad(korisnik.getGrad());
            k.setDrzava(korisnik.getDrzava());
            k.setTitula(korisnik.getTitula());
            k.setEmail(korisnik.getEmail());
            k.setKorisnickoIme(korisnik.getKorisnicko_ime());
            k.setLozinka(korisnik.getLozinka());
            k.setTipKorisnika(korisnik.getTipKorisnika());
            k.setAktiviran_nalog(true);
            k.setId_casopisa(new HashSet<>());
            Set<Naucna_oblast> no_list = new HashSet<>();
            for(Naucna_oblastDTO n : korisnik.getNaucne_oblasti())
            {
                Naucna_oblast no = nos.getByNaziv(n.getNaziv());
                no_list.add(no);
            }
            k.setNaucne_oblasti(no_list);

            korisnik_service.save(k);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
