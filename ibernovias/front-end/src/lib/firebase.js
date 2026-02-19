import { initializeApp } from 'firebase/app'
import { 
  getAuth, 
  setPersistence, 
  browserLocalPersistence,
  connectAuthEmulator
} from 'firebase/auth'
import { getFirestore, connectFirestoreEmulator } from 'firebase/firestore'

// Configuración de Firebase
// IMPORTANTE: Reemplaza con tus credenciales reales de Firebase
const firebaseConfig = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY || 'AIzaSyDemoKeyForDevelopment',
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN || 'ibernovia-demo.firebaseapp.com',
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID || 'ibernovia-demo',
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET || 'ibernovia-demo.appspot.com',
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID || '123456789',
  appId: import.meta.env.VITE_FIREBASE_APP_ID || '1:123456789:web:abcdef123456'
}

// Inicializar Firebase
const app = initializeApp(firebaseConfig)

// Inicializar Auth
const auth = getAuth(app)
setPersistence(auth, browserLocalPersistence)
  .catch(error => console.error('Error configurando persistencia:', error))

// Inicializar Firestore
const db = getFirestore(app)

// Usar emuladores si estamos en desarrollo (opcional)
if (import.meta.env.MODE === 'development' && window.location.hostname === 'localhost') {
  try {
    // Descomenta las líneas siguientes si usas emuladores locales
    // connectAuthEmulator(auth, 'http://localhost:9099', { disableWarnings: true })
    // connectFirestoreEmulator(db, 'localhost', 8080)
  } catch (e) {
    // El emulador ya está configurado
  }
}

export { auth, db, app }
