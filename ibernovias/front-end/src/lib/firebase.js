import { initializeApp } from 'firebase/app'
import {
  getAuth,
  setPersistence,
  browserLocalPersistence,
  connectAuthEmulator
} from 'firebase/auth'
import { getFirestore, connectFirestoreEmulator } from 'firebase/firestore'

const parseBooleanEnv = (value) => typeof value === 'string' && value.trim().toLowerCase() === 'true'

const configuredFirebase = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY?.trim() || '',
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN?.trim() || '',
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID?.trim() || '',
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET?.trim() || '',
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID?.trim() || '',
  appId: import.meta.env.VITE_FIREBASE_APP_ID?.trim() || ''
}

const missingFirebaseKeys = Object.entries(configuredFirebase)
  .filter(([, value]) => !value)
  .map(([key]) => key)

const hasCompleteFirebaseConfig = missingFirebaseKeys.length === 0
const requestedGoogleAuth = parseBooleanEnv(import.meta.env.VITE_ENABLE_GOOGLE_AUTH ?? 'false')

const firebaseStatus = {
  requestedGoogleAuth,
  hasCompleteFirebaseConfig,
  missingKeys: missingFirebaseKeys,
  canUseGoogleAuth: requestedGoogleAuth && hasCompleteFirebaseConfig,
  message: requestedGoogleAuth
    ? hasCompleteFirebaseConfig
      ? ''
      : `Faltan variables de Firebase en frontend: ${missingFirebaseKeys.join(', ')}`
    : 'El acceso con Google/Firebase esta desactivado por configuracion.'
}

if (requestedGoogleAuth && !hasCompleteFirebaseConfig) {
  console.warn(
    `Firebase no esta completamente configurado. Faltan: ${missingFirebaseKeys.join(', ')}`
  )
}

let app = null
let auth = null
let db = null

if (firebaseStatus.canUseGoogleAuth) {
  app = initializeApp(configuredFirebase)
  auth = getAuth(app)
  db = getFirestore(app)

  setPersistence(auth, browserLocalPersistence)
    .catch((error) => console.error('Error configurando persistencia:', error))

  if (import.meta.env.MODE === 'development' && window.location.hostname === 'localhost') {
    try {
      // Descomenta las lineas siguientes si usas emuladores locales
      // connectAuthEmulator(auth, 'http://localhost:9099', { disableWarnings: true })
      // connectFirestoreEmulator(db, 'localhost', 8080)
    } catch {
      // El emulador ya esta configurado
    }
  }
}

export { auth, db, app, firebaseStatus }
export const canUseGoogleAuth = firebaseStatus.canUseGoogleAuth
