import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { signup } from '../api/authApi'
import '../styles/auth.css'

function SignupPage() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [name, setName] = useState('')
  const [errorMessage, setErrorMessage] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)

  const navigate = useNavigate()

  async function handleSubmit(e) {
    e.preventDefault()
    setErrorMessage('')
    setIsSubmitting(true)

    try {
      await signup({ email, password, name })
      alert('회원가입이 완료되었어요! 로그인해주세요.')
      navigate('/login')
    } catch (error) {
      const message = error.response?.data?.message || '회원가입에 실패했어요. 잠시 후 다시 시도해주세요.'
      setErrorMessage(message)
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <div className="auth-tape" />
        <div className="auth-emoji">📚</div>
        <h1 className="auth-title">AI Study Mate</h1>
        <p className="auth-subtitle">계정을 만들고 시작해볼까요?</p>

        <form onSubmit={handleSubmit} className="auth-form">
          <input
            type="text"
            placeholder="이름"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            className="auth-input"
          />
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
            placeholder="비밀번호 (4자 이상)"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="auth-input"
          />

          {errorMessage && <p className="auth-error">{errorMessage}</p>}

          <button type="submit" disabled={isSubmitting} className="auth-button auth-button--secondary">
            {isSubmitting ? '가입 중...' : '회원가입'}
          </button>
        </form>

        <p className="auth-footer">
          이미 계정이 있나요? <Link to="/login">로그인</Link>
        </p>
      </div>
    </div>
  )
}

export default SignupPage
