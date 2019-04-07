# Spring Boot Workshop 2.0

## Vorausetzungen

* JDK 11
* GIT
* IDE mit Unterstützung für Java
* REST-Client z.B. [insomnia](https://insomnia.rest/) oder [SoapUI](https://www.soapui.org/)

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


### Aufgabe 3.3: mocke Domainservice im WEB-MVC-Test

Der `PetShopRestControllerTest` soll so angepasst werden, dass der `PetShopRestController` einen Mock von `PetShopService` verwendet.
Für das Mocken von Beans wird das Mock-Framwork Mockito verwendet, jedoch muss die Bean anstelle von `@Mock` mit `@MockBean` annotiert werden damit diese im Application-Context registriert wird.

**_BEISPIELE:_** mocken mit Mock-Framework Mockito

```java
import org.mockito.ArgumentMatchers;import java.util.Arrays;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MyRestController.class)
class WebMvcTestWithMockitoTest {
    @MockBean
    private MyBean mock;

    /**
    * Mock a method which have no parameters and returns a list of strings for any case.
    */
    @Test
    public testMockMethodWithNoParametersWhichReturnsListOfStrings() {
        when(mock.list()).thenReturn(Arrays.asList("a", "b", "c"));

        // usage of mock.list() omitted
    }

    /**
    * Mock a method which have parameters and returns a list of strings for any case.
    * Use org.mockito.ArgumentMatchers#any() for the given parameter.
    */
    @Test
    public testMockMethodWithAnyParameterWhichReturnsListOfStrings() {
        when(mock.listWithParameters(any())).thenReturn(Arrays.asList("a", "b", "c"));

        // usage of mock.listWithParameters(...) omitted
    }

    /**
    * Mock a method which have parameters and and returns a list of strings for given parameter criteria(s).
    * Use org.mockito.ArgumentMatchers#eq(Object) for equalize criteria or other matchers.
    */
    @Test
    public testMockMethodWithSpecificParameterWhichReturnsListOfStrings() {
        when(mock.listWithParameters(eq(3))).thenReturn(Arrays.asList("a", "b", "c"));

        // usage of mock.listWithParameters(...) omitted
    }

    /**
    * Mock a method which have no parameters and no return, but throw an exception for any case.
    */
    @Test
    public testMockMethodWithNoParametersAndReturnsNothingWhichThrowsException() {
        doThrow(MyException.class).when(mock).doStuff();

        // usage of mock.doStuff() omitted
    }

    /**
    * Mock a method which have parameters and no return, but throw an exception for any case.
    * Use org.mockito.ArgumentMatchers#any() for the given parameter.
    */
    @Test
    public testMockMethodWithAnyParameterAndReturnsNothingWhichThrowsException() {
        doThrow(MyException.class).when(mock).doMoreStuff(any());
    }

    /**
    * Mock a method which have parameters and no return, but throw an exception given parameter criteria(s).
    * Use org.mockito.ArgumentMatchers#eq(Object) for equalize criteria or other matchers.
    */
    @Test
    public testMockMethodWithAnyParameterAndReturnsNothingWhichThrowsException() {
        doThrow(MyException.class).when(mock).doMoreStuff(eq(3));
    }

    /**
    * Mock a method which have no parameters and returns a list of strings, but throw an exception for any case.
    */
    @Test
    public testMockMethodWithNoParametersWhichThrowsException() {
        when(mock.list()).andThrow(MyException.class);

        // usage of mock.list() omitted
    }

    /**
    * Mock a method which have parameters and returns a list of strings, but throw an exception for any case.
    * Use org.mockito.ArgumentMatchers#any() for the given parameter.
    */
    @Test
    public testMockMethodWithAnyParameterWhichReturnsListOfStringsButThrowsException() {
        when(mock.listWithParameters(any())).thenThrow(MyException.class);

        // usage of mock.listWithParameters(...) omitted
    }

    /**
    * Mock a method which have parameters and returns a list of strings, but throw an exception for given parameter criteria(s).
    * Use org.mockito.ArgumentMatchers#eq(Object) for equalize criteria or other matchers.
    */
    @Test
    public testMockMethodWithAnyParameterWhichReturnsListOfStringsButThrowsException() {
        when(mock.listWithParameters(eq(3))).thenThrow(MyException.class);

        // usage of mock.listWithParameters(...) omitted
    }
}
```

**_HINWEIS:_** Wenn eine Bean gemockt und deren Verhalten beschrieben und überprüft werden soll benötigt diese die Annotationen `@MockBean` und `@Autowired`.

**_HINWEIS:_** Wenn eine Bean mit `@MockBean` annotiert wird um diese zu mocken, wird in Abhängkeit ob es sich um eine Interface oder Klasse handelt ggf. auf transitive Abhängigkeiten überprüft.
Bei letzterem können transitive Abhängigkeiten vorkommen, wenn entweder nur ein Constructor existiert oder einer der mit `@Autowired` annotierte ist.
Entsprechend müssen dessen Abhängigkeiten ebenfalls mit `@MockBean` gemockt werden.
Um transitive Abhängigkeiten zu vermeiden empfielt es sich stattdessen ein Interface aus der Klasse zu extrahieren und dieses mit `@MockBean` zu mocken.

**_HINWEIS:_** Das Mock-Framwork Mockito ist Teil der Abhängigkeit `org.springframework.boot:spring-boot-starter-test`.
Für mehr Informationen zum Mock-Framework Mockito [Reference Documentation](https://site.mockito.org/)

**_HINWEIS:_** Spring Boot [Mocking and Syping Beans Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-mocking-beans)