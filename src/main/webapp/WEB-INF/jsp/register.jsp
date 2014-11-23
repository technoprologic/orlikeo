<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:url value="/help" var="helpUrl" />
<c:url value="/register" var="createAccountUrl" />

<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
        <h1 class="text-center login-title">Zarejestruj nowe konto</h1>
        <div class="account-wall">
            <form method="post" class="form-signin">
            
				<div class="form-group">
        			<label for="validate-text">Imię</label>
					<div class="input-group">
						<input type="text" class="form-control" name="validate-text" id="validate-text"  required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
						
				<div class="form-group">
        			<label for="validate-text">Nazwisko</label>
					<div class="input-group">
						<input type="text" class="form-control" name="validate-text" id="validate-text"  required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
				
				<div class="form-group">
        			<label for="validate-email">Adres e-mail</label>
					<div class="input-group" data-validate="email">
						<input type="text" class="form-control" name="validate-email" id="validate-email"  required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
				
				<div class="form-group">
        			<label for="validate-password">Hasło</label>
					<div class="input-group">
						<input type="password" class="form-control" name="validate-password" id="validate-password"  required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
                <button type="submit" class="btn btn-lg btn-primary btn-block" disabled>Zarejestruj</button>
            </form>
            </div>
        </div>
    </div>
</div>