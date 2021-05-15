<template>
  <el-container style="height: 100%;width:100%;padding: 0">
    <el-main>
      <el-row style="height: fit-content;margin-top: 10px ;">
        <el-row style="line-height: 50px;">
          <el-col :span="3">
            <img :src="getImgPath()" style="border-radius: 100%;width: 40px;height: 40px;float: left">
          </el-col>
          <el-col :span="21" style="font-size: 18px;padding-left: 2px;text-align: left;">
            <b>{{ adminPost.adminname }}</b>
          </el-col>
        </el-row>
        <el-row style="font-size: 10px;line-height: 10px;text-align: left;margin-bottom: 3px">
          {{ tranTime(adminPost.time) }}
        </el-row>
        <el-col :span="24" style="margin: 5px">
          <el-row class="left" type="flex" style="line-height: 2rem;text-align: left;word-break: break-all">{{
              adminPost.title
            }}</el-row>
          <el-row class="Left" type="flex">
            {{ adminPost.content }}
          </el-row>
        </el-col>
        <el-row style="height: fit-content;margin: 0">
          <img v-for="img in adminPost.images" v-if="img" :src="getImgPath(adminPost.dir,img)" style="width: 100%">
        </el-row>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import Vue from 'vue'
export default {
  name: "ViewAdminPost",
  data(){
    return{
      adminPost:{},
    }
  }
  ,methods:{
    getImgPath(){
      return Vue.prototype.serverBaseURL+"/image/admin.jpeg";
    },
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
    this.adminPost=this.$route.query.adminPost;
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

.el-row .Left {
  line-height: 2rem;
  margin-top: 5px;
  margin-left: 0;
  text-align: left;
  word-break: break-all;
}

.el-col {
  border: #ffffff;
}
.el-dialog__wrapper {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: auto;
  margin: 0;
}

.el-header, .el-footer {
  flex: 0;
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-main {
  background-color: transparent;
  color: #333;
  padding: 0px;
  height: 100%;
  line-height: 160px;
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


