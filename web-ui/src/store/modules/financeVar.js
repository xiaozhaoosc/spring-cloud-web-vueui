import { fetchSystemVars } from '@/api/financeVar'

const financeVar = {
  state: {
    switchStatuses: [
      { key: true, display_name: '启用' },
      { key: false, display_name: '暂停' }
    ],
    switchStatusKeyValues: {},
    statusYorNKeyValues: {},
    paymentChannels: [],
    channelKeyValues: {},
    platforms: [],
    platformKeyValues: {},
    paymentStatuses: [],
    paymentStatusKeyValues: {},
    paymentBusinessTypes: [],
    paymentBusinessTypeKeyValues: {},
    loanStatuses: [],
    loanStatusKeyValues: {}
  },

  mutations: {
    SET_SWITCH_STATUS: (state) => {
      state.switchStatusKeyValues = state.switchStatuses.reduce((acc, cur) => {
        acc[cur.key] = cur.display_name
        return acc
      }, {})
      state.statusYorNKeyValues = state.switchStatuses.reduce((acc, cur) => {
        acc[cur.key] = cur.key ? '是' : '否'
        return acc
      }, {})
    },
    SET_PAYMENT_CHANNEL: (state, channels) => {
      state.paymentChannels = channels
      state.channelKeyValues = channels.reduce((acc, cur) => {
        acc[cur.key] = cur.display_name
        return acc
      }, {})
    },
    SET_PLATFORM: (state, platforms) => {
      state.platforms = platforms
      state.platformKeyValues = platforms.reduce((acc, cur) => {
        acc[cur.key] = cur.display_name
        return acc
      }, {})
    },
    SET_PAYMENT_STATUS: (state, paymentStatuses) => {
      state.paymentStatuses = paymentStatuses
      state.paymentStatusKeyValues = paymentStatuses.reduce((acc, cur) => {
        acc[cur.key] = cur.display_name
        return acc
      }, {})
    },
    SET_PAYMENT_BUSINESS_TYPE: (state, paymentBusinessTypes) => {
      state.paymentBusinessTypes = paymentBusinessTypes
      state.paymentBusinessTypeKeyValues = paymentBusinessTypes.reduce((acc, cur) => {
        acc[cur.key] = cur.display_name
        return acc
      }, {})
    },
    SET_LOAN_STATUS: (state, loanStatuses) => {
      state.loanStatuses = loanStatuses
      state.loanStatusKeyValues = loanStatuses.reduce((acc, cur) => {
        acc[cur.key] = cur.display_name
        return acc
      }, {})
    }
  },

  actions: {
    SetSwitchStatus({ commit, state }) {
      commit('SET_SWITCH_STATUS')
    },
    // 获取代付渠道信息
    GetSystemPaymentChannels({ commit, state }) {
      return new Promise((resolve, reject) => {
        fetchSystemVars('channel').then(response => {
          commit('SET_PAYMENT_CHANNEL', response.data.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    GetSystemPlatforms({ commit, state }) {
      return new Promise((resolve, reject) => {
        fetchSystemVars('platform').then(response => {
          commit('SET_PLATFORM', response.data.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    GetSystemPaymentStatuses({ commit, state }) {
      return new Promise((resolve, reject) => {
        fetchSystemVars('payment_status').then(response => {
          commit('SET_PAYMENT_STATUS', response.data.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    GetSystemPaymentBusinessTypes({ commit, state }) {
      return new Promise((resolve, reject) => {
        fetchSystemVars('payment_business_type').then(response => {
          commit('SET_PAYMENT_BUSINESS_TYPE', response.data.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },
    GetSystemLoanStatuses({ commit, state }) {
      return new Promise((resolve, reject) => {
        fetchSystemVars('loan_status').then(response => {
          commit('SET_LOAN_STATUS', response.data.data)
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default financeVar
