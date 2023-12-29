<template>
  <div class="aspect-ratio-container">
  <div class="app-container"  id="ww"
    v-loading="fullscreenLoading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-size="300px"
  >
    <!--今日 昨日概况--->
    <div class="ho">
      <div style="border: 1px solid #eeeeee; height: 40px">
        <i id="i">今日概况</i>
        <i id="ii">昨日概况</i>
        <!--昨日概况--->
        <div
          style="
            width: 575px;
            border: 1px solid #fff;
            margin-top: 10px;
            display: inline-block;
          "
        >
          <ul id="jrgk">
            <li>
              单据数量<i>{{iywareData.documents==null?'0':iywareData.documents}}</i
              ><button class="rkan">入库</button>
            </li>
            <li>
              入库数量<i>{{iywareData.inNum==null?'0':iywareData.inNum}}</i>
            </li>
            <li>
              入库金额<i>{{iywareData.inPrice==null?'0.00':iywareData.inPrice}}</i>
            </li>
          </ul>
          <ul id="jrgk1">
            <li>
              单据数量<i>{{oywareData.documents==null?'0':oywareData.documents}}</i
              ><button class="ckan">出库</button>
            </li>
            <li>
              出库数量<i>{{oywareData.outNum==null?'0':oywareData.outNum}}</i>
            </li>
            <li>
              出库金额<i>{{oywareData.outPrice==null?'0.00':oywareData.outPrice}}</i>
            </li>
          </ul>
        </div>

        <!-- 今日概况--->
        <div
          style="
            width: 569px;
            border: 1px solid #fff;
            margin-top: 10px;
            float: left;
          "
        >
          <ul id="jrgk">
            <li>
              单据数量<i>{{itwareData.documents==null?'0':itwareData.documents}}</i
              ><button class="rkan">入库</button>
            </li>
            <li>
              入库数量<i>{{itwareData.inNum==null?'0':itwareData.inNum}}</i>
            </li>
            <li>
              入库金额<i>{{itwareData.inPrice==null?'0.00':itwareData.documents}}</i>
            </li>
          </ul>
          <ul id="jrgk1">
            <li>
              单据数量<i>{{otwareData.documents==null?'0':otwareData.documents}}</i
              ><button class="ckan">出库</button>
            </li>
            <li>
              出库数量<i>{{otwareData.outNum==null?'0':otwareData.outNum}}</i>
            </li>
            <li>
              出库金额<i>{{otwareData.outPrice==null?'0.00':otwareData.outPrice}}</i>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="hong">
      <!--库存占比--->
      <div class="ho1">
        <div style="border: 1px solid #eeeeee; width: 590px; height: 40px">
          <i id="i">库存数量占比</i>
          <div
            ref="bt"
            style="
              width: 500px;
              margin-left: 40px;
              margin-top: -35px;
              height: 450px;
            "
          ></div>
          <p
            style="
              position: relative;
              left: 272px;
              top: -239px;
              width: 44px;
              font-size: 13px;
              font-weight: bold;
            "
          >
            总数量 {{allQuantity}}
          </p>
        </div>
      </div>
      <!--库存金额占比--->
      <div class="ho2">
        <div style="border: 1px solid #eeeeee;height: 40px">
          <i id="i">库存金额占比</i>
          <div
            ref="bt1"
            style="
              width: 500px;
              margin-left: 40px;
              margin-top: -35px;
              height: 450px;
            "
          ></div>
          <p
            style="
              position: relative;
              left: 272px;
              top: -239px;
              width: 44px;
              font-size: 13px;
              font-weight: bold;
            "
          >
            总金额{{allPrice}}
          </p>
        </div>
      </div>
    </div>
    <!--入库统计--->
    <div id="rk">
      <div style="border: 1px solid #eeeeee; height: 40px">
        <i id="i">入库统计</i>
        <div
          ref="chart"
          style="
            width: 1100px;
            margin-left: -0px;
            margin-top: 10px;
            height: 350px;
          "
        ></div>
      </div>
    </div>
    <!--出库统计--->
    <div id="ck">
      <div style="border: 1px solid #eeeeee; height: 40px">
        <i id="i">出库统计</i>
        <div
          ref="chart1"
          style="
            width: 1100px;
            margin-left: -0px;
            margin-top: 10px;
            height: 340px;
          "
        ></div>
      </div>
    </div>
    <!--入库排名--->
    <div class="zb">
      <div style="border: 1px solid #eeeeee;  height: 40px">
        <i id="i">入库排名</i>
        <el-table
          ref="singleTable"
          :data="inRankeData"
          :header-cell-style="{
            'text-align': 'center',
            'font-weight': 'bold',
            color: '#000',
          }"
          :cell-style="{ 'text-align': 'center' }"
          style="width: 100%"
        >
          <el-table-column type="index" label="排名" width="110">
            <template scope="scope">
              <span
                class="index_common"
                v-bind:class="[
                  scope.$index + 1 == '1'
                    ? 'index_one'
                    : scope.$index + 1 == '2'
                    ? 'index_two'
                    : scope.$index + 1 == '3'
                    ? 'index_three'
                    : 'index_more',
                ]"
              >
                {{ scope.$index + 1 }}
              </span>
            </template>
          </el-table-column>
          <el-table-column property="g_name" label="货品名称" width="110">
          </el-table-column>
          <el-table-column property="inBoundQuantitys" label="数量" width="107">
          </el-table-column>
          <el-table-column label="金额" width="110">
            <template slot-scope="scope">
              <span>{{ formatMoney(scope.row.inBoundPuPrices) }}</span>
            </template>
          </el-table-column>
          <el-table-column property="inBoundTimes" label="入库次数" width="110">
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!--出库排名--->
    <div class="zb1">
      <div style="border: 1px solid #eeeeee;  height: 40px">
        <i id="i">出库排名</i>
        <el-table
          ref="singleTable"
          :data="outRankeData"
          :header-cell-style="{
            'text-align': 'center',
            'font-weight': 'bold',
            color: '#000',
          }"
          :cell-style="{ 'text-align': 'center' }"
          style="width: 100%"
        >
          <el-table-column type="index" label="排名" width="110">
            <template scope="scope">
              <span
                class="index_common"
                v-bind:class="[
                  scope.$index + 1 == '1'
                    ? 'index_one'
                    : scope.$index + 1 == '2'
                    ? 'index_two'
                    : scope.$index + 1 == '3'
                    ? 'index_three'
                    : 'index_more',
                ]"
              >
                {{ scope.$index + 1 }}
              </span>
            </template>
          </el-table-column>
          <el-table-column property="g_name" label="货品名称" width="110">
          </el-table-column>
          <el-table-column property="outBoundQuantitys" label="数量" width="107">
          </el-table-column>
          <el-table-column label="销售单价" width="110">
            <template slot-scope="scope">
              <span>{{ formatMoney(scope.row.outBoundPuPrices) }}</span>
            </template>
          </el-table-column>
          <el-table-column property="outBoundTimes" label="出库次数" width="110">
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-button
      title="顶部"
      v-if="showElement"
      icon="el-icon-top"
      id="db"
      @click="scollTop"
    ></el-button>
  </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import {
  ptStockNum,
  ptStockMoney,
  inbound,
  outbound,
  inRank,
  outRank,
  itwarehouse,
  outTwarehouse,
  iywarehouse, outYwarehouse
} from "@/api/home/home";
export default {
  data() {
    return {
      showElement: false,
      fullscreenLoading: false,
      //库存数量占比
      tpStockNumData:[],
      StockNumData:[],
      allQuantity:0,
      //库存金额占比
      tpStockPriceData:[],
      StockPriceData:[],
      allPrice:0,
      //入库统计
      inboundData:[],
      inbll_date: [],
      in_Num:[],
      in_Price:[],
      //出库统计
      outboundData:[],
      outbll_date: [],
      out_Num:[],
      out_Price:[],
      // 入库排名
      inRankeData: [],
      outRankeData: [],
     //今日概况入库
      itwareData:{},
      //出库
      otwareData:{},
      //昨日概况入库
      iywareData:{},
      //出库
      oywareData:{},
    };
  },
  methods: {
     //保留两位小数
    formatMoney(value) {
      return value.toFixed(2);
    },
    //回到顶部
    scollTop() {
      //  设置一个定时器
      const upRoll = setInterval(() => {
        const top = document.documentElement.scrollTop; // 每次获取页面被卷去的部分
        const speed = Math.ceil(top / 20); // 每次滚动多少 （步长值）
        if (document.documentElement.scrollTop > 0) {
          document.documentElement.scrollTop -= speed; // 不在顶部 每次滚动到的位置
        } else {
          clearInterval(upRoll); // 回到顶部清除定时器
        }
      }, 10);
    },
    handleScroll() {
      // 检查滚动位置，当达到某个条件时显示元素
      if (window.scrollY > 300) {
        this.showElement = true;
      }
      if (window.scrollY < 50) {
        this.showElement = false;
      }
    },
    //加载
    openFullScreen1() {
      this.fullscreenLoading = true;
      setTimeout(() => {
        this.fullscreenLoading = false;
      }, 1000);
    },
    //入库树状图
    inwarehouse() {
      inbound().then(response => {
        this.inboundData=response.data;
        if (this.inboundData!=null && this.inboundData.length>0){
          for (let i = 0; i < this.inboundData.length; i++) {
            this.inbll_date[i] = this.inboundData[i].date;
            this.in_Num[i] = this.inboundData[i].iNnum;
            this.in_Price[i] = this.inboundData[i].inPrice;
          }
        }
        const myChart = echarts.init(this.$refs.chart,null, { renderer: 'svg' }); // 实例化echarts对象
        //配置图表
        var option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            }
          },
          toolbox: {
            feature: {
              dataView: { show: true, readOnly: false },
              magicType: { show: true, type: ['line', 'bar'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          legend: {
            data: ['数量', '金额']
          },
          xAxis: [
            {
              type: 'category',
              data: this.inbll_date,
              axisPointer: {
                type: 'shadow'
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '数量',
              min: 0,
              max: 5000,
              interval: 500,
            },
            {
              type: 'value',
              name: '金额',
              min: 0,
              max: 500000,
              interval: 100000,
            }
          ],
          series: [
            {
              name: '数量',
              type: 'bar',
              tooltip: {
                valueFormatter: function (value) {
                  return value ;
                }
              },
              data: this.in_Num,
            },
            {
              name: '金额',
              type: 'line',
              yAxisIndex: 1,
              smooth: true, // 添加平滑曲线属性
              tooltip: {
                valueFormatter: function (value) {
                  return value;
                }
              },
              data: this.in_Price,
            }
          ]
        };
        myChart.setOption(option);
      });
    },
    //出库树状图
    outwareHouse() {
      outbound().then(response => {
        this.outboundData=response.data;
        if (this.outboundData!=null && this.outboundData.length>0){
          for (let i = 0; i < this.outboundData.length; i++) {
            this.outbll_date[i] = this.outboundData[i].date;
            this.out_Num[i] = this.outboundData[i].oNnum;
            this.out_Price[i] = this.outboundData[i].outPrice;
          }
        }
        const myChart = echarts.init(this.$refs.chart1,null, { renderer: 'svg' }); // 实例化echarts对象
        //配置图表
        var option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            }
          },
          toolbox: {
            feature: {
              dataView: { show: true, readOnly: false },
              magicType: { show: true, type: ['line', 'bar'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          legend: {
            data: ['数量', '金额']
          },
          xAxis: [
            {
              type: 'category',
              data: this.outbll_date,
              axisPointer: {
                type: 'shadow'
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '数量',
              min: 0,
              max: 2000,
              interval: 500,
            },
            {
              type: 'value',
              name: '金额',
              min: 0,
              max: 10000,
              interval: 1000,
            }
          ],
          series: [
            {
              name: '数量',
              type: 'bar',
              itemStyle: { // 添加itemStyle属性
                color: '#f7d893'
              },
              tooltip: {
                valueFormatter: function (value) {
                  return value ;
                }
              },
              data: this.out_Num,
            },
            {
              name: '金额',
              type: 'line',
              yAxisIndex: 1,
              smooth: true, // 添加平滑曲线属性
              itemStyle: { // 添加itemStyle属性
                color: '#91cc75'
              },
              tooltip: {
                valueFormatter: function (value) {
                  return value;
                }
              },
              data: this.out_Price,
            }
          ]
        };
        myChart.setOption(option);
      });
    },
    //库存数量占比
    PercentageStockNum(){
      ptStockNum().then(response => {
        this.tpStockNumData = response.data;
        // console.info(this.tpStockNumData);
        this.allQuantity=this.tpStockNumData[0].allQuantity;
        // 遍历接收到的数据并转换为所需的格式
          this.tpStockNumData.forEach((item) => {
            this.StockNumData.push({
              value: (item.ratio * 100).toFixed(2),
              name: item.w_name,
              label: {
                formatter: '{b}\n{c}%'
              }
            });
          });
      const myChart = echarts.init(this.$refs.bt, null, { renderer: 'svg' }); // 实例化echarts对象
      var option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '10%',
          left: 'center'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '60%'],
            avoidLabelOverlap: true,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'outside'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 20,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.StockNumData,
          }
        ]
      };
      myChart.setOption(option);
      });
    },
    //库存金额占比
    PercentageStockGoodsMoney(){
      ptStockMoney().then(response => {
        this.tpStockPriceData = response.data;
        // console.info(this.tpStockPriceData);
        this.allPrice=this.tpStockPriceData[0].allPrice.toFixed(2);
        // 遍历接收到的数据并转换为所需的格式
        this.tpStockPriceData.forEach((item) => {
          this.StockPriceData.push({
            value: (item.ratio * 100).toFixed(2),
            name: item.w_name,
            label: {
              formatter: '{b}\n{c}%'
            }
          });
        });
      const myChart = echarts.init(this.$refs.bt1,null, { renderer: 'svg' }); // 实例化echarts对象
      var option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '10%',
          left: 'center'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '60%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'outside'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 20,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: this.StockPriceData,
            color: ['#ff5f10', '#91f4e7', '#FF9800'],

          }
        ]
      };
        myChart.setOption(option);
      });
    },
    // 入库排名
    inwarehouseRank() {
      inRank().then(response => {
          this.inRankeData=response.data;
      })
    },
    //出库排名
    outwarehouseRank() {
      outRank().then(response => {
        this.outRankeData=response.data;
      })
    },
    //今日概况入库
    inTodayWarehouse(){
      itwarehouse().then(response => {
       const tpPrice=response.data.inPrice;
        response.data.inPrice = tpPrice.toFixed(2).replace(/\d{1,3}(?=(\d{3})+(?:\.))/g, '$&,');
        this.itwareData=response.data;
      })
    },
  //   出库
    outTodayWarehouse(){
      outTwarehouse().then(response => {
        const tpPrice=response.data.outPrice;
        response.data.outPrice = tpPrice.toFixed(2).replace(/\d{1,3}(?=(\d{3})+(?:\.))/g, '$&,');
        this.otwareData=response.data;
      })
    },

    //昨日概况入库
    inYesterDayWarehouse(){
      iywarehouse().then(response => {
        const tpPrice=response.data.inPrice;
        response.data.inPrice = tpPrice?.toFixed(2)?.replace(/\d{1,3}(?=(\d{3})+(?:\.))/g, '$&,');
        this.iywareData=response.data;
      })
    },
    //   出库
    outYesterDayWarehouse(){
      outYwarehouse().then(response => {
        const tpPrice=response.data.outPrice;
        response.data.outPrice = tpPrice?.toFixed(2)?.replace(/\d{1,3}(?=(\d{3})+(?:\.))/g, '$&,');
        this.oywareData=response.data;
      })
    }
  },
  mounted() {
    this.openFullScreen1();
    window.addEventListener("scroll", this.handleScroll);
    this.inwarehouse();
    this.outwareHouse();
    this.PercentageStockNum();
    this.PercentageStockGoodsMoney();
    this.inwarehouseRank();
    this.outwarehouseRank();
    this.inTodayWarehouse();
    this.outTodayWarehouse();
    this.inYesterDayWarehouse();
    this.outYesterDayWarehouse();
  },
};
</script>

