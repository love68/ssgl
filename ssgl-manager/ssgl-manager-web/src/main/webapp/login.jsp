<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/16 0016
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
    <html lang="en" class="no-js">

    <head>

        <meta charset="utf-8">
        <title>宿舍管理系统后台登录</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- CSS -->

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/supersized.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/js/html5.js"></script>
        <![endif]-->
        <script src="${pageContext.request.contextPath}/js/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/tooltips.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
    </head>

<body>

<div class="page-container">
    <div class="main_box">
        <div class="login_box">
            <div class="login_logo">
                <img src="images/logo.png" >
            </div>

            <div class="login_form">
                <form action="/login.action" id="login_form" method="post">
                    <div class="form-group">
                        <label for="j_username" class="t">邮　箱：</label>
                        <input id="email" value="" name="username" type="text" class="form-control x319 in"
                               autocomplete="off">
                    </div>
                    <div class="form-group">
                        <label for="j_password" class="t">密　码：</label>
                        <input id="password" value="" name="password" type="password"
                               class="password form-control x319 in">
                    </div>
                    <div class="form-group">
                        <label for="j_captcha" class="t">验证码：</label>
                        <input id="j_captcha" name="j_captcha" type="text" class="form-control x164 in">
                        <img id="captcha_img" alt="点击更换" title="点击更换" src="${pageContext.request.contextPath }/validatecode.jsp" class="m"
                             onclick="javascript:document.getElementById('captcha_img').src='${pageContext.request.contextPath }/validatecode.jsp?'+Math.random();">
                    </div>
                    <div class="form-group">
                        <label class="t"></label>
                        <label for="j_remember" class="m">
                            <input id="j_remember" type="checkbox" value="true">&nbsp;记住登陆账号!</label>
                    </div>
                    <div class="form-group space">
                        <label class="t"></label>　　　
                        <button type="button"  id="submit_btn"
                                class="btn btn-primary btn-lg">&nbsp;登&nbsp;录&nbsp </button>
                        <input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg">
                    </div>
                </form>

                <script>
                    //提交表达
                    $("#submit_btn").click(function () {
                        var user = $("#login_form").serializeJson();
                        console.info(user);
                        <%--window.location.href="${pageContext.request.contextPath}/user/login.action";--%>
                        $("#login_form").submit();


                    });


                    $.fn.serializeJson=function(){
                        var serializeObj={};
                        var array=this.serializeArray();
                        $(array).each(function(){
                            if(serializeObj[this.name]){
                                if($.isArray(serializeObj[this.name])){
                                    serializeObj[this.name].push(this.value);
                                }else{
                                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                                }
                            }else{
                                serializeObj[this.name]=this.value;
                            }
                        });
                        return serializeObj;
                    };
                </script>

            </div>
        </div>
        <div class="bottom">Copyright &copy; 2018</div>
    </div>
</div>

<!-- Javascript -->

<script src="${pageContext.request.contextPath}/js/supersized.3.2.7.min.js"></script>
<script src="${pageContext.request.contextPath}/js/supersized-init.js"></script>
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>

</body>
</html>
