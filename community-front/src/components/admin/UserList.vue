<template>
  <el-container direction="vertical" style="width: 100%;height: 80%">
    <el-dialog
      :visible.sync="healthRecordVisible"
      style="width: 100%;height: 100%">
      <el-table
        :data="healthRecord"
        style="width: 100%;height: 100%"
        row-style="{height:50px}"
        cell-style="{height:50px}"
      >
        <el-table-column prop="temperature" label="体温"></el-table-column>
        <el-table-column prop="isUncomfortable" label="近期是否有发烧、呕吐、腹泻等症状"></el-table-column>
        <el-table-column prop="comeFromDangerous" label="近期是否有高风险地区旅居史"></el-table-column>
        <el-table-column prop="detail" label="详情"></el-table-column>
        <el-table-column prop="" label="上传时间">
          <template slot-scope="scope">
            {{ tranTime(scope.row.time) }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog
      title="新增居民"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="名字">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="sex in ['男','女']"
              :key="sex"
              :label="sex"
              :value="sex">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="form.identity"></el-input>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="form.telephone"></el-input>
        </el-form-item>
        <el-form-item label="楼号">
          <el-select v-model="form.section" placeholder="请选择楼号">
            <el-option
              v-for="section in 30"
              :key="section"
              :label="section"
              :value="section">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间号">
          <el-input v-model="form.cell"></el-input>
        </el-form-item>
        <el-form-item label="健康码状态">
          <el-select v-model="form.health" placeholder="请选择健康状态">
            <el-option
              v-for="item in healthoptions"
              :key="item.value"
              :label="item.value"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogType==='add'?addUser():updateUser()">确 定
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-row>
      搜索
      <el-input
        placeholder="请输入姓名或手机号"
        prefix-icon="el-icon-search"
        v-model="searchinput"
      >
      </el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchUser"></el-button>
      <el-button type="primary" icon="el-icon-add" @click="showDialog('add',-1)">添加新居民</el-button>
    </el-row>
    <br>
    <el-row>
      筛选
      <el-select v-model="section" placeholder="请选择楼号">
        <el-option
          v-for="section in 30"
          :key="section"
          :label="section"
          :value="section">
        </el-option>
      </el-select>
      <el-select v-model="health" placeholder="请选择健康状态">
        <el-option
          v-for="item in healthoptions"
          :key="item.value"
          :label="item.value"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" @click="selectUser">筛选</el-button>
    </el-row>
    <br>
    <el-row>

      <div>
        <el-table
          :data="curTableData"
          border
          fit
          style="width: 100%;height: 100%">
          <el-table-column prop="id" label="id" width="40"></el-table-column>
          <el-table-column prop="photo" label="头像" width="90">
            <template slot-scope="scope">
              <img :src="getImgPath(scope.row.dir,scope.row.photo)" height="50px" width="50px"
                   style="border-radius: 50%">
            </template>
          </el-table-column>
          <el-table-column prop="name" label="名字" width="100"></el-table-column>
          <el-table-column prop="sex" label="性别" width="50"></el-table-column>
          <el-table-column prop="identity" label="身份证号" width="200"></el-table-column>
          <el-table-column prop="telephone" label="电话号码" width="200"></el-table-column>
          <el-table-column prop="section" label="楼号" width="80"></el-table-column>
          <el-table-column prop="cell" label="房间号" width="100"></el-table-column>
          <el-table-column prop="health" label="健康码状态" width="100"></el-table-column>
          <el-table-column prop="operate" label="操作">
            <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-edit"
                         @click="index=scope.$index;showDialog('update')"></el-button>
              <el-button type="danger" icon="el-icon-delete" @click="index=scope.$index;deleteUser(scope.row.id)"></el-button>
              <el-button type="primary" icon="el-icon-more-outline" @click="openHealthRecord(scope.row.id)"></el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          layout="prev, pager, next"
          @current-change="changePage"
          :current-page.sync="tableAtr.currentPage"
          :page-size.sync="tableAtr.pageSize"
          :total.sync="tableAtr.total">
        </el-pagination>
      </div>
    </el-row>
  </el-container>
</template>

<script>
import Vue from 'vue'

export default {
  name: "UserList",
  data() {
    return {
      tableAtr: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      dialogType: '',
      dialogVisible: false,
      healthRecordVisible: false,
      index: -1,
      tableData: [],
      curTableData: [],
      searchinput: '',
      healthRecord: [],
      healthoptions: [{
        value: '绿'
      }, {
        value: '红'
      }, {
        value: ''
      }],
      section: '',
      health: '',
      form: {
        id: '',
        name: '',
        sex: '',
        identity: '',
        telephone: '',
        section: '',
        dir: '',
        photo: '',
        cell: '',
        health: ''
      }
    }
  },
  methods: {
    changePage(currentPage) {
      this.tableAtr.currentPage = currentPage;
      if (currentPage * this.tableAtr.pageSize <= this.tableAtr.total) {
        this.curTableData = this.tableData.slice((currentPage - 1) * this.tableAtr.pageSize, currentPage * this.tableAtr.pageSize)
      } else {
        this.curTableData = this.tableData.slice((currentPage - 1) * this.tableAtr.pageSize, this.tableAtr.total)
      }
    },
    flushTable(currentPage){
      this.tableAtr.total = this.tableData.length;
      this.curTableData = this.tableData.slice((currentPage-1)*this.tableAtr.pageSize, currentPage*this.tableAtr.pageSize);
      this.tableAtr.currentPage = currentPage;
    },


    searchUser() {
      if (this.searchinput === '') {
        this.$http.get("admin/userList").then(res => {
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.$http.get("admin/searchUser", {params: {nameOrTelephone: this.searchinput}}).then(res => {
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      }
    },
    selectUser() {
      this.$http.get("admin/selectUser", {params: {section: this.section, health: this.health}}).then(res => {
        this.tableData = res.data;
        this.flushTable(1)
      })
        .catch(err => {
          console.log(err);
        });
    },
    updateUser() {
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.index;
      console.log('更新行' + index);
      this.$http.post("admin/updateUser", this.form).then(res => {
        if (res.data === 'success') {
          this.tableData.splice(index, 1, this.form);
          this.flushTable(this.tableAtr.currentPage)
        } else {
          console.log("修改失败");
        }
      })
        .catch(err => {
          console.log(err);
        }).finally(() => {
        this.dialogVisible = false;
      })
    },
    deleteUser(id) {
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.index;
      this.$http.get("admin/deleteUser", {params: {id: id}}).then(res => {
        if (res.data === 'success') {
          this.tableData.splice(index, 1);
          console.log(index)
          console.log(this.tableData.length)
          if(this.tableData.length>0&&index===this.tableData.length){
            this.flushTable(this.tableAtr.currentPage-1)
          }
          else {
            this.flushTable(this.tableAtr.currentPage)
          }
        }
        else
          console.log("删除失败")
      })
        .catch(err => {
          console.log(err);
        });
    },
    addUser() {
      this.$http.post("admin/addUser", this.form).then(res => {
        if (res.data !== 'fail') {
          this.form.id = res.data;
          this.tableData.splice(this.tableData.length, 0, this.form);
          this.flushTable(this.tableAtr.currentPage)
        } else
          console.log("删除失败")
      })
        .catch(err => {
          console.log(err);
        }).finally(() => {
        this.dialogVisible = false;
        this.form = this.$options.data().form;
      })
    },
    showDialog(type) {
      this.dialogType = type;
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.index;
      if (type === 'update') {
        this.form = this.tableData[index];
      } else {
        this.form = this.$options.data().form;
      }
      this.dialogVisible = true;
    },
    openHealthRecord(id) {
      console.log('id' + id)
      this.healthRecordVisible = true;
      this.$http.get("admin/getHealthByUserid", {params: {userid: id}}).then(res => {
        this.healthRecord = res.data;
      })
        .catch(err => {
          console.log(err);
        })
    },
    tranTime(UTCDateString) {
      // time = time.slice(0, 10) + "  " + time.slice(11, 19);
      // return time;
      if (!UTCDateString) {
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
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    },
    getImgPath(dir, photo) {
      if (photo === undefined || photo === null || photo === '') {
        return Vue.prototype.serverBaseURL + "/image/default.png"
      }
      return Vue.prototype.serverBaseURL + dir + photo;
    }
  },
  created() {
    this.$http.get("admin/userList").then(res => {
      this.tableData = res.data;
      this.curTableData = this.tableData.slice(0, this.tableAtr.pageSize);
      this.tableAtr.currentPage = 1;
      this.tableAtr.total = this.tableData.length;
    })
      .catch(err => {
        console.log(err);
      });
  }
}
</script>

<style scoped>

.el-input {
  width: 250px;
}
</style>
