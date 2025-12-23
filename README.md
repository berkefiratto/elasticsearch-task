# Fund Search Backoffice (Elasticsearch Case)

This project was implemented as part of a backend case study.

The main focus is **data ingestion**, **Elasticsearch-based querying**, and a **clean separation between persistence and search concerns**.

The UI is intentionally minimal and exists only to make backend behavior easy to test.

---

## Overview

The application allows:

- Importing fund data from an Excel file
- Persisting data in PostgreSQL
- Indexing the same data into Elasticsearch
- Searching, filtering, sorting, and paginating results using Elasticsearch

PostgreSQL is treated as the **source of truth**, while Elasticsearch is used **exclusively for read/query operations**.

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA (PostgreSQL)
- Spring Data Elasticsearch (ELC client)
- Elasticsearch
- Thymeleaf
- Apache POI
- Docker (for local dependencies)

---

## Architecture Notes

### Write Path

- Excel → Service → PostgreSQL
- After successful persistence, records are indexed into Elasticsearch

### Read Path

- All search-related operations are executed against Elasticsearch
- No search logic relies on the relational database

### Layering

- **Controller**: Request handling (UI)
- **Service**: Business logic and Elasticsearch query construction
- **Repositories**:
  - JPA repository for persistence
  - Elasticsearch repository for indexing

Elasticsearch queries are written explicitly using **native DSL**, avoiding repository-derived methods and preventing Elasticsearch from being treated like a relational datastore.

---

## Excel Import

- Excel (`.xlsx`) files are uploaded via the UI
- Parsed using Apache POI
- Each row is mapped to:
  - A JPA entity (PostgreSQL)
  - An Elasticsearch document

Expected fields include:

- Fund code
- Fund name
- Umbrella type
- Multiple return metrics (1M, 3M, 6M, YTD, 1Y, 5Y)

---

## Search Functionality

Search is implemented using **Elasticsearch native queries**, not repository-derived methods.

### Supported Features

#### Search
- Fund code: prefix-based search
- Fund name: partial text search

#### Filtering
- Umbrella type
- Minimum 1-year return (range query)

#### Sorting
- By 1-year or 5-year return
- Ascending / descending
- Implemented using Elasticsearch native sorting  
  (not `PageRequest + Sort`)

#### Pagination
- Backend-driven
- Query state preserved across pages

---

## UI

- Simple Thymeleaf-based backoffice screen
- Used for:
  - Triggering Excel import
  - Executing searches
  - Applying filters and sorting
  - Viewing paginated results

No frontend framework was used, as UI complexity is not the focus of the case.

---

## Running the Application

1. Start Elasticsearch and PostgreSQL  
   *(Docker is recommended)*

2. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
3. Access the UI:
    ```md
    http://localhost:8080/admin/funds


# Idempotency & Import Considerations

For the scope of this case, the Excel import is implemented as a simple, synchronous operation.

The goal was to keep the focus on search behavior and Elasticsearch query design, rather than building a fully robust ingestion pipeline.

In a real production setup, this flow would need to be idempotent to avoid duplicate records when the same file (or the same data) is imported more than once.

This could be addressed in several straightforward ways, such as:
	•	Enforcing a natural idempotency key (e.g. fund code) at the database level
	•	Detecting repeated imports via a file checksum or import identifier

Indexing into Elasticsearch would still be performed only after a successful database write, ensuring consistency between the primary datastore and the search index.

These concerns were deliberately kept out of scope to keep the case focused and avoid introducing complexity unrelated to the core requirements.
