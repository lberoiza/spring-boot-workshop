# Spring Boot Workshop 2.0

## Aufgabenkomplex 3

Der Aufgabenkomplex befasst sich mit der Erstellung von Services in Spring Boot. Ziel dieses Aufgabenkomplexes ist das die vorhandene Domain-Logik sich nicht mehr im
REST-Controller `PetShopRestController` befindet sondern in einem Domain-Service.

### Aufgabe 3.1: erstelle Domain-Service

Erstelle einen Domain-Service `de.osp.springbootworkshop.domain.service.PetShopService` mit der Domain-Logik aus `PetShopRestController`. Des weiteren sollen die Exceptions
`PetShopApiException`, `PetAlreadyExistsException` und `PetNotExistsException` in `de.osp.springbootworkshop.domain.service` verschoben werden. Die übergangsweise Persistenz von
`Pet` aus `PetShopRestController` soll weiterhin verwendet werden.


### Aufgabe 3.2: injiziere Domain-Service in REST-Controller und delegiere API-Aufrufe

Der Domain-Service `PetShopService` soll via Constructor-Injection in den `PetShopRestController` injiziert werden. Anschließend sollen die REST-Endpunkt die API vom Domain-Service
`PetShopService verwenden`. Abschließend soll getestet werden, dass die REST-Endpunkte sich wie vor der Verschiebung der Domain-Logik verhalten.

**_HINWEIS:_** Die Annotation `@Autowired` ist bei Constructor-Injection nicht nötig solange nur ein Konstruktor existiert.


### Zusatzaufgabe: mocke Domain-Service im WEB-MVC-Test

Der `PetShopRestControllerTest` soll so angepasst werden, dass der `PetShopRestController` einen Mock von `PetShopService` verwendet. Für das Mocken von Beans wird dasMock-Framwork
Mockito verwendet, jedoch muss die Bean anstelle von `@Mock` mit `@MockBean` annotiert werden damit diese im Application-Context registriert wird.

**_DOKUMENTATION:_**
[Spring Boot Mocking and Spying Beans](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-mocking-beans)

**_HINWEIS:_** Wenn eine Bean gemockt und deren Verhalten beschrieben und überprüft werden soll benötigt diese die Annotationen `@MockBean` und `@Autowired`.

**_HINWEIS:_** Wenn eine Bean mit `@MockBean` annotiert wird um diese zu mocken, wird in Abhängkeit ob es sich um eine Interface oder Klasse handelt ggf. auf transitive
Abhängigkeiten überprüft. Bei letzterem können transitive Abhängigkeiten vorkommen, wenn entweder nur ein Constructor existiert oder einer der mit `@Autowired` annotiert ist.
Entsprechend müssen dessen Abhängigkeiten ebenfalls mit `@MockBean` gemockt werden. Um transitive Abhängigkeiten zu vermeiden empfielt es sich stattdessen ein Interface aus der
Klasse zu extrahieren und dieses mit `@MockBean` zu mocken.

**_HINWEIS:_** Das Mock-Framwork Mockito ist Teil der Abhängigkeit `org.springframework.boot:spring-boot-starter-test`. Für mehr Informationen zum Mock-Framework Mockito
[Reference Documentation](https://site.mockito.org/)
