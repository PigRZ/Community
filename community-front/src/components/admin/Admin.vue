<template>
    <el-container style="height: 100%;width:100%;">
      <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%">
        <el-form ref="form" :model="form" label-width="70px">
          <el-form-item label="旧密码">
            <el-input type="password" v-model="form.oldPwd" ></el-input>
          </el-form-item>
          <el-form-item v-model="form.newPwd" label="新密码">
            <el-input  type="password" v-model="form.newPwd" ></el-input>
          </el-form-item>
          <el-form-item ><el-button type="primary" @click="changePwd" style="float: right;">确定</el-button></el-form-item>
        </el-form>
      </el-dialog>
      <el-aside width="200px" >
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          :collapse="false">
          <el-menu-item to="/userList" index="1">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <router-link :to="{name:'UserList'}" @click.native="flushRouter">居民列表</router-link>
            </template>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-s-goods"></i>
              社区团购
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1">
                <router-link :to="{name:'GoodList'}" @click.native="flushRouter">商品列表</router-link>
              </el-menu-item>
              <el-menu-item index="2-2">
                <router-link :to="{name:'OrderList'}" @click.native="flushRouter">订单列表</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              物业报修
            </template>
            <el-menu-item-group>
              <el-menu-item index="3-1">
                <router-link :to="{name:'RepairItemList'}" @click.native="flushRouter">维修项目列表</router-link>
              </el-menu-item>
              <el-menu-item index="3-2">
                <router-link :to="{name:'RepairList'}" @click.native="flushRouter">居民报修清单</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              论坛管理
            </template>
            <el-menu-item-group>
              <el-menu-item index="4-1">
                <router-link :to="{name:'AdminPostList'}" @click.native="flushRouter">管理我的公告</router-link>
              </el-menu-item>
              <el-menu-item index="4-2">
                <router-link :to="{name:'UserPostList'}" @click.native="flushRouter">居民帖子管理</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <el-menu-item to="/" index="5">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <router-link :to="{name:'AdminChat'}" @click.native="flushRouter">在线聊天</router-link>
            </template>
          </el-menu-item>
          <el-menu-item to="/" index="6">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <router-link :to="{name:'PartyMemberList'}" @click.native="flushRouter">党员下沉</router-link>
            </template>
          </el-menu-item>
        </el-menu>

      </el-aside>

      <el-container direction="vertical">
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="dialogVisible=true;form.oldPwd='';form.newPwd=''">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <el-main>
          <!--在这里展示视图-->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

</template>
<script>
export default {
  name: "Admin",
  data(){
    return{
      isRouterAlive:true,
      dialogVisible:false,
      id:-1,
      form:{
        id:-1,
        oldPwd:'',
        newPwd:''
      }
    }
  },
  methods:{
    flushRouter() {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
    changePwd(){
      console.log(typeof this.id)
      this.form.id=parseInt(this.id);
      console.log(typeof this.form.id)
      this.$http.post('/admin/changePwd',this.form).then(res => {
        if (res.data === 'success') {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.dialogVisible=false;
        } else {
          this.$message.error("修改失败" + res.data);
        }
      }).catch(error=>{
        this.$message.error("修改失败" + res.data);
      })
    },
    async logout(){
      window.sessionStorage.removeItem("adminid");
      this.$router.push("/");
      this.$http.get("/admin/logout",{params:{id:this.id}}).then(()=>{
      })
    },
  },

  created() {
    this.id=window.sessionStorage.getItem('adminid');
    console.log(typeof this.id)
    console.log('admin登录成功'+this.id)
  }
}
</script>
<style scoped lang="scss">
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
.el-aside {
  color: #333;
}
a{
  text-decoration: none;

}
a:link {color: black}     /* 未访问的链接 */
a:visited {color: black}  /* 已访问的链接 */
a:hover {color: black}    /* 当有鼠标悬停在链接上 */
a:active {color: black}   /* 被选择的链接 */
.router-link-active {
  text-decoration: none;
}
</style>
