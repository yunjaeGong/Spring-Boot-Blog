<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="/auth/login" method="post">
        <%-- spring security가 요청 가로챔 --%>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" placeholder="Enter Username" name="username">
        </div>
        <div class="form-group">
            <label for="pwd">Password:</label>
            <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password">
        </div>

        <%--Remember me--%>
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input" type="checkbox" name="remember"> Remember me
            </label>
        </div>
        <button id="btn-login" class="btn btn-primary">로그인</button>
        <%--폼 이용 로그인--%>
    </form>

</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>
