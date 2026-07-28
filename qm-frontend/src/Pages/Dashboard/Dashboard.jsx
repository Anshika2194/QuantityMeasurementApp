import { NavLink } from 'react-router-dom'
import { FaHistory, FaInfoCircle, FaUserCircle, FaSignOutAlt } from 'react-icons/fa'
import { useAuth } from '../../hooks/useAuth.js'
import MeasurementModule from '../../components/MeasurementModule/MeasurementModule.jsx'
import styles from './Dashboard.module.css'

export default function Dashboard() {
  const { user, logout } = useAuth()

  return (
    <section className={`container ${styles.wrapper}`}>
      <aside className={styles.sidebar}>
        <div className={styles.avatar}><FaUserCircle /></div>
        <h2 className={styles.name}>Welcome back</h2>
        <p className={styles.email}>{user?.email}</p>

        <nav className={styles.nav}>
          <NavLink to="/history" className={styles.navItem}>
            <FaHistory /> History
          </NavLink>
          <NavLink to="/about" className={styles.navItem}>
            <FaInfoCircle /> About
          </NavLink>
          <button className={styles.navItem} onClick={logout}>
            <FaSignOutAlt /> Logout
          </button>
        </nav>
      </aside>

      <div className={styles.main}>
        <MeasurementModule />
      </div>
    </section>
  )
}
