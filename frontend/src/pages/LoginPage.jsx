import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { login } from '../api/authApi'
import { useAuth } from '../context/AuthContext'
import '../styles/auth.css'

function LoginPage() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [errorMessage, setErrorMessage] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)

  const { loginUser } = useAuth()
  const navigate = useNavigate()

  async function handleSubmit(e) {
    e.preventDefault()
    setErrorMessage('')
    setIsSubmitting(true)

    try {
      const response = await login({ email, password })
      const { token, email: userEmail, name } = response.data

      loginUser(token, { email: userEmail, name })
      navigate('/')
    } catch (error) {
      const message = error.response?.data?.message || '로그인에 실패했어요. 잠시 후 다시 시도해주세요.'
      setErrorMessage(message)
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <div className="auth-tape" />
        <div className="auth-emoji">🐣</div>
        <h1 className="auth-title">AI Study Mate</h1>
        <p className="auth-subtitle">로그인하고 오늘의 공부를 시작해요</p>

        <form onSubmit={handleSubmit} className="auth-form">
          <input
            type="email"
            placeholder="이메일"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="auth-input"
          />
          <input
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="auth-input"
          />

          {errorMessage && <p className="auth-error">{errorMessage}</p>}

          <button type="submit" disabled={isSubmitting} className="auth-button auth-button--primary">
            {isSubmitting ? '로그인 중...' : '로그인'}
          </button>
        </form>

        <p className="auth-footer">
          아직 계정이 없나요? <Link to="/signup">회원가입</Link>
        </p>
      </div>
    </div>
  )
}

export default LoginPage
