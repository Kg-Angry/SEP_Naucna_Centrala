package com.centrala.naucna_centrala.repository;

import com.centrala.naucna_centrala.model.Korpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Korpa_repository extends JpaRepository<Korpa,Long> {

    void deleteAllById(Long id);
}
