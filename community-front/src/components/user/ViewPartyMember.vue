<template>
  <el-container direction="vertical" style="">
    <el-main>
    <el-table
      :data="partyMemberList"
      style="width: 100%;"
      class="TH"
    >
      <el-table-column prop="name" label="姓名" width="100">
      </el-table-column>
      <el-table-column prop="telephone" label="电话">
      </el-table-column>
    </el-table>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "ViewPartyMember",
  data() {
    return {
      partyMemberList: [{name:"姓名",telephone:"电话"}],
    }
  },
  created() {
    this.$http.get("user/getPartyMemberList").then(res => {
      this.partyMemberList = res.data;
    })
  }
}
</script>

<style lang="scss">
.el-table__header tr,
.el-table__header th .TH {
  padding: 0;
  line-height: 2rem;
}
</style>
