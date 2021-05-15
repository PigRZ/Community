<template>
  <el-container style="width: 100%;height: 100%" direction="vertical">
    <el-container>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" style="width: 100%;height: 100%;padding: 20px;line-height: 100px;margin-top: 100px;">
        <el-form-item label="维修类型">
          <el-select v-model="form.type" @change="setNameOptions(form.type)" placeholder="请选择维修项目的类型">
            <el-option
              v-for="type in typeOptions"
              :key="type"
              :label="type"
              :value="type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="维修项目">
          <el-select v-model="form.name" @change="setId(form.name)" placeholder="请选择维修项目">
            <el-option
              v-for="name in nameOptions"
              :key="name"
              :label="name"
              :value="name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-input
          type="textarea"
          :rows="4"
          placeholder="(请描述详细信息，200字以内)"
          v-model="form.detail">
        </el-input>
      </el-form>
    </el-container>
    <el-footer>
      <div style="float: right;">
        <el-button @click="addRepair">提交维修申请</el-button>
      </div>
    </el-footer>
  </el-container>
</template>


<script>
export default {
  name: "UserRepairItemList",
  data() {
    return {
      forbid_repair:true,
      userid:-1,
      typeOptions:[],
      nameOptions:[],
      repairItemId:-1,
      repairItemList:[],
      form:{
        id:-1,
        name:'',
        type:'',
        detail:''
      },
      rules: {
        id: [
          {required: true, message: '请选择维修类型和项目', trigger: 'blur',min:1}
        ],
        detail:[
          {required: true, message: '最多200字', trigger: 'blur',min:0,max:200}
          ]
      }
    }
  },
  methods: {
    setNameOptions(type){
      this.nameOptions=[]
      for(var repairItem of this.repairItemList){
        if(repairItem.type===type){
          this.nameOptions.splice(this.nameOptions.length,0,repairItem.name);
        }
      }
    },
    setId(name){
      for(var repairItem of this.repairItemList){
        if(repairItem.name===name){
          this.form.id=repairItem.id;
        }
      }
    },

    async addRepair(){
      await this.$http.post("user/addRepair",{repairitemid:this.form.id,userid:this.userid,detail:this.form.detail}).then(res => {
        if(res.data==="success"){
          this.$message({
            message: '提交成功',
            type:'success',
          });
        }
        else{
          this.$message.error('提交失败');
        }
      })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.userid=window.sessionStorage.getItem('userid');
    this.$http.get("user/repairItemList").then(res => {
      console.log(res);
      this.repairItemList = res.data;
    })
      .catch(err => {
        console.log(err);
      });
    this.$http.get("user/repairItemTypeList").then(res => {
      console.log(res);
      this.typeOptions=res.data;
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
