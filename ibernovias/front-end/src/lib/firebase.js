import { initializeApp } from 'firebase/app'
import {
  getAuth,
  setPersistence,
  browserLocalPersistence,
  connectAuthEmulator
} from 'firebase/auth'
import { getFirestore, connectFirestoreEmulator } from 'firebase/firestore'

const configuredFirebase = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY?.trim() || '',
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN?.trim() || '',
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID?.trim() || '',
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET?.trim() || '',
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID?.trim() || '',
  appId: import.meta.env.VITE_FIREBASE_APP_ID?.trim() || ''
}

const hasCompleteFirebaseConfig = Object.values(configuredFirebase).every(Boolean)

if (import.meta.env.PROD && !hasCompleteFirebaseConfig) {
  throw new Error(
    'Firebase no esta configurado correctamente en produccion. Revisa las variables VITE_FIREBASE_* del frontend.'
  )
}

if (!import.meta.env.PROD && !hasCompleteFirebaseConfig) {
  console.warn(
    'Firebase no esta completamente configurado en desarrollo. El registro/login con Firebase puede fallar hasta definir VITE_FIREBASE_*.'
  )
}

const firebaseConfig = hasCompleteFirebaseConfig
  ? configuredFirebase
  : {
      apiKey: 'AIzaSyDemoKeyForDevelopment',
      authDomain: 'ibernovia-demo.firebaseapp.com',
      projectId: 'ibernovia-demo',
      storageBucket: 'ibernovia-demo.appspot.com',
      messagingSenderId: '123456789',
      appId: '1:123456789:web:abcdef123456'
    }

const app = initializeApp(firebaseConfig)

const auth = getAuth(app)
setPersistence(auth, browserLocalPersistence)
  .catch((error) => console.error('Error configurando persistencia:', error))

const db = getFirestore(app)

if (import.meta.env.MODE === 'development' && window.location.hostname === 'localhost') {
  try {
    // Descomenta las lineas siguientes si usas emuladores locales
    // connectAuthEmulator(auth, 'http://localhost:9099', { disableWarnings: true })
    // connectFirestoreEmulator(db, 'localhost', 8080)
  } catch {
    // El emulador ya esta configurado
  }
}

export { auth, db, app }
