<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="快递号">
              <a-input placeholder="请输入快递号" v-model="queryParam.trackingId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="收货人id">
              <a-input placeholder="请输入收货人id" v-model="queryParam.receivePeopleId"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="发货人id">
              <a-input placeholder="请输入发货人id" v-model="queryParam.sendPeopleId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="发货时间">
              <a-input placeholder="请输入发货时间" v-model="queryParam.sendTime"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="是否收获，0还未收，1已经接收">
              <a-input placeholder="请输入是否收获，0还未收，1已经接收" v-model="queryParam.receiveStatus"></a-input>
            </a-form-item>
          </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('物流信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <pmsProductDeliver-modal ref="modalForm" @ok="modalFormOk"></pmsProductDeliver-modal>
  </a-card>
</template>

<script>
  import PmsProductDeliverModal from './modules/PmsProductDeliverModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "PmsProductDeliverList",
    mixins:[JeecgListMixin],
    components: {
      PmsProductDeliverModal
    },
    data () {
      return {
        description: '物流信息管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: '快递号',
            align:"center",
            dataIndex: 'trackingId'
           },
		   {
            title: '收货人id',
            align:"center",
            dataIndex: 'receivePeopleId'
           },
		   {
            title: '发货人id',
            align:"center",
            dataIndex: 'sendPeopleId'
           },
		   {
            title: '发货时间',
            align:"center",
            dataIndex: 'sendTime'
           },
		   {
            title: '是否收获，0还未收，1已经接收',
            align:"center",
            dataIndex: 'receiveStatus'
           },
		   {
            title: '收货佐证，网址，上传到阿里云或者minio',
            align:"center",
            dataIndex: 'certificate'
           },
		   {
            title: '公司名称',
            align:"center",
            dataIndex: 'companyName'
           },
		   {
            title: '详细地址',
            align:"center",
            dataIndex: 'detailAddress'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/pms/pmsProductDeliver/list",
          delete: "/pms/pmsProductDeliver/delete",
          deleteBatch: "/pms/pmsProductDeliver/deleteBatch",
          exportXlsUrl: "pms/pmsProductDeliver/exportXls",
          importExcelUrl: "pms/pmsProductDeliver/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>