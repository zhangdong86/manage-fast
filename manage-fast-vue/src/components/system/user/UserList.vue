<!--
系统用户 @author lijianan @email 4141914@gmail.com @date 2018-04-14 10:05:24
-->
<template>
  <div>
    <el-form :inline="true">
      <el-form-item label="用户名">
        <el-input placeholder="请输入用户名" size="small" v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input placeholder="请输入邮箱" size="small" v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input placeholder="请输入手机号" size="small" v-model="form.mobile"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="search" @click="refresh" title="根据输入的条件查询" size="small">查询</el-button>
        <el-button type="primary" icon="plus" @click="doAdd()" title="添加" size="small">添加</el-button>
      </el-form-item>
    </el-form>
    <el-table border :data="dataList" v-loading="loading" element-loading-text="正在加载......" style="width: 100%">
      <el-table-column prop="id" label="id"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="mobile" label="手机号"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template slot-scope="props">
          {{props.row.status|user_status_format}}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间">
        <template slot-scope="props">
          {{props.row.createTime|date_time_format}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template slot-scope="props">
          <div>
            <el-button type="text" @click="doEdit(props.row)">编辑</el-button>
            <el-button type="text" @click="doDelete(props.row)">删除</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <br/>
    <div style="text-align: right" v-if="total > 0">
      <el-pagination background layout="prev, pager, next" :current-page="page" :total="total"
                     @current-change="(curr) => {this.page = curr ; this.refresh();}"></el-pagination>
    </div>
    <UserDialog ref="dialog" :refresh="refresh"></UserDialog>
  </div>
</template>
<script>
  import UserDialog from './UserDialog.vue';

  export default {
    data: function () {
      return {
        total: 0,
        page: 1,
        dataList: [],
        form: {
          id: null,
          username: null,
          password: null,
          salt: null,
          email: null,
          mobile: null,
          status: null,
          createUserId: null,
          createTime: null,

        },
        loading: false
      }
    },
    computed: {},
    created: function () {
      this.refresh();
    },
    methods: {
      refresh() {
        const that = this;
        that.loading = true;
        const requestData = {...that.form, page: that.page - 1};
        that.$http.post("/api/user/queryPage", JSON.stringify(requestData)).then(res => {
          that.loading = false;
          that.dataList = res.data.content;
          that.total = res.data.totalSize;
        }).catch(res => {
          that.$message.error("获取系统用户列表失败：" + res);
          that.loading = false;
        });
      },
      doAdd() {
        this.$refs["dialog"].addDialog();
      },
      doEdit(row) {
        this.$refs["dialog"].editDialog(row);
      },
      doDelete(row) {
        const that = this;
        this.$confirm('你确定要删除吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          that.$http.delete("/api/user/delete", {
            params: {ids: [row.id]}
          }).then(res => {
            this.$message.success("删除成功");
            that.refresh();
          }).catch(res => {
            that.$message.error("删除失败：" + res);
          });
        }).catch(res => {
          console.log(res);
        });
      }
    },
    components: {
      UserDialog
    }
  }
</script>
<style>

</style>
