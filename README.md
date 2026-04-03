
# HR Platform

Web aplikacija za upravljanje kandidatima i njihovim veštinama.

## Tehnologije

- **Backend:** Java 17, Spring Boot 3.3.5, Spring Data JPA
- **Baza podataka:** MySQL
- **Dokumentacija:** Swagger (OpenAPI)
- **Testovi:** JUnit 5, Mockito, MockMvc
- **Frontend:** React, Bootstrap

## Pokretanje projekta

### Preduslovi
- Java 17
- MySQL
- Node.js

### Baza podataka
1. Otvori MySQL Workbench
2. Pokreni `schema.sql`

### Backend
1. Otvori projekat u IntelliJ
2. Kreiraj `src/main/resources/application.properties`:
3. spring.datasource.url=jdbc:mysql://localhost:3306/hr_platform
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   server.port=8080
Pokreni aplikaciju

### Frontend
cd frontend
npm install
npm start

### API Dokumentacija
Nakon pokretanja: `http://localhost:8080/swagger-ui/index.html`


## Najzahtevniji deo

Najzahtevniji deo implementacije bili su JUnit testovi, jer sam ih pisao prvi
put. Morao sam da savladam dva nova koncepta istovremeno — JUnit 5 za pisanje
testova i Mockito za "lažiranje" baze podataka, što je zahtevalo dobro
razumevanje toka podataka kroz celu aplikaciju. Implementirao sam ukupno 18
testova za Service i Controller sloj.

React frontend bio je takođe izazovan zbog manjka iskustva sa njim, ali uz
malo truda uspeo sam da implementiram sve potrebne funkcionalnosti.

Generalno, proces pravljenja ove aplikacije bio je zanimljiv upravo zato što
nisam nailazio na prevelike prepreke — većina tehnologija i koncepata bila mi
je poznata, što mi je omogućilo da se fokusiram na kvalitet implementacije
umesto na savladavanje osnova.