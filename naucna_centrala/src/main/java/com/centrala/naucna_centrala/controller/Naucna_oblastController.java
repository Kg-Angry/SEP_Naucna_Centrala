package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.Naucna_oblastDTO;
import com.centrala.naucna_centrala.model.Naucna_oblast;
import com.centrala.naucna_centrala.service.Naucna_oblast_service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "api/naucna_oblast")
public class Naucna_oblastController {

    @Autowired
    private Naucna_oblast_service nos;

    private static final Logger logger = LoggerFactory.getLogger(Naucna_oblastController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/sveOblasti")
    public ResponseEntity<List<Naucna_oblastDTO>> sve_naucne_oblasti()
    {
        List<Naucna_oblast> no = nos.findAll();
        List<Naucna_oblastDTO> noDTO = new ArrayList<>();

        for(Naucna_oblast n : no)
        {
            noDTO.add(new Naucna_oblastDTO(n));
        }

        return new ResponseEntity<>(noDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/kreirajOblast")
    public ResponseEntity<?> kreirajOblast(@RequestBody Naucna_oblastDTO noDTO)
    {
        Naucna_oblast noModel = nos.getByNaziv(noDTO.getNaziv());
        if(noModel == null) {
            Naucna_oblast no = new Naucna_oblast();
            no.setNaziv(noDTO.getNaziv());
            no.setOpis(noDTO.getOpis());

            nos.save(no);
            logger.info("\n\t\tKreirana je naucna oblast "+ no.getNaziv()+" u sistem naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            logger.info("\n\t\tNije uspesno kreirana naucna oblast .\n");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/izmeniOblast")
    public ResponseEntity<?> izmeniOblast(@RequestBody Naucna_oblastDTO noDTO)
    {
        Naucna_oblast no = nos.getByNaziv(noDTO.getNaziv());

        if(no!=null)
        {
            no.setOpis(noDTO.getOpis());
            nos.save(no);
            logger.info("\n\t\tNaucna oblast "+ no.getNaziv()+" je uspesno izmenjena u sistem naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            logger.info("\n\t\tNije uspesno izmenjena naucna oblast.\n");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/obrisi/{naziv}", method = RequestMethod.DELETE)
    public ResponseEntity<?> obrisiNaucnuOblast(@PathVariable String naziv)
    {
        Naucna_oblast no = nos.getByNaziv(naziv);

        if(no!=null) {
            nos.remove(naziv);
            logger.info("\n\t\tNaucna oblast "+ no.getNaziv()+" je uspesno obrisana iz sistema naucne centrale.\n");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            logger.info("\n\t\tProblem prilikom brisanja naucne oblasti!!!\n");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
