<template>
  <el-col>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose">

      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="标题">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入内容"
            v-model="form.content">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addAdminPost">确 定
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-row>
      <el-button type="primary" @click="dialogVisible=true">发布公告</el-button>
    </el-row>
    <el-row>

      <div>
        <el-table
          :data="tableData"
          badminPost
          style="width: 100%"
        >
          <el-table-column prop="adminname" label="发帖人" width="100"></el-table-column>
          <el-table-column prop="title" label="标题" width="400"></el-table-column>
          <el-table-column prop="time" label="发帖时间" width="400"></el-table-column>
          <el-table-column prop="operate" label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="danger" icon="el-icon-delete"
                         @click="deleteIndex=scope.$index;deleteAdminPost()"></el-button>
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
export default {
  name: "AdminPostList",
  data() {
    return {
      tableAtr: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      curTableData: [],
      dialogVisible: false,
      adminid: '',
      deleteIndex: '',
      tableData: [{
        id: '',
        adminid: '',
        adminname: '',
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
    flushTable(currentPage) {
      this.tableAtr.total = this.tableData.length;
      this.curTableData = this.tableData.slice((currentPage - 1) * this.tableAtr.pageSize, currentPage * this.tableAtr.pageSize);
      this.tableAtr.currentPage = currentPage;
    },
    addAdminPost() {
      this.form.adminid = this.adminid;
      this.$http.post("admin/addAdminPost", this.form).then(res => {
        console.log(res);
        if (res.data === 'success') {
          this.$http.get("admin/adminPostList", {params: {adminid: this.adminid}}).then(res => {
            console.log(res);
            this.tableData = res.data;
            for (var row of this.tableData) {
              row.time = this.tranTime(row.time);
            }
            this.flushTable(this.tableAtr.currentPage)
          })
            .catch(err => {
              console.log(err);
            });
        } else {
          console.log("添加失败")
        }
      })
        .catch(err => {
          console.log(err);
        });
      this.dialogVisible = false;
      this.form = this.$options.data().form;
    },
    deleteAdminPost() {
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.deleteIndex;
      this.$http.get("admin/deleteAdminPost", {params: {id: this.tableData[index].id}}).then(res => {
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
    }
  },
  created() {
    this.adminid = window.sessionStorage.getItem('adminid');
    console.log(this.adminid);
    this.$http.get("admin/adminPostList", {params: {adminid: this.adminid}}).then(res => {
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

<style scoped>

.el-input {
  width: 250px;
}
</style>
