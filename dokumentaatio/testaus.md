## Testaus

![jacoco-report](https://user-images.githubusercontent.com/52420413/146794202-5fd9b354-6607-4a46-9db1-97a6e48b1462.png)

Sovelluksen testien rivikattavuus on 93% ja haaraumakattavuus 82%. Testaamatta jäi ainakin haaraumakattavuuden osalta joitain haaraumia tietokonevastustajan pelivalintoja
muodostavissa metodeissa. 

Suurimmassa osassa sovelluksen testeissä käytetään useita luokkia samaan aikaan. Tosin myös yksikkötestejä on jonkin verran. Sovellusta testataan siis niin  yksikkö, kuin että integraatiotasolla. 

### DAO luokat
DAO luokkien `UserServicen` ja  `GameServicen` metodeja testataan integraatiotesteillä tiedostoissa`UserServiceTest` ja `GameServiceTest` 
Testeissä luodaan testien alussa testitietokanta, joka tyhjennätään testien lopuksi. 

### Sovelluslogiikan luokat

Luokkien `Ship` ja `Square` toimintaa testaan sekä yksikkö, että integraatiotesteillä tiedostoissa `ShipTest` ja `SquareTest`

Tietokonevastustajan `Computer` toimintaa testaan tiedostossa `ComputerTest`. Testit sisältävät sekä yksikkö- että integraatiotestejä.

Pelitilaluokan `Game` toimintaa testaan tiedostossa `GameTest`. Testit ovat enimmäkseen integraatiotestejä

### Järjestelmätestaus
Järjestelmätestaus on suoritettu manuaalisesti
Sovellusta on testattu Linux ympäristössä javan versiolla 11. Testauksessa on käyty läpi kaikki sovelluksen toiminnallisuudet. 
### Testeihin jääneet puuteet
Testien luomaa tietokantatiedostoa ei poisteta testien suorituksen jälkeen

### Ohjelmaan jääneet ongelmat
* ohjelmaan jäi pari checkstyle virhettä
  * GameDao luokassa sijaitseva metodi `createGame` ylittää metodin maksimipituuden kolmella rivillä. Metodin pituutta lisää lähinnä tietokantaan arvot asettavat `setInt` metodikutsut
  * Computer luokka ylittää jonkin verran checkstylen määrittämän luokan pituusrajan
* tietokonevastustajan käyttämä "brute force" menetelmä ei ole kovin tehokas menetelmä ja  sen sijaan olisi parempi pitää kirjaa jäljellä olevista ruuduista
* tietokonevastustajan "tekoäly" toimii ajoittain hieman bugisesti, jos laivat asetetaan vierekkäin
