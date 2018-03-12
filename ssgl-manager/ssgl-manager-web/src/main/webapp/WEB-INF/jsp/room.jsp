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

            var flag = "";
            $("#roomDialog").dialog({
                title:'添加楼层',
                closed:true,
                modal:true,
                draggable:false
            });
            $("#roomDatagrid").datagrid({
                title:'房间管理',
                url:'${pageContext.request.contextPath}/room/selectRoomsPage.action',
                toolbar:[
                    {
                        text:'新增房间',
                        iconCls:"icon-add",
                        handler:function () {
                            flag = "add";
                            $("#roomDialog").dialog({
                                title:"添加房间"
                            });
                            $("#roomDialog").dialog("open");
                        }
                    },{
                        text:'删除房间',
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
                                            url:"${pageContext.request.contextPath}/room/deleteRooms.action",
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
                    },{
                        text:'修改房间',
                        iconCls:"icon-edit",
                        handler:function () {
                            flag = "edit";
                            var arr = $("#dg").datagrid("getSelections");
                            if(arr.length!=1){
                                $.messager.show({
                                    title:"提示信息",
                                    msg:"请选择一行数据操作",
                                    showType:'show'
                                })
                            }else{
                                $("#roomDialog").dialog({
                                    title:"修改楼层"
                                })
                                $("#roomDialog").dialog("open");
                                $("#roomForm").form("load",{
                                    building_no:arr[0].buildingNo,
                                    layer:arr[0].layer,
                                    students:arr[0].students,
                                    roomNumber:arr[0].roomNumber,
                                    spaces:arr[0].spaces
                                });
                            }
                        }
                    },{
                        text:'查找房间',
                        iconCls:"icon-search",
                        handler:function () {

                        }
                    },{
                        text:'刷新',
                        iconCls:"icon-reload",
                        handler:function () {
                            $("#roomDatagrid").datagrid("reload");
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
                ]],
                pagination:true
            });

        });

    </script>
</head>
<body>
    <div>
        <table id="roomDatagrid"></table>
    </div>

    <div id="roomDialog" style="display:none;">
        <form id="roomForm"  method="post">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <td>宿舍楼号：</td>
                    <td><input id="building_no" name="building_no" value="" ></td>
                    <script>
                        $(function () {
                            var bulidingNo = "";
                            $("#building_no").combobox({
                                url:'${pageContext.request.contextPath}/dormitory/findAllDormitories.action',
                                valueField:'buildingNo',
                                textField:'buildingNo',
                                onChange:function (newValue, oldValue) {
                                    bulidingNo = newValue;
                                    $("#layer").combobox({
                                        url:'${pageContext.request.contextPath}/floor/getLayers.action?buildingNo='+bulidingNo,
                                        valueField:'buildingNo',
                                        textField:'buildingNo'
                                    });
                                }
                            });

                        });
                    </script>
                </tr>
                <tr>
                    <td>楼层号：</td>
                    <td><input id="layer" name="layer" class="easyui-combobox" value="" required="true" validType="length[1,1]" missingMessage="楼号必填" invalidMessage="楼层号必须为1位"></td>
                </tr>
                <tr>
                    <td>学生总数：</td>
                    <td><input id="students" type="text" name="students" class="easyui-numberbox" /></td>
                </tr>
                <tr>
                    <td>房间总数：</td>
                    <td><input id="room_number" type="text" name="roomNumber" class="easyui-numberbox" required="true" missingMessage="房间总数必填"></td>
                </tr>
                <tr>
                    <td>剩余房间数：</td>
                    <td><input id="spaces" type="text" name="spaces" class="easyui-numberbox" required="true" missingMessage="剩余总数必填"></td>
                </tr>
                <tr align="center">
                    <td><a id="confirm" class="easyui-linkbutton">确定</a></td>
                    <td><a id="cancel" class="easyui-linkbutton">取消</a></td>
                </tr>
            </table>
        </form>
    </div>

</body>
</html>
