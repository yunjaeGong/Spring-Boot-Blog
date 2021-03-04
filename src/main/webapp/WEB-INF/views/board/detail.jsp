<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>


<div class="container">
    <div>
        글번호: <span id="id"><i>${board.id}</i></span>
        작성자: <span><i>${board.user.username}</i></span>
    </div>
    <br/>

    <div>
        <h3>${board.title}</h3>
    </div>
    <br/>

    <div>
        <p><strong style="color: darkgray; font-family: Verdana; font-size: large">Content:</strong></p>
        <hr/>
        <div>${board.content}</div>
    </div>
    <br/><br/>
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
    <c:if test="${board.user.id = principal.user.id}">
        <button id="btn-delete" class="btn btn-danger">삭제</button>
        <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
    </c:if>

</div>

<script src="js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>
