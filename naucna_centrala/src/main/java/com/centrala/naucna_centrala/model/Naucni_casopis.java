package com.centrala.naucna_centrala.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.annotations.Columns;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;

@Entity
public class Naucni_casopis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "naziv",nullable = false, length = 255)
    private String naziv;

    @Column(name="issn",nullable = false, length = 255)
    private int issn;

    @Column(name="tipCasopisa",nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private TipCasopisa tipCasopisa;

    @ElementCollection(targetClass = TipPlacanja.class)
    @JoinTable(name = "tipovi_placanja", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "tip_placanja", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<TipPlacanja> tipoviPlacanja = new ArrayList<>();

    //glavni urednik je samo 1 na casopisu
    @ManyToOne
    private Korisnik glavni_urednik;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Korisnik> urednici = new HashSet<>();

    //casopis -> recenzent
    @ManyToMany
    @JoinTable(
            name="casopis_recenzenti",
            joinColumns = @JoinColumn( name = "casopis_id"),
            inverseJoinColumns = @JoinColumn (name = "recenzent_id")
    )
    private Set<Korisnik> recenzent = new HashSet<>();

    //casopis -> naucne oblasti
    @ManyToMany
    @JoinTable(
            name="casopis_naucneoblasti",
            joinColumns = @JoinColumn( name = "casopis_id"),
            inverseJoinColumns = @JoinColumn (name = "naucna_oblast_id")
    )
    private Set<Naucna_oblast> naucna_oblast = new HashSet<>();

    @Column(name = "status",nullable = false, length = 255)
    private boolean status;

    public Naucni_casopis() {
    }

    public Naucni_casopis(String naziv, int issn, TipCasopisa tipCasopisa, List<TipPlacanja> tipoviPlacanja, Korisnik glavni_urednik, Set<Korisnik> urednici, Set<Korisnik> recenzent, Set<Naucna_oblast> naucna_oblast, boolean status) {
        this.naziv = naziv;
        this.issn = issn;
        this.tipCasopisa = tipCasopisa;
        this.tipoviPlacanja = tipoviPlacanja;
        this.glavni_urednik = glavni_urednik;
        this.urednici = urednici;
        this.recenzent = recenzent;
        this.naucna_oblast = naucna_oblast;
        this.status = status;
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

    public List<TipPlacanja> getTipoviPlacanja() {
        return tipoviPlacanja;
    }

    public void setTipoviPlacanja(List<TipPlacanja> tipoviPlacanja) {
        this.tipoviPlacanja = tipoviPlacanja;
    }

    public Korisnik getGlavni_urednik() {
        return glavni_urednik;
    }

    public void setGlavni_urednik(Korisnik glavni_urednik) {
        this.glavni_urednik = glavni_urednik;
    }

    public Set<Korisnik> getUrednici() {
        return urednici;
    }

    public void setUrednici(Set<Korisnik> urednici) {
        this.urednici = urednici;
    }

    public Set<Korisnik> getRecenzent() {
        return recenzent;
    }

    public void setRecenzent(Set<Korisnik> recenzent) {
        this.recenzent = recenzent;
    }

    public Set<Naucna_oblast> getNaucna_oblast() {
        return naucna_oblast;
    }

    public void setNaucna_oblast(Set<Naucna_oblast> naucna_oblast) {
        this.naucna_oblast = naucna_oblast;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
