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

Initialisiere ein Projekt für Spring Boot mit Hilfe von [Spring initializr](https://start.spring.io/) und verwende dabei folgende Angaben:

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

Der Actuator verfügt Endpoints für JMX und REST. Standardmäßig sind nur einige der Endpoints für JMX und REST aktiviert (siehe [Actuator Endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)).
Standardmäßig sind nur die Actuator REST-Endpoints `actuator/info` und `actuator/health` aktiviert. Es sollen zusätzlich die REST-Endpoints `actuator/metrics` und `actuator/beans` aktiviert werden.
Dazu muss die Datei unter [/src/resources/application.properties](src/main/resources/application.properties) bearbeitet werden.
Dabei soll das Property `management.endpoints.web.exposure.include=health,info,metrics,beans` gesetzt werden, wodurch die zusätzlichen REST-Endpoints aktiviert sind.

**_HINWEIS:_** Um die gängigsten Properties einzusehen kann die Übersicht der [common-application-properties](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html) eingesehen werden. Im Absatz ***ENDPOINTS WEB CONFIGURATION*** werden die Properties für die Actuator REST-Endpoints aufgelistet.


### Aufgabe 1.4: Starten der Spring Boot Anwendung

Standardmäßig laufen Spring Boot Anwendungen unter dem Port 8080, um dies zu ändern muss das Property `server.port=<port>` gesetzt werden.
Starte die erstellte Spring Boot Anwendung über Maven mit `mvn spring-boot:run`.
Mache dich vertraut mit den zuvor in der *Aufgabe 1.2* aktivierten REST-Endpoints des Actuators.
Dazu kann die Übersicht der REST-Endpoints des Actuators unter [http://127.0.0.1:8080/actuator](http://127.0.0.1:8080/actuator) eingesehen werden.


## Aufgabenkomplex 2

Dieser Aufgabenkomplex befasst sich mit der Erstellung und Fehlerbehandlung von Endpunkten mit REST-Controllern in Spring Boot.
Ziel dieses Aufgabenkomplexes ist es REST-Endpunkte zur Interaktion mit dem Domainmodel von **Pet Store** bereitzustellen.
Das Domainmodel besteht zunächst nur aus der Entity `Pet`, welches sich unter `de.osp.springbootworkshop.domain.model` befindet.

```java
public class Pet {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String type;

    @NotNull
    private LocalDate birthDay;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    @DecimalMin("0.00")
    private BigDecimal price;

    // omitted public constructor, getter, setter, equals, hashCode, toString and optionally builder
}
```

### Aufgabe 2.1: erstelle einen REST-Controller

Es soll ein REST-Controller `de.osp.springbootworkshop.application.rest.PetShopRestController` angelegt werden.
Übergangsweise soll im `PetShopRestController` eine `Map<String, Pet>` erstellt werden, wobei der Key der Name des Haustiers und der Value das entsprechende 'Pet'.

```java
@RestController
@RequestMapping("/petshop/pets")
public class PetShopRestController {
    private final Map<String, Pet> pets;

    public PetShopRestController() {
        this.pets = new ConcurrentHashMap<>();

        Pet klaus = Pet.builder()
                .name("Klaus")
                .type("Hamster")
                .birthDay(LocalDate.of(2019, 4, 13))
                .price(BigDecimal.valueOf(20))
                .build();

        Pet rubert = Pet.builder()
                .name("Rubert")
                .type("Hund")
                .birthDay(LocalDate.of(2018, 9, 18))
                .price(BigDecimal.valueOf(550))
                .build();

        Pet blacky = Pet.builder()
                .name("Blacky")
                .type("Katze")
                .birthDay(LocalDate.of(2018, 12, 12))
                .price(BigDecimal.valueOf(350))
                .build();

        this.pets.put(klaus.getName().toLowerCase().trim(), klaus);
        this.pets.put(rubert.getName().toLowerCase().trim(), rubert);
        this.pets.put(blacky.getName().toLowerCase().trim(), blacky);
    }

    // omitted REST endpoints
}

```

### Aufgabe 2.2: erstelle und teste REST-Endpoint zur Auflistung aller Haustiers

Es soll ein REST-Endpoint `GET http://<host>:<port>/petshop/pets` im `PetShopRestController` erstellt werden.
Der Response-Body soll vom Typ `Collection<Pet>` und Content-Type `application/json` sein und im Positivfall den HTTP-Status-Code `200` zurückgeben.

***Übergangsweise soll der REST-Endpoint alle Haustiere mit `Map#values()` zurückgeben.***

**_HINWEIS:_** Standardmäßig wird im Positivfall der HTTP-Status-Code `HttpStatus#OK` zurückgegeben.

### Aufgabe 2.3: erstelle und teste REST-Endpoint zur Anlage eines Haustiers

Es soll ein REST-Endpunkt `POST http://<host>:<port>/petshop/pets` im `PetShopRestController` erstellt werden.
Der Request-Body soll vom Typ `Pet` und vom Content-Type `application/json` sein und validiert werden.
Der Response-Body soll vom Typ `Pet` und Content-Type `application/json` sein und im Positivfall den HTTP-Status-Code `200` zurückgeben.
Im Fehlerfall dass das Haustier mit dem Namen schon existiert soll eine `PetAlreadyExistsException` geworfen werden.
Die `PetAlreadyExistsException` leitet dabei von der abstrakten `de.ops.springbootworkshop.application.rest.model.PetShopApiException` ab.

```java
public abstract class PetShopApiException extends RuntimeException {
    // super constructors omitted
}
```

```java
public class PetAlreadyExistsException extends PetShopApiException {
    // super constructors omitted
}
```

***Übergangsweise soll der REST-Endpoint ein neuen Eintrag mit `Map#put(String, Pet)` anlegen.***

**_HINWEIS:_** Damit ein Methodenparameter als Request-Body erkannt wird muss dieser mit `@RequestBody` annotiert werden.

**_HINWEIS:_** Wenn der Request-Body validiert werden soll muss dieser mit `@Validated` annotiert werden.


### Aufgabe 2.4: erstelle und teste REST-Endpoint zur Entfernung eines Haustiers

Es soll ein REST-Endpunkt `DELETE http://<host>:<port>/petshop/pets/{name}` im `PetShopRestController` erstellt werden.
Der Path-Parameter `{name}` wird übergangsweise nicht ausgewertet.
Der Response-Body soll leer sein bzw. vom Typ `void` sein und im Positivfall den HTTP-Status-Code `204` zurückgeben.
Im Fehlerfall dass das Haustier mit dem Namen nicht existiert soll eine `PetNotExistsException` geworfen werden.
Die `PetAlreadyExistsException` leitet dabei von der abstrakten `de.ops.springbootworkshop.application.rest.model.PetShopApiException` ab.

```java
public class PetNotExistsException extends PetShopApiException {
    // super constructors omitted
}
```

***Übergangsweise soll der REST-Endpoint das Haustier mit `Map#remove(String)` entfernen.***

**_HINWEIS:_** Damit ein Methodenparameter als Path-Variable erkannt wird muss dieser mit `@PathVariable` annotiert werden.

**_HINWEIS:_** Standardmäßig werden Path-Variablen auf gleichnamige Methodenparameter gemappt.

**_HINWEIS:_** Damit im Positivfall ein HTTP-Status-Code abweichend zu `HttpStatus#OK` zurückgegeben werden kann muss die Methode mit `@ResponseStatus` annotiert werden.
Die Klasse `HttpStatus` besitzt die entsprechenden Konstanten für die HTTP-Status-Codes.


### Aufgabe 2.5: erstelle und teste Fehlerbehandlung

Die zuvor erstellen `PetAlreadyExistsException` und `PetNotExistsException`, welche von `PetShopApiException` ableiten
sollen durch einen Exception-Handler `de.ops.springbootworkshop.application.rest.PetShopExceptionHandler` behandelt werden.
Dabei wird der Response als `ResponseEntity#ResponseEntity(Object, HttpStatus)` konstruiert und zurückgegeben.
Der Response-Body bzw. `Objekt`  soll als `de.ops.springbootworkshop.application.rest.model.ApiError` angegeben werden, welcher die eigentliche Fehlermeldung der Exception enthält.
Der HTTP-Status-Code bzw. `HttpStatus` soll bei allen Exceptions die von `PetShopExceptionHandler` ableiten `HttpStatus#BAD_REQUEST` sein.

```java
@ControllerAdvice
public class PetShopExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ApiError.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // omitted custom exception handler(s)
}
```

```java
public class ApiError {
    private String message;

    // omitted public constructor, getter, setter, equals, hashCode, toString and static factory
}
```

**_HINWEIS:_** Wenn eine separate Klasse zur Behandlung von Exceptions verwendet wird, dann muss diese mit `@ControllerAdvice` annotiert werden.

**_HINWEIS:_** Es ist möglich einen Exception-Handler für konkrete, abstrakte bzw. abgeleite Exceptions zu erstellen. Dazu muss die Methode mit `@ExceptionHandler` anntotiert sein und die Exception als Parameter besitzen.

**_HINWEIS:_** Wenn der Exception-Handler von `ResponseEntityExceptionHandler` ableitet werden gängige Exception behandelt. Die Methoden können überschrieben werden, um die Fehlerbehandlung für die jeweilige Exception anzupassen.


### Aufgabe 2.6: erstelle und teste WEB-MVC-Test

Es soll ein WEB-MVC-Test für den `PetShopRestController` erstellt werden für alle REST-Endpoints mit folgenden Szenarien:

| REST-Endpoint                                | Szenario                                                               | Erwartung                                                                                    |
|:---------------------------------------------|:-----------------------------------------------------------------------|:---------------------------------------------------------------------------------------------|
| `GET http://<host>:<port>/petshop/pets`      | -                                                                      | HTTP-Status-Code `HttpStatus#OK` und Content-Type `MediaType#APPLICATION_JSON_UTF8`          |
| `POST http://<host>:<port>/petshop/pets`     | Invalider Request, es fehlen ein oder mehrere Angaben                  | HTTP-Status-Code `HttpStatus#BAD_REQUEST` und Content-Type `MediaType#APPLICATION_JSON_UTF8` |
| `POST http://<host>:<port>/petshop/pets`     | Invalider Request, es wird ein Haustier angelegt das bereits existiert | HTTP-Status-Code `HttpStatus#BAD_REQUEST`                                                    |
| `POST http://<host>:<port>/petshop/pets`     | Valider Request                                                        | HTTP-Status-Code `HttpStatus#OK` und Response-Body entspricht Request-Body                   |
| `DELETE http://<host>:<port>/petshop/{name}` | Invalider Request, der Name eines Haustiers existiert nicht            | HTTP-Status-Code `HttpStatus#BAD_REQUEST`                                                    |
| `DELETE http://<host>:<port>/petshop/{name}` | Valider Request                                                        | HTTP-Status-Code `HttpStatus#NO_CONTENT`                                                     |

Zur Umsetzung der WEB-MVC-Tests kann folgende Vorlage verwendet werden:

```java
// other imports omitted

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PetShopRestController.class)
public class PetShopRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String toJSON(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    // tests omitted
}
```

## Aufgabenkomplex 3

Der Aufgabenkomplex befasst sich mit der Erstellung von Services in Spring Boot.
Ziel dieses Aufgabenkomplexes ist das die vorhandene Domainlogik sich nicht mehr im REST-Controller `PetShopRestController` befindet sondern in einem Domainservice.

### Aufgabe 3.1: erstelle Domainservice

Erstelle einen Domainservice `de.osp.springbootworkshop.domain.service.PetShopService` und verschiebe die Domainlogik in die jeweiligen Methoden.
Desweiteren sollen die Exceptions `PetShopApiException`, `PetAlreadyExistsException` und `PetNotExistsException` in `de.osp.springbootworkshop.domain.service` verschoben werden.
Die Übergangsweise Persistierung der `Pet` soll weiterverwendet werden.

```java
@Service
public class PetShopService {
    private final Map<String, Pet> pets;

    public PetShopService() {
        this.pets = new ConcurrentHashMap<>();

        Pet klaus = Pet.builder()
                .name("Klaus")
                .type("Hamster")
                .birthDay(LocalDate.of(2019, 4, 13))
                .price(BigDecimal.valueOf(20))
                .build();

        Pet rubert = Pet.builder()
                .name("Rubert")
                .type("Hund")
                .birthDay(LocalDate.of(2018, 9, 18))
                .price(BigDecimal.valueOf(550))
                .build();

        Pet blacky = Pet.builder()
                .name("Blacky")
                .type("Katze")
                .birthDay(LocalDate.of(2018, 12, 12))
                .price(BigDecimal.valueOf(350))
                .build();

        this.pets.put(klaus.getName().toLowerCase().trim(), klaus);
        this.pets.put(rubert.getName().toLowerCase().trim(), rubert);
        this.pets.put(blacky.getName().toLowerCase().trim(), blacky);
    }

    public Collection<Pet> listPets() {
        // omitted domain logic
    }

    public Pet createPet(final Pet pet) {
        // omitted domain logic
    }

    public void deletePet(final String name) {
        // omitted domain logic
    }
}
```

### Aufgabe 3.2: infiziere Domainservice in REST-Controller und delegiere API-Aufrufe

Der Domainservice `PetShopService` soll via Constructor-Injection in den `PetShopRestController` injiziert werden.
Anschließend sollen die REST-Endpunkt die API vom Domainservice `PetShopService verwenden`.
Abschließend soll getestet werden, dass die REST-Endpunkte sich wie vor der Verschiebung der Domainlogik verhalten.

**_HINWEIS:_** Die Annotation `@Autowired` ist bei Constructor-Injection nicht nötig solange nur ein Constrcutor existiert.