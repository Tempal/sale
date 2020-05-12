<template>
  <div>
    <a-select v-model="testData" style="width: 120px" >
      <a-select-option value="jack">Jack</a-select-option>
      <a-select-option value="lucy">Lucy</a-select-option>
      <a-select-option value="disabled" disabled>Disabled</a-select-option>
      <a-select-option value="Yiminghe">yiminghe</a-select-option>
    </a-select>
    <div>  我是备选的: {{ testData }}</div>
    <a-select  style="width: 120px" >
      <a-select-option v-for="(item,index) in options" :key="index" :value="item.value">{{ item.text }}</a-select-option>
    </a-select>
    <a-select mode="multiple" :defaultValue="multiData" @change="handleMultiChang" style="width: 120px" >
      <a-select-option value="jack">Jack</a-select-option>
      <a-select-option value="lucy">Lucy</a-select-option>
      <a-select-option value="disabled" disabled>Disabled</a-select-option>
      <a-select-option value="Yiminghe">yiminghe</a-select-option>
    </a-select>
    <div> 我是多选的结果 {{ multiData }}</div>
  </div>

</template>

<script>
    export default {
      name: 'select',
      data(){
        return {
          options:[],
          testData:"Yiminghe",
          multiData:["jack","lucy"]

        }
      },
      methods:{
          loadOptions(){
            this.options=[{
              text: "Jack",
              value:"Jack"
              },{
              text: "Lucy",
              value:"Luck"
            },{
              text: "Other",
              value:"Other"
            }
            ]
          },
        handleMultiChang(value){
            this.multiData=value
        }
      },
      created(){
        this.loadOptions()
      }
    }

</script>

<style scoped>

</style>