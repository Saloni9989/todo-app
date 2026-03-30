# backend-app (Spring Boot Todo API)

In-memory CRUD API for todos using `ArrayList` (no database).

## Endpoints

- `GET /api/todo/{id}`
- `POST /api/todos`
- `PUT /api/todos/{id}`
- `DELETE /api/todos/{id}`

## Run

```bash
mvn clean spring-boot:run
```

Server runs on `http://localhost:8080`.

## Test

```bash
mvn test
```

## CORS

This backend allows requests from other origins to `/api/**` (useful for React/Vue frontends during development).

## Examples (cURL)

Create:
```bash
curl -X POST http://localhost:8080/api/todos ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Study\",\"description\":\"Read spring docs\",\"status\":false}"
```

Get:
```bash
curl http://localhost:8080/api/todo/1
```

Update:
```bash
curl -X PUT http://localhost:8080/api/todos/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Study hard\",\"description\":\"Read and practice\",\"status\":true}"
```

Delete:
```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

