# Spring Boot Workshop 2.0

## Aufgabenkomplex 1

Dieser Aufgabenkomplex befasst sich mit der Initialisierung von Projekten für Spring Boot. Ziel ist es ein Maven-Projekt zu initialisieren, die Anwendung zu starten und den
Actuator zu aufzurufen.

### Aufgabe 1.1: Initialisieren von Spring Boot Projekt

Es soll ein Projekt mittels [Spring initializr](https://start.spring.io/) initialisiert werden. Dabei sollen folgende Angaben verwendet werden:

| Angabe                          | Wert                                                         |
|:--------------------------------|:-------------------------------------------------------------|
| Project                         | Maven Project                                                |
| Language                        | Java                                                         |
| Spring Boot                     | 2.1.4                                                        |
| Project Metadata > Group        | de.osp                                                       |
| Project Metadata > Artifact     | spring-boot-workshop                                         |
| Project Metadata > Name         | **wird automatisch erzeugt**                                 |
| Project Metadata > Description  | **wird automatisch erzeugt**                                 |
| Project Metadata > Package Name | **wird automatisch erzeugt**                                 |
| Project Metadata > Packing      | JAR                                                          |
| Project Metadata > Java Version | 8                                                           |
| Dependencies                    | Web *\[Web\]*, JPA *\[SQL\]* H2 *\[SQL\]* Actuator *\[Ops\]* |

**_HINWEIS:_** Um alle verfügbaren Dependencies anzuzeigen klick auf den Link **Dependencies > See all** im Spring initialzr.


### Aufgabe 1.2: vertraut machen mit Struktur des Projekts

Es soll sich mit dem generierten Projekt vertraut gemacht werden. Dazu soll ein Blick in die `pom.xml` geworfen werden hinsichtlich der Angaben zu Artefakt, Abhängigkeiten und
Build-Prozess für Spring Boot. Ferner soll ein Blick in die Hauptklasse der Spring Boot Anwendung `de.osp.springbootworkshop.SpringBootWorkshopApplication` bzw. dazu generierten
Test `de.osp.springbootworkshop.SpringBootWorkshopApplicationTests` geworfen werden. Die Konfiguration der Spring Boot Anwendung wird durch
`src/main/resources/application.properties` vorgenommen, welche zunächst allerdings leer ist bzw. keine standardmäßige Konfiguration überschreibt.


### Aufgabe 1.3: weitere Actuator Endpoints aktivieren

Es sollen für den Actuator die zusätzliche REST-Endpoints `GET /actuator/metrics` und `GET /actuator/beans` aktiviert werden. Dazu muss muss in der `application.properties`
folgende Konfiguration vorgenommen werden:

```properties
management.endpoints.web.exposure.include=health,info,metrics,beans
```

**_DOKUMENTATION:_**
[Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html),
[common-application-properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)


### Aufgabe 1.4: starten der Spring Boot Anwendung

Es sol die Spring Boot Anwendung gestartet werden, dazu muss mit Maven das Goal `mvn spring-boot:run` ausgeführt werden. Anschließend soll die Übersicht der Actuator REST-Endpunkte
mit [http://localhost:8080/actuator](http://localhost:8080/actuator) aufgerufen werden.

**_HINWEIS:_** Wenn der Port `8080` belegt ist kann in der `application.properties` mit folgender Konfiguration der Port angepasst werden:

```properties
server.port= # port of the server
```
