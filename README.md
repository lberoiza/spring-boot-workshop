# Spring Boot Workshop 2.0

## Aufgabenkomplex 1

Dieser Aufgabenkomplex befasst sich mit der Initialisierung von Projekten für Spring Boot.

### Aufgabe 1.1: Initialisieren von Spring Boot Projekt

Diese Aufgabe hat das Ziel zu vermitteln wie Projekte für Spring Boot initialisiert werden können.

Initialisiere ein Projekt für Spring Boot mit Hilfe von [Spring initializr](https://start.spring.io/) und verwende dabei folgende Angaben:

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
| Project Metadata > Java Version | 11                                                           |
| Dependencies                    | Web *\[Web\]*, JPA *\[SQL\]* H2 *\[SQL\]* Actuator *\[Ops\]* |

**_HINWEIS:_** Um alle verfügbaren Dependencies anzuzeigen klick auf den Link **Dependencies > See all** im Spring initialzr.


### Aufgabe 1.2: vertraut machen mit Struktur des Projekts

Diese Aufgabe hat das Ziel zu vermitteln wie generierte Projekte für Spring Boot aufgebaut sind. Da es sich um ein Maven Projekt handelt wird eine `pom.xml` erzeugt in der sich
Angaben zum Artefakt, Abhängigkeiten und Build-Prozess wiederfinden. Ferner wird die Klasse `de.osp.springbootworkshop.SpringBootWorkshopApplication` generiert, welche der
Startpunkt der Spring Boot Anwendung ist. Ebenso wird die Klasse `de.osp.springbootworkshop.SpringBootWorkshopApplicationTests` generiert. Die Testklasse besitzt nur einen leeren
Test und dient dazu das starten der Anwendung zu testen. Im Order für Ressourcen befindet sich die `src/main/resources/application.properties`, welche als zentraler Punkt für die
Konfiguration der Anwendung fungiert.

Mache dich mit der generierten Struktur des Spring Boot Projekts vertraut.


### Aufgabe 1.3: weitere Actuator Endpoints aktivieren

Der Actuator verfügt Endpoints für JMX und REST. Standardmäßig sind nur einige der Endpoints für JMX und REST aktiviert. Standardmäßig sind nur die Actuator REST-Endpoints `info`
und `health` aktiviert. Es sollen zusätzlich die REST-Endpoints `metrics` und `beans` aktiviert werden. Dafür muss die `application.properties` wie folgt bearbeitet werden:

```properties
management.endpoints.web.exposure.include=health,info,metrics,beans
```

**_DOKUMENTATION:_**
[Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html),
[common-application-properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)


### Aufgabe 1.4: starten der Spring Boot Anwendung

Standardmäßig laufen Spring Boot Anwendungen unter dem Port 8080, um dies zu ändern muss das Property `server.port=<port>` gesetzt werden. Starte die erstellte Spring Boot
Anwendung über Maven mit `mvn spring-boot:run`. Mache dich vertraut mit den REST-Endpoints des Actuators. Eine Übersicht über die aktivierten REST-Endpoints des Actuators kann mit
[http://localhost:8080/actuator](http://localhost:8080/actuator) eingesehen werden.
