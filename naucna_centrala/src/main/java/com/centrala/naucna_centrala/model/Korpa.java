package com.centrala.naucna_centrala.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Korpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name="casopis_korpa",
            joinColumns = @JoinColumn( name = "casopis_id"),
            inverseJoinColumns = @JoinColumn (name = "korpa_id")
    )
    private Set<Naucni_casopis> naucni_casopis = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name="rad_korpa",
            joinColumns = @JoinColumn( name = "rad_id"),
            inverseJoinColumns = @JoinColumn (name = "korpa_id")
    )
    private Set<Naucni_rad> naucni_rad = new HashSet<>();

    public Korpa() {
    }

    public Korpa(Set<Naucni_casopis> nc , Set<Naucni_rad> nr)
    {
        this.naucni_casopis=nc;
        this.naucni_rad=nr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Naucni_casopis> getNaucni_casopis() {
        return naucni_casopis;
    }

    public void setNaucni_casopis(Set<Naucni_casopis> naucni_casopis) {
        this.naucni_casopis = naucni_casopis;
    }

    public Set<Naucni_rad> getNaucni_rad() {
        return naucni_rad;
    }

    public void setNaucni_rad(Set<Naucni_rad> naucni_rad) {
        this.naucni_rad = naucni_rad;
    }
}
