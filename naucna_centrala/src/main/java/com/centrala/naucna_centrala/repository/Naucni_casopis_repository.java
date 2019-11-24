package com.centrala.naucna_centrala.repository;

import com.centrala.naucna_centrala.model.Naucni_casopis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Naucni_casopis_repository extends JpaRepository<Naucni_casopis, Long> {

    Naucni_casopis findByNaziv(String naziv);
    void deleteByNaziv(String naziv);
}
