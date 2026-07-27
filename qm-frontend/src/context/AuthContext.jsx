import { createContext, useState, useEffect, useCallback } from 'react'
import { authService } from '../services/authService.js'

export const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const token = authService.getToken()
    if (token) {
      setUser(authService.getUserFromToken())
      setIsAuthenticated(true)
    }
    setLoading(false)
  }, [])

  const login = useCallback(() => {
    authService.loginWithGoogle()
  }, [])

  const completeLogin = useCallback((token) => {
    authService.saveToken(token)
    setUser(authService.getUserFromToken())
    setIsAuthenticated(true)
  }, [])

  const logout = useCallback(() => {
    authService.logout()
    setUser(null)
    setIsAuthenticated(false)
  }, [])

  const value = { user, isAuthenticated, loading, login, completeLogin, logout }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}