<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="流水号">
          <a-input placeholder="请输入流水号" v-decorator="['id', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发票号">
          <a-input placeholder="请输入发票号" v-decorator="['invoiceNumber', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="订单号">
          <a-input placeholder="请输入订单号" v-decorator="['orderId', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收付款金额">
          <a-input-number v-decorator="[ 'money', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否收到钱">
          <j-dict-select-tag v-decorator="[ 'finishStatus', {}]" :triggerChange="true" dictCode="finish_status" type="radio"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否开发票">
          <j-dict-select-tag v-decorator="[ 'billStatus', {}]" :triggerChange="true" dictCode="finish_status" type="radio"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "CmsCustomerReceiptPayModal",
    data () {
      return {
        title:"操作",
        bAdd: false,
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
        form: this.$form.createForm(this),
        validatorRules:{
        },
        url: {
          add: "/cms/cmsCustomerReceiptPay/add",
          edit: "/cms/cmsCustomerReceiptPay/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.bAdd=true;
        this.edit({});
      },
      edit (record) {
        console.log("this.bAdd value is "+this.bAdd);
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id','invoiceNumber','orderId','money','finishStatus','billStatus'))
		  //时间格式化
        });

        if(this.model.finishStatus == null){
          this.model.finishStatus=1;
        }
        if(this.model.billStatus == null){
          this.model.billStatus=1;
        }
        if(this.model.id ==null){
          this.model.id=this.getSerial();
        }

      },
      close () {
        this.$emit('close');
        this.visible = false;
        //还原
        this.bAdd = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(this.bAdd == true){
              httpurl+=this.url.add;
              method = 'post';
              this.bAdd = false;
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化

            console.log(formData)
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
      getSerial:function () {
        const now = new Date();
        let month = now.getMonth() + 1;
        let monthT=(month<10)?("0"+month.toString()):month.toString();
        let day = now.getDate();
        let dayT=(day<10)?("0"+day.toString()):day.toString();
        let hour = now.getHours();
        let hourT=(hour<10)?("0"+hour.toString()):hour.toString();
        let minutes = now.getMinutes();
        let minutesT=(minutes<10)?("0"+minutes.toString()):minutes.toString();
        let seconds = now.getSeconds();
        let secondsT=(seconds<10)?("0"+seconds.toString()):seconds.toString();
        return now.getFullYear().toString() + monthT + dayT + hourT + minutesT + secondsT + (Math.round(Math.random() * 89 + 100)).toString();

      },

    }
  }
</script>

<style lang="less" scoped>

</style>