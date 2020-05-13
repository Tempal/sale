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
          label="快递流水">
          <a-input placeholder="请输入快递流水" v-decorator="['id', {} ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="快递号">
          <a-input placeholder="请输入快递号" v-decorator="['trackingId', {} ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收货人">
          <a-select   showSearch
                      optionFilterProp="children"
                      :filterOption="filterOption"
                      placeholder="请输入收货人" v-model="receivePeopleName" @change="pickByPeople">
          <a-select-option v-for="(item,key) in allContact" :key="key" :value="item.id">
            {{ item.username }}
          </a-select-option>

          </a-select>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收货人电话">
          <a-input placeholder="请输入收货人电话" v-decorator="['receivePeoplePhone', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="公司名称">
          <a-input placeholder="请输入公司名称" v-decorator="['companyName', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="详细地址">
          <a-input placeholder="请输入详细地址" v-decorator="['detailAddress', {}]" />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发货人">
          <!--<a-input placeholder="请输入发货人" v-decorator="['sendPeopleId', {}]" />-->
          <a-select   showSearch
                      optionFilterProp="children"
                      :filterOption="filterOption"
                      placeholder="请输入发货人" v-model="sendPeopleName" @change="pickByPeopleSe">
            <a-select-option v-for="(item,key) in allContact" :key="key" :value="item.id">
              {{ item.username }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发货时间">
          <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'sendTime', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="是否收货">
          <j-dict-select-tag v-decorator="[ 'receiveStatus', {}]" :triggerChange="true" dictCode="finish_status" type="radio" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="收货佐证">
          <a-input placeholder="请输入收货佐证，网址，上传到阿里云或者minio" v-decorator="['certificate', {}]" />
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
    name: "PmsProductDeliverModal",
    data () {
      return {
        title:"操作",
        visible: false,
        bAdd: false,
        allContact: [],
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
        trackingId:{rules: [{ required: true, message: '请输入快递号!' }]},
        },
        url: {
          add: "/pms/pmsProductDeliver/add",
          edit: "/pms/pmsProductDeliver/edit",
          contactList: "/cms/cmsCustomerContact/queryAllContact",
        },
      }
    },
    created () {
      this.init();
    },
    methods: {
      init() {
        getAction(this.url.contactList).then((res)=>{
          if(res.success){
            this.allContact = res.result;
            console.log(JSON.stringify(this.allContact));
          }
        });
      },
      add () {
        this.bAdd=true;
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id','trackingId','receivePeopleId','receivePeopleName','receivePeoplePhone','sendPeopleId','sendPeopleName','receiveStatus','certificate','companyName','detailAddress'))
		  //时间格式化
          this.form.setFieldsValue({sendTime:this.model.sendTime?moment(this.model.sendTime):null})
        });
        if(this.model.id==null){
          this.model.id=this.getSerial();
        }

        if(this.model.receiveStatus==null){
          this.model.receiveStatus=0;
        }

      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.bAdd = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(this.bAdd == true){
              httpurl+=this.url.add;
              method = 'post';
              this.bAdd = false;
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            //时间格式化
            formData.sendTime = formData.sendTime?formData.sendTime.format('YYYY-MM-DD HH:mm:ss'):null;

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
      filterOption(input, option) {
        return (
          option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
        );
      },
      pickByPeople:function (value) {
          const index = this.allContact.findIndex(x => x.id == value);
          this.model.companyName = this.allContact[index].company;
          this.model.receivePeopleId = this.allContact[index].id;
          this.model.receivePeopleName = this.allContact[index].username;
          this.model.receivePeoplePhone = this.allContact[index].phone;
          console.log("receivePeoplePhone:"+this.model.receivePeoplePhone +" v:"+this.allContact[index].phone);
          this.model.detailAddress = this.allContact[index].address;
          this.form.setFieldsValue(pick(this.model,'companyName','receivePeopleId','receivePeopleName','receivePeoplePhone','detailAddress'));
      },
      pickByPeopleSe:function (value) {
        const index = this.allContact.findIndex(x => x.id == value);
        this.model.sendPeopleId = this.allContact[index].id;
        this.model.sendPeopleName = this.allContact[index].username;
        this.form.setFieldsValue(pick(this.model,'sendPeopleId','sendPeopleName'));
      }


    }
  }
</script>

<style lang="less" scoped>

</style>