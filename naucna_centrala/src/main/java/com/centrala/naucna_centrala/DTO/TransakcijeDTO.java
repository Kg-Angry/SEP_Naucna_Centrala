package com.centrala.naucna_centrala.DTO;

public class TransakcijeDTO {

    private Long id;
    private String idTransakcije;
    private String uuid;
    private String naziv;
    private String vremeKreiranjaTransakcije;
    private String status;
    private String uplatilac;
    private String tipPlacanja;
    private String orderId;
    private Double cena;

    public TransakcijeDTO() {
    }

    public TransakcijeDTO(Long id, String idTransakcije, String uuid, String naziv, String vremeKreiranjaTransakcije, String status, String uplatilac, String tipPlacanja, String orderId, Double cena) {
        this.id = id;
        this.idTransakcije = idTransakcije;
        this.uuid = uuid;
        this.naziv = naziv;
        this.vremeKreiranjaTransakcije = vremeKreiranjaTransakcije;
        this.status = status;
        this.uplatilac = uplatilac;
        this.tipPlacanja = tipPlacanja;
        this.orderId = orderId;
        this.cena = cena;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTransakcije() {
        return idTransakcije;
    }

    public void setIdTransakcije(String idTransakcije) {
        this.idTransakcije = idTransakcije;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getVremeKreiranjaTransakcije() {
        return vremeKreiranjaTransakcije;
    }

    public void setVremeKreiranjaTransakcije(String vremeKreiranjaTransakcije) {
        this.vremeKreiranjaTransakcije = vremeKreiranjaTransakcije;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUplatilac() {
        return uplatilac;
    }

    public void setUplatilac(String uplatilac) {
        this.uplatilac = uplatilac;
    }

    public String getTipPlacanja() {
        return tipPlacanja;
    }

    public void setTipPlacanja(String tipPlacanja) {
        this.tipPlacanja = tipPlacanja;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }
}
