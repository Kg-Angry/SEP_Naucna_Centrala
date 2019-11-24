package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.KorisnikDTO;
import com.centrala.naucna_centrala.model.Korisnik;
import com.centrala.naucna_centrala.service.Korisnik_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@RestController
@RequestMapping(value = "api/korisnik")
public class KorisnikController {

    @Autowired
    private Korisnik_service korisnik_service;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = "/registracijaKorisnika")
    public ResponseEntity<Void> registracijaKorisnika(@RequestBody KorisnikDTO korisnik)
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
        k.setAktiviran_nalog(false);
        k.setId_casopisa(new HashSet<>());
        k.setRecenzenti(new HashSet<>());

        korisnik_service.save(k);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logovanje")
    public ResponseEntity<?> logovanjeKorisnika(@RequestBody KorisnikDTO k)
    {
        List<Korisnik> nadjen = korisnik_service.findAll();

        //obratiti paznju da se nalog mora aktivirati
        for(Korisnik kor : nadjen)
        {
            if(kor.getKorisnickoIme().equals(k.getKorisnicko_ime()) && kor.getLozinka().equals(k.getLozinka())) {
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
}
