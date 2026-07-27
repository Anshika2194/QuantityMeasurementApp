import { FaGithub, FaLinkedin, FaRulerCombined } from 'react-icons/fa'
import styles from './Footer.module.css'

export default function Footer() {
  return (
    <footer className={styles.footer}>
      <div className={`container ${styles.inner}`}>
        <div className={styles.brand}>
          <FaRulerCombined /> <span>QuantityMeasurement</span>
        </div>
        <p>&copy; {new Date().getFullYear()} QuantiFy. Built with Spring Boot & React.</p>
        <div className={styles.socials}>
          <a href="https://github.com" target="_blank" rel="noreferrer"><FaGithub /></a>
          <a href="https://linkedin.com" target="_blank" rel="noreferrer"><FaLinkedin /></a>
        </div>
      </div>
    </footer>
  )
}