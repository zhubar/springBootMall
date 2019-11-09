<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>


<script>
    $(function(){

        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.registerErrorMessageDiv").css("visibility","visible");
        </c:if>

        $(".registerForm").submit(function(){
            if(0==$("#receiver").val().length  ){
                $("span.errorMessage").html("请输入收件人");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
            if(0==$("#mobile").val().length){
                $("span.errorMessage").html("请输入手机号码");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
            if(0==$("#address").val().length){
                $("span.errorMessage").html("请输入收货地址");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }
            if(0==$("#password").val().length){
                $("span.errorMessage").html("请输入密码");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }

            if($("#password").val() !=$("#repeatpassword").val()){
                $("span.errorMessage").html("密码不一致");
                $("div.registerErrorMessageDiv").css("visibility","visible");
                return false;
            }

            return true;
        });
    })
</script>



<form method="post" action="modify" class="registerForm">


    <div class="registerDiv">
        <div class="registerErrorMessageDiv">
            <div class="alert alert-danger" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                <span class="errorMessage"></span>
            </div>
        </div>


        <table class="registerTable" align="center">
            <tr>
                <td  class="registerTip registerTableLeftTD">设置个人信息</td>
                <td></td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">收件人</td>
                <td  class="registerTableRightTD"><input id="receiver" name="receiver" type = "text" value = "${user.receiver}" placeholder="${user.receiver}" > </td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">手机号码</td>
                <td class="registerTableRightTD"><input id="mobile" name="mobile" type = "text" value = "${user.mobile}"  placeholder="${user.mobile}" > </td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">收货地址</td>
                <td class="registerTableRightTD"><input id="address" name ="address"  type = "text" value = "${user.address}" placeholder="${user.address}" > </td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">密码</td>
                <td class="registerTableRightTD"><input id="password" name ="password"  type = "password" value = "${user.password}" placeholder="${user.password}" > </td>
            </tr>
            <tr>
                <td class="registerTableLeftTD">密码确认</td>
                <td class="registerTableRightTD"><input id="repeatpassword" type="password" value = "${user.password}"   placeholder="请再次输入你的密码" > </td>
            </tr>

            <tr>
                <td colspan="2" class="registerButtonTD">
                    <a href="modifySuccess"><button>提   交</button></a>
                </td>
            </tr>
        </table>
    </div>
</form>