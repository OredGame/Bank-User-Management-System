import http from './http'

// ==================================
// 【前后端通讯：认证服务封装】
// 说明：当前不使用 JWT/Token，直接按后端会话接口调用
// ==================================

/**
 * 登录
 * @param {{username: string, password: string}} payload
 * @returns {Promise<any>}
 */
export function login(payload) {
    return http.post('/user/login', payload).then(response => {
        const {code, message, data} = response.data

        if (code === 200) {
            console.log('登录成功：', data)
            return {data, message}

        } else {
            // 失败：抛出错误，携带 message
            const error = new Error(message || '请求失败')
            error.code = code
            throw error
        }
    })
}

/**
 * 获取当前用户信息
 * @returns {Promise<any>}
 */
export function fetchUserById(id) {
    return http.get(`/user/${id}`).then(response => {
        const { code, message, data } = response.data

        if (code === 200) {
            return { data, message }
        } else {
            const error = new Error(message || '请求失败')
            error.code = code
            throw error
        }
    })
}

/**
 * 注册
 * @param {{account: string, password: string}} payload
 * @returns {Promise<any>}
 */
export function register(payload) {
    return http.post('/user/register', payload).then(response => {
        const {code, message, data} = response.data // 👈 解构后端返回的统一结构

        if (code === 200) {
            // 成功：直接返回 user 数据（或整个 data）
            return {data, message} // 或 return { user: data, message }
        } else {
            // 失败：抛出错误，携带 message
            const error = new Error(message || '请求失败')
            error.code = code
            throw error // 👈 这样调用处 catch 能捕获到 message
        }
    })
}

export function forgot(payload) {
    return http.patch('/user/passwordreset', payload).then(response => {
        const {code, message, data} = response.data // 👈 解构后端返回的统一结构

        if (code === 200) {
            // 成功：直接返回 user 数据（或整个 data）
            return {data, message} // 或 return { user: data, message }
        } else {
            // 失败：抛出错误，携带 message
            const error = new Error(message || '请求失败')
            error.code = code
            throw error // 👈 这样调用处 catch 能捕获到 message
        }
    })
}

export function identify(id,payload) {
    return http.patch(`/user/${id}/identification`,payload).then(response => {
        const { code, message, data } = response.data
        if (code === 200) {
            return { data, message }
        } else {
            const error = new Error(message || '请求失败')
            error.code = code
            throw error
        }
    })
}

export function contactInfoChange(id,payload) {
    return http.patch(`/user/${id}/contact-info`,payload).then(response => {
        const { code, message, data } = response.data
        if (code === 200) {
            return { data, message }
        } else {
            const error = new Error(message || '请求失败')
            error.code = code
            throw error
        }
    })
}

// 销户接口（DELETE /user/{id}）
export function deleteUser(id) {
    return http.delete(`/user/${id}`).then(response => {
        const { code, message, data } = response.data
        if (code === 200) {
            return { data, message }
        } else {
            const error = new Error(message || '请求失败')
            error.code = code
            throw error
        }
    })
}