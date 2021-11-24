# Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovellus on klassinen kahden pelaajan vuoropohjainen laivanupotuspeli, jossa tavoitteena on tuhota vastustajan laivasto.
## Käyttäjät
Sovelluksessa on ainoastaan yksi käyttäjärooli eli <i>normaali käyttäjä</i>.
## Käyttöliittymä
Käyttöliittymän päänäkymä koostuu kahdesta 10x10 kokoisesta ruudukosta, jotka ovat merkitty numeroista ja kirjaimista koostuvalla kordinaatistolla. 
## Perusversion tajoama toiminnallisuus
### Ennen pelikierroksen alkua
* käyttäjä voi asettaa laivat pelikentälle omalla vuorollaan haluamiinsa sijainteihin, joko vaaka tai pystysuoraan. 
  * käyttäjällä on omia laivoja yhteensä 6kpl: yksi 5 ruutua pitkä, yksi 4 ruutua pitkä, kaksi 3 ruutua pitkää, yksi 2 ruutua pitkä ja yksi 1 ruutua pitkä laiva. laivat ovat yhden ruudun levyisiä
### Pelin ollessa käynnissä 
* käyttäjä voi klikata vastustajan ruudukon kordinaattia, johon haluaa kohdistaa ammuksen
  * jos ammus osuu vastustajan laivaan, käyttäjä saa jatkaa omaa vuoroaan
  * kaikki vastustajan laivat ensimmäisenä upotettuaan, voittaa käyttäjä pelin
## Jatkokehitysideoita
* pelitilastojen tallettaminen tietokantaan
* ääniefektit
* pelaaminen tietokonetta vastaan
