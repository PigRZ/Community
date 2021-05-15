<template>
    <el-main>
      <el-table
        :data="healthRecord"
        style="width: 100%;height: 100%"
        row-style="{height:50px}"
        cell-style="{height:50px}"
      >
        <el-table-column prop="temperature" label="体温"> </el-table-column>
        <el-table-column prop="isUncomfortable" label="近期是否有发烧、呕吐、腹泻等症状"> </el-table-column>
        <el-table-column prop="comeFromDangerous" label="近期是否有高风险地区旅居史"> </el-table-column>
        <el-table-column prop="detail" label="详情"> </el-table-column>
        <el-table-column prop="" label="上传时间">
          <template slot-scope="scope">
            {{tranTime(scope.row.time)}}
          </template>
        </el-table-column>
      </el-table>
    </el-main>
</template>

<script>
export default {
  name: "HealthRecord",
  data(){
    return{
      userid:-1,
      healthRecord: [],
    }
  },
  methods:{
    tranTime(UTCDateString) {
      // time = time.slice(0, 10) + "  " + time.slice(11, 19);
      // return time;
      if(!UTCDateString){
        return '-';
      }
      function formatFunc(str) {    //格式化显示
        return str > 9 ? str : '0' + str
      }
      var date2 = new Date(UTCDateString);     //这步是关键
      var year = date2.getFullYear();
      var mon = formatFunc(date2.getMonth() + 1);
      var day = formatFunc(date2.getDate());
      var hour = date2.getHours();
      hour = formatFunc(hour);
      var min = formatFunc(date2.getMinutes());
      return year + '-' + mon + '-' + day + ' ' + ' ' + hour + ':' + min;
    },
  },
  created() {
    this.userid=window.sessionStorage.getItem("userid");
    this.$http.get("/user/getHealthByUserid", {params: {userid:window.sessionStorage.getItem("userid")}}).then(res => {
      this.healthRecord=res.data;
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
