<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<div class="row">

    <div class="colored-block">
        <p class="error-label">
            <c:if test="${not empty errors['error']}">
                <fmt:message key="${errors['error']}"/>
            </c:if>
        </p>
    </div>
    <div class=" create-faq-wrap colored-block">
        <div class="create-faq-title"><fmt:message key="admin.faq.create"/></div>
        <c:choose>
        <c:when test="${not empty errors['createErrors']}">
        <form class="create-faq" action="${pageContext.servletContext.contextPath}/controller" method="POST"
              onsubmit="return validateCreateFaqForm()">
            </c:when>
            <c:otherwise>
            <form class="create-faq" action="${pageContext.servletContext.contextPath}/controller" style="display: none"
                  method="POST"
                  onsubmit="return validateCreateFaqForm()">
                </c:otherwise>
                </c:choose>
                <input type="hidden" name="command" value="create_faq"/>
                <input class="input-text" type="text" name="question"
                       placeholder="<fmt:message key="admin.faq.type_question"/>"
                       id="create-question">
                <p class="error-label" id="create-invalid-question">
                    <c:if test="${(not empty errors['invalidFAQQuestion'])&&(not empty errors['createErrors'])}">
                        <fmt:message key="${errors['invalidFAQQuestion']}"/>
                    </c:if>
                </p>
                <textarea class="input-text text-area" name="answer" cols="0" rows="0"
                          id="create-answer" placeholder="<fmt:message
                        key="admin.faq.type_answer"/>"></textarea>
                <p class="error-label" id="create-invalid-answer">
                    <c:if test="${(not empty errors['invalidFAQAnswer'])&&(not empty errors['createErrors'])}">
                        <fmt:message key="${errors['invalidFAQAnswer']}"/>
                    </c:if>
                </p>
                <input class="input-btn" type="submit" value="<fmt:message key="common.btn.create"/>">
            </form>
    </div>
</div>
<article class="row">
    <div class="colored-block">
        <div class="faq-wrap">
            <c:forEach var="item" items="${faqList}">
                <form class="faq-item clearfix">
                    <div class="clearfix">
                        <div class="faq-item-title" style="float: left" id="faq_title_${item.id}">${item.question}</div>
                        <div class="btn-group faq-edit-btn-group clearfix">
                            <button type="button" class="btn-edit"><span class="glyphicon glyphicon-pencil"></span>
                            </button>
                            <button type="button" class="btn-delete"><span class="glyphicon glyphicon-trash"></span>
                            </button>
                        </div>
                    </div>
                    <div class="faq-item-answer" id="faq_answer_${item.id}" style="display: none">${item.answer}</div>
                </form>
            </c:forEach>
        </div>
    </div>
</article>
<div class="popup-wrap" id="edit-faq-popup">
    <div class="popup-holder edit-faq-holder" id="edit-faq" style="display: none">
        <form class="edit-faq-form clearfix" action="${pageContext.servletContext.contextPath}/controller" method="POST"
              onsubmit="return validateEditFaqForm()">
            <input type="hidden" name="command" value="edit_faq"/>
            <input class="input-text" type="text" name="question" id="edit-question" value="" readonly="readonly">
            <textarea class="input-text text-area" name="answer" id="edit-answer" cols="0" rows="0"></textarea>
            <p class="error-label" id="edit-invalid-answer">
                <c:if test="${(not empty errors['invalidFAQAnswer'])&&(not empty errors['editErrors'])}">
                    <fmt:message key="${errors['invalidFAQAnswer']}"/>
                </c:if>
            </p>
            <div class="btn-group">
                <input type="submit" value='<fmt:message key="common.btn.save"/>'>
                <input class="edit-faq-close" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close edit-faq-close"><a href="">x</a></div>
    </div>
</div>
<div class="popup-wrap" id="delete-faq-popup">
    <div class="popup-holder delete-faq-holder" id="delete-faq" style="display: none">
        <form class="delete-faq-form clearfix" action="${pageContext.servletContext.contextPath}/controller"
              method="POST">
            <input type="hidden" name="command" value="delete_faq"/>
            <h3><fmt:message key="admin.delete_question"/></h3>
            <input class="input-text" type="text" name="question" id="delete-faq-question" value="">
            <div class="btn-group">
                <input type="submit" value='<fmt:message key="common.btn.delete"/>'>
                <input class="delete-faq-close" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close delete-faq-close"><a href="">x</a></div>
    </div>
</div>
