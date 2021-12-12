# Battleship
Klassinen laivanupotuspeli
## Dokumentaatio
* [tuntikirjanpito](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
* [vaatimusmäärittely](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)
* [arkkitehtuuri](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
* [käyttöohje](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

## Releaset
* [Viikko 5](https://github.com/tommivk/ot-harjoitustyo/releases/tag/viikko5)
* [Viikko 6](https://github.com/tommivk/ot-harjoitustyo/releases/tag/viikko6)

## Komentorivitoiminnot
### Sovelluksen käynnistys
```
cd battleship
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
### Checkstyle raportin luominen
``` 
mvn jxr:jxr checkstyle:checkstyle
```

### Suoritettavan jarin generointi
```
mvn package
```

### JavaDoc
```
mvn javadoc:javadoc
```
