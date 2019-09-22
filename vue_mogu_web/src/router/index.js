import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const constantRouterMap = [
  { path: '/', component: () => import('@/views/index') },
  { path: '/index', component: () => import('@/views/index') },
  { path: '/info', component: () => import('@/views/info') },
  { path: '/about', component: () => import('@/views/about') },
  { path: '/life', component: () => import('@/views/life') },
  { path: '/list', component: () => import('@/views/list') },
  { path: '/share', component: () => import('@/views/share') },
  { path: '/time', component: () => import('@/views/time') }
]

const router = new Router({
  routes: constantRouterMap
})

/*
* 添加一个全局的前置钩子函数，这个函数会在路由切换开始时调用。调用发生在整个切换流水线之前。如果此钩子函数拒绝了切换，整个切换流水线根本就不会启动。
* */
// to: Route: 即将要进入的目标 路由对象
// from: Route: 当前导航正要离开的路由
// next: Function: 一定要调用该方法来 resolve 这个钩子。执行效果依赖 next 方法的调用参数。
router.beforeEach((to, from, next) => {
  var reloaded = window.localStorage.getItem('reloaded') || '0'
  if (to.path === '/about') {        
    window.localStorage.setItem('reloaded', reloaded)
  } else if (from.path === '/about') {
    var count = Number(reloaded) + 1
    window.localStorage.setItem('reloaded', count)
  }
  next()
})

export default router
