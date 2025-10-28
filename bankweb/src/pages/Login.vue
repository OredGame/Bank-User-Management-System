<script setup>
import {  ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { login } from '../services/auth'

const router = useRouter()
const route = useRoute()

const form = ref({
  account: '',
  password: '',
  remember: false
})

const loading = ref(false)
const errorMsg = ref('')

function validate() {
  errorMsg.value = ''
  if (!form.value.account.trim()) {
    errorMsg.value = '请输入账号'
    return false
  }
  if (!form.value.password) {
    errorMsg.value = '请输入密码'
    return false
  }
  return true
}

async function onSubmit() {
  if (!validate()) return
  loading.value = true
  try {
    // ==============================================
    // 【前后端通讯：发起登录请求】
    // 说明：向后端 POST /auth/login 发送账号与密码
    // 返回成功后保存 token（若后端为 Cookie 会话可省略）
    // ==============================================
    const { data } = await login({ account: form.value.account, password: form.value.password })
    // 当前不使用 JWT：不保存 token，依赖后端会话或返回状态
    const redirect = (route.query.redirect || '/home')
    await router.replace(redirect)
  } catch (err) {
    // 统一错误提示
    errorMsg.value = err?.response?.data?.message || '登录失败，请检查账号或密码'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-slate-50">
    <div class="w-full max-w-lg md:max-w-xl mx-4">
      <div class="text-center mb-8">
        <div class="inline-flex items-center justify-center w-16 h-16 rounded-full bg-pharmacy-primary shadow">
          <span class="text-white text-2xl font-extrabold">Bank</span>
        </div>
        <h1 class="mt-4 text-3xl font-extrabold text-gray-800">安全银行</h1>
        <p class="text-gray-500">安全、高效、专业</p>
      </div>

      <div class="bg-white rounded-2xl shadow-xl p-8">
        <form @submit.prevent="onSubmit" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">账号</label>
            <input v-model="form.account" type="text" placeholder="请输入账号"
                   class="w-full rounded-lg border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 px-4 py-2 outline-none transition" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">密码</label>
            <input v-model="form.password" type="password" placeholder="请输入密码"
                   class="w-full rounded-lg border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 px-4 py-2 outline-none transition" />
          </div>

          <div class="flex items-center justify-between">
            <label class="inline-flex items-center gap-2 select-none">
              <input v-model="form.remember" type="checkbox" class="rounded text-blue-600 focus:ring-blue-500" />
              <span class="text-sm text-gray-600">记住我</span>
            </label>
            <a href="#" class="text-sm text-orange-600 hover:underline">忘记密码？</a>
          </div>

          <button type="submit" :disabled="loading" class="w-full py-3 rounded-lg font-semibold text-white bg-pharmacy-primary hover:bg-pharmacy-primaryDark disabled:opacity-60 disabled:cursor-not-allowed transition shadow">
            {{ loading ? '登录中...' : '登录' }}
          </button>

          <p v-if="errorMsg" class="text-red-600 text-sm">{{ errorMsg }}</p>
          <p class="text-sm text-gray-600">没有账号？<router-link to="/register" class="text-blue-700 hover:underline">去注册</router-link></p>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>


