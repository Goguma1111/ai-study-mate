import axios from 'axios'

// 모든 API 요청은 이 인스턴스를 통해 보냅니다.
// 나중에 로그인 기능을 추가하면, 여기에 JWT 토큰을 자동으로 헤더에 붙이는 코드를 추가하면 됩니다.
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8081', // 백엔드 서버 주소
  headers: {
    'Content-Type': 'application/json',
  },
})

export default axiosInstance
