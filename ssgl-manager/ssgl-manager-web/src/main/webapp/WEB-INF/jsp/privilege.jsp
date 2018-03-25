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
    <title>房间管理</title>
    <script>
        $(function () {


            $("#privilegeDatagrid").datagrid({
                title:'房间管理',
                url:'${pageContext.request.contextPath}/privilege/selectPrivilegePage.action',
                columns:[[
                    {field:'id',title:'权限标识',width:100},
                    {field:'name',title:'权限名称',width:100},
                    {field:'code',title:'权限',width:100,align:'right'},
                    {field:'description',title:'描述',width:100,align:'right'},
                    {field:'page',title:'对应action',width:200,align:'left'},
                    {field:'generatemenu',title:'是否生成菜单',width:100,align:'right'},
                    {field:'pid',title:'父权限id',width:100,align:'right'},
                ]],
                fit:true,
                pagination:true,
                rownumbers:true

            });

        });

    </script>
</head>
<body>
    <div id="cc" style="width:100%;height:100%;">
        <table id="privilegeDatagrid"></table>
    </div>
</body>
</html>
