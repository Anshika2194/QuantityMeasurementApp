import { FaReact, FaGoogle, FaLock, FaMobileAlt, FaLayerGroup, FaServer } from 'react-icons/fa'
import Card from '../../components/Card/Card.jsx'
import styles from './About.module.css'

const STACK = [
  { icon: <FaServer />, title: 'Spring Boot Microservices', text: 'Two independent services — auth-service and quantity-service — each with their own port and responsibility.' },
  { icon: <FaReact />, title: 'React 18', text: 'A fast, component-driven frontend built with functional components, hooks, and React Router.' },
  { icon: <FaLock />, title: 'JWT Authentication', text: 'auth-service issues signed tokens; quantity-service validates them independently using a shared secret.' },
  { icon: <FaGoogle />, title: 'Google OAuth', text: "One-click, passwordless sign-in handled entirely by Google's secure OAuth 2.0 flow." },
  { icon: <FaLayerGroup />, title: 'REST APIs', text: 'Clean, resource-oriented endpoints for compare, convert, add, subtract, divide and history.' },
  { icon: <FaMobileAlt />, title: 'Responsive UI', text: 'A modern, mobile-first interface built with CSS Modules and flexible grid layouts.' }
]

export default function About() {
  return (
    <section className={`container ${styles.wrapper}`}>
      <h1>About QuantityMeasurement</h1>
      <p className={styles.intro}>
        QuantityMeasurement is a full-stack quantity measurement system, now built on a microservice
        architecture. It lets you compare, convert, add, subtract and divide values across length,
        weight, volume and temperature — all backed by a persistent history of every operation you run.
      </p>

      <div className={styles.grid}>
        {STACK.map((s) => <Card key={s.title} icon={s.icon} title={s.title}>{s.text}</Card>)}
      </div>
    </section>
  )
}
