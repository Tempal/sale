<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="品牌名">
              <a-input placeholder="请输入品牌名" v-model="queryParam.brandName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="首字母">
              <a-input placeholder="请输入首字母" v-model="queryParam.firstLetter"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="品牌制造商">
              <a-select v-model="queryParam.factoryStatus" placeholder="请输入品牌制造商">
                <a-select-option v-for="(item,key) in factoryStatusOption" :key="key" :value="item.value">
                  {{ item.text }}
                </a-select-option>
              </a-select>

            </a-form-item>
            <!--<a-form-item label="品牌制造商">-->
              <!--<a-input placeholder="请输入品牌制造商" v-model="queryParam.factoryStatus"></a-input>-->
            <!--</a-form-item>-->
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="产品数量">
              <a-input placeholder="请输入产品数量" v-model="queryParam.productCount"></a-input>
            </a-form-item>
          </a-col>
          <!--<a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--<a-form-item label="品牌logo">-->
              <!--<a-input placeholder="请输入品牌logo" v-model="queryParam.logo"></a-input>-->
            <!--</a-form-item>-->
          <!--</a-col>-->
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
      <a-button type="primary" icon="download" @click="handleExportXls('商品品牌')">导出</a-button>
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

        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="getAvatarView(record.logo)" icon="user"/>
          </div>
        </template>
        <template slot="factoryS" slot-scope="text, record, index">
          {{ getfactoryStatus(record.factoryStatus) }}
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
    <pmsProductBrand-modal ref="modalForm" @ok="modalFormOk"></pmsProductBrand-modal>
  </a-card>
</template>

<script>
  import PmsProductBrandModal from './modules/PmsProductBrandModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {getAction,getFileAccessHttpUrl} from '@/api/manage'
  import {ajaxGetDictItems} from '@/api/api'

  export default {
    name: "PmsProductBrandList",
    factoryStatusOption : {},
    mixins:[JeecgListMixin],
    components: {
      PmsProductBrandModal
    },
    data () {
      return {
        description: '商品品牌管理页面',
        // 表头
        columns: [

		   {
            title: '品牌名',
            align:"center",
            dataIndex: 'brandName'
           },
		   {
            title: '首字母',
            align:"center",
            dataIndex: 'firstLetter'
           },
          {
            title: '品牌制造商',
            align: "center",
            dataIndex: 'factoryStatus_dictText',
          },
		   {
            title: '产品数量',
            align:"center",
            dataIndex: 'productCount'
           },
          {
            title: '品牌logo',
            align:"center",
            dataIndex: 'logo',
            scopedSlots: {customRender: "avatarslot"}
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/pms/pmsProductBrand/list",
          delete: "/pms/pmsProductBrand/delete",
          deleteBatch: "/pms/pmsProductBrand/deleteBatch",
          exportXlsUrl: "pms/pmsProductBrand/exportXls",
          importExcelUrl: "pms/pmsProductBrand/importExcel",
       },
    }
  },
    created() {
      ajaxGetDictItems('factory_status', null).then((res) => {
        if (res.success) {
          this.factoryStatusOption=res.result;
          console.log("create getfactoryStatus :"+this.factoryStatusOption);
        }
      });
    },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
      getfactoryStatus:function  (factoryStatus) {
        var item;
        console.log("getfactoryStatus :"+this.factoryStatusOption);
        for(item in this.factoryStatusOption){
          if(item==factoryStatus)
            return this.factoryStatusOption[item].text;
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