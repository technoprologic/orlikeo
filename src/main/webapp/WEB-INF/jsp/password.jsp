<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:url value="/password" var="passwordUrl" />



<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Zmien swoje hasło</h1>
            <div class="account-wall">
                <img class="profile-img" src="http://cdn.content.easports.com/fifa/fltOnlineAssets/2013/fut/items/images/players/web/158023.png"
                    alt="">
                <form method="get" class="form-signin" action="${passwordUrl}">           
                <div class="form-group">
					<div class="input-group">
						<input type="password" class="form-control" name="password" id="password"  placeholder="Stare hasło" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<input type="password" class="form-control" name="password" id="password"  placeholder="Nowe hasło" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>				
				<div class="form-group">
					<div class="input-group">
						<input type="password" class="form-control" name="password" id="password"  placeholder="Potwierdź nowe hasło" required>
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Zmień hasło</button>
                </form>
            </div>
        </div>
    </div>
</div>


