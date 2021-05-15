<template>
  <el-container direction="vertical">
    <el-main>
      <el-dialog
        title="发帖"
        :visible.sync="dialogVisible"
        :fullscreen="true"
        :before-close="handleClose"
        style="height: 100%;width:100%">
        <el-container direction="vertical">
          <el-main style="width: 100%;height: 100%;padding: 0">
            <el-form ref="userpost" :model="userpost"
                     style="width: 100%;height: 90%;padding: 0;">
              <el-form-item label="标题">
                <el-input v-model="userpost.title"></el-input>
              </el-form-item>
              <el-form-item label="内容">
                <el-input
                  type="textarea"
                  :rows="4"
                  placeholder="(请输入内容)"
                  v-model="userpost.content">
                </el-input>
              </el-form-item>
                <el-upload
                  :action="actionAddress()"
                  list-type="picture-card"
                  :with-credentials="true"
                  :on-success="onSuccess"
                  :on-remove="handleRemove">
                  <i class="el-icon-plus"></i>
                </el-upload>
              <el-form-item>
                <br>
                <el-button type="primary" @click="addUserPost">发布</el-button>
              </el-form-item>
            </el-form>
          </el-main>
        </el-container>
      </el-dialog>
      <router-view v-if="isRouterAlive"></router-view>
    </el-main>
    <el-footer>
      <el-container>
        <el-col :span="10">
          <i class="el-icon-s-home"></i>
          <router-link :to="{name:'ViewPostList'}" @click.native="flushRouter">浏览论坛</router-link>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" icon="el-icon-plus" circle @click="dialogVisible=true"></el-button>
        </el-col>
        <el-col :span="10">
          <i class="el-icon-user"></i>
          <router-link :to="{name:'MyPost'}" @click.native="flushRouter">发帖记录</router-link>
        </el-col>
      </el-container>
    </el-footer>
  </el-container>
</template>

<script>
import Vue from "vue";

export default {
  name: "Post",
  data() {
    return {
      isRouterAlive: true,
      userid: -1,
      dialogVisible: false,
      userpost: {
        userid: -1,
        title: '',
        content: '',
        images: []
      },
    }
  },
  methods: {
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    },
    flushRouter() {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
    actionAddress() {
      return "http://localhost:8081/user/addTempGoodImg";
      // return "http://1.117.62.189/user/addTempGoodImg";
    },
    onSuccess(res) {
      this.userpost.images.splice(this.userpost.images.length, 0, res)
      console.log(this.userpost.images)
    },
    handleRemove(file, fileList) {
      for(var i=0;i< this.userpost.images.length;){
        if(file.response===this.userpost.images[i]){
          this.userpost.images.splice(i,1);
          console.log(file.response)
          console.log(this.userpost.images)
        }else{
          i++;
        }
      }
    },
    addUserPost() {
      this.userpost.userid = this.userid;
      console.log( this.userpost.images)
      this.$http.post("user/addUserPost", this.userpost).then(res => {
        if (res.data === "success") {
          this.dialogVisible = false;
          this.$message({
            message: '发帖成功',
            type: 'success'
          })
          this.isRouterAlive = false
          this.$nextTick(function () {
            this.isRouterAlive = true
          })
        } else {
          this.$message.error('发帖失败');
        }
      }).catch(err => {
        console.log(err);
      });
    }
  },
  created() {
    this.userid = window.sessionStorage.getItem("userid");
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

.el-message-box {
  width: 300px;
}

.el-row {
  border: #ffffff;
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

