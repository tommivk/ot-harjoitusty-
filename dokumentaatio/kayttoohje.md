## Sovelluksen käynnistys
Sovellus käynnistetään suorittamalla komento ```mvn compile exec:java -Dexec.mainClass=com.battleship.App``` hakemiston `battleship` sisällä
###

Sovellus aukeaa näkymään jossa voi valita sisäänkirjautumisen tai uuden käyttäjän luomisen

![start](https://user-images.githubusercontent.com/52420413/145722796-f34e4cee-9e86-4828-b721-1f6e8eabc6d6.png)

## Kirjautuminen
Sisäänkirjautuminen tapahtuu kirjoittamalla käyttäjänimi tekstikenttään ja klikkaamalla ```ok```

![login](https://user-images.githubusercontent.com/52420413/145722806-4dddc7ec-adb0-42e4-9818-d17a8fa751b4.png)

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

## Pelitilastot
Näkymässä ``Statistics`` voi tarkastella pelitilastoja.
![statistics](https://user-images.githubusercontent.com/52420413/145722843-9597cd62-ddfe-4b77-a879-b751f66af220.png)

## Pelinäkymä
Uuden pelin käynnistyttyä avautuu pelinäkymä, jossa pelaavat voivat asettaa laivat pelikentälle omalla vuorollaan. 
![gamescene](https://user-images.githubusercontent.com/52420413/145722838-d698864f-aca6-46e4-84a2-e529ed5fc6a9.png)

Pelin päätyttyä voi aloittaa uuden pelin samaa vastustajaa vastaan ``New game`` napista tai poistua pelistä klikkaamalla ``Quit``
![game_finished](https://user-images.githubusercontent.com/52420413/145722840-65ec29bc-2ac8-4220-bb81-0d823bd28b93.png)

