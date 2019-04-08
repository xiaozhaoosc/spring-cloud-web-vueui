import Layout from '@/views/layout/Layout'

const systemConfigRouter = {
  path: '/system-Config',
  component: Layout,
  redirect: 'noredirect',
  name: '系统配置管理',
  meta: {
    title: '系统配置管理',
    icon: 'sys_manage',
    roles: ['corepeizi']
  },
  children: [
    {
      path: '/coreroleList',
      component: () => import('@/views/admin/coreroleList'),
      name: 'CoreroleList',
      meta: { title: '角色管理', icon: 'role_manage', roles: ['corerole'] }
    },
    {
      path: '/coreuserList',
      component: () => import('@/views/admin/coreuserList'),
      name: 'CoreuserList',
      meta: { title: '用户管理', icon: 'user_manage', roles: ['coreuser'] }
    },
    {
      path: '/coremenuList',
      component: () => import('@/views/admin/coremenuList'),
      name: 'CoremenuList',
      meta: { title: '菜单管理', icon: 'menu_manage', roles: ['coremenu'] }
    },
    {
      path: '/corefunctionList',
      component: () => import('@/views/admin/corefunctionList'),
      name: 'CorefunctionList',
      meta: { title: '功能管理', icon: 'fun_manage', roles: ['corefunction'] }
    },
    {
      path: '/coreorgList',
      component: () => import('@/views/admin/coreorgList'),
      name: 'CoreorgList',
      meta: { title: '部门管理', icon: 'org_manage', roles: ['coreorg'] }
    },
    {
      path: '/scparamList',
      component: () => import('@/views/scparam/scparamList'),
      name: 'ScparamList',
      meta: { title: '参数管理', icon: 'param_manage', roles: ['scparam'] }
    }
  ]
}

export default systemConfigRouter
