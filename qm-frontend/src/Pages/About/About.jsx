import { FaReact, FaGoogle, FaLock, FaMobileAlt, FaLayerGroup, FaServer } from 'react-icons/fa'
import Card from '../../components/Card/Card.jsx'
import styles from './About.module.css'

const STACK = [
  { icon: <FaServer />, title: 'Spring Boot', text: 'A robust Java backend exposing REST APIs for every measurement operation, secured with Spring Security.' },
  { icon: <FaReact />, title: 'React 18', text: 'A fast, component-driven frontend built with functional components, hooks, and React Router.' },
  { icon: <FaLock />, title: 'JWT Authentication', text: 'Stateless authentication using signed JSON Web Tokens attached to every protected request.' },
  { icon: <FaGoogle />, title: 'Google OAuth', text: "One-click, passwordless sign-in handled entirely by Google's secure OAuth 2.0 flow." },
  { icon: <FaLayerGroup />, title: 'REST APIs', text: 'Clean, resource-oriented endpoints for compare, convert, add, subtract, divide and history.' },
  { icon: <FaMobileAlt />, title: 'Responsive UI', text: 'A modern, mobile-first interface built with CSS Modules and flexible grid layouts.' }
]

export default function About() {
  return (
    <section className={`container ${styles.wrapper}`}>
      <h1>About QuantityMeasurement</h1>
      <p className={styles.intro}>
        QuantityMeasurement is a full-stack quantity measurement system. It lets you compare, convert, add, subtract
        and divide values across length, weight, volume and temperature — all backed by a persistent history
        of every operation you run.
      </p>
      <div className={styles.grid}>
        {STACK.map((s) => <Card key={s.title} icon={s.icon} title={s.title}>{s.text}</Card>)}
      </div>
    </section>
  )
}