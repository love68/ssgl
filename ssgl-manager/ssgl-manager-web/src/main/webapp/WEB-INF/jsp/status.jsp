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
    <title>学生情况管理</title>
    <script>
        $(function () {
            $("#sid").validatebox({
                validType:{
                    remote:['${pageContext.request.contextPath}/student/checkStudentSid.action','sid']
                }
            });

            $("#btn1").click(function () {
                $('#dg').datagrid('load' ,serializeForm($('#mysearch')));
            });

            $("#btn2").click(function () {
                $("#mysearch").form("clear");
            });
            $("#cancel").click(function () {
                $("#statusForm").form("clear");
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
            var flag = "";
            $("#statusDialog").dialog({
                title:'添加状态',
                closed:true,
                modal:true,
                draggable:false
            });
            $("#dg").datagrid({
                title:'学生情况管理',
                url:'${pageContext.request.contextPath}/status/selectStatusPage.action',
                toolbar:[
                    {
                        text:'新增',
                        iconCls:"icon-add",
                        handler:function () {
                            flag = "add";
                            $("#statusDialog").dialog({
                                title:"添加"
                            });
                            $("#statusDialog").dialog("open");
                        }
                    },{
                        text:'查找',
                        iconCls:"icon-search",
                        handler:function () {
                            $("#cc").layout("expand","north");
                        }
                    },{
                        text:'刷新',
                        iconCls:"icon-reload",
                        handler:function () {
                            $("#dg").datagrid("reload");
                        }
                    }
                ],
                columns:[[
                    {field:"ck",title:"选择",checkbox:true},
                    {field:'studentid',title:'学号',width:100},
                    {field:'name',title:'学生姓名',width:100},
                    {field:'stateid',title:'状态',width:100,align:'right',
                        formatter: function(value,row,index) {
                            if(value==1){
                                return '正常';
                            }else if(value==2){
                                return "外出"
                            }else{
                                return "请假"
                            }
                        }
                    },
                    {field:'createtime',title:'创建时间',width:100,align:'right'},
                    {field:'adminname',title:'记录人',width:100,align:'right'}
                ]],
                pagination:true,
                fit:true,
                rownumbers:true
            });


            $("#confirm").click(function () {
                $('#statusForm').form("submit",{
                    url:'${pageContext.request.contextPath}/status/addStatus.action' ,
                    onSubmit:function(){
                        if(!$('#statusForm').form('validate')){
                            $.messager.show({
                                title:'提示信息' ,
                                msg:'验证没有通过,不能提交表单!'
                            });
                            return false ;		//当表单验证不通过的时候 必须要return false
                        }
                    } ,
                    success:function(result){
                        //关闭对话框
                        $("#statusDialog").dialog("close");
                        //刷新数据表格
                        $("#dg").datagrid("reload");
                        //清空所选项
                        $("#dg").datagrid("clearSelections");
                        //清空表单
                        $("#statusForm").form("clear");
                        var result = $.parseJSON(result);
                        $.messager.show({
                            title:result.status ,
                            msg:result.message
                        });
                    }
                });
            });
});


    </script>
</head>
<body>
    <div id="cc" class="easyui-layout" style="width:100%;height:100%;">
        <div id="serarchDiv" data-options="region:'north',title:'查询',split:true,collapsed:true"style="height:100px;" >
            <form id="mysearch" method="post">
                姓名：<input name="name" class="easyui-textbox" value="">
                <a class="easyui-linkbutton" id="btn1">搜索</a>
                <a class="easyui-linkbutton" id="btn2">清空</a>
            </form>
        </div>
        <div data-options="region:'center',split:true" style="height:100px;">
            <table id="dg"></table>
        </div>
    </div>

    <div id="statusDialog" style="display:none;">
        <form id="statusForm"  method="post">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <td>学号：</td>
                    <td><input id="studentid" name="studentid" class="easyui-validatebox" value="" required="true" missingMessage="学号必填"></td>
                </tr>
                <tr>
                    <td>学生姓名：</td>
                    <td><input id="name" name="name" value="" class="easyui-textbox"></td>
                </tr>

                <tr>
                    <td>创建时间：</td>
                    <td><input id="createtime" type="text" name="createtime" class="easyui-datetimebox" required="true" missingMessage="时间必填"/></td>
                </tr>
                <tr>
                    <td>学生情况：</td>
                    <td><input id="stateid" type="text" prompt="1:正常,2外出,3:请假" name="stateid" class="easyui-textbox" required="true" missingMessage="学生情况必填"></td>
                </tr>
                <tr>
                    <td>记录人：</td>
                    <td><input id="adminnum" type="text" name="adminname" value="${loginUser.username}" class="easyui-textbox" required="true" missingMessage="记录人必填"></td>
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
