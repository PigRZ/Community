<template>
    <el-main>
      <el-table
        :data="orderList"
        style="width: 100%;height: 100%"
        :show-header="false"
        row-style="{height:50px}"
        cell-style="{height:50px}"
      >
        <el-table-column prop="id" label="我的订单">
          <template slot-scope="scope">
            <el-container >
              <el-header style="text-align: center">
                {{scope.row.date}}
              </el-header>
              <el-main>
                <el-container class="con" style="font-family:Arial;font-size: 18px;text-align: center ">
                  <el-col :span="10"> <img :src="getImgPath(scope.row.imagepath)"  height="60px"> </el-col>
                  <el-col :span="6" ><label style="position: relative">{{scope.row.goodname}}</label></el-col>
                  <el-col :span="4">×{{scope.row.count}}</el-col>
                  <el-col :span="4" >
                    <div v-if="scope.row.done==='是'">已完成</div>
                    <div v-else>未完成</div>
                  </el-col>
                  </el-container>
              </el-main>
            </el-container>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
</template>

<script>
import Vue from 'vue'
export default {
  name: "UserOrderList",
  data(){
    return{
      userid:-1,
      orderList: [],
    }
  },
  methods:{
    getImgPath(path) {
      return Vue.prototype.serverBaseURL + path;
    },
  },
  created() {
    this.userid=window.sessionStorage.getItem("userid");
    this.$http.get("user/getOrderById", {params: {id:window.sessionStorage.getItem("userid")}}).then(res => {
      this.orderList=res.data;
    })
      .catch(err => {
        console.log(err);
      });
  }
}
</script>

<style scoped lang="scss">
html,
body, #app,
.el-container {
  padding: 0px;
  margin: 0px;
  height: 100%;

}
con.el-container{
  position: relative;
  .el-col{
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
  }
}
.el-row {
  border: #ffffff;
}

.el-col {
  border: #ffffff;
}

.el-footer {
  color: #333;
  padding: 0;
  max-height: 20px;
}


.el-main {
  background-color: transparent;
  color: #333;
  height: 100%;
  line-height: 40px;
  padding: 0px;
}


a {
  text-decoration: none;

}

a:link {
  color: black
}

/* 未访问的链接 */
a:visited {
  color: black
}

/* 已访问的链接 */
a:hover {
  color: black
}

/* 当有鼠标悬停在链接上 */
a:active {
  color: black
}

/* 被选择的链接 */
.router-link-active {
  text-decoration: none;
}

</style>
