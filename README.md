# Spring Boot Workshop 2.0

## Vorbedingungen

### erweitern des Domainmodel

Das Domainmodel wird erweitert um `PetType`, welches sich unter `de.osp.springbootworkshop.domain.model` befindet.

```java
public class PetType {
    @NotNull
    @NotEmpty
    private String name;

    // omitted public no args constructor, getter, setter, equals, hashCode, toString and optionally builder
}
```

### JPA im Domainmodel verwenden

Damit `Pet` und `PetType` als Entity mit JPA verwendet werden können müssen diese mit diversen Annotationen versehen werden.

```java
@Entity
@Table(name = "pets")
@Validated
public class Pet {
    @Id
    @NotNull
    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    @NotNull
    private PetType type;

    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthDay;

    @Column(name ="price", nullable = false, columnDefinition = "DECIMAL(6,2)")
    @NotNull
    @Digits(integer = 6, fraction = 2)
    @DecimalMin("0.00")
    private BigDecimal price;

    // omitted public no args constructor, getter, setter, equals, hashCode, toString and optionally builder
}
```

```java
@Entity
@Table(name = "pet_types")
@Validated
public class PetType {
    @Id
    @NotNull
    @NotEmpty
    private String name;

    // omitted public no args constructor, getter, setter, equals, hashCode, toString and optionally static factory
}
```

### initialisieren der Datenbank mit Schema

Wenn es gewünscht ist, dass die Datenbank mit Schemas initialisiert wird existieren zwei Möglichkeiten.
Die erste Möglichkeit besteht darin die Generierung der Tabellen anhand der
Klassen die mit der JPA Annotation `@Entity` versehen sind abzuleiten.
Wenn eine embedded Datenbank verwendet wird, dann ist standardmäßig die Generierung von DDL Skripten zur Initialisierung der Datenbank aktiviert.
Ob und wie die Initialisierung der Datenbank stattfinden soll kann anhand diverser Properties in der `application.properties` festgelegt
werden:

```properties
spring.jpa.generate-ddl= # false or true
spring.jpa.hibernate.ddl-auto= # none, validate, update, create or create-drop
```

Die zweite Möglichkeit besteht darin ein SQL-Skript `src/main/resources/scheme.sql` anzulegen und via DDL die Tabellen zu initialisieren.
Standardmäßig ist diese Art der Initialisierung für embdedded Datenbanken aktiviert, kann jedoch über folgendes Property in der `application.prroperties` geändert werden:

```properties
spring.datasource.initialization-mode= # never, always or embedded
```