<style scoped>
.index_common {
  width: 20px;
  height: 20px;
  display: inline-block;
  border-radius: 4em;
  color: #ffffff;
}
.index_one {
  background: red;
}
.index_two {
  background: #e6a23c;
}
.index_three {
  background: #409eff;
}
.index_more {
  background: #c8c9cc;
}
.rkan {
  float: right;
  margin-right: 20px;
  margin-top: 10px;
  width: 41px;
  height: 26px;
  line-height: 26px;
  border: 1px solid #5fcb71;
  color: #5fcb71;
  border-radius: 2px;
  background-color: #effaf1;
}
.ckan {
  float: right;
  margin-right: 20px;
  margin-top: 10px;
  width: 41px;
  height: 26px;
  line-height: 26px;
  border: 1px solid #f3b23e;
  color: #f3b23e;
  border-radius: 2px;
  background-color: #fdf3e2;
}
#jrgk {
  height: 122px;
  width: 260px;
  line-height: 40px;
  border-right: 1px solid #eeeeee;
  margin-top: 20px;
  margin-left: 20px;
  font-size: 13px;
  color: #999999;
  float: left;
}
#jrgk1 {
  margin-left: 300px;
  height: 122px;
  line-height: 40px;
  border-right: 1px solid #eeeeee;
  margin-top: 20px;
  font-size: 13px;
  color: #999999;
}
#jrgk i {
  font-size: 13px;
  color: #000;
  margin-left: 20px;
  font-weight: bold;
}
#jrgk1 i {
  font-size: 13px;
  color: #000;
  margin-left: 20px;
  font-weight: bold;
}
.zb {
  width: 590px;
  height: 575px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 2px 0 10px 3px #dcdde2;
  float: left;
  margin-top: 140px;
  margin-bottom: 160px;
}
.zb1 {
  margin-left: 42px;
  width: 590px;
  height: 575px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 2px 0 10px 3px #dcdde2;
  float: left;
  margin-top: 140px;
  margin-left: 200PX;
  margin-bottom: 160px;
}
#ww .el-loading-spinner {
  top: 20%;
  margin-top: -21px;
  width: 100%;
  text-align: center;
  position: relative;
  font-size: 50px;
}
#ww .el-loading-spinner .el-loading-text {
  color: #409eff;
  margin: 3px 0;
  font-size: 15px;
}
#ww .el-loading-mask {
  position: absolute;
  z-index: 2000;
  background-color: rgba(255, 255, 255, 0.9);
  margin: 0;
  top: 0;
  right: -8px;
  bottom: 0;
  left: 0;
  transition: opacity 0.3s;
}
#rk {
  width: 1220px;
  height: 380px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 2px 0 10px 3px #dcdde2;
  margin-top: 470px;
}
#ck {
  width: 1220px;
  height: 380px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 2px 0 10px 3px #dcdde2;
  margin-top: 140px;
}
#i {
  line-height: 40px;
  font-size: 13px;
  font-weight: bold;
  margin-left: 20px;
}
#ii {
  line-height: 40px;
  font-size: 13px;
  font-weight: bold;
  margin-left: 520px;
}
#db {
  position: fixed;
  right: 20px;
  bottom: 10px;
  width: 40px;
  height: 40px;
  padding: 0 !important;
  border-radius: 30px !important;
}
.ho {
  width: 1220px;
  height: 250px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 2px 0 10px 3px #dcdde2;
}
.hong {
  margin-top: 25px;
  width: 1350px;
  height: 250px;
  border-radius: 5px;
}
.ho1 {
  margin-top: 60px;
  width: 590px;
  height: 480px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 2px 0 10px 3px #dcdde2;
  float: left;
}
.ho2 {
  margin-top: 60px;
  margin-left: 40px;
  width: 590px;
  height: 480px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 2px 0 10px 3px #dcdde2;
  float: left;

}
.aspect-ratio-container{
  position: relative;
  width: 100%;
  margin-left: 75px;
}
.app-container>*{
  transform: scale(1.25);
  transform-origin: 0 0; /* 设置缩放原点为左上角 */
}
.aspect-ratio-container.app-container{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%
}
</style>
