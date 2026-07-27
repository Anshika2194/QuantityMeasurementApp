import { useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { FiMenu, FiX } from 'react-icons/fi'
import { FaRulerCombined } from 'react-icons/fa'
import { useAuth } from '../../hooks/useAuth.js'
import styles from './Navbar.module.css'

export default function Navbar() {
  const [open, setOpen] = useState(false)
  const { isAuthenticated, user, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/')
    setOpen(false)
  }

  const linkClass = ({ isActive }) => isActive ? `${styles.link} ${styles.active}` : styles.link

  return (
    <header className={styles.navbar}>
      <div className={`container ${styles.inner}`}>
        <NavLink to="/" className={styles.brand} onClick={() => setOpen(false)}>
          <FaRulerCombined className={styles.brandIcon} />
          <span>QuantityMeasurement</span>
        </NavLink>

        <button className={styles.toggle} onClick={() => setOpen(!open)} aria-label="Toggle menu">
          {open ? <FiX size={24} /> : <FiMenu size={24} />}
        </button>

        <nav className={`${styles.links} ${open ? styles.linksOpen : ''}`}>
          {!isAuthenticated && (
            <>
              <NavLink to="/" className={linkClass} onClick={() => setOpen(false)}>Home</NavLink>
              <NavLink to="/about" className={linkClass} onClick={() => setOpen(false)}>About</NavLink>
              <NavLink to="/login" className={styles.ctaLink} onClick={() => setOpen(false)}>Login</NavLink>
            </>
          )}
          {isAuthenticated && (
            <>
              <NavLink to="/dashboard" className={linkClass} onClick={() => setOpen(false)}>Dashboard</NavLink>
              <NavLink to="/history" className={linkClass} onClick={() => setOpen(false)}>History</NavLink>
              <span className={styles.profile}>{user?.email}</span>
              <button className={styles.logoutBtn} onClick={handleLogout}>Logout</button>
            </>
          )}
        </nav>
      </div>
    </header>
  )
}