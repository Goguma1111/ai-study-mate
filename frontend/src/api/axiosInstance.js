import axios from 'axios'

// 모든 API 요청은 이 인스턴스를 통해 보냅니다.
const axiosInstance = axios.create({
  baseURL: 'http://localhost:8081', // 백엔드 서버 주소 (MySQL이 8080을 쓰고 있어서 백엔드는 8081)
  headers: {
    'Content-Type': 'application/json',
  },
})

// 요청 인터셉터: 모든 요청이 서버로 나가기 직전에 이 함수를 거칩니다.
// localStorage에 로그인 토큰이 저장되어 있으면, 자동으로 헤더에 붙여줍니다.
// 덕분에 앞으로 만들 모든 API 호출 코드에서 토큰을 일일이 챙기지 않아도 됩니다.
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export default axiosInstance
