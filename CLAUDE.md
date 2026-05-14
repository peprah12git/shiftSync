# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=ShiftsyncApplicationTests

# Skip tests during build
mvn clean install -DskipTests
```

## Environment Variables

The application will not start without these set:

| Variable | Purpose |
|---|---|
| `DB_URL` | PostgreSQL JDBC URL (default: `jdbc:postgresql://localhost:5432/shiftsync`) |
| `DB_USERNAME` | DB user (default: `postgres`) |
| `DB_PASSWORD` | DB password |
| `SECRET_KEY` | HS256 signing secret for JWT |
| `SECRET_KEY_EXPIRATION` | JWT TTL in milliseconds |
| `ADMIN_MAIL` | Seeded HR_ADMIN email |
| `ADMIN_PASSWORD` | Seeded HR_ADMIN password |

## Architecture

### Request Lifecycle

```
HTTP Request
  → JwtAuthenticationFilter          (validates JWT, loads UserDetails from DB, checks isActive)
  → SecurityConfig (role checks)
  → Controller
  → Service (business logic + security scoping)
  → Repository / JPA
```

### Role Model

Three roles with distinct access boundaries:

- **EMPLOYEE** — self-service only (`GET/PATCH /api/v1/employees/me`)
- **MANAGER** — read employees and shifts scoped to their assigned locations only
- **HR_ADMIN** — full access including `/api/admin/**`

Manager location assignments live in the `manager_locations` join table. `EmployeeService` auto-injects a location filter whenever the caller's role is `MANAGER`, so managers cannot see outside their locations even if they manipulate query params.

### User vs Employee

There is a deliberate two-entity split:

- **User** — authentication identity (email, password, role, isActive). Created via `POST /api/auth/register`.
- **Employee** — workforce profile (department, location, hours, skills). Created separately by HR_ADMIN via `POST /api/v1/employees/createEmployee`.

A `User` can exist without an `Employee` record. The `Employee` entity holds a `@OneToOne User user` FK. Employee IDs are formatted as `EMP-0001` in responses (derived in `EmployeeMapper`, not stored).

### Security Filter Detail

`JwtAuthenticationFilter` calls `UserDetailsService.loadUserByUsername` on every authenticated request to get a live DB snapshot. Authentication is only set when `userDetails.isEnabled() && userDetails.isAccountNonLocked()` — deactivated users (`isActive=false`) are rejected immediately, not at token expiry.

### Database Migrations

Flyway manages schema via `src/main/resources/db/migration/`:

- `V1__init.sql` — all 13 tables
- `V2__add_table_constaints.sql` — unique/FK constraints + 56 indexes
- `V3__refresh_token_entity.sql` — refresh_tokens table
- `V4__seed_locations_and_departments.sql` — 3 locations, 6 departments

Always add new migrations as the next version (`V5__...`). Never modify existing migration files.

### Dynamic Filtering (Specifications)

`EmployeeSpecification` builds JPA `Predicate` chains for `GET /api/v1/employees/getALlEmployees`. When adding new filter fields, add the predicate to `EmployeeSpecification` and the corresponding field to `EmployeeFilterDTO`.

### Exception → HTTP Mapping

Custom exceptions in `exception/` are mapped in `GlobalExceptionHandler`:

| Exception | Status |
|---|---|
| `DuplicateEmailException` | 409 |
| `EmailNotFoundException` / `ResourceNotFoundException` | 404 |
| `InvalidTokenException` / `AuthenticationException` | 401 |
| `AccessDeniedException` | 403 |
| `ForbiddenFieldException` | 403 |

`ForbiddenFieldException` is thrown by `EmployeeService.updateMe` when an employee attempts to change a field they don't own (role, department, etc.).

### Soft Deletes

Employees are deactivated via `isActive=false` on both the `Employee` and its linked `User`. There are no hard deletes anywhere in the current codebase.

### Optimistic Locking

`ShiftAssignment` uses `@Version` — include the version field in update DTOs when implementing shift assignment mutations to avoid silent overwrites under concurrent requests.
