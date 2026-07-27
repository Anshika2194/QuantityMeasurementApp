import styles from './Card.module.css'

export default function Card({ icon, title, children, className = '' }) {
  return (
    <div className={`${styles.card} ${className}`}>
      {icon && <div className={styles.icon}>{icon}</div>}
      {title && <h3 className={styles.title}>{title}</h3>}
      <div className={styles.body}>{children}</div>
    </div>
  )
}