import axios from 'axios'

// ===============================
// 【前后端通讯核心：Axios 实例】
// 说明：本项目当前不使用 JWT/Token，依赖后端会话或开放接口
// ===============================

const http = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
    timeout: 15000
})

// 可按需添加请求/响应拦截器；当前无 Token，不做额外头部处理
http.interceptors.request.use((config) => config, (error) => Promise.reject(error))
http.interceptors.response.use((response) => response, (error) => Promise.reject(error))

export default http


