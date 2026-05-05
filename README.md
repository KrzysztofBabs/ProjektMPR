Prosta aplikacja webowa CRUD do zarządzania autami, napisana w **Spring Boot**. Projekt podzielony jest na dwie osobne aplikacje: **backend (REST API)** oraz **frontend (widoki Thymeleaf)**.

## Technologie

- Java 21
- Spring Boot 3.3.4 (Web, Data JPA, Thymeleaf, WebFlux)
- Gradle
- Baza danych H2 (in-memory)
- Thymeleaf (widoki)
- RestClient (komunikacja frontend → backend)
- iText / PDFBox / Flying Saucer (generowanie PDF)
- JUnit, Mockito, REST Assured (testy)

## Struktura projektu

```
ProjektMPR/
├── backend(API)/   # REST API – Spring Boot, JPA, H2 (port 8081)
└── frontend/       # Aplikacja widokowa – Thymeleaf (port 8082)
```

## Funkcjonalność

- Wyświetlanie listy aut
- Dodawanie nowego auta
- Edycja istniejącego auta
- Usuwanie auta po ID
- Obsługa błędów (np. brak auta o podanym ID)

### Model `Auto`

- `id` – identyfikator z bazy
- `model` – nazwa modelu
- `rokProdukcji`
- `identyfikator` – generowany automatycznie na podstawie modelu i roku

## Endpointy backendu (REST)

| Metoda | Endpoint | Opis |
|--------|----------|------|
| GET    | `/auta/zRepo`        | Pobranie wszystkich aut |
| POST   | `/autoo/dodaj`       | Dodanie nowego auta |
| PUT    | `/autko/update`      | Aktualizacja auta |
| DELETE | `/auto/wyrzuc/{id}`  | Usunięcie auta po ID |

## Ścieżki frontendu (widoki)

- `/view/all` – lista aut
- `/view/add` – dodawanie auta
- `/view/update` – aktualizacja auta
- `/view/delete` – usuwanie auta
