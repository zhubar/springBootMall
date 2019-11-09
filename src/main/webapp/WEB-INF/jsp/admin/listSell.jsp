
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" import="java.util.*" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>
<html>
<head>
    <title>销售统计</title>
</head>
<body>
<div class="workingArea">
    <h1 class="label label-info">销售统计</h1>
    <br>
    <br>
</div>
<div style="width:400px;margin:0px auto">
    <canvas id="myChart" ></canvas>
</div>


<input type = "hidden" value="${cname}" id="cname"/>
<input type = "hidden" value="${csell}" id="csell"/>
<input type = "hidden" value="${pname}" id="pname"/>
<input type = "hidden" value="${psell}" id="psell"/>
<%
    session.setAttribute("f",1);
%>
<ul id="myTab" class="nav nav-tabs">
    <c:forEach items="${cid}" var="c" varStatus="loop">
        <c:if test="${f != 1}">
            <li>
                <a href="#${c}" data-toggle="tab" id = "${c}" name = "hlink" value = "${c}">
                        ${cname[loop.count-1]}
                </a>
            </li>
        </c:if>
        <c:if test="${f == 1}">
            <li class="active">
                <a href="#${c}" data-toggle="tab" id="${c}"name = "hlink" value = "${c}" >
                        ${cname[loop.count-1]}
                </a>
            </li>
            <%
                session.setAttribute("f",0);
            %>
        </c:if>
    </c:forEach>
</ul>

<div style="width:400px;margin:0px auto" id = "myNavChartPar">
    <canvas id="myNavChart" ></canvas>
</div>
<%--
<%
    session.setAttribute("f",1);
%>
<div id="myTabContent" class="tab-content" >
    <c:forEach items="${cid}" var="c" varStatus="loop">
        <c:if test="${f != 1}">
            <div class="tab-pane fade" id="${c}">
                <p>${c}</p>
            </div>

        </c:if>
        <c:if test="${f == 1}">
            <div class="tab-pane fade in active" id="${c}">
                <p>${c}</p>
            </div>
            <%
                session.setAttribute("f",0);
            %>
        </c:if>
    </c:forEach>
</div>
--%>
<script>
    var number = $("#cname").val();
    var n = $("#csell").val();
    var cname = $("#cname").val().substring(1,number.length - 1);
    var csell = $("#csell").val().substring(1,n.length - 1);
    var cnameSplit = cname.split(',');
    var csellSplit = csell.split(',');
    function col(params){

        var colorarrays = ["#2F9323","#D9B63A","#2E2AA4","#9F2E61","#4D670C","#BF675F","#1F814A","#357F88","#673509","#310937","#1B9637","#F7393C"];

        return colorarrays.slice(0,params);

    }
    var co = col(cnameSplit.length);
    var ctx = document.getElementById('myChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: cnameSplit,
            datasets: [{
                label: '分类销售统计',
                data: csellSplit,
                borderColor:'blue',
                backgroundColor:'skyblue',
                borderWidth: 1,
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        min: 0
                    }
                }]
            }
        }
    });

    var mumber = $("#pname").val();
    var m = $("#psell").val();
    var pname = $("#pname").val().substring(1,mumber.length - 1);
    var psell = $("#psell").val().substring(1,m.length - 1);
    var pnameSplit = pname.split(',');
    var psellSplit = psell.split(',');
    var ctx = document.getElementById('myNavChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: pnameSplit,
            datasets: [{
                label: '产品销售统计',
                data: psellSplit,
                borderColor:'blue',
                backgroundColor:'skyblue',
                borderWidth: 1,
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        min: 0
                    }
                }]
            }
        }
    });

    $('#myTab').on('click',"[name = 'hlink']",function() {
        var data = $(this).attr('id')

        $.ajax({
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type: "GET",
            url: "/sellAjax",
            data:{
                id:data
            },
            statusCode: {
                404: function () {
                    alert('page not found');
                },
                400: function () {
                    alert('page not foundhjhg');
                }
            },
            success: function (data) {
                document.getElementById('myNavChart').remove();
                $('#myNavChartPar').append('<canvas id="myNavChart"></canvas>');
                var Split = data.split('?');
                var mumber = Split[0];
                var m = Split[1];
                var pname = mumber.substring(1,mumber.length - 1);
                var psell = m.substring(1,m.length - 1);
                var pnameSplit = pname.split(',');
                var psellSplit = psell.split(',');
                var ctx = document.getElementById('myNavChart').getContext('2d');
                var myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: pnameSplit,
                        datasets: [{
                            label: '产品销售统计',
                            data: psellSplit,
                            borderColor:'blue',
                            backgroundColor:'skyblue',
                            borderWidth: 1,
                        }]
                    },
                    options: {
                        scales: {
                            yAxes: [{
                                ticks: {
                                    min: 0
                                }
                            }]
                        }
                    }
                });
            }
        });

    });
</script>

</body>
</html>
