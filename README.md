# QuantityMeasurement — Microservice Architecture

Two independent Spring Boot services + one React frontend.

```
auth-service/       Google OAuth login + JWT issuing        → port 8081
quantity-service/   Measurement operations + history + DB   → port 8082
qm-frontend/        React 18 SPA                             → port 5173
```

## How the two backend services trust each other

`auth-service` signs JWTs; `quantity-service` only verifies signatures. Both
read the same `jwt.secret` value from their own `application.properties`.
Because that value must match exactly, `quantity-service` can validate a
token without ever calling `auth-service` — no synchronous dependency
between them at request time.

## One-time setup

1. In `auth-service/.env`, confirm your real Google OAuth credentials are present:
   ```
   GOOGLE_CLIENT_ID=...
   GOOGLE_CLIENT_SECRET=...
   ```
2. In Google Cloud Console, the OAuth client's **Authorized redirect URI**
   must be:
   ```
   http://localhost:8081/login/oauth2/code/google
   ```
3. `quantity-service`'s H2 database is file-based (`./data/quantitydb`) and
   persists across restarts automatically — no setup needed.

## Running everything (3 terminals)

```bash
# Terminal 1
cd auth-service
mvn spring-boot:run

# Terminal 2
cd quantity-service
mvn spring-boot:run

# Terminal 3
cd qm-frontend
npm install
npm run dev
```

Visit **http://localhost:5173**.

## Request flow

1. User clicks "Continue with Google" → browser goes to
   `auth-service` (`8081`) → Google OAuth consent screen.
2. Google redirects back to `auth-service`
   (`/login/oauth2/code/google`).
3. `auth-service` issues a JWT and redirects the browser to
   `http://localhost:5173/#/login/callback?token=...`.
4. React stores the token in `localStorage` and redirects to `/dashboard`.
5. Every `/api/quantity/**` call from React goes straight to
   `quantity-service` (`8082`) with `Authorization: Bearer <token>` —
   validated locally, no call back to `auth-service`.

## Changing ports or the frontend URL

- `auth-service/src/main/resources/application.properties` → `server.port`, `app.frontend-url`
- `quantity-service/src/main/resources/application.properties` → `server.port`
- `qm-frontend/.env` → `VITE_AUTH_SERVICE_URL`, `VITE_QUANTITY_SERVICE_URL`
- CORS allow-list in both services' `SecurityConfig.java` → update if the frontend origin changes
