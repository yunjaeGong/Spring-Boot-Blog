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
    .n-reply {
        padding: 8px;
        margin-left: 24px;
        margin-bottom: 8px;
    }

</style>

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

    <div>
        <div class="card mb-2">

            <input type="hidden" id="boardId" value="${board.id}"/>
            <input type="hidden" id="principalId" value="${principal.user.id}"/>

            <div class="card-header d-flex align-items-center">
                <p class="d-flex align-items-center"><h5 style="color: dimgray;font-size: 16px;line-height: 1;margin: 0 10px;">Reply</h5></p>
            </div>
            <c:choose>
                <c:when test="${empty principal}">
                    <div class="form-inline my-2">
                        <label for="userId"></label>
                        <input type="text" class="form-control ml-2" placeholder="id" id="userId">
                        <label for="replyPassword"></label>
                        <input type="text" class="form-control ml-2" placeholder="password" id="replyPassword">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="mx-2">
                        <a href="#"><h5>${principal.user.username}</h5></a>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="p-2"><textarea class="form-control px-2" id="replyContent" rows="3"></textarea></div>
            <div class="card-footer bg-white p-2">
                <button id="btn-reply" type="button" class="btn btn-secondary btn-sm" onclick="index.saveReply()">Register</button>
            </div>
        </div>
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
                            <div class="card-body">
                                <p class="card-text">${reply.content}</p>
                            </div>
                            <div class="card-footer bg-white p-2">
                                <%--<button id="btn-nested-reply" type="button" class="btn btn-secondary btn-sm">Reply</button>--%>
                                <div class="like cursor action-collapse">
                                    <button class="btn btn-secondary btn-sm" data-toggle="collapse" aria-expanded="true" aria-controls="collapse-${rootStatus.count}" href="#collapse-${rootStatus.count}">Reply</button>
                                    <small class="text-muted ml-2" style="vertical-align: middle">Last updated 3 mins ago</small > <%--TODO: 업데이트된 시간{x mins ago, x days 7], x week, a year, ""}--%>
                                    <span class="float-right">
                                    <c:choose>
                                        <c:when test="${board.user.id == principal.user.id}">
                                            <button onclick="index.replyDelete(${board.id},${reply.id}" class="btn btn-danger btn-sm">삭제</button>
                                            <button onclick="index.replyDelete(${board.id},${reply.id}" class="btn btn-warning btn-sm">수정</button>
                                        </c:when>
                                    </c:choose>
                                </span>
                                </div>



                                <%-- Collapsible --%>
                                    <div id="collapse-${rootStatus.count}" class="bg-light p-2 collapse">
                                            <%-- nested replies --%>
                                        <c:forEach var="nestedReply" items="${nestedReplies}" varStatus="status">
                                            <c:if test="${nestedReply.parentId eq reply.id}">
                                                <div class="bg-white n-reply">
                                                    <div class="d-flex flex-row user-info">
                                                        <span class="fs-16">${nestedReply.user.username}</span>
                                                            <%--<div class="d-flex flex-column justify-content-start ml-2"><span class="d-block font-weight-bold name">Marry Andrews</span><span class="date text-black-50">Shared publicly - Jan 2020</span></div>--%>
                                                    </div>
                                                    <div class="mt-2 mx-2">
                                                        <p class="comment-text">${nestedReply.content}</p>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>

                                            <%--nested reply post--%>
                                            <%--principal--%>
                                        <div class="d-flex flex-row user-info">
                                            <div class="form-inline my-2">
                                                <label for="nestedReplyId-${reply.id}"></label>
                                                <input type="text" class="form-control ml-2" placeholder="id" id="nestedReplyId-${reply.id}">
                                                <label for="nestedReplyPassword-${reply.id}"></label>
                                                <input type="text" class="form-control ml-2" placeholder="password" id="nestedReplyPassword-${reply.id}">
                                            </div>
                                        </div>

                                        <div class="d-flex flex-row align-items-start">
                                                <textarea class="form-control ml-2 shadow-none textarea form-control" rows="3" id="nestedReplyContent-${reply.id}"></textarea>
                                        </div>

                                        <div class="mt-2 text-right">
                                            <button class="btn btn-secondary btn-sm shadow-none" id="btn-nested-reply-save-${reply.id}"
                                                    onclick="index.saveNestedReply(${reply.id})">Post comment</button>
                                            <button class="btn btn-outline-primary btn-sm ml-1 shadow-none" type="button">Cancel</button>
                                        </div>
                                    </div>
                                    <%-- End of Collapsible --%>
                            </div>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <%--<ul class="replies">
                <c:forEach var="reply" items="${rootReplies}" varStatus="rootStatus">
                    <li class="reply">
                    <div class="d-flex flex-flow row reply reply-wrapper">
                        <div class="col-lg-10">
                            <div class="d-flex flex-column comment-section mb-2" id="myGroup">
                                <div class="bg-white p-2">
                                    <div class="d-flex flex-row user-info">
                                            &lt;%&ndash;<div class="form-inline my-2">
                                                <label for="replyId"></label>
                                                <input type="text" class="form-control ml-2" placeholder="id" id="replyId">
                                                <label for="replyPassword"></label>
                                                <input type="text" class="form-control ml-2" placeholder="password" id="replyPassword">
                                            </div>&ndash;%&gt;
                                            &lt;%&ndash; Displayed Reply Principal &ndash;%&gt;
                                        <div class="d-flex flex-column justify-content-start ml-2">
                                            <span class="d-block font-weight-bold name">${reply.user.username}</span>
                                            <span class="date text-black-50"><fmt:formatDate value="${reply.user.createDate}" pattern="MM yyyy"/></span>
                                        </div>
                                    </div>
                                    <div class="mt-2 mx-2">
                                        <p class="comment-text">${reply.content}</p>
                                    </div>
                                </div>
                                <div class="bg-light p-2">
                                    <div class="d-flex fs-12">
                                        <div class="like p-2 cursor action-collapse" data-toggle="collapse" aria-expanded="true" aria-controls="collapse-${rootStatus.count}" href="#collapse-${rootStatus.count}"><button class="btn btn-secondary btn-sm ml-2">Reply</button></div>
                                    </div>
                                </div>

                                    &lt;%&ndash; Collapsible NestedReply &ndash;%&gt;
                                <div id="collapse-${rootStatus.count}" class="bg-light p-2 collapse">
                                        &lt;%&ndash; nested replies &ndash;%&gt;
                                    <c:forEach var="nestedReply" items="${nestedReplies}" varStatus="status">
                                        &lt;%&ndash;<c:if test="${nestedReply.parentId eq reply.id}">&ndash;%&gt;
                                        <div class="bg-white n-reply">
                                            <div class="d-flex flex-row user-info">
                                                <div class="d-flex flex-column justify-content-start ml-2">
                                                    <span class="d-block font-weight-bold name">${nestedReply.user.userId}</span>
                                                    <span class="date text-black-50">Shared publicly - <fmt:formatDate value="${nestedReply.createDate}" pattern="MM yyyy"/></span>
                                                </div>
                                            </div>
                                            <div class="mt-2 mx-2">
                                                <p class="comment-text">${nestedReply.content}</p>
                                            </div>
                                        </div>
                                        &lt;%&ndash;</c:if>&ndash;%&gt;
                                    </c:forEach>

                                        &lt;%&ndash;nested reply post&ndash;%&gt;
                                        &lt;%&ndash; principal &ndash;%&gt;
                                    <div class="d-flex flex-row user-info">
                                        <div class="form-inline my-2">
                                            <label for="nestedReplyId-${reply.id}"></label>
                                            <input type="text" class="form-control" placeholder="Id" id="nestedReplyId-${reply.id}">
                                            <label for="nestedReplyPassword-${reply.id}"></label>
                                            <input type="text" class="form-control ml-2" placeholder="Password" id="nestedReplyPassword-${reply.id}">
                                        </div>
                                    </div>
                                        &lt;%&ndash; textarea &ndash;%&gt;
                                    <div class="d-flex flex-row">
                                        <label>
                                            <textarea class="form-control ml-1 shadow-none textarea" id="nestedReplyContent-${reply.id}"></textarea>
                                        </label>
                                    </div>

                                    <div class="mt-2 text-right">
                                        <button class="btn btn-secondary btn-sm shadow-none" id="btn-nested-reply-save-${reply.id}"
                                                type="button" onclick="saveNestedReply(${reply.id})">Post comment
                                        </button>
                                        <button class="btn btn-outline-primary btn-sm ml-1 shadow-none" type="button">Cancel
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </li>
        </ul>--%>


    </div>
</div>


<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>
