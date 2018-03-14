<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/1 0001
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/common-easyui.jsp"%>
<html>
<head>
    <script>

        $.extend($.fn.validatebox.defaults.rules, {
            idcardRule: {
                validator: function(value,param){
                    return value.length == param[0];
                },
                message: '请输入正确的身份证号'
            }
        });

        $(function () {
            $("#visitorDatagrid").datagrid({
                title: '访客管理',
                url: '${pageContext.request.contextPath}/visitor/selectVisitorsPage.action',
                toolbar: [
                    {
                        text: '新增访客',
                        iconCls: "icon-add",
                        handler: function () {
                            $("#visitorDialog").dialog({
                                title:"添加房间"
                            });
                            $("#visitorDialog").dialog("open");
                        }
                    },{
                        text: '查找访客',
                        iconCls: "icon-search",
                        handler: function () {
                            console.log("xxxx");
                            $("#cc").layout("expand","north");
                        }
                    },{
                        iconCls: 'icon-reload',
                        text: '刷新',
                        handler: function () {
                            $("#visitorDatagrid").datagrid("reload");
                        }
                    }
                ],
                columns: [[
                    {field: 'name', title: '访客姓名', width: 100},
                    {field: 'visiterId', title: '身份证号', width: 200},
                    {field: 'visitTime', title: '来访时间', width: 200},
                    {field: 'visitStudentName', title: '要找学生姓名', width: 100, align: 'right'},
                    {field: 'phone', title: '手机号', width: 100, align: 'right'},
                    {field: 'content', title: '来访事由', width: 500, align: 'right'}
                ]],
                pagination:true

            });
            $("#confirm").click(function () {
                $('#visitorForm').form("submit",{
                    url:'${pageContext.request.contextPath}/visitor/addVisitor.action',
                    onSubmit:function(){
                        if(!$('#visitorForm').form('validate')){
                            $.messager.show({
                                title:'提示信息' ,
                                msg:'验证没有通过,不能提交表单!'
                            });
                            return false ;		//当表单验证不通过的时候 必须要return false
                        }
                    } ,
                    success:function(result){
                        //关闭对话框
                        $("#visitorDialog").dialog("close");
                        //刷新数据表格
                        $("#dg").datagrid("reload");
                        //清空所选项
                        $("#dg").datagrid("clearSelections");
                        //清空表单
                        $("#visitorForm").form("clear");
                        var result = $.parseJSON(result);
                        $.messager.show({
                            title:result.status ,
                            msg:result.message
                        });
                    }
                });
            });

            $("#cancel").click(function () {
                $("#visitorForm").form("clear");
            });

            $("#btn1").click(function () {
                $('#visitorDatagrid').datagrid('load' ,serializeForm($('#mysearch')));
            });

            $("#btn2").click(function () {
                $("#mysearch").form("clear");
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
                访客姓名：<input  name="name" value="" class="easyui-textbox" >
                访问人：<input  type="text" class="easyui-textbox" name="visitStudentName" />
                到访时间（开始）：<input  type="text" name="startVisitTime" class="easyui-datetimebox" data-options="editable:false">
                到访时间（结束）：<input  type="text" name="endVisitTime" class="easyui-datetimebox" data-options="editable:false">
                <a class="easyui-linkbutton" id="btn1">搜索</a>
                <a class="easyui-linkbutton" id="btn2">清空</a>
            </form>
        </div>
        <div data-options="region:'center',split:true" style="height:100px;">
            <table id="visitorDatagrid"></table>
        </div>
    </div>


    <div id="visitorDialog" style="display:none;">
        <form id="visitorForm"  method="post">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <td>身份证号：</td>
                    <td><input id="visiterId" name="visiterId" value=""class="easyui-textbox" required="true" data-options="validType:'idcardRule[18]'" missingMessage="身份号必填" invalidMessage="身份号必须为18位"></td>
                </tr>
                <tr>
                    <td>访客姓名：</td>
                    <td><input id="name" name="name" value="" class="easyui-textbox" required="true" missingMessage="访客姓名必填"></td>
                </tr>

                <tr>
                    <td>访问人：</td>
                    <td><input id="visitStudentName" type="text" class="easyui-textbox" name="visitStudentName" required="true" missingMessage="必填"/></td>
                </tr>
                <tr>
                    <td>到访时间：</td>
                    <td><input id="visitTime" type="text" name="visitTime" class="easyui-datetimebox" data-options="editable:false" required="true" missingMessage="到访时间必填"></td>
                </tr>
                <tr>
                    <td>手机号码：</td>
                    <td><input id="phone" type="text" name="phone" class="easyui-numberbox" required="true" validType="length[11,11]" missingMessage="手机号必填" invalidMessage="手机号必须为11位数字"></td>
                </tr>
                <tr>
                    <td>到访事由：</td>
                    <td><input id="content" type="text" name="content" class="easyui-textbox" required="true" ></td>
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
