<template>
  <el-col>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
      >

      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="图片">
          <el-upload
            class="avatar-uploader"
            action= "http://localhost:8081/admin/addTempGoodImg"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :with-credentials="true"
            :before-upload="beforeAvatarUpload"
            >
            <img v-if="form.imagepath" :src="getImgPath(form.imagepath)" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="名字">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="form.price"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" placeholder="请选择商品类型">
            <el-option
              v-for="type in options"
              :key="type"
              :label="type"
              :value="type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="dialogVisible = false;">取 消</el-button>
          <el-button type="primary" @click="dialogType==='add'?addGood():updateGood()">确 定
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-row>
      搜索
      <el-input
        placeholder="请输入商品名称"
        prefix-icon="el-icon-search"
        v-model="searchinput"
        style="width: 150px"
      ></el-input>
      <el-button type="primary" icon="el-icon-search" @click="searchGood"> </el-button>
      <el-button type="primary" icon="el-icon-add" @click="showDialog('add')">添加新商品</el-button>
    </el-row>
    <br>
    <el-row>
      筛选
      <el-select v-model="type" placeholder="请选择商品类型">
        <el-option
          v-for="type in options"
          :key="type"
          :label="type"
          :value="type">
        </el-option>
      </el-select>
      <el-button type="primary" @click="selectGood">筛选</el-button>
    </el-row>
    <br>
    <el-row>

      <div>
        <el-table
          :data="curTableData"
          border
          fit
          style="width: 100%"
        >
          <el-table-column prop="id" label="id" width="40"></el-table-column>
          <el-table-column prop="imagepath" label="图片" width="60">
            <template slot-scope="scope">
              <img :src="getImgPath(scope.row.imagepath)" width="100%">
            </template>
          </el-table-column>
          <el-table-column prop="name" label="名字" width="150"></el-table-column>
          <el-table-column prop="type" label="类型" width="100"></el-table-column>
          <el-table-column prop="price" label="价格(元)" width="100"></el-table-column>
          <el-table-column prop="operate" label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-edit" @click="index=scope.$index;showDialog('update')"></el-button>
              <el-button type="danger" icon="el-icon-delete" @click="index=scope.$index;deleteGood(scope.row.id)"></el-button>
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
import Vue from 'vue'
export default {
  name: "GoodList",
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
      oldimagepath:'',
      index:-1,
      options:['','食品','蔬菜','水果','工具','调味料','生活用品','其他'],
      tableData: [],
      searchinput: '',
      type:'',
      form: {
        id: '',
        imagepath:'',
        name: '',
        type: '',
        price:'',
        file:'',
      },
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
    actionAddress(){
      return Vue.prototype.serverBaseURL+"admin/addTempGoodImg"//"http://192.168.137.1:8081/admin/addTempGoodImg"
    },
    searchGood() {
      if (this.searchinput === '') {
        this.$http.get("admin/goodList").then(res => {
          console.log(res);
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.$http.get("admin/searchGood", {params: {name: this.searchinput}}).then(res => {
          console.log(res);
          this.tableData = res.data;
          this.flushTable(1)
        })
          .catch(err => {
            console.log(err);
          });
      }
    },
    selectGood() {
      this.$http.get("admin/selectGood", {params: {type: this.type}}).then(res => {
        console.log(res);
        this.tableData = res.data;
        this.flushTable(1)
      })
        .catch(err => {
          console.log(err);
        });
    },
    updateGood() {
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.index;
      console.log('更新行'+index);
      this.$http.post("admin/updateGood", this.form).then(updateres => {
        if (updateres.data === 'success') {
          this.form.imagepath='/image/good/'+this.form.imagepath.substring(this.form.imagepath.lastIndexOf('/'))
          this.tableData.splice(index, 1, this.form);
          this.flushTable(this.tableAtr.currentPage)
        } else {
          console.log("修改商品失败");
        }
      })
        .catch(err => {
          console.log(err);
        }).finally(() => {
          this.dialogVisible = false;
        })
    },
    deleteGood(id) {
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.index;
      this.$http.get("admin/deleteGood", {params: {id: id}}).then(res => {
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
    addGood() {
      console.log("添加商品")
      console.log(this.form)
      this.$http.post("admin/addGood", this.form).then(res => {
        if (res.data !== 'fail') {
          this.form.id = res.data;
          this.tableData.splice(this.tableData.length, 0, this.form);
          this.flushTable(this.tableAtr.currentPage)
        } else
          console.log("添加失败")
      })
        .catch(err => {
          console.log(err);
        }).finally(() => {
          this.dialogVisible = false;
          this.form = this.$options.data().form;
        })
    },
    handleAvatarSuccess(res, file) {
      this.form.imagepath = res
    },
    beforeAvatarUpload(file) {

      const isLt10M = file.size / 1024 / 1024 < 10;

      if (!isLt10M) {
        this.$message.error('上传图片大小不能超过 10MB!');
      }
      return isLt10M;
    },
    getImgPath(path){
      return Vue.prototype.serverBaseURL +path;
    }
    ,
    showDialog(type) {
      this.dialogType = type;
      let index = (this.tableAtr.currentPage-1)*this.tableAtr.pageSize+this.index;
      if (type === 'update') {
        this.oldimagepath=this.tableData[index].imagepath;
        this.form = this.tableData[index];
      } else {
        this.form = this.$options.data().form;
      }
      this.dialogVisible = true;
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          if(this.dialogType==='update') {
            this.form.imagepath = this.oldimagepath
            done();
          }
        })
        .catch(_ => {
        });
    },
  },
  created() {
    this.$http.get("admin/goodList").then(res => {
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


.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
