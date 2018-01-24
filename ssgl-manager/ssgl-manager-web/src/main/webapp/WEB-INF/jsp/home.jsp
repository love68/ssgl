<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/30 0030
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/common-easyui.jsp" %>
<html>
<head>
    <title>宿舍管理系统后台首页</title>
    <script>
        $(function () {
            $("#aa").accordion({
               fit:true
            });
        });
    </script>
    <style>
        a{
            text-decoration: none;
        }
        a:hover{color:#555;font-size: 14px;font-weight: bold;}
    </style>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',title:'宿舍管理系统',split:true" style="height:100px;background-image: url('/images/123.jpg');">
        <div style="margin-left: 20px;">[${loginUser.username}],您好</div>
    </div>
    <div data-options="region:'south',title:'版权信息',split:true" style="height:100px;"></div>
    <div data-options="region:'west',title:'系统菜单',split:true" style="width:200px;">
        <div id="aa" class="easyui-accordion" style="width:180px;height:200px;">
            <div title="学生管理" data-options="">
                <ul>
                    <a href="#">
                        <div style="height: 30px;border-top: solid 0.5px grey;background-color: #66FF99;border-bottom: solid 0.5px grey;margin-left: -40px;margin-top: -12px;">
                            <div style="font-size: 14px;margin-top: 5px;margin-left: 16px;">学生管理</div>
                        </div>
                    </a>

                    <script>
                        $(function () {
                            /*$("a[name='studentManage']").click(function () {
                               $("#tt").tabs("add",{
                                   title:"学生管理"
                               })
                            });*/
                            $("a").click(function () {
                                var title = $(this).text();
                                title = $.trim(title);
                                if("学生管理" == title){
                                    //查看该选项卡是否已经打开
                                    var flag = $("#tt").tabs("exists",title);
                                    //如果未打开
                                    if(!flag){
                                        //打开选项卡
                                        $("#tt").tabs("add",{
                                            "title" : title,
                                            "closable" : true,
                                            "content":'<iframe frameborder=0 style=width:100%;height:100% src='+ "${pageContext.request.contextPath}/student/managerStudent.action" +' ></iframe>'
                                        });
                                    }
                                }
                            })
                        })
                    </script>



                </ul>
            </div>
            <div title="宿舍楼管理" data-options="" style="">
                <a id="sslgl">
                    <div style="height: 30px;border-top: solid 0.5px grey;background-color: #66FF99;border-bottom: solid 0.5px grey;">
                        <div style="font-size: 14px;margin-top: 5px;margin-left: 16px;">宿舍楼管理</div>
                    </div>
                </a>
                <a id="lcgl">
                    <div style="height: 30px;border-top: solid 0.5px grey;background-color: #66FF99;border-bottom: solid 0.5px grey;">
                        <div style="font-size: 14px;margin-top: 5px;margin-left: 16px;">楼层管理</div>
                    </div>
                </a>
            </div>
            <script>
                $(function () {
                    /*$("a[name='studentManage']").click(function () {
                       $("#tt").tabs("add",{
                           title:"学生管理"
                       })
                    });*/
                    $("a").click(function () {
                        var title = $(this).text();
                        title = $.trim(title);
                        if("宿舍楼管理" == title){
                            //查看该选项卡是否已经打开
                            var flag = $("#tt").tabs("exists",title);
                            //如果未打开
                            if(!flag){
                                //打开选项卡
                                $("#tt").tabs("add",{
                                    "title" : title,
                                    "closable" : true,
                                    "content":'<iframe frameborder=0 style=width:100%;height:100% src='+ "${pageContext.request.contextPath}/dormitory/toDormitoryUI.action" +' ></iframe>'
                                });
                            }
                        }else if("楼层管理" == title){
                            //查看该选项卡是否已经打开
                            var flag = $("#tt").tabs("exists",title);
                            //如果未打开
                            if(!flag){
                                //打开选项卡
                                $("#tt").tabs("add",{
                                    "title" : title,
                                    "closable" : true,
                                    "content":'<iframe frameborder=0 style=width:100%;height:100% src='+ "${pageContext.request.contextPath}/floor/toFloorUI.action" +' ></iframe>'
                                });
                            }
                        }
                    })
                })
            </script>

            <div title="房间管理">
                content3
            </div>
            <div title="权限管理">
                content3
            </div>
            <div title="管理员管理">
                content3
            </div>
            <div title="系统设置">
                <a id="logout" onclick="logout()">
                    <div style="height: 30px;border-top: solid 0.5px grey;background-color: #66FF99;border-bottom: solid 0.5px grey;">
                        <div style="font-size: 14px;margin-top: 5px;margin-left: 16px;">退出系统</div>
                    </div>
                </a>
            </div>
            <script>
                function logout() {
                    $.messager.confirm('确认对话框', '您想要退出该系统吗？', function(r){
                        if (r){
                            location.href="${pageContext.request.contextPath}/user/logout.action";
                        }
                    });
                }
            </script>
        </div>

    </div>
    <div data-options="region:'center'" style="background:#eee;">
        <div id="tt" class="easyui-tabs" data-options="fit:true">
        </div>
    </div>
</body>
</html>K
