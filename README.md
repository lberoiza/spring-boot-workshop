# Spring Boot Workshop 2.0

## Aufgabenkomplex 2

Dieser Aufgabenkomplex befasst sich mit der Erstellung und Fehlerbehandlung von Endpunkten mit REST-Controllern in Spring Boot. Ziel dieses Aufgabenkomplexes ist es REST-Endpunkte
zur Interaktion mit dem Domain-Model von Pet Store bereitzustellen.


### Aufgabe 2.1: erstelle einen REST-Controller

Es soll ein REST-Controller `de.osp.springbootworkshop.application.rest.PetShopRestController` angelegt werden. Übergangsweise soll `de.osp.springbootworkshop.domain.model.Pet` im `PetShopRestController`
in einer `Map<String, Pet>` persistiert werden.

```java
// annotations omitted
public class PetShopRestController {
    private final Map<String, Pet> pets;

    public PetShopRestController() {
        this.pets = new ConcurrentHashMap<>();

        Pet klaus = new Pet("Klaus", "Hamster", LocalDate.of(2019, 4, 13), BigDecimal.valueOf(20));
        Pet rubert = new Pet("Rubert","Hund", LocalDate.of(2018, 9, 18), BigDecimal.valueOf(550));
        Pet blacky = new Pet("Blacky","Katze", LocalDate.of(2018, 12, 12),  BigDecimal.valueOf(350));

        this.pets.put(klaus.getName().toLowerCase().trim(), klaus);
        this.pets.put(rubert.getName().toLowerCase().trim(), rubert);
        this.pets.put(blacky.getName().toLowerCase().trim(), blacky);
    }

    // methods for REST endpoints omitted
}
```

**_DOKUMENTATION:_** [Spring Boot Web MVC](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-spring-mvc)


### Aufgabe 2.2: erstelle und teste REST-Endpoint zur Auflistung aller Haustiers

Es soll ein REST-Endpoint erstellt werden im `PetShopRestController` zur Auflistung von `Pet`. Dabei sollen die temporär persistierten `Pet` mittels `Map#values()` abgefragt werden.

| Angabe          | Wert                                                |
|:----------------|:----------------------------------------------------|
| Resource        | `GET /petshop/pets`                                 |
| Request-Body    | -                                                   |
| Response-Status | `200` bzw. `HttpStatus#OK`                          |
| Response-Body   | `Iterable<Pet>` vom Content-Type `application/json` |
| Exception       | -                                                   |

**_HINWEIS:_** Standardmäßig wird im positiven Fall der HTTP-Status-Code `HttpStatus#OK` zurückgegeben.


### Aufgabe 2.3: erstelle und teste REST-Endpoint zur Anlage eines Haustiers

Es soll ein REST-Endpoint erstellt werden im `PetShopRestController` zur Anlage von `Pet`. Dabei soll das `Pet` temporär mittels `Map#put(String, Pet)` persistiert werden.

| Angabe          | Wert                                                                   |
|:----------------|:-----------------------------------------------------------------------|
| Resource        | `POST /petshop/pets`                                                   |
| Request-Body    | `Pet` vom Content-Type `application/json`                              |
| Response-Status | `200` bzw. `HttpStatus#OK`                                             |
| Response-Body   | `Pet`  vom Content-Type `application/json`                             |
| Exception       | `PetAlreadyExistsException` wenn `Pet` mit dem Namen bereits existiert |


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

**_HINWEIS:_** Damit ein Methodenparameter als Request-Body erkannt wird muss dieser mit `@RequestBody` annotiert werden.

**_HINWEIS:_** Wenn der Request-Body validiert werden soll muss dieser mit `@Validated` annotiert werden.


### Aufgabe 2.4: erstelle und teste REST-Endpoint zur Entfernung eines Haustiers

Es soll ein REST-Endpoint erstellt werden im `PetShopRestController` zur Entfernung von `Pet`. Dabei soll das `Pet` aus der temporären Persistierung mittels `Map#remove(String)` entfernt werden.

| Angabe          | Wert                                                             |
|:----------------|:-----------------------------------------------------------------|
| Resource        | `DELETE /petshop/pets/{name}`                                    |
| Request-Body    | -                                                                |
| Response-Status | `204` bzw. `HttpStatus.NO_CONTENT`                               |
| Response-Body   | -                                                                |
| Exception       | `PetNotExistsException` wenn `Pet` mit dem Namen nicht existiert |

```java
public class PetNotExistsException extends PetShopApiException {
    // super constructors omitted
}
```

**_HINWEIS:_** Damit ein Methodenparameter als Path-Variable erkannt wird muss dieser mit `@PathVariable` annotiert werden.

