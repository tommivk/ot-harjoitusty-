# Battleship
Klassinen laivanupotuspeli
## Dokumentaatio
* [tuntikirjanpito](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
* [vaatimusmäärittely](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)
* [arkkitehtuuri](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
* [käyttöohje](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)
* [testausdokumentti](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)

## Releaset
* [Viikko 5](https://github.com/tommivk/ot-harjoitustyo/releases/tag/viikko5)
* [Viikko 6](https://github.com/tommivk/ot-harjoitustyo/releases/tag/viikko6)
* [loppupalautus](https://github.com/tommivk/ot-harjoitustyo/releases/tag/loppupalautus)

## Komentorivitoiminnot
Kaikki komennot suoritetaan hakemistossa `battleship`
```
cd battleship
```
### Sovelluksen käynnistys

```
mvn compile exec:java -Dexec.mainClass=com.battleship.App
```

### Testien suorittaminen
```
mvn test
```
### Testikattavuusraportin luominen
```
mvn jacoco:report
```
raportti löytyy tiedostosta <em>target/site/jacoco/index.html</em>

### Checkstyle raportin luominen
``` 
mvn jxr:jxr checkstyle:checkstyle
```
raportti löytyy tiedostosta <em>target/site/checkstyle.html</em>
### Suoritettavan jarin generointi
```
mvn package
```
generoi suoritettavan <em>jar</em> tiedoston <em>battleship-1.0-SNAPSHOT.jar</em> hakemistoon <em>target</em>
### JavaDoc
```
mvn javadoc:javadoc
```
generoitu javadoc löytyy tiedostosta <em>target/site/apidocs/index.html</em>
