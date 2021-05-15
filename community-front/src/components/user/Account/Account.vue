<template>
  <el-container style="width:100%;height: 100%;padding: 0">
    <router-view></router-view>
  </el-container>
</template>

<script>
export default {
  name: "Account",
  data() {
    return {
    }
  },
  methods: {

  },
  created() {
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
  padding-left: 25px;
  text-align: left;
  line-height: 40px;
  font-size: 25px
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

