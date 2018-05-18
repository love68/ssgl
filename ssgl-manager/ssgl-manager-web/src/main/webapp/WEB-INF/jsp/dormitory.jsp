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
    <title>宿舍楼管理</title>
    <script>
        $(function () {
            var flag = "";
            $("#dormitoryDialog").dialog({
                title:'添加宿舍楼',
                closed:true,
                modal:true,
                draggable:false

            });

            $("#dg").datagrid({
                idField:"buildingNo",
                title:'宿舍楼管理',
                fit:true,
                pagination:true,
                pageSize:5,
                pageList:[5,10,15,20],
                url:"${pageContext.request.contextPath}/dormitory/selectAllDormitories.action",
                rownumbers:true,
                columns:[[
                    {field:"ck",title:"选择",checkbox:true},
                    {field:"buildingNo",title:"宿舍楼号"},
                    {field:"students",title:"学生总数"},
                    {field:"rooms",title:"房间总数"},
                    {field:"surplus",title:"剩余房间总数"},
                    {field:"floors",title:"楼层数"}
                ]],
                toolbar:[
                    {
                        text:'添加宿舍楼',
                        iconCls: 'icon-add',
                        handler: function(){
                            flag = "add";
                            $("#dormitoryDialog").dialog({
                                title:"添加宿舍楼"
                            });
                            $("#dormitoryForm").form("clear");
                            $("#dormitoryDialog").dialog("open");
                        }
                    },{
                        text:'删除宿舍楼',
                        iconCls: 'icon-remove',
                        handler: function(){
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
                                            url:"${pageContext.request.contextPath}/dormitory/deleteDormitories.action",
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
                                                console.log(r);
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
                        text:'修改宿舍楼',
                        iconCls: 'icon-edit',
                        handler: function(){
                            flag = "edit";
                            var arr = $("#dg").datagrid("getSelections");
                            if(arr.length!=1){
                                $.messager.show({
                                    title:"提示信息",
                                    msg:"请选择一行数据操作"
                                })
                            }else{
                                $("#dormitoryDialog").dialog({
                                    title:"修改宿舍楼"
                                })
                                $("#dormitoryDialog").dialog("open");
                                $("#dormitoryForm").form("load",{
                                    id:arr[0].id,
                                    buildingNo:arr[0].buildingNo,
                                    students:arr[0].students,
                                    rooms:arr[0].rooms,
                                    floors:arr[0].floors,
                                    surplus:arr[0].surplus
                                });
                            }
                        }
                    },{
                        text:'查找宿舍楼',
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
                    },{
                        iconCls: 'icon-print',
                        text: '导出',
                        handler: function () {
                            window.location.href="${pageContext.request.contentType}/dormitory/exportDormitory.action";
                        }
                    }
                ]
            });

           $("#confirm").click(function () {
                $('#dormitoryForm').form("submit",{
                    url:flag == "add" ? '${pageContext.request.contextPath}/dormitory/addDormitory.action':'${pageContext.request.contextPath}/dormitory/editDormitory.action' ,
                    onSubmit:function(){
                        if(!$('#dormitoryForm').form('validate')){
                            $.messager.show({
                                title:'提示信息' ,
                                msg:'验证没有通过,不能提交表单!'
                            });
                            return false ;		//当表单验证不通过的时候 必须要return false
                        }
                    } ,
                    success:function(result){
                        $("#dormitoryDialog").dialog("close");
                        $("#dg").datagrid("clearSelections");
                        $("#dg").datagrid("reload");
                        $("#dormitoryForm").form("clear");
                        var result = $.parseJSON(result);
                        $.messager.show({
                            title:result.status ,
                            msg:result.message
                        });
                    }
                });
            });

            $("#cancel").click(function () {
                $("#dormitoryForm").form("clear");
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
    <div id="cc" class="easyui-layout" style="width:100%;height: 85%;">
        <div id="serarchDiv" data-options="region:'north',title:'查询',split:true,collapsed:true" style="height: 100px;">
            <form id="mysearch" method="post">
                宿舍楼号：<input id="building_no1" name="buildingNo" value="">
                <script>
                    $(function () {
                        var bulidingNo = "";
                        $("#building_no1").combobox({
                            url:'${pageContext.request.contextPath}/dormitory/findAllDormitories.action',
                            valueField:'buildingNo',
                            textField:'buildingNo'
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

    <div id="dormitoryDialog">
        <form id="dormitoryForm"  method="post">
            <input type="hidden" name="id">
            <table>
                <tr>
                    <td>宿舍楼号：</td>
                    <td><input id="buildingNo" type="text" name="buildingNo" class="easyui-numberbox" required="true" validType="length[1,1]" missingMessage="楼号必填" invalidMessage="楼号必须为1位"></td>
                </tr>
                <tr>
                    <td>学生总数：</td>
                    <td><input id="students" type="text" name="students" class="easyui-numberbox" /></td>
                </tr>
                <tr>
                    <td>房间总数：</td>
                    <td><input id="rooms" type="text" name="rooms" class="easyui-numberbox" required="true" missingMessage="房间总数必填"></td>
                </tr>
                <tr>
                    <td>楼层数：</td>
                    <td><input id="floors" type="text" name="floors" class="easyui-numberbox" required="true" missingMessage="楼层数必填"></td>
                </tr>
                <tr>
                    <td>剩余房间数：</td>
                    <td><input id="surplus" type="text" name="surplus" class="easyui-numberbox" value="" required="true" missingMessage="剩余房间数必填"></td>
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
