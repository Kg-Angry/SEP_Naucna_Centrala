package com.centrala.naucna_centrala.controller;

import com.centrala.naucna_centrala.DTO.KorisnikDTO;
import com.centrala.naucna_centrala.DTO.Naucna_oblastDTO;
import com.centrala.naucna_centrala.DTO.Naucni_casopisDTO;
import com.centrala.naucna_centrala.model.Korisnik;
import com.centrala.naucna_centrala.model.Naucna_oblast;
import com.centrala.naucna_centrala.model.Naucni_casopis;
import com.centrala.naucna_centrala.model.TipPlacanja;
import com.centrala.naucna_centrala.service.Korisnik_service;
import com.centrala.naucna_centrala.service.Naucna_oblast_service;
import com.centrala.naucna_centrala.service.Naucni_casopis_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET, value = "/sviCasopisi")
    public ResponseEntity<List<Naucni_casopisDTO>> svi_casopisi()
    {
        List<Naucni_casopis> nc = ncs.findAll();
        List<Naucni_casopisDTO> ncDTO = new ArrayList<>();

        for(Naucni_casopis n : nc)
        {
            if(n != null) {
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
        List<TipPlacanja> t = new ArrayList<>();

        if(nc == null)
        {
            Naucni_casopis naucni_casopis = new Naucni_casopis();
            naucni_casopis.setNaziv(ncDTO.getNaziv());
            naucni_casopis.setIssn(ncDTO.getIssn());
            naucni_casopis.setTipCasopisa(ncDTO.getTipCasopisa());
            Korisnik k = ks.findByKorisnicko_ime(ncDTO.getGlavni_urednik().getKorisnicko_ime());

            if(k != null)
            {
                naucni_casopis.setGlavni_urednik(k);
            }

            for(KorisnikDTO k1 : ncDTO.getUrednici())
            {

                Korisnik k2 = ks.findByKorisnicko_ime(k1.getKorisnicko_ime());

                if( k2 != null)
                    urednici.add(k2);
            }
            naucni_casopis.setUrednici(urednici);

            for(KorisnikDTO k1 : ncDTO.getRecenzent())
            {
                Korisnik k2 = ks.findByKorisnicko_ime(k1.getKorisnicko_ime());
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
            if(ncDTO.getTipoviPlacanja() != null) {
                for (TipPlacanja tip : ncDTO.getTipoviPlacanja()) {
                    t.add(tip);
                }
            }
            naucni_casopis.setTipoviPlacanja(t);
            naucni_casopis.setCena(ncDTO.getCena());
            naucni_casopis.setStatus(false);

            naucni_casopis = ncs.save(naucni_casopis);

            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/izmeniCasopis")
    public ResponseEntity<?> izmeniCasopis(@RequestBody Naucni_casopisDTO ncDTO)
    {
        Naucni_casopis nc = ncs.findByNaziv(ncDTO.getNaziv());
        Set<Korisnik> urednici = new HashSet<>();
        Set<Korisnik> recenzenti = new HashSet<>();
        Set<Naucna_oblast> naucna_oblast = new HashSet<>();
        if(nc != null)
        {
            nc.setIssn(ncDTO.getIssn());
            nc.setTipCasopisa(ncDTO.getTipCasopisa());

            for(KorisnikDTO korisnikDTO : ncDTO.getUrednici())
            {
                Korisnik kor = ks.findByKorisnicko_ime(korisnikDTO.getKorisnicko_ime());
                if( kor != null)
                    urednici.add(kor);

            }
            nc.setUrednici(urednici);

            for(KorisnikDTO k1 : ncDTO.getRecenzent())
            {
                Korisnik k2 = ks.findByKorisnicko_ime(k1.getKorisnicko_ime());
                if( k2 != null)
                    recenzenti.add(k2);
            }
            nc.setRecenzent(recenzenti);

            for(Naucna_oblastDTO no : ncDTO.getNaucna_oblast())
            {
                Naucna_oblast n = nos.getByNaziv(no.getNaziv());

                if(n != null)
                    naucna_oblast.add(n);

            }
            nc.setNaucna_oblast(naucna_oblast);

            nc.setTipoviPlacanja(ncDTO.getTipoviPlacanja());
            nc.setCena(ncDTO.getCena());
            ncs.save(nc);
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/obrisiCasopis/{naziv}")
    public ResponseEntity<?> obrisiCasopis(@PathVariable String naziv)
    {
        Naucni_casopis nc = ncs.findByNaziv(naziv);

        if(nc != null)
        {
            ncs.remove(naziv);
            return new ResponseEntity<>(HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
