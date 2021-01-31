package com.centrala.naucna_centrala.service;

import com.centrala.naucna_centrala.model.Korpa;
import com.centrala.naucna_centrala.repository.Korpa_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Korpa_service {

    @Autowired
    private Korpa_repository korpa_repository;

    public Korpa findById(Long id)
    {
        return korpa_repository.getOne(id);
    }

    public List<Korpa> findAll()
    {
        return korpa_repository.findAll();
    }

    public void save(Korpa k)
    {
        korpa_repository.save(k);
    }

    public void remove(Long id)
    {
        korpa_repository.deleteAllById(id);
    }
}
