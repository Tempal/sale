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
          label="地区编码">
          <a-input placeholder="请输入编码" v-decorator="['id', { initialValue: this.count } ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="地区名">
          <a-input placeholder="请输入地区名" v-decorator="['area', validatorRules.area ]" />
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
    name: "CmsCustomerAreaModal",
    data () {
      return {
        title:"操作",
        count : '',
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
        area:{rules: [{ required: true, message: '请输入地区名!' }]},
        },
        url: {
          add: "/cms/cmsCustomerArea/add",
          edit: "/cms/cmsCustomerArea/edit",
        },
      }
    },
    created () {
      this.getCount();
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
          this.form.setFieldsValue(pick(this.model,'id','area'))
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
      getCount:function () {
        var resR;
        getAction('/cms/cmsCustomerArea/queryCount').then((res)=>{
          resR=res.result;
          this.count=res.result;
          console.log("result is "+resR);
          return resR.result;
          // this.form.setFieldsValue({
          //      id: resR.result,
          // });
        });

      },


    }
  }
</script>

<style lang="less" scoped>

</style>