<template>
  <el-container v-if="isMobile" class="pc">
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="mobile-login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item  style="margin-left: 40px">
        <el-button type="primary" v-on:click="onSubmit('loginForm','user')">居民登录</el-button>
        <!--        <el-button type="primary" v-on:click="onSubmit('loginForm','admin')" >管理员登录</el-button>-->
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="200px">
      <span>账号或密码错误</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
  <el-container v-else class="pc">
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="pc-login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item style="margin-left: 40px">
        <!--        <el-button type="primary" v-on:click="onSubmit('loginForm','user')" >居民登录</el-button>-->
        <el-button type="primary" v-on:click="onSubmit('loginForm','admin')">管理员登录</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="200px">
      <span>账号或密码错误</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      id: '',
      isMobile: false,
      form: {
        username: '',
        password: ''
      },

      // 表单验证，需要在 el-form-item 元素中增加 prop 属性
      rules: {
        username: [
          {required: true, message: '账号不可为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不可为空', trigger: 'blur'}
        ]
      },

      // 对话框显示和隐藏
      dialogVisible: false
    }
  },
  methods: {
    onSubmit(formName, type) {
      // 为表单绑定验证功能
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let data = {username: this.form.username, password: this.form.password};
          if (type === 'user') {
            const {data: res} = await this.$http.post('userlogin', this.form);
            if (res !== -1) {
              window.sessionStorage.setItem('userid', parseInt(res));
              console.log('----------------')
              console.log(typeof window.sessionStorage.getItem('userid'))
              this.$router.push('User');
            } else {
              this.dialogVisible = true;
              return false;
            }
          } else if (type === 'admin') {
            const {data: res} = await this.$http.post('adminlogin', this.form);
            if (res !== -1) {
              console.log(res)
              window.sessionStorage.setItem('adminid', parseInt(res));
              console.log('----------------')
              console.log(typeof window.sessionStorage.getItem('adminid'))
              this.$router.push("Admin");
            } else {
              this.dialogVisible = true;
              return false;
            }
          }

        } else {
          this.dialogVisible = true;
          return false;
        }
      });
    }
  }
  ,
  created() {
    this.isMobile = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i);
  }
}
</script>

<style lang="scss" scoped>

.pc-login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  height: 250px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;

}
.mobile-login-box {
  //border: 1px solid #DCDFE6;
  width: 350px;
  height: 250px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;

}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}

.pc {
  height: 100%;
  width: 100%;
}
</style>
