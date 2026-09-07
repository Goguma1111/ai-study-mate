import { useEffect, useState } from 'react'
import axiosInstance from '../api/axiosInstance'

/**
 * 이 페이지의 목적: "프론트가 백엔드와 실제로 대화할 수 있는가?"를 눈으로 확인하기 위함입니다.
 * 화면이 뜨자마자(useEffect) 백엔드의 /api/health 를 호출하고 결과를 상태(state)에 저장합니다.
 */
function TestPage() {
  const [message, setMessage] = useState('백엔드 응답을 기다리는 중...')
  const [isError, setIsError] = useState(false)

  useEffect(() => {
    axiosInstance
      .get('/api/health')
      .then((response) => {
        setMessage(response.data.message)
      })
      .catch(() => {
        setIsError(true)
        setMessage('백엔드 연결에 실패했어요. 백엔드 서버(8080 포트)가 켜져 있는지 확인해주세요.')
      })
  }, [])

  return (
    <div style={{ padding: '40px', fontFamily: 'sans-serif', textAlign: 'center' }}>
      <h1>🐣 AI Study Mate</h1>
      <p style={{ color: isError ? 'crimson' : 'seagreen', fontWeight: 'bold' }}>
        {message}
      </p>
    </div>
  )
}

export default TestPage
