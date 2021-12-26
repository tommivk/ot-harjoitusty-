### Pakkauskaavio
![pakkauskaavio](https://user-images.githubusercontent.com/52420413/146930045-5d0474d3-c3fd-4b6e-954b-c0c4e1c5a7cb.jpg)


`enums` pakkauksesta löytyvät sovelluksen käyttämät enumit, joita on pakkauksessa kaksi, `Shipdirection` ja `Player`. `UI` pakkauksesta löytyy graafisen käyttölittymän rakentava luokka. `Domain` pakkauksesta löytyvät sovelluslogiikkaan liittyvät luokat. `dao` pakkauksesta löytyvät pysyväistiedon hallintaan liittyvät luokat. 

### Käyttöliittymä
Käyttöliittymä sisältää 7 erilaista näkymää
* aloitusnäkymä
* käyttäjän sisäänkirjautumisnäkymä
* uuden käyttäjän luomisnäkymä
* pelivalintanäkymä
* toisen käyttäjän sisäänkirjautumisnäkymä
* tilastonäkymä
* pelitilanäkymä
 
Näkymät ovat toteutettu metodeina, jotka palauttavat Scene olion

### Tiedokanta
Luokat `DBGameDao` ja `DBUserDao` kommunikoivat tietokannan kanssa.

Sovellus käyttää Sqlite3 tietokantaa, johon se tallettaa tiedot käyttäjistä sekä pelitilastot peleistä. Tietokanta on suunnittelun suhteen yksinkertainen ja siinä on vain kaksi taulua. 
Taulussa `Users` on kaksi saraketta `id` ja `username` 
Taulussa `Games` on sarakkeet `id` `playeroneshots`  `playertwoshots` `playeronehits`  `playertwohits` sekä sarakkeet  `winner`  `playerone` `playertwo`, jotka ovat myös viiteavaimia `Users` tauluun. 


### Sovelluslogiikka
 Yhtä pelikentän ruutua pelissä kuvastaa ``Square`` olio, jolla on ilmentymämuuttujana javafx olio ``Rectangle``, jonka javafx lopulta renderöi käyttöliittymään. ``Square`` olioon on myös mahdollista lisätä ilmentymämuuttujana olio ``Ship`` joka kuvastaa yhtä laivaa pelikentällä. `Computer` luokka sisältää kaikki pelin tietokonevastustajan toiminnot ja metodit. `Game` luokka vastaa pelin pelitilan hallinnasta.

### Sekvenssikaavio uuden käyttäjän luomisesta
![newuser](https://user-images.githubusercontent.com/52420413/146920034-85c4996c-c610-43bc-a765-4f65f7e77c6c.png)

`DBUserDao` tallettaa tietokantaan uuden käyttäjän, mikäli käyttäjänimeä ei ole vielä olemassa ja onnistuessaan palauttaa `User` olion. 
Mikäli tietokantaoperaatio epäonnistuu, palauttaa `DBUserDao` `null`. Vastaavasti `UserService` luokka palauttaa `true` onnistuneen operaation jälkeen ja `false` mikäli operaatio epäonnistuu

### Sekvenssikaavio käyttäjän sisäänkirjautumisesta
![login](https://user-images.githubusercontent.com/52420413/145857100-95f4a585-1d3e-4fea-863e-118e1266557a.png)

`DBUserDao` hakee tietokannasta käyttäjän käyttäjänimen perusteella, ja onnistuessaan palauttaa `User` olion. 
Mikäli tietokantaoperaatio epäonnistuu, palauttaa `DBUserDao` `null`. Vastaavasti `UserService` luokka palauttaa `true` onnistuneen operaation jälkeen ja `false` mikäli operaatio epäonnistuu


### Sekvenssikaavio siitä mitä tapahtuu,kun uusi peli luodaan
![gamecreation](https://user-images.githubusercontent.com/52420413/145845645-9047ddb2-c09d-4898-a9b2-7868536d07e0.png)

`DBGameDao` tallettaa tietokantaan uuden pelin onnistuessaan palauttaa `Game` olion. 
Mikäli tietokantaoperaatio epäonnistuu, palauttaa `DBGameDao` `null`. Vastaavasti `GameService` luokka palauttaa `true` onnistuneen operaation jälkeen ja `false` mikäli operaatio epäonnistuu


### Sekvenssikaavio siitä mitä Game luokassa tapahtuu, kun uusi peli luodaan
![newgame](https://user-images.githubusercontent.com/52420413/145848023-82584b02-a533-4efb-b920-269bbdbbb153.png)

Jos peli on tietokonetta vastaan luo `Game` luokka olion `Computer` ja tallettaa sen ilmentyjämuuttujana. metodia `new Square()` kutsutaan yhteensä 200 kertaa ja oliot tallennetaan ilmentyjämuuttujiin `playerOneSquares` ja `playerTwoSquares` (100 molempiin). Metodia `new Ship()` kutsutaan yhteensä 12 kertaa ja luodut oliot tallennetaan ilmentyjämuuttujiin `playerOneShips` ja `playerTwoShips` (6 molempiin).

### Sekvenssikaavio laivan asettamisesta kentälle
![p1ships](https://user-images.githubusercontent.com/52420413/146924310-4740f318-e927-41a4-837b-c58516b8e19f.png)

`Game` ottaa seuraavan laivan pelaajan laivojen pinosta ja asettaa sen ilmentymämuuttujaksi niihin `Square` olioihin, joihin laiva asetetaan koordinaattien, laivan pituuden sekä laivan orientaation mukaan. Tämän jälkeen `Ship` oliolle lisätään ilmentymämuuttujaksi `Rectangle` olio `Square` oliosta, johon sitten lopulta vaihdetaan laivan kuva. Kun kaikki pelaajan laivat ovat asetettu kentälle, kutsutaan metodia `clearButtonImages` mikä muuttaa kaikki peliruudut takaisin valkoisiksi.

### Sekvenssikaavio siitä, kun pelaaja ampuu ohi ja vuoro vaihtuu
![player1turn](https://user-images.githubusercontent.com/52420413/146925761-3fa5e1dc-5f01-400c-8ba2-17bb4e9438ca.png)
Käyttöliittymä tarkasta aluksi onko peli vielä käynnissä ja että onko pelivuoro oikea. Sen jälkeen tarkastetaan onko ruutua jo ammuttu, mikäli ei ole, tarkastetaan sen jälkeen onko ruudussa laivaa vai ei. Mikäli ruudussa on laiva, palauttaa square luokka `true` ja muutoin `false`. Sen jälkeen tietokantaan talletetaan tieto uudesta laukauksesta. 

### Sekvenssikaavio siitä, kun pelaaja osuu vastustajan laivaan
![player1hit](https://user-images.githubusercontent.com/52420413/146926422-5cf3b83f-bda9-4fe4-bf73-c806daa66451.png)
Prosessi on sama kuin edellisessä kaaviossa, mutta pelivuoro ei vaihdu ja nyt peliruutuun asetetaan laivan kuva sekä tietokantaan talletetaan tieto myös osumasta. 


### Luokkakaavio
![luokkakaavio](https://user-images.githubusercontent.com/52420413/145857688-6a367501-8e45-40a8-96b1-eaa23d2809b9.jpg)

Luokkakaaviosta näkyy luokkien väliset yhteydet


### Ohjelman arkkitehtuuriin jääneet ongelmat
* käyttöliittymää rakentavaa koodia olisi ehkä voinut refaktoroida hieman enemmän erillisiksi metodeiksi
* scene näkymät olisi voinut mahdollisesti jakaa omiin luokkiinsa
