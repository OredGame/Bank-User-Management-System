<template>
  <div class="layout">
    <!-- 左侧菜单 -->
    <nav class="sidebar">
      <div class="menu-top">
        <h2>管理系统</h2>
        <ul>
          <li class="active">用户信息</li>
        </ul>
      </div>
      <button class="logout-btn" @click="handleLogout">登出</button>
    </nav>

    <!-- 右侧内容区 -->
    <main class="main-content">
      <div class="user-info">
        <h1>用户信息</h1>
        <p><strong>ID：</strong>{{ getUserId() }}</p>
        <p><strong>用户名：</strong>{{ userInfo.username }}</p>

        <!-- 姓名 + 身份证号（共用按钮） -->
        <div class="field-group">
          <div class="input-with-action">
            <label><strong>姓名：</strong></label>
            <div class="input-wrapper">
              <input
                  v-if="editingField === 'idCardName'"
                  v-model="tempName"
                  type="text"
                  class="edit-input"
              />
              <span v-else>{{ userInfo.fullName }}</span>
            </div>
            <div v-if="editingField === 'idCardName'" class="actions">
              <button
                  class="btn save-btn"
                  :disabled="loadingIdCardName"
                  @click="saveIdCardName"
              >
                <span v-if="!loadingIdCardName">✅</span>
                <span v-else>⏳</span>
              </button>
              <button
                  class="btn cancel-btn"
                  :disabled="loadingIdCardName"
                  @click="cancelIdCardName"
              >❌</button>
            </div>
            <button
                v-else
                class="btn edit-trigger"
                :disabled="isIdCardInfoLocked"
                :title="isIdCardInfoLocked ? '姓名和身份证已实名认证，不可修改' : ''"
                @click="startEditIdCardName"
            >✎</button>
          </div>

          <div class="input-with-action">
            <label><strong>身份证号：</strong></label>
            <div class="input-wrapper">
              <input
                  v-if="editingField === 'idCardName'"
                  v-model="tempIdCard"
                  type="text"
                  class="edit-input"
              />
              <span v-else>{{ userInfo.idCardNumber }}</span>
            </div>
            <div v-if="editingField === 'idCardName'" class="actions-placeholder"></div>
            <div v-else class="edit-placeholder"></div>
          </div>
        </div>

        <!-- 手机号 -->
        <div class="input-with-action">
          <label><strong>手机号：</strong></label>
          <div class="input-wrapper">
            <input
                v-if="editingField === 'phone'"
                v-model="tempPhone"
                type="text"
                class="edit-input"
            />
            <span v-else>{{ userInfo.phone }}</span>
          </div>
          <div v-if="editingField === 'phone'" class="actions">
            <button
                class="btn save-btn"
                :disabled="loadingPhone"
                @click="savePhone"
            >
              <span v-if="!loadingPhone">✅</span>
              <span v-else>⏳</span>
            </button>
            <button
                class="btn cancel-btn"
                :disabled="loadingPhone"
                @click="cancelPhone"
            >❌</button>
          </div>
          <button
              v-else
              class="btn edit-trigger"
              @click="startEditPhone"
          >✎</button>
        </div>
      </div>
    </main>

    <!-- 自定义 Toast -->
    <div v-if="toast.visible" class="toast" :class="toast.type">
      {{ toast.message }}
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { contactInfoChange, fetchUserById, identify } from "@/services/auth.js"

const router = useRouter()

// ✅ 获取当前用户 ID 的函数（始终从 localStorage 读取）
const getUserId = () => {
  return localStorage.getItem('Id')
}

// 响应式数据（不包含 id 字段）
const userInfo = ref({
  username: '',
  fullName: '',
  idCardNumber: '',
  phone: ''
})

const tempName = ref('')
const tempIdCard = ref('')
const tempPhone = ref('')

const isIdCardInfoLocked = ref(false)
const editingField = ref(null) // 'idCardName' | 'phone'
const loadingIdCardName = ref(false)
const loadingPhone = ref(false)

const toast = ref({
  visible: false,
  message: '',
  type: 'info'
})

const showToast = (message, type = 'info') => {
  toast.value = { visible: true, message, type }
  setTimeout(() => {
    toast.value.visible = false
  }, 2500)
}

// 编辑逻辑
const startEditIdCardName = () => {
  if (isIdCardInfoLocked.value) {
    showToast('姓名和身份证已实名认证，不可修改', 'warning')
    return
  }
  tempName.value = userInfo.value.fullName
  tempIdCard.value = userInfo.value.idCardNumber
  editingField.value = 'idCardName'
}

const startEditPhone = () => {
  tempPhone.value = userInfo.value.phone
  editingField.value = 'phone'
}

// 保存姓名+身份证
const saveIdCardName = async () => {
  const name = tempName.value.trim()
  const idCard = tempIdCard.value.trim()

  if (!name) {
    showToast('姓名不能为空', 'warning')
    return
  }

  const idCardRegex = /^\d{17}[\dXx]$/
  if (!idCardRegex.test(idCard)) {
    showToast('身份证号必须为18位，最后一位可为X', 'error')
    return
  }

  const currentId = getUserId()
  if (!currentId) {
    showToast('用户未登录', 'error')
    router.replace('/login')
    return
  }

  loadingIdCardName.value = true
  try {
    await identify(currentId, { fullName: name, idCardNumber: idCard })
    await fetchUserInfo()
    showToast('姓名+身份证已保存', 'success')
  } catch {
    showToast('保存失败，请重试', 'error')
  } finally {
    loadingIdCardName.value = false
    editingField.value = null
  }
}

