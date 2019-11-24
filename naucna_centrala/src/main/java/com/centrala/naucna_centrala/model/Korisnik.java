package com.centrala.naucna_centrala.model;

import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="ime", nullable = false, length=255)
    private String ime;

    @Column(name ="prezime", nullable = false, length=255)
    private String prezime;

    @Column(name ="grad", nullable = false, length=255)
    private String grad;

    @Column(name ="drzava", nullable = false, length=255)
    private String drzava;

    @Column(name ="titula", length=255)
    private String titula;

    @Column(name ="email", nullable = false, length=255)
    private String email;

    @Column(name ="korisnicko_ime", nullable = false, length=255)
    private String korisnickoIme;

    @Column(name ="lozinka", nullable = false, length=255)
    private String lozinka;

    @Column(name ="tipKorisnika", nullable = false, length=255)
    @Enumerated(EnumType.STRING)
    private TipKorisnika tipKorisnika;

    //aktivacija naloga
    @Column(name ="aktiviran_nalog", nullable = false, length=255)
    private boolean aktiviran_nalog;

    //lista casopisa koje ima glavni urednik
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Naucni_casopis> id_casopisa = new HashSet<>();

    //vise recenzenata na vise naucnih casopisa
    @ManyToMany(mappedBy = "recenzent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Naucni_casopis> recenzenti = new HashSet<>();

    public Korisnik()
    {

    }

    public Korisnik(String ime, String prezime, String grad, String drzava, String titula, String email, String korisnickoIme, String lozinka, TipKorisnika tipKorisnika, boolean aktiviran_nalog, Set<Naucni_casopis> id_casopisa, Set<Naucni_casopis> recenzenti) {
        this.ime = ime;
        this.prezime = prezime;
        this.grad = grad;
        this.drzava = drzava;
        this.titula = titula;
        this.email = email;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
        this.tipKorisnika = tipKorisnika;
        this.aktiviran_nalog = aktiviran_nalog;
        this.id_casopisa = id_casopisa;
        this.recenzenti = recenzenti;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public TipKorisnika getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(TipKorisnika tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public boolean isAktiviran_nalog() {
        return aktiviran_nalog;
    }

    public void setAktiviran_nalog(boolean aktiviran_nalog) {
        this.aktiviran_nalog = aktiviran_nalog;
    }

    public Set<Naucni_casopis> getId_casopisa() {
        return id_casopisa;
    }

    public void setId_casopisa(Set<Naucni_casopis> id_casopisa) {
        this.id_casopisa = id_casopisa;
    }

    public Set<Naucni_casopis> getRecenzenti() {
        return recenzenti;
    }

    public void setRecenzenti(Set<Naucni_casopis> recenzenti) {
        this.recenzenti = recenzenti;
    }
}
