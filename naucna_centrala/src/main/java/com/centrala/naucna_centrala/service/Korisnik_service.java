package com.centrala.naucna_centrala.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.centrala.naucna_centrala.Security.AES256bit;
import com.centrala.naucna_centrala.Security.TokenUtils;
import com.centrala.naucna_centrala.model.Korisnik;
import com.centrala.naucna_centrala.model.TipKorisnika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.centrala.naucna_centrala.repository.Korisnik_repository;
import java.util.List;


@Service
@Transactional
public class Korisnik_service {

    @Autowired
    private Korisnik_repository kr;
    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Korisnik findId(Long id)
    {
        return kr.getOne(id);
    }

    public void remove (String korisnicko_ime)
    {
        kr.deleteByKorisnickoIme(korisnicko_ime);
    }

    public Korisnik findByKorisnicko_ime (String korisnicko_ime)
    {
        return kr.findByKorisnickoIme(korisnicko_ime);
    }

    public List<Korisnik> findAll()
    {
        return kr.findAll();
    }

    public Korisnik save(Korisnik k)
    {
        return kr.save(k);
    }

    public String login(String username, String password, TipKorisnika role) {
        try {
            //kriptujem username zbog baze da moze da se pronadje
            String ime = AES256bit.encrypt(username,AES256bit.secretKey);
            Korisnik korisnik = kr.findByKorisnickoIme(ime);

            if(korisnik != null)
            {
                if(username.equals(AES256bit.decrypt(korisnik.getKorisnickoIme(),AES256bit.secretKey)) && passwordEncoder.matches(password, korisnik.getLozinka()) && korisnik.isAktiviran_nalog()) {

                    //kriptovano ime ide u token tako da je lakse da se nadje u bazi
                    String jwt = tokenUtils.generateToken(korisnik.getKorisnickoIme());

                    return jwt;
                }else
                    return "";

            }else
                return "";

            //korisnik je onaj iz baze
            /*Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, passwordEncoder.matches(password, korisnik.getLozinka())));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            korisnik = (Korisnik) authentication.getPrincipal();*/

        } catch (Exception e) {
            return "";
        }
    }
}
