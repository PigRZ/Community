<template>
  <el-col>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="名字">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.telephone"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogType==='add'?addPartyMember():updatePartyMember()">确 定
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-row>
      搜索
      <el-input
        placeholder="请输入党员名字"
        prefix-icon="el-icon-search"
        v-model="searchinput"
      >
      </el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchPartyMember"></el-button>
      <el-button type="primary" icon="el-icon-add" @click="showDialog('add')">新增党员</el-button>
    </el-row>
    <br>
    <el-row>

      <div>
        <el-table
          :data="curTableData"
          border
          style="width: 100%"
        >
          <el-table-column prop="id" label="id" width="40"></el-table-column>
          <el-table-column prop="name" label="名字" width="150"></el-table-column>
          <el-table-column prop="telephone" label="电话" width="300"></el-table-column>
          <el-table-column prop="work" label="是否执勤" width="100"></el-table-column>
          <el-table-column prop="operate" label="操作" width="400">
            <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-turn-off"
                         @click="index=scope.$index;changeWorkState()"></el-button>
              <el-button type="primary" icon="el-icon-edit"
                         @click="index=scope.$index;showDialog('update')"></el-button>
              <el-button type="danger" icon="el-icon-delete"
                         @click="index=scope.$index;deletePartyMember(scope.row.id)"></el-button>
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
  name: "PartyMemberList",
  data() {
    return {
      tableAtr: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      curTableData: [],
      dialogType: '',
      dialogVisible: false,
      index: -1,
      tableData: [],
      searchinput: '',
      type: '',
      form: {
        id: '',
        name: '',
        work: '否',
        telephone: ''
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
    changeWorkState() {
      let index = (this.tableAtr.currentPage - 1) * this.tableAtr.pageSize + this.index;
      if (this.tableData[index].work === '是') {
        this.$http.get("admin/setPartyMemberNoWork", {params: {id: this.tableData[index].id}}).then(res => {
          if (res.data === 'success') {
            this.tableData[index].work = '否';
            this.flushTable(this.tableAtr.currentPage)
          }
        })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.$http.get("admin/setPartyMemberWork", {params: {id: this.tableData[index].id}}).then(res => {
          if (res.data === 'success') {
            this.tableData[index].work = '是';
            this.flushTable(this.tableAtr.currentPage)
          }
        })
          .catch(err => {
            console.log(err);
          });
      }
    },
    searchPartyMember() {
      if (this.searchinput === '') {
        this.$http.get("admin/partyMemberList").then(res => {
          console.log(res);
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.$http.get("admin/searchPartyMember", {params: {name: this.searchinput}}).then(res => {
          console.log(res);
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      }
    },
    updatePartyMember() {
      let index = (this.tableAtr.currentPage - 1) * this.tableAtr.pageSize + this.index;
      console.log('更新行' + index);
      this.$http.post("admin/updatePartyMember", this.form).then(updateres => {
        if (updateres.data === 'success') {
          this.tableData.splice(index, 1, this.form);
          this.flushTable(this.tableAtr.currentPage)
        } else {
          console.log("修改党员信息失败");
        }
      })
        .catch(err => {
          console.log(err);
        }).finally(() => {
        this.dialogVisible = false;
      })
    },
    deletePartyMember(id) {
      let index = (this.tableAtr.currentPage - 1) * this.tableAtr.pageSize + this.index;
      this.$http.get("admin/deletePartyMember", {params: {id: id}}).then(res => {
        if (res.data === 'success') {
          this.tableData.splice(index, 1);
          if(this.tableData.length>0&&index===this.tableData.length){
            this.flushTable(this.tableAtr.currentPage-1)
          }
          else {
            this.flushTable(this.tableAtr.currentPage)
          }
        }
        else
          console.log("删除党员记录失败")
      })
        .catch(err => {
          console.log(err);
        });
    },
    addPartyMember() {
      console.log("添加党员")
      this.$http.post("admin/addPartyMember", this.form).then(res => {
        if (res.data !== 'fail') {
          this.form.id = res.data;
          this.tableData.splice(this.tableData.length, 0, this.form);
          this.tableData[this.tableData.length - 1].work = '否';
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
      let index = (this.tableAtr.currentPage - 1) * this.tableAtr.pageSize + this.index;
      if (type === 'update') {
        this.form = this.tableData[index];
      } else {
        this.form = this.$options.data().form;
      }
      this.dialogVisible = true;
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
    this.$http.get("admin/partyMemberList").then(res => {
      console.log(res);
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
