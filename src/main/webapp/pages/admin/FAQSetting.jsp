<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<div class="row">

    <div class="show-message">
        <p class="error-label"
           <c:if test="${(!btg:contains(errors,'createFAQError' ))}">style="display:none;"</c:if>>
            <fmt:message key="admin.faq.create.error"/>
        </p>
        <p class="error-label"
           <c:if test="${(!btg:contains(errors,'editFAQError' ))}">style="display:none;"</c:if>>
            <fmt:message key="admin.faq.edit.error"/>
        </p>
        <p class="error-label"
           <c:if test="${(!btg:contains(errors,'deleteFAQError' ))}">style="display:none;"</c:if>>
            <fmt:message key="admin.faq.delete.error"/>
        </p>
    </div>
    <div class=" create-faq-wrap colored-block">
        <div class="create-faq-title"><fmt:message key="admin.faq.create"/></div>

        <form class="create-faq" action="${pageContext.servletContext.contextPath}/controller"
              <c:if test="${!btg:contains(errors,'invalidCreateParams')}">style="display:none;"</c:if>
              method="POST"
              onsubmit="return validateCreateFaqForm()">

            <input type="hidden" name="command" value="create_faq"/>
            <input class="input-text <c:if test="${!btg:contains(errors,'invalidFAQQuestionError' )}">error</c:if>"
                   type="text" name="question"
                   placeholder="<fmt:message key="admin.faq.type_question"/>"
                   id="create-question">
            <p class="error-label" id="create-invalid-question"
               <c:if test="${!btg:contains(errors,'invalidFAQQuestionError' )}">style="display:none;"</c:if>>

                <fmt:message key="admin.faq.invalid_question"/>

            </p>
            <textarea
                    class="input-text text-area <c:if test="${!btg:contains(errors,'invalidFAQAnswerError' )}">error</c:if>"
                    name="answer" cols="0" rows="0"
                    id="create-answer" placeholder="<fmt:message
                        key="admin.faq.type_answer"/>"></textarea>
            <p class="error-label" id="create-invalid-answer"
               <c:if test="${!btg:contains(errors,'invalidFAQAnswerError' )}">style="display:none;"</c:if>>
                <fmt:message key="admin.faq.invalid_answer"/>
            </p>
            <input class="input-btn" type="submit" value="<fmt:message key="common.btn.create"/>">
        </form>
    </div>
</div>
<article class="row">
    <div class="colored-block">
        <div class="faq-wrap">
            <p class="error-label"
               <c:if test="${!btg:contains(errors,'showFAQError' )}">style="display:none;"</c:if>>
                <fmt:message key="common.faq.error"/>
            </p>
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
<div class="popup-wrap" id="edit-faq-popup"
     <c:if test="${!btg:contains(errors,'invalidEditParams')}">style="display: none"</c:if>>
    <div class="popup-holder edit-faq-holder" id="edit-faq"
         <c:if test="${!btg:contains(errors,'invalidEditParams')}">style="display: none"</c:if>>
        <form class="edit-faq-form clearfix" action="${pageContext.servletContext.contextPath}/controller" method="POST"
              onsubmit="return validateEditFaqForm()">
            <input type="hidden" name="command" value="edit_faq"/>
            <input class="input-text" type="text" name="question" id="edit-question" value="" readonly="readonly">
            <textarea
                    class="input-text text-area <c:if test="${!btg:contains(errors,'invalidFAQAnswer' )}">error</c:if>"
                    name="answer" id="edit-answer" cols="0" rows="0"></textarea>
            <p class="error-label" id="edit-invalid-answer"
               <c:if test="${!btg:contains(errors,'invalidFAQAnswer')}">style="display: none"</c:if>>
                <fmt:message key="admin.faq.invalid_answer"/>
            </p>
            <div class="btn-group">
                <input type="submit" value='<fmt:message key="common.btn.save"/>'>
                <input class="edit-faq-close" type="button" value='<fmt:message key="common.btn.cancel"/>'>
            </div>
        </form>
        <div class="popup-close edit-faq-close"><a href="">x</a></div>
    </div>
</div>
<div class="popup-wrap" id="delete-faq-popup" style="display: none">
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
