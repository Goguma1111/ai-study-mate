import axiosInstance from './axiosInstance'

/**
 * 이 파일은 "백엔드의 /api/auth/** 주소들을 자바스크립트 함수로 감싸놓은 것"입니다.
 * 컴포넌트에서는 axios를 직접 쓰지 않고 이 함수들만 호출하면 됩니다.
 * 나중에 API 주소가 바뀌어도 이 파일 한 곳만 고치면 되는 장점이 있어요.
 */

export function signup({ email, password, name }) {
  return axiosInstance.post('/api/auth/signup', { email, password, name })
}

export function login({ email, password }) {
  return axiosInstance.post('/api/auth/login', { email, password })
}
