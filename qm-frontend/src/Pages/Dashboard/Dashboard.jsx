import { NavLink } from 'react-router-dom'
import { FaHistory, FaInfoCircle, FaUserCircle } from 'react-icons/fa'
import { useAuth } from '../../hooks/useAuth.js'
import MeasurementModule from '../../components/MeasurementModule/MeasurementModule.jsx'
import styles from './Dashboard.module.css'

export default function Dashboard() {
  const { user } = useAuth()
  return (
    <section className={`container ${styles.wrapper}`}>
      <div className={styles.welcomeCard}>
        <FaUserCircle size={42} />
        <div>
          <h1>Welcome back</h1>
          <p>{user?.email}</p>
        </div>
      </div>

      <div className={styles.quickNav}>
        <NavLink to="/history" className={styles.navCard}><FaHistory /><span>View History</span></NavLink>
        <NavLink to="/about" className={styles.navCard}><FaInfoCircle /><span>About This Project</span></NavLink>
      </div>

      <MeasurementModule />
    </section>
  )
}