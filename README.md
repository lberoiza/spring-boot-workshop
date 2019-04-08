# Spring Boot Workshop 2.0

## Vorausetzungen

* JDK 11
* GIT
* IDE mit Unterstützung für Java
* REST-Client z.B. [insomnia](https://insomnia.rest/) oder [SoapUI](https://www.soapui.org/)

## Aufgabenkomplex 4

Der Aufgabenkomplex befasst sich mit der Erstellung von Repositories für SQL in Spring Boot. Ziel dieses Aufgabenkomplexes ist die Übergangsweise Persistenz mit `Map<String, Pet>`
aus dem Domainservice `PetShopService` in eine SQL Datenbank zu verschieben. Und das der Zugriff auf die Datenbank mit Repositories durch Spring Data erfolgt.


### Aufgabe 4.1

Im Workshop wird H2 als Embedded SQL Datenbank verwendet. Im Aufgabenkomplex 1 wurde bei den Abhängigkeiten die H2 Datenbank aus der Kategorie SQL ausgewählt. Diese Abhängigkeit
findet sich in der `pom.xml` des Projekts wieder als `com.h2database:h2`. Um sich mit der Datenbank via Browser verbinden zu können muss zunächst die H2 Console in den
`application.properties` aktiviert werden.

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Nach dem Start der Spring Boot Anwendung ist die H2 Console unter [http://localhost:8080/h2-console]( http://localhost:8080/actuator) und man kann sich mit folgenden Anmeldedaten
gegen die Datenbank verbinden:

| Bezeichnung  | Wert                 |
|:-------------|:---------------------|
| Driver Class | org.h2.Driver        |
| JDBC URL     | `jdbc:h2:mem:testdb` |
| User Name    | sa                   |
| Password     | -                    |

**_DOKUMENTATION:_** Spring Boot H2 Web Console
[Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html#boot-features-sql-h2-console)

**_HINWEIS:_** Um SQL-Statements im Log der Spring Boot Anwendung sehen zu können müssen zwei Properties in `application.properties` gesetzt werden:

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```