<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#bought").click(function () {
            var page = "checkLogin";
            $.get(
                page,
                function (result) {
                    if ("success" == result) {
                        location.href = $("#bought").attr("href");
                    }
                    else {
                        $("#loginModal").modal('show');
                    }
                }
            );
            return false;
        });
        $("#cart").click(function () {
            var page = "checkLogin";
            $.get(
                page,
                function (result) {
                    if ("success" == result) {
                        location.href = $("#cart").attr("href");
                    }
                    else {
                        $("#loginModal").modal('show');
                    }
                }
            );
            return false;
        });
    });
</script>
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
            <a id = "bought" href="bought">我的订单</a>
            <a id = "cart" href="cart">
            <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
            购物车<strong>${cartTotalItemNumber}</strong>件</a>
        </span>

</nav>