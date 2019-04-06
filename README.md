# Spring Boot Workshop 2.0

## Vorausetzungen
* JDK 11
* GIT
* IDE mit Unterstützung für Java
* REST-Client z.B. [insomnia](https://insomnia.rest/) oder [SoapUI](https://www.soapui.org/)

## Aufgabenkomplex 1
Dieser Aufgabenkomplex befasst sich mit der Initialisierung von Projekten für Spring Boot.

### Aufgabe 1.1: Initialisieren von Spring Boot Projekt
Diese Aufgabe hat das Ziel zu vermitteln wie Projekte für Spring Boot initialisiert werden können.

Initialisiere ein Projekt für Spring Boot mit Hilfe von [Spring initializr](https://start.spring.io/) und verwende dabei folgende Angaben:

| Angabe | Wert |
|:---|:---|
| Project| Maven Project |
| Language | Java |
| Spring Boot | 2.1.4 |
| Project Metadata > Group | de.osp |
| Project Metadata > Artifact | spring-boot-workshop |
| Project Metadata > Name | **wird automatisch erzeugt** |
| Project Metadata > Description | **wird automatisch erzeugt** |
| Project Metadata > Package Name | **wird automatisch erzeugt** |
| Project Metadata > Packing | JAR |
| Project Metadata > Java Version  | 11 |
| Dependencies | Web *\[Web\]*, JPA *\[SQL\]* H2 *\[SQL\]* Actuator *\[Ops\]* |

**_HINWEIS:_**  Um alle verfügbaren Dependencies anzuzeigen klick auf den Link **Dependencies > See all** im Spring initialzr.


### Aufgabe 1.2: Vertraut machen mit Projektstruktur
Diese Aufgabe hat das Ziel zu vermitteln wie generierte Projekte für Spring Boot aufgebaut sind.
Da es sich um ein Maven Projekt handelt wird eine [pom.xml](pom.xml) erzeugt in der sich Angaben zum Artefakt, Abhängigkeiten und Build-Prozess wiederfinden.
Ferner wird die Klasse [SpringBootWorkshopApplication.java](src/main/java/de/osp/springbootworkshop/SpringBootWorkshopApplication.java) generiert, welche der Startpunkt der Spring Boot Anwendung ist.
Ebenso wird die Klasse [SpringBootWorkshopApplicationTests.java](src/test/java/de/osp/springbootworkshop/SpringBootWorkshopApplicationTests.java) generiert.
Die Testklasse besitzt nur einen leeren Test und dient dazu das starten der Anwendung zu testen.
Im Order für Resourcen befindet sich die [application.properties](src/main/resources/application.properties), welche als zentraler Punkt für die Konfiguration der Anwendung fungiert.

Mache dich mit der generierten Struktur des Spring Boot Projekts vertraut.


### Aufgabe 1.3: weitere Actuator Endpoints aktivieren
Der Actuator verfügt Endpoints für JMX und REST. Standardmäßig sind nur einige der Endpoints für JMX und REST aktiviert (siehe [Actuator Endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)).
Standardmäßig sind nur die Actuator REST-Endpoints `actuator/info` und `actuator/health` aktiviert. Es sollen zusätzlich die REST-Endpoints `actuator/metrics` und `actuator/beans` aktiviert werden.
Dazu muss die Datei unter [/src/resources/application.properties](src/main/resources/application.properties) bearbeitet werden.
Dabei soll das Property `management.endpoints.web.exposure.include=health,info,metrics,beans` gesetzt werden, wodurch die zusätzlichen REST-Endpoints aktiviert sind.

**_HINWEIS:_** Um die gängigsten Properties einzusehen kann die Übersicht der [common-application-properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) eingesehen werden. Im Absatz ***ENDPOINTS WEB CONFIGURATION*** werden die Properties für die Actuator REST-Endpoints aufgelistet.


### Aufgabe 1.4: Starten der Spring Boot Anwendung
Standardmäßig laufen Spring Boot Anwendungen unter dem Port 8080, um dies zu ändern muss das Property `server.port=<port>` gesetzt werden.
Starte die erstellte Spring Boot Anwendung über Maven mit `mvn spring-boot:run`.
Mache dich vertraut mit den zuvor in der *Aufgabe 1.2* aktivierten REST-Endpoints des Actuators.
Dazu kann die Übersicht der REST-Endpoints des Actuators unter [http://127.0.0.1:8080/actuator](http://127.0.0.1:8080/actuator) eingesehen werden.