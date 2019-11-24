package com.centrala.naucna_centrala.DTO;

import com.centrala.naucna_centrala.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

public class Naucni_radDTO {

    private Long id;
    private String naslov;
    private Set<KorisnikDTO> koautori = new HashSet<>();
    private String kljucni_pojmovi;
    private String apstrakt;
    private Naucna_oblastDTO oblast_pripadanja;
    private String putanja_upload_fajla;
    private Naucni_casopisDTO naucni_casopis;

    public Naucni_radDTO()
    {

    }

    public Naucni_radDTO(Long id, String naslov, Set<Korisnik> koautori, String kljucni_pojmovi, String apstrakt, Naucna_oblast oblast_pripadanja, String putanja_upload_fajla, Naucni_casopis naucni_casopis) {
        this.id = id;
        this.naslov = naslov;
        for(Korisnik k : koautori)
        {
            this.koautori.add(new KorisnikDTO(k));
        }
        this.kljucni_pojmovi = kljucni_pojmovi;
        this.apstrakt = apstrakt;
        this.oblast_pripadanja = new Naucna_oblastDTO(oblast_pripadanja);
        this.putanja_upload_fajla = putanja_upload_fajla;
        this.naucni_casopis = new Naucni_casopisDTO(naucni_casopis);
    }

    public Naucni_radDTO(Naucni_rad nr)
    {
        this(nr.getId(),nr.getNaslov(),nr.getKoautori(),nr.getKljucni_pojmovi(),nr.getApstrakt(),nr.getOblast_pripadanja(),nr.getPutanja_upload_fajla(),nr.getNaucni_casopis());
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

    public Set<KorisnikDTO> getKoautori() {
        return koautori;
    }

    public void setKoautori(Set<KorisnikDTO> koautori) {
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

    public Naucna_oblastDTO getOblast_pripadanja() {
        return oblast_pripadanja;
    }

    public void setOblast_pripadanja(Naucna_oblastDTO oblast_pripadanja) {
        this.oblast_pripadanja = oblast_pripadanja;
    }

    public String getPutanja_upload_fajla() {
        return putanja_upload_fajla;
    }

    public void setPutanja_upload_fajla(String putanja_upload_fajla) {
        this.putanja_upload_fajla = putanja_upload_fajla;
    }

    public Naucni_casopisDTO getNaucni_casopis() {
        return naucni_casopis;
    }

    public void setNaucni_casopis(Naucni_casopisDTO naucni_casopis) {
        this.naucni_casopis = naucni_casopis;
    }
}
