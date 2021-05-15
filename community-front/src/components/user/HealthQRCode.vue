<template>
  <el-container direction="vertical" style="display:flex;align-items: center">
    <el-container style="width: 100%;height: 100%" direction="vertical">
      <el-row style="text-align: center;font-size:22px;line-height: 40px">我的健康码<br></el-row>
      <el-row><hr></el-row>
      <el-row style="text-align: center;line-height: 0px">
      <vue-qr :logoSrc="imageUrl"
              :text="userid"
              :colorDark="color"
              colorLight="white"
              :logoScale="0.2"
              :size="320"
              style="margin-top:20px"></vue-qr>
      </el-row>
      <el-row><hr></el-row>
      <el-row style="text-align: left;margin-left: 30px;font-size:22px;line-height: 40px" type="flex">姓名:&nbsp{{user.name}}<br></el-row>
      <el-row><hr></el-row>
      <el-row style="text-align: left;margin-left: 30px;font-size:22px;line-height: 40px"  type="flex">住址:&nbsp{{user.section}}号楼--{{user.cell}}室</el-row>
      <el-row><hr></el-row>
    </el-container>
  </el-container>
</template>

<script>
import vueQr from 'vue-qr'
import Health from "./Health/Health";

export default {
  name: "HealthQRCode",
  data() {
    return {
      userid: -1,
      color: 'green',
      user: {},
      imageUrl: require("../../assets/logo.png"),
    }
  },
  components: {
    Health,
    vueQr
  },
  created() {
    this.userid = window.sessionStorage.getItem("userid")
    this.$http.get("/user/getUserById", {params: {id: this.userid}}).then(res => {
      this.user = res.data
      if (this.user.health === '绿') {
        this.color = 'green';
      } else {
        this.color = 'red'
      }
      console.log(this.user)
    })
  }
}
</script>

<style scoped>

</style>
