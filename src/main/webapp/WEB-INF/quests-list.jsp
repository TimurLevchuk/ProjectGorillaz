<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp" %>
<main role="main">
    <div class="album py-5 bg-light">
        <div class="container">
            <div class="row justify-content-md-center">
                <div class="col-md-auto">
                    <button onclick="document.location='/create-quest'"
                            type="button" class="btn btn-xl btn-outline-primary">Создать квест</button>
                </div>
            </div>
            <div class="row m-5">
                <c:forEach var="quest" items="${sessionScope.questsList}">
                    <c:set var="questId" value="${quest.getId()}" scope="session"/>
                    <div class="col-md-4">
                        <div class="card mb-4 box-shadow">
                            <a href="quest?id=${questId}">
                                <div style="display: flex; justify-content: center;">
                                    <img src="static/quest_image.jpg" alt="question image" height="350px"/>
                                </div>
                            </a>
                            <div class="card-body">
                                <p class="card-text"><b>${quest.getTitle()}</b></p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <button onclick="document.location='/quest?id=${questId}'"
                                                type="button"
                                                class="btn btn-sm btn-outline-secondary">Играть</button>
                                        <c:if test="${quest.getAuthor().getId().equals(sessionScope.userId)}">
                                            <button type="submit" form="deleteForm${questId}" class="btn btn-sm btn-outline-secondary">Удалить</button>
                                            <form id="deleteForm${questId}" action="delete?id=${questId}" method="POST"></form>
                                        </c:if>
                                    </div>
                                    <small class="text-muted">${quest.getAuthor().getName()}</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</main>
<%@include file="footer.jsp" %>