**_DOKUMENTATION:_**
[Spring Boot Database Initialization](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html#howto-database-initialization)

### initialisieren der Datenbank mit Daten

Wenn die Datenbank mit Daten initialisiert werden soll existiert die Möglichkeit ein SQL-Skript `src/main/resources/data.sql` anzulegen und via DML die Tabellen mit Daten zu
befüllen.

```sql
INSERT INTO pet_types (name) VALUES
    ('Hamster'),
    ('Maus'),
    ('Fisch'),
    ('Hund'),
    ('Katze'),
    ('Vogel');

insert into pets (name, type, birth_date, price) values
('Klaus', 'Hamster', to_date('13.04.2019', 'dd.mm.yyyy'), 20);
insert into pets (name, type, birth_date, price) values
('Rubert', 'Hund', to_date('18.09.2018', 'dd.mm.yyyy'), 550);
insert into pets (name, type, birth_date, price) values
('Blacky', 'Katze', to_date('12.12.2018', 'dd.mm.yyyy'), 350);
```

Standardmäßig ist diese Art der Initialisierung für embdedded Datenbanken aktiviert, kann jedoch über folgendes Property in der `application.prroperties` geändert werden:

```properties
spring.datasource.initialization-mode= # never, always or embedded
```

**_DOKUMENTATION:_**
[Spring Boot Database Initialization](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html#howto-database-initialization)


## Aufgabenkomplex 4

Der Aufgabenkomplex befasst sich mit der Erstellung von Repositories für SQL in Spring Boot. Ziel dieses Aufgabenkomplexes ist die Übergangsweise Persistenz mit `Map<String, Pet>`
aus dem Domainservice `PetShopService` in eine SQL Datenbank zu verschieben. Und das der Zugriff auf die Datenbank mit Repositories durch Spring Data erfolgt.


### Aufgabe 4.1: aktivieren der H2 Console

Im Workshop wird H2 als Embedded SQL Datenbank verwendet. Im Aufgabenkomplex 1 wurde bei den Abhängigkeiten die H2 Datenbank aus der Kategorie SQL ausgewählt. Diese Abhängigkeit
findet sich in der `pom.xml` des Projekts wieder als `com.h2database:h2`. Um sich mit der Datenbank via Browser verbinden zu können muss zunächst die H2 Console in den
`application.properties` aktiviert werden.

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Nach dem Start der Spring Boot Anwendung ist die H2 Console unter [http://localhost:8080/h2-console]( http://localhost:8080/actuator) und man kann sich mit folgenden Anmeldedaten
gegen die Datenbank verbinden:

| Bezeichnung  | Wert                 |
|:-------------|:---------------------|
| Driver Class | org.h2.Driver        |
| JDBC URL     | `jdbc:h2:mem:testdb` |
| User Name    | sa                   |
| Password     | -                    |

Die Tabellen `pets` und `pet_types` sollten angelegt sein. Für letztere sollten bereits Daten existieren.

**_DOKUMENTATION:_**
[Spring Boot H2 Web Console](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html#boot-features-sql-h2-console)

**_HINWEIS:_** Um SQL-Statements im Log der Spring Boot Anwendung sehen zu können müssen zwei Properties in `application.properties` gesetzt werden:

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Aufgabe 4.2: implementiere ein Repository

Es soll ein Repository `de.osp.springbootworkshop.domain.repository.PetRepository` für das Entity `Pet` umgesetzt werden.
Dabei soll `PetRepository` als Interface implementiert werden und von dem Interface `CrudRepository<T,ID>` ableiten.

**_DOKUMENTATION:_**
[Spring Data Repositories](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories)
[CrudRepository Java Doc](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)


### Aufgabe 4.3: erweitern von Repository mit eigenen Queries

Das Repository `PetRespository` soll um eine Methode zur Abfrage einer `List<Pet>` anhand deren Geburtstag erweitert werden.
Wahlweise kann die Umsetzung des Queries durch den Methodennamen oder durch die Annotation `@Query` erfolgen.

**_DOKUMENTATION:_**
[Spring Data Defining Query Methods](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details),
[Spring Data Query Methods Handling Nullability](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.nullability),

**_HINWEIS:_** Wenn Queries mit der Annotation `@Query` umgesetzt werden, dann wird standardmäßig das Query im Dialekt `JPQL` erwartet.
Jedoch kann in der Annotation `@Query` die Angabe `nativeQuery = true` gemacht werden damit das Query mit dem Dialekt der verwendeten Datenbank erwartet wird.


### Zusatzaufgabe: Testen von Repository mit Data-JPA-Test

Die Query-Methode zur Abfrage von `List<Pet>` anhand deren Geburtstag soll mit einem Data-JPA-Test getestet werden.

**_DOKUMENTATION:_**
[Spring Data JPA Test](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-jpa-test)

**_HINWEIS:_** Das Asssertion-Framwork assertJ ist Teil der Abhängigkeit `org.springframework.boot:spring-boot-starter-test` ([assertJ](http://joel-costigliola.github.io/assertj/)).

**_HINWEIS:_** Standardmäßig werden im Test-Scope die SQL-Skripte `scheme.sql` und `data.sql` ausgeführt.
Ist dieses verhalten nicht im Test-Scope nicht gewünscht kann es in `src/test/resources/application.properties` deaktiviert werden.