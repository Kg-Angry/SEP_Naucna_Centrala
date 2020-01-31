package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.*;
import com.centrala.naucna_centrala.Security.AES256bit;
import com.centrala.naucna_centrala.model.*;
import com.centrala.naucna_centrala.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/naucni_casopis")
public class Naucni_casopisController {

    @Autowired
    private Naucni_casopis_service ncs;
    @Autowired
    private Naucna_oblast_service nos;
    @Autowired
    private Korisnik_service ks;
    @Autowired
    private Naucni_rad_service naucni_rad_service;
    @Autowired
    private EmailService email;
    @Autowired
    private Korpa_service korpa_service;

    private static final Logger logger = LoggerFactory.getLogger(Naucni_casopisController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/sviCasopisi")
    public ResponseEntity<List<Naucni_casopisDTO>> svi_casopisi()
    {
        List<Naucni_casopis> nc = ncs.findAll();
        List<Naucni_casopisDTO> ncDTO = new ArrayList<>();

        for(Naucni_casopis n : nc)
        {
            if(n != null) {
                n.getGlavni_urednik().setKorisnickoIme(AES256bit.decrypt(n.getGlavni_urednik().getKorisnickoIme(),AES256bit.secretKey));
                ncDTO.add(new Naucni_casopisDTO(n));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(ncDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/kreirajCasopis")
    public ResponseEntity<?> kreirajCasopis(@RequestBody Naucni_casopisDTO ncDTO)
    {
        Naucni_casopis nc = ncs.findByNaziv(ncDTO.getNaziv());
        Set<Korisnik> urednici = new HashSet<>();
        Set<Korisnik> recenzenti = new HashSet<>();
        Set<Naucna_oblast> naucna_oblast = new HashSet<>();
        Set<UnosZaTipovePlacanja> unosZaTipovePlacanja = new HashSet<>();

        if(nc == null)
        {
            Naucni_casopis naucni_casopis = new Naucni_casopis();
            naucni_casopis.setNaziv(ncDTO.getNaziv());
            naucni_casopis.setIssn(ncDTO.getIssn());
            naucni_casopis.setTipCasopisa(ncDTO.getTipCasopisa());
            System.out.println("Korisnik ime: " + ncDTO.getGlavni_urednik().getKorisnicko_ime());
            Korisnik k = ks.findByKorisnicko_ime(AES256bit.encrypt(ncDTO.getGlavni_urednik().getKorisnicko_ime(),AES256bit.secretKey));

            if(k != null)
            {
                naucni_casopis.setGlavni_urednik(k);
            }

            for(KorisnikDTO k1 : ncDTO.getUrednici())
            {

                Korisnik k2 = ks.findByKorisnicko_ime(AES256bit.encrypt(k1.getKorisnicko_ime(),AES256bit.secretKey));

                if( k2 != null)
                    urednici.add(k2);
            }
            naucni_casopis.setUrednici(urednici);

            for(KorisnikDTO k1 : ncDTO.getRecenzent())
            {
                Korisnik k2 = ks.findByKorisnicko_ime(AES256bit.encrypt(k1.getKorisnicko_ime(),AES256bit.secretKey));
                if( k2 != null)
                    recenzenti.add(k2);
            }
            naucni_casopis.setRecenzent(recenzenti);

            for(Naucna_oblastDTO no : ncDTO.getNaucna_oblast())
            {
                Naucna_oblast n = nos.getByNaziv(no.getNaziv());

                if(n != null)
                    naucna_oblast.add(n);

            }
            naucni_casopis.setNaucna_oblast(naucna_oblast);

            naucni_casopis.setCena(ncDTO.getCena());
            naucni_casopis.setStatus(false);
            for(UnosZaTipovePlacanjaDTO u : ncDTO.getUnosTipova())
            {
                UnosZaTipovePlacanja u1 = new UnosZaTipovePlacanja();
                u1.setTipPlacanja(u.getTipPlacanja());
                u1.setPopunjeno(u.isPopunjeno());
                unosZaTipovePlacanja.add(u1);
            }
            naucni_casopis.setUnosTipova(unosZaTipovePlacanja);
            naucni_casopis = ncs.save(naucni_casopis);
            logger.info("\n\t\tKreiran je casopis "+ naucni_casopis.getNaziv()+", a kreirao ga je "+ naucni_casopis.getGlavni_urednik().getKorisnickoIme() +" na sistem naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }else
        {logger.info("\n\t\tCasopis nije uspesno kreiran ili vec postoji u bazi!!!.\n");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
    }

    @RequestMapping(method = RequestMethod.PUT, value="/izmeniCasopis")
    public ResponseEntity<?> izmeniCasopis(@RequestBody Naucni_casopisDTO ncDTO)
    {
        Naucni_casopis nc = ncs.findOne(ncDTO.getId());
        Set<Korisnik> urednici = new HashSet<>();
        Set<Korisnik> recenzenti = new HashSet<>();
        Set<Naucna_oblast> naucna_oblast = new HashSet<>();
        if(nc != null)
        {
            nc.setNaziv(ncDTO.getNaziv());
            nc.setIssn(ncDTO.getIssn());
            nc.setTipCasopisa(ncDTO.getTipCasopisa());

//            for(KorisnikDTO korisnikDTO : ncDTO.getUrednici())
//            {
//                Korisnik kor = ks.findByKorisnicko_ime(AES256bit.encrypt(korisnikDTO.getKorisnicko_ime(),AES256bit.secretKey));
//                if( kor != null)
//                    urednici.add(kor);
//
//            }
//            nc.setUrednici(urednici);
//
//            for(KorisnikDTO k1 : ncDTO.getRecenzent())
//            {
//                Korisnik k2 = ks.findByKorisnicko_ime(AES256bit.encrypt(k1.getKorisnicko_ime(),AES256bit.secretKey));
//                if( k2 != null)
//                    recenzenti.add(k2);
//            }
//            nc.setRecenzent(recenzenti);

            for(Naucna_oblastDTO no : ncDTO.getNaucna_oblast())
            {
                Naucna_oblast n = nos.getByNaziv(no.getNaziv());

                if(n != null)
                    naucna_oblast.add(n);

            }
            nc.setNaucna_oblast(naucna_oblast);

            nc.setCena(ncDTO.getCena());
            ncs.save(nc);
            logger.info("\n\t\tCasopis "+ nc.getNaziv() +", izmenjen od strane "+ nc.getGlavni_urednik().getKorisnickoIme()+".\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }else
        {logger.info("\n\t\tCasopis nije uspesno izmenjen.\n");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/obrisiCasopis/{naziv}")
    public ResponseEntity<?> obrisiCasopis(@PathVariable String naziv)
    {
        Naucni_casopis nc = ncs.findByNaziv(naziv);

        if(nc != null)
        {
            ncs.remove(naziv);
            logger.info("\n\t\tCasopis "+ nc.getNaziv() +" je uspesno obrisan.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }else
        {logger.info("\n\t\tCasopis nije uspesno obrisan.\n");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
    }

    @PostMapping(value="/aktivirajCasopis/{dopuniti}/{text}")
    public void dopunitiCasopis(@RequestBody Naucni_casopisDTO ncDTO,@PathVariable Integer dopuniti, @PathVariable String text)
    {

        Naucni_casopis nc = ncs.findByNaziv(ncDTO.getNaziv());
        if(nc != null){
            if(dopuniti == 1) {
                nc.setDopuniti(true);
                email.sendMailDupunaCasopisa(nc,nc.getGlavni_urednik(),text);
            }
            else {
                nc.setDopuniti(false);
            }
            nc.setStatus(true);
            ncs.save(nc);
        }
    }

    //kroz korisnika progurati korpu sa artiklima!!!!
    @PostMapping(value="/dodajUKorpu")
    public ResponseEntity<?> dodajUKorpu(@RequestBody KorisnikDTO korisnik)
    {
        Korpa korpa = korpa_service.findById(korisnik.getKorpa().getId());
        if(korpa == null)
        {
            Korpa kreiraj_korpu = new Korpa();
            Set<Naucni_casopis> nc_list = new HashSet<>();
            Set<Naucni_rad> nr_list = new HashSet<>();

            for(Naucni_casopisDTO n : korisnik.getKorpa().getNaucni_casopis_list())
            {
                Naucni_casopis nc = ncs.findByNaziv(n.getNaziv());
                nc_list.add(nc);
            }
            kreiraj_korpu.setNaucni_casopis(nc_list);
            for(Naucni_radDTO r : korisnik.getKorpa().getNaucni_rad_list())
            {
                Naucni_rad nr = naucni_rad_service.findByNaslov(r.getNaslov());
                nr_list.add(nr);
            }
            kreiraj_korpu.setNaucni_rad(nr_list);
            korpa_service.save(kreiraj_korpu);
            return new ResponseEntity<>(HttpStatus.OK);
        }else
        {
            Set<Naucni_casopis> nc_list = korpa.getNaucni_casopis();
            Set<Naucni_rad> nr_list = korpa.getNaucni_rad();
            for(Naucni_casopisDTO n : korisnik.getKorpa().getNaucni_casopis_list())
            {
                Naucni_casopis nc = ncs.findByNaziv(n.getNaziv());
                nc_list.add(nc);
            }
            korpa.setNaucni_casopis(nc_list);
            for(Naucni_radDTO r : korisnik.getKorpa().getNaucni_rad_list())
            {
                Naucni_rad nr = naucni_rad_service.findByNaslov(r.getNaslov());
                nr_list.add(nr);
            }
            korpa.setNaucni_rad(nr_list);
            korpa_service.save(korpa);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(value="/izbaciIzKorpe/{id_casopisa}")
    public ResponseEntity<?> izbaciIzKorpe(@RequestBody KorisnikDTO k, @PathVariable Long id_casopisa)
    {
        Korisnik korisnik = ks.findByKorisnicko_ime(AES256bit.encrypt(k.getKorisnicko_ime(),AES256bit.secretKey));
        Korpa korpa = korisnik.getKorpa();
        System.out.println("Korpa: " + korpa.getId());
        Set<Naucni_casopis> nc_list = new HashSet<>();
        for(Naucni_casopis n : korisnik.getKorpa().getNaucni_casopis())
        {
            System.out.println("U korpi ima: "+n.getNaziv());
            if(n.getId() != id_casopisa)
            {
                nc_list.add(n);
            }
        }
        korpa.setNaucni_casopis(nc_list);
        korpa_service.save(korpa);
        korisnik.setKorpa(korpa);
        ks.save(korisnik);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value="/popunjenaForma/{nacin}")
    public ResponseEntity<?> popunjenaForma(@RequestBody Naucni_casopisDTO naucni_casopisDTO, @PathVariable String nacin)
    {
        Naucni_casopis naucni_casopis=ncs.findByNaziv(naucni_casopisDTO.getNaziv());

        if(naucni_casopis!=null)
        {
            for(UnosZaTipovePlacanja t : naucni_casopis.getUnosTipova())
            {
                if(t.getTipPlacanja().equals(nacin))
                {
                    t.setPopunjeno(true);
                    ncs.save(naucni_casopis);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
