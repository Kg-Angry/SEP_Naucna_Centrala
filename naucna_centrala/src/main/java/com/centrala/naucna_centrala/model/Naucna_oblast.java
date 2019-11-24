package com.centrala.naucna_centrala.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Naucna_oblast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="naziv",nullable = false)
    private String naziv;

    @Column(name="opis",nullable = false)
    private String opis;


    public Naucna_oblast()
    {

    }

    public Naucna_oblast(String naziv, String opis) {
        this.naziv = naziv;
        this.opis = opis;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

}
