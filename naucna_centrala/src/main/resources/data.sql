--Korisnik
--korisnicko_ime: kgangry, lozinka: kgangry
insert into korisnik(aktiviran_nalog,drzava,email,grad,ime,korisnicko_ime,lozinka, prezime,tip_korisnika,titula,cekirao_recenzent)
values (1,"Srbija","alexva02@gmail.com","Kragujevac","Aleksa","3CYwfRLqI4i/mknlruD09g==","$2a$10$aHgojqgN/JMalenY3dck8O/lwXww/pKzde81R1yAJoeOmzbi0Aw0K","Vasic","UREDNIK","",false);
-- korisnicko_ime: admin, lozinka: admin
insert into korisnik(aktiviran_nalog,drzava,email,grad,ime,korisnicko_ime,lozinka, prezime,tip_korisnika,titula,cekirao_recenzent)
values (1,"Srbija","alexva02@gmail.com","Kragujevac","A","QQEWlMUab87Put0SI3KNnA==","$2a$10$GWQ4wYUVKQxSWs83MKT1eO6Dqru4jiy9U4L90D0rHKDY3e70yqlTG","V","ADMINISTRATOR","",false);
-- korisnicko_ime: recenzent, lozinka: recenzent
insert into korisnik (aktiviran_nalog,drzava,email,grad,ime,korisnicko_ime,lozinka,prezime,cekirao_recenzent,tip_korisnika,titula,urednik_casopisa_id)
values (1,'SRB','alexva02@gmail.com','NS','recenzent','ssqd8GlSEow3Sh18gpQMog==','$2a$10$YMKzW0EyRYmvTojo6.ivpO39YETQk/uo1e81pnM60iu29F.7lZMKW','recenzent',true,'RECENZENT','MAs',NULL);
-- korisnicko_ime: recenzent1, lozinka: recenzent1
insert into korisnik (aktiviran_nalog,drzava,email,grad,ime,korisnicko_ime,lozinka,prezime,cekirao_recenzent,tip_korisnika,titula,urednik_casopisa_id)
values (1,'SRB','alexva02@gmail.com','BG','recenzent1','bDxdskc0tCZ/UBdiYY86Ng==','$2a$10$cqgHTiKo1V3CCUoVGEyGHuDYH3tEliqlkK8Vk1KS7tfBrcpO0x586','recenzent1',true,'RECENZENT','Dipl',NULL);
-- korisnicko_ime: recenzent2, lozinka: recenzent2
insert into korisnik (aktiviran_nalog,drzava,email,grad,ime,korisnicko_ime,lozinka,prezime,cekirao_recenzent,tip_korisnika,titula,urednik_casopisa_id)
values (1,'SRB','alexva02@gmail.com','KG','recenzent2','+A6XXm8m1D/dEWbFGvoaDA==','$2a$10$Kr05TuN2N5LzukErseS/ou8uASKQsON8PRyXhp/q0E4gNP3hmxDiO','recenzent2',true,'RECENZENT','Mas',NULL);


--Naucna oblast
insert into naucna_oblast(naziv,opis) values ("IT","Oblast namenjena za infromacione tehnologije");
insert into naucna_oblast(naziv,opis) values ("Elektrotehnika","Oblast namenjena za rad sa uredjajima koji su tehnicke prirode");
insert into naucna_oblast(naziv,opis) values ("Gradjevinarstvo","Oblast namenjena za izgradnju infrastrukture");


insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (1,1);
insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (1,3);
insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (2,2);
insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (2,3);
insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (3,3);
insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (3,2);
insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (4,2);
insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (5,1);
insert into korisnik_naucneoblasti(korisnik_id, naucna_oblast_id) values (5,3);

-- SELECT * from korisnik;