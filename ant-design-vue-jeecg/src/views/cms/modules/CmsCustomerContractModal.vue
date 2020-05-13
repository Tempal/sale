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
          label="合同流水">
          <a-input placeholder="请输入合同流水" v-decorator="['id', {initialValue: getSerial()} ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="合同编号">
          <a-input placeholder="请输入合同编号" v-decorator="['contractNo', validatorRules.contractNo ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="签订时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'signTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="有效时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'validTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="甲方">
          <a-select   showSearch
                      optionFilterProp="children"
                      :filterOption="filterOption"
                      v-decorator="[ 'partyA', { required: true }]">
            <a-select-option v-for="(item,key) in companyName" :key="key" :value="item.name">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="乙方">
          <a-select   showSearch
                      optionFilterProp="children"
                      :filterOption="filterOption"
                      v-decorator="[ 'partyB', { required: true }]">
            <a-select-option v-for="(item,key) in companyName" :key="key" :value="item.name">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="甲方联系人">
          <a-input placeholder="请输入甲方联系人" v-decorator="['partyAPeople', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="乙方联系人">
          <a-input placeholder="请输入乙方联系人" v-decorator="['partyAPhone', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="甲方联系电话">
          <a-input placeholder="请输入甲方联系电话" v-decorator="['partyBPeople', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="乙方联系电话">
          <a-input placeholder="请输入乙方联系电话" v-decorator="['partyBPhone', {}]" />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import { getAction } from '../../../api/manage'

  export default {
    name: "CmsCustomerContractModal",
    data () {
      return {
        title:"操作",
        companyName: {},
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
        contractNo:{rules: [{ required: true, message: '请输入合同编号!' }]},
        },
        url: {
          add: "/cms/cmsCustomerContract/add",
          edit: "/cms/cmsCustomerContract/edit",
        },
      }
    },
    created () {
      getAction('/cms/cmsCustomerCompany/queryAllCompany').then((res)=>{
        console.log("------------")
        console.log(res)
        this.companyName=res.result
        console.log(this.companyName)
      });
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
          this.form.setFieldsValue(pick(this.model,'contractNo','partyA','partyB','partyAPeople','partyBPeople'))
		  //时间格式化
          this.form.setFieldsValue({signTime:this.model.signTime?moment(this.model.signTime):null})
          this.form.setFieldsValue({validTime:this.model.validTime?moment(this.model.validTime):null})
        });

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
            //时间格式化
            formData.signTime = formData.signTime?formData.signTime.format('YYYY-MM-DD HH:mm:ss'):null;
            formData.validTime = formData.validTime?formData.validTime.format('YYYY-MM-DD HH:mm:ss'):null;

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