**_HINWEIS:_** Standardmäßig werden Path-Variablen auf gleichnamige Methodenparameter gemappt.

**_HINWEIS:_** Damit im positiven Fall ein HTTP-Status-Code abweichend zu `HttpStatus#OK` zurückgegeben werden kann muss die Methode mit `@ResponseStatus` annotiert werden. Die Klasse
`HttpStatus` besitzt die entsprechenden Konstanten für die HTTP-Status-Codes.


### Aufgabe 2.5: erstelle und teste Fehlerbehandlung

Es soll ein Exception-Handler `de.ops.springbootworkshop.application.rest.PetShopExceptionHandler` erstellt werden, welcher von `ResponseEntityExceptionHandler` ableitet. Für die
Aufgabe soll mit `de.ops.springbootworkshop.application.rest.model.ApiError` eine einheitliche Datenstruktur zur Ausgabe von verschiedenen Fehlern verwendet werden. Der `ApiError`
soll dabei den Fehlertext der Exception beinhalten.

```java
public class ApiError {
    private String message;

    // omitted public constructor, getter, setter, equals, hashCode and toString
}
```

Im Exception-Handler soll die Fehlerbehandlung der Exception `MethodArgumentNotValidException` anpassen, wozu die Methode
`handleMethodArgumentNotValid(MethodArgumentNotValidException, HttpHeaders, HttpStatus, WebRequest)` aus `ResponseEntityExceptionHandler` überschrieben werden soll.

| Angabe          | Wert                               |
|:----------------|:-----------------------------------|
| Response-Status | `400` bzw.`HttpStatus#BAD_REQUEST` |
| Response-Body   | `ApiError`                         |


Der Exception-Handler soll um eine Fehlerbehandlung von `PetShopApiException` erweitert werden, dazu muss eine Methode implementiert und annotiert werden.

| Angabe          | Wert                               |
|:----------------|:-----------------------------------|
| Response-Status | `400` bzw.`HttpStatus#BAD_REQUEST` |
| Response-Body   | `ApiError`                         |

**_DOKUMENTATION:_** [Spring Boot Web MVC Error Handling](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-error-handling)

**_HINWEIS:_** Wenn eine separate Klasse zur Behandlung von Exceptions verwendet wird, dann muss diese mit `@ControllerAdvice` annotiert werden.

**_HINWEIS:_** Es ist möglich einen Exception-Handler für konkrete, abstrakte bzw. abgeleitete Exceptions zu erstellen. Dazu muss die Methode mit `@ExceptionHandler` annotiert sein
und die Exception als Parameter besitzen.

**_HINWEIS:_** Wenn der Exception-Handler von `ResponseEntityExceptionHandler` ableitet werden gängige Exception behandelt. Die Methoden können überschrieben werden, um die
Fehlerbehandlung für die jeweilige Exception anzupassen.


### Zusatzaufgabe: erstelle und teste Web-MVC Test

Der `PetShopRestController` soll hinsichtlich Funktionalität getestet werden für die folgenden Szenarien:


| REST-Endpoint            | Szenario                                                               | Erwartung                                                                                                        |
|:-------------------------|:-----------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------|
| `GET /petshop/pets`      | -                                                                      | HTTP-Status-Code `200` und Content-Type `MediaType#APPLICATION_JSON_UTF8`                                        |
| `POST /petshop/pets`     | Invalider Request, es fehlen ein oder mehrere Angaben                  | HTTP-Status-Code `400` und Content-Type `MediaType#APPLICATION_JSON_UTF8`                                        |
| `POST /petshop/pets`     | Invalider Request, es wird ein Haustier angelegt das bereits existiert | HTTP-Status-Code `400`                                                                                           |
| `POST /petshop/pets`     | Valider Request                                                        | HTTP-Status-Code `200`, Content-Type `MediaType#APPLICATION_JSON_UTF8` und Response-Body entspricht Request-Body |
| `DELETE /petshop/{name}` | Invalider Request, der Name eines Haustiers existiert nicht            | HTTP-Status-Code `400`                                                                                           |
| `DELETE /petshop/{name}` | Valider Request                                                        | HTTP-Status-Code `204`                                                                                           |

Dazu soll ein Web-MVC-Test `de.osp.springbootworkshop.application.rest.PetShopRestControllerTest` erstellt werden.

```java
// other imports omitted

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// annotations omitted
public class PetShopRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String toJSON(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    @TestConfiguration
    public static class MockMvcConfig {
        @Bean
        public MockMvc mockMvc(WebApplicationContext applicationContext) {
            return MockMvcBuilders.webAppContextSetup(applicationContext)
                    .build();
        }
    }

    // tests omitted
}
```

**_DOKUMENTATION:_**
[Spring Boot Web MVC Test](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-with-mock-environment)
