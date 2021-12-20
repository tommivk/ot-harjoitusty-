# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovellus on klassinen kahden pelaajan vuoropohjainen laivanupotuspeli, jossa tavoitteena on tuhota vastustajan laivasto. Peliä voi pelata tietokonevastustajaa vastaan tai toista käyttäjää vastaan samalla tietokoneella.
## Käyttäjät
Sovelluksessa on ainoastaan yksi käyttäjärooli eli <i>normaali käyttäjä</i>.
## Käyttöliittymä
Käyttöliittymä sisältää 7 erilaista näkymää
* aloitusnäkymä
* käyttäjän sisäänkirjautumisnäkymä
* uuden käyttäjän luomisnäkymä
* pelivalintanäkymä
* toisen käyttäjän sisäänkirjautumisnäkymä
* tilastonäkymä
* pelitilanäkymä

pelitilanäkymä koostuu kahdesta 10x10 kokoisesta ruudukosta, jotka ovat merkitty numeroista ja kirjaimista koostuvalla kordinaatistolla. 
## Toiminnallisuudet
### Ennen sisäänkirjautumista
* käyttäjä voi valita aloitusnäkymässä joko sisäänkirjautumisen tai uuden käyttäjätunnuksen luomisen.
### Uuden käyttäjän luominen
* käyttäjällä  voi luoda uuden käyttäjätännuksen
### Sisäänkirjautuminen
* käyttäjä voi kirjautua sisään käyttäjänimellä
### Kirjautumisen jälkeen 
* käyttäjä voi aloittaa uuden pelin tietokonetta tai toista käyttäjää vastaan
* käyttäjä voi tarkastella omia pelitilastoja pelitilastonäkymässä
* käyttäjä voi hakea muiden käyttäjien tilastoja pelitilastonäkymässä
* käyttäjä voi kirjautua ulos sovelluksesta
### Ennen pelikierroksen alkua
* käyttäjä voi asettaa laivat pelikentälle omalla vuorollaan haluamiinsa sijainteihin, joko vaaka tai pystysuoraan.
  * käyttäjällä on omia laivoja yhteensä 6kpl: yksi 5 ruutua pitkä, yksi 4 ruutua pitkä, kaksi 3 ruutua pitkää, kaksi 2 ruutua pitkää laivaa. laivat ovat yhden ruudun levyisiä.
  * laivoja ei voi asettaa päällekkäin
### Pelin ollessa käynnissä
* käyttäjä voi klikata omalla pelivuorollaan vastustajan ruudukon kordinaattia, johon haluaa kohdistaa ammuksen. 
  * jos ammus osuu vastustajan laivaan, käyttäjä saa jatkaa omaa vuoroaan. 
  * kaikki vastustajan laivat ensimmäisenä upotettuaan, voittaa käyttäjä pelin ja peli päättyy. 
### Pelin jälkeen
* käyttäjä voi aloittaa uuden pelin samaa vastustajaa vastaan
* käyttäjä voi poistua pelistä

