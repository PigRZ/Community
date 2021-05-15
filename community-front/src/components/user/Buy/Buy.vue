<template>
  <el-container style="width:100%;height: 100%;padding: 0" direction="vertical">
    <el-main>
        <router-view v-if="isRouterAlive"></router-view>
    </el-main>
    <el-footer>
      <el-container>
        <el-col>
          <i class="el-icon-s-goods"></i>
          <router-link :to="{name:'UserGoodList'}"  @click.native="flushRouter">团购</router-link>
        </el-col>
        <el-col>
          <i class="el-icon-s-order"></i>
          <router-link :to="{name:'UserOrderList'}"  @click.native="flushRouter">订单</router-link>
        </el-col>
      </el-container>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "Buy",
  data(){
    return{
      isRouterAlive:true,
    }
  },
  methods:{
    flushRouter() {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
  },

  created() {
  }
}
</script>

<style scoped lang="scss">

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
