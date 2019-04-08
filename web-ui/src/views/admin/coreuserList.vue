<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="$t('用户名')" v-model="listQuery.code" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-button v-waves v-permission="['coreuser.query']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <el-button v-waves v-permission="['coreuser.save']" class="filter-item" type="primary" icon="el-icon-add" @click="handleView()">{{ $t('table.add') }}</el-button>
      <el-checkbox v-model="showActions" class="filter-item" style="margin-left:15px;" @change="tableKey=tableKey+1">{{ $t('table.actions') }}</el-checkbox>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;">
      <el-table-column :label="$t('table.id')" align="center" width="60">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('用户名')" required align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.code }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('昵称')" required align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.nickName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('姓名')" required align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('手机号')" required align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('语言')" required align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.language }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('部门')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.coreOrgName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('角色')" width="120px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.coreRoleName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('创建时间')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.status')" required width="110px" align="center">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.state | stateFormatFilter() }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="showActions" :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['coreuser.save']" type="primary" size="mini" @click="handleView(scope.row)">{{ $t('table.edit') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.pageIndex" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>
    <el-dialog :title="$t('Edit')" :visible.sync="dialogFormVisible" width="950px">
      <el-form ref="dataForm" :inline="true" :model="temp" :rules="formRules" label-width="100px" style="margin-left:50px;">
        <el-form-item :label="$t('ID')" prop="id">
          <el-input v-model="temp.id" disabled style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('用户名')" required prop="code">
          <el-input v-model="temp.code" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('姓名')" required prop="name">
          <el-input v-model="temp.name" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('密码')" prop="password">
          <el-input v-model="temp.password" name="password" type="password" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('昵称')" required prop="nickName">
          <el-input v-model="temp.nickName" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('语言')" prop="language">
          <el-input v-model="temp.language" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('手机号')" required prop="phone">
          <el-input v-model="temp.phone" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('状态')" required prop="state">
          <el-select v-model="temp.state" class="filter-item" style="width:300px">
            <el-option label="启用" value= "1" >启用</el-option>
            <el-option label="停用" value= "0" >停用</el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('部门')" prop="coreOrgName">
          <el-input
            v-model="temp.coreOrgName"
            style="width:300px"
            @focus="handleCoreFunction"/>
        </el-form-item>
        <el-form-item :label="$t('角色')" prop="state">
          <el-select
            v-model="userRoles"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请选择角色">
            <el-option
              v-for="item in allRoles"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
        </el-select></el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button v-permission="['coreuser.save']" type="primary" @click="saveDataForm()">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="$t('编辑')" :visible.sync="dialogDeptVisible">
      <el-tree ref="deptTree" :data="treeDeptData" :props="defaultProps" class="filter-tree" check-strictly node-key="id" highlight-current default-expand-all @node-click="getNodeData"/>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, saveData } from '@/api/coreuser'
import { allList, userAllList } from '@/api/corerole'
import { fetchCoreorgList } from '@/api/coreorg'
import waves from '@/directive/waves' // 水波纹指令

const loanbankDictStateOptions = [
  { key: '1', display_name: '启用' },
  { key: '0', display_name: '停用' }
]

// arr to obj ,such as { CN : "China", US : "USA" }
const loanbankDictStateKeyValue = loanbankDictStateOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

const dicSmallCodeOptions = [
  { key: '1', display_name: '易宝' },
  { key: '2', display_name: '宝付' },
  { key: '6', display_name: '口袋' }
]

// arr to obj ,such as { CN : "China", US : "USA" }
const dicSmallCodeKeyValue = dicSmallCodeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'CoreuserList',
  directives: {
    waves
  },
  filters: {
    stateFormatFilter(state) {
      return loanbankDictStateKeyValue[state]
    },
    dicSmallCodeFormatFilter(dicSmallCode) {
      return dicSmallCodeKeyValue[dicSmallCode]
    }
  },
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === null || value === undefined || value.length < 6) {
        callback(new Error('密码不能少于6位'))
      } else {
        callback()
      }
    }
    return {
      allRoles: [],
      userRoles: [],
      dialogDeptVisible: false,
      treeDeptData: [],
      defaultProps: {
        children: 'children',
        id: 'id',
        label: 'label'
      },
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        pageIndex: 1,
        pageSize: 20
      },
      showActions: true,
      formRules: {
        // username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
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
        this.list = response.data.data
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.pageIndex = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.pageIndex = val
      this.getList()
    },
    handleCoreFunction() {
      fetchCoreorgList().then(response => {
        this.treeDeptData = response.data.data
        this.dialogDeptVisible = true
      }).catch(err => { console.log(err) })
    },
    getNodeData(data) {
      this.dialogDeptVisible = false
      this.temp.coreOrgName = data.label
      this.temp.orgId = data.id
    },
    handleView(row) {
      this.temp = Object.assign({}, row) // copy obj

      allList().then(response => {
        this.allRoles = response.data.data
      })

      if (this.temp.id !== undefined) {
        userAllList(this.temp).then(response => {
          this.userRoles = response.data.data
        })
      }

      if (this.temp.createTime !== undefined && row.createTime) {
        this.temp.createTime = null
      }
      if (this.temp.updateTime !== undefined && row.updateTime) {
        this.temp.updateTime = null
      }
      this.dialogFormVisible = true
    },
    saveDataForm() {
      this.$refs['dataForm'].validate((valid) => {
        if (this.temp.orgId === undefined || this.temp.orgId === null) {
          this.$message({
            message: '请选择部门',
            type: 'error'
          })
          valid = false
        }
        if (valid) {
          if (this.userRoles !== undefined && this.userRoles.length !== 0) {
            var fIds = ''
            for (let i = 0; i < this.userRoles.length; i++) {
              if (i === this.userRoles.length - 1) {
                fIds += this.userRoles[i]
              } else {
                fIds += this.userRoles[i] + ','
              }
            }
            this.temp.coreRoleIds = fIds
          } else {
            this.temp.coreRoleIds = null
          }

          saveData(this.temp).then(response => {
            this.$message({
              message: response.data.message,
              type: 'success'
            })
            this.getList()
            this.dialogFormVisible = false
          })
        }
      })
    }
  }
}
</script>
