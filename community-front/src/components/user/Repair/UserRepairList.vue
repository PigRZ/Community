<template>
    <el-main>
      <el-table
        :data="repairList"
        style="width: 100%;height: 100%"
        :show-header="false"
        row-style="{height:50px}"
        cell-style="{height:50px}"
      >
        <el-table-column prop="id" label="我的报修记录">
          <template slot-scope="scope">
            <el-container >
              <el-header style="text-align: center">
                {{scope.row.date}}
              </el-header>
              <el-main>
                <el-container class="con" style="font-family:Arial;font-size: 18px;text-align: center ">
                  <el-col :span="18" >
                    <el-row><label style="position: relative">{{scope.row.repairitemname}}</label></el-row>
                    <el-row><label style="position: relative">{{scope.row.detail}}</label></el-row>
                  </el-col>
                  <el-col :span="6" >
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
export default {
  name: "UserRepairList",
  data(){
    return{
      userid:-1,
      repairList: [],
    }
  },
  methods:{
  },
  created() {
    this.userid=window.sessionStorage.getItem("userid");
    this.$http.get("user/getRepairByUserid", {params: {userid:window.sessionStorage.getItem("userid")}}).then(res => {
      this.repairList=res.data;
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
