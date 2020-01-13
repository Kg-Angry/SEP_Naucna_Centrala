package com.centrala.naucna_centrala.DTO;

import com.centrala.naucna_centrala.Security.AES256bit;
import com.centrala.naucna_centrala.model.*;
import sun.security.krb5.internal.crypto.Aes256;

import java.util.HashSet;
import java.util.Set;

public class KorisnikDTO {

    private Long id;
    private String ime;
    private String prezime;
    private String grad;
    private String drzava;
    private String titula;
    private String email;
    private String korisnicko_ime;
    private String lozinka;
    private boolean recenzent;
    private TipKorisnika tipKorisnika;
    private boolean aktiviran_nalog;
    private Set<Naucna_oblastDTO> naucne_oblasti = new HashSet<>();
    private KorpaDTO korpa;

    public KorisnikDTO()
    {

    }

    public KorisnikDTO(Long id,String ime, String prezime, String grad, String drzava, String titula, String email, String korisnicko_ime, String lozinka, TipKorisnika tipKorisnika, boolean aktiviran_nalog, Set<Naucna_oblast> no, boolean recenzent, Korpa k) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.grad = grad;
        this.drzava = drzava;
        this.titula = titula;
        this.email = email;
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;
        this.tipKorisnika = tipKorisnika;
        this.aktiviran_nalog = aktiviran_nalog;
        for(Naucna_oblast n : no)
        {
            naucne_oblasti.add(new Naucna_oblastDTO(n));
        }
        this.recenzent = recenzent;
        if(k == null)
        {
            this.korpa = new KorpaDTO();
        }else
            this.korpa = new KorpaDTO(k);

    }

    public KorisnikDTO(Korisnik k)
    {
        this(k.getId(),k.getIme(),k.getPrezime(),k.getGrad(),k.getDrzava(),k.getTitula(),k.getEmail(),k.getKorisnickoIme(),k.getLozinka(),k.getTipKorisnika(),k.isAktiviran_nalog(),k.getNaucne_oblasti(),k.isRecenzent(),k.getKorpa());
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

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
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

    public boolean isRecenzent() {
        return recenzent;
    }

    public void setRecenzent(boolean recenzent) {
        this.recenzent = recenzent;
    }

    public Set<Naucna_oblastDTO> getNaucne_oblasti() {
        return naucne_oblasti;
    }

    public void setNaucne_oblasti(Set<Naucna_oblastDTO> naucne_oblasti) {
        this.naucne_oblasti = naucne_oblasti;
    }

    public KorpaDTO getKorpa() {
        return korpa;
    }

    public void setKorpa(KorpaDTO korpa) {
        this.korpa = korpa;
    }
}
