import { NavLink } from 'react-router-dom'
import styles from './NotFound.module.css'

export default function NotFound() {
  return (
    <section className={styles.wrapper}>
      <h1>404</h1>
      <p>The page you're looking for doesn't exist.</p>
      <NavLink to="/" className={styles.link}>Back to Home</NavLink>
    </section>
  )
}
