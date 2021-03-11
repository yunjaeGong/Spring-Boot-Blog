<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<style>
    /* reply system */

    ul.replys {
        margin: 0px;
        padding: 0px;
        list-style: none;
    }
    ul.replys li.reply {
        position: relative;
        margin-bottom: 15px;
        padding: 0px;
        z-index: 9;
    }
    ul.replys li.reply:before {
        content: '';
        width: 1px;
        height: 100%;
        background: #ebedf2;
        position: absolute;
        left: 15px;
        top: 15px;
    }
    ul.replys li.reply:last-child:before {
        display: none;
    }
    ul.replys li.reply .reply-wrapper {
        position: relative;
        z-index: 99;
    }
    ul.replys li.reply .card .card-header {
        position: relative;
        padding-top: 8px;
        padding-bottom: 8px;
    }
    ul.replys li.reply .card .card-header img {
        width: 32px;
        background: #fff;
    }
    ul.replys li.reply .card .card-header h5 {
        font-size: 16px;
        line-height: 1;
        margin: 0 10px;
    }
    ul.replys li.reply .card .card-header .reply-date {
        font-size: 14px;
        color: #bbb;
    }
    ul.replys li.reply .card .card-header .ribbon {
        width: 40px;
        height: 40px;
        overflow: hidden;
        position: absolute;
        top: -5px;
        left: -5px;
    }
    ul.replys li.reply .card .card-header .ribbon span {
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
    ul.replys li.reply .card .card-header .ribbon:before,
    ul.replys li.reply .card .card-header .ribbon:after {
        position: absolute;
        z-index: -1;
        content: '';
        display: block;
        border: 5px solid #2980b9;
        border-top-color: transparent;
        border-left-color: transparent;
    }
    ul.replys li.reply .card .card-header .ribbon:before {
        top: 0;
        right: 0;
    }
    ul.replys li.reply .card .card-header .ribbon:after {
        bottom: 0;
        left: 0;
    }
    ul.replys li.reply ul {
        padding-left: 30px;
        margin-top: 15px;
    }
    ul.replys li > ul > li:first-child > .reply-wrapper > .card > .card-header:after {
        content: '';
        width: 15px;
        height: 1px;
        background: #ebedf2;
        position: absolute;
        left: -15px;
        top: 50%;
        z-index: 3;
    }
    ul.replys li > ul > li:first-child > .reply-wrapper > .card > .card-header:before {
        content: '';
        width: 1px;
        height: 100%;
        background: #ebedf2;
        position: absolute;
        left: -16px;
        bottom: 50%;
        z-index: 1;
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

    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
    <c:if test="${board.user.id == principal.user.id}">
    <button id="btn-delete" class="btn btn-danger">삭제</button>
    <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
    </c:if>
    <br/><br/>

    <%-- replies --%>

    <div>
        <div class="card mb-2">
            <div class="card-header d-flex align-items-center">
                <p class="d-flex align-items-center"><h5 style="color: dimgray;font-size: 16px;line-height: 1;margin: 0 10px;">Reply</h5></p>
            </div>
            <c:choose>
                <c:when test="${empty principal}">
                    <div class="form-inline my-2">
                        <label for="replyId"></label>
                        <input type="text" class="form-control ml-2" placeholder="Id" id="replyId">
                        <label for="replyPassword"></label>
                        <input type="text" class="form-control ml-2" placeholder="Password" id="replyPassword">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="mx-2">
                        <a href="#"><h4>${principal.user.username}</h4></a>
                    </div>
                </c:otherwise>
            </c:choose>

            <div><textarea class="form-control px-2" id="replyContent" rows="3"></textarea></div>
            <div class="card-footer bg-white p-2">
                <button type="button" class="btn btn-secondary btn-sm">Register</button>
            </div>
        </div>

        <%-- https://github.com/ZsharE/threaded-replys-bootstrap --%>
        <%-- TODO: MIT License 표기 --%>

        <br/>
        <ul class="replies">
            <c:forEach var="reply" items="${board.replies}">
                <li id="reply--1" class="reply">
                    <div class="reply-wrapper">
                        <div class="card">
                            <div class="card-header d-flex align-items-center">
                                <a href="#" class="d-flex align-items-center"><h5>${reply.user.username}</h5></a>
                                <div class="reply-date ml-2" data-toggle="tooltip" title="Feb 5, 2018 8:21 pm"
                                     data-placement="top" data-original-title="Feb 5, 2018 8:21 pm">${reply.createDate}
                                </div>
                            </div>
                            <div class="card-body">
                                <p class="card-text">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Eos sapiente,
                                    nam ipsam veritatis reiciendis dolore soluta magni sit pariatur veniam laborum
                                    perferendis, molestias amet excepturi voluptatem iure porro reprehenderit doloribus.</p>
                            </div>
                            <div class="card-footer bg-white p-2">
                                <button type="button" class="btn btn-secondary btn-sm">Reply</button>
                                <small class="text-muted ml-2">Last updated 3 mins ago</small>
                            </div>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>

        <div class="card">
            <div class="card-header">댓글 리스트</div>
            <ul class="list-group">
                <div class="d-flex justify-content-between p-2" style="border-style: none;">
                    <div>작성자 : username &nbsp;</div>
                    <button class="btn-danger badge">삭제</button>
                </div>

                <li class="list-group-item d-flex justify-content-between" style="border-style: solid none none none">
                    <div>댓글 내용</div>
                </li>
            </ul>
        </div>
    </div>
</div>


<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>
