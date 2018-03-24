<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/1 0001
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/common-easyui.jsp"%>
<html>
<head>
    <script>
        Date.prototype.toLocaleString = function() {
            return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + "/ " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
        };
        $.extend($.fn.validatebox.defaults.rules, {
            validatePhone: {
                validator: function (value) {
                    return value.length == 11;
                },
                message: "请输入正确的手机号"
            }
        });
        $(function () {

            var flag = "";
            $("#dg").datagrid({
                title: '访客管理',
                fit:true,
                url: '${pageContext.request.contextPath}/selectUsersPage.action',
                toolbar: [
                    {
                        text: '新增管理员',
                        iconCls: "icon-add",
                        handler: function () {
                            flag = "add";
                            $("#userDialog").dialog({
                                title:"添加管理员"
                            });
                            $("#userForm").form("clear");
                            $("#userDialog").dialog("open");
                        }
                    },{
                        text: '查找管理员',
                        iconCls: "icon-search",
                        handler: function () {
                            $("#cc").layout("expand","north");
                        }
                    },{
                        text: '修改信息',
                        iconCls: "icon-edit",
                        handler: function () {
                            flag = "edit";
                            var arr = $("#dg").datagrid("getSelections");
                            var unixTimestamp = new Date(arr[0].birthday) ;
                            commonTime = unixTimestamp.toLocaleString().toString();
                            arr[0].birthday=commonTime.substr(0,commonTime.indexOf(" "));
                            if(arr.length!=1){
                                $.messager.show({
                                    title:"提示信息",
                                    msg:"请选择一行数据操作",
                                    showType:'show'
                                })
                            }else{
                                $("#userDialog").dialog({
                                    title:"修改信息"
                                })
                                $("#userDialog").dialog("open");
                                $("#userForm").form("load",{
                                    id:arr[0].id,
                                    username:arr[0].username,
                                    password:arr[0].password,
                                    birthday:arr[0].birthday,
                                    gender:arr[0].gender,
                                    telephone:arr[0].telephone,
                                    email:arr[0].email,
                                    remark:arr[0].remark
                                });
                            }
                        }
                    },{
                        text:'删除管理员',
                        iconCls:"icon-remove",
                        handler:function () {
                            var arr = $("#dg").datagrid("getSelections");
                            if(arr.length<=0){
                                $.messager.show({
                                    title:"提示信息",
                                    msg:"请至少选择一行删除"
                                })
                            }else {
                                $.messager.confirm("确认信息","确定删除吗？",function (r) {
                                    var ids ="";
                                    if(r){
                                        for(var i = 0;i<arr.length;i++){
                                            ids += arr[i].id + ",";
                                        }
                                        ids = ids.substring(0,ids.length-1);
                                        $.ajax({
                                            url:"${pageContext.request.contextPath}/deleteUsers.action",
                                            type:"post",
                                            data:{ids:ids},
                                            async:true,
                                            cache:false,
                                            dataType:"json",
                                            success:function (r) {
                                                $("#dg").datagrid("reload");
                                                $("#dg").datagrid("clearSelections");
                                                $.messager.show({
                                                    title:r.status,
                                                    msg:r.message
                                                })
                                            },
                                            error:function (r) {
                                                $.messager.show({
                                                    title:r.status,
                                                    msg:r.message
                                                })
                                            }
                                        })
                                    }
                                });
                            }
                        }
                    }
                ],
                columns: [[
                    {field:"ck",title:"选择",checkbox:true},
                    {field: 'username', title: '管理员姓名', width: 100},
                    {field: 'password', title: '密码', width: 100,formatter:function (value,row,index) {
                        return "******";
                    }},
                    {field: 'birthday', title: '生日', width: 100,
                        formatter: function (value, row, index) {
                            var time = new Date(value);
                            return time.toLocaleDateString();
                        }
                    },
                    {field: 'gender', title: '性别', width: 100, align: 'right',
                        formatter: function (value, row, index) {
                            if(value==1){
                               return '男';
                            }else if(value==0){
                                return '<span style=color:red; >女</span>';
                            }
                        }},
                    {field: 'telephone', title: '手机号', width: 100, align: 'right'},
                    {field: 'remark', title: '评价', width: 100, align: 'right'},
                    {field: 'email', title: '邮箱', width: 100, align: 'right'}
                ]],
                pagination:true

            });

            $("#confirm").click(function () {
                $('#userForm').form("submit",{
                    url:flag == "add" ? '${pageContext.request.contextPath}/addUser.action':'${pageContext.request.contextPath}/updateUser.action' ,
                    onSubmit:function(){
                        if(!$('#userForm').form('validate')){
                            $.messager.show({
                                title:'提示信息' ,
                                msg:'验证没有通过,不能提交表单!'
                            });
                            return false ;		//当表单验证不通过的时候 必须要return false
                        }
                    } ,
                    success:function(result){
                        //关闭对话框
                        $("#userDialog").dialog("close");
                        //刷新数据表格
                        $("#dg").datagrid("reload");
                        //清空所选项
                        $("#dg").datagrid("clearSelections");
                        //清空表单
                        $("#userForm").form("clear");
                        var result = $.parseJSON(result);
                        $.messager.show({
                            title:result.status ,
                            msg:result.message
                        });
                    }
                });
            });
            $("#cancel").click(function () {
                $("#userForm").form("clear");
            });
            $("#btn2").click(function () {
                $("#mysearch").form("clear");
            });

            $("#btn1").click(function () {
                $('#dg').datagrid('load' ,serializeForm($('#mysearch')));
            });
            //js方法：序列化表单
            function serializeForm(form){
                var obj = {};
                $.each(form.serializeArray(),function(index){
                    if(obj[this['name']]){
                        obj[this['name']] = obj[this['name']] + ','+this['value'];
                    } else {
                        obj[this['name']] =this['value'];
                    }
                });
                return obj;
            }
        });

    </script>
