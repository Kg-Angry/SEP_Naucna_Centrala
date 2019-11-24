package com.centrala.naucna_centrala.repository;

import com.centrala.naucna_centrala.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Korisnik_repository extends JpaRepository<Korisnik,Long> {

    void deleteByKorisnickoIme(String korisnicko_ime);
    Korisnik findByKorisnickoIme(String korisnicko_ime);
}
