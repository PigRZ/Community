<template>
  <el-container direction="vertical" style="height: 100%;width: 100%;padding: 0">
    <el-main class="chatContent" ref="chatContent" style="overflow: auto">
      <div v-for="chatMessage in chatMessageList">
        <el-row v-if="chatMessage.userid.toString()!==userid.toString()" style="height: fit-content">
          <el-row style="font-size: 10px;line-height: 10px;text-align: center;margin-bottom: 3px">
            {{ tranTime(chatMessage.time) }}
          </el-row>
          <el-col :span="4" style="line-height: 50px;">
            <img :src="getImgPath(chatMessage)" style="border-radius: 100%;width: 40px;height: 40px;float: top">
          </el-col>
          <el-col :span="20">
            <el-row style="font-size: 13px;line-height: 10px;text-align: left">{{ chatMessage.adminname }}{{ chatMessage.username }}</el-row>
            <el-row class="Left" type="flex">
              {{ chatMessage.content }}
            </el-row>
          </el-col>
        </el-row>

        <el-row v-else style="direction: revert">
          <el-row style="font-size: 10px;line-height: 10px;text-align: center;margin-bottom: 3px">
            {{ tranTime(chatMessage.time) }}
          </el-row>
          <el-col :span="20">
            <el-row style="font-size: 13px;line-height: 10px;text-align: right">{{ chatMessage.username }}</el-row>
            <el-row class="Right" type="flex">
              {{ chatMessage.content }}
            </el-row>
          </el-col>
          <el-col :span="4" style="line-height: 50px;">
            <img :src="getImgPath(chatMessage)" style="border-radius: 100%;width: 40px;height: 40px;float: top">
          </el-col>
        </el-row>
      </div>
    </el-main>
    <el-footer>
      <el-col :span="18">
        <el-input v-model="inputText"></el-input>
      </el-col>
      <el-col :span="6">
        <el-button @click="sendMessage">发送</el-button>
      </el-col>
    </el-footer>
  </el-container>
</template>

<script>
import Vue from 'vue'

export default {
  name: "Chat",
  data() {
    return {
      isReloadData: true,
      url: 'ws://' + Vue.prototype.serverHost + '/chat/',
      ws: null,
      user: null,
      count: 0,
      userid: -1, //当前用户ID
      chatMessageList: [], //聊天记录的数组
      inputText: "" //input输入的值
    }
  },
  methods: {
    sendMessage() {
      let data = {userid: this.userid, content: this.inputText}
      this.$http.post("/user/addTextMessage", data).then(res => {
        if (res.data === 'success') {
          this.sendMsg()
          this.inputText = "";
        } else {
          this.$message.error("发送失败")
        }
      }).catch(error => {
        this.$message.error("发送失败")
      })
    },
    async joinRoom() {
      if (this.ws) {
        alert("你已经在聊天室");
        return;
      }
      this.ws = new WebSocket(this.url + "user/" + this.userid + '/' + this.user.name + '/' + this.user.photo);
      this.ws.onopen = this.webscoketonopen;
      this.ws.onmessage = this.webscoketonmessage;
      //发生错误触发
      this.ws.onerror = function () {
        console.log("连接错误")
      };
      //正常关闭触发
      this.ws.onclose = function () {
        console.log("连接关闭");
      };
    },
    webscoketonopen() {
      console.log("与服务器成功建立连接");

    },
    webscoketonmessage(value) {
      let msg = value.data
      console.log("消息：" + msg);
      let attributes = msg.split("#");
      console.log(attributes)
      var message = {}
      message.sendType = attributes[0];
      message.userid = attributes[1];
      message.username = attributes[2];
      message.photo = attributes[3];
      message.msgType = attributes[4];
      message.time = attributes[5];
      let len = 0;
      for (var i = 0; i <= 5; i++) {
        len += attributes[i].length + 1;
      }
      message.content = msg.substring(len)
      this.chatMessageList.splice(this.chatMessageList.length, 0, message)
      if (message.userid.toString() === this.userid.toString()) {
        this.scrollBottom();
      }
    },
    exitRoom() {
      this.closeWebSocket();
    },
    sendMsg() {
      if (!this.ws) {
        alert('你已经掉线，请重新加入');
        return;
      }
      if (this.ws.readyState === 1) {
        this.ws.send(this.inputText);
        this.inputText = '';
      } else {
        alert('发送失败');
      }
    },
    closeWebSocket() {
      if (this.ws) {
        this.ws.close();
        this.ws = null;
      }
    },
    talking(content) {
      console.log(content);
    },
    tranTime(time) {
      if (time.length > 19) {
        time = time.slice(0, 10) + "  " + time.slice(11, 19);
      }
      return time;
    },
    getImgPath(chatMessage) {
      let photo = chatMessage.photo;
      if (photo === undefined || photo === null || photo === '') {
        return Vue.prototype.serverBaseURL + "image/default.png";
      } else {
        if (chatMessage.sendType === "user")
          return Vue.prototype.serverBaseURL + "/image/user/" + chatMessage.userid + '/' + photo;
        else
          return Vue.prototype.serverBaseURL + "/image/" + photo;
      }
    },
    flushChatMessageList() {
      this.$http.get('/user/getChatMessageList').then(res => {
        this.chatMessageList = res.data;
      })
    },
    //滚动条到底部
    scrollBottom() {
      console.log('滚动到底部')
      // let el = this.$el.getElementsByClassName("msg-box");
      // console.log(el)
      // console.log(el.scrollTop + '----' + el.scrollHeight)
      // el.scrollTop = el.scrollHeight;
      // console.log(el.scrollTop + '----' + el.scrollHeight)
      // this.$nextTick(() =>{
        this.$refs.chatContent.scrollTop = this.$refs.chatContent.scrollHeight;
      // })
    },
  },

  created() {
    this.userid = parseInt(window.sessionStorage.getItem("userid"))
    this.$http.get("/user/getUserById", {params: {id: this.userid}}).then(res => {
      this.user = res.data
      this.joinRoom();
    })
    this.flushChatMessageList();
    // this.scrollBottom()
  },
  mounted() {
    this.scrollBottom()
  }
}
</script>

<style lang="scss" scoped>
html,
body, #app,
.el-container {
  padding: 0px;
  margin: 0px;
  height: 100%;
}

.el-col {
  height: min-content;
}

.el-row .Left {
  border-radius: 10px;
  background-color: #d9d9d9;
  max-width: 70%;
  width: fit-content;
  line-height: 2rem;
  border: thin solid black;
  margin: 10px;
  text-align: left;
  word-break: break-all;
}

.el-row .Right {
  border-radius: 10px;
  background-color: #d9d9d9;
  max-width: 70%;
  width: fit-content;
  line-height: 2rem;
  border: thin solid black;
  margin: 10px;
  text-align: left;
  word-break: break-all;
  float: right;
}

.el-dialog__wrapper {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: auto;
  margin: 0;
}

.el-header, .el-footer {
  flex: 0;
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-main {
  background-color: transparent;
  color: #333;
  padding: 20px 8px 0;
  height: 100%;
  width: 100%;
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
