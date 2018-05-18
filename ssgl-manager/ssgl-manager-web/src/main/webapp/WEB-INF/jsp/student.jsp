<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/30 0030
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/common-easyui.jsp" %>
<html>
<head>
    <title>学生管理</title>
    <script>

        Date.prototype.toLocaleString = function() {
            return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + "/ " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
        };

        var flag ="";
        $.extend($.fn.validatebox.defaults.rules, {
            validateSid: {
                validator: function (value) {
                    return value.length == 10;
                },
                message: '学号必须为10位'
            },
            validateRoomNumber: {
                validator: function (value) {
                    return value.length == 4;
                },
                message: "宿舍号必须是4位"
            },
            validateDormitoryNo: {
                validator: function (value) {
                    return value.length == 1 && value != '0';
                },
                message: "宿舍号必须是1位且不能为0"
            },
            validateBedNo: {
                validator: function (value) {
                    return value.length == 1 && value != '0' && value != '9';
                },
                message: "床号必须是1位且不能为0"
            },
            validateAddress: {
                validator: function (value) {
                    return value.trim().length > 0;
                },
                message: "请正确的输入家庭住址"
            },
            validateName: {
                validator: function (value) {
                    return value.trim().length > 0;
                },
                message: "请输入正确的姓名"
            },
            validatePhone: {
                validator: function (value) {
                    return value.length == 11;
                },
                message: "请输入正确的手机号"
            }
        });

        $(function () {
            $("#entranceTime").datebox({
                required: true,
                editable:false,
                missingMessage: '入学时间必填'
            });
            $("#entranceTime1").datebox({
                editable:false
            });
            $("#graduateTime").datebox({
                required: true,
                editable:false,
                missingMessage: '毕业时间必填'
            });
            $("#graduateTime1").datebox({
                editable:false
            });

            $("#roomNumber").combobox({
                required: true,
                missingMessage: "宿舍号必填"
            });
            $("#dormitoryNo").combobox({
                required: true,
                missingMessage: "楼号必填",
                url:'${pageContext.request.contextPath}/dormitory/findAllDormitories.action',
                valueField:'buildingNo',
                textField:'buildingNo',
                onChange:function (newValue, oldValue) {
                    var bulidingNo = newValue;
                    $("#roomNumber").combobox({
                        url:'${pageContext.request.contextPath}/room/getRooms.action?buildingNo='+bulidingNo,
                        valueField:'roomNumber',
                        textField:'roomNumber'
                    });
                }
            });
            $("#bedNo").numberbox({
                required: true,
                missingMessage: "床号必填",
                validType: 'validateBedNo'
            }),

            $("#province").combobox({
                required: true,
                url:"${pageContext.request.contextPath}/province/selectProvinces.action",
                valueField:"provinceid",
                textField:"province",
                missingMessage:"省份必填！",
                onSelect:function (record) {
                    var provinceid=record.provinceid;
                    $("#city").combobox("clear");
                    $("#county").combobox("clear");
                    $("#city").combobox("reload","${pageContext.request.contextPath}/city/selectCitiesByProvinceId.action?provinceid="+provinceid);
                }
            }),
            $("#city").combobox({
                required: true,
                valueField:"cityid",
                textField:"city",
                missingMessage:"市区必填！",
                onSelect:function (record) {
                    var cityid = record.cityid;
                    $("#county").combobox("clear");
                    $("#county").combobox("reload","${pageContext.request.contextPath}/city/selectCountiesByCityId.action?cityid="+cityid);
                }
            }),
            $("#county").combobox({
                required: true,
                valueField:"areaid",
                textField:"area",
                missingMessage:"所属县必填！"
            }),

            $("#faculty").combobox({
                required:true,
                valueField:"facultyid",
                textField:"faculty",
                missingMessage:"学院必填",
                url:"${pageContext.request.contextPath}/faculty/selectAllFaculties.action",
                onSelect:function (record) {
                    var faultyid = record.facultyid;
                    $("#profess").combobox("clear");
                    $("#profess").combobox("reload","${pageContext.request.contextPath}/faculty/selectAllFacultiesByFacultyId.action?facultyid="+faultyid);
                }
            }),
                $("#faculty1").combobox({
                valueField:"facultyid",
                textField:"faculty",
                url:"${pageContext.request.contextPath}/faculty/selectAllFaculties.action",
                onSelect:function (record) {
                    var faultyid = record.facultyid;
                    $("#profess").combobox("clear");
                    $("#profess").combobox("reload","${pageContext.request.contextPath}/faculty/selectAllFacultiesByFacultyId.action?facultyid="+faultyid);
                }
            }),
            $("#profess,#profess1").combobox({
                required:true,
                valueField:"facultyid",
                textField:"faculty",
                missingMessage:"专业必填"
            }),

            $("#phone").numberbox({
                required: true,
                missingMessage: "手机号码必填",
                validType: 'validatePhone'
            }),
            $("#homePhone").numberbox({
                required: true,
                missingMessage: "家庭号码必填",
                validType: 'validatePhone'
            }),

            $("#dg").datagrid({
                title:"学生管理",
                url: '${pageContext.request.contextPath}/student/selectStudentsPage.action',
                fit:true,
                pagination:true,
                frozenColumns:[[{field:"ck",title:"选择",checkbox:true},{field:'sid',title:'学号',width:100}]],
                columns:[[
                    {field:'name',title:'姓名',width:60},
                    {field:'address',title:'地址',width:140,align:'right'},
                    {field:'age',title:'年龄',width:40,align:'right'},
                    {field:'dormitoryNo',title:'宿舍楼号',width:60,align:'center'},
                    {field:'duty',title:'职务',width:60,align:'center'},
                    {field:'graduateTime',title:'毕业时间',width:100,align:'right',
                        formatter: function (value, row, index) {
                            var time = new Date(value);
                            return time.toLocaleDateString();
                        }},
                    {field:'homePhone',title:'家庭电话',width:100,align:'right'},
                    {field:'phone',title:'电话',width:100,align:'right'},
                    {field:'entranceTime',title:'入学时间',width:100,align:'right'},
                    {field:'faculty',title:'院系',width:100,align:'right',
                        formatter: function(value,row,index){
                            if (value==1){
                                return "计算机工程系";
                            } else if(value==2){
                                return "数学系";
                            }else if(value==3){
                                return "外语系";
                            }else if(value==4){
                                return "经济管理系";
                            }else if(value==5){
                                return "物理系";
                            }else if(value==6){
                                return "中文系";
                            }else if(value==7){
                                return "中语系";
                            }else if(value==8){
                                return "化学工程系";
                            }else if(value==9){
                                return "初等教育学院";
                            }else if(value==10){
                                return "体育系";
                            }else if(value==11){
                                return "音乐系";
                            }else if(value==12){
                                return "美术系";
                            }
                        }},
                    {field:'isGraduate',title:'是否毕业',width:80,align:'center',
                        formatter: function(value,row,index){
                            if (value==true){
                                return "是";
                            } else if(value==false){
                                return "否";
                            }
                        }
                    },
                    {field:'isUndergraduate',title:'是否本科',width:80,align:'center',
                        formatter: function(value,row,index){
                            if (value==true){
                                return "是";
                            } else if(value==false){
                                return "否";
                            }
                        }},
                    {field:'phone',title:'电话',width:100,align:'right'},
                    {field:'roomNumber',title:'宿舍号',width:80,align:'center'},
                    {field:'sex',title:'性别',width:60,align:'center',
                        formatter: function(value,row,index){
                            if (value==true){
                                return "男";
                            } else if(value==false){
                                return "女";
                            }
                        }},
                    {field:'bedNo',title:'床位',width:40,align:'center'},
                    {field:'icon',title:'头像',width:120,align:'right'},
                ]],
                toolbar: [
                    {
                        iconCls: 'icon-add',
                        text: '添加学生',
                        handler: function () {
                            flag = "add";
                            $("#mydialog").dialog({
                                title:"添加学生"
                            });
                            $("#mydialog").dialog('open');
                        }
                    },{
                        iconCls: 'icon-remove',
                        text: '删除学生',
                        handler: function () {
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
                                            url:"${pageContext.request.contextPath}/student/deleteStudent.action",
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
                    }, {
                        iconCls: 'icon-search',
                        text: '查找学生',
                        handler: function () {
                            $("#cc").layout("expand","north");
                        }
                    }, {
                        iconCls: 'icon-reload',
                        text: '刷新',
                        handler: function () {
                            $("#dg").datagrid("reload");
                        }
                    }, {
                        iconCls: 'icon-print',
                        text: '导出',
                        handler: function () {
                            window.location.href="${pageContext.request.contentType}/student/exportStudent.action?name="+$("#name1").val()+"&sid="+$("#sid1").val()+"&sex="+$("#sex1").val()+"&age="+$("#age1").val()+"&entranceTime="+$("#entranceTime1").val()+"&graduateTime="+$("#graduateTime1").val()+"&faculty="+$("#faculty1").val()+"&roomNumber="+$("#roomNumber1").val()+"&duty="+$("#duty1").val();
                        }
                    }, {
                        iconCls: 'icon-print',
                        text: '交换宿舍',
                        handler: function () {
                            var ids = "";
                           var arr = $("#dg").datagrid("getSelections");
                           if(arr.length==2){
                               for(var i = 0;i<arr.length;i++){
                                   ids += arr[i].id + ",";
                               }
                               ids = ids.substring(0,ids.length-1);
                                $.ajax({
                                    url:"${pageContext.request.contextPath}/student/changeStudentRoom.action",
                                    type:"post",
                                    data:{ids:ids},
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
                           }else{
                               $.messager.show({
                                   title:'提示信息',
                                   msg:'请选择两个学生',
                                   timeout:5000,
                                   showType:'slide'
                               });
                            }
                        }
                    }
                ],
                onDblClickRow:function (index, row) {
                    var jq = top.jQuery;
                    var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/student/selectStudentInfo.action?id=" + row.id + "'></iframe>";
                    jq("#tt").tabs('add',{
                        title:"学生详细信息",
                        content:content,
                        closable:true
                    });
                }
            });


            $("#btn2").click(function () {
                $("#mysearch").form("clear");
            });

            $("#btn1").click(function () {
                $('#dg').datagrid('load' ,serializeForm($('#mysearch')));
            });

            $("#confirm").click(function () {
                $('#studentForm').form("submit",{
                    url:flag == "add" ? '${pageContext.request.contextPath}/student/addStudent.action':'${pageContext.request.contextPath}/student/editStudent.action' ,
                    onSubmit:function(){
                        if(!$('#studentForm').form('validate')){
                            $.messager.show({
                                title:'提示信息' ,
                                msg:'验证没有通过,不能提交表单!'
                            });
                            return false ;		//当表单验证不通过的时候 必须要return false
                        }
                    } ,
                    success:function(result){
                        //关闭对话框
                        $("#mydialog").dialog("close");
                        //刷新数据表格
                        $("#dg").datagrid("reload");
                        //清空所选项
                        $("#dg").datagrid("clearSelections");
                        //清空表单
                        $("#studentForm").form("clear");
                        var result = $.parseJSON(result);
                        $.messager.show({
                            title:result.status ,
                            msg:result.message
                        });
                    }
                });
            });
            $("#cancel").click(function () {
                $("#studentForm").form("clear");
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

    <div id="cc" class="easyui-layout" style="width:100%;height:530px;">
        <div id="serarchDiv" data-options="region:'north',title:'查询',split:true,collapsed:true"style="height:100px;" >
            <form id="mysearch" method="post">
                姓名：<input id="name1"name="name1" type="text" value="" class="easyui-textbox">
                学号：<input id="sid1" name="sid" type="text" value="" class="easyui-textbox">
                宿舍号：<input id="roomNumber1" name="roomNumber" class="easyui-numberbox" value="">
                年龄：<input id="age1" name="age" type="text" value="" class="easyui-numberbox"/>
                性别 ：
                <select id="sex1" class="easyui-combobox" name="sex" style="width:200px;">
                    <option value="-1">请选择</option>
                    <option value="0">女</option>
                    <option value="1">男</option>
                </select>
                <br/>
                入学时间：
                   <input id="entranceTime1" type="text" name="entranceTime" value="">
                毕业时间：
                    <input id="graduateTime1" type="text" name="graduateTime" value="">
                职务：<input id="duty1"type="text" name="duty" value="" class="easyui-textbox">
                院系：
                    <input id="faculty1" name="faculty" value="" style="width: 70px;">
                <a class="easyui-linkbutton" id="btn1">搜索</a>
                <a class="easyui-linkbutton" id="btn2">清空</a>
            </form>
        </div>
        <div data-options="region:'center',split:true" style="height:100px;">
            <table id="dg"></table>
        </div>
    </div>

<div style="width:380px;height:480px;background: url('../../images/form.jpg')" id="mydialog" class="easyui-dialog"
     modal="true" closed="true" title="添加学生">
    <form id="studentForm" action="" method="post" enctype="multipart/form-data">
        <table align="center">
            <tr>
                <td>学号：</td>
                <td><input id="sid" type="text" name="sid" value=""
                           class="easyui-numberbox"
                           data-options="required:true,
                       missingMessage:'学号为必选项！',
                       validType:'validateSid'"></td>
            </tr>
            <tr>
                <td>姓名：</td>
                <td><input id="name" name="name" type="text" value="" class="easyui-textbox" required="true"
                           missingMessage="学生姓名必填！" validType="validateName"></td>
            </tr>
            <tr>
                <td>年龄：</td>
                <td><input id="age" name="age" type="text" value="18" class="easyui-numberbox" required="true"
                           missingMessage="学生年龄必填！"></td>
            </tr>
            <tr>
                <td>性别 ：</td>
                <td>
                    <input name="sex" type="radio" checked="checked" value="0">女
                    <input name="sex" type="radio" value="1">男
                </td>
            </tr>
            <tr>
                <td>入学时间：</td>
                <td><input id="entranceTime" type="text" name="entranceTime" value=""></td>
            </tr>
            <tr>
                <td>毕学时间：</td>
                <td><input id="graduateTime" type="text" name="graduateTime" value=""></td>
            </tr>
            <tr>
                <td>本科生：</td>
                <td>
                    <input name="isUndergraduate" type="radio" checked="checked" value="0">否
                    <input name="isUndergraduate" type="radio" value="1">是
                </td>
            </tr>
            <tr>
                <td>毕业生：</td>
                <td>
                    <input name="isGraduate" type="radio" checked="checked" value="0">否
                    <input name="isGraduate" type="radio" value="1">是
                </td>
            </tr>
            <tr>
                <td>楼号：</td>
                <td>
                    <input id="dormitoryNo" name="dormitoryNo" value="">
                </td>
            </tr>
            <tr>
                <td>宿舍号：</td>
                <td><input id="roomNumber" name="roomNumber" value="">
                    </td>
            </tr>
            <tr>
                <td>床号：</td>
                <td><input id="bedNo" type="text" name="bedNo" value=""></td>
            </tr>
            <tr style="rowspan: 3">
                <td>家庭住址：</td>
                <td><input id="province" name="province" value="" style="width: 70px;">
                <input id="city" name="city" value="" style="width: 70px;">
                <input id="county"name="county" value="" style="width: 70px;"></td>
            </tr>
            <tr>
                <td>手机号码：</td>
                <td><input id="phone" type="text" name="phone" value=""></td>
            </tr>
            <tr>
                <td>家庭电话：</td>
                <td><input id="homePhone" type="text" name="homePhone" value=""></td>
            </tr>
            <tr>
                <td>职务：</td>
                <td><input id="duty" type="text" name="duty" value="无" class="easyui-textbox"></td>
            </tr>
            <tr>
                <td>院系：</td>
                <td><input id="faculty" name="faculty" value="" style="width: 70px;">
                    <input id="profess" name="profess" value="" style="width: 70px;"></td>
            </tr>
            <tr>
                <td>照片：</td>
                <td><input id="icon" type="file" name="icon" value=""></td>
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
