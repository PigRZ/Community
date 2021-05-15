<template>
  <el-container style="height: 100%;font-family:'Helvetica Neue',Helvetica,'PingFang SC','Hiragino Sans GB','Microsoft YaHei','微软雅黑',Arial,sans-serif;font-size: 18px;"
                direction="vertical" >
      <el-container>
        <el-col :span="8">
          <el-row>
            <router-link :to="{name:'Buy'}">社区团购</router-link>
          </el-row>
          <el-row>
            <router-link :to="{name:'Repair'}">物业报修</router-link>
          </el-row>
          <el-row>
            <router-link :to="{name:'Health'}">健康上报</router-link>
          </el-row>
        </el-col>
        <el-col :span="8">

          <el-row>
            <router-link :to="{name:'Chat'}">社区聊天</router-link>
          </el-row>
          <el-row >
            <router-link :to="{name:'HealthQRCode'}">健康码</router-link>
          </el-row>
          <el-row >
            <router-link :to="{name:'ViewAdminPostList'}">查看公告</router-link>
          </el-row>
        </el-col>
        <el-col :span="8">
          <el-row>
            <router-link :to="{name:'Post'}">社区论坛</router-link>
          </el-row>
          <el-row>
            <router-link :to="{name:'ViewPartyMember'}">值班党员</router-link>
          </el-row>
          <el-row>
            <router-link :to="{name:'Account'}">个人中心</router-link>
          </el-row>
        </el-col>
      </el-container>
  </el-container>
</template>
<script>
export default {
  name: "User",
  data() {
    return {
      isRouterAlive:true,
      id: ''
    }
  },
  methods: {
    flushRouter() {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
  },
  created() {
    this.id = window.sessionStorage.getItem('userid');
    console.log('user登录成功' + this.id)
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

.el-header,.el-footer {
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
.el-row{
  border: 1px solid #AAAAAA;
}
</style>
