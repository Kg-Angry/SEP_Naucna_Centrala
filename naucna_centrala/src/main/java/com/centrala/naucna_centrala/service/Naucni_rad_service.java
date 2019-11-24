package com.centrala.naucna_centrala.service;

import com.centrala.naucna_centrala.model.Naucni_rad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.centrala.naucna_centrala.repository.Naucni_rad_repository;

import java.util.List;


@Service
@Transactional
public class Naucni_rad_service {

    @Autowired
    private Naucni_rad_repository nrr;

    public Naucni_rad findOne(Long id)
    {
        return nrr.getOne(id);
    }

    public List<Naucni_rad> findAll()
    {
        return nrr.findAll();
    }

    public Naucni_rad save(Naucni_rad nr)
    {
        return nrr.save(nr);
    }

    public Naucni_rad findByNaslov(String naslov){ return nrr.findByNaslov(naslov);}

    public void remove(String naslov)
    {
        nrr.deleteByNaslov(naslov);
    }
}
