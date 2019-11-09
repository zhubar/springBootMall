<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<nav class="top ">
    <a href="home">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        天猫首页
    </a>

    <span>喵，欢迎来天猫</span>

    <c:if test="${!empty user}">
        <a href="">${user.name}</a>
        <a href="logout">退出</a>
        <a href="userInformation">个人信息</a>
    </c:if>

    <c:if test="${empty user}">
        <a href="userLogin">请登录</a>
        <a href="userRegister">免费注册</a>
    </c:if>

    <span class="pull-right">
            <a href="bought">我的订单</a>
            <a href="cart">
            <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
            购物车<strong>${cartTotalItemNumber}</strong>件</a>
        </span>

</nav>