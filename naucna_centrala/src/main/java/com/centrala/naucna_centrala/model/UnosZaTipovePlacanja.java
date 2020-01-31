package com.centrala.naucna_centrala.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
@Entity
public class UnosZaTipovePlacanja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipPlacanja;
    private boolean popunjeno;

    @ManyToOne
    @JoinColumn(name="casopis_id")
    private Naucni_casopis naucniCasopis;

    public UnosZaTipovePlacanja() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipPlacanja() {
        return tipPlacanja;
    }

    public void setTipPlacanja(String tipPlacanja) {
        this.tipPlacanja = tipPlacanja;
    }

    public boolean isPopunjeno() {
        return popunjeno;
    }

    public void setPopunjeno(boolean popunjeno) {
        this.popunjeno = popunjeno;
    }
}
