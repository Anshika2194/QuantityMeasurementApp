import { Routes, Route } from 'react-router-dom'
import MainLayout from './layouts/MainLayout.jsx'
import ProtectedRoute from './layouts/ProtectedRoute.jsx'
import Home from './pages/Home/Home.jsx'
import Login from './pages/Login/Login.jsx'
import LoginCallback from './pages/LoginCallback/LoginCallback.jsx'
import Dashboard from './pages/Dashboard/Dashboard.jsx'
import History from './pages/History/History.jsx'
import About from './pages/About/About.jsx'
import NotFound from './pages/NotFound/NotFound.jsx'

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />

      <Route element={<MainLayout />}>
        <Route path="/login" element={<Login />} />
        <Route path="/login/callback" element={<LoginCallback />} />
        <Route path="/about" element={<About />} />
        <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
        <Route path="/history" element={<ProtectedRoute><History /></ProtectedRoute>} />
        <Route path="*" element={<NotFound />} />
      </Route>
    </Routes>
  )
}

export default App