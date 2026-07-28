import { NavLink } from 'react-router-dom'
import { FcGoogle } from 'react-icons/fc'
import { FaRulerCombined } from 'react-icons/fa'
import { useAuth } from '../../hooks/useAuth.js'
import styles from './Home.module.css'

export default function Home() {
  const { isAuthenticated, login } = useAuth()

  return (
    <section className={styles.wrapper}>
      <div className={styles.panel}>
        <div className={styles.left}>
          <div className={styles.brand}>
            <FaRulerCombined />
            <span>QuantityMeasurement</span>
          </div>

          <h1>Precise quantity measurement, beautifully simple</h1>
          <p>
            Convert, compare, add, subtract and divide length, weight, volume
            and temperature — backed by a secure Spring Boot API.
          </p>
        </div>

        <div className={styles.right}>
          <div className={styles.card}>
            <div className={styles.logo}><FaRulerCombined /></div>

            {isAuthenticated ? (
              <>
                <h2>Welcome back</h2>
                <p>You're already signed in.</p>
                <NavLink to="/dashboard" className={styles.googleBtn}>
                  Go to Dashboard
                </NavLink>
              </>
            ) : (
              <>
                <h2>Welcome</h2>
                <p>Sign in to start measuring. No passwords, ever.</p>
                <button className={styles.googleBtn} onClick={login}>
                  <FcGoogle size={20} />
                  <span>Continue with Google</span>
                </button>
              </>
            )}

            <p className={styles.footnote}>
              By continuing, you agree to sign in securely via Google OAuth.
            </p>
          </div>
        </div>
      </div>
    </section>
  )
}
