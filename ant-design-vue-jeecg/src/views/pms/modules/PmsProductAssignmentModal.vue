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
        <a-form-item label="分配号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'id']" placeholder="请输入订单号" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'orderId', validatorRules.orderId]" placeholder="请输入订单号" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="物流号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'deliverId', validatorRules.deliverId]" placeholder="请输入物流号" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="地区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'areaId', validatorRules.areaId]" placeholder="请输入地区"></a-input>
        </a-form-item>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="商品信息" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="5">客户名</a-col>
                <a-col :span="5">性别</a-col>
                <a-col :span="5">身份证号码</a-col>
                <a-col :span="5">手机号</a-col>
                <a-col :span="4">操作</a-col>
              </a-row>

              <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in orderMainModel.jeecgOrderCustomerList" :key="index">
                <a-col :span="5">
                  <a-form-item>
                    <a-input placeholder="客户名" v-decorator="['jeecgOrderCustomerList['+index+'].name', {'initialValue':item.name,rules: [{ required: true, message: '请输入用户名!' }]}]" />
                  </a-form-item>
                </a-col>
                <a-col :span="5">
                  <a-form-item>
                    <a-select placeholder="性别" v-decorator="['jeecgOrderCustomerList['+index+'].sex', {'initialValue':item.sex}]">
                      <a-select-option value="1">男</a-select-option>
                      <a-select-option value="2">女</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="5">
                  <a-form-item>
                    <a-input placeholder="身份证号" v-decorator="['jeecgOrderCustomerList['+index+'].idcard', {'initialValue':item.idcard,rules: [{ pattern: '^\\d{6}(18|19|20)?\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|[xX])$', message: '身份证号格式不对!' }]}]"/>
                  </a-form-item>
                </a-col>
                <a-col :span="5">
                  <a-form-item>
                    <a-input placeholder="手机号" v-decorator="['jeecgOrderCustomerList['+index+'].telphone', {'initialValue':item.telphone,rules: [{ pattern: '^1(3|4|5|7|8)\\d{9}$', message: '手机号格式不对!' }]}]"/>
                  </a-form-item>
                </a-col>
                <a-col :span="4">
                  <a-form-item>
                    <a-button @click="addRowCustom" icon="plus"></a-button>&nbsp;
                    <a-button @click="delRowCustom(index)" icon="minus"></a-button>
                  </a-form-item>
                </a-col>
              </a-row>
            </div>
          </a-tab-pane>

        </a-tabs>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "PmsProductAssignmentModal",
    components: {
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
        orderId:{},
        deliverId:{},
        areaId:{},
        },
        url: {
          add: "/org.jeecg.modules.sale.pms/pmsProductAssignment/add",
          edit: "/org.jeecg.modules.sale.pms/pmsProductAssignment/edit",
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
          this.form.setFieldsValue(pick(this.model,'orderId','deliverId','areaId'))
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
        this.form.setFieldsValue(pick(row,'orderId','deliverId','areaId'))
      },


    }
  }
</script>