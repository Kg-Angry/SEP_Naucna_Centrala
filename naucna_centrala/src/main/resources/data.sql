--Korisnik
--korisnicko_ime: kgangry, lozinka: kgangry
insert into korisnik(aktiviran_nalog,drzava,email,grad,ime,korisnicko_ime,lozinka, prezime,tip_korisnika,titula)
values (1,"Srbija","alex@gmail.com","Kragujevac","Aleksa","3CYwfRLqI4i/mknlruD09g==","$2a$10$aHgojqgN/JMalenY3dck8O/lwXww/pKzde81R1yAJoeOmzbi0Aw0K","Vasic","UREDNIK","");
-- korisnicko_ime: admin, lozinka: admin
insert into korisnik(aktiviran_nalog,drzava,email,grad,ime,korisnicko_ime,lozinka, prezime,tip_korisnika,titula)
values (1,"Srbija","alex@gmail.com","Kragujevac","A","QQEWlMUab87Put0SI3KNnA==","$2a$10$GWQ4wYUVKQxSWs83MKT1eO6Dqru4jiy9U4L90D0rHKDY3e70yqlTG","V","ADMINISTRATOR","");

--Naucna oblast
insert into naucna_oblast(naziv,opis) values ("IT","Oblast namenjena za infromacione tehnologije");
insert into naucna_oblast(naziv,opis) values ("Elektrotehnika","Oblast namenjena za rad sa uredjajima koji su tehnicke prirode");
insert into naucna_oblast(naziv,opis) values ("Gradjevinarstvo","Oblast namenjena za izgradnju infrastrukture");

-- SELECT * from korisnik;