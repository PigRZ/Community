<template>
  <el-main style="padding: 0">
    <el-row style="text-align: center;padding-top: 20px;padding-left: 0;line-height: 60px;">
      <el-upload
        :action="uploadPhotoPath(user.id)"
        :show-file-list="false"
        :with-credentials="true"
        :on-success="setPhoto"
        :before-upload="beforeAvatarUpload">
        <img :src="getImgPath(user.dir,user.photo)" width="80px" height="80px" style="border-radius: 100%;padding: 0;">
      </el-upload>
    </el-row>
    <el-row style="text-align: center;font-size: 30px;padding-left: 0;">
      <b>{{ user.name }}</b>
    </el-row>
    <el-row style="padding-top: 0;line-height:20px;padding-left: 0"><hr></el-row>
    <el-row  @click.native="push({name:'UserInfo',query: {user:user}})">
      我的资料
      <div style="float: right"><i class="el-icon-arrow-right"></i></div>
    </el-row>
    <el-row style="padding-top: 0;line-height:20px;padding-left: 0"><hr></el-row>
    <el-row @click.native="push({name:'UpdateUserInfo',query: {user:user}})">
      修改信息
      <div style="float: right"><i class="el-icon-arrow-right"></i></div>
    </el-row >
    <el-row style="padding-top: 0;line-height:10px;padding-left: 0"><hr></el-row>
    <el-row @click.native="push({name:'ChangePwd',query: {user:user}})">
      修改密码
      <div style="float: right"><i class="el-icon-arrow-right"></i></div>
    </el-row>
    <el-row style="padding-top: 0;line-height:10px;padding-left: 0"><hr> </el-row>
    <el-row  @click.native="logout">
      退出登录
      <div style="float: right"><i class="el-icon-arrow-right"></i></div>
    </el-row>
    <el-row style="padding-top: 0;line-height:10px;padding-left: 0">
      <hr style="color: black">
    </el-row>
  </el-main>
</template>

<script>
import Vue from 'vue'
export default {
  name: "AccountMenu",
  data() {
    return {
      userid: -1,
      user: {},
    }
  },
  methods: {
    getImgPath(dir, photo) {
      if(photo===undefined||photo===null||photo==='') {
        return Vue.prototype.serverBaseURL+"image/default.png";
      }
      else {
        return  Vue.prototype.serverBaseURL+ dir + photo;
      }

    },
    setPhoto(res, file){
      console.log("res"+res)
      this.user.photo=res;
    },
    uploadPhotoPath(id){
      // return "http://1.117.62.189/user/uploadPhoto/"+id
      return "http://localhost:8081/user/uploadPhoto/"+id
    },
    async logout(){
      window.sessionStorage.removeItem("userid");
      this.$router.push("/");
      this.$http.get("/user/logout",{params:{id:this.userid}}).then(()=>{
      })
    },
    push(dist){
      this.$router.push(dist);
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    }
  },
  created() {
    this.userid = window.sessionStorage.getItem("userid");
    this.$http.get("/user/getUserById", {params: {id: this.userid}}).then(res => {
      this.user = res.data
      console.log(this.user)
    })
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
  padding-left: 20px;
  text-align: left;
  line-height: 36px;
  font-size: 20px;
  font-weight: bold;
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
