package com.centrala.naucna_centrala.service;

import com.centrala.naucna_centrala.model.Naucni_casopis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.centrala.naucna_centrala.repository.Naucni_casopis_repository;

import java.util.List;

@Service
@Transactional
public class Naucni_casopis_service {

    @Autowired
    private Naucni_casopis_repository ncr;

    public Naucni_casopis findOne(Long id)
    {
        return ncr.getOne(id);
    }

    public List<Naucni_casopis> findAll()
    {
        return ncr.findAll();
    }

    public Naucni_casopis save(Naucni_casopis nc)
    {
        return ncr.save(nc);
    }

    public Naucni_casopis findByNaziv (String naziv)
    {
        return ncr.findByNaziv(naziv);
    }

    public void remove(String naziv)
    {
        ncr.deleteByNaziv(naziv);
    }
}
