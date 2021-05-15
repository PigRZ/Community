<template>
  <el-container direction="vertical" id="form">
    <el-main style="padding: 0">
      <el-row>
        <el-col :span="6" style="text-align: right">姓名:</el-col>
        <el-col :span="18" style="padding-left: 30px">{{user.name}}</el-col>
      </el-row>
      <el-row style="padding-top: 0;line-height:20px;padding-left: 0"><hr></el-row>

      <el-row >
        <el-col :span="6" style="text-align: right">性别:</el-col>
        <el-col :span="18" style="padding-left: 30px">{{user.sex}}</el-col>
      </el-row>
      <el-row style="padding-top: 0;line-height:20px;padding-left: 0"><hr></el-row>

      <el-row >

        <el-col :span="6" style="text-align: right">身份证号:</el-col>
        <el-col :span="18" style="padding-left: 30px">{{user.identity}}</el-col>
      </el-row>
      <el-row style="padding-top: 0;line-height:20px;padding-left: 0"><hr></el-row>

      <el-row>
        <el-col :span="6" style="text-align: right">电话号码:</el-col>
        <el-col :span="18" style="padding-left: 30px">{{user.telephone}}</el-col>

      </el-row>
      <el-row style="padding-top: 0;line-height:20px;padding-left: 0"><hr></el-row>

      <el-row>

        <el-col :span="6" style="text-align: right">住址:</el-col>
        <el-col :span="18" style="padding-left: 30px">{{user.section}}号楼—{{user.cell}}室</el-col>
      </el-row>
      <el-row style="padding-top: 0;line-height:20px;padding-left: 0"><hr></el-row>
      <el-row>

        <el-col :span="6" style="text-align: right">健康状态:</el-col>
        <el-col :span="18" style="padding-left: 30px">{{user.health}}</el-col>
      </el-row>
      <el-row style="padding-top: 0;line-height:20px;padding-left: 0"><hr></el-row>
    </el-main>
    <el-footer>
      <router-link :to="{name:'UpdateUserInfo',query:{user:user}}">修改资料</router-link>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "UserInfo",
  data(){
    return{
      userid:-1,
      user:{},
    }
  },
  created() {
    this.userid=window.sessionStorage.getItem("userid");
    this.user=this.$route.query.user;
    if(this.user.id===undefined) {
      this.$http.get("/user/getUserById", {params: {id: this.userid}}).then(res => {
        this.user = res.data
      })
    }
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
  padding-left: 20px;
  text-align: left;
  line-height: 40px;
  font-size: 20px
}

.el-col {
  border: #ffffff;
}
.el-header, .el-footer {
  background-color: #3f5c6d2c;
  color: #333;
  text-align: center;
  line-height: 60px;
}


.el-main {
  background-color: transparent;
  color: #333;
  text-align: center;
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
