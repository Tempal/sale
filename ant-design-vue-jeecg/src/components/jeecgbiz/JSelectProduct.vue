<template>
  <j-select-biz-component
    :value="value"

    name="商品"
    displayKey="nickName"

    :returnKeys="returnKeys"
    :listUrl="url.list"
    :columns="columns"
    queryParamText="商品选择"

    v-on="$listeners"
    v-bind="$attrs"
  />
</template>

<script>
  import JSelectBizComponent from './JSelectBizComponent'

  export default {
    name: 'JSelectMultiProduct',
    components: { JSelectBizComponent },
    props: ['value'],
    data() {
      return {
        returnKeys: ['id', 'nickName'],
        url: { list: '/pms/pmsProductInfo/queryAssignmentIdNull' },
        columns: [
          { title: '商品别名', dataIndex: 'nickName', align: 'center', width: 120 },
          { title: '商品型号', dataIndex: 'modelName', align: 'center', width: 120 }
        ]
      }
    }
  }
</script>

<style lang="less" scoped></style>