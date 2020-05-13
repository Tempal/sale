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
          label="系统编号">
          <!--<a-input placeholder="请输入编号" v-decorator="['id', {initialValue: getSerial()}]" />-->
          <a-input placeholder="请输入编号" v-decorator="['id']" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="序列号">
          <a-input placeholder="请输入序列号" v-decorator="['productSn', validatorRules.productSn ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="别名">
          <a-input placeholder="请输入别名" v-decorator="['nickName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="型号">
          <a-select  v-decorator="[ 'modelName', { required: true, message: '请选择型号' ,initialValue: modelAllName[0].modelName }]">
            <a-select-option v-for="(item,key) in modelAllName" :key="key" :value="item.modelName">
              {{ item.modelName }}
            </a-select-option>

          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="注册状态">
          <!--<a-input-number v-decorator="[ 'registerStatus', {}]" />-->
          <j-dict-select-tag v-decorator="[ 'registerStatus', {initialValue:'1'}]" :triggerChange="true" dictCode="pms_camera_register" type="radio"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="上架状态">
          <!--<a-input-number v-decorator="[ 'publishStatus', {}]" />-->
          <j-dict-select-tag v-decorator="[ 'publishStatus', {initialValue:'1'}]" 	:triggerChange="true" dictCode="pms_camera_up" type="radio"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="维修次数">
          <a-input-number v-decorator="[ 'maintainTime', {initialValue:'0'}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="进货时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'purchaseTime', {}]" />
          <!--<a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' :defaultValue="defaultDate" v-model="model.purchaseTime" />-->
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="分配号">
          <a-input placeholder="请输入分配号" v-decorator="['assignmentId' ]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import {getAction} from '@/api/manage'

  export default {
    name: "PmsProductInfoModal",
    data () {
      return {
        title:"操作",
        bAdd: false,
        visible: false,
        defaultDate: new moment(),
        model: {},
        modelAllName: [{modelAllName:""}],
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
        productSn:{rules: [{ required: true, message: '请输入序列号!' }]},
        },
        url: {
          add: "/pms/pmsProductInfo/add",
          edit: "/pms/pmsProductInfo/edit",
        },
      }
    },
    created () {
      getAction('/pms/pmsProductModel/queryAllModel').then((res)=>{
          console.log("------------")
          console.log(res)
          this.modelAllName=res.result;
          console.log(res.result)
      });
      this.form.setFieldsValue({
            purchaseTime: this.defaultDate,
          });
    },
    methods: {
      add () {

        this.model.purchaseTime = new moment().format('YYYY-MM-DD HH:mm:ss').toString();
        this.bAdd=true;
        this.edit(this.model);
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.form.setFieldsValue(pick(this.model,'id', 'productSn','nickName','modelName','registerStatus','publishStatus','maintainTime'));
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id', 'productSn','nickName','modelName','registerStatus','publishStatus','maintainTime'))
		  //时间格式化
          this.form.setFieldsValue({purchaseTime:this.model.purchaseTime?moment(this.model.purchaseTime):null})
        });
        if(this.model.id == null){
          this.model.id = this.getSerial();
        }

      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.bAdd==false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(this.bAdd==true){
              httpurl+=this.url.add;
              method = 'post';
              this.bAdd==false;
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            //formData.purchaseTime = formData.purchaseTime?formData.purchaseTime.format('YYYY-MM-DD HH:mm:ss'):null;
            if(that.model.purchaseTime != null)
            {
              let t = that.model.purchaseTime.format("YYYY-MM-DD HH:mm:ss");
              formData.purchaseTime = t;
            }
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