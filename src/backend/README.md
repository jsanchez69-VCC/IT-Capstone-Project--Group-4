# IT Onboarding/Offboarding System — Backend

Spring Boot REST API for the Ethos Events onboarding/offboarding capstone project (Group 4).
It covers user login, employee profiles, onboarding/offboarding processes with checklists,
equipment tracking, help desk tickets, and a dashboard summary.

## Stack
- Java 21 (also runs on newer JDKs such as 25), Spring Boot 3.5
- Spring Web (REST API), Spring Data JPA, Spring Security (password hashing)
- H2 in-memory database for now. Data resets every time the app restarts.
  A switch to MySQL is prepared in `application.properties`.

## Running it
In VS Code, run `OnboardingApplication`, or from this folder:
```bash
mvn spring-boot:run
```
- API: `http://localhost:8081`
- H2 console: `http://localhost:8081/h2-console`
  (JDBC URL `jdbc:h2:mem:onboardingdb`, user `sa`, no password)

## Project layout
```
model/        JPA entities (User, Employee, Equipment, OnboardingProcess, ChecklistTask, Ticket + enums)
repository/   Spring Data JPA repositories
service/      Business logic
controller/   REST endpoints
dto/          Request bodies
config/       Security + CORS config, error handling, demo data
```

## Demo accounts
Created on every startup while `app.seed-demo-data=true` in `application.properties`.
Turn this off before deployment.

| Username | Password | Role |
|---|---|---|
| admin | Admin123! | ADMIN |
| manager | Manager123! | MANAGER |
| employee | Employee123! | EMPLOYEE |

## Frontend connection
- **CORS:** browsers may call the API from the addresses in `app.cors.allowed-origins`
  (`application.properties`). If your frontend runs somewhere else, add its address there.
- **Login:** the response contains the user's `role`. The frontend uses it to decide which
  pages to show. The API does not block requests by role yet and there are no tokens or sessions.
- **Errors:** every error returns JSON like `{"error": "message"}`.

| Status | Meaning |
|---|---|
| 400 | Bad input, missing required field, unknown id, invalid value |
| 401 | Wrong username or password |
| 409 | Conflict, for example issuing equipment that is already issued |
| 500 | Unexpected server error |

## API reference

### Auth
- `POST /api/auth/register` — `{ username, email, password, role }`
- `POST /api/auth/login` — `{ username, password }` returns `{ id, username, email, role }`

### Employees
- `POST /api/employees` — `{ fullName, email, jobTitle, department }`
- `GET /api/employees`
- `GET /api/employees/{id}`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`

### Onboarding / offboarding processes
- `POST /api/processes/start` — `{ employeeId, type, startDate?, endDate?, taskDescriptions? }`
  - `type` is `ONBOARDING` or `OFFBOARDING`
  - dates are `yyyy-MM-dd`; start defaults to today; end cannot be before start
  - omit `taskDescriptions` to get the default 5-task checklist for that type
  - the response includes the checklist tasks
- `GET /api/processes`
- `GET /api/processes/{id}` — includes status and tasks
- `GET /api/processes/employee/{employeeId}`
- `GET /api/processes/{id}/tasks`
- `PATCH /api/processes/tasks/{taskId}` — `{ "status": "PENDING" | "IN_PROGRESS" | "DONE" }`
  - the process status (`NOT_STARTED`, `IN_PROGRESS`, `COMPLETED`) updates automatically

### Equipment
- `POST /api/equipment` — `{ type, serialNumber }`
- `GET /api/equipment`
- `GET /api/equipment/available`
- `GET /api/equipment/employee/{employeeId}`
- `POST /api/equipment/issue` — `{ equipmentId, employeeId }`
- `POST /api/equipment/{id}/sign-receipt`
- `POST /api/equipment/{id}/return`

### Help desk tickets
- `POST /api/tickets` — `{ title, description?, category?, priority?, employeeId? }`
  (only `title` is required; priority defaults to `MEDIUM`)
- `GET /api/tickets` — filter with `?status=OPEN`
- `GET /api/tickets/{id}`
- `GET /api/tickets/employee/{employeeId}`
- `PATCH /api/tickets/{id}` — any of `status`, `priority`, `assignedTo`
  - status: `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`
  - priority: `LOW`, `MEDIUM`, `HIGH`
  - resolving or closing sets `resolvedDate`; reopening clears it

### Dashboard
- `GET /api/dashboard/summary` — counts for employees, processes (active onboardings and
  offboardings, by status), equipment (by status), and tickets (by status)

## Known limitations
- Anyone can register with any role, including `ADMIN`.
- No access control: roles are stored but endpoints do not check them.
- No tokens or sessions yet (login only returns the user's details).
- Returned equipment stays `RETURNED` instead of going back to `AVAILABLE`.
- Offboarding does not automatically return issued equipment.
- Default checklists have 5 tasks each; the final lists from the team are not applied yet.
- Deleting an employee that still has processes, equipment, or tickets fails.
- Data is in memory and is lost whenever the app restarts.

## Before deployment
1. Set `app.seed-demo-data=false` in `application.properties`.
2. Tighten `SecurityConfig` (`anyRequest().permitAll()` is for development only) and add real
   authentication (JWT or sessions) plus role checks.
3. Switch from H2 to a real database (MySQL settings are in `application.properties`).
4. Restrict `app.cors.allowed-origins` to the deployed frontend address.
5. Deploy early and test, don't wait for the final milestone.
