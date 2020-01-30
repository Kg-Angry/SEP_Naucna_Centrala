package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.KorisnikDTO;
import com.centrala.naucna_centrala.DTO.Naucni_casopisDTO;
import com.centrala.naucna_centrala.DTO.Naucni_radDTO;
import com.centrala.naucna_centrala.Security.AES256bit;
import com.centrala.naucna_centrala.model.*;
import com.centrala.naucna_centrala.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping(value = "api/naucni_rad")
public class Naucni_radController {

    @Autowired
    private Naucni_rad_service nrs;
    @Autowired
    private Naucna_oblast_service nos;
    @Autowired
    private Korisnik_service korisnikService;
    @Autowired
    private Naucni_casopis_service ncs;
    @Autowired
    ServletContext context;
    @Autowired
    private Korpa_service korpa_service;

    private static final Logger logger = LoggerFactory.getLogger(Naucni_radController.class);

    @RequestMapping(method = RequestMethod.POST, value="/kreiraj")
    public ResponseEntity<?> kreirajRad(@RequestBody Naucni_radDTO rad)
    {

        Naucni_rad nr = nrs.findByNaslov(rad.getNaslov());
        Set<Korisnik> Koautori = new HashSet<>();
        if(nr == null)
        {
            Naucni_rad naucni_rad = new Naucni_rad();
            naucni_rad.setNaslov(rad.getNaslov());
            for(KorisnikDTO kDTO : rad.getKoautori())
            {
                Korisnik k = korisnikService.findByKorisnicko_ime(kDTO.getKorisnicko_ime());
                    if(k != null)
                        Koautori.add(k);
            }
            naucni_rad.setKoautori(Koautori);
            Naucni_casopis c = ncs.findByNaziv(rad.getNaucni_casopis().getNaziv());
            if( c != null)
                naucni_rad.setNaucni_casopis(c);
            naucni_rad.setKljucni_pojmovi(rad.getKljucni_pojmovi());
            naucni_rad.setApstrakt(rad.getApstrakt());
            Naucna_oblast no = nos.getByNaziv(rad.getOblast_pripadanja().getNaziv());
            naucni_rad.setOblast_pripadanja(no);
            naucni_rad.setPutanja_upload_fajla(rad.getPutanja_upload_fajla());
            Korisnik k = korisnikService.findByKorisnicko_ime(AES256bit.encrypt(rad.getAutor().getKorisnicko_ime(),AES256bit.secretKey));
            naucni_rad.setAutor(k);
            naucni_rad.setCena(rad.getCena());
            nrs.save(naucni_rad);
            logger.info("\n\t\tKreiran je naucni rad "+ naucni_rad.getNaslov()+" u sistem naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            logger.info("\n\t\tNije uspesno kreiran rad.\n");
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/uploadFile/{naslov}")
    public ResponseEntity<?> uploadFile(@RequestBody Naucni_radDTO ncDTO,@PathVariable String naslov) {

        Naucni_rad nr = nrs.findByNaslov(naslov);
        System.out.println("Naziv fajla: " + ncDTO.getPutanja_upload_fajla());
        if (!ncDTO.getPutanja_upload_fajla().isEmpty()) {
                Path currentWorkingDir = Paths.get("").toAbsolutePath();
                String realPathtoUploads = currentWorkingDir.normalize().toString()+"/src/main/resources/upload/";
                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdir();
                }

                String filePath = realPathtoUploads + ncDTO.getPutanja_upload_fajla();
                nr.setPutanja_upload_fajla(filePath);
                File dest = new File(filePath);

                nrs.save(nr);
                logger.info("\n\t\tUspesno je upload-ovan PDF fajl "+ ncDTO.getPutanja_upload_fajla() +" u naucni rad u sistem naucne centrale.\n");
                return new ResponseEntity<>(HttpStatus.CREATED);

    }
        logger.info("\n\t\tNije moguce dodati pdf fajl u naucni casopis.\n");
        return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sviRadovi")
    public ResponseEntity<List<Naucni_radDTO>> svi_naucni_radovi()
    {
        List<Naucni_rad> naucni_rad = nrs.findAll();
        List<Naucni_radDTO> naucni_radDTO = new ArrayList<>();

        for(Naucni_rad nr : naucni_rad)
        {
            naucni_radDTO.add(new Naucni_radDTO(nr));
        }

        return new ResponseEntity<>(naucni_radDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/izmeniRad")
    public ResponseEntity<?> izmeniRad(@RequestBody Naucni_radDTO nrDTO)
    {
        Naucni_rad nr = nrs.findByNaslov(nrDTO.getNaslov());
        Set<Korisnik> Koautori = new HashSet<>();
        if(nr != null)
        {
            for(KorisnikDTO kDTO : nrDTO.getKoautori())
            {
                Korisnik k = korisnikService.findByKorisnicko_ime(kDTO.getKorisnicko_ime());
                Koautori.add(k);
            }
            nr.setKoautori(Koautori);
            nr.setKljucni_pojmovi(nrDTO.getKljucni_pojmovi());
            nr.setApstrakt(nrDTO.getApstrakt());
            Naucna_oblast no = nos.getByNaziv(nrDTO.getOblast_pripadanja().getNaziv());
            nr.setOblast_pripadanja(no);
            nr.setPutanja_upload_fajla(nrDTO.getPutanja_upload_fajla());

            nrs.save(nr);
            logger.info("\n\t\tNaucni rad"+nr.getNaslov()+" je uspesno izmenjen.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("\n\t\tNaucni rad nije uspesno izmenjen!\n");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/obrisiRad/{naziv}")
    public ResponseEntity<Void> obrisiRad(@PathVariable String naziv)
    {
        Naucni_rad nr = nrs.findByNaslov(naziv);
        if(nr != null)
        {
            nrs.remove(naziv);
            logger.info("\n\t\tUspesno je obrisan "+ nr.getNaslov() +" naucni rad.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.info("\n\t\tNije uspesno obrisan"+ naziv +" naucni rad.\n");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
                Naucni_rad nr = nrs.findByNaslov(r.getNaslov());
                nr_list.add(nr);
            }
            kreiraj_korpu.setNaucni_rad(nr_list);
            korpa_service.save(kreiraj_korpu);
            logger.info("\n\t\tUspesno su dodati radovi u korpu.\n");
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
                Naucni_rad nr = nrs.findByNaslov(r.getNaslov());
                nr_list.add(nr);
            }
            korpa.setNaucni_rad(nr_list);
            korpa_service.save(korpa);
            logger.info("\n\t\tUspesno su dodati radovi u korpu.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(value="/izbaciIzKorpe/{id_rada}")
    public ResponseEntity<?> izbaciIzKorpe(@RequestBody KorisnikDTO k, @PathVariable Long id_rada)
    {
        Korisnik korisnik = korisnikService.findByKorisnicko_ime(AES256bit.encrypt(k.getKorisnicko_ime(),AES256bit.secretKey));
        Korpa korpa = korisnik.getKorpa();
        System.out.println("Korpa: " + korpa.getId());
        Set<Naucni_rad> nc_list = new HashSet<>();
        for(Naucni_rad n : korisnik.getKorpa().getNaucni_rad())
        {
            System.out.println("U korpi ima: "+n.getNaslov());
            if(n.getId() != id_rada)
            {
                nc_list.add(n);
            }
        }
        korpa.setNaucni_rad(nc_list);
        korpa_service.save(korpa);
        korisnik.setKorpa(korpa);
        korisnikService.save(korisnik);
        logger.info("\n\t\tUspesno je izbacen rad sa ID: "+ id_rada +" iz korpe.\n");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
