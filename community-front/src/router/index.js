//导入vue
import Vue from 'vue';
import VueRouter from 'vue-router';
//导入组件
import Admin from "../components/admin/Admin";
import Login from "../components/Login";
import UserList from "../components/admin/UserList";
import GoodList from "../components/admin/GoodList";
import OrderList from "../components/admin/OrderList";
import RepairList from "../components/admin/RepairList";
import RepairItemList from "../components/admin/RepairItemList";
import AdminPostList from "../components/admin/AdminPostList";
import UserPostList from "../components/admin/UserPostList";
import PartyMemberList from "../components/admin/PartyMemberList";
import User from "../components/user/User";
import Account from "../components/user/Account/Account";
import Buy from "../components/user/Buy/Buy";
import UserGoodList from "../components/user/Buy/UserGoodList";
import UserOrderList from "../components/user/Buy/UserOrderList";
import Repair from "../components/user/Repair/Repair";
import UserRepairItemList from "../components/user/Repair/UserRepairItemList";
import UserRepairList from "../components/user/Repair/UserRepairList";
import Post from "../components/user/Post/Post";
import ViewPostList from "../components/user/Post/ViewPostList";
import MyPost from "../components/user/Post/MyPost";
import ViewPost from "../components/user/Post/ViewPost";
import Home from "../components/user/Home";
import UserInfo from "../components/user/Account/UserInfo";
import ChangePwd from "../components/user/Account/ChangePwd";
import UpdateUserInfo from "../components/user/Account/UpdateUserInfo";
import AccountMenu from "../components/user/Account/AccountMenu";
import Health from "../components/user/Health/Health";
import HealthUpload from "../components/user/Health/HealthUpload";
import HealthRecord from "../components/user/Health/HealthRecord";
import HealthQRCode from "../components/user/HealthQRCode";
import Chat from "../components/user/Chat";
import AdminChat from "../components/admin/AdminChat";
import ViewAdminPostList from "../components/user/AdminPost/ViewAdminPostList";
import ViewAdminPost from "../components/user/AdminPost/ViewAdminPost";
import ViewPartyMember from "../components/user/ViewPartyMember";
//使用
Vue.use(VueRouter);
//导出
const router = new VueRouter({
  // mode: 'history',
  routes: [
    //登录页
    {
      path: '/login',
      name:Login,
      component: Login
    },
    {
      path: '/',
      redirect:'/login'
    },
    {
      //首页
      path: '/admin',
      name: 'Admin',
      component: Admin,
      redirect:'/admin/userList',
      children: [
        {
          path: 'userList',
          name: 'UserList',
          component: UserList
        },
        {
          path: 'goodList',
          name: 'GoodList',
          component: GoodList
        },
        {
          path: 'orderList',
          name: 'OrderList',
          component: OrderList
        },
        {
          path: 'repairList',
          name: 'RepairList',
          component: RepairList
        },
        {
          path: 'repairItemList',
          name: 'RepairItemList',
          component: RepairItemList
        },
        {
          path: 'adminPostList',
          name: 'AdminPostList',
          component: AdminPostList
        },
        {
          path: 'userPostList',
          name: 'UserPostList',
          component: UserPostList
        },
        {
          path: 'partyMemberList',
          name: 'PartyMemberList',
          component: PartyMemberList
        },{
          path: 'adminChat',
          name: 'AdminChat',
          component: AdminChat
        }
      ]
    },
    {
      //首页
      path: '/user',
      name: 'User',
      component: User,
      redirect:'/user/home',
      children: [
        {
          path: 'home',
          name: 'Home',
          component: Home
        },
        {
          path: 'account',
          name: 'Account',
          component: Account,
          redirect:'account/accountMenu',
          children:[
            {
              path: 'accountMenu',
              name: 'AccountMenu',
              component: AccountMenu,
            },
            {
              path: 'userInfo',
              name: 'UserInfo',
              component: UserInfo,
            },{
              path: 'changePwd',
              name: 'ChangePwd',
              component: ChangePwd,
            },{
              path: 'updateUserInfo',
              name: 'UpdateUserInfo',
              component: UpdateUserInfo,
            },
          ]
        },
        {
          path: 'buy',
          name: 'Buy',
          component: Buy,
          redirect:'buy/userGoodList',
          children: [
            {
              path: 'userGoodList',
              name: 'UserGoodList',
              component: UserGoodList
            }, {
              path: 'userOrderList',
              name: 'UserOrderList',
              component: UserOrderList
            }
          ]
        },
        {
          path: 'repair',
          name: 'Repair',
          component: Repair,
          redirect:'repair/userRepairItemList',
          children: [
            {
              path: 'userRepairItemList',
              name: 'UserRepairItemList',
              component: UserRepairItemList
            }, {
              path: 'userRepairList',
              name: 'UserRepairList',
              component: UserRepairList
            }
          ]
        },
        {
          path: 'health',
          name: 'Health',
          component: Health,
          redirect:'health/healthUpload',
          children: [
            {
              path: 'healthUpload',
              name: 'HealthUpload',
              component: HealthUpload
            }, {
              path: 'healthRecord',
              name: 'HealthRecord',
              component: HealthRecord
            }
          ]
        },
        {
          path: 'viewAdminPostList',
          name: 'ViewAdminPostList',
          component: ViewAdminPostList,
        },{
          path: 'viewAdminPost',
          name: 'ViewAdminPost',
          component: ViewAdminPost
        },
        {
          path: 'post',
          name: 'Post',
          component: Post,
          redirect:'post/viewPostList',
          children: [
            {
              path: 'viewPostList',
              name: 'ViewPostList',
              component: ViewPostList,
            },
            {
              path: 'myPost',
              name: 'MyPost',
              component: MyPost
            },
            {
              path: 'viewPost',
              name: 'ViewPost',
              component: ViewPost
            }]
        },
        {
          path: 'healthQRCode',
          name: 'HealthQRCode',
          component: HealthQRCode
        },
        {
          path: 'viewPartyMember',
          name: 'ViewPartyMember',
          component: ViewPartyMember
        },
        {
          path: 'chat',
          name: 'Chat',
          component: Chat
        }

      ]
    },
  ]
})
router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next();// 访问路径为登录
  const userid = window.sessionStorage.getItem("userid");// session取值
  const adminid = window.sessionStorage.getItem("adminid");
  if (!userid&&!adminid) {
    return next('/login');// 没登录去登录
  }
  else if(!userid&&adminid&&to.path.startsWith("/user")) {
    return next('/login');
  }
  else if(userid&&!adminid&&to.path.startsWith("/admin")) {
    return next('/login');
  }
  else {
    next();
  }
})

export default router
