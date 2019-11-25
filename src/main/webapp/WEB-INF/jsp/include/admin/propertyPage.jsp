<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>


<script>
    $(function () {
        $("ul.pagination li.disabled a").click(function () {
            return false;
        });
    });

</script>

<nav>
    <ul class="pagination">
        <li <c:if test="${!pageInfo.hasPreviousPage}">class="disabled"</c:if>>
            <a href="?cid=${category.id}&page=${pageInfo.firstPage}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>

        <li <c:if test="${!pageInfo.hasPreviousPage}">class="disabled"</c:if>>
            <a href="?cid=${category.id}&page=${pageInfo.prePage}" aria-label="Previous">
                <span aria-hidden="true">&lsaquo;</span>
            </a>
        </li>

        <c:forEach begin="1" end="${pageInfo.pages}" var="status">

            <c:if test="${status-pageInfo.pageNum<=2 && status-pageInfo.pageNum>=-2}">

                <li <c:if test="${status==pageInfo.pageNum}">class="disabled"</c:if>>
                    <a href="?cid=${category.id}&page=${status}"
                       <c:if test="${status==pageInfo.pageNum}">class="current"</c:if>
                    >${status}</a>
                </li>

            </c:if>
        </c:forEach>

        <li <c:if test="${!pageInfo.hasNextPage}">class="disabled"</c:if>>
            <a href="?cid=${category.id}&page=${pageInfo.nextPage}" aria-label="Next">
                <span aria-hidden="true">&rsaquo;</span>
            </a>
        </li>
        <li <c:if test="${!pageInfo.hasNextPage}">class="disabled"</c:if>>
            <a href="?cid=${category.id}&page=${pageInfo.lastPage}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>