<template>
  <el-container style="height: 100%;width:100%;padding: 0">
    <el-main>
      <el-table
        :data="adminPostList"
        style="width: 100%"
        :show-header="false">
        <el-table-column >
          <template slot-scope="scope">
            <el-row style="display:flex;align-items:center;word-break: break-all">
              <el-col :span="4">
                <img :src="getImgPath()" style="width: 30px;height: 30px;border-radius: 100%">
              </el-col>
              <el-col>
                <el-row style="font-size: 15px">{{scope.row.adminname}}</el-row>
                <el-row style="font-size: 12px" v-text="tranTime(scope.row.time)"></el-row>
              </el-col>
            </el-row>
            <el-row style="font-size: 20px"><router-link :to="{name:'ViewAdminPost',query: {adminPost: scope.row}}"><b>{{scope.row.title}}</b></router-link></el-row>
            <el-row style="font-size: 19px">{{scope.row.content}}</el-row>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
import Vue from 'vue'
export default {
  name: "ViewAdminPostList",
  data() {
    return {
      adminPostList:[],
    }
  },
  methods: {
    getImgPath(){
      return Vue.prototype.serverBaseURL+"/image/admin.jpeg";
    }
    ,
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
    this.$http.get("user/getAdminPostList").then(res=>{
      this.adminPostList=res.data;
    }).catch(err => {
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
.el-row {
  border: #ffffff;

}

.el-col {
  border: #ffffff;
}

.el-footer {
  color: #333;
  padding: 0;
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
