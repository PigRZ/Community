<template>
  <el-container direction="vertical">
    <el-main>
      <router-view></router-view>
    </el-main>
    <el-footer>
      <el-container>
        <el-col>
          <router-link :to="{name:'HealthUpload'}" @click.native="flushRouter">上传</router-link>
        </el-col>
        <el-col>
          <router-link :to="{name:'HealthRecord'}"  @click.native="flushRouter">记录</router-link>
        </el-col>
      </el-container>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "Health",
  data() {
    return {
      isRouterAlive:true,
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
  flex:0;
  background-color: #3f5c6d2c;
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
