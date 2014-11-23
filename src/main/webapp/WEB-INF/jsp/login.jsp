<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:url value="/" var="loginUrl" />
<c:url value="/help" var="helpUrl" />
<c:url value="/register" var="createAccountUrl" />


<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Zaloguj i korzystaj z możliwości jakie daje Orlikeo!</h1>
            <div class="account-wall">
                <img class="profile-img" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=120"
                    alt="">
                <form method="get" class="form-signin" action="${loginUrl}">           
                <div class="form-group">
					<div class="input-group" data-validate="email">
						<input type="text" class="form-control" name="email" id="email"  placeholder="E-mail" required autofocus>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>					
				<div class="form-group">
					<div class="input-group">
						<input type="password" class="form-control" name="password" id="password"  placeholder="Hasło" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Zaloguj</button>
                <label class="checkbox pull-left">
                <input type="checkbox" value="remember-me">Zapamiętaj mnie</label>
                <a href="${helpUrl}" class="pull-right need-help">Potrzebujesz pomocy? </a><span class="clearfix"></span>
                </form>
            </div>
            <a href="${createAccountUrl}" class="text-center new-account">Utwórz nowe konto</a>
        </div>
    </div>
</div>


