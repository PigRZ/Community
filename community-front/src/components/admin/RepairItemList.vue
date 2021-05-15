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
        <el-form-item label="类型">
          <el-select v-model="form.type" placeholder="请选择维修项目的类型">
            <el-option
              v-for="type in options"
              :key="type"
              :label="type"
              :value="type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogType==='add'?addRepairItem():updateRepairItem()">确 定
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-row>
      搜索
      <el-input
        placeholder="请输入维修项目名称"
        prefix-icon="el-icon-search"
        v-model="searchinput"
      >
      </el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchRepairItem"></el-button>
      <el-button type="primary" icon="el-icon-add" @click="showDialog('add')">添加新维修项目
      </el-button>
    </el-row>
    <br>
    <el-row>
      筛选
      <el-select v-model="type" placeholder="请选择维修项目的类型">
        <el-option
          v-for="type in options"
          :key="type"
          :label="type"
          :value="type">
        </el-option>
      </el-select>
      <el-button type="primary" @click="selectRepairItem">筛选</el-button>
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
          <el-table-column prop="type" label="类型" width="100"></el-table-column>
          <el-table-column prop="operate" label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-edit" @click="index=scope.$index;showDialog('update')"></el-button>
              <el-button type="danger" icon="el-icon-delete" @click="index=scope.$index;deleteRepairItem(scope.row.id)"></el-button>
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
  name: "RepairItemList",
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
      index:-1,
      options:['','水','电','金','木','泥','油漆','厨房'],
      tableData: [],
      searchinput: '',
      type:'',
      form: {
        id: '',
        name: '',
        type: ''
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
    searchRepairItem() {
      if (this.searchinput === '') {
        this.$http.get("admin/repairItemList").then(res => {
          console.log(res);
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.$http.get("admin/searchRepairItem", {params: {name: this.searchinput}}).then(res => {
          console.log(res);
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      }
    },
    selectRepairItem() {
      this.$http.get("admin/selectRepairItem", {params: {type: this.type}}).then(res => {
        console.log(res);
        this.tableData = res.data;
        this.flushTable(1)
      })
        .catch(err => {
          console.log(err);
        });
    },
    updateRepairItem() {
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.index;
      console.log('更新行'+index);
      this.$http.post("admin/updateRepairItem", this.form).then(res => {
        if (res.data === 'success') {
          this.tableData.splice(index, 1, this.form);
          this.flushTable(this.tableAtr.currentPage)
        } else {
          console.log("修改维修项目失败");
        }
      })
        .catch(err => {
          console.log(err);
        }).finally(() => {
          this.dialogVisible = false;
        })
    },
    deleteRepairItem(id) {
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.index;
      this.$http.get("admin/deleteRepairItem", {params: {id: id}}).then(res => {
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
          console.log("删除商品失败")
      })
        .catch(err => {
          console.log(err);
        });
    },
    addRepairItem() {
      console.log("添加维修项目")
      this.$http.post("admin/addRepairItem", this.form).then(res => {
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
    this.$http.get("admin/repairItemList").then(res => {
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
