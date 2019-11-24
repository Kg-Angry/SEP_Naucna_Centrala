package com.centrala.naucna_centrala.model;

import org.hibernate.annotations.Columns;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Naucni_rad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="naslov", nullable = false, length = 255)
    private String naslov;

    //vise koautor-a u jednom radu
    @OneToMany(mappedBy = "id")
    private Set<Korisnik> koautori = new HashSet<>();

    @Column(name="kljucni_pojmovi", nullable = false, length = 255)
    private String kljucni_pojmovi;

    @Column(name="apstrakt", nullable = false, length = 255)
    private String apstrakt;

    @OneToOne
    private Naucna_oblast oblast_pripadanja;

    @Column(name="putanja_upload_fajla", nullable = false, length = 255)
    private String putanja_upload_fajla;

    @ManyToOne
    private Naucni_casopis naucni_casopis;

    public Naucni_rad()
    {

    }

    public Naucni_rad(String naslov, Set<Korisnik> koautori, String kljucni_pojmovi, String apstrakt, Naucna_oblast oblast_pripadanja, String putanja_upload_fajla, Naucni_casopis naucni_casopis) {
        this.naslov = naslov;
        this.koautori = koautori;
        this.kljucni_pojmovi = kljucni_pojmovi;
        this.apstrakt = apstrakt;
        this.oblast_pripadanja = oblast_pripadanja;
        this.putanja_upload_fajla = putanja_upload_fajla;
        this.naucni_casopis = naucni_casopis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public Set<Korisnik> getKoautori() {
        return koautori;
    }

    public void setKoautori(Set<Korisnik> koautori) {
        this.koautori = koautori;
    }

    public String getKljucni_pojmovi() {
        return kljucni_pojmovi;
    }

    public void setKljucni_pojmovi(String kljucni_pojmovi) {
        this.kljucni_pojmovi = kljucni_pojmovi;
    }

    public String getApstrakt() {
        return apstrakt;
    }

    public void setApstrakt(String apstrakt) {
        this.apstrakt = apstrakt;
    }

    public Naucna_oblast getOblast_pripadanja() {
        return oblast_pripadanja;
    }

    public void setOblast_pripadanja(Naucna_oblast oblast_pripadanja) {
        this.oblast_pripadanja = oblast_pripadanja;
    }

    public String getPutanja_upload_fajla() {
        return putanja_upload_fajla;
    }

    public void setPutanja_upload_fajla(String putanja_upload_fajla) {
        this.putanja_upload_fajla = putanja_upload_fajla;
    }

    public Naucni_casopis getNaucni_casopis() {
        return naucni_casopis;
    }

    public void setNaucni_casopis(Naucni_casopis naucni_casopis) {
        this.naucni_casopis = naucni_casopis;
    }
}

