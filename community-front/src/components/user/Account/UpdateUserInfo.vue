<template>
  <el-container style="width: 100%" direction="vertical">
    <el-main>
      <el-form ref="user" :model="user" label-width="100px" class="A"
               style="width: 90%;height: 60%;margin-top:150px;padding:0">
        <el-form-item  label="姓名" style="font-size: 25px">
          <el-input v-model="user.name" style="padding-right: 25px"></el-input>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="user.telephone" style="padding-right: 25px"></el-input>
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer @click.native="updaterUserInfo">
      确定
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "UpdateUserInfo",
  data() {
    return {
      userid: -1,
      user: {},
    }
  },
  methods: {
    updaterUserInfo() {
      this.$http.post("/user/updaterUserInfo", this.user).then(res => {
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


<style  scoped lang="scss" >
html,
body, #app,
.el-container {
  padding: 0px;
  margin: 0px;
  height: 100%;
}

.A .el-form-item__label{
  color: red;
  font-size: 22px;
}

.el-row {
  border: #ffffff;
  padding-left: 25px;
  text-align: left;
  line-height: 40px;
  font-size: 25px
}

.i_label.el-form-item__label {
  font-size: 30px;
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
