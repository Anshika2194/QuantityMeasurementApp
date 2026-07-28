import { useEffect } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { useAuth } from '../../hooks/useAuth.js'
import styles from './LoginCallback.module.css'

export default function LoginCallback() {
  const [searchParams] = useSearchParams()
  const navigate = useNavigate()
  const { completeLogin } = useAuth()

  useEffect(() => {
    const token = searchParams.get('token')
    if (token) {
      completeLogin(token)
      navigate('/dashboard', { replace: true })
    } else {
      navigate('/login', { replace: true })
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return <p className={styles.text}>Signing you in...</p>
}
