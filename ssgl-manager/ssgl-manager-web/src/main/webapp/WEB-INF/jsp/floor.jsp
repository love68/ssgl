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
    <title>楼层管理</title>
    <script>
        $(function () {
            var flag = "";
            $("#floorDialog").dialog({
                title:'添加楼层',
                closed:true,
                modal:true,
                draggable:false
            });
            $("#dg").datagrid({
                idField:"id",
                title:'楼层管理',
                url:"${pageContext.request.contextPath}/floor/selectFloorDormitories.action",
                rownumbers:true,
                columns:[[
                    {field:"ck",title:"选择",checkbox:true},
                    {field:"buildingNo",title:"宿舍楼号"},
                    {field:"layer",title:"楼层"},
                    {field:"students",title:"学生总数"},
                    {field:"roomNumber",title:"房间总数"},
                    {field:"spaces",title:"剩余房间总数"}
                ]],
                toolbar:[
                    {
                        text:'添加楼层',
                        iconCls: 'icon-add',
                        handler: function(){
                            flag = "add";
                            $("#floorDialog").dialog({
                                title:"添加楼层"
                            });
                            $("#floorForm").form("clear");
                            $("#floorDialog").dialog("open");
                        }
                    },{
                        text:'删除楼层',
                        iconCls: 'icon-remove',
                        handler: function() {
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
                                            url:"${pageContext.request.contextPath}/floor/deleteFloors.action",
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
                        text:'修改楼层',
                        iconCls: 'icon-edit',
                        handler: function(){
                            flag = "edit";
                            var arr = $("#dg").datagrid("getSelections");
                            if(arr.length!=1){
                                $.messager.show({
                                    title:"提示信息",
                                    msg:"请选择一行数据操作",
                                    showType:'show'
                                })
                            }else{
                                $("#floorDialog").dialog({
                                    title:"修改楼层"
                                })
                                $("#floorDialog").dialog("open");
                                $("#floorForm").form("load",{
                                    building_no:arr[0].buildingNo,
                                    layer:arr[0].layer,
                                    students:arr[0].students,
                                    roomNumber:arr[0].roomNumber,
                                    spaces:arr[0].spaces
                                });
                            }
                        }
                    },{
                        text:'查找楼层',
                        iconCls: 'icon-search',
                        handler: function(){
                            $("#cc").layout("expand","north");
                        }
                    },{
                        text:'刷新',
                        iconCls: 'icon-reload',
                        handler: function(){
                            $("#dg").datagrid("reload");
                        }
                    }
                ],
                pagination:true,
                pageSize:5,
                pageList:[5,10,15,20]
            });

           $("#confirm").click(function () {
                $('#floorForm').form("submit",{
                    url:flag == "add" ? '${pageContext.request.contextPath}/floor/addFloor.action':'${pageContext.request.contextPath}/floor/editFloor.action' ,
                    onSubmit:function(){
                        if(!$('#floorForm').form('validate')){
                            $.messager.show({
                                title:'提示信息' ,
                                msg:'验证没有通过,不能提交表单!'
                            });
                            return false ;		//当表单验证不通过的时候 必须要return false
                        }
                    } ,
                    success:function(result){
                        $("#floorDialog").dialog("close");
                        $("#dg").datagrid("clearSelections");
                        $("#dg").datagrid("reload");
                        $("#floorForm").form("clear");
                        var result = $.parseJSON(result);
                        $.messager.show({
                            title:result.status ,
                            msg:result.message
                        });
                    }
                });
            });
            $("#btn1").click(function () {
                $('#dg').datagrid('load' ,serializeForm($('#mysearch')));
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
            $("#cancel").click(function () {
                $("#floorForm").form("clear");
            });
        });
    </script>
</head>
<body>
<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
    <div id="serarchDiv" data-options="region:'north',title:'查询',split:true,collapsed:true"style="height:100px;" >
        <form id="mysearch" method="post">
            宿舍楼号：<input id="building_no1" name="building_no" value="" >
            <script>
                $(function () {
                    var bulidingNo = "";
                    $("#building_no1").combobox({
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

            <a class="easyui-linkbutton" id="btn1">搜索</a>
            <a class="easyui-linkbutton" id="btn2">清空</a>
        </form>
    </div>
    <div data-options="region:'center',split:true" style="height:100px;">
        <table id="dg"></table>
    </div>
</div>
    <div id="floorDialog">
        <form id="floorForm"  method="post">
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
                    <%--<td>
                        <select name="layer">
                            <c:forEach var="layer" begin="0" end="6" step="1">
                                <option value="layer">${layer}</option>
                            </c:forEach>
                        </select>
                    </td>--%>
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
                    <td><a id="cancel" class="easyui-linkbutton">重置</a></td>
                </tr>
            </table>
        </form>
    </div>

</body>
</html>
