<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="./layout/header.jsp" %>
<style>
    .list-unstyled > li > a {
        color: dimgray;
    }
</style>
<div class="container">
    <h3>Collapsible Navbar</h3>
    <br>
    <div class="row">
        <aside class="col-md-3 blog-sidebar border">
            <div class="p-3 mb-3 bg-light rounded">
                <h4 class="font-italic">About</h4>
                <p class="mb-0">Etiam porta <em>sem malesuada magna</em> mollis euismod. Cras mattis consectetur purus
                    sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.</p>
            </div>

            <div class="p-3">
                <h4 class="font-italic">Archives</h4>
                <ol class="list-unstyled mb-0">
                    <li><a href="#">March 2014</a></li>
                    <li><a href="#">February 2014</a></li>
                    <li><a href="#">January 2014</a></li>
                    <li><a href="#">December 2013</a></li>
                    <li><a href="#">November 2013</a></li>
                    <li><a href="#">October 2013</a></li>
                    <li><a href="#">September 2013</a></li>
                    <li><a href="#">August 2013</a></li>
                    <li><a href="#">July 2013</a></li>
                    <li><a href="#">June 2013</a></li>
                    <li><a href="#">May 2013</a></li>
                    <li><a href="#">April 2013</a></li>
                </ol>
            </div>

            <div class="p-3">
                <h4 class="font-italic">Elsewhere</h4>
                <ol class="list-unstyled">
                    <li><a href="#">GitHub</a></li>
                    <li><a href="#">Twitter</a></li>
                    <li><a href="#">Facebook</a></li>
                </ol>
            </div>
        </aside>

        <div class="col-md-8 blog-main">
            <c:forEach var="board" items="${boards.content}">
                <div class="card my-2">
                        <%--<img class="card-img-top" src="profile.png" alt="Card image">--%>
                    <div class="card-body">
                        <h4 class="card-title">${board.title}</h4>
                            <%-- board 객체의 getTitle 호출됨 --%>
                        <a href="/board/${board.id}" class="btn btn-empty" style="color: dimgray; font-family: Verdana">Continue Reading...</a>
                    </div>
                </div>
            </c:forEach>

            <%-- pagination --%>
            <%-- 표시될 최대 페이지 수(maxPage) --%>
            <c:set var="pageLimit" value="${boards.totalPages>maxPage?maxPage:boards.totalPages}"/>

            <ul class="pagination justify-content-center">
                <%-- prev button --%>
                <c:choose>
                    <c:when test="${boards.first}">
                        <li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Prev</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Prev</a></li>
                    </c:otherwise>
                </c:choose>

                <c:forEach var="i" begin="1" end="${pageLimit}">
                    <li <c:if test="${i-1 eq boards.number}">class="page-item active"</c:if>><a class="page-link"
                                                                                              href="?page=${i-1}">${i}</a>
                    </li>
                </c:forEach>

                <%-- next button --%>
                <c:choose>
                    <c:when test="${boards.last}">
                        <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>

<%@ include file="./layout/footer.jsp" %>