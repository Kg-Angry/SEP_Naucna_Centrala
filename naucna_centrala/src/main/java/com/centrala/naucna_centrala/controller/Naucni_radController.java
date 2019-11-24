package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.KorisnikDTO;
import com.centrala.naucna_centrala.DTO.Naucni_radDTO;
import com.centrala.naucna_centrala.model.*;
import com.centrala.naucna_centrala.service.Korisnik_service;
import com.centrala.naucna_centrala.service.Naucna_oblast_service;
import com.centrala.naucna_centrala.service.Naucni_casopis_service;
import com.centrala.naucna_centrala.service.Naucni_rad_service;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

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

            nrs.save(naucni_rad);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }else
            return new ResponseEntity<>(HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value="/uploadFile/{naslov}")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file,@PathVariable String naslov) throws IOException {

        Naucni_rad nr = nrs.findByNaslov(naslov);

        if (!file.isEmpty()) {
            try {
                Path currentWorkingDir = Paths.get("").toAbsolutePath();
                String realPathtoUploads = currentWorkingDir.normalize().toString()+"/src/main/resources/upload/";
                if (!new File(realPathtoUploads).exists()) {
                    new File(realPathtoUploads).mkdir();
                }

                String orgName = file.getOriginalFilename();
                String filePath = realPathtoUploads + orgName;
                nr.setPutanja_upload_fajla(filePath);
                File dest = new File(filePath);
                file.transferTo(dest);
                nrs.save(nr);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (IOException e)
            {
                System.out.println(e.getMessage());
            }

    }
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
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/obrisiRad/{naziv}")
    public ResponseEntity<Void> obrisiRad(@PathVariable String naziv)
    {
        Naucni_rad nr = nrs.findByNaslov(naziv);
        if(nr != null)
        {
            nrs.remove(naziv);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
