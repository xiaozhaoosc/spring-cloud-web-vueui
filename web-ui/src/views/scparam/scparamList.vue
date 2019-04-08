<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="$t('参数编号')" v-model="listQuery.paramValue" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input :placeholder="$t('参数名称')" v-model="listQuery.paramName" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-select v-model="listQuery.statue" :placeholder="$t('状态')" clearable style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option label="有效" value="1">有效</el-option>
        <el-option label="无效" value="2">无效</el-option>
      </el-select>
      <el-button v-waves v-permission="['scparam.query']" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <el-button v-waves v-permission="['scparam.save']" class="filter-item" type="primary" icon="el-icon-add" @click="handleView()">{{ $t('table.add') }}</el-button>
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
      <el-table-column :label="$t('参数编号')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.paramValue }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('参数名称')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.paramName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('参数值1名称')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.paramNameOne }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('参数值2名称')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.paramNameTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('参数值3名称')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.paramNameThree }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('参数值4名称')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.paramNameFour }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('参数值5名称')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.paramNameFive }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.status')" width="110px" align="center">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.statue | stateFormatFilter() }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column v-if="showActions" :label="$t('table.actions')" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-permission="['scparam.save']" type="primary" size="mini" @click="handleView(scope.row)">{{ $t('table.edit') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.pageIndex" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>
    <el-dialog :title="$t('Edit')" :visible.sync="dialogFormVisible" width="950px">
      <el-form ref="dataForm" :inline="true" :model="temp" label-width="100px" style="margin-left:50px;">
        <el-form-item :label="$t('ID')" prop="sysParamId">
          <el-input v-model="temp.sysParamId" disabled style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数编号')" required prop="paramValue">
          <el-input v-model="temp.paramValue" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数名称')" required prop="paramName">
          <el-input v-model="temp.paramName" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数值1')" prop="paramValueOne">
          <el-input v-model="temp.paramValueOne" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数1名称')" prop="paramNameOne">
          <el-input v-model="temp.paramNameOne" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数值2')" prop="paramValueTwo">
          <el-input v-model="temp.paramValueTwo" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数2名称')" prop="paramNameTwo">
          <el-input v-model="temp.paramNameTwo" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数值3')" prop="paramValueThree">
          <el-input v-model="temp.paramValueThree" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数3名称')" prop="paramNameThree">
          <el-input v-model="temp.paramNameThree" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数值4')" prop="paramValueFour">
          <el-input v-model="temp.paramValueFour" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数4名称')" prop="paramNameFour">
          <el-input v-model="temp.paramNameFour" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数值5')" prop="paramValueFive">
          <el-input v-model="temp.paramValueFive" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('参数5名称')" prop="paramNameFive">
          <el-input v-model="temp.paramNameFive" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="$t('状态')" required prop="statue">
          <el-select v-model="temp.statue" class="filter-item" style="width:300px">
            <el-option label="有效" value= "1" >有效</el-option>
            <el-option label="无效" value= "2" >无效</el-option>
          </el-select>
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
import { fetchList, saveData } from '@/api/scparam'
import waves from '@/directive/waves' // 水波纹指令

const loanbankDictStateOptions = [
  { key: '1', display_name: '有效' },
  { key: '2', display_name: '无效' }
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
  name: 'ScparamList',
  directives: {
    waves
  },
  filters: {
    stateFormatFilter(statue) {
      return loanbankDictStateKeyValue[statue]
    },
    dicSmallCodeFormatFilter(dicSmallCode) {
      return dicSmallCodeKeyValue[dicSmallCode]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        pageIndex: 1,
        pageSize: 20
      },
      showActions: true,
      temp: {
        sysParamId: undefined,
        createTime: undefined,
        modifyTime: undefined
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
      }, () => {
        this.list = []
        this.total = 0
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
      if (this.temp.modifyTime !== undefined && row.modifyTime) {
        this.temp.modifyTime = null
      }
      this.dialogFormVisible = true
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
