
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" import="java.util.*" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<html>
<head>
    <title>属性管理</title>

    <script>
        $(function () {
            $("#addForm").submit(function () {
                if (checkEmpty("name", "属性名称")) {
                    return true;
                }
                return false;

            });

        });
    </script>
</head>
<body>
<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="listCategory">所有分类</a></li>
        <li><a href="listProperty?cid=${category.id}">${category.name}</a></li>
        <li class="active">属性管理</li>
    </ol>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>属性名称</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageInfo.list}" var="p">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td><a href="editProperty?cid=${category.id}&id=${p.id}"><span class="glyphicon glyphicon-edit"></span> </a>
                    </td>
                    <td><a href="deleteProperty?cid=${category.id}&id=${p.id}"><span class="glyphicon glyphicon-trash"></span> </a>
                    </td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/propertyPage.jsp" %>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增属性</div>
        <dic class="panel-body">
            <form method="post" id="addForm" action="insertProperty">
                <table class="addTable">
                    <tr>
                        <td>属性名称</td>
                        <td><input id="name" name="name" type="text" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${category.id}">
                            <button type="submit" class="btn btn-success">提交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </dic>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>
</body>
</html>
