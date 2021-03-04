<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>


<div class="container">
    <form>
        <input type="hidden" id="id" value="${board.id}">
        <div class="form-group">
            <input type="text" value="${board.title}" class="form-control" id="title" placeholder="Enter Title" name="title">
        </div>

        <div class="form-group">
            <textarea class="form-control summernote" rows="5" id="content"> value="${board.content}"</textarea>
        </div>
    </form>
    <button id="btn-update" class="btn btn-primary">수정</button>
</div>


<script>
    $('.summernote').summernote({
        placeholder: 'Enter Content',
        tabsize: 2,
        height: 300
    });
</script>

<script src="js/board.js"></script>

<%@ include file="../layout/footer.jsp" %>
