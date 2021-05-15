<template>
  <el-container style="width: 100%;height: 100%" direction="vertical">
    <el-container>
      <el-form v-model="form" :rules="rules" style="width: 100%;height: 100%;padding: 20px;line-height: 100px;margin-top: 100px;">
        <el-form-item label="体温" >
          <br>
          <el-input v-model="form.temperature" type="number"></el-input>
        </el-form-item>
        <el-form-item label="近期是否有发烧、呕吐、腹泻等症状" >
          <br>
          <el-radio-group v-model="form.isUncomfortable" style="float: left;">
            <el-radio label="否">否</el-radio>
            <el-radio label="是">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="近期是否有中高风险地区旅居史" >
          <br>
          <el-radio-group v-model="form.comeFromDangerous" style="float: left;">
            <el-radio label="否">否</el-radio>
            <el-radio label="是">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" >
          <br>
          <el-input v-model="form.detail" type="textarea" :row="3"></el-input>
        </el-form-item>
      </el-form>
    </el-container>
    <el-footer>
      <div style="float: right;">
        <el-button @click="uploadHealth">提交</el-button>
      </div>
    </el-footer>
  </el-container>
</template>


<script>
export default {
  name: "HealthUpload",
  data() {
    return {
      forbid_repair:true,
      userid:-1,
      form:{
        userid:-1,
        temperature:undefined,
        isUncomfortable:'否',
        comeFromDangerous:'否',
        detail:'',
      },
      rules: {
          userid:[{

          }]
      }
    }
  },
  methods: {
    uploadHealth(){
      this.$http.post("user/uploadHealth",this.form).then(res=>{
        if(res.data==="success"){
          this.$message({
            type:"success",
            message:"提交成功"
          })
        }
        else{
          this.$message.error("提交失败")
        }
      })
    }
  },
  created() {
    this.userid=window.sessionStorage.getItem('userid');
    this.form.userid=this.userid;
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