const cancelIdCardName = () => {
  editingField.value = null
}

// 保存手机号
const savePhone = async () => {
  const phone = tempPhone.value.trim()
  const phoneRegex = /^1[3-9]\d{9}$/
  if (!phoneRegex.test(phone)) {
    showToast('手机号格式不正确（11位，以1开头）', 'error')
    return
  }

  const currentId = getUserId()
  if (!currentId) {
    showToast('用户未登录', 'error')
    router.replace('/login')
    return
  }

  loadingPhone.value = true
  try {
    await contactInfoChange(currentId, { phone })
    await fetchUserInfo()
    showToast('手机号已保存', 'success')
  } catch {
    showToast('保存失败，请重试', 'error')
  } finally {
    loadingPhone.value = false
    editingField.value = null
  }
}

const cancelPhone = () => {
  editingField.value = null
}

// 登出
const handleLogout = () => {
  localStorage.removeItem('Id')
  router.replace('/login')
}

// 获取用户信息
const fetchUserInfo = async () => {
  const id = getUserId()
  if (!id) {
    router.replace('/login')
    return
  }

  try {
    const { data } = await fetchUserById(id)
    userInfo.value.username = data.username || ''
    userInfo.value.fullName = data.fullName || ''
    userInfo.value.idCardNumber = data.idCardNumber || ''
    userInfo.value.phone = data.phone || ''

    isIdCardInfoLocked.value = !!(data.fullName?.trim() && data.idCardNumber?.trim())
  } catch (err) {
    showToast('获取用户信息失败', 'error')
    localStorage.removeItem('Id')
    router.replace('/login')
  }
}

// 组件挂载时加载数据
onMounted(() => {
  fetchUserInfo()
})
</script>

<style scoped>
/* 样式保持不变，此处省略以节省篇幅 */
.layout {
  display: flex;
  height: 100vh;
  width: 100vw;
  margin: 0;
  background-color: #f9f9f9;
}

.sidebar {
  width: 200px;
  background-color: #2c3e50;
  color: white;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 20px 0;
  box-shadow: 2px 0 6px rgba(0, 0, 0, 0.1);
}

.sidebar h2 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 18px;
}

.menu-top {
  flex: 1;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  padding: 14px 24px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.sidebar li:hover {
  background-color: #34495e;
}

.sidebar li.active {
  background-color: #3498db;
  font-weight: bold;
}

.logout-btn {
  width: calc(100% - 40px);
  margin: 0 20px;
  padding: 10px;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-btn:hover {
  background-color: #c0392b;
}

.main-content {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 40px 40px 40px 0;
  box-sizing: border-box;
}

.user-info {
  max-width: 500px;
  width: 100%;
  background: white;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.user-info h1 {
  text-align: center;
  margin-bottom: 24px;
  color: #333;
  font-size: 24px;
}

.user-info p {
  font-size: 16px;
  margin: 12px 0;
  color: #444;
}

.input-with-action {
  display: flex;
  align-items: center;
  margin: 16px 0;
}

.input-with-action label {
  width: 100px;
  font-weight: bold;
  color: #2c3e50;
  margin-right: 12px;
  flex-shrink: 0;
}

.input-wrapper {
  flex: 1;
  min-height: 32px;
  display: flex;
  align-items: center;
}

.input-wrapper span {
  font-size: 16px;
  color: #444;
}

.edit-input {
  width: 100%;
  padding: 8px 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.btn {
  width: 28px;
  height: 28px;
  border: none;
  border-radius: 50%;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: 12px;
}

.save-btn {
  background-color: #2ecc71;
  color: white;
}

.save-btn:hover:not(:disabled) {
  background-color: #27ae60;
}

.cancel-btn {
  background-color: #e74c3c;
  color: white;
  margin-left: 6px;
}

.cancel-btn:hover:not(:disabled) {
  background-color: #c0392b;
}

.edit-trigger {
  background: none;
  color: #3498db;
  font-size: 14px;
}

.edit-trigger:hover:not(:disabled) {
  color: #2980b9;
}

.edit-trigger:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.actions,
.actions-placeholder {
  width: 80px;
  display: flex;
  gap: 6px;
}

.edit-placeholder {
  width: 24px;
}

.toast {
  position: fixed;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  padding: 12px 24px;
  border-radius: 6px;
  color: white;
  font-size: 14px;
  z-index: 1000;
  animation: fadeInOut 2.5s ease-in-out;
}

.toast.success { background-color: #2ecc71; }
.toast.error   { background-color: #e74c3c; }
.toast.warning { background-color: #f39c12; }
.toast.info    { background-color: #3498db; }

@keyframes fadeInOut {
  0% { opacity: 0; transform: translateX(-50%) translateY(20px); }
  10% { opacity: 1; transform: translateX(-50%) translateY(0); }
  90% { opacity: 1; transform: translateX(-50%) translateY(0); }
  100% { opacity: 0; transform: translateX(-50%) translateY(20px); }
}
</style>