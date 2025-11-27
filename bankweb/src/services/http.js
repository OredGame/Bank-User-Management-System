import axios from 'axios'

// ===============================
// 【前后端通讯核心：Axios 实例】
// 说明：本项目当前不使用 JWT/Token，依赖后端会话或开放接口
// ===============================

const http = axios.create({
    baseURL:  "http://localhost:8080/",
    timeout: 15000
})

http.interceptors.request.use((config) => config, (error) => Promise.reject(error))
http.interceptors.response.use((response) => response, (error) => Promise.reject(error))

export default http


