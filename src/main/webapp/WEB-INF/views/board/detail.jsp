<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<style>
    /* reply system */

    ul.replies {
        margin: 0px;
        padding: 0px;
        list-style: none;
    }
    ul.replies li.reply {
        position: relative;
        margin-bottom: 15px;
        padding: 0px;
        z-index: 9;
    }
    ul.replies li.reply:before {
        content: '';
        width: 1px;
        height: 100%;
        background: #ebedf2;
        position: absolute;
        left: 15px;
        top: 15px;
    }
    ul.replies li.reply:last-child:before {
        display: none;
    }
    ul.replies li.reply .reply-wrapper {
        position: relative;
        z-index: 99;
    }
    ul.replies li.reply .card .card-header {
        position: relative;
        padding-top: 8px;
        padding-bottom: 8px;
        margin-bottom: 0;
        line-height: 20px;
        vertical-align: bottom;
    }
    ul.replies li.reply .card .card-header .username{
        font-size: 20px;
        font-weight: normal;
        color: dimgray;
    }
    ul.replies li.reply .card .card-header img {
        width: 32px;
        background: #fff;
    }
    ul.replies li.reply .card .card-header .reply-date {
        font-size: 14px;
        color: #bbb;
        margin-left: 8px;
    }
    ul.replies li.reply .card .card-header .ribbon {
        width: 40px;
        height: 40px;
        overflow: hidden;
        position: absolute;
        top: -5px;
        left: -5px;
    }
    ul.replies li.reply .card .card-header .ribbon span {
        position: absolute;
        display: block;
        width: 58px;
        padding: 0px 0;
        background-color: #4680ff;
        box-shadow: 0 5px 10px rgba(0, 0, 0, 0.05);
        color: #fff;
        font-size: 8px;
        font-weight: 400;
        text-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
        text-transform: uppercase;
        text-align: center;
        right: -5px;
        top: 10px;
        transform: rotate(-45deg);
        z-index: 9;
    }
    ul.replies li.reply .card .card-header .ribbon:before,
    ul.replies li.reply .card .card-header .ribbon:after {
        position: absolute;
        z-index: -1;
        content: '';
        display: block;
        border: 5px solid #2980b9;
        border-top-color: transparent;
        border-left-color: transparent;
    }
    ul.replies li.reply .card .card-header .ribbon:before {
        top: 0;
        right: 0;
    }
    ul.replies li.reply .card .card-header .ribbon:after {
        bottom: 0;
        left: 0;
    }
    ul.replies li.reply ul {
        padding-left: 30px;
        margin-top: 15px;
    }
    ul.replies li > ul > li:first-child > .reply-wrapper > .card > .card-header:after {
        content: '';
        width: 15px;
        height: 1px;
        background: #ebedf2;
        position: absolute;
        left: -15px;
        top: 50%;
        z-index: 3;
    }
    ul.replies li > ul > li:first-child > .reply-wrapper > .card > .card-header:before {
        content: '';
        width: 1px;
        height: 100%;
        background: #ebedf2;
        position: absolute;
        left: -16px;
        bottom: 50%;
        z-index: 1;
    }
    .n-reply{
        padding: 8px;
        margin-left: 24px;
        margin-bottom: 8px;
    }

</style>

