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
          <!--<a-input-number v-decorator="[ 'orderId', validatorRules.orderId]" placeholder="请输入订单号" style="width: 100%"/>-->
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption"
                     v-decorator="[ 'orderId', validatorRules.orderId, { required: true, message: '请输入订单号' }]">
            <a-select-option v-for="(item,key) in allOrderId" :key="key" :value="item.orderId">
              {{ item.orderId }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="物流号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'deliverId', validatorRules.deliverId]" placeholder="请输入物流号" style="width: 100%"/>
          <!--<a-select  showSearch-->
                     <!--optionFilterProp="children"-->
                     <!--:filterOption="filterOption"-->
                     <!--v-decorator="[ 'deliverId', validatorRules.deliverId, { required: true, message: '请输入物流号' }]">-->
            <!--<a-select-option v-for="(item,key) in allDeliverId" :key="key" :value="item.trackingId">-->
              <!--{{ item.trackingId }}-->
            <!--</a-select-option>-->
          <!--</a-select>-->
        </a-form-item>
        <a-form-item label="地区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<a-input v-decorator="[ 'areaId', validatorRules.areaId]" placeholder="请输入地区"></a-input>-->
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption"
                     v-decorator="[ 'areaId', validatorRules.areaId, { required: true, message: '请选择地区' }]">
            <a-select-option v-for="(item,key) in allArea" :key="key" :value="item.area">
              {{ item.area }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="商品信息" key="1">
            <div>
              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="5">别名</a-col>
                <a-col :span="5">型号</a-col>
                <a-col :span="4">操作</a-col>
              </a-row>

              <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in assignmentModel.productInfoList" :key="index">
                <a-col :span="5">
                  <a-form-item>
                    <!--<a-input placeholder="别名" v-decorator="['productInfoList['+index+'].nick_name', {'initialValue':item.name,rules: [{ required: true, message: '请输入别名!' }]}]" />-->

                    <a-select  showSearch
                               optionFilterProp="children"
                               :filterOption="filterOption"
                               v-decorator="[ 'productInfoList['+index+'].nick_name', validatorRules.areaId, { required: true, message: '请输入别名' }]">
                      <a-select-option v-for="(item,key) in allProductUnAssign" :key="key" :value="item.nickName">
                        {{ item.nickName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="5">
                  <a-form-item>
                    <a-input placeholder="型号" v-decorator="['productInfoList['+index+'].model_name', {'initialValue':item.name,rules: [{ required: true, message: '请输入别名!' }]}]" />
                  </a-form-item>
                </a-col>
                <a-col :span="4">
                  <a-form-item>
                    <a-button @click="addRowProductInfo" icon="plus"></a-button>&nbsp;
                    <a-button @click="delRowProductInfo(index)" icon="minus"></a-button>
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

  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "PmsProductAssignmentModal",
    components: {
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        assignmentModel: {productInfoList: [{}]},
        allDeliverId : {},
        allOrderId : {},
        allArea: {},
        allProductUnAssign: {},
        width:800,
        visible: false,

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
          add: "/pms/pmsProductAssignment/add",
          edit: "/pms/pmsProductAssignment/edit",
          productInfoListNull: "/pms/pmsProductInfo/queryAssignmentIdNull",
          orderList: "/cms/cmsCustomerOrder/queryAllOrderId",
          deliverList: "/pms/pmsProductDeliver/queryAllDeliverId",
          areaList: "/cms/cmsCustomerArea/queryAllArea",
          productInfoListId: "/pms/pmsProductInfo/queryById",
        }

      }
    },
    created () {
      getAction(this.url.orderList).then((res)=>{
        if(res.success){
          this.allOrderId = res.result;
          console.log(" allOrderId is "+JSON.stringify(res.result));

        }
      });
      getAction(this.url.deliverList).then((res)=>{
        if(res.success){
          this.allDeliverId = res.result;

        }
      });
      getAction(this.url.areaList).then((res)=>{
        if(res.success){
          this.allArea = res.result;

        }
      });
      getAction(this.url.productInfoListNull).then((res)=>{
        if(res.success){
          this.allProductUnAssign = res.result;

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
        this.assignmentModel = Object.assign({}, record);
        this.assignmentModel.productInfoList= [{}];
        this.visible = true;
        if(this.bAdd==false){
          let params = {id:this.orderMainModel.id}
          //初始化订单机票列表
          getAction(this.url.productInfoListId,params).then((res)=>{
            if(res.success){
              this.orderMainModel.productInfoList = res.result;
              this.$forceUpdate()
            }
          })
        }
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.assignmentModel,'id','orderId','deliverId','areaId'))
        })
        if(this.assignmentModel.id == null){
          this.assignmentModel.id=this.getSerial();
        }

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
            if(this.bAdd==true){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.assignmentModel, values);
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
      filterOption(input, option) {
        return (
          option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
        );
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'orderId','deliverId','areaId'))
      },
      addRowProductInfo () {
        this.assignmentModel.productInfoList.push({});
        this.$forceUpdate();
      },
      delRowProductInfo (index) {
        console.log(index)
        this.assignmentModel.productInfoList.splice(index,1);
        this.$forceUpdate();
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