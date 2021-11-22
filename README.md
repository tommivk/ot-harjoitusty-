# Battleship
Klassinen laivanupotuspeli
## Dokumentaatio
* [tuntikirjanpito](https://github.com/tommivk/ot-harjoitustyo/blob/master/tuntikirjanpito.md)
* [vaatimusmäärittely](https://github.com/tommivk/ot-harjoitustyo/blob/master/vaatimusmaarittely.md)

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
### Testikattavuus reportin luominen
```
mvn jacoco:report
```
