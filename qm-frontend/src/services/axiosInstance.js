import axios from 'axios'

const axiosInstance = axios.create({
  baseURL: `${import.meta.env.VITE_QUANTITY_SERVICE_URL}/api`,
  headers: { 'Content-Type': 'application/json' }
})

axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('jwt')
      window.location.hash = '#/login'
    }
    return Promise.reject(error)
  }
)

export default axiosInstance
