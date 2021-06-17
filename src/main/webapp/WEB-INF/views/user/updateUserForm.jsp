<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form id="modifyUserForm" onsubmit="updateUser()">
        <input type="hidden" id="id" value="${principal.user.id}"/>
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" value="${principal.user.username}" id="username" maxlength="30" readonly>
        </div>

        <div class="form-group">
            <label for="prev_password">Previous Password</label>
            <input type="password" class="form-control" placeholder="이전 비밀번호를 입력하세요" id="prev_password" maxlength="30" required>
        </div>
        <div class="form-group">
            <label for="new_password">New Password</label>
            <input type="password" class="form-control" placeholder="새로운 비밀번호를 입력하세요" id="new_password" maxlength="30">
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control mb-2" value="${principal.user.email}" id="email" maxlength="30" readonly>
            <button type="button" class="btn btn-primary btn-sm shadow-none float-right" id="btn-modify-email" onclick="modifyEmailToggle()">수정</button>
        </div>
        <span>
            <button class="btn btn-primary btn-sm shadow-none" type="submit">수정</button>
            <button class="btn btn-outline-primary btn-sm shadow-none" onclick="history.back()">취소</button>
        </span>

    </form>


</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>
