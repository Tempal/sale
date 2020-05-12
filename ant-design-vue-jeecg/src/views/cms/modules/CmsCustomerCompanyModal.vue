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
          label="公司名">
          <a-input placeholder="请输入公司名" v-decorator="['name', validatorRules.name ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="市">
          <!--<a-select  showSearch-->
                     <!--optionFilterProp="children"-->
                     <!--:filterOption="filterOption"-->
                     <!--:value="value" v-decorator="[ 'city', { required: true, message: '请选择地市局' }]">-->
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption"
                     >
            <a-select-option v-for="(item,key) in allArea" :key="key" :value="item.area">
              {{ item.area }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="公司地址">
          <a-input placeholder="请输入公司地址" v-decorator="['detailAddress', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收货地址">
          <a-input placeholder="请输入收货地址" v-decorator="['receiveAddress', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="公司类型">
          <j-dict-select-tag v-decorator="[ 'companyType', {initialValue:'0'}]" :triggerChange="true" dictCode="companyType" type="radio"/>
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
    name: "CmsCustomerCompanyModal",
    data () {
      return {
        title:"操作",
        visible: false,
        allArea:{},
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
        name:{rules: [{ required: true, message: '请输入公司名!' }]},
        },
        url: {
          add: "/cms/cmsCustomerCompany/add",
          edit: "/cms/cmsCustomerCompany/edit",
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
          this.form.setFieldsValue(pick(this.model,'name','city','detailAddress','receiveAddress','companyType'))
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


    }
  }
</script>

<style lang="less" scoped>

</style>