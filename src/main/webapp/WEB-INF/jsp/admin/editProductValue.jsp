<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<title>编辑产品属性值</title>

<script>
    $(function () {
        $("input.pvValue").keyup(function () {
            var value = $(this).val();
            var page = "updateProductPropertyValue";
            var pvid = $(this).attr("pvid");
            var pid = $(this).attr("pid");
            var ptid = $(this).attr("ptid");
            var parentSpan = $(this).parent("span");
            parentSpan.css("border", "1px solid yellow");
            $.post(
                page,
                {"value": value, "pvid": pvid,"pid": pid,"ptid": ptid},
                function (result) {
                    if ("success" == result)
                        parentSpan.css("border", "1px solid green");
                    else
                        parentSpan.css("border", "1px solid red");
                }
            );
        });
    });
</script>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="listCategory">所有分类</a></li>
        <li><a href="listProduct?cid=${p.cid}">${c.name}</a></li>
        <li class="active">${p.name}</li>
        <li class="active">编辑产品属性</li>
    </ol>

    <div class="editPVDiv">
        <c:forEach items="${pvs}" var="pv">
            <div class="eachPV">
                <span class="pvName">${pv.property.name}</span>
                <span class="pvValue"><input class="pvValue" pid = "${pv.product.id}" ptid = "${pv.property.id}" pvid="${pv.id}" type="text" value="${pv.value}"></span>
            </div>
        </c:forEach>
        <div style="clear:both"></div>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-body">
                <table class="addTable">
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <a href="listProduct?cid=${p.cid}">
                                <button type="submit" class="btn btn-success">提交</button>
                            </a>
                        </td>
                    </tr>
                </table>
        </div>
    </div>

</div>
