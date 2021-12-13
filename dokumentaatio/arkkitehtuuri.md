### Pakkauskaavio
![pakkauskaavio](https://user-images.githubusercontent.com/52420413/145840477-ce0309b3-a64a-4161-9ae8-8a52e2369876.jpg)

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

### Tiedostot
Sovellus käyttää Sqlite3 tietokantaa, johon se tallettaa tiedot käyttäjistä sekä pelitilastot peleistä. Tietokanta on suunnittelun suhteen yksinkertainen ja siinä on vain kaksi taulua. 
Taulussa `Users` on kaksi saraketta `id` ja `username` 
Taulussa `Games` on sarakkeet `id` `finished` `playeroneshots`  `playertwoshots` `playeronehits`  `playertwohits` sekä sarakkeet  `winner`  `playerone` `playertwo`, jotka ovat myös viiteavaimia `Users` tauluun. 


### Sovelluslogiikka
Yhtä pelikentän ruutua kuvastaa ``Square`` olio, jolla on ilmentymämuuttujana javafx olio ``Rectangle``, jonka javafx lopulta renderöi käyttöliittymään. ``Square`` olioon on myös mahdollista lisätä ilmentymämuuttujana olio ``Ship`` joka kuvastaa yhtä laivaa pelikentällä.

### Sekvenssikaavio käyttäjän sisäänkirjautumisesta
![login](https://user-images.githubusercontent.com/52420413/145857100-95f4a585-1d3e-4fea-863e-118e1266557a.png)


### Sekvenssikaavio siitä mitä tapahtuu,kun uusi peli luodaan
![gamecreation](https://user-images.githubusercontent.com/52420413/145845645-9047ddb2-c09d-4898-a9b2-7868536d07e0.png)

### Sekvenssikaavio siitä mitä Game luokassa tapahtuu, kun uusi peli luodaan
![newgame](https://user-images.githubusercontent.com/52420413/145848023-82584b02-a533-4efb-b920-269bbdbbb153.png)

Jos peli on tietokonetta vastaan luo `Game` luokka olion `Computer` ja tallettaa sen ilmentyjämuuttujana. metodia `new Square()` kutsutaan yhteensä 200 kertaa ja oliot tallennetaan ilmentyjämuuttujiin `player1Squares` ja `player2Squares`. Metodia `new Ship()` kutsutaan yhteensä 12 kertaa ja luodut oliot tallennetaan ilmentyjämuuttujiin `player1Ships` ja `player2Ships` 

### Luokkakaavio
![luokkakaavio](https://user-images.githubusercontent.com/52420413/145853645-b76b9881-84e2-4546-b29a-b3f7cbe97069.jpg)


### Sekvenssikaavio koko peliprosessista pääpiirteittäin
![sekvenssikaavio](https://user-images.githubusercontent.com/52420413/144903849-8fa947bc-c414-4414-9418-f5d9a3373cd6.png)
