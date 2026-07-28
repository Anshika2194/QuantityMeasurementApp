import { FcGoogle } from 'react-icons/fc'
import { FaRulerCombined } from 'react-icons/fa'
import { useAuth } from '../../hooks/useAuth.js'
import styles from './Login.module.css'

export default function Login() {
  const { login } = useAuth()

  return (
    <section className={styles.wrapper}>
      <div className={styles.card}>
        <div className={styles.logo}><FaRulerCombined /></div>
        <h1>Welcome to QuantityMeasurement</h1>
        <p>Sign in to convert, compare and track your quantity measurements, all in one place.</p>
        <button className={styles.googleBtn} onClick={login}>
          <FcGoogle size={22} />
          <span>Continue with Google</span>
        </button>
        <p className={styles.footnote}>By continuing, you agree to sign in securely via Google OAuth. No passwords, ever.</p>
      </div>
    </section>
  )
}
