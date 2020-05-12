<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="序列号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'productSn', validatorRules.productSn]" placeholder="请输入序列号"></a-input>
        </a-form-item>
        <a-form-item label="别名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nickName', validatorRules.nickName]" placeholder="请输入别名"></a-input>
        </a-form-item>
        <a-form-item label="型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'modelName', validatorRules.modelName]" placeholder="请输入型号"></a-input>
        </a-form-item>
        <a-form-item label="注册状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'registerStatus', validatorRules.registerStatus]" placeholder="请输入注册状态" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="上架状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'publishStatus', validatorRules.publishStatus]" placeholder="请输入上架状态" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="维修次数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'maintainTime', validatorRules.maintainTime]" placeholder="请输入维修次数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="进货时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择进货时间" v-decorator="[ 'purchaseTime', validatorRules.purchaseTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="分配id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'assignmentId', validatorRules.assignmentId]" placeholder="请输入分配id" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入创建人"></a-input>
        </a-form-item>
        <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入更新人"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建日期" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择更新日期" v-decorator="[ 'updateTime', validatorRules.updateTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'

  export default {
    name: "PmsProductInfoModal",
    components: {
      JDate,
    },
    props:{
      mainId:{
        type:String,
        required:false,
        default:''
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        productSn:{rules: [{ required: true, message: '请输入序列号!' }]},
        nickName:{},
        modelName:{},
        registerStatus:{},
        publishStatus:{},
        maintainTime:{},
        purchaseTime:{},
        assignmentId:{},
        createBy:{},
        updateBy:{},
        createTime:{},
        updateTime:{},
        },
        url: {
          add: "/org.jeecg.modules.sale.pms/pmsProductAssignment/addPmsProductInfo",
          edit: "/org.jeecg.modules.sale.pms/pmsProductAssignment/editPmsProductInfo",
        }

      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'productSn','nickName','modelName','registerStatus','publishStatus','maintainTime','purchaseTime','assignmentId','createBy','updateBy','createTime','updateTime'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            formData['assignmentId'] = this.mainId
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }

        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'productSn','nickName','modelName','registerStatus','publishStatus','maintainTime','purchaseTime','assignmentId','createBy','updateBy','createTime','updateTime'))
      },


    }
  }
</script>
