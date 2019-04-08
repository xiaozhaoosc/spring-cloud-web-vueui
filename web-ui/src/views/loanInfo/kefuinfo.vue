<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input :placeholder="$t('table.idCardNo')" v-model="listQuery.id_card_no" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-input :placeholder="$t('table.phone')" v-model="listQuery.phone" clearable style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter"/>
      <el-button v-waves v-model="listQuery.id" class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
    </div>
    <!--
    <div class="filter-container">
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="loanInfoDownload">{{ $t('table.export') }}</el-button>
    </div>
    -->

    <el-table
      v-loading="listLoading"
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
      <el-table-column :label="$t('table.platform')" width="80px" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.platform | statusFilter">{{ scope.row.platform | valueFormatFilter($store.state.financeVar.platformKeyValues) }}</el-tag>
        </template>
      </el-table-column>
      <!-- <el-table-column label="loan_id" align="center" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.applicationId')" align="center" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.application_id }}</span>
        </template>
      </el-table-column>
      -->
      <el-table-column :label="$t('table.fullname')" align="center" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.customer_name }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.idCardNo')" align="center" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.id_card_no }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.phone')" align="center" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.loanStatus')" width="120px" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | valueFormatFilter($store.state.financeVar.loanStatusKeyValues) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.amount')" align="center" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.amount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="贷种产品" width="120px" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.loan_type | statusFilter">{{ scope.row.loan_type | loanTypeOptionsFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.loanPeriod')" align="center" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.loan_period }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.loanStartDay')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.loan_start_day }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.loanEndDay')" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.loan_end_day }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="'实际还款时间'" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.settle_date | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" width="190" class-name="small-padding fixed-width">
        <!--<template slot-scope="scope">
          <router-link :to="'/loan-info/info/detail/'+scope.row.id" class="link-type">
            <el-button v-waves type="primary" size="mini">{{ $t('table.view') }}</el-button>
          </router-link>
        </template>-->
        <template slot-scope="scope">
          <el-button v-if="scope.row.loan_type==1 || scope.row.loan_type==3" type="primary" size="mini" @click="handleView(scope.row)">{{ $t('table.view') }}</el-button>
          <el-button v-if="scope.row.loan_type==2" type="primary" size="mini" @click="handleFenqiView(scope.row)">{{ $t('table.view') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination v-show="total>0" :current-page="listQuery.page" :page-sizes="[10,20,30,50]" :page-size="listQuery.limit" :total="total" background layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    </div>
    <el-dialog :title="'详情'" :visible.sync="dialogFormVisible" width="58%" top="5%">
      <el-tabs type="card">
        <el-tab-pane label="订单详情">
          <el-card shadow="always">
            <el-form v-if="loanInfoDetail.loanStagesDto !== null" ref="dataForm" :inline="true" :model="temp" label-width="100px" style="margin-left:50px;">
              <el-form-item :label="'订单类型'">
                <el-tag style="width:300px">
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.purchaseWay === 1">自费购买</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.purchaseWay === 2">贷款购买</span>
                </el-tag>
              </el-form-item>
              <el-form-item :label="'状态结果'">
                <el-tag style="width:300px">
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.state === 0">未付款，处理中</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.state === 1">付款成功</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.state === 2">付款失败</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.state === 3">已付款已过期</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.state === 4">已付款退款中</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.state === 5">已付款，已退款</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.state === 6">已付款，退款失败</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.state === 7">付款失败，重新发起</span>
                </el-tag>
              </el-form-item>
              <el-form-item :label="'支付渠道'">
                <el-tag style="width:300px">
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.channel === 1">银行卡</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.channel === 2">支付宝</span>
                  <span v-if="loanInfoDetail.loanPremium !== null && loanInfoDetail.loanPremium.channel === 3">微信</span>
                </el-tag>
              </el-form-item>
              <el-form-item :label="'支付金额'">
                <el-input v-model="loanInfoDetail.loanPremium.premium" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'创建时间'">
                <el-input v-model="loanInfoDetail.loanPremium.createTime" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'贷后利息'">
                <el-input v-model="loanInfoDetail.loanStagesDto.interest" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'服务费'">
                <el-input v-model="loanInfoDetail.loanStagesDto.serviceCharge" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'贷款保费'">
                <el-input v-model="loanInfoDetail.loanStagesDto.premium" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'贷款保费利息'">
                <el-input v-model="loanInfoDetail.loanStagesDto.premiumInterest" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'逾期费'">
                <el-input v-model="loanInfoDetail.loanStagesDto.overdueCharge" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'逾期天数'">
                <el-input v-model="loanInfoDetail.loanStagesDto.overdueDays" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'应还金额'">
                <el-input v-model="loanInfoDetail.loanStagesDto.amount" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'实际还款金额'">
                <el-input v-model="loanInfoDetail.loanStagesDto.realRepayAmount" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'贷款期数'">
                <el-input v-model="temp.loan_periods" style="width:300px"/>
              </el-form-item>

            </el-form>
          </el-card>

        </el-tab-pane>

        <el-tab-pane label="代付">
          <el-card :model="temp" shadow="always">
            <el-table
              v-loading="listLoading"
              :data="loanInfoDetail.buPayments"
              border
              fit
              highlight-current-row
              style="width: 100%;">

              <el-table-column label="交易金额" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.transMoney }}</span>
                </template>
              </el-table-column>
              <el-table-column :label="'业务类型'" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.businessType === '1'">代付</span>
                  <span v-if="scope.row.businessType === '2'">退款</span>
                </template>
              </el-table-column>
              <el-table-column :label="'支付渠道'" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.institutionType === '1'">乐视小贷</span>
                  <span v-if="scope.row.institutionType === '2'">易宝</span>
                  <span v-if="scope.row.institutionType === '3'">口袋</span>
                  <span v-if="scope.row.institutionType === '4'">星河小贷</span>
                  <span v-if="scope.row.institutionType === '5'">微金石</span>
                  <span v-if="scope.row.institutionType === '10'">山西小贷2</span>
                </template>
              </el-table-column>
              <el-table-column :label="'状态'" width="80px" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.state === 0">待处理</span>
                  <span v-if="scope.row.state === 1">成功</span>
                  <span v-if="scope.row.state === 2">失败</span>
                </template>
              </el-table-column>
              <el-table-column label="订单创建时间" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="手机号" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.phone }}</span>
                </template>
              </el-table-column>
              <el-table-column label="银行卡" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.accNo }}</span>
                </template>
              </el-table-column>
              <el-table-column label="备注" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.remark }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="代扣">
          <el-card :model="temp" shadow="always">
            <el-table
              v-loading="listLoading"
              :data="loanInfoDetail.withholds"
              border
              fit
              highlight-current-row
              style="width: 100%;">

              <el-table-column label="交易金额" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.payAmount }}</span>
                </template>
              </el-table-column>
              <el-table-column :label="'业务类型'" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.payType === '1'">用户主动还款</span>
                  <span v-if="scope.row.payType === '2'">系统自动全额扣款</span>
                  <span v-if="scope.row.payType === '3'">用户续期</span>
                  <span v-if="scope.row.payType === '4'">系统自动部分扣款</span>
                  <span v-if="scope.row.payType === '5'">客服后台扣款</span>
                  <span v-if="scope.row.payType === '6'">系统自动本金扣款</span>
                  <span v-if="scope.row.payType === '7'">用户购买VIP保费</span>
                  <span v-if="scope.row.payType === '8'">催收后台发起扣款</span>
                  <span v-if="scope.row.payType === '9'">快钱转代扣</span>
                </template>
              </el-table-column>
              <el-table-column :label="'支付渠道'" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.payPlatform === '1'">宝付</span>
                  <span v-if="scope.row.payPlatform === '2'">快钱</span>
                </template>
              </el-table-column>
              <el-table-column :label="'状态'" width="80px" align="center">
                <template slot-scope="scope">
                  <span v-if="scope.row.payStatus === 0">待处理</span>
                  <span v-if="scope.row.payStatus === 1">处理中</span>
                  <span v-if="scope.row.payStatus === 2">扣款完成</span>
                  <span v-if="scope.row.payStatus === 4">扣款失败</span>
                </template>
              </el-table-column>
              <el-table-column label="订单创建时间" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="手机号" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.payPhone }}</span>
                </template>
              </el-table-column>
              <el-table-column label="银行卡" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.payBankCard }}</span>
                </template>
              </el-table-column>
              <el-table-column label="备注" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.remark }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="续期">
          <el-card :model="temp" shadow="always">
            <el-form v-if="loanInfoDetail.loanOrderRenewal != null" ref="dataForm" :inline="true" :model="temp" label-width="100px" style="margin-left:50px;">
              <el-form-item :label="'续期次数'">
                <el-input v-model="loanInfoDetail.loanOrderRenewal.renewalTimes" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'续期总金额费'">
                <el-input v-model="loanInfoDetail.loanOrderRenewal.totalAmout" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'续期手续费'" >
                <el-input v-model="loanInfoDetail.loanOrderRenewal.renewalFee" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'保费'" >
                <el-input v-model="loanInfoDetail.loanOrderRenewal.safe" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'利息'" >
                <el-input v-model="loanInfoDetail.loanOrderRenewal.interest" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'剩余保费利息'" >
                <el-input v-model="loanInfoDetail.loanOrderRenewal.safeInterest" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'创建时间'" >
                <el-input v-model="loanInfoDetail.loanOrderRenewal.createTime" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'续期状态'">
                <el-tag style="width:300px">
                  <span v-if="loanInfoDetail.loanOrderRenewal.state === 0">待续期(银行卡)</span>
                  <span v-if="loanInfoDetail.loanOrderRenewal.state === 1">续期成功</span>
                  <span v-if="loanInfoDetail.loanOrderRenewal.state === 2">续期失败</span>
                  <span v-if="loanInfoDetail.loanOrderRenewal.state === 3">待续期(三方支付)</span>
                  <span v-if="loanInfoDetail.loanOrderRenewal.state === 4">续期完成</span>
                </el-tag>
              </el-form-item>
            </el-form>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="手动还款记录">
          <el-card :model="temp" shadow="always">
            <el-form v-if="loanInfoDetail.loanRepaymentLine != null" ref="dataForm" :inline="true" :model="temp" label-width="100px" style="margin-left:50px;">
              <el-form-item :label="'还款金额'">
                <el-input v-model="loanInfoDetail.loanRepaymentLine.reAmount" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'免还金额'">
                <el-input v-model="loanInfoDetail.loanRepaymentLine.mhAmount" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'外部订单号'">
                <el-input v-model="loanInfoDetail.loanRepaymentLine.outOrderId" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'还款方式'">
                <el-tag style="width:300px">
                  <span v-if="loanInfoDetail.loanRepaymentLine.payType === '1'">支付宝</span>
                  <span v-if="loanInfoDetail.loanRepaymentLine.payType === '6'">微信</span>
                  <span v-if="loanInfoDetail.loanRepaymentLine.payType === '8'">银行</span>
                </el-tag>
              </el-form-item>
              <el-form-item :label="'状态'">
                <el-tag style="width:300px">

                  <span v-if="loanInfoDetail.loanRepaymentLine.state === 1">成功</span>
                  <span v-if="loanInfoDetail.loanRepaymentLine.state === 2">失败</span>
                </el-tag>
              </el-form-item>
              <el-form-item :label="'还款日期'">
                <el-input v-model="loanInfoDetail.loanRepaymentLine.reDate" style="width:300px"/>
              </el-form-item>
              <el-form-item :label="'创建时间'" >
                <el-input v-model="loanInfoDetail.loanRepaymentLine.createTime" style="width:300px"/>
              </el-form-item>
            </el-form>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <!-- 分期客户详情查看 -->
    <el-dialog :visible.sync="fenqiDialogVisible" title="分期客户详情" width="85%" top="5%">
      <el-tag size="medium" >个人信息：</el-tag>
      <el-card :model="temp" shadow="always">
        <el-form ref="dataForm" :model="temp" :inline="true" style="margin-left:50px;">
          <el-form-item :label="'姓名'" prop="customer_name" >
            <el-input v-model="temp.customer_name" style="width:100px"/>
          </el-form-item>
          <el-form-item :label="'手机号'" prop="phone" >
            <el-input v-model="temp.phone" style="width:200px"/>
          </el-form-item>
          <el-form-item :label="'证件号码'" prop="id_card_no" >
            <el-input v-model="temp.id_card_no" style="width:250px"/>
          </el-form-item>
          <el-form-item label="UID" prop="customer_id" >
            <el-input v-model="temp.customer_id" style="width:300px"/>
          </el-form-item>
        </el-form>
      </el-card>

      <el-tag size="medium" >还款信息：</el-tag>
      <el-card shadow="always">
        <el-form ref="dataForm" :model="temp" :inline="true" label-width="100px" style="margin-left:20px;">
          <el-form-item :label="'进件渠道'" prop="platform" >
            <el-tag :type="temp.platform | statusFilter" style="width:200px">{{ temp.platform | valueFormatFilter($store.state.financeVar.platformKeyValues) }}</el-tag>
          </el-form-item>
          <el-form-item label="贷种产品" prop="loanType">
            <el-tag :type="temp.loan_type | statusFilter" style="width:200px">{{ temp.loan_type | loanTypeOptionsFilter }}</el-tag>
          </el-form-item>
          <el-form-item label="还款方式" prop="repayment_type">
            <el-input v-model="temp.repayment_type" style="width:200px"/>
          </el-form-item>
          <!-- <el-form-item :label="'贷款编号'" prop="id">
            <el-input v-model="temp.id" style="width:300px"/>
          </el-form-item> -->
          <el-form-item :label="'申请单号'" prop="application_id">
            <el-input v-model="temp.application_id" style="width:200px"/>
          </el-form-item>
          <el-form-item :label="'借款总额'" prop="amount" >
            <el-input v-model="temp.amount" style="width:200px"/>
          </el-form-item>

          <!-- <el-form-item :label="'状态'" prop="status" >
            <el-tag :type="temp.status | statusFilter" >{{ temp.status | valueFormatFilter($store.state.financeVar.loanStatusKeyValues) }}</el-tag>
          </el-form-item> -->
          <!-- <el-form-item :label="'贷款期限'" prop="loan_period" >
            <el-input v-model="temp.loan_period" style="width:300px"/>
          </el-form-item> -->
          <el-form-item :label="'贷款期数'" prop="loan_periods" >
            <el-input v-model="temp.loan_periods" style="width:200px"/>
          </el-form-item>
          <!-- <el-form-item :label="'放款日期'" prop="loan_date" >
            <el-input v-model="temp.loan_date" style="width:120px"/>
          </el-form-item> -->
          <el-form-item :label="'合同起始日'" prop="loan_start_day" >
            <el-input v-model="temp.loan_start_day" style="width:200px"/>
          </el-form-item>
          <el-form-item :label="'合同终止日'" prop="loan_end_day" >
            <el-input v-model="temp.loan_end_day" style="width:200px" />
          </el-form-item>

          <el-form-item :label="'应还本金'" prop="total_principal" >
            <el-input v-model="temp.total_principal" style="width:200px"/>
          </el-form-item>
          <el-form-item :label="'应还利息'" prop="total_interest" >
            <el-input v-model="temp.total_interest" style="width:200px"/>
          </el-form-item>
          <el-form-item :label="'应还服务费'" prop="total_service_charge" >
            <el-input v-model="temp.total_service_charge" style="width:200px"/>
          </el-form-item>
          <!-- <el-form-item :label="'当前逾期天数'" prop="total_overdue_days" >
            <el-input v-model="temp.total_overdue_days" style="width:100px"/>
          </el-form-item> -->
          <el-form-item :label="'逾期费'" prop="total_overdue_charge" >
            <el-input v-model="temp.total_overdue_charge" style="width:200px"/>
          </el-form-item>
        </el-form>
      </el-card>

      <el-tabs type="card">
        <el-tab-pane label="期供信息">
          <el-card :model="temp" shadow="always">
            <el-table
              v-loading="listLoading"
              :data="stageInfoList"
              border
              fit
              highlight-current-row
              style="width: 100%;">
              <!-- <el-table-column :label="$t('table.id')" align="center" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index + 1 }}</span>
                </template>
              </el-table-column> -->

              <el-table-column label="期数" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.period }}</span>
                </template>
              </el-table-column>
              <!-- <el-table-column label="本期开始日期" align="center" width="120">
                <template slot-scope="scope">
                  <span>{{ scope.row.begin_date }}</span>
                </template>
              </el-table-column> -->
              <el-table-column label="应还款日" align="center" width="120">
                <template slot-scope="scope">
                  <span>{{ scope.row.end_date }}</span>
                </template>
              </el-table-column>
              <el-table-column label="应还金额（元）" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.amount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="已还款日" align="center" width="120">
                <template slot-scope="scope">
                  <span>{{ scope.row.order_finish_time }}</span>
                </template>
              </el-table-column>
              <el-table-column label="已还金额（元）" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.real_repay_amount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="未还金额（元）" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.debtAmount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="逾期天数" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.overdue_days }}</span>
                </template>
              </el-table-column>
              <el-table-column label="逾期金额（元）" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.overdue_charge }}</span>
                </template>
              </el-table-column>
              <el-table-column label="还款状态" align="center">
                <template slot-scope="scope">
                  <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | paymentStatusFilter }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="结清日期" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.settle_time }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-tab-pane>

        <el-tab-pane label="交易明细">
          <el-card :model="temp" shadow="always">
            <div class="filter-container">
              <el-date-picker v-model="listQuery.trade_start_time" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" class="filter-item" placeholder="交易时间-开始"/>
              <el-date-picker v-model="listQuery.trade_end_time" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" class="filter-item" placeholder="交易时间-结束"/>

              <el-select v-model="listQuery.tradeType" placeholder="交易类型" clearable style="width: 140px" class="filter-item" @change="handleTradeInfoListFilter">
                <el-option v-for="item in tradeTypeOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
              </el-select>
              <el-button v-waves v-model="listQuery.id" class="filter-item" type="primary" icon="el-icon-search" @click="handleTradeInfoListFilter">{{ $t('table.search') }}</el-button>
            </div>

            <el-table
              v-loading="listLoading"
              :data="tradeInfoList"
              border
              fit
              highlight-current-row
              style="width: 100%;">
              <!-- <el-table-column :label="$t('table.id')" align="center" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index + 1 }}</span>
                </template>
              </el-table-column> -->

              <el-table-column label="交易完成时间" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.order_finish_time }}</span>
                </template>
              </el-table-column>
              <el-table-column label="业务类型" align="center" >
                <template slot-scope="scope">
                  <el-tag :type="scope.row.biz_type | statusFilter">{{ scope.row.biz_type | bizTypeFilter }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="交易类型" align="center" >
                <template slot-scope="scope">
                  <el-tag :type="scope.row.trade_type | statusFilter">{{ scope.row.trade_type | tradeTypeFilter }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="交易来源" align="center" >
                <template slot-scope="scope">
                  <el-tag :type="scope.row.trade_source | statusFilter">{{ scope.row.trade_source | tradeSourceFilter }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="金额" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.amount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="平账金额" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.use_amount }}</span>
                </template>
              </el-table-column>
              <!-- <el-table-column label="是否结清" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.settle_status }}</span>
                </template>
              </el-table-column> -->
              <el-table-column label="操作人" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.operator }}</span>
                </template>
              </el-table-column>
              <el-table-column label="备注" align="center" >
                <template slot-scope="scope">
                  <span>{{ scope.row.remark }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <el-dialog :title="'手动还款'" :visible.sync="dialogHandRepaymentVisible" width="550px">
      <el-form ref="dataFormRepayment" :inline="true" :model="repaymentLine" label-width="100px" style="margin-left:50px;">
        <el-form-item :label="'姓名'" prop="name">
          <el-input v-model="repaymentLine.name" disabled style="width:300px"/>
        </el-form-item>
        <el-form-item :label="'手机号'" prop="phone">
          <el-input v-model="repaymentLine.phone" disabled style="width:300px"/>
        </el-form-item>
        <el-form-item :label="'待还款'" prop="name">
          <el-input v-model="repaymentLine.amount" disabled style="width:300px"/>
        </el-form-item>
        <el-form-item
          :label="'还款金额'"
          :rules="[
            { pattern: /^\d+(\.\d{0,2})?$/, required: true, trigger: 'blur',message: '还款金额有误!' }
          ]"
          prop="reAmount">
          <el-input v-model="repaymentLine.reAmount" style="width:300px" @input="reAmountHandleFilter"/>
        </el-form-item>
        <el-form-item
          :label="'催收免还'"
          :rules="[
            { pattern: /^\d+(\.\d{0,2})?$/, required: true, trigger: 'blur',message: '免还金额有误!' }
          ]"
          prop="mhAmount">
          <el-input v-model="repaymentLine.mhAmount" style="width:300px"/>
        </el-form-item>
        <el-form-item
          :label="'支付订单号'"
          :rules="[
            { required: true, trigger: 'blur',message: '请输入支付订单号' }
          ]"
          prop="outOrderId">
          <el-input v-model="repaymentLine.outOrderId" style="width:300px"/>
        </el-form-item>
        <el-form-item :label="'还款方式'" prop="payType" required>
          <el-select v-model="repaymentLine.payType" class="filter-item" style="width:300px" >
            <el-option label="银行卡" value="8">银行卡</el-option>
            <el-option label="支付宝" value="1">支付宝</el-option>
            <el-option label="微信" value="6">微信</el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          :label="'还款日期'"
          :rules="[
            { required: true, trigger: 'blur',message: '请输入还款日期' }
          ]"
          prop="reDate">
          <el-date-picker v-model="repaymentLine.reDate" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" style="width:300px" class="filter-item" placeholder="还款日期"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogHandRepaymentVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="handRepayment">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchListLoanInfo, fetchStageInfo, fetchTradeInfo, loanInfoExport, getLoanInfoDetail, getRemainAmount, handRepayment } from '@/api/loanInfo'
