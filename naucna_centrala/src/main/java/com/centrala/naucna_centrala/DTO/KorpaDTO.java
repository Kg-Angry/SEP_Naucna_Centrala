package com.centrala.naucna_centrala.DTO;

import com.centrala.naucna_centrala.model.Korpa;
import com.centrala.naucna_centrala.model.Naucni_casopis;
import com.centrala.naucna_centrala.model.Naucni_rad;

import java.util.HashSet;
import java.util.Set;

public class KorpaDTO {

    private Long id;
    private Set<Naucni_casopisDTO> naucni_casopis_list = new HashSet<>();
    private Set<Naucni_radDTO> naucni_rad_list = new HashSet<>();

    public KorpaDTO()
    {

    }

    public KorpaDTO(Long id,Set<Naucni_casopis> naucni_casopis, Set<Naucni_rad> naucni_rad)
    {
        this.id=id;
        for(Naucni_casopis n : naucni_casopis)
        {
            naucni_casopis_list.add(new Naucni_casopisDTO(n));
        }
        for(Naucni_rad r : naucni_rad)
        {
            naucni_rad_list.add(new Naucni_radDTO(r));
        }
    }

    public KorpaDTO(Korpa k)
    {
        this(k.getId(),k.getNaucni_casopis(),k.getNaucni_rad());
    }

    public Set<Naucni_casopisDTO> getNaucni_casopis_list() {
        return naucni_casopis_list;
    }

    public void setNaucni_casopis_list(Set<Naucni_casopisDTO> naucni_casopis_list) {
        this.naucni_casopis_list = naucni_casopis_list;
    }

    public Set<Naucni_radDTO> getNaucni_rad_list() {
        return naucni_rad_list;
    }

    public void setNaucni_rad_list(Set<Naucni_radDTO> naucni_rad_list) {
        this.naucni_rad_list = naucni_rad_list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
