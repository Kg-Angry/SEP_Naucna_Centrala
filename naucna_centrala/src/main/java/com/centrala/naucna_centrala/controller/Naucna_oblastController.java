package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.Naucna_oblastDTO;
import com.centrala.naucna_centrala.model.Naucna_oblast;
import com.centrala.naucna_centrala.service.Naucna_oblast_service;
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
        Naucna_oblast no = new Naucna_oblast();
        no.setNaziv(noDTO.getNaziv());
        no.setOpis(noDTO.getOpis());

        nos.save(no);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/izmeniOblast")
    public ResponseEntity<?> izmeniOblast(@RequestBody Naucna_oblastDTO noDTO)
    {
        Naucna_oblast no = nos.getByNaziv(noDTO.getNaziv());

        if(no!=null)
        {
            no.setOpis(noDTO.getOpis());
            nos.save(no);
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @RequestMapping(value = "/obrisi/{naziv}", method = RequestMethod.DELETE)
    public ResponseEntity<?> obrisiNaucnuOblast(@PathVariable String naziv)
    {
        Naucna_oblast no = nos.getByNaziv(naziv);

        if(no!=null) {
            nos.remove(naziv);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
