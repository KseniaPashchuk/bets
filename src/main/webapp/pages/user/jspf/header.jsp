<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<header class="header-wrap row clearfix">
    <div class="logo-wrap colored-block col-lg-3">
        <a href="${pageContext.servletContext.contextPath}/controller?command=show_main_page"><img class="logo-img"
                                                                                       src="../../resources/images/logo-1.png"
                                                                                       alt="<fmt:message key="company_name"/>"></a>
    </div>
    <div class="menu-wrap colored-block ">
        <ul class="main-menu clearfix">
            <li class="item-main"><a href="${pageContext.servletContext.contextPath}/pages/common/news.jsp"
                                     class="main-link">
                <fmt:message key="common.menu.news"/></a>
            </li>
            <li class="item-main"><a
                    href="${pageContext.servletContext.contextPath}/controller?command=create_matches_page"
                    class="main-link"><fmt:message key="common.menu.matches"/></a>
            </li>
            <li class="item-main">
                <a href="${pageContext.servletContext.contextPath}/controller?command=show_faq"
                   class="main-link"><fmt:message key="common.menu.FAQ"/></a>
            </li>
            <li class="item-main">
                <a href="${pageContext.servletContext.contextPath}/controller?command=show_user_profile"
                   class="main-link"><fmt:message key="common.menu.profile"/></a>
            </li>
        </ul>
    </div>
    <div class="lang-wrap colored-block ">
        <a href="@" class="lang">RU</a>

    </div>
    <div class="user-action colored-block col-lg-3">
        <div class="authorization">
            <a href="javascript://" class="btn-enter" id="show-logout"><fmt:message key="common.logout.btn.quit"/></a>
        </div>
    </div>
</header>
