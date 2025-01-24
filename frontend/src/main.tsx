import { createRoot } from 'react-dom/client'
import './assets/index.css'
import { AuthProvider } from './contexts/Auth.tsx'
import { Toaster } from 'react-hot-toast'
import { RoutesApp } from './routes/RoutesApp.tsx'

createRoot(document.getElementById('root')!).render(
  <AuthProvider>
    <Toaster
      position="top-center"
      reverseOrder={false}
    />
    <RoutesApp/>
  </AuthProvider>
)
