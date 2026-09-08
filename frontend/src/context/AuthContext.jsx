import { createContext, useContext, useEffect, useState } from 'react'

/**
 * "로그인했는지 여부"는 여러 화면(대시보드, 과목관리, 오늘의 공부 등)에서 다 알아야 하는 정보입니다.
 * 매번 props로 전달하기 번거로우니, Context라는 기능으로 앱 전체에서 공유되는 전역 상태로 만듭니다.
 *
 * 사용법: 어떤 컴포넌트에서든 `const { user, loginUser, logout } = useAuth()` 로 꺼내 쓸 수 있습니다.
 */
const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [isLoading, setIsLoading] = useState(true) // 새로고침 직후 "로그인 정보 확인 중" 상태

  // 앱이 처음 켜질 때(새로고침 포함) localStorage에 저장된 로그인 정보가 있는지 확인합니다.
  // 이 덕분에 로그인한 채로 새로고침해도 다시 로그인 화면으로 튕기지 않습니다.
  useEffect(() => {
    const token = localStorage.getItem('token')
    const savedUser = localStorage.getItem('user')

    if (token && savedUser) {
      setUser(JSON.parse(savedUser))
    }
    setIsLoading(false)
  }, [])

  // 로그인 성공 시 호출: 토큰과 사용자 정보를 저장하고, 화면에서 쓸 상태도 업데이트
  function loginUser(token, userInfo) {
    localStorage.setItem('token', token)
    localStorage.setItem('user', JSON.stringify(userInfo))
    setUser(userInfo)
  }

  // 로그아웃 시 호출: 저장된 정보를 전부 지움
  function logout() {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    setUser(null)
  }

  return (
    <AuthContext.Provider value={{ user, isLoading, loginUser, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

// 다른 컴포넌트에서 이 함수 하나만 호출하면 로그인 상태에 접근할 수 있습니다.
export function useAuth() {
  return useContext(AuthContext)
}
