const LOGIN_URL = `${import.meta.env.VITE_AUTH_SERVICE_URL}/oauth2/authorization/google`

export const authService = {
  loginWithGoogle: () => {
    window.location.href = LOGIN_URL
  },
  saveToken: (token) => localStorage.setItem('jwt', token),
  getToken: () => localStorage.getItem('jwt'),
  logout: () => localStorage.removeItem('jwt'),
  isAuthenticated: () => !!localStorage.getItem('jwt'),
  getUserFromToken: () => {
    const token = localStorage.getItem('jwt')
    if (!token) return null
    try {
      const payload = JSON.parse(atob(token.split('.')[1]))
      return { email: payload.sub }
    } catch {
      return null
    }
  }
}
