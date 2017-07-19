function showLoginPopup(){	
	document.getElementById("enter-wrap").style.display = "block";	
	document.getElementById("register-wrap").style.display = "none";	
	document.getElementsByClassName("btn-enter")[1].style.color = "white";
	document.getElementsByClassName("btn-registration")[1].style.color = "#ffa71b";
}
function showRegisterPopup(){
	document.getElementById("enter-wrap").style.display = "none";	
	document.getElementById("register-wrap").style.display = "block";	
}
function closeLogoutPopup(){
	document.getElementById("logout-popup").style.display = "none";	
	document.getElementById("logout-check").style.display = "none";	
}

function closePopup(){
	document.getElementById("authorization-popup").style.display = "none";	
	document.getElementById("authorization-form").style.display = "none";	
}

function cleanForm(){
	document.getElementById("reg_email").value="";
	document.getElementById("reg_password").value="";
	document.getElementById("conf_password").value="";
	document.getElementById("reg_name").value="";
	document.getElementById("reg_surname").value="";
	document.getElementById("login").value="";
	document.getElementById("password").value="";
	var error_labels = document.getElementsByClassName("error-label");
	for(var i = 0; i < error_labels.length; i++){
		error_labels[i].classList.add("hidden");
	}
	
	var len = document.getElementsByClassName("error").length;	
	for(var i = 0; i < len; i++){
		document.getElementsByClassName("error")[0].classList.remove("error");
	}
}

function validateEnter(form){
	var elems = form.elements;
	if(!elems.login.value){
		elems.login.classList.add("error");
		document.getElementById("login-error").classList.remove("hidden");
	}
	else{
		elems.login.classList.remove("error");
		document.getElementById("login-error").classList.add("hidden");
	}
	if(!elems.password.value){
		elems.password.classList.add("error");
		document.getElementById("login-error").classList.remove("hidden");
	}
	else{
		elems.password.classList.remove("error");
		document.getElementById("login-error").classList.add("hidden");
	}
}

function validateRegistration(form) {

	var REGEX_EMAIL = new RegExp("^.+@\\w+\\.\\w+$");
	var REGEX_PASSWORD = new RegExp("^(?=\\w{6,10}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)");

	var elems = form.elements;
	if(elems.rule.checked){ 

		document.getElementById("rule_error").classList.add("hidden");		
	}	
	else{

		document.getElementById("rule_error").classList.remove("hidden");
	}

	if(REGEX_EMAIL.test(elems.reg_email.value)){
		document.getElementById("email_error").classList.add("hidden");
		elems.reg_email.classList.remove("error");
	}	
	else{
		document.getElementById("email_error").classList.remove("hidden");
		elems.reg_email.classList.add("error");
	}
	
	if((!elems.reg_password.value)||(elems.reg_password.value.lenght < 6 )|| (elems.reg_password.value.lenght > 20)){
		document.getElementById("password_size_error").classList.remove("hidden");
		elems.reg_password.classList.add("error");		
	}
	else{
		document.getElementById("password_size_error").classList.add("hidden");
		elems.reg_password.classList.remove("error");	
		if (REGEX_PASSWORD.test(elems.reg_password.value)){
			document.getElementById("password_error").classList.add("hidden");
			elems.reg_password.classList.remove("error"); 
		}else{
			document.getElementById("password_error").classList.remove("hidden");
			elems.reg_password.classList.add("error");
		}
		
	}
	
	if(elems.reg_password.value != elems.conf_password.value){
		document.getElementById("conf_password_error").classList.remove("hidden");
		elems.conf_password.classList.add("error");
	}	
	else{
		document.getElementById("conf_password_error").classList.add("hidden");
		elems.conf_password.classList.remove("error");

	}

	if((!elems.reg_age.value) || (elems.reg_age.value < 18) || (elems.reg_age.value > 120)){
		document.getElementById("age_error").classList.remove("hidden");
		elems.reg_age.classList.add("error");
	}	
	else{
		document.getElementById("conf_password_error").classList.add("hidden");
		elems.conf_password.classList.remove("error");

	}
}
function showLogoutPopup(){
	document.getElementById("logout-popup").style.display = "block";
	document.getElementById("logout-check").style.display = "block";			
}




