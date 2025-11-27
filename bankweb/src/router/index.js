import { createRouter, createWebHistory } from 'vue-router'

const Login = () => import('../pages/Login.vue')
const Register = () => import('../pages/Register.vue')
const Info = () => import('../pages/Info.vue')
const Forgot = () => import('../pages/Forgot.vue')

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', redirect: '/login' },
        { path: '/login', name: 'login', component: Login, meta: { public: true } },
        { path: '/register', name: 'register', component: Register, meta: { public: true } },
        { path: '/info', name: 'info', component: Info,meta: { public: true } },
        { path: '/forgot', name: 'forgot', component: Forgot, meta: { public: true } },
    ]
})


export default router


