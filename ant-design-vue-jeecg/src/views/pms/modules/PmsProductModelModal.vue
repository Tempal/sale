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
          label="产品型号">
          <a-input placeholder="请输入产品型号" v-decorator="['modelName', validatorRules.modelName ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="市场价">
          <a-input-number v-decorator="[ 'originalPrice', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="销量">
          <a-input-number v-decorator="[ 'sale', {initialValue:'0'}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="库存">
          <a-input-number v-decorator="[ 'stock', {initialValue:'1'}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="库存预警值">
          <a-input-number v-decorator="[ 'lowStock', {initialValue:'1'}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="照片">
          <!--<a-input placeholder="请输入照片" v-decorator="['pic', {}]" />-->
          <j-image-upload  v-decorator="[ 'pic', ]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单位">
          <a-input placeholder="请输入单位" v-decorator="['unit', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="商品描述">
          <a-input placeholder="请输入商品描述" v-decorator="['description', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="关键词">
          <a-input placeholder="请输入关键词" v-decorator="['keywords', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="所属分类">
          <!--<a-select  v-decorator="[ 'productCategoryId', { required: true, message: '请选择所属分类' }]">-->
            <!--<a-select-option v-for="(item,key) in catValue" :key="key" :value="item.id">-->
              <!--{{ item.name }}-->
            <!--</a-select-option>-->
          <!--</a-select>-->
          <j-tree-select
            v-decorator="[ 'productCategoryId', { required: true, message: '请选择所属分类' }]"
            placeholder="请选择所属分类"
            dict="pms_product_category,name,id"
            pidField="parent_id">
          </j-tree-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="品牌名称">
          <a-select  v-decorator="[ 'brandName', { required: true, message: '请选择品牌名称' }]">
            <a-select-option v-for="(item,key) in brandValue" :key="key" :value="item.brandName">
              {{ item.brandName }}
            </a-select-option>
          </a-select>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JTreeSelect from '@/components/jeecg/JTreeSelect'
  import {getAction} from '@/api/manage'

  export default {
    name: "PmsProductModelModal",
    components: {
      JImageUpload,JTreeSelect
    },
    data () {
      return {
        title:"操作",
        visible: false,
        catValue : {},
        brandValue :{},
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
        modelName:{rules: [{ required: true, message: '请输入产品型号!' }]},
        },
        url: {
          add: "/pms/pmsProductModel/add",
          edit: "/pms/pmsProductModel/edit",
        },
      }
    },
    created () {
    },
    mounted(){
      getAction('/pms/pmsProductCategory/list').then((res)=>{
        if (res.success) {
          console.log("modal result is " + res.result);
          this.catValue = res.result.records;
        }
      });
      getAction('/pms/pmsProductBrand/queryAllBrand').then((res)=>{
        if (res.success) {
          this.brandValue = res.result;
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
          this.form.setFieldsValue(pick(this.model,'modelName','originalPrice','sale','stock','lowStock','pic','unit','description','keywords','productCategoryId','brandName'))
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
            if(!this.model.modelName){
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