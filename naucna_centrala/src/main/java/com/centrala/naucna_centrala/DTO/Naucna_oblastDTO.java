package com.centrala.naucna_centrala.DTO;

import com.centrala.naucna_centrala.model.Naucna_oblast;
import com.centrala.naucna_centrala.model.Naucni_casopis;
import com.centrala.naucna_centrala.model.Naucni_rad;

import java.util.Set;

public class Naucna_oblastDTO {

    private Long id;
    private String naziv;
    private String opis;


    public Naucna_oblastDTO()
    {

    }

    public Naucna_oblastDTO(Long id, String naziv, String opis) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
    }

    public Naucna_oblastDTO(Naucna_oblast no)
    {
        this(no.getId(),no.getNaziv(),no.getOpis());
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
