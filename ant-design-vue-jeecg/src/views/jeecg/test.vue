<template >
  <a-card :bordered="false" style="min-height: 300px">
    <a-button @click="handlClickGetValue">获取数据</a-button>
    <a-input placeholder="Basic usage" v-model="testData"/>
    <div>
      {{ testData }}
    </div>
    <a-input placeholder="Basic usage2" defaultValue="testData2" @change="handleChange"/>
    <div>
      {{ testData2 }}
    </div>
    <j-date v-model="datestr" placeholder="请选择日期"></j-date>
  <j-editable-table ref="table1"
                    :columns="columns"
                    :actionButton="true"
                    :rowSelection="true"
                    :dataSource="dataSource">
    <!-- 定义插槽 -->
    <!-- 这种定义插槽的写法是vue推荐的新版写法（https://cn.vuejs.org/v2/guide/components-slots.html#具名插槽），旧版已被废弃的写法不再支持 -->
    <!-- 若webstorm这样写报错，请看这篇文章：https://blog.csdn.net/lxq_9532/article/details/81870651 -->
    <!--<template v-slot:action="props">-->
      <!--<a @click="handleDelete(props)">删除</a>-->
    <!--</template>-->
  </j-editable-table>

  </a-card>

</template>
<script>
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import JDate from '../../components/jeecg/JDate'
  export default {
    components: { JDate, JEditableTable },
    data() {
      return {
        datestr:"",
        testData:"",
        testData2:"我是defaultvalue",
        columns: [
          // ...
          {
            title: '操作',
            key: 'action',
            width: '8%',
            type: FormTypes.slot, // 定义该列为 自定义插值列
            slotName: 'action' // slot 的名称，对应 v-slot 冒号后面和等号前面的内容
          },
          {
            title: '年龄',
            key: 'age',
            type: FormTypes.inputNumber, // 定义该列为 自定义插值列
            placeholder:'请输入年龄',
            validateRules: [
              {
                required: true, // 必填
                message: '${title}不能为空' // 提示的文本
              },
              ]
          },
          {
            title: '是否在校',
            key: 'isSchool',
            width: '120px',
            type: FormTypes.checkbox, // 定义该列为 自定义插值列
            defaultChecked:true
          },
          {
            title: '开学时间',
            key: 'date',
            type: FormTypes.datetime, // 定义该列为 自定义插值列
          },
          {
            title: '一寸照',
            key: 'upload',
            token:'true',
            action: window._CONFIG['domainURL']+'/sys/common/upload',
            type: FormTypes.upload, // 定义该列为 自定义插值列
            responseName: 'message'
          }
        ],
        dataSource:[]
      }
    },
    methods: {
      handlClickGetValue(){
        let value=this.$refs.table1.getValuesSync()
        console.log("获取到的值： ",value)
      },
      handleChange(e){
        this.testData2=e.target.value
      },
      /* a 标签的点击事件，删除当前选中的行 */
      handleDelete(props) {
        // 参数解释
        // props.index ：当前行的下标
        // props.text ：当前值，可能是defaultValue定义的值，也可能是从dataSource中取出的值
        // props.rowId ：当前选中行的id，如果是新增行则是临时id
        // props.column ：当前操作的列
        // props.getValue ：这是一个function，执行后可以获取当前行的所有值（禁止在template中使用）
        //                  例：const value = props.getValue()
        // props.target ：触发当前事件的实例，可直接调用该实例内的方法（禁止在template中使用）
        //                  例：target.add()

        // 使用实例：删除当前操作的行
        let { rowId, target } = props
        target.removeRows(rowId)
      }
    }
  }
</script>