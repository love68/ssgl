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
        $(function () {

            var flag = "";
            $("#visitorDatagrid").datagrid({
                title: '访客管理',
                url: '${pageContext.request.contextPath}/user/selectUsersPage.action',
                toolbar: [
                    {
                        text: '新增管理员',
                        iconCls: "icon-add",
                        handler: function () {
                            flag = "add";
                            $("#userDialog").dialog({
                                title:"添加管理员"
                            });
                            $("#userDialog").dialog("open");
                        }
                    },{
                        text: '查找访客',
                        iconCls: "icon-search",
                        handler: function () {

                        }
                    },{
                        text:'删除',
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
                                            url:"${pageContext.request.contextPath}/user/deleteUsers.action",
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
                    {field: 'name', title: '访客姓名', width: 100},
                    {field: 'visiterId', title: '身份证号', width: 100},
                    {field: 'visitTime', title: '来访时间', width: 100},
                    {field: 'visitStudentName', title: '要找学生姓名', width: 100, align: 'right'},
                    {field: 'phone', title: '手机号', width: 100, align: 'right'},
                    {field: 'content', title: '来访事由', width: 100, align: 'right'}
                ]]

            });

        });

    </script>
</head>
<body>
<div>
    <table id="visitorDatagrid"></table>
</div>

<div id="roomDialog" style="display:none;">
    <form id="roomForm"  method="post">
        <input type="hidden" name="id">
        <table>
            <tr>
                <td>宿舍号：</td>
                <td><input id="roomNumber" name="roomNumber" class="easyui-numberbox" value="" required="true" validType="length[4,4]" missingMessage="宿舍号必填" invalidMessage="宿舍号必须为4位"></td>
            </tr>
            <tr>
                <td>宿舍楼号：</td>
                <td><input id="building_no" name="building_no" value="" ></td>
                <script>
                    $(function () {
                        var bulidingNo = "";
                        $("#building_no").combobox({
                            url:'${pageContext.request.contextPath}/dormitory/findAllDormitories.action',
                            valueField:'buildingNo',
                            textField:'buildingNo'
                        });
                    });
                </script>
            </tr>

            <tr>
                <td>宿舍容量：</td>
                <td><input id="capacity" type="text" name="capacity" class="easyui-numberbox" required="true" missingMessage="宿舍容量"/></td>
            </tr>
            <tr>
                <td>实际人数：</td>
                <td><input id="people_num" type="text" name="people_num" class="easyui-numberbox" required="true" missingMessage="实际人数必填"></td>
            </tr>
            <tr>
                <td>宿舍星级：</td>
                <td><input id="star_level" type="text" name="star_level" class="easyui-numberbox" required="true" missingMessage="星级必填"></td>
            </tr>
            <tr>
                <td>宿舍分数：</td>
                <td><input id="score" type="text" name="score" class="easyui-numberbox" ></td>
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

