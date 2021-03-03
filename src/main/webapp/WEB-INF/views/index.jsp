<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="./layout/header.jsp" %>

<div class="container">
    <h3>Collapsible Navbar</h3>
    <br>
    <c:forEach var="board" items="${boards.content}">
        <div class="card my-2">
                <%--<img class="card-img-top" src="profile.png" alt="Card image">--%>
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                <%-- board 객체의 getTitle 호출됨 --%>
                <a href="#" class="btn btn-primary">자세히 보기</a>
            </div>
        </div>
    </c:forEach>

    <ul class="pagination justify-content-center">
        <li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Prev</a></li>
        <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
    </ul>

</div>

<%@ include file="./layout/footer.jsp" %>