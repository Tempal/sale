<template>
    <div id="myChart" :style="{width:'600px',height:'300px'}"></div>
</template>

<script>
    export default {
      name: 'echartdemo',
      data(){
        return {
          msg: 'EChart demo'
        }
      },
      mounted(){
        this.drawLine()
      },
      methods:{
        drawLine(){
          let myChart=this.$echarts.init(document.getElementById('myChart'))
          myChart.setOption({
            title:{ text: 'XXXX高铁商品统计' },
            tooltip: {},
            xAxis: {
              data: ['水果','酸奶','可乐','矿泉水','瓜子花生','扑克牌']
            },
            yAxis: {},
            series: [{
              name: '销售量',
              type: 'bar',
              data: [105,40,16,210,80]
            }]
          });
        }
      }
    }
</script>

<style scoped>

</style>