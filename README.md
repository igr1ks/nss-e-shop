# E-shop Microservices Project

## Přehled projektu

Projekt je implementován jako **mikroslužbová architektura** a skládá se ze tří samostatných mikroslužeb:

* `user-service`
* `product-service`
* `order-service`

Každou mikroslužbu je nutné spustit samostatně.

---

# Požadované služby v Dockeru

Před spuštěním projektu musí být spuštěny následující služby:

* `userdb`
* `productdb`
* `orderdb`
* `redis`
* `kafka`
* `elasticsearch`

---

# Porty služeb

| Service         | Port |
| --------------- | ---- |
| User Service    | 8082 |
| Product Service | 8080 |
| Order Service   | 8081 |

---

# Autentizace

Autentizace je realizována pomocí **JWT tokenů**.

Postup:

1. Zaregistrovat nového uživatele (`Register`).
2. Přihlásit se (`Login`).
3. Po úspěšném přihlášení server vrátí **JWT token**.
4. Tento token je nutné přidat do všech požadavků, které vyžadují autorizaci.

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# Přidělení role ADMIN

Po registraci má každý uživatel roli **CUSTOMER**.

Pro udělení administrátorských oprávnění je potřeba připojit se k databázi `userdb` a spustit následující SQL příkaz:

```sql
UPDATE users
SET role = 'ADMIN'
WHERE email = 'email použitý při registraci';
```

Poté bude mít uživatel přístup ke všem administrátorským endpointům.

---

# Testovací scénář (Postman)

Projekt obsahuje připravenou Postman kolekci se scénářem testování.

Doporučené pořadí:

1. **Register**

   * registrace nového uživatele

2. **Login**

   * přihlášení
   * získání JWT tokenu

3. **Me as User by Token**

   * ověření identity přihlášeného uživatele

4. **Create Category**

   * vytvoření nové kategorie
   * vyžaduje roli ADMIN

5. **Get Categories**

   * získání seznamu kategorií

6. **Create Product**

   * vytvoření produktu
   * vyžaduje roli ADMIN

7. **Edit Product**

   * úprava produktu

8. **Get Products**

   * získání seznamu produktů

9. **Increase Stock**

   * navýšení skladového množství produktu

10. **ElasticSearch**

    * vyhledávání produktů pomocí Elasticsearch

11. **Create Order**

    * vytvoření objednávky
    * vyžaduje JWT token

---

# Ukázka vytvoření objednávky

```json
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

---

# Použité technologie

Projekt využívá následující technologie:

* **Spring Boot**
* **Spring Security**
* **JWT Authentication**
* **PostgreSQL**
* **Apache Kafka** – komunikace mezi mikroslužbami pomocí událostí.
* **Redis** *(Product Service)* – cache produktů pro rychlejší načítání dat.
* **Elasticsearch** *(Product Service)* – fulltextové vyhledávání produktů.
* **Docker**
* **Maven**
* **JPA / Hibernate**

---

# Použité návrhové vzory (Design Patterns)

V projektu byly implementovány následující návrhové vzory:

### Factory Pattern

Použit pro vytváření objektů:

* `Product`
* DTO objektů

### State Pattern

Použit v `Order Service` pro správu životního cyklu objednávky (`OrderState`).

### Strategy Pattern

Použit pro implementaci různých platebních strategií (`PaymentStrategy`).

### Facade Pattern

Použit při vytváření objednávky pro zjednodušení komunikace mezi jednotlivými částmi systému.

### Adapter / Facade Pattern

Použit v `User Service` při autentizaci a práci s JWT tokeny.

---

# Důležité

Pro správnou funkčnost aplikace musí být spuštěny všechny mikroslužby i podpůrné služby:

* user-service
* product-service
* order-service
* userdb
* productdb
* orderdb
* redis
* kafka
* elasticsearch

`Order Service` komunikuje s `Product Service`, kde ověřuje existenci produktu a jeho dostupnost na skladě před vytvořením objednávky.

Součástí projektu je také připravená **Postman Collection**, která obsahuje kompletní scénář testování všech endpointů.
