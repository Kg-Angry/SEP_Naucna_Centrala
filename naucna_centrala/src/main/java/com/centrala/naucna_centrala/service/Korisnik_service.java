package com.centrala.naucna_centrala.service;

import com.centrala.naucna_centrala.model.Korisnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.centrala.naucna_centrala.repository.Korisnik_repository;

import java.util.List;

@Service
@Transactional
public class Korisnik_service {

    @Autowired
    private Korisnik_repository kr;

    public Korisnik findOne(Long id)
    {
        return kr.getOne(id);
    }

    public void remove (String korisnicko_ime)
    {
        kr.deleteByKorisnickoIme(korisnicko_ime);
    }

    public Korisnik findByKorisnicko_ime (String korisnicko_ime)
    {
        return kr.findByKorisnickoIme(korisnicko_ime);
    }

    public List<Korisnik> findAll()
    {
        return kr.findAll();
    }

    public Korisnik save(Korisnik k)
    {
        return kr.save(k);
    }
}
