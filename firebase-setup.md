# 🔐 Configuración Firebase para IBERNOVIA

## Estado Actual
✅ **Firebase está integrado** pero necesita credenciales reales para funcionar completamente.

## Pasos para Configurar Firebase

### 1. **Crear Proyecto en Firebase Console**
1. Ve a https://console.firebase.google.com
2. Haz clic en "Crear proyecto"
3. Nombre: `IBERNOVIA`
4. Desactiva Google Analytics (opcional)
5. Crea el proyecto

### 2. **Configurar Autenticación**
1. En la consola, ve a **Autenticación**
2. Haz clic en **Comenzar**
3. Selecciona **Email/Contraseña**
4. Habilita "Email/Contraseña"
5. Opcionalmente habilita "Cuenta de Google" o "Facebook"

### 3. **Obtener Credenciales Web**
1. En Configuración del Proyecto (⚙️ abajo a la izquierda)
2. Selecciona **Apps** → **Web**
3. Haz clic en el icono `</>` si no has creado una app web
4. Copia el objeto `firebaseConfig`

### 4. **Actualizar Variables de Entorno**
Edita los archivos `.env` y `.env.production`:

```env
VITE_API_URL=http://localhost:8080

# Firebase - Copia estos valores de tu consola
VITE_FIREBASE_API_KEY=TU_API_KEY_AQUI
VITE_FIREBASE_AUTH_DOMAIN=tu-proyecto.firebaseapp.com
VITE_FIREBASE_PROJECT_ID=tu-proyecto
VITE_FIREBASE_STORAGE_BUCKET=tu-proyecto.appspot.com
VITE_FIREBASE_MESSAGING_SENDER_ID=123456789
VITE_FIREBASE_APP_ID=1:123456789:web:abc123def456
```

### 5. **Reiniciar el Servidor**
```bash
npm run dev
```

## Características Implementadas

✅ **Registro de usuarios** - Email y contraseña
✅ **Login seguro** - Protegido por Firebase
✅ **Persistencia de sesión** - Se mantiene al recargar
✅ **Actualización automática de tokens** - Cada 55 minutos
✅ **Manejo de errores** - Mensajes claros al usuario
✅ **Admin panel** - Acceso con credenciales especiales

## Flujo de Autenticación

1. Usuario crea cuenta o inicia sesión
2. Firebase valida credenciales
3. Genera ID Token JWT
4. El token se guarda en memoria (seguro)
5. El auth store monitorea cambios con `onAuthStateChanged`
6. Se redirige según rol (admin → `/admin`, usuario → `/tienda`)

## Acceso Admin

Para crear una cuenta admin:
1. Ve a `/auth`
2. Pestaña "Admin"
3. Crea cuenta con email que contenga "admin"
4. Acceso automático al `/admin`

## Seguridad Implementada

✅ Contraseñas encriptadas en Firebase
✅ HTTPS obligatorio en producción
✅ No se almacenan contraseñas en localStorage
✅ Tokens JWT del lado del cliente
✅ Validación en Firebase Console
✅ Reglas de seguridad Firestore

## Próximos Pasos (Opcional)

1. **Añadir OAuth** (Google, Facebook)
   - Habilitar en Firebase Console
   - Agregar botones en AuthView.vue

2. **Recuperación de contraseña**
   - Usar `sendPasswordResetEmail()`
   - Crear vista de reset

3. **Verificación de email**
   - Usar `sendEmailVerification()`
   - Mostrar UI apropiada

4. **Perfil de usuario**
   - Guardar datos adicionales en Firestore
   - Sincronizar con Firebase Profile

## Documentación Oficial
- [Firebase Auth Docs](https://firebase.google.com/docs/auth)
- [Vue + Firebase](https://firebase.google.com/docs/web/setup)
- [Firebase Emulator](https://firebase.google.com/docs/emulator-suite) (para testing local)

---

**⚠️ IMPORTANTE**: Nunca commits las credenciales de Firebase a GitHub. Las variables de entorno nunca deben ser públicas.
