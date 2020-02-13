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

    //provera da li je pri registraciji cekirao da bude i recenzent
    @Column(name="cekirao_recenzent")
    private boolean recenzent;

    @ManyToMany
    @JoinTable(
            name="korisnik_naucneoblasti",
            joinColumns = @JoinColumn( name = "korisnik_id"),
            inverseJoinColumns = @JoinColumn (name = "naucna_oblast_id")
    )
    private Set<Naucna_oblast> naucne_oblasti;

    //lista casopisa koje ima glavni urednik
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Naucni_casopis> id_casopisa = new HashSet<>();

    //vise recenzenata na vise naucnih casopisa
    @ManyToMany
    @JoinTable(
            name="recenzent_naucniCasopis",
            joinColumns = @JoinColumn( name = "korisnik_id"),
            inverseJoinColumns = @JoinColumn (name = "naucni_casopis_id")
    )
    private Set<Naucni_casopis> recenzenti = new HashSet<>();

    @ManyToOne
    private Naucni_casopis urednik_casopisa;

    @OneToOne
    private Korpa korpa;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="korisnik_role",joinColumns = {@JoinColumn(name = "user_id")},inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<Role> roles;

    public Korisnik()
    {

    }

    public Korisnik(String ime, String prezime, String grad, String drzava, String titula, String email, String korisnickoIme, String lozinka, TipKorisnika tipKorisnika, boolean aktiviran_nalog, Set<Naucni_casopis> id_casopisa, Set<Naucni_casopis> recenzenti, Set<Naucna_oblast> naucne_oblasti, boolean recenzent) {
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
        this.naucne_oblasti = naucne_oblasti;
        this.recenzenti = recenzenti;
        this.recenzent = recenzent;

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

    public boolean isRecenzent() {
        return recenzent;
    }

    public void setRecenzent(boolean recenzent) {
        this.recenzent = recenzent;
    }

    public Set<Naucna_oblast> getNaucne_oblasti() {
        return naucne_oblasti;
    }

    public void setNaucne_oblasti(Set<Naucna_oblast> naucne_oblasti) {
        this.naucne_oblasti = naucne_oblasti;
    }

    public Naucni_casopis getUrednik_casopisa() {
        return urednik_casopisa;
    }

    public void setUrednik_casopisa(Naucni_casopis urednik_casopisa) {
        this.urednik_casopisa = urednik_casopisa;
    }

    public Korpa getKorpa() {
        return korpa;
    }

    public void setKorpa(Korpa korpa) {
        this.korpa = korpa;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
