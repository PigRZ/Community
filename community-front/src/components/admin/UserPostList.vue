<template>

  <el-col>
    <el-dialog
      :visible.sync="dialogShow"
      style="width: 100%;height: 100%">
      <el-row style="margin-top: 10px ;border-width: 0 0 1px 0; border-style: solid; border-color: black;">
        <el-row style="line-height: 50px;">
          <el-col :span="2">
            <img :src="getImgPath('',userPost.userphoto)" style="border-radius: 100%;width: 40px;height: 40px;float: left">
          </el-col>
          <el-col :span="22" style="font-size: 18px;padding-left: 2px;text-align: left;">
            <b>{{ userPost.username }}</b>
          </el-col>
        </el-row>
        <el-row style="font-size: 8px;line-height: 10px;text-align: left;margin-bottom: 3px" >
          {{tranTime(userPost.time)}}
        </el-row>
        <el-col :span="24" style="margin: 5px">
          <el-row class="left" type="flex" style="line-height: 2rem;text-align: left;word-break: break-all">
           <b>{{ userPost.title }}</b>
          </el-row>
          <el-row class="Left" type="flex">
            {{ userPost.content }}
          </el-row>
        </el-col>
        <el-row style="height: fit-content;margin: 0">
          <img v-for="img in userPost.images" v-if="img" :src="getImgPath(userPost.dir,img)" style="width: 100%">
        </el-row>
      </el-row>
    </el-dialog>
    <el-row>

      <div>
        <el-table
          :data="curTableData"
          badminPost
          style="width: 100%"
        >
          <el-table-column prop="username" label="发帖人" width="200"></el-table-column>
          <el-table-column prop="title" label="标题" width="300"></el-table-column>
          <el-table-column prop="content" label="内容" width="400"></el-table-column>
          <el-table-column prop="time" label="发帖时间" width="200"></el-table-column>
          <el-table-column prop="operate" label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-view"
                         @click="showUserPost(scope.row.id)"></el-button>
              <el-button type="danger" icon="el-icon-delete"
                         @click="deleteIndex=scope.$index;deleteUserPost(scope.row.id)"></el-button>
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
  </el-col>
</template>

<script>
import Vue from "vue";

export default {
  name: "UserPostList",
  data() {
    return {
      tableAtr: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      curTableData: [],
      dialogShow: false,
      dialogVisible: false,
      adminid: '',
      deleteIndex: -1,
      userPost: {},
      tableData: [{
        id: '',
        userid: '',
        username: '',
        title: '',
        content: '',
        time: '',
      },],
      form: {
        adminid: '',
        title: '',
        content: '',
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
    showUserPost(id) {
      this.$http.get("admin/getUserPostById", {params: {id: id}}).then(res => {
        this.userPost = res.data
        this.dialogShow = true;
      })
    },
    getImgPath(dir, image) {
      return Vue.prototype.serverBaseURL + dir + image;
    },
    deleteUserPost(id) {
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.deleteIndex;
      this.$http.get("admin/deleteUserPost", {params: {id:id}}).then(res => {
        console.log(res);
        if (res.data === 'success') {
          this.tableData.splice(index, 1);
          if(this.tableData.length>0&&index===this.tableData.length){
            this.flushTable(this.tableAtr.currentPage-1)
          }
          else {
            this.flushTable(this.tableAtr.currentPage)
          }
        } else {
          console.log("删除失败" + this.tableData[index])
        }
      })
        .catch(err => {
          console.log(err);
        });
    },
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
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    }
  },
  created() {
    this.$http.get("admin/userPostList").then(res => {
      console.log(res);
      this.tableData = res.data;
      for (var row of this.tableData) {
        row.time = this.tranTime(row.time);
      }
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

<style lang="scss" scoped>

.el-input {
  width: 250px;
}

.el-row .Left {
  line-height: 2rem;
  margin-top: 5px;
  margin-left: 0;
  text-align: left;
  word-break: break-all;
}
</style>
