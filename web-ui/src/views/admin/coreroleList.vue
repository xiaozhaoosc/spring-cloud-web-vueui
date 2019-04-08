<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="'角色名称'" v-model="listQuery.name" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-button v-waves v-permission="['corerole.query']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <el-button v-waves v-permission="['corerole.save']" class="filter-item" type="primary" icon="el-icon-add" @click="handleView()">{{ $t('table.add') }}</el-button>
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
      <el-table-column :label="'角色名称'" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'角色编码'" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.code }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'创建时间'" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'角色类型'" width="110px" align="center">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.type | valueFormatFilter(roleDictStateKeyValue) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="showActions" :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-check"
            size="small"
            @click="handleGrade(scope.row)">功能权限</el-button>
          <el-button v-permission="['corerole.save']" type="primary" size="mini" @click="handleView(scope.row)">{{ $t('table.edit') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.pageIndex" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>
    <el-dialog :title="'Edit'" :visible.sync="dialogFormVisible" width="950px">
      <el-form ref="dataForm" :inline="true" :model="temp" label-width="100px" style="margin-left:50px;">
        <el-form-item :label="'ID'" prop="id">
          <el-input v-model="temp.id" disabled style="width:300px"/>
        </el-form-item>
        <el-form-item
          :label="'角色名称'"
          :rules="[
            { required: true, min:1,max: 15, trigger: 'blur' }
          ]"
          prop="name">
          <el-input v-model="temp.name" maxlength="15" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="'角色编号'" required prop="code">
          <el-input v-model="temp.code" maxlength="15" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="'角色类型'" required prop="type">
          <el-select v-model="temp.type" class="filter-item" style="width:300px">
            <el-option v-for="item in roleDictStateOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="saveDataForm()">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="grade.box"
      title="菜单"
      width="40%">
      <el-tree
        ref="tree"
        :data="functionAll"
        :default-checked-keys="grade.check"
        :default-expanded-keys="grade.check"
        :default-expand-all="false"
        show-checkbox
        node-key="id"/>
      <span
        slot="footer"
        class="dialog-footer">
        <el-button
          v-permission="['corerole.saveRoleFunction']"
          type="primary"
          @click="handleGradeUpdate">更新</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, saveData, saveRoleFunction, getLastChildNodeIds } from '@/api/corerole'
import { fetchFunctionList } from '@/api/corefunction'
import waves from '@/directive/waves' // 水波纹指令

const roleDictStateOptions = [
  { key: 'R0', display_name: '操作角色' },
  { key: 'R1', display_name: '工作流角色' }
]

const roleDictStateKeyValue = roleDictStateOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})

export default {
  name: 'CoreroleList',
  directives: {
    waves
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      functionAll: null,
      listQuery: {
        pageIndex: 1,
        pageSize: 20
      },
      grade: {
        box: false,
        check: []
      },
      filters: {
        valueFormatFilter(type) {
          return roleDictStateKeyValue[type]
        }
      },
      showActions: true,
      roleDictStateOptions: roleDictStateOptions,
      roleDictStateKeyValue: roleDictStateKeyValue,
      temp: {
        bankDictId: undefined,
        bankcode: undefined,
        bankimage: undefined,
        bankname: undefined,
        bankbg: undefined,
        bankprefix: undefined,
        statue: undefined,
        dicSmallCode: undefined,
        singleLimit: undefined,
        singleDayLimit: undefined,
        singleDayTimes: undefined
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
    handleView(row) {
      this.temp = Object.assign({}, row) // copy obj
      if (this.temp.createTime !== undefined && row.createTime) {
        this.temp.createTime = null
      }
      if (this.temp.updateTime !== undefined && row.updateTime) {
        this.temp.updateTime = null
      }
      this.dialogFormVisible = true
    },
    /**
     * @title 权限更新
     *
     **/
    handleGradeUpdate() {
      if (this.$refs.tree.getCheckedKeys().length !== 0) {
        var fIds = ''
        for (let i = 0; i < this.$refs.tree.getCheckedKeys().length; i++) {
          if (i === this.$refs.tree.getCheckedKeys().length - 1) {
            fIds += this.$refs.tree.getCheckedKeys()[i]
          } else {
            fIds += this.$refs.tree.getCheckedKeys()[i] + ','
          }
        }
        this.temp.functionIds = fIds
      } else {
        this.temp.functionIds = null
      }

      if (this.temp.createTime !== undefined) {
        this.temp.createTime = null
      }

      saveRoleFunction(this.temp).then(response => {
        this.$message({
          message: response.data.message,
          type: 'success'
        })
      })

      this.$refs.tree.setCheckedKeys([])
      // this.grade.check = []
      this.grade.box = false
    },
    /**
     * @title 打开权限
     */
    handleGrade(row) {
      this.temp = row

      fetchFunctionList().then(response => {
        this.functionAll = response.data.data
      })

      getLastChildNodeIds(row.id).then(response => {
        this.$refs.tree.setCheckedKeys(response.data.data)
      })

      this.grade.box = true
    },
    saveDataForm() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
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
    }
  }
}
</script>
