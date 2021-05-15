<template>
  <el-container style="height: 100%;width:100%;padding: 0">


    <el-main>
      <el-table
        :data="userPostList"
        style="width: 100%"
        :show-header="false"
        row-style="{height:50px}"
        cell-style="{height:50px}"
      >
        <el-table-column prop="" label="标题" min-width="70%">
          <template slot-scope="scope">
            <el-row>{{ scope.row.time }}</el-row>
            <el-row>{{ scope.row.title }}</el-row>
          </template>
        </el-table-column>
        <el-table-column prop="" label="删除" min-width="20%">
          <template slot-scope="scope">
            <el-button type="danger" icon="el-icon-delete" @click="deleteUserPost(scope.$index)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "MyPost",
  data() {
    return {
      userid: -1,
      userPostList: [],
    }
  },
  methods: {
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
    async deleteUserPost(index) {
      await this.$http.get("user/deleteUserPost",{params:{id:this.userPostList[index].id}}).then(res => {
        console.log(res);
        if(res.data==='success') {
          this.userPostList.splice(index, 1);
          this.$message({
            message: '删除成功',
            type: 'success'
          })
        }
        else {
          console.log("删除失败"+this.userPostList[index])
          this.$message.error('删除失败')
        }
      })
        .catch(err => {
          console.log(err);
        });
    },

  },
  created() {
    this.userid = window.sessionStorage.getItem("userid");
    this.$http.get("user/getUserPostByUserid", {params: {userid: window.sessionStorage.getItem("userid")}}).then(res => {
      this.userPostList = res.data;
      for(var userPost of this.userPostList){
        userPost.time=this.tranTime(userPost.time);
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

con.el-container {
  position: relative;

  .el-col {
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
