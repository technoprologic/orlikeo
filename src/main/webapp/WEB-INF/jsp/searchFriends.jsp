<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/friends/search" var="searchFriends" />
<c:url value="/friends/userDetail" var="userDetails" />

<c:url value="/friends/friendRequest" var="inviteFriend" />

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Znajomi <small><i class="glyphicon glyphicon-user"></i> Wyszukaj Znajomego</small>
		</h1>
	</div>
</div>



<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
        <h1 class="text-center login-title">Wyszukaj</h1>
        <div class="account-wall">
            <form:form method="POST" class="form-signin" action="${ searchFriends }">
				<div class="form-group">
        			<label for="validate-email">Adres e-mail</label>
					<div class="input-group" data-validate="email">
						<input  type="text" class="form-control" name="email" id="validate-email"  required >
						<span class="input-group-addon danger"><span class="glyphicon glyphicon-remove"></span></span>
					</div>
				</div>
                <button type="submit" class="btn btn-lg btn-primary btn-block" disabled>Szukaj</button>
            </form:form>
            </div>
        </div>
    </div>
</div>



<c:if test="${ not empty userEmail }">
		<legend>Znaleziono</legend>
		<table data-toggle="table" style="background-color: white" >
			<thead>
				<tr>
					<th>Użytkownik</th>
					<th>Akcja</th>
				</tr>
			</thead>
			<tbody>
               	<tr>
					<td>${ userEmail }</td>
					<td><div class="container">
							<a href="${ userDetails }/${userEmail}" class="btn btn-info" role="button">Zobacz profil</a>
							<%-- <form:form method="POST" action="${ inviteFriend }" >
								
								<input  type="hidden" class="form-control" name="email" id="validate-email"  value="${ userEmail }" >
								<button type="submit" class="btn  btn-primary" >Zaproś</button>
							</form:form> --%>
							</div>
					</td>
			    </tr>
			</tbody>
		</table>
</c:if>



<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />" ></script>