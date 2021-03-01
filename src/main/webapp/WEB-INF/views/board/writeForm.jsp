<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form>
        <div class="container">
            <form>
                <div class="form-group">
                    <label for="title">Title:</label>
                    <input type="text" class="form-control" id="title" placeholder="Enter Title" name="title">
                </div>

                <div class="form-group">
                    <label for="content">Content:</label>
                    <textarea class="form-control" rows="5" id="content"></textarea>
                </div>

                <button id="btn-save" class="btn btn-primary">저장</button>
            </form>
        </div>

    </form>
</div>

<%@ include file="../layout/footer.jsp" %>
