<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/friends/user" var="detailsUrl" />


Page Heading
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Znajomi <small><i class="glyphicon glyphicon-user"></i> Dodaj znajomego</small>
		</h1>
	</div>
</div>
/.row


<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
        <h1 class="text-center login-title">Zarejestruj nowe konto</h1>
        <div class="account-wall">
            <form method="get" class="form-signin" action="${sendUserInvotation}">
				<div class="form-group">
        			<label for="validate-email">Adres e-mail</label>
					<div class="input-group" data-validate="email">
						<input type="text" class="form-control" name="validate-email" id="validate-email"  required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
                <button type="submit" class="btn btn-lg btn-primary btn-block" disabled>Zarejestruj</button>
            </form>
            </div>
        </div>
    </div>
</div>