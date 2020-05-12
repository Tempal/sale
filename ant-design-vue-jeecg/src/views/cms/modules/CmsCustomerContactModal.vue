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
          label="用户ID">
          <a-input placeholder="请输入用户ID" v-decorator="['id', {initialValue: getSerial()} ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="用户名">
          <a-input placeholder="请输入用户名" v-decorator="['username', validatorRules.username ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="公司名">
          <a-select   showSearch
                      optionFilterProp="children"
                      :filterOption="filterOption"
                      :value="value"
                      v-decorator="[ 'company', { required: true }]">
            <a-select-option v-for="(item,key) in companyName" :key="key" :value="item.name">
              {{ item.name }}
            </a-select-option>
          </a-select>

        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="昵称">
          <a-input placeholder="请输入昵称" v-decorator="['nickname', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="手机号码">
          <a-input placeholder="请输入手机号码" v-decorator="['phone', validatorRules.phone ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="性别">
          <j-dict-select-tag v-decorator="[ 'gender', {initialValue:'1'}]" 	:triggerChange="true" dictCode="sex" type="radio"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所在城市">
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption"
                     :value="value" v-decorator="[ 'city', { required: true, message: '请选择地市局' }]">
            <a-select-option v-for="(item,key) in allArea" :key="key" :value="item.area">
              {{ item.area }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="职业">
          <a-input placeholder="请输入职业" v-decorator="['job', {}]" />
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
    name: "CmsCustomerContactModal",
    data () {
      return {
        title:"操作",
        visible: false,
        allArea: {},
        companyName: {},
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
        username:{rules: [{ required: true, message: '请输入用户名!' }]},
        phone:{rules: [{ required: true, message: '请输入手机号码!' }]},
        },
        url: {
          add: "/cms/cmsCustomerContact/add",
          edit: "/cms/cmsCustomerContact/edit",
        },
      }
    },
    created () {
      getAction("/cms/cmsCustomerArea/queryAllArea").then((res)=>
      {
        if (res.success) {
          console.log("area result is " + res.result);
          this.allArea = res.result;
        }
      });
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
          this.form.setFieldsValue(pick(this.model,'id','username','company','nickname','phone','gender','city','job'))
		  //时间格式化
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