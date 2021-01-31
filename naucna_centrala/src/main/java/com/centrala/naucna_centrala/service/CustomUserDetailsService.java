package com.centrala.naucna_centrala.service;

import com.centrala.naucna_centrala.Security.AES256bit;
import com.centrala.naucna_centrala.model.Korisnik;
import com.centrala.naucna_centrala.model.Role;
import com.centrala.naucna_centrala.repository.Korisnik_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.crypto.Aes256;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private Korisnik_repository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		username = AES256bit.decrypt(username,AES256bit.secretKey);
		Korisnik user = userRepository.findByKorisnickoIme(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			Set<GrantedAuthority> grantedAuthority = new HashSet<GrantedAuthority>();
			Set<Role> roles = user.getRoles();
			for(Role role: roles)
			grantedAuthority.add(new SimpleGrantedAuthority(role.getName()));

			return new org.springframework.security.core.userdetails.User(user.getKorisnickoIme(), user.getLozinka(),
					grantedAuthority);
		}
	}

}
