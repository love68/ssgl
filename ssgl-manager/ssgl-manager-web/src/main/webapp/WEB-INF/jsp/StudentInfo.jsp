<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="common/common-easyui.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>学生详细信息</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
function hideURLbar(){ window.scrollTo(0,1); } </script>

<link href="${pageContext.request.contentType}/css/style.css" rel="stylesheet" type="text/css" media="all">
</head>
<body>
<div class="form">
	<div class="form-content">
		<form action="${pageContext.request.contentType}/student/updateStudent.action" method="post">
			<input type="hidden" name="id" value="${student.id}">
			<div class="name">
				<label>学号</label>
				<input class="input1" type="text" name="sid" readonly required="" value="${student.sid}" >
			</div>
			<div class="name">
				<label>姓名</label>
				<input class="input1" type="text" name="name" value="${student.name}" readonly required="">
			</div>
			<div class="name">
				<img src="${student.icon}" name="icon">
			</div>
			<div class="email">
				<label>手机号码</label>
				<input class="input1" type="text" name="phone" value="${student.phone}" required="">
			</div>
			<div class="pass1">
				<label>年龄</label>
				<input class="input1" type="text" name="age" required="" value="${student.age}">
			</div>
			<div class="pass2">
				<label>性别</label>
				<input class="input1" type="text" name="sex" readonly required=""
						<c:choose>
							<c:when test="${student.sex}">value='男'</c:when>
							<c:otherwise>value='女'</c:otherwise>
						</c:choose>
				/>
			</div>

			<div class="pass2">
				<label>入学时间</label>
				<input class="input1" type="text" name="entranceTime" readonly required="" value="${student.entranceTime}">
			</div>

			<div class="pass2">
				<label>毕学时间</label>
				<input class="input1" type="text" name="graduateTime" readonly required="" value="<fmt:formatDate value="${student.graduateTime}" pattern="yyyy-MM-dd"/>">
			</div>

			<div class="pass2">
				<label>本科生</label>
				<input class="input1" type="text" name="isUndergraduate" readonly required=""
					   <c:choose>
						   <c:when test="${student.isUndergraduate}">value='是'</c:when>
						   <c:otherwise>value='否'</c:otherwise>
					   </c:choose>
				>
			</div>

			<div class="pass2">
				<label>楼号</label>
				<input class="input1" type="text" name="dormitoryNo" required="" readonly value="${student.dormitoryNo}">
			</div>

			<div class="pass2">
				<label>宿舍号</label>
				<input class="input1" type="text" name="roomNumber" required="" readonly value="${student.roomNumber}">
			</div>

			<div class="pass2" style="display: none">
				<label>床号</label>
				<input class="input1" type="text" name="bedNo" required="" readonly value="${student.bedNo}">
			</div>

			<div class="pass2">
				<label>家庭住址</label>
				<input class="input1" type="text" name="address" readonly required="" value="${student.address}">
			</div>

			<div class="pass2">
				<label>家庭电话</label>
				<input class="input1" type="text" name="homePhone" required="" value="${student.homePhone}">
			</div>

			<div class="pass2">
				<label>职务</label>
				<input class="input1" type="text" name="duty" required="" value="${student.duty}">
			</div>
			<div class="pass2">
				<label>院系</label>
				<input class="input1" type="text" readonly name="faculty" required="" <c:choose>
																						<c:when test="${student.faculty==1}">
																							  value="计算机工程系"
																						</c:when>
																						<c:when test="${student.faculty==2}">
																							   value="数学系"
																						</c:when><c:when test="${student.faculty==3}">
																							   value="外语系"
																						</c:when><c:when test="${student.faculty==4}">
																							   value="经济管理系"
																						</c:when><c:when test="${student.faculty==5}">
																							   value="物理系"
																						</c:when><c:when test="${student.faculty==6}">
																							   value="中文系"
																						</c:when><c:when test="${student.faculty==7}">
																							   value="中语系"
																						</c:when><c:when test="${student.faculty==8}">
																							   value="化学工程系"
																						</c:when><c:when test="${student.faculty==9}">
																							   value="初等教育学院"
																						</c:when><c:when test="${student.faculty==9}">
																							   value="体育系"
																						</c:when><c:when test="${student.faculty==9}">
																							   value="音乐系"
																						</c:when><c:when test="${student.faculty==9}">
																							   value="美术系"
																						</c:when>
																						<c:otherwise>
					   																		value="那是不可能滴"
																						</c:otherwise>
																					</c:choose> >
			</div>
			<div style="display: none;"><img src="${student.icon}" name="icon"><input name="isGraduate" type="radio" value="${student.isGraduate}"></div>
			<div class="signup">
				<input type="submit" value="修改">
			</div>
		</form>
	</div>
</div>

<footer>&copy; 2018 All rights reserved | Design by <a>jiajunkang</a></footer>
</body>
</html>