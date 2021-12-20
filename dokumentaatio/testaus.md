## Testaus

![jacoco-report](https://user-images.githubusercontent.com/52420413/146794202-5fd9b354-6607-4a46-9db1-97a6e48b1462.png)

Sovelluksen testien rivikattavuus on 93% ja haaraumakattavuus 82%. Testaamatta jäi ainakin haaraumakattavuuden osalta joitain haaraumia tietokonevastustajan pelivalintoja
muodostavissa metodeissa. 

Suurimmassa osassa sovelluksen testeissä käytetään useita luokkia samaan aikaan. Tosin myös yksikkötestejä on jonkin verran. Sovellusta testataan siis niin  yksikkö, kuin että integraatiotasolla. 

### DAO luokat
DAO luokkien `UserServicen` ja  `GameServicen` metodeja testataan integraatiotesteillä tiedostoissa`UserServiceTest` ja `GameServiceTest` 
Testeissä luodaan testien alussa testitietokanta, joka tyhjennätään testien lopuksi. 

### Muut luokat

Luokkien `Ship` ja `Square` toimintaa testaan sekä yksikkö, että integraatiotesteillä tiedostoissa `ShipTest` ja `SquareTest`

Tietokonevastustajan `Computer` toimintaa testaan tiedostossa `Computertest`. Testit sisältävät sekä yksikkö- että integraatiotestejä.

Pelitilaluokan `Game` toimintaa testaan tiedostossa `GameTest`. Testit ovat enimmäkseen integraatiotestejä

### Testeihin jääneet puuteet
Testien luomaa tietokantatiedostoa ei poisteta testien suorituksen jälkeen
