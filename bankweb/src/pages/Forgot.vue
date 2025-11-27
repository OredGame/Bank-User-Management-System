<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {forgot, register} from '../services/auth'

const router = useRouter()

const form = ref({
  username: '',
  password: '',
  confirmPassword: '',
  phone:''
})

const loading = ref(false)
const errorMsg = ref('')
function validate() {
  errorMsg.value = ''
  if (!form.value.username.trim()) {
    errorMsg.value = '请输入账号';
    return false
  }
  if (!form.value.password) {
    errorMsg.value = '请输入密码';
    return false
  }
  if (form.value.password.length < 6) {
    errorMsg.value = '密码至少6位';
    return false
  }
  if (form.value.password !== form.value.confirmPassword) {
    errorMsg.value = '两次输入的密码不一致';
    return false
  }
  if (!form.value.phone.trim()) {
    errorMsg.value = '请输入手机号';
    return false
  }

  // ✅ 新增：手机号格式校验（11位，以1开头）
  const phoneRegex = /^1[3-9]\d{9}$/
  if (!phoneRegex.test(form.value.phone)) {
    errorMsg.value = '手机号格式不正确（11位，以1开头）'
    return false
  }

  return true
}
async function onSubmit() {
  if (!validate()) return
  loading.value = true
  try {
    // ==============================================
    // 【前后端通讯：发起重置密码请求】
    // 说明：向后端 POST user/register 提交账号与密码
    // ==============================================
    await forgot({ username: form.value.username, password: form.value.password,confirmPassword: form.value.confirmPassword,phone: form.value.phone })
    // 重置成功：跳转登录页
    await router.replace('/login')
  } catch (err) {
    errorMsg.value = err.message || '重置失败，请稍后再试'
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
          <span class="text-white text-2xl font-extrabold">H</span>
        </div>
        <h1 class="mt-4 text-3xl font-extrabold text-gray-800">重置密码</h1>
      </div>

      <div class="bg-white rounded-2xl shadow-xl p-8">
        <form @submit.prevent="onSubmit" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">账号</label>
            <input v-model="form.username" type="text" placeholder="请输入账号"
                   class="w-full rounded-lg border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 px-4 py-2 outline-none transition" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">新密码</label>
            <input v-model="form.password" type="password" placeholder="请输入新密码（至少6位）"
                   class="w-full rounded-lg border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 px-4 py-2 outline-none transition" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">确认密码</label>
            <input v-model="form.confirmPassword" type="password" placeholder="请再次输入密码"
                   class="w-full rounded-lg border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 px-4 py-2 outline-none transition" />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">手机号</label>
            <input v-model="form.phone" type="text" placeholder="请输入手机号"
                   class="w-full rounded-lg border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 px-4 py-2 outline-none transition" />
          </div>
          <button type="submit" :disabled="loading" class="w-full py-3 rounded-lg font-semibold text-white bg-pharmacy-primary hover:bg-pharmacy-primaryDark disabled:opacity-60 disabled:cursor-not-allowed transition shadow">
            {{ loading ? '重置中...' : '重置密码' }}
          </button>

          <p v-if="errorMsg" class="text-red-600 text-sm">{{ errorMsg }}</p>
          <p class="text-sm text-gray-600">已有账号？<router-link to="/login" class="text-blue-700 hover:underline">去登录</router-link></p>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>


