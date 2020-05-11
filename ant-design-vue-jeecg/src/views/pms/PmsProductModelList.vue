<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="产品型号">
              <a-input placeholder="请输入产品型号" v-model="queryParam.modelName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="市场价">
              <a-input placeholder="请输入市场价" v-model="queryParam.originalPrice"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="销量">
              <a-input placeholder="请输入销量" v-model="queryParam.sale"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="库存">
              <a-input placeholder="请输入库存" v-model="queryParam.stock"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="库存预警值">
              <a-input placeholder="请输入库存预警值" v-model="queryParam.lowStock"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('产品型号')">导出</a-button>
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

        <template slot="productCategoryIdChange" slot-scope="text, record, index">
          {{ getProductCat(record.productCategoryId) }}
        </template>
        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="getAvatarView(record.pic)" icon="logo"/>
          </div>
        </template>

        <template slot="cutless" slot-scope="text, record, index">
          <j-ellipsis :value="record.description" :length="30"/>
        </template>


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
    <pmsProductModel-modal ref="modalForm" @ok="modalFormOk"></pmsProductModel-modal>
  </a-card>
</template>

<script>
  import PmsProductModelModal from './modules/PmsProductModelModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {getAction,getFileAccessHttpUrl} from '@/api/manage'

  export default {
    name: "PmsProductModelList",
    mixins:[JeecgListMixin],
    components: {
      PmsProductModelModal
    },
    data () {
      return {
        description: '产品型号管理页面',
        catValue : {},
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
            title: '产品型号',
            align:"center",
            dataIndex: 'modelName'
           },
		   {
            title: '市场价',
            align:"center",
            dataIndex: 'originalPrice'
           },
		   {
            title: '销量',
            align:"center",
            dataIndex: 'sale'
           },
		   {
            title: '库存',
            align:"center",
            dataIndex: 'stock'
           },
		   {
            title: '库存预警值',
            align:"center",
            dataIndex: 'lowStock'
           },
		   {
            title: '照片',
            align:"center",
            dataIndex: 'pic',
         scopedSlots: {customRender: "avatarslot"}
           },
		   {
            title: '单位',
            align:"center",
            dataIndex: 'unit'
           },
		   {
            title: '商品描述',
            align:"center",
            dataIndex: 'description'
           },
		   {
            title: '关键词',
            align:"center",
            dataIndex: 'keywords'
           },
		   {
            title: '所属分类',
            align:"center",
            dataIndex: 'productCategoryId'
           },
		   {
            title: '品牌名称',
            align:"center",
            dataIndex: 'brandName'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/pms/pmsProductModel/list",
          delete: "/pms/pmsProductModel/delete",
          deleteBatch: "/pms/pmsProductModel/deleteBatch",
          exportXlsUrl: "pms/pmsProductModel/exportXls",
          importExcelUrl: "pms/pmsProductModel/importExcel",
       },
    }
  },
    created () {
    },
    mounted(){
      let temp=getAction('/pms/pmsProductCategory/list');
      console.log("temp is " + temp.result);
      getAction('/pms/pmsProductCategory/list').then((res)=>{
        if (res.success) {
          console.log("result is " + res.result);
          this.catValue = res.result.records;
          console.log("catValue is " + this.catValue);
        }
      });
    },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },

    methods: {
      getProductCat:function (productCategoryId) {
        var item;
        console.log("catValue is "+ this.catValue);
        for(item in this.catValue){
          if(item.id==productCategoryId)
            return item.name;
        }
      },
      getAvatarView: function (avatar) {
        return getFileAccessHttpUrl(avatar,this.url.imgerver,"http")
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>