</head>
<body>

    <div id="cc" class="easyui-layout" style="width:100%;height:100%;">
        <div id="serarchDiv" data-options="region:'north',title:'查询',split:true,collapsed:true"style="height:100px;" >
            <form id="mysearch" method="post">
                用户名：<input name="username" type="text" value="">
                <a class="easyui-linkbutton" id="btn1">搜索</a>
                <a class="easyui-linkbutton" id="btn2">清空</a>
            </form>
        </div>
        <div data-options="region:'center',split:true" style="height:100px;">
            <table id="dg"></table>
        </div>
    </div>

<div id="userDialog" style="display:none;"  modal="true" >
    <form id="userForm"  method="post">
        <input type="hidden" name="id">
        <table>
            <tr>
                <td>用户名：</td>
                <td><input id="username" name="username" class="easyui-textbox" required="true" missingMessage="用户名必填"></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input id="password" name="password" class="easyui-passwordbox" prompt="Password" required="true" missingMessage="密码必填"></td>
            </tr>
            <tr>
                <td>生日：</td>
                <td><input id="birthday" name="birthday" class= "easyui-datebox" required ="required" missingMessage="生日必填"> </td>
            </tr>

            <tr>
                <td>性别：</td>
                <td><input name="gender" type="radio" checked="checked" value="0">女
                    <input name="gender" type="radio" value="1">男</td>
            </tr>
            <tr>
                <td>电话：</td>
                <td><input id="telephone" type="text" name="telephone" class="easyui-numberbox" required="true" validType="length[11,11]" missingMessage="手机号必填" invalidMessage="手机号必须为11位数字" missingMessage="手机号必填" ></td>
            </tr>
            <tr>
                <td>评价：</td>
                <td><input id="remark" type="text" name="remark" class="easyui-textbox" required="true" missingMessage="评价必填"></td>
            </tr>
            <tr>
                <td>邮箱：</td>
                <td><input id="email" type="text" name="email" class="easyui-textbox" data-options="required:true,validType:'email'"></td>
            </tr>
            <tr align="center">
                <td><a id="confirm" class="easyui-linkbutton">确定</a></td>
                <td><a id="cancel" class="easyui-linkbutton">重置</a></td>
            </tr>
        </table>
    </form>
</div>


</body>
</html>