<div class="container">
    <div>
        <h1>${board.title}</h1>
    </div>
    <br/>

    <div>
        <p><strong style="color: dimgray; font-family: Verdana; font-size: large">Content:</strong></p>
        <hr/>
        <div>${board.content}</div>
    </div>
    <br/><br/>


    <%-- 돌아가기 글 삭제, 수정 --%>

    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
    <c:if test="${board.user.id == principal.user.id}">
        <button id="btn-delete" class="btn btn-danger">삭제</button>
        <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
    </c:if>
    <br/><br/>

    <%-- 댓글 작성 --%>

    <div class="col-7">
        <%-- https://github.com/ZsharE/threaded-comments-bootstrap --%>
        <%-- TODO: MIT License 표기 --%>
        <%-- replies --%>
        <%-- wrapper 안에 reply class들--%>
        <br/>
        <ul class="replies">
            <c:forEach var="reply" items="${rootReplies}" varStatus="rootStatus">
                <li id="reply-${reply.id}" class="reply">
                    <div class="reply-wrapper">
                        <div class="card">
                            <div class="card-header">
                                    <a href="#" class="username">${reply.user.username}</a>

                                <span class="reply-date"><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"  value="${reply.createDate}" /></span>
                            </div>
                            <div id="replyCardBody-${reply.id}" class="card-body">
                                <textarea id="replyTextareaId-${reply.id}" rows="3" name="text" class="form-control"
                                          style="resize:vertical; display:none"></textarea>
                                <span class="card-text"
                                      id="replyContentId-${reply.id}">${reply.content}</span>
                            </div>
                            <div class="card-footer bg-white p-2">
                                <%--<button id="btn-nested-reply" type="button" class="btn btn-secondary btn-sm">Reply</button>--%>
                                <div class="like cursor action-collapse">
                                    <button class="btn btn-secondary btn-sm" data-toggle="collapse" aria-expanded="true" aria-controls="collapse-${rootStatus.count}" href="#collapse-${rootStatus.count}">Reply</button>
                                    <small class="text-muted ml-2" style="vertical-align: middle">${reply.timeAgo}</small >
                                    <span class="float-right">
                                    <c:choose>
                                        <c:when test="${board.user.id == principal.user.id}">
                                            <button onclick="index.replyDelete(${board.id},${reply.id})" class="btn btn-danger btn-sm">삭제</button>
                                            <button id="replyModify-${reply.id}"  onclick="index.modifyReplyTextareaToggle(${reply.id}, 'reply')"
                                                    style="display: inline-block" class="btn btn-warning btn-sm">수정</button>

                                            <span style="display: none" id="replyOnModify-${reply.id}">
                                            <button onclick="index.updateReply(${board.id}, ${reply.id}, 'reply')"
                                                class="btn btn-secondary btn-sm">Register</button>
                                            <button class="btn btn-outline-primary btn-sm shadow-none"
                                                onclick="index.modifyReplyTextareaToggle(${reply.id}, 'reply')">Cancel</button>
                                            </span>
                                        </c:when>
                                    </c:choose>
                                    </span>
                                </div>
                                <%-- Collapsible --%>
                                <div id="collapse-${rootStatus.count}" class="bg-light p-2 collapse">
                                    <%-- Nested replies --%>
                                    <ul class="replies">
                                        <c:forEach var="nestedReply" items="${nestedReplies}"
                                                   varStatus="status">
                                            <c:if test="${nestedReply.parentId eq reply.id}">
                                                <li class="reply">
                                                    <div class="reply-wrapper">
                                                        <div class="card">
                                                            <div class="card-header">
                                                                <a href="#"
                                                                   class="username">${nestedReply.user.username}</a>
                                                                    <%--<div class="d-flex flex-column justify-content-start ml-2"><span class="d-block font-weight-bold name">Marry Andrews</span><span class="date text-black-50">Shared publicly - Jan 2020</span></div>--%>
                                                            </div>

                                                            <div id="nestedReplyCardBody-${nestedReply.id}" class="card-body">
                                                                <textarea id="nestedReplyTextareaId-${nestedReply.id}" rows="3" name="text" class="form-control"
                                                                          style="resize:vertical; display:none"></textarea>
                                                                <span class="card-text"
                                                                   id="nestedReplyContentId-${nestedReply.id}">${nestedReply.content}</span>
                                                            </div>
                                                            <div class="card-footer bg-white p-2">
                                                                <small class="text-muted ml-2" style="vertical-align: middle">${nestedReply.timeAgo}</small >
                                                                <span class="float-right" id="nestedReplyFooterId-${nestedReply.id}">
                                                                    <c:choose>
                                                                        <c:when test="${board.user.id == principal.user.id}">
                                                                            <button onclick="index.replyDelete(${board.id},${nestedReply.id})"
                                                                                    class="btn btn-danger btn-sm">삭제</button>
                                                                            <button style="display: inline-block" id="nestedReplyModify-${nestedReply.id}"
                                                                                    onclick="index.modifyReplyTextareaToggle(${nestedReply.id}, 'nestedReply')"
                                                                                    class="btn btn-warning btn-sm">수정</button>

                                                                            <span style="display: none" id="nestedReplyOnModify-${nestedReply.id}">
                                                                                <button onclick="index.updateReply(${board.id}, ${nestedReply.id}, 'nestedReply')"
                                                                                        class="btn btn-secondary btn-sm">Register</button>
                                                                                <button class="btn btn-outline-primary btn-sm shadow-none"
                                                                                        onclick="index.modifyReplyTextareaToggle(${nestedReply.id}, 'nestedReply')">Cancel</button>
                                                                            </span>

                                                                        </c:when>
                                                                    </c:choose>
                                                                </span>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>

                                    <%-- Post NestedReply --%>
                                    <%-- Principal --%>
                                    <div class="reply-wrapper">
                                        <div class="card">
                                            <div class="card-header">
                                                <c:choose>
                                                    <c:when test="${empty principal}">
                                                        <div class="d-flex form-inline">
                                                            <label for="nestedReplyUsername-${reply.id}"></label>
                                                            <input type="text" class="form-control ml-2"
                                                                   placeholder="id"
                                                                   id="nestedReplyUsername-${reply.id}">
                                                            <label for="nestedReplyPassword-${reply.id}"></label>
                                                            <input type="text" class="form-control ml-2"
                                                                   placeholder="password"
                                                                   id="nestedReplyPassword-${reply.id}">
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="#"
                                                           class="username">${principal.user.username}</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>

                                            <div class="p-2">
                                                <label for="nestedReplyContent-${reply.id}"></label>
                                                <textarea class="form-control px-2"
                                                          id="nestedReplyContent-${reply.id}"
                                                          rows="3"></textarea>
                                            </div>
                                            <div class="card-footer bg-white p-2">
                                                <button class="btn btn-secondary btn-sm shadow-none"
                                                        id="btn-nested-reply-save-${reply.id}"
                                                        onclick="index.saveNestedReply(${reply.id})">Post
                                                    comment
                                                </button>
                                                <button class="btn btn-outline-primary btn-sm ml-1 shadow-none"
                                                        type="button" data-toggle="collapse" aria-controls="collapse-${rootStatus.count}" data-target="#collapse-${rootStatus.count}">Cancel
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                    <%-- End of Collapsible --%>
                            </div>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>

        <%-- Reply textarea --%>
            <div class="reply-wrapper">
                <div class="card">

                    <input type="hidden" id="boardId" value="${board.id}"/>
                    <input type="hidden" id="principalId" value="${principal.user.id}"/>

                    <div class="card-header"
                         style="position: relative;padding-top: 8px;padding-bottom: 8px;margin-bottom: 0;line-height: 20px;vertical-align: bottom;">
                        <c:choose>
                            <c:when test="${empty principal}">
                                <div class="form-inline">
                                    <label for="userId"></label>
                                    <input type="text" class="form-control ml-2" placeholder="id" id="userId">
                                    <label for="replyPassword"></label>
                                    <input type="text" class="form-control ml-2" placeholder="password"
                                           id="replyPassword">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a href="#" class="username">${principal.user.username}</a>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="p-2">
                        <textarea class="form-control px-2" id="replyContent" rows="3"></textarea>
                    </div>
                    <div class="card-footer bg-white p-2">
                        <button id="btn-reply" type="button" class="btn btn-secondary btn-sm"
                                onclick="index.saveReply()">Register</button>
                    </div>
                </div>
            </div>
    </div>
</div>


<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>
