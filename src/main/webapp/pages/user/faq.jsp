<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<article class="row">
    <div class="colored-block">
        <div class="faq-wrap">
            <c:forEach var="item" items="${faqList}">
                <div class="faq-item clearfix">
                    <div class="faq-item-title clearfix" id="faq_title_${item.id}">${item.question}</div>
                    <div class="faq-item-answer" id="faq_answer_${item.id}" style="display: none">${item.answer}</div>
                </div>
            </c:forEach>
        </div>
    </div>
</article>