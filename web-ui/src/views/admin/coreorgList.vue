<template>
  <div>
    <basic-container>
      <el-container>
        <el-header class="menu-header">
          <el-button-group>
            <el-button
              v-permission="['coreorg.save']"
              type="primary"
              icon="el-icon-plus"
              size="small"
              @click.native="handleAddOneLve">根目录</el-button>
            <el-button
              v-permission="['coreorg.save']"
              type="primary"
              icon="el-icon-plus"
              size="small"
              @click.native="handleAdd">新增</el-button>
            <el-button
              v-permission="['coreorg.save']"
              type="primary"
              icon="el-icon-edit"
              size="small"
              @click.native="handleEdit">编辑</el-button>
            <el-button
              v-permission="['coreorg.del']"
              type="primary"
              icon="el-icon-delete"
              size="small"
              @click.native="handleDel">删除</el-button>
          </el-button-group>
        </el-header>
      </el-container>
      <el-container>
        <el-aside width="300px">
          <el-tree
            :data="menuAll"
            :expand-on-click-node="false"
            node-key="id"
            highlight-current
            default-expand-all
            @node-click="handleNodeClick"/>
        </el-aside>
        <el-main>
          <el-form
            ref="dataForm"
            :model="form"
            label-width="80px">
            <el-form-item label="父节点ID" style="width:300px">
              <el-input
                v-model="parentForm.id"
                :disabled="true"/>
            </el-form-item>
            <el-form-item label="父节点" style="width:300px">
              <el-input
                v-model="parentForm.label"
                :disabled="true"/>
            </el-form-item>
            <el-form-item label="ID" style="width:300px">
              <el-input
                v-model="form.id"
                :disabled="true"/>
            </el-form-item>
            <el-form-item label="部门编号" prop="code" required style="width:300px">
              <el-input
                v-model="form.code"
                :disabled="formGrade"/>
            </el-form-item>
            <el-form-item label="部门名称" prop="label" required style="width:300px">
              <el-input
                v-model="form.label"
                :disabled="formGrade"/>
            </el-form-item>
            <el-form-item :label="$t('类型')" prop="type" required>
              <el-select v-model="form.type" class="filter-item" style="width:220px" >
                <el-option label="公司" value="1">公司</el-option>
                <el-option label="部门" value="2">部门</el-option>
                <el-option label="小组" value="3">小组</el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button
                v-permission="['coreorg.save']"
                v-if="formStatus=='add'"
                :disabled="formGrade"
                type="primary"
                @click="saveDataForm">新增</el-button>
              <el-button
                v-permission="['coreorg.save']"
                v-if="formStatus=='edit'"
                :disabled="formGrade"
                type="primary"
                @click="saveDataForm">修改</el-button>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </basic-container>
  </div>
</template>

<script>
import { fetchCoreorgList, saveData, delData } from '@/api/coreorg'
import { validatenull } from '@/utils/validate'
import { findParent } from '@/utils/util'
export default {
  name: 'CoreorgList',
  data() {
    return {
      form: {
        pId: undefined
      },
      obj: {},
      parentForm: {},
      formGrade: true,
      formStatus: '',
      menuAll: null
    }
  },
  created() {
    this.getList(null)
  },
  mounted() { },
  methods: {
    getList() {
      this.listLoading = true
      fetchCoreorgList(this.listQuery).then(response => {
        this.menuAll = response.data.data
        this.listLoading = false
      }, err => {
        console.log(err)
        this.list = []
      })
    },
    handleNodeClick(data, checked, indeterminate) {
      this.parentForm = Object.assign({}, findParent(this.menuAll, data.id))
      this.formGrade = true
      this.formStatus = ''
      this.obj = data
      this.form = data
      if (this.form.pId === 0) {
        this.parentForm.id = 0
        this.parentForm.label = '根目录'
      }
    },
    handleAddOneLve() {
      this.formGrade = false
      this.formStatus = 'add'
      this.form = {}
      this.form.pId = 0
      this.parentForm.id = 0
      this.parentForm.label = '根目录'
    },
    handleAdd() {
      this.formGrade = false
      this.formStatus = 'add'
      this.parentForm.id = this.form.id
      this.parentForm.label = this.form.label
      if (this.parentForm.id === undefined) {
        this.parentForm.id = 0
        this.parentForm.label = '根目录'
      }
      this.form = {}
      this.form.pId = this.parentForm.id
    },
    handleEdit() {
      if (validatenull(this.obj)) {
        this.$message({
          showClose: true,
          message: '请选择菜单',
          type: 'warning'
        })
        return false
      }
      this.form = Object.assign({}, this.obj)
      this.formStatus = 'edit'
      this.formGrade = false
    },
    handleDel() {
      this.$confirm(`是否确认删除序号为${this.form.label}`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delData(this.form.id).then(response => {
          this.$message({
            message: response.data.message,
            type: 'success'
          })
          this.getList()
        }).catch(err => { console.log(err) })
      })
    },
    saveDataForm() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.form.children = null
          saveData(this.form).then(response => {
            this.$message({
              message: response.data.message,
              type: 'success'
            })
            this.getList()
          }).catch(err => { console.log(err) })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.menu-container {
  padding: 0 20px;
}
.menu-header {
  padding: 8px 0;
}
</style>
