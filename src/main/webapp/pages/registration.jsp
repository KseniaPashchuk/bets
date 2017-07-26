<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ctg" uri="mtag" %>

<html>
<head>
    <title> Tag Example</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/bets-style.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap-datetimepicker.css">

    <script type="text/javascript" src="../js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="../js/bootstrap.js"></script>
    <script type="text/javascript" src="../js/moment.js"></script>
    <script type="text/javascript" src="../js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="../js/index.js"></script>
    <script type="text/javascript" src="../js/registration.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Fira+Mono:400,500&amp;subset=cyrillic" rel="stylesheet">
</head>
<body>
<div class="authorization-wrap">
    <div class="enter-wrap popup-holder" id="enter-wrap">
        <div class="buttons">
            <a class="btn-enter" href="javascript:showLoginPopup()" style="color: white">Вход</a>
            <span>/</span>
            <a class="btn-registration" href="javascript:showRegisterPopup()" style="color: #ffa71b">Регистрация</a>
        </div>
        <form class="enter" id="login_form" onsubmit="validateEnter(this.form)" method="POST" action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="sign_in"/>
            <div class="head-enter-registration">Вход в систему</div>
            <label class="hidden error-label" id="login-error">Неправильный логин или пароль!</label>
            <div class="shell-for-input">
                <label for="login">
                    <input class="e-mail-enter" type="text" id="login" name="login" placeholder="Логин" value=""
                           autocomplete="off">
                </label>

            </div>
            <div class="shell-for-input">
                <label for="password">
                    <input class='pass-enter' type="password" id="password" name="password" placeholder="Пароль"
                           value="" autocomplete="off">
                </label>
            </div>
            <a href="javascript://" class="forget-link">Забыли пароль?</a>
            <input type="submit" id="login-btn" class="sign-in-btn" value="Войти">
        </form>
    </div>
    <div class="register-wrap popup-holder" style="display: none" id="register-wrap">
        <div class="buttons">
            <a class="btn-enter" href="javascript:showLoginPopup()" style="color: #ffa71b">Вход</a>
            <span>/</span>
            <a class="btn-registration" href="javascript:showRegisterPopup()" style="color: white">Регистрация</a>
        </div>
        <form class="registration" id="reg_form" onsubmit="validateEnter(this.form)" method="POST" action="${pageContext.servletContext.contextPath}/controller">
            <input type="hidden" name="command" value="sign_up"/>
            <div class="about-registration">
                <div class="head-enter-registration">Регистрация пользователя</div>
                <div class="necessarily">Все поля формы обязательны для заполнения!</div>
                <div class="hidden error-label" id="rule_error" name="rule_error">Пожалуйста, ознакомтесь с правилами и
                    условиями
                </div>
            </div>
            <div class="shell-registration clearfix">
                <div class="left-side-of-registration">
                    <div class="shell-for-input">
                        <div class="shell-for-input  add-hint">
                            <label for="reg_email">
                                <input class='e-mail-reg required' type="text" id="reg_email" name="login" autocomplete="false">
                                <div class="input-label"  id="reg_email_label">email@example.com<span style="color: red">*</span></div>
                            </label>
                        </div>
                    </div>
                    <div class="shell-for-input">
                        <label for="reg_password">
                            <input type="password" id="reg_password" class="required" name="password" autocomplete="false">
                            <div class="input-label" id="reg_password_label">password<span
                                    style="color: red">*</span></div>
                        </label>
                    </div>

                    <div class="shell-for-input">
                        <label for="conf_password">
                            <input type="password" id="conf_password" name="conf_password" class="required" autocomplete="false">
                            <div class="input-label"  id="conf_password_label">confirm
                                password<span style="color: red">*</span></div>
                        </label>

                    </div>
                    <div class="shell-for-input">
                        <label for="reg_name">
                            <input type="text" id="reg_name" class="required" name="name" autocomplete="false">
                            <div class="input-label" id="reg_name_label">name<span
                                    style="color: red">*</span></div>
                        </label>
                    </div>
                    <div class="shell-for-input">
                        <label for="reg_surname">
                            <input type="text" id="reg_surname" class="required" name="surname" autocomplete="false">
                            <div class="input-label"  id="reg_surname_label">surname<span
                                    style="color: red">*</span></div>
                        </label>
                    </div>
                </div>
                <div class="right-side-of-registration">
                    <div class="shell-for-input">
                        <label for="reg_credit_card">
                            <input type="text" id="reg_credit_card" class="required" name="credit_card" autocomplete="false">
                            <div class="input-label"  for="reg_credit_card" id="reg_credit_card_label">Credit card
                                number<span style="color: red">*</span></div>
                        </label>
                    </div>
                    <div class="shell-for-input clearfix">
                        <div class="select-birth-date input-group date" id='select-birth-date'>
                            <input type="text" class="form-control" name="birth_date" placeholder="dd/mm/yyyy "/>
                            <span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
                        </div>
                    </div>
                    <div class="common-select shell-for-input clearfix">
                        <select class="fake-width-currency" id="regSelectCurrency">
                            <option value="1" selected="selected">USD. Американский доллар</option>
                            <option value="2">EUR. Евро</option>
                            <option value="3">RUB. Российский рубль</option>
                        </select>
                    </div>
                    <div class="confirmation">
                        <input id="rule" type="checkbox"/>
                        <label for="rule">С <a class='read-rule' href='Info#Info/rules'>правилами</a> и <a
                                class="orange" href="Default.aspx#Info/siteRules">условиями</a> ознакомлен и
                            согласен</label>
                    </div>
                </div>
            </div>
            <input type="submit" id="register-btn" class="sign-up-btn" value="Зарегистрироваться"
                   onclick="validateRegistration(this.form)">
        </form>
    </div>
    <div id="pass-recovery" style="display: none">
        <div class="popup-holder forget-pass" id="forget-form">
            <h2>Восстановление пароля.</h2>
            <form class="forget-form" id="pass-rec">
                <label>Введите Ваш E-mail</label>
                <input type="text" name="input_mail" placeholder="E-mail">
                <p class="abort-mail error"></p>
                <input class="button-enter button-forget" type="button" value='Восстановить'>
            </form>
        </div>
        <div id="after-forget" class="popup-holder after-forget">
            <h2>Восстановление пароля.</h2>
            <div class="notice">Пароль будет отправлен на указанный E-mail</div>
            <div class="popup-close">x</div>
        </div>
    </div>
</div>
</body>
</html>

