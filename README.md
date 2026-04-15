# IBERNOVIA

Proyecto ecommerce B2B/B2C con:
- Frontend: Vue 3 + Vite
- Backend: Spring Boot + MySQL

## Requisitos

- Node.js 20+
- Java 21
- MySQL 8+

## Arranque rapido

### 1) Backend (Spring Boot)

Ubicacion:
`ibernovias/Proyecto_Ibernovia/Proyecto_Ibernovia`

Variables recomendadas en PowerShell:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="toor"
$env:APP_JWT_SECRET="IBERNOVIA_SUPER_SECRET_KEY_CHANGE_IN_PRODUCTION_2026_64_CHAR_MINIMUM_KEY"

# SMTP Gmail (opcional pero recomendado para formularios de contacto)
$env:SPRING_MAIL_HOST="smtp.gmail.com"
$env:SPRING_MAIL_PORT="587"
$env:SPRING_MAIL_USERNAME="info@ibernovia.es"
$env:SPRING_MAIL_PASSWORD="TU_APP_PASSWORD"
```

Ejecutar:

```powershell
cd ibernovias/Proyecto_Ibernovia/Proyecto_Ibernovia
.\mvnw.cmd spring-boot:run
```

Backend en:
`http://localhost:8080`

### 2) Frontend (Vue + Vite)

Ubicacion:
`ibernovias/front-end`

Instalar dependencias:

```powershell
cd ibernovias/front-end
npm install
```

Ejecutar en desarrollo:

```powershell
npm run dev
```

Frontend en:
- `http://localhost:5174` (o siguiente puerto libre, por ejemplo 5175)

## Variables de entorno frontend

Usa como base:
`ibernovias/front-end/.env.example`

Variables clave:
- `VITE_API_URL=http://localhost:8080`
- Firebase (`VITE_FIREBASE_*`) si quieres usar login social
- `VITE_ADMIN_EMAILS=admin@ibernovia.com,dam@ibernovia.com`

## Seguridad aplicada

- Endpoints admin protegidos por JWT + rol `ADMIN`
- Operaciones de escritura de productos protegidas
- Setup de admin con password hasheada (BCrypt)
- Credenciales externalizadas por variables de entorno

## Notas

- El endpoint publico de contacto es `POST /api/contacto`
- Los endpoints `api/contacto/admin/*` requieren token admin
- No subas claves reales a git