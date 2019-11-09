<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<a href="${contextPath}">
    <img id="logo" src="img/site/logo.png" class="logo">
</a>

<form action="search" method="post">
    <div class = "searchDiv">
        <input name="keyword"  type="text" placeholder="">
        <button type="submit" >搜索</button>
        <div class="searchBelow">
            <c:forEach items="${categories}" var="category" varStatus="st">
                <c:if test="${st.count>=5 and st.count<=8}">
                        <span>
                            <a href="category?cid=${category.id}">
                                    ${category.name}
                            </a>
                            <c:if test="${st.count!=8}">
                                <span>|</span>
                            </c:if>
                        </span>
                </c:if>
            </c:forEach>
        </div>
    </div>
</form>
