# Battleship
Klassinen laivanupotuspeli
## Dokumentaatio
* [tuntikirjanpito](https://github.com/tommivk/ot-harjoitustyo/blob/master/tuntikirjanpito.md)
* [vaatimusmäärittely](https://github.com/tommivk/ot-harjoitustyo/blob/master/vaatimusmaarittely.md)
* [arkkitehtuuri](https://github.com/tommivk/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

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
