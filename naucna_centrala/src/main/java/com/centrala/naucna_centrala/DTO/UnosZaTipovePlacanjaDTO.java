package com.centrala.naucna_centrala.DTO;

import com.centrala.naucna_centrala.model.UnosZaTipovePlacanja;

public class UnosZaTipovePlacanjaDTO {

    private Long id;
    private String tipPlacanja;
    private boolean popunjeno;

    public UnosZaTipovePlacanjaDTO(Long id, String tipPlacanja, boolean popunjeno) {
        this.id = id;
        this.tipPlacanja = tipPlacanja;
        this.popunjeno = popunjeno;
    }

    public UnosZaTipovePlacanjaDTO(UnosZaTipovePlacanja u)
    {
        this(u.getId(),u.getTipPlacanja(),u.isPopunjeno());
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
