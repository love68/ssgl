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


            $("#roomDatagrid").datagrid({
                title:'房间管理',
                url:'${pageContext.request.contextPath}/room/selectRoomsPage.action',
                toolbar:[
                    {
                        text:'新增房间',
                        iconCls:"icon-add",
                        handler:function () {

                        }
                    },{
                        text:'删除房间',
                        iconCls:"icon-remove",
                        handler:function () {

                        }
                    },{
                        text:'编辑房间',
                        iconCls:"icon-edit",
                        handler:function () {

                        }
                    },{
                        text:'查找房间',
                        iconCls:"icon-search",
                        handler:function () {

                        }
                    }
                ],
                columns:[[
                    {field:'roomNumber',title:'宿舍号',width:100},
                    {field:'capacity',title:'宿舍容量',width:100},
                    {field:'peopleNum',title:'宿舍人数',width:100,align:'right'},
                    {field:'dormitoryNum',title:'宿舍楼号',width:100,align:'right'},
                    {field:'starLevel',title:'宿舍星级',width:100,align:'right'},
                    {field:'score',title:'宿舍评分',width:100,align:'right'},
                ]]

            });

        });

    </script>
</head>
<body>
    <div>
        <table id="roomDatagrid"></table>
    </div>
</body>
</html>
