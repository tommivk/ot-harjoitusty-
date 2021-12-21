## Sovelluksen käynnistys

Lataa `battleship.jar`, `images.zip` ja `config.properties` tiedostot releasesta [loppupalautus](https://github.com/tommivk/ot-harjoitustyo/releases/tag/loppupalautus) ja pura tiedosto `images.zip`

Tiedostojen `config.properties` ja kuvatiedostot sisältävän kansion `images` täytyy olla samassa hakemistossa suoritettavan jarin kanssa 

Sovellus käynnistetään komennolla `java -jar battleship.jar`
### Konfiguraatiot
`config.properties` tiedostossa on kaksi muuttuujaa, joiden avulla voi muuttaa sovelluksen toimintaa.

`computerShotDelay` määrittää tietokonepelaajan pelisiirtojen välisen viiveen. Arvo annetaan millisekunteina kokonaislukuna. 

`databaseFile` määrittää sovelluksen käyttämän tietokantatiedoston nimen.

### Sovelluksen käyttäminen

Sovellus aukeaa näkymään jossa voi valita sisäänkirjautumisen tai uuden käyttäjän luomisen

![start](https://user-images.githubusercontent.com/52420413/145722796-f34e4cee-9e86-4828-b721-1f6e8eabc6d6.png)

## Kirjautuminen
Sisäänkirjautuminen tapahtuu kirjoittamalla käyttäjänimi tekstikenttään ja klikkaamalla ```login```

![login](https://user-images.githubusercontent.com/52420413/146791202-5cf72dc1-d00e-4f72-ad1a-b7164100bd20.png)


## Uuden käyttäjän luominen 
Uuden käyttäjän luominen onnisuu new user näkymässä kirjoittamalla käyttäjänimi tekstikenttään ja klikkaamalla ```add```. Klikkaamalla ```generate random name``` 
nappia on mahdollista generoida satunnainen nimi.  
![signup](https://user-images.githubusercontent.com/52420413/145722802-212bbab1-35fb-489b-a78b-b8005c6bf1c2.png)

## Pelivalinta
Sisäänkirjautumisen jälkeen aukeavassa näkymässä voi aloittaa uuden pelin toista käyttäjää vastaan klikkamalla ```Vs Another player```, aloittaa uuden pelin tietokonetta vastaan
klikkamalla ``VS Computer`` tai tarkastella käyttäjäkohtaisia pelitilastoja klikkaamalla ```Show statistics```.

![game_selection](https://user-images.githubusercontent.com/52420413/145722811-7c3d0ea4-44c7-48a0-b320-138c20cb9e9b.png)


## Uuden pelin aloittaminen
Kun peli aloitetaan toista käyttäjää vastaan siirrytään pelaajan 2 sisäänkirjautumisnäkymään. Peli alkaa onnistuneen sisäänkirjautumisen jälkeen. 
Klikkaamalla ``Add new user`` siirrytään uuden käyttäjän luomisnäkymään, jossa voi lisätä uuden käyttäjän.

![playertwologin](https://user-images.githubusercontent.com/52420413/145722819-4c4ca8d6-6fb2-49f4-b478-2f1d44c765e9.png)

## Pelinäkymä
Uuden pelin käynnistyttyä avautuu pelinäkymä, jossa pelaavat voivat asettaa laivat pelikentälle omalla vuorollaan. Klikkaamalla hiiren oikeaa nappia pelikentän päällä voi muuttaa laivan suuntaa. Hiiren vasen klikkaus asettaa laivan kentällä.
![gamescene](https://user-images.githubusercontent.com/52420413/145722838-d698864f-aca6-46e4-84a2-e529ed5fc6a9.png)

Laivojen asettamisen jälkeen alkaa peli, jossa pelaajat voivat ampua toistensa laivoja omalla vuorollaan klikkaamalla pelikentän ruutuja. 
![gamestarted](https://user-images.githubusercontent.com/52420413/146787941-7da4e6c3-d5f8-4d76-b911-256998d31e83.png)


Pelin päätyttyä voi aloittaa uuden pelin samaa vastustajaa vastaan ``New game`` napista tai poistua pelistä klikkaamalla ``Quit``
![game-end](https://user-images.githubusercontent.com/52420413/146944086-0f45c28b-1057-4567-8e9a-8aa0793ef872.png)


## Pelitilastot
Näkymässä ``Statistics`` voi tarkastella pelitilastoja. Sekä hakea muiden käyttäjien pelitilastoja käyttäjänimen perusteella. 
![statistics](https://user-images.githubusercontent.com/52420413/146787556-5154c52b-ba70-47cd-b155-153b9c9ee16a.png)


