import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { AuthProvider, useAuth } from './context/AuthContext'
import LoginPage from './pages/LoginPage'
import SignupPage from './pages/SignupPage'
import DashboardPage from './pages/DashboardPage'

// 로그인 안 한 사람이 대시보드 주소로 직접 들어오면 로그인 화면으로 돌려보내는 컴포넌트
function PrivateRoute({ children }) {
  const { user, isLoading } = useAuth()

  if (isLoading) {
    // 새로고침 직후 localStorage 확인 중일 때 잠깐 보여주는 화면 (깜빡임 방지)
    return <p style={{ textAlign: 'center', marginTop: '40px' }}>로딩 중...</p>
  }

  return user ? children : <Navigate to="/login" replace />
}

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignupPage />} />
      <Route
        path="/"
        element={
          <PrivateRoute>
            <DashboardPage />
          </PrivateRoute>
        }
      />
    </Routes>
  )
}

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
