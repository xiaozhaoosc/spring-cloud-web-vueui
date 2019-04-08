<template>
  <div class="app-container">
    <div class="filter-container">
      <el-select v-model="listQuery.appName" :placeholder="'APP平台'" clearable style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option label="惠花花" value="4">惠花花</el-option>
        <el-option label="秒购" value="5">秒购</el-option>
        <el-option label="分多多" value="6">分多多</el-option>
      </el-select>
      <el-select v-model="listQuery.loanType" :placeholder="'贷种产品'" clearable style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option label="极速贷" value="1">极速贷</el-option>
        <el-option label="大额贷" value="2">大额贷</el-option>
      </el-select>
      <el-input :placeholder="'费率等级'" v-model="listQuery.rateLevel" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>

      <el-select v-model="listQuery.status" :placeholder="'状态'" clearable style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option label="无效" value="0">无效</el-option>
        <el-option label="有效" value="1">有效</el-option>
      </el-select>

      <el-button v-waves v-permission="['rateConfig.query']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <el-button v-waves v-permission="['rateConfig.save']" class="filter-item" type="primary" icon="el-icon-add" @click="handleSaveOrUpdate()">{{ $t('table.add') }}</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;">
      <el-table-column :label="'序号'" align="center" width="60">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column :label="'ID'" align="center" width="60">
        <template slot-scope="scope">
          <span>{{ scope.row.rateId }}</span>
        </template>
      </el-table-column> -->
      <el-table-column :label="'APP平台'" align="center" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.appName | statusFilter">{{ scope.row.appName | appNameFormatFilter() }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="'贷种产品'" align="center" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.loanType | statusFilter">{{ scope.row.loanType | loanTypeFormatFilter() }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="'费率等级'" align="center" width="90">
        <template slot-scope="scope">
          <span>{{ scope.row.rateLevel }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'期数'" align="center" width="60">
        <template slot-scope="scope">
          <span>{{ scope.row.period }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'最小费率(%)'" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.minimumRate | rateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'最大费率(%)'" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.maximumRate | rateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'风险保障金费率(%)'" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.riskEnsureRatio | rateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('保费占比(%)')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.insuranceRatio | rateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('资产管理费占比(%)')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.managementRatio | rateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('vip服务费占比(%)')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.vipServiceRatio | rateFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'备注'" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.remark }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('状态')" align="center" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | rateStatusFormatFilter() }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column :label="'创建时间'" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="showActions" :label="$t('table.actions')" align="center" width="180" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['rateConfig.save']" type="primary" size="mini" @click="handleSaveOrUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
          <el-button v-permission="['rateConfig.save']" type="danger" size="mini" @click="handleDelete(scope.row)">{{ $t('table.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.page" :page-sizes="[10,20,30,50]" :page-size="listQuery.limit" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>

    <!-- 添加修改弹框 -->
    <el-dialog :title="'Edit'" :visible.sync="dialogFormVisible" width="800px">
      <el-form ref="dataForm" :inline="true" :model="temp" label-width="150px" style="margin-left:40px;">
        <el-form-item :label="'APP平台:'" prop="appName" required>
          <el-select v-model="temp.appName" class="filter-item" style="width:100px">
            <el-option label="惠花花" value="4">惠花花</el-option>
            <el-option label="秒购" value="5">秒购</el-option>
            <el-option label="分多多" value="6">分多多</el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="'贷种产品:'" prop="loanType" required>
          <el-select v-model="temp.loanType" class="filter-item" style="width:110px">
            <el-option label="极速贷" value="1">极速贷</el-option>
            <el-option label="大额贷" value="2">大额贷</el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item :label="'ID:'" prop="rateId" >
          <el-input v-model="temp.rateId" disabled style="width:100px" />
        </el-form-item> -->
        <!-- <el-select v-model="listQuery.platform" :placeholder="$t('table.platform')" clearable style="width: 140px" class="filter-item" @change="handleFilter">
          <el-option v-for="item in $store.state.financeVar.platforms" :key="item.key" :label="item.display_name" :value="item.key"/>
        </el-select> -->
        <el-form-item :label="'费率等级:'" prop="rateLevel" required>
          <el-input v-model="temp.rateLevel" style="width:100px"/>
        </el-form-item>
        <el-form-item :label="'期数:'" prop="period" required>
          <el-input v-model="temp.period" style="width:110px" placeholder="最多俩位数"/>
        </el-form-item>
        <el-form-item :label="'最小费率:'" prop="minimumRate" required>
          <el-input v-model="temp.minimumRate" style="width:100px"/>%
        </el-form-item>
        <el-form-item :label="'最大费率:'" prop="maximumRate" required>
          <el-input v-model="temp.maximumRate" style="width:100px"/>%
        </el-form-item>
        <el-form-item :label="'风险保障金费率:'" prop="riskEnsureRatio" required>
          <el-input v-model="temp.riskEnsureRatio" style="width:100px"/>%
        </el-form-item>
        <el-form-item :label="'保费占比:'" prop="insuranceRatio" required>
          <el-input v-model="temp.insuranceRatio" style="width:100px"/>%
        </el-form-item>
        <el-form-item :label="'资产管理费占比:'" prop="managementRatio" required>
          <el-input v-model="temp.managementRatio" style="width:100px"/>%
        </el-form-item>
        <el-form-item :label="'vip服务费占比:'" prop="vipServiceRatio" required>
          <el-input v-model="temp.vipServiceRatio" style="width:100px"/>%
        </el-form-item>
        <el-form-item :label="'状态:'" prop="status" required>
          <el-radio-group v-model="temp.status">
            <el-radio label="0" >无效</el-radio>
            <el-radio label="1" >有效</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="'备注:'">
          <el-input :autosize="{ minRows: 2, maxRows: 4}" v-model="temp.remark" type="textarea" style="width:500px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="saveDataForm()">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, saveData, deleteData } from '@/api/rateConfig'
import waves from '@/directive/waves' // 水波纹指令

export default {
  directives: {
    waves
  },
  filters: {
    // 占比拼接百分号
    rateFilter(rate) {
      rate = rate.trim()
      if (rate !== '' && rate !== undefined && rate !== null && rate !== 'null') {
        rate = rate + '%'
      }
      return rate
    },
    // app平台
    appNameFormatFilter(status) {
      const statusMap = {
        '4': '惠花花',
        '5': '秒购',
        '6': '分多多'
      }
      return statusMap[status]
    },
    // 状态
    rateStatusFormatFilter(status) {
      const statusMap = {
        '0': '无效',
        '1': '有效'
      }
      return statusMap[status]
    },
    // 贷款类型
    loanTypeFormatFilter(status) {
      const statusMap = {
        '1': '极速贷',
        '2': '大额贷'
      }
      return statusMap[status]
    },
    statusFilter(status) {
      const statusMap = {
        0: '',
        1: 'warning',
        2: 'success',
        3: 'info',
        4: 'warning',
        5: 'success',
        6: '',
        true: 'success',
        false: 'info'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20
      },
      showActions: true,
      temp: {

      },
      dialogFormVisible: false,
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data.data.items
        this.total = response.data.data.total
        this.listLoading = false
      }, () => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    handleSaveOrUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      // 判断编辑还是新增
      if (typeof row !== 'undefined') { // 编辑
        this.temp.status = this.temp.status.toString()
        this.temp.loanType = this.temp.loanType.toString()
        this.temp.appName = this.temp.appName.toString()
      }

      this.dialogFormVisible = true
    },

    // 占比输入数字验证
    ratioCheck(name, value) {
      if (value !== '' && value !== undefined && value !== null && value !== 'null') {
        // 判断value为正数，最多俩位小数，
        var reg1 = /^[1-9][0-9]*(\.[0-9]{1,2})?$/
        var reg2 = /^0(\.[0-9]{1,2})?$/

        value = value.trim()
        if (!reg1.test(value) && !reg2.test(value)) {
          this.$message(
            {
              showClose: true,
              message: name + ' 请输入非负数字，最多俩位小数',
              type: 'error'
            }
          )
          return true
        } else {
          return false
        }
      } else {
        this.$message(
          {
            showClose: true,
            message: name + ' 不能为空',
            type: 'error'
          }
        )
        return true
      }
    },
    // 期数验证
    periodCheck(name, value) {
      if (value !== '' && value !== undefined && value !== null && value !== 'null') {
        // 判断value为正整数，最多俩位
        var reg1 = /^[1-9][0-9]?$/

        if (!reg1.test(value)) {
          this.$message(
            {
              showClose: true,
              message: name + ' 请输入非负数字，最多俩位数',
              type: 'error'
            }
          )
          return true
        }
      }
      return false
    },

    // 非空验证
    isEmpty(name, value) {
      if (value === undefined || value === null || value === 'null') {
        this.$message(
          {
            showClose: true,
            message: name + ' 不能为空',
            type: 'error'
          }
        )
        return true
      } else {
        value = value.trim()
        if (value === '') {
          this.$message(
            {
              showClose: true,
              message: name + ' 不能为空',
              type: 'error'
            }
          )
          return true
        }
      }
      return false
    },

    saveDataForm() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 费率等级非空校验
          if (this.isEmpty('费率等级', this.temp.rateLevel)) { return }
          // 期数参数校验 非空，必须是数字
          if (this.periodCheck('期数', this.temp.period)) { return }
          // 验证参数
          if (this.ratioCheck('最小费率', this.temp.minimumRate)) { return }
          if (this.ratioCheck('最大费率', this.temp.maximumRate)) { return }
          if (this.ratioCheck('风险保障金费率', this.temp.riskEnsureRatio)) { return }
          if (this.ratioCheck('保费占比', this.temp.insuranceRatio)) { return }
          if (this.ratioCheck('资产管理费占比', this.temp.managementRatio)) { return }
          if (this.ratioCheck('vip服务费占比', this.temp.vipServiceRatio)) { return }

          // 最大费率不能小于最小费率
          if (parseFloat(this.temp.maximumRate) < parseFloat(this.temp.minimumRate)) {
            this.$message(
              {
                showClose: true,
                message: '费率范围配置有误',
                type: 'error'
              }
            )
            return
          }
          // 风险保障金费率不能大于最小费率
          if (parseFloat(this.temp.riskEnsureRatio) > parseFloat(this.temp.minimumRate)) {
            this.$message(
              {
                showClose: true,
                message: '风险保障金费率不能大于最小费率',
                type: 'error'
              }
            )
            return
          }

          // 验证几个占比相加是不是等于100
          var realToalRatio = parseFloat(this.temp.insuranceRatio) + parseFloat(this.temp.managementRatio) + parseFloat(this.temp.vipServiceRatio)
          realToalRatio = Math.round(realToalRatio * 100) / 100
          if (realToalRatio !== 100) {
            this.$message(
              {
                showClose: true,
                message: '费用占比输入有误',
                type: 'error'
              }
            )
            return
          }

          saveData(this.temp).then(response => {
            this.$message({
              message: response.data.message,
              type: 'success'
            })
            this.dialogFormVisible = false
            this.getList()
          })
        }
      })
    },

    handleDelete(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.$confirm(`确认是否删除该条配置？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteData(this.temp).then(response => {
          this.$message({
            message: response.data.message,
            type: 'success'
          })
          this.getList()
        }).catch(err => { console.log(err) })
      })
    }

  }
}
</script>
