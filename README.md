# Java projekt váz a Háttéralkalmazások tantárgy 1. házi feladatához

## Projekt importálása IDE-be

A projekt fordításához legalább 11-es JDK szükséges. (OpenJDK is megfelelő, pl. <https://jdk.java.net/java-se-ri/11>)

A projektet Maven segítségével buildeljük, így tetszőleges IDE-be importálhatjuk.

- Eclipse:
  - File > Import... > Existing Maven projects, itt ki kell kiválasztani a repositoryt, amiben a pom.xml megtalálható (Képekkel illusztrálva lásd itt: <https://javabydeveloper.com/import-maven-project-eclipse/>)
- IntelliJ:
  - <https://www.lagomframework.com/documentation/1.6.x/java/IntellijMaven.html>
- NetBeans:
  - <https://www.hubberspot.com/2014/09/how-to-import-existing-maven-project.html>

Az importálás után bármelyik IDE esetén némi türelem szükséges, amíg a projekt minden függősége letöltődik.

## Tesztek futtatása

A 4. és 5. feladat helyes megoldását a teszt osztályok futtatásával lehet ellenőrizni. Ezt több módon el lehet végezni:

- *mvn test* parancssorból
- Eclipse IDE-ben jobb klikk > Run as > JUnit test (vagy Debug as, ha debugolni szeretnénk), ahol a jobb klikket különböző elemeken végezhetjük:
  - Az src\test\java folderen -> az összes teszt lefut
  - Egy kiválasztott package-en -> az adott package-ben lévő tesztek futnak le
  - Egy osztyályon (akár a package explorerben, akár az editorban kijelölve) ->
  - Egy kijelölt metóduson az editorban -> csak az adott teszt metódus fut le
- IntelliJ esetén is hasonló: <https://www.jetbrains.com/help/idea/performing-tests.html>

A tesztek egyébként lefutnak a github-ra történő push esetén is, az eredmények a repository webes felületén az Actions fülön tekinthetők meg.

A tesztek jellegzetessége, hogy egyáltalán nem szükséges hozzá adatbázist beállítani, mert egy beágyazott in-memory H2 adatbázist használnak.

Nagyon fontos, hogy a teszteket nem szabad módosítani, ezt beadáskor ellenőrizzük.

## Az alkalmazás futtatása

A projekt egy Spring Boot-os parancssori alkalmazást tartalmaz, amely a LogisticsApplication osztály futtatásával indítható. Futtatáskor létrejön a springes kontextus, ennek során megpróbál az adatbázishoz csatlakozni, és létrehozni az entitásoknak megfelelő adatbázis táblákat. Az adatbázis URL, felhasználónév és jelszó az application.properties fájlban beállítandó, különben a futtatás sikertelen lesz.

Ha az indulás után még bármi kódot szeretnél futtatni, azt a LogisticsApplication run metódusában tehetnéd meg, de alapvetően nincs rá szükség. Az 1., 2. és 3. feladat megoldását úgy tudod ellenőrizni, hogy megnézed, hogy létrejöttek-e az entitásoknak megfelelő táblák az adatbázisban. Ha a JPA specifikációnak ellentmondó hibás entitásokat írsz, a futtatás szintén elhal. (Ebben az esetben egyébként a tesztek sem tudnak elindulni.)

## 



