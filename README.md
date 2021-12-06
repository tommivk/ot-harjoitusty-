# Battleship
Klassinen laivanupotuspeli
## Dokumentaatio
* [tuntikirjanpito](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
* [vaatimusmäärittely](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)
* [arkkitehtuuri](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Releaset
* [Viikko 5](https://github.com/tommivk/ot-harjoitustyo/releases/tag/viikko5)

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
