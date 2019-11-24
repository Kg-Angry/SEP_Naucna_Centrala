package com.centrala.naucna_centrala.repository;

import com.centrala.naucna_centrala.model.Naucna_oblast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Naucna_oblast_repository extends JpaRepository<Naucna_oblast, Long> {

    Naucna_oblast getByNaziv(String naziv);
    void deleteByNaziv(String naziv);
}
