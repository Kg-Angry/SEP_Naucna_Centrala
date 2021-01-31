package com.centrala.naucna_centrala.DTO;

import com.centrala.naucna_centrala.model.*;

import java.util.*;

public class Naucni_casopisDTO {

    private Long id;
    private String naziv;
    private int issn;
    private TipCasopisa tipCasopisa;
    private KorisnikDTO glavni_urednik;
    private Set<KorisnikDTO> urednici = new HashSet<>();
    private Set<KorisnikDTO> recenzent = new HashSet<>();
    private Set<Naucna_oblastDTO> naucna_oblast = new HashSet<>();
    private boolean status;
    private boolean dopuniti;
    private Double cena;
    private Set<UnosZaTipovePlacanjaDTO> unosTipova = new HashSet<>();

    public Naucni_casopisDTO()
    {

    }

    public Naucni_casopisDTO(Long id, String naziv, int issn, TipCasopisa tipCasopisa, Korisnik glavni_urednik, Set<Korisnik> urednici, Set<Korisnik> recenzent, Set<Naucna_oblast> naucna_oblast, boolean status, double cena,boolean dopuniti, Set<UnosZaTipovePlacanja> unosZaTipovePlacanja) {
        this.id = id;
        this.naziv = naziv;
        this.issn = issn;
        this.tipCasopisa = tipCasopisa;
        this.glavni_urednik = new KorisnikDTO(glavni_urednik);
        for(Korisnik k : urednici)
        {
            this.urednici.add(new KorisnikDTO(k));
        }
        for(Korisnik k : recenzent)
        {
            this.recenzent.add(new KorisnikDTO(k));
        }
        for(Naucna_oblast n : naucna_oblast)
        {
            this.naucna_oblast.add(new Naucna_oblastDTO(n));
        }

        this.status = status;
        this.dopuniti=dopuniti;
        this.cena=cena;
        for(UnosZaTipovePlacanja u : unosZaTipovePlacanja)
        {
            this.unosTipova.add(new UnosZaTipovePlacanjaDTO(u));
        }
    }

    public Naucni_casopisDTO(Naucni_casopis nc)
    {
        this(nc.getId(),nc.getNaziv(),nc.getIssn(),nc.getTipCasopisa(),nc.getGlavni_urednik(),nc.getUrednici(),nc.getRecenzent(),nc.getNaucna_oblast(),nc.isStatus(),nc.getCena(),nc.isDopuniti(),nc.getUnosTipova());
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

    public int getIssn() {
        return issn;
    }

    public void setIssn(int issn) {
        this.issn = issn;
    }

    public TipCasopisa getTipCasopisa() {
        return tipCasopisa;
    }

    public void setTipCasopisa(TipCasopisa tipCasopisa) {
        this.tipCasopisa = tipCasopisa;
    }

    public KorisnikDTO getGlavni_urednik() {
        return glavni_urednik;
    }

    public void setGlavni_urednik(KorisnikDTO glavni_urednik) {
        this.glavni_urednik = glavni_urednik;
    }

    public Set<KorisnikDTO> getUrednici() {
        return urednici;
    }

    public void setUrednici(Set<KorisnikDTO> urednici) {
        this.urednici = urednici;
    }

    public Set<KorisnikDTO> getRecenzent() {
        return recenzent;
    }

    public void setRecenzent(Set<KorisnikDTO> recenzent) {
        this.recenzent = recenzent;
    }

    public Set<Naucna_oblastDTO> getNaucna_oblast() {
        return naucna_oblast;
    }

    public void setNaucna_oblast(Set<Naucna_oblastDTO> naucna_oblast) {
        this.naucna_oblast = naucna_oblast;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public boolean isDopuniti() {
        return dopuniti;
    }

    public void setDopuniti(boolean dopuniti) {
        this.dopuniti = dopuniti;
    }

    public Set<UnosZaTipovePlacanjaDTO> getUnosTipova() {
        return unosTipova;
    }

    public void setUnosTipova(Set<UnosZaTipovePlacanjaDTO> unosTipova) {
        this.unosTipova = unosTipova;
    }
}
