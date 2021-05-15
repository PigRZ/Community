<template>
  <el-container style="width: 100%;height: 100%" direction="vertical">
    <el-container>
      <el-main>
        <el-form ref="pwd" :model="pwd" label-width="100px"
                 style="width: 100%;height: 60%;padding:0;padding-top:150px">
          <el-form-item  label="原密码"  class="A">
            <el-form-item__label></el-form-item__label>
            <el-input type="password" placeholder="请输入原密码" v-model="pwd.oldPwd"></el-input>
          </el-form-item>
          <el-form-item label="新密码" class="A">
            <el-form-item__label></el-form-item__label>
            <el-input type="password" placeholder="请输入新密码" v-model="pwd.newPwd"></el-input>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
    <el-footer @click.native="changePwd">
      确定
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "ChangePwd",
  data() {
    return {
      userid: -1,
      user: {},
      pwd: {
        oldPwd: '',
        newPwd: '',
      },
      rules: {
        oldPwd: [
          {required: true, message: '旧密码不可为空', trigger: 'blur'}
        ],
        newPwd: [
          {required: true, message: '新密码不可为空', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    changePwd() {

      this.$http.post("user/changePwd", {
        id: this.user.id,
        oldPwd: this.pwd.oldPwd,
        newPwd: this.pwd.newPwd
      }).then(res => {
        if (res.data === 'success') {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.$router.go(-1);
        } else {
          this.$message.error("修改失败" + res.data);
        }
      })

      //
      // }
      console.log("修改结束")
    }
  },
  created() {
    this.userid = window.sessionStorage.getItem("userid");
    this.user = this.$route.query.user;
    if (this.user.id === undefined) {
      this.$http.get("/user/getUserById", {params: {id: this.userid}}).then(res => {
        this.user = res.data
      })
    }
  }
}
</script>
<style lang="scss">
.A .el-form-item__label{
  font-size: 22px;
}

</style>

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

