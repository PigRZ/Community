<template>
  <el-container style="height: 100%;width:100%;'Helvetica Neue',Helvetica,'PingFang SC','Hiragino Sans GB','Microsoft YaHei','微软雅黑',Arial,sans-serif;font-size: 18px;"
                direction="vertical" >
    <el-header>
      <el-container>
        <el-col span="1">
          <i class="el-icon-back" @click="$router.go(-1);"></i>
        </el-col>
        <el-col span="5"></el-col>
      </el-container>
    </el-header>

    <el-main>
      <router-view  v-if="isRouterAlive"/>
    </el-main>
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
  width: 100%;
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
  padding: 0;
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
