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
                     v-decorator="[ 'orderId', { required: true, message: '请输入订单号' }]" @change="pickArea">
                     <!--v-decorator="[ 'orderId', { required: true, message: '请输入订单号' }]" >-->

            <a-select-option v-for="(item,key) in allOrderId" :key="key" :value="item.orderId">
              {{ item.orderId }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="物流号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<a-input-number v-decorator="[ 'deliverId', validatorRules.deliverId]" placeholder="请输入物流号" style="width: 100%"/>-->
          <a-select  showSearch
                     optionFilterProp="children"
                     :filterOption="filterOption"
                     v-decorator="[ 'deliverId', validatorRules.deliverId, { required: true, message: '请输入物流号' }]">
            <a-select-option v-for="(item,key) in allDeliverId" :key="key" :value="item.trackingId">
              {{ item.trackingId }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="地区" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'areaId', validatorRules.areaId]" placeholder="请输入地区"></a-input>
          <!--<a-select  showSearch-->
                     <!--optionFilterProp="children"-->
                     <!--:filterOption="filterOption"-->
                     <!--v-decorator="[ 'areaId', validatorRules.areaId, { required: true, message: '请选择地区' }]">-->
            <!--<a-select-option v-for="(item,key) in allArea" :key="key" :value="item.area">-->
              <!--{{ item.area }}-->
            <!--</a-select-option>-->
          <!--</a-select>-->
        </a-form-item>

        <!-- 子表单区域 -->
        <a-tabs defaultActiveKey="1" >
          <a-tab-pane tab="商品信息" key="1">
            <div>
              <!--<a-row :gutter="24">-->
                <!--<a-col :span="12">-->
                  <!--<a-form-item label="选择角色">-->
                    <!--<j-select-multi-product v-model="proList"/>-->
                  <!--</a-form-item>-->
                <!--</a-col>-->
                <!--<a-col :span="12">选中值：{{ proList }}</a-col>-->
              <!--</a-row>-->

              <a-row type="flex" style="margin-bottom:10px" :gutter="16">
                <a-col :span="8">别名</a-col>
                <a-col :span="10">型号</a-col>
                <a-col :span="4">操作</a-col>
              </a-row>



              <a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in assignmentModel.pmsProductInfoList" :key="index">
                <a-col :span="8">
                  <a-form-item>
                    <!--<a-input placeholder="别名" v-decorator="['pmsProductInfoList['+index+'].nick_name', {'initialValue':item.name,rules: [{ required: true, message: '请输入别名!' }]}]" @change="pickModel($event,index)"/>-->

                    <a-select  showSearch
                               optionFilterProp="children"
                               :filterOption="filterOption"
                               v-model="assignmentModel.pmsProductInfoList[index].nickName"
                               @change="pickModel($event,index)">
                               <!--v-decorator="[ 'pmsProductInfoList['+index+'].nickName', { required: true, message: '请输入别名' }]" -->

                      <a-select-option v-for="(item,key) in allProductUnAssign" :key="key"
                                       :value="item.nickName">
                        {{ item.nickName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="10">
                  <a-form-item>
                    <!--<a-input   v-decorator="['pmsProductInfoList['+index+'].modelName']" />-->
                    <a-input   v-model="assignmentModel.pmsProductInfoList[index].modelName" :disabled="true"/>
                  </a-form-item>
                </a-col>
                <a-col :span="4">
                  <a-form-item>
                    <a-button @click="addRowProductInfo" icon="plus"></a-button>&nbsp;
                    <a-button @click="delRowProductInfo(index)" icon="minus"></a-button>
                  </a-form-item>
                </a-col>
              <!--</a-row><a-row type="flex" style="margin-bottom:10px" :gutter="16">-->
                <!--<a-col :span="5">别名</a-col>-->
                <!--<a-col :span="5">型号</a-col>-->
                <!--<a-col :span="4">操作</a-col>-->
              <!--</a-row>-->



              <!--<a-row type="flex" style="margin-bottom:10px" :gutter="16" v-for="(item, index) in assignmentModel.pmsProductInfoList" :key="index">-->
                <!--<a-col :span="5">-->
                  <!--<a-form-item>-->
                    <!--&lt;!&ndash;<a-input placeholder="别名" v-decorator="['pmsProductInfoList['+index+'].nick_name', {'initialValue':item.name,rules: [{ required: true, message: '请输入别名!' }]}]" @change="pickModel($event,index)"/>&ndash;&gt;-->

                    <!--<a-select  showSearch-->
                               <!--optionFilterProp="children"-->
                               <!--:filterOption="filterOption"-->
                               <!--v-decorator="[ 'pmsProductInfoList['+index+'].nickName', { required: true, message: '请输入别名' }]" @change="pickModel($event,index)">-->
                      <!--<a-select-option v-for="(item,key) in allProductUnAssign" :key="key"-->
                                       <!--:value="item.nickName">-->
                        <!--{{ item.nickName }}-->
                      <!--</a-select-option>-->
                    <!--</a-select>-->
                  <!--</a-form-item>-->
                <!--</a-col>-->
                <!--<a-col :span="5">-->
                  <!--<a-form-item>-->
                    <!--<a-input   v-decorator="['pmsProductInfoList['+index+'].modelName', {'initialValue':item.name,rules: [{ required: true, message: '请输入型号!' }]}]" />-->
                  <!--</a-form-item>-->
                <!--</a-col>-->
                <!--<a-col :span="4">-->
                  <!--<a-form-item>-->
                    <!--<a-button @click="addRowProductInfo" icon="plus"></a-button>&nbsp;-->
                    <!--<a-button @click="delRowProductInfo(index)" icon="minus"></a-button>-->
                  <!--</a-form-item>-->
                <!--</a-col>-->
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
  import JSelectMultiProduct from '@/components/jeecgbiz/JSelectProduct'

  export default {
    name: "PmsProductAssignmentModal",
    components: {
      JSelectMultiProduct
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        // assignmentModel: {pmsProductInfoList: [{}],proList:[]},
        assignmentModel: {pmsProductInfoList: [{"orderId":"","id":"","productSn":"","nickName":"","modelName":"","registerStatus":0,"publishStatus":0,"maintainTime":0,"purchaseTime":"","assignmentId":null,"createBy":null,"updateBy":null,"createTime":"","updateTime":null}]},
        allDeliverId : {},
        allOrderId : {},
        allArea: {},
        allProductUnAssign: {},
        bAdd: false,
        modelName: "海康二代",
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
          productInfoListId: "/pms/pmsProductInfo/queryByAssignmentId",
        }

      }
    },
    created () {
      this.init();

    },
    // computed:{
    //   getModelName:function (index) {
    //     if(this.modelName[index]==null){
    //       return "";
    //     }
    //     return this.modelName[index];
    //   }
    // },
    methods: {
      add () {
        this.bAdd=true;

        this.edit({});
      },
      edit (record) {
        this.init();
        this.form.resetFields();
        this.assignmentModel = Object.assign({}, record);
        // this.assignmentModel.proList=[];
        this.visible = true;
        if(this.bAdd==false){
          console.log("start get Action "+this.assignmentModel.id);
          let params = {assignmentId:this.assignmentModel.id}
          //初始化订单列表

          getAction(this.url.productInfoListId,params).then((res)=>{

            if(res.success){
              if(res.result.length == 0){
                this.assignmentModel.pmsProductInfoList= [{"orderId":"","id":"","productSn":"","nickName":"","modelName":"","registerStatus":0,"publishStatus":0,"maintainTime":0,"purchaseTime":"","assignmentId":null,"createBy":null,"updateBy":null,"createTime":"","updateTime":null}];
              }else{
                this.assignmentModel.pmsProductInfoList = res.result;
              }

              console.log(" res.result is "+res.result +"  pmsProductInfoList  is"+JSON.stringify(this.assignmentModel.pmsProductInfoList));

            }
            this.$forceUpdate()
          })
        }
        this.visible = true;

        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.assignmentModel,'id','orderId','deliverId','areaId','proList'))
          // debugger
          this.form.setFieldsValue(pick(this.assignmentModel.pmsProductInfoList,'orderId','id','productSn','nickName','modelName','registerStatus','publishStatus','maintainTime','purchaseTime','assignmentId','createBy','updateBy','createTime','updateTime'))
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
            console.log("before form, assignmentModel is "+JSON.stringify(this.assignmentModel));
            console.log("before form, value is "+JSON.stringify(values));
            // let formData = Object.assign(this.assignmentModel, values);
            let formData = this.assignmentModel;
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
        this.assignmentModel.pmsProductInfoList.push({});
        this.$forceUpdate();
      },
      delRowProductInfo (index) {
        console.log(index)
        this.assignmentModel.pmsProductInfoList.splice(index,1);
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
      pickArea:function (value) {
        const index = this.allOrderId.findIndex(x => x.orderId == value);
        // console.log("index is "+index+"  allOrderId is "+this.allOrderId[index].orderId);
        this.assignmentModel.orderId = this.allOrderId[index].orderId;
        this.assignmentModel.areaId = this.allOrderId[index].assignedArea;
        // this.form.setFieldsValue(pick(this.assignmentModel,'orderId','areaId'));
      },
      pickModel:function (event,index) {
        console.log("开始执行 "+event +" index is "+index);


        const indexRow = this.allProductUnAssign.findIndex(x => x.nickName == event);
        // if(this.ModelName.length<index+1){
        //   this.modelName.push(this.allProductUnAssign[indexRow].modelName);
        // }
        // else{
        //   this.modelName[index]=this.allProductUnAssign[indexRow].modelName;
        // }
        // this.modelName[index]=this.allProductUnAssign[indexRow].modelName;
        // this.modelName.push(this.allProductUnAssign[indexRow].modelName);
        this.modelName="海康一代";
        console.log("add modelname is "+this.modelName);
        this.$set(this.assignmentModel.pmsProductInfoList,index,this.allProductUnAssign[indexRow]);

        // this.assignmentModel.pmsProductInfoList[index].id= this.allProductUnAssign[indexRow].id;
        // this.assignmentModel.pmsProductInfoList[index].productSn= this.allProductUnAssign[indexRow].productSn;
        // this.assignmentModel.pmsProductInfoList[index].nickName= this.allProductUnAssign[indexRow].nickName;
        // this.assignmentModel.pmsProductInfoList[index].modelName= this.allProductUnAssign[indexRow].modelName;
        // this.assignmentModel.pmsProductInfoList[index].registerStatus= this.allProductUnAssign[indexRow].registerStatus;
        // this.assignmentModel.pmsProductInfoList[index].publishStatus= this.allProductUnAssign[indexRow].publishStatus;
        // this.assignmentModel.pmsProductInfoList[index].maintainTime= this.allProductUnAssign[indexRow].maintainTime;
        // this.assignmentModel.pmsProductInfoList[index].purchaseTime= this.allProductUnAssign[indexRow].purchaseTime;
        // this.assignmentModel.pmsProductInfoList[index].assignmentId= this.allProductUnAssign[indexRow].assignmentId;
        // this.assignmentModel.pmsProductInfoList[index].createBy= this.allProductUnAssign[indexRow].createBy;
        // this.assignmentModel.pmsProductInfoList[index].updateBy= this.allProductUnAssign[indexRow].updateBy;
        // this.assignmentModel.pmsProductInfoList[index].createTime= this.allProductUnAssign[indexRow].createTime;
        // this.assignmentModel.pmsProductInfoList[index].updateTime= this.allProductUnAssign[indexRow].updateTime;
        console.log(" pmsProductInfoList is"+JSON.stringify(this.assignmentModel.pmsProductInfoList));

        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.assignmentModel.pmsProductInfoList[indexRow],'orderId','id','productSn','nickName','modelName','registerStatus','publishStatus','maintainTime','purchaseTime','assignmentId','createBy','updateBy','createTime','updateTime'))
        });
        this.$set(this.assignmentModel.pmsProductInfoList,index,this.allProductUnAssign[indexRow]);
        this.assignmentModel.pmsProductInfoList.splice(index,1,this.allProductUnAssign[indexRow]);
          // this.form.setFieldsValue(pick(this.assignmentModel.pmsProductInfoList[index],'id','productSn','nickName',
          // 'modelName','registerStatus','publishStatus','maintainTime','purchaseTime','assignmentId',
          // 'createBy','updateBy','createTime','updateTime'));

        //'maintainTime','purchaseTime','assignmentId','createBy','updateBy','createTime','updateTime'

        // this.form.setFieldsValue(pick(this.assignmentModel.pmsProductInfoList[index],'id','productSn','nickName','modelName','registerStatus',
        //                           'publishStatus','maintainTime','purchaseTime','assignmentId','createBy','updateBy','createTime','updateTime'))
        // console.log("modelname is "+this.getModelName(index));
        // console.log("modelname is "+this.modelName);
        console.log("After pmsProductInfoList is"+JSON.stringify(this.assignmentModel.pmsProductInfoList));
        this.$forceUpdate();

      },
      // getModelName:function(index){
      //   console.log("modelname change is "+this.modelName);
      //   if(!this.modelName)
      //     return "";
      //
      //   return this.modelName[index];
      //
      // },
      init:function () {
        getAction(this.url.orderList).then((res)=>{
          if(res.success){
            this.allOrderId = res.result;
            // console.log(" allOrderId is "+JSON.stringify(res.result));

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
            this.allProductUnAssign = res.result.records;
            // console.log(" allProductUnAssign is "+JSON.stringify(res.result));
          }
        });
      }

    }
  }
</script>