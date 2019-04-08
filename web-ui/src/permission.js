import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import { getToken } from '@/utils/auth' // getToken from cookie

NProgress.configure({ showSpinner: false })// NProgress Configuration

// permission judge function
function hasPermission(roles, permissionRoles) {
  if (roles.indexOf('admin') >= 0) return true // admin permission passed directly
  if (!permissionRoles) return true
  return roles.some(role => permissionRoles.indexOf(role) >= 0)
}

const whiteList = ['/login', '/auth-redirect']// no redirect whitelist

function redirect(to, from, next, NProgress, store, router, Message) {
  /* has token*/
  if (to.path === '/login') {
    next({ path: '/' })
    NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
  } else {
    if (!store.getters.roles) { // 判断当前用户是否已拉取完user_info信息
      store.dispatch('GetUserInfo').then(res => { // 拉取user_info
        const roles = res.data.data.roles // note: roles must be a array! such as: ['editor','develop']
        store.dispatch('GenerateRoutes', { roles }).then(() => { // 根据roles权限生成可访问的路由表
          router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
          next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        })
      }).catch(() => {
        store.dispatch('FedLogOut').then(() => {
          next({ path: '/' })
        })
      })
    } else {
      // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
      if (hasPermission(store.getters.roles, to.meta.roles)) {
        next()
      } else {
        next({ path: '/401', replace: true, query: { noGoBack: true }})
      }
      // 可删 ↑
    }
  }
}

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  if (getToken()) { // determine if there has token
    if (store.state.financeVar.paymentChannels.length === 0) {
      store.dispatch('SetSwitchStatus')
      var varPaymentChannel = store.dispatch('GetSystemPaymentChannels').then(() => {}).catch(() => {})
      var varPlatform = store.dispatch('GetSystemPlatforms').then(() => {}).catch(() => {})
      var varPaymentStatus = store.dispatch('GetSystemPaymentStatuses').then(() => {}).catch(() => {})
      var varPaymentBusinessType = store.dispatch('GetSystemPaymentBusinessTypes').then(() => {}).catch(() => {})
      var varPaymentLoanStatus = store.dispatch('GetSystemLoanStatuses').then(() => {}).catch(() => {})
      Promise.all([varPaymentChannel, varPlatform, varPaymentStatus, varPaymentBusinessType, varPaymentLoanStatus]).then(function() {
        redirect(to, from, next, NProgress, store, router, Message)
      }).catch(function(r) {})
    } else {
      redirect(to, from, next, NProgress, store, router, Message)
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
