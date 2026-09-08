import { useAuth } from '../context/AuthContext'
import '../styles/dashboard.css'

/**
 * 지금은 "로그인이 잘 되는지" 확인하기 위한 임시 화면입니다.
 * 2단계(과목 등록)부터 이 자리에 실제 대시보드 내용(과목 카드, D-day 등)이 들어갑니다.
 */
function DashboardPage() {
  const { user, logout } = useAuth()

  return (
    <div className="dashboard-page">
      <div className="auth-emoji">🐣</div>
      <h1 className="dashboard-greeting">반가워요, {user?.name}님!</h1>
      <p className="dashboard-email">{user?.email}로 로그인되어 있어요.</p>
      <button onClick={logout} className="dashboard-logout">
        로그아웃
      </button>
    </div>
  )
}

export default DashboardPage
