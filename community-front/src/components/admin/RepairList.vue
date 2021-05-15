<template>
  <el-col>
    <el-row>
      搜索
      <el-input
        placeholder="请输入用户名称"
        prefix-icon="el-icon-search"
        v-model="username"
      >
      </el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchRepair('username')"></el-button>
      <el-input
        placeholder="请输入维修项目名称"
        prefix-icon="el-icon-search"
        v-model="repairitemname"
      >
      </el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchRepair('repairitemname')"></el-button>
    </el-row>
    <br>
    <el-row>

      <div>
        <el-table
          :data="curTableData"
          border
          style="width: 100%"
        >
          <el-table-column prop="username" label="居民姓名" width="100"></el-table-column>
          <el-table-column prop="useraddress" label="居民住址" width="100"></el-table-column>
          <el-table-column prop="repairitemname" label="维修项目" width="100"></el-table-column>
          <el-table-column prop="detail" label="详细说明" width="100"></el-table-column>
          <el-table-column prop="date" label="日期" width="100"></el-table-column>
          <el-table-column prop="done" label="是否完成" width="100"></el-table-column>
          <el-table-column prop="operate" label="操作">
            <template slot-scope="scope">
              <el-button type="success" icon="el-icon-circle-check" @click="operateIndex=scope.$index;completeRepair()">完成</el-button>
              <el-button type="danger" icon="el-icon-delete" @click="operateIndex=scope.$index;deleteRepair(scope.row.id)">删除</el-button>
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
      operateIndex:-1,
      tableData: [{
        id:'',
        userid:'',
        username:'',
        repairitemid:'',
        repairitemname:'',
        date:'',
        done:'',
      },],
      username: '',
      repairitemname: ''
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
    searchRepair(type) {
      if(type==='username'){
        this.repairitemname='';
        this.$http.get("admin/searchRepairByUsername",{params:{username:this.username}}).then(res => {
          console.log(res);
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      }
      else if(type==='repairitemname'){
        this.username='';
        this.$http.get("admin/searchRepairByRepairitemname",{params:{repairitemname:this.repairitemname}}).then(res => {
          console.log(res);
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      }
    },
    deleteRepair(id){
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.operateIndex;
      this.$http.get("admin/deleteRepair",{params:{id:id}}).then(res => {
        console.log(res);
        if(res.data==='success') {
          this.tableData.splice(index, 1);
          if(this.tableData.length>0&&index===this.tableData.length){
            this.flushTable(this.tableAtr.currentPage-1)
          }
          else {
            this.flushTable(this.tableAtr.currentPage)
          }
        }
        else {
          console.log("删除失败"+this.tableData[index])
        }
      })
        .catch(err => {
          console.log(err);
        });
    },
    completeRepair(){
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.operateIndex;
      this.$http.get("admin/completeRepair",{params:{id:this.tableData[index].id}}).then(res => {
        console.log(res);
        if(res.data==='success') {
          this.tableData[index].done='是';
          this.flushTable(this.tableAtr.currentPage)
        }
        else {
          console.log("完成失败"+this.tableData[index])
        }
      })
        .catch(err => {
          console.log(err);
        });
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
    this.$http.get("admin/repairList").then(res => {
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
