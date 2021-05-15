<template>
  <el-container style="width: 100%;height: 100%;" direction="vertical">
    <el-header>
      <div>
        <el-row :gutter="10">
          <el-col :span="20">
            <el-input v-model="searchInput" placeholder="想买点什么" prefix-icon="el-icon-search"></el-input>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" icon="el-icon-search" @click="searchGood"></el-button>
          </el-col>
        </el-row>
      </div>
    </el-header>
    <el-container>
      <el-aside width="20%">
        <el-table
          :data="typeList"
          :show-header="false"
          style="width: 100%"
        >
          <el-table-column prop="type" label="类型">
            <template slot-scope="scope">
              <a @click="showGoodListByType(scope.row.type)">{{ scope.row.type }}</a>
            </template>
          </el-table-column>
        </el-table>
      </el-aside>
      <el-container direction="vertical">
        <el-main>
          <el-table
            :data="curGoodList"
            :show-header="false"
            style="width: 100%"
          >
            <el-table-column prop="imagepath" label="图片" min-width="20%">
              <template slot-scope="scope">
                <img :src='getImgPath(scope.row.imagepath)' width="100%">
              </template>
            </el-table-column>
            <el-table-column prop="name" label="名称" min-width="15%"></el-table-column>
            <el-table-column prop="price" label="价格" min-width="15%"></el-table-column>
            <el-table-column prop="count" label="数量" min-width="50%">
              <template slot-scope="scope">
                <el-input-number v-model="scope.row.count" @change="handleChange" :placeholder="0" :min="0" :max="10"
                                 size="small"></el-input-number>
              </template>
            </el-table-column>
          </el-table>
        </el-main>

        <el-footer  >
          <el-button @click="buyGoods":disabled="forbid_buy" style="float: right"> 现在下单</el-button>
        </el-footer>
      </el-container>
    </el-container>

  </el-container>
</template>


<script>
import Vue from 'vue'
export default {
  name: "UserGoodList",
  data() {
    return {
      count: 0,
      forbid_buy: true,
      userid: -1,
      searchInput: '',
      typeList: [{
        type: '全部',
      }],
      curGoodList: [],
      goodList: [{
        id: '',
        name: '',
        imagepath: '',
        type: '',
        price: '',
        count: 0,
      }],
    }
  },
  methods: {
    showGoodListByType(type) {
      if (type === '全部') {
        this.curGoodList = this.goodList;
      } else {
        this.curGoodList = this.$options.data().curGoodList;
        for (var good of this.goodList) {
          if (good.type === type) {
            this.curGoodList.splice(this.curGoodList.length, 0, good);
          }
        }
      }
    },
    getImgPath(path) {
      return Vue.prototype.serverBaseURL + path;
    },
    searchGood() {
      if (this.searchInput === '') {
        this.curGoodList = this.goodList;
      } else {
        this.curGoodList = this.$options.data().curGoodList;
        for (var good of this.goodList) {
          if (good.name.indexOf(this.searchInput) !== -1) {
            this.curGoodList.splice(this.curGoodList.length, 0, good);
          }
        }
      }
    },
    handleChange() {
      var forbid_buy = true;
      for (var good of this.curGoodList) {
        if (good.count > 0) {
          forbid_buy = false;
        }
      }
      console.log("forbid_buy" + forbid_buy)
      this.forbid_buy = forbid_buy;
    }
    ,
    async buyGoods() {
      var orders = []
      for (var good of this.curGoodList) {
        if (good.count > 0) {
          orders.splice(orders.length, 0, {userid: this.userid, goodid: good.id, count: good.count});
        }
      }
      await this.$http.post("user/buyGood", orders).then(res => {
        if (res.data === "success") {
          this.$message({
            message: '下单成功',
            type: 'success'
          });
        } else {
          this.$message.error('下单失败');
        }
      })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.userid = window.sessionStorage.getItem('userid');
    this.$http.get("user/goodList").then(res => {
      console.log(res);
      this.goodList = res.data;
      this.curGoodList = res.data;
    })
      .catch(err => {
        console.log(err);
      });
    this.$http.get("user/goodTypeList").then(res => {
      console.log(res);
      for (var type of res.data) {
        this.typeList.splice(1, 0, type);
      }
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

.el-row {
  border: #ffffff;
}

.el-col {
  border: #ffffff;
}

.el-header, .el-footer {
  flex: 0;
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