import waves from '@/directive/waves'
import { parseTime } from '@/utils'
export default {
  name: 'LoanInfo',
  directives: {
    waves
  },
  filters: {
    // 期供信息-结清方式
    settleWayFilter(status) {
      const statusMap = {
        1: '正常还款',
        2: '提前还款',
        3: '续期'
      }
      return statusMap[status]
    },
    // 交易明细-业务类型
    bizTypeFilter(status) {
      const statusMap = {
        1: '用户主动还款',
        2: '系统自动全额扣款',
        4: '系统自动部分扣款',
        5: '客服后台扣款',
        6: '系统自动本金扣款',
        8: '催收后台发起扣款'
      }
      return statusMap[status]
    },
    // 交易明细-交易来源
    tradeSourceFilter(status) {
      const statusMap = {
        1: '支付宝流水',
        2: '财务系统',
        3: '催收',
        4: '宝付',
        5: '易宝',
        6: '微信流水'
      }
      return statusMap[status]
    },
    // 交易明细-交易类型
    tradeTypeFilter(status) {
      const statusMap = {
        1: '对公还款',
        2: '减免',
        3: '优惠券',
        4: '溢缴款',
        5: '还款'
      }
      return statusMap[status]
    },

    // 借款类型
    loanTypeOptionsFilter(status) {
      const statusMap = {
        1: '小贷',
        2: '分期',
        3: '小贷分期'
      }
      return statusMap[status]
    },
    // 分期表 状态
    paymentStatusFilter(status) {
      const statusMap = {
        1: '待还款',
        2: '已还款'
      }
      return statusMap[status]
    },
    statusFilter(status) {
      const statusMap = {
        0: '',
        1: 'warning',
        2: 'success',
        3: 'info',
        4: 'info',
        5: 'info',
        6: 'info',
        true: 'success',
        false: 'info'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      loanTypeOptions: [{ key: 1, display_name: '小贷' }, { key: 2, display_name: '分期' }, { key: 3, display_name: '小贷分期' }],
      tradeTypeOptions: [{ key: 1, display_name: '对公还款' }, { key: 2, display_name: '减免' }, { key: 3, display_name: '优惠券' }, { key: 4, display_name: '溢缴款' }, { key: 5, display_name: '还款' }],
      list: null,
      total: null,
      stageInfoList: null,
      stageInfoListTotal: null,
      tradeInfoList: null,
      tradeInfoListTotal: null,
      loanInfoDetail: {
        loanStagesDto: {
          principal: undefined,
          interest: undefined,
          serviceCharge: undefined,
          premium: undefined,
          premiumInterest: undefined,
          overdueCharge: undefined,
          overdueDays: undefined,
          amount: undefined,
          realRepayAmount: undefined,
          settleWay: undefined,
          status: undefined,
          beginDate: undefined,
          endDate: undefined
        },
        withholds: [],
        buPayments: [],
        loanPremium: {},
        loanOrderRenewal: {
        }
      },
      listLoading: true,
      listQuery: {
        customer_name: undefined,
        id_card_no: undefined,
        application_id: undefined,
        phone: undefined,
        platform: undefined,
        status: undefined,
        start_time: undefined,
        end_time: undefined,
        loanType: undefined,
        id: undefined,
        tradeType: undefined,
        trade_start_time: undefined,
        trade_end_time: undefined,
        page: 1,
        limit: 10
      },
      repaymentLine: {
        applicationId: undefined,
        name: undefined,
        phone: undefined,
        outOrderId: undefined,
        reAmount: 0.00,
        mhAmount: 0.00,
        reDate: undefined,
        payType: '8',
        amount: 0.00
      },
      temp: {
        platform: undefined,
        application_id: undefined,
        customer_name: undefined,
        id_card_no: undefined,
        phone: undefined,
        status: undefined,
        amount: undefined,
        loan_period: undefined,
        loan_start_day: undefined,
        loan_end_day: undefined,
        loanType: undefined,
        customer_id: undefined,
        repayment_type: '分期结清',
        total_principal: undefined,
        total_interest: undefined,
        total_service_charge: undefined,
        total_overdue_charge: undefined,
        total_overdue_days: undefined
      },
      dialogFormVisible: false,
      dialogHandRepaymentVisible: false,
      fenqiDialogVisible: false,
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchListLoanInfo(this.listQuery).then(response => {
        this.list = response.data.data.items
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    // 查询 期供信息
    getStageInfoList() {
      fetchStageInfo(this.listQuery).then(response => {
        this.stageInfoList = response.data.data.items
        // this.stageInfoListTotal = response.data.data.total
      }).catch(() => {
        this.stageInfoList = []
        this.stageInfoListTotal = 0
      })
    },
    // 查询 交易明细
    getTradeInfoList() {
      fetchTradeInfo(this.listQuery).then(response => {
        this.tradeInfoList = response.data.data.items
        // this.tradeInfoListTotal = response.data.data.total
      }).catch(() => {
        this.tradeInfoList = []
        this.tradeInfoListTotal = 0
      })
    },
    // 手动还款弹窗
    handRepaymentView(row) {
      this.repaymentLine.applicationId = ''
      this.repaymentLine.name = ''
      this.repaymentLine.phone = ''
      this.repaymentLine.outOrderId = ''
      this.repaymentLine.reAmount = null
      this.repaymentLine.mhAmount = null
      var d = new Date().toLocaleDateString()
      this.repaymentLine.reDate = parseTime(d)

      this.repaymentLine.applicationId = row.application_id
      this.repaymentLine.name = row.customer_name
      this.repaymentLine.phone = row.phone

      getRemainAmount(row.application_id).then(response => {
        this.repaymentLine.amount = response.data.data
      })

      this.dialogHandRepaymentVisible = true
    },
    // 手动还款
    reAmountHandleFilter() {
      if (this.repaymentLine.reAmount > this.repaymentLine.amount) {
        this.repaymentLine.reAmount = 0
      }
      this.repaymentLine.mhAmount = Number(this.repaymentLine.amount) - Number(this.repaymentLine.reAmount)
    },
    // 手动还款
    handRepayment(row) {
      this.$refs['dataFormRepayment'].validate((valid) => {
        if (this.repaymentLine.reAmount < 0) {
          this.$message({
            message: '还款金额金额有误!',
            type: 'error'
          })
          valid = false
        }

        if (valid) {
          this.$confirm(`是否确认还款订单号为${this.repaymentLine.applicationId}`, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            handRepayment(this.repaymentLine).then(response => {
              this.$message({
                message: response.data.message,
                type: 'success'
              })
              this.dialogHandRepaymentVisible = false
            })
          })
        }
      })
    },
    // 小贷查看
    handleView(row) {
      this.temp = null
      this.temp = Object.assign({}, row) // copy obj
      getLoanInfoDetail(row.application_id, row.id).then(response => {
        this.loanInfoDetail = response.data.data
      })
      this.dialogFormVisible = true
    },
    // 分期查看
    handleFenqiView(row) {
      this.temp = null
      this.temp = Object.assign({}, row) // copy obj

      this.temp.amount = this.temp.amount + '元'
      this.temp.total_principal = this.temp.total_principal + '元'
      this.temp.total_interest = this.temp.total_interest + '元'
      this.temp.total_service_charge = this.temp.total_service_charge + '元'
      this.temp.total_overdue_charge = this.temp.total_overdue_charge + '元'

      // if (row.amount) {
      //   this.temp.amount = this.temp.amount + '元'
      // }
      // if (row.total_principal) {
      //   this.temp.total_principal = this.temp.total_principal + '元'
      // }
      // if (row.total_interest) {
      //   this.temp.total_interest = this.temp.total_interest + '元'
      // }
      // if (row.total_service_charge) {
      //   this.temp.total_service_charge = this.temp.total_service_charge + '元'
      // }
      // if (row.total_overdue_charge) {
      //   this.temp.total_overdue_charge = this.temp.total_overdue_charge + '元'
      // }

      // if (row.loan_date) {
      //   this.temp.loan_date = parseTime(row.loan_date, '{y}-{m}-{d}')
      // }
      // if (row.loan_start_day) {
      //   this.temp.loan_start_day = parseTime(row.loan_start_day, '{y}-{m}-{d}')
      // }
      // if (row.loan_end_day) {
      //   this.temp.loan_end_day = parseTime(row.loan_end_day, '{y}-{m}-{d}')
      // }

      this.temp.repayment_type = '分期结清'
      this.fenqiDialogVisible = true

      this.listQuery.id = row.id
      // 期供信息
      this.getStageInfoList()
      // 交易明细
      this.getTradeInfoList()
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    // 交易明细-搜索
    handleTradeInfoListFilter() {
      this.listQuery.page = 1
      this.getTradeInfoList()
    },

    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    // 导出
    loanInfoDownload() {
      this.downloadLoading = true

      // if (this.listQuery.start_time === undefined || this.listQuery.end_time === undefined) {
      //   this.$message({
      //     message: '日期不能为空',
      //     type: 'error'
      //   })
      // } else {
      loanInfoExport(this.listQuery)
      // }
      this.downloadLoading = false
    }
  }
}
</script>
