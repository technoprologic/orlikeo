<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/account/password" var="passwordUrl" />
<c:url value="/account/edit" var="editAccountUrl" />
<c:url value="/register" var="removeAccountUrl" />



<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Profil zawodnika <small><i class="glyphicon glyphicon-info-sign"></i> Informacje</small>
		</h1>
	</div>
</div>


<div class="container" >
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">${ user.name } ${ user.surname }</h3>
            </div>
            <div class="panel-body">
              <div class="row">
                <div class="col-md-3 col-lg-3 " align="center"> 
                <img alt="User Pic" src="http://afcchiropracticphoenix.com/wp-content/plugins/google-places-reviews-pro/assets/images/mystery-man.png" class="img-circle"></div>
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
						<td>Email:</td>
                        <td><a href="mailto:${user.email}">${user.email}</a></td>
                      </tr>
                      <tr>
						<td>Data urodzenia:</td>
                        <td><fmt:formatDate value="${user.dateOfBirth}" type="both" pattern="dd.MM.yyyy" /></td>
                      </tr>
                      <tr>
						<td>Pozycja:</td>
                        <td>${user.position}</td>
                      </tr>
                      <tr>
						<td>Waga:</td>
                        <td>${user.weight}</td>
                      </tr>
                      <tr>
						<td>Wzrost:</td>
                        <td>${user.height}</td>
                      </tr>
                      <tr>
						<td>Noga:</td>
                        <td>${user.foot}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
			<div class="panel-footer text-right">
				<c:if test="${ self }">
					<a href="${passwordUrl}" class="btn btn-primary">Zmien hasło</a>
					<a href="${editAccountUrl}" class="btn btn-primary">Edytuj profil</a>
					<a href="${removeAccountUrl}" class="btn btn-danger" title="Usuń konto"><i class="glyphicon glyphicon-remove"> </i></a>
				</c:if>
		<c:choose>
					<c:when test="${ contact == 'pendingRequester'}">Wysłano zaproszenie do użytkownika
                        <button type="button" title="Cofnij" class="btn btn-warning" data-toggle="modal" data-target="#cancelInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-stop"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'pendingReceiver'}">Otrzymałeś zaproszenie od użytkownika
                        <button type="button" title="Akceptuj" class="btn btn-success" data-toggle="modal" data-target="#acceptInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ok-sign"></i></button>
                        <button type="button" title="Odrzuć" class="btn btn-warning" data-toggle="modal" data-target="#declineInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-remove-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'friends'}">Jesteście znajomymi
                        <button type="button" title="Usuń" class="btn btn-danger" data-toggle="modal" data-target="#removeFriendshipModal" data-whatever="${ email }"><i class="glyphicon glyphicon-minus-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'decliner'}">Odrzuciłeś zaproszenie użytkownika
                        <button type="button" title="Zaproś" class="btn btn-success" data-toggle="modal" data-target="#inviteUserModal" data-whatever="${ email }"><i class="glyphicon glyphicon-plus-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'declined'}">Użytkownik odrzucił Twoje zaproszenie
                        <button type="button" title="Zaproś" class="btn btn-success" data-toggle="modal" data-target="#inviteUserModal" data-whatever="${ email }"><i class="glyphicon glyphicon-plus-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when>
					<c:when test="${ contact == 'blocker'}">Zablokowałeś użytkownika
                        <button type="button" title="Odblokuj" class="btn btn-success" data-toggle="modal" data-target="#unblockUser" data-whatever="${ email }"><i class="glyphicon glyphicon-refresh"></i></button>
					</c:when>
					<c:when test="${ contact == 'blocked'}">
						Zostałeś zablokowany przez użytkownika
					</c:when>
					<c:when test="${ contact == 'without'}">
                        <button type="button" title="Zaproś" class="btn btn-success" data-toggle="modal" data-target="#inviteUserModal" data-whatever="${ email }"><i class="glyphicon glyphicon-plus-sign"></i></button>
                        <button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</c:when> 
		</c:choose>
			</div>
          </div>
        </div>
</div>

<%--
<button type="button" title="Zaproś" class="btn btn-success" data-toggle="modal" data-target="#inviteUserModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-plus-sign"></i></button>
<button type="button" title="Akceptuj" class="btn btn-success" data-toggle="modal" data-target="#acceptInvitationModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-ok-sign"></i></button>
<button type="button" title="Odrzuć" class="btn btn-warning" data-toggle="modal" data-target="#declineInvitationModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-remove-sign"></i></button>
<button type="button" title="Cofnij" class="btn btn-warning" data-toggle="modal" data-target="#cancelInvitationModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-stop"></i></button>
<button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
<button type="button" title="Odblokuj" class="btn btn-success" data-toggle="modal" data-target="#unblockUser" data-whatever="${ user.email }"><i class="glyphicon glyphicon-refresh"></i></button>
<button type="button" title="Usuń" class="btn btn-danger" data-toggle="modal" data-target="#removeFriendshipModal" data-whatever="${ user.email }"><i class="glyphicon glyphicon-minus-sign"></i></button>
--%>
