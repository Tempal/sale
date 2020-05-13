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
          label="订单号">
          <a-input placeholder="请输入订单id" v-decorator="['orderId']" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="产品名">
          <!--<a-input placeholder="请输入产品名" v-decorator="['modelName', {}]" />-->
          <!--<a-select  showSearch-->
                     <!--optionFilterProp="children"-->
                     <!--:filterOption="filterOption"-->
                     <!--v-decorator="[ 'modelName', { required: true, message: '请选择产品型号' }]">-->
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption" v-decorator="[ 'modelName', { required: true, message: '请选择产品型号' }]">
            <a-select-option v-for="(item,key) in this.allModel" :key="key" :value="item.modelName">
              {{ item.modelName }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="已发货数">
          <a-input placeholder="请输入供应量，已发货物数" v-decorator="['orderSupply', {initialValue:'0'}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="需求量">
          <a-input-number placeholder="请输入需求量" v-decorator="['orderNumber', {initialValue:'1'}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="订单类型">
          <!--<a-input v-decorator="[ 'orderType', {}]" />-->
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption"
                     v-decorator="[ 'orderType', { required: true, message: '请选择订单类型' }]">
            <a-select-option v-for="(item,key) in this.allOrderType" :key="key" :value="item.orderTypeName">
              {{ item.orderTypeName }}
            </a-select-option>
          </a-select>

        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="地区">
          <!--<a-input-number v-decorator="[ 'assignedArea', {}]" />-->
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption"
                     v-decorator="[ 'assignedArea', { required: true, message: '请选择地市局' }]">
            <a-select-option v-for="(item,key) in this.allArea" :key="key" :value="item.area">
              {{ item.area }}
            </a-select-option>
          </a-select>

        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="合同编号">
          <!--<a-input placeholder="请输入contractId" v-decorator="['contractId', {}]" />-->
          <!--<a-input-number v-decorator="[ 'assignedArea', {}]" />-->
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption"
                     v-decorator="[ 'contractId', { required: true, message: '请选择合同编号' }]">
            <a-select-option v-for="(item,key) in this.allContractNo" :key="key" :value="item.contractNo">
              {{ item.contractNo }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="订单金额">
          <a-input-number v-decorator="[ 'money', {initialValue:'1'}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="下订单时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'orderTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="有效时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'avaliableTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="状态">
          <j-dict-select-tag v-decorator="[ 'validStatus', {initialValue:'1'}]" :triggerChange="true" dictCode="valid_status" type="radio"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "CmsCustomerOrderModal",
    data () {
      return {
        title:"操作",
        allArea: {},
        allModel: {},
        allOrderType: {},
        allContractNo: {},
        visible: false,
        bAdd: false,
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
        orderId:{rules: [{ required: true, message: '请输入订单id!' }]},
        },
        url: {
          add: "/cms/cmsCustomerOrder/add",
          edit: "/cms/cmsCustomerOrder/edit",
        },
      }
    },
    created () {
      getAction("/cms/cmsCustomerArea/queryAllArea").then((res)=>
      {
          console.log("area result is " + JSON.stringify(res.result));
          this.allArea = res.result;
          console.log(" allArea is "+JSON.stringify(res.result));
      });
      getAction("/pms/pmsProductModel/queryAllModel").then((res)=>
      {
        if (res.success) {
          console.log("model result is " + res.result);
          this.allModel = res.result;
        }
      });
      getAction("/cms/cmsCustomerOrderType/queryAllType").then((res)=>
      {
        if (res.success) {
          console.log("allOrderType result is " + res.result);
          this.allOrderType = res.result;
        }
      });
      getAction("/cms/cmsCustomerContract/queryAllNo").then((res)=>
      {
        if (res.success) {
          console.log("allContractNo result is " + res.result);
          this.allContractNo = res.result;
        }
      });

    },
    methods: {
      add () {
        this.bAdd=true;
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'orderId','modelName','orderSupply','orderNumber','orderType','assignedArea','contractId','money','validStatus'))
		  //时间格式化
          this.form.setFieldsValue({orderTime:this.model.orderTime?moment(this.model.orderTime):null})
          this.form.setFieldsValue({avaliableTime:this.model.avaliableTime?moment(this.model.avaliableTime):null})
        });
        if(this.model.orderId == null)
          this.model.orderId=this.getSerial();

      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.bAdd=false;
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
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            formData.orderTime = formData.orderTime?formData.orderTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.avaliableTime = formData.avaliableTime?formData.avaliableTime.format('YYYY-MM-DD HH:mm:ss'):null;

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
      filterOption(input, option) {
        return (
          option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
        );
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