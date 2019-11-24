package com.centrala.naucna_centrala.service;

import com.centrala.naucna_centrala.model.Naucna_oblast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.centrala.naucna_centrala.repository.Naucna_oblast_repository;

import java.util.List;


@Service
@Transactional
public class Naucna_oblast_service {

    @Autowired
    private Naucna_oblast_repository nor;

    public Naucna_oblast findOne(Long id)
    {
        return nor.getOne(id);
    }

    public void remove(String naziv)
    {
        nor.deleteByNaziv(naziv);
    }

    public Naucna_oblast getByNaziv(String naziv)
    {
        return nor.getByNaziv(naziv);
    }

    public List<Naucna_oblast> findAll()
    {
        return nor.findAll();
    }

    public Naucna_oblast save(Naucna_oblast no)
    {
        return nor.save(no);
    }
}
