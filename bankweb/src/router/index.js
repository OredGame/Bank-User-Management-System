import { createRouter, createWebHistory } from 'vue-router'

const Login = () => import('../pages/Login.vue')
const Register = () => import('../pages/Register.vue')
const Home = () => import('../pages/Home.vue')


const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', redirect: '/login' },
        { path: '/login', name: 'login', component: Login, meta: { public: true } },
        { path: '/register', name: 'register', component: Register, meta: { public: true } },
        { path: '/home', name: 'home', component: Home },
    ]
})


export default router


