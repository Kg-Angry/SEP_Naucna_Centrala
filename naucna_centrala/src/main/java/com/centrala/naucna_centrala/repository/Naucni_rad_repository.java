package com.centrala.naucna_centrala.repository;

import com.centrala.naucna_centrala.model.Naucni_rad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Naucni_rad_repository extends JpaRepository<Naucni_rad, Long> {

    Naucni_rad findByNaslov(String naslov);

    void deleteByNaslov(String naslov);
}
