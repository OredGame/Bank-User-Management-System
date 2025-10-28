import http from './http'

// ==================================
// 【前后端通讯：认证服务封装】
// 说明：当前不使用 JWT/Token，直接按后端会话接口调用
// ==================================

/**
 * 登录
 * @param {{account: string, password: string}} payload
 * @returns {Promise<any>}
 */
export function login(payload) {
    // 醒目：向后端发起登录请求（无 JWT 处理）
    return http.post('/auth/login', payload)
}

/**
 * 退出登录
 * @returns {Promise<any>}
 */
export function logout() {
    return http.post('/auth/logout')
}

/**
 * 获取当前用户信息
 * @returns {Promise<any>}
 */
export function fetchMe() {
    return http.get('/auth/me')
}

/**
 * 注册
 * @param {{account: string, password: string}} payload
 * @returns {Promise<any>}
 */
export function register(payload) {
    // 【前后端通讯：注册请求】
    return http.post('/auth/register', payload)
}


