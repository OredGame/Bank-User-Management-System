<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../services/auth'

const router = useRouter()

const form = ref({
  account: '',
  password: '',
  confirm: ''
})

const loading = ref(false)
const errorMsg = ref('')

function validate() {
  errorMsg.value = ''
  if (!form.value.account.trim()) { errorMsg.value = '请输入账号'; return false }
  if (!form.value.password) { errorMsg.value = '请输入密码'; return false }
  if (form.value.password.length < 6) { errorMsg.value = '密码至少6位'; return false }
  if (form.value.password !== form.value.confirm) { errorMsg.value = '两次输入的密码不一致'; return false }
  return true
}

async function onSubmit() {
  if (!validate()) return
  loading.value = true
  try {
    // ==============================================
    // 【前后端通讯：发起注册请求】
    // 说明：向后端 POST /auth/register 提交账号与密码
    // ==============================================
    await register({ account: form.value.account, password: form.value.password })
    // 注册成功：跳转登录页
    await router.replace('/login')
  } catch (err) {
    errorMsg.value = err?.response?.data?.message || '注册失败，请稍后再试'
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
        <h1 class="mt-4 text-3xl font-extrabold text-gray-800">用户注册</h1>
        <p class="text-gray-500">创建你的安全银行账号</p>
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
            <input v-model="form.password" type="password" placeholder="请输入密码（至少6位）"
                   class="w-full rounded-lg border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 px-4 py-2 outline-none transition" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">确认密码</label>
            <input v-model="form.confirm" type="password" placeholder="请再次输入密码"
                   class="w-full rounded-lg border border-gray-300 focus:border-blue-500 focus:ring-2 focus:ring-blue-200 px-4 py-2 outline-none transition" />
          </div>

          <button type="submit" :disabled="loading" class="w-full py-3 rounded-lg font-semibold text-white bg-pharmacy-primary hover:bg-pharmacy-primaryDark disabled:opacity-60 disabled:cursor-not-allowed transition shadow">
            {{ loading ? '注册中...' : '注册' }}
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


