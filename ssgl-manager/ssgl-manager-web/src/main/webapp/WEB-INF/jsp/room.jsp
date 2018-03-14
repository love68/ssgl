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
            $("#dg").datagrid({
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
                                    dormitoryNum:arr[0].dormitoryNum,
                                    score:arr[0].score,
                                    starLevel:arr[0].starLevel,
                                    peopleNum:arr[0].peopleNum,
                                    roomNumber:arr[0].roomNumber,
                                    capacity:arr[0].capacity
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
                            $("#dg").datagrid("reload");
                        }
                    }
                ],
                columns:[[
                    {field:"ck",title:"选择",checkbox:true},
                    {field:'roomNumber',title:'宿舍号',width:100},
                    {field:'capacity',title:'宿舍容量',width:100},
                    {field:'peopleNum',title:'宿舍人数',width:100,align:'right'},
                    {field:'dormitoryNum',title:'宿舍楼号',width:100,align:'right'},
                    {field:'starLevel',title:'宿舍星级',width:100,align:'right'},
                    {field:'score',title:'宿舍评分',width:100,align:'right'},
                ]],
                pagination:true,
                pageSize:5,
                pageList:[5,10,15,20]
            });


            $("#confirm").click(function () {
                $('#roomForm').form("submit",{
                    url:flag == "add" ? '${pageContext.request.contextPath}/room/addRoom.action':'${pageContext.request.contextPath}/room/editRoom.action' ,
                    onSubmit:function(){
                        if(!$('#roomForm').form('validate')){
                            $.messager.show({
                                title:'提示信息' ,
                                msg:'验证没有通过,不能提交表单!'
                            });
                            return false ;		//当表单验证不通过的时候 必须要return false
                        }
                    } ,
                    success:function(result){
                        //关闭对话框
                        $("#roomDialog").dialog("close");
                        //刷新数据表格
                        $("#dg").datagrid("reload");
                        //清空所选项
                        $("#dg").datagrid("clearSelections");
                        //清空表单
                        $("#roomForm").form("clear");
                        var result = $.parseJSON(result);
                        $.messager.show({
                            title:result.status ,
                            msg:result.message
                        });
                    }
                });
            });

            $("#cancel").click(function () {
                $("#roomForm").form("clear");
            });

        });
    </script>
</head>
<body>
    <div>
        <table id="dg"></table>
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
                    <td><input id="dormitoryNum" name="dormitoryNum" value="" ></td>
                    <script>
                        $(function () {
                            var bulidingNo = "";
                            $("#dormitoryNum").combobox({
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
                    <td><input id="peopleNum" type="text" name="peopleNum" class="easyui-numberbox" required="true" missingMessage="实际人数必填"></td>
                </tr>
                <tr>
                    <td>宿舍星级：</td>
                    <td><input id="starLevel" type="text" name="starLevel" class="easyui-numberbox" required="true" missingMessage="星级必填"></td>
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
