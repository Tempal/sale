<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <a-modal
    :title="title"
    :width="800"
    :destroyOnClose="true"
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
          label="编号">
          <a-input  placeholder="请输入编号" disabled v-decorator="['id', { initialValue: this.count } ]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="上级编号">
          <!--a-input placeholder="请输入上级编号" v-decorator="['parentId', {}]" /-->
          <a-select  v-decorator="[ 'parentId', { required: true, message: '请选择' }]">
            <a-select-option v-for="(item,key) in oldValue" :key="key" :value="item.id">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="名字">
          <a-input placeholder="请输入名字" v-decorator="['name', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="数量">
          <a-input-number v-decorator="[ 'productCount', { initialValue: 0 }]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单位">
          <a-input placeholder="请输入单位" v-decorator="['productUnit', {initialValue: '个'}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="描述">
          <a-input placeholder="请输入描述" v-decorator="['description', {}]" />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import {getAction} from '@/api/manage'

  export default {
    name: "PmsProductCategoryModal",
    data () {
      return {
        title:"操作",
        visible: false,
        count : '',
        oldValue: {},
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
          add: "/pms/pmsProductCategory/add",
          edit: "/pms/pmsProductCategory/edit",
        },
      }
    },
    created () {

    },mounted(){
      getAction('/pms/pmsProductCategory/list').then((res)=>{
        this.oldValue=res.result.records;
      });
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
          this.form.setFieldsValue(pick(this.model,'id','parentId','name','productCount','productUnit','description'))
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
        getAction('/pms/pmsProductCategory/queryCount').then((res)=>{